import java.net.Socket;
import java.net.ServerSocket;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ThreadSpawn extends Thread{
    private Socket client;
    private Config config;
    // Set connection values for the thread.
    public ThreadSpawn(Socket client, Config config) {
        this.client = client;
        this.config = config;
    }

    public void run() {
        try (InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream()) {
                String url = getRequestUrl(in);
                if (url == null){
                    return;
                }

                if (url.endsWith("/")) {
                    url = "/html/index.html";
                }
                // Sanatize url.
                url.replace('.', ' ');
                // Build endpoints.
                String responseFilePath = config.getRoot() + "/" + config.getPath() + url;
                File file = new File(responseFilePath);
                // Check if endpoint exist.
                if (!file.exists()) {
                    sendNotFound(out);
                    return;
                }
                // Send http header out.
                sendHeader(out);
                // Send file payload.
                sendFile(out, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    // Parse user url request.
    private String getRequestUrl(InputStream stream) {
        Scanner reader = (new Scanner(stream)).useDelimiter("\n");
        if (!reader.hasNext()) {
            return null;    
        }

        String line = reader.next();
        String url = line.split(" ")[1];
        return url;
    }
    // Payload for file not found.
    private void sendNotFound(OutputStream output) {
        PrintStream out = new PrintStream(output);
        out.println("HTTP/1.0 404 Not Found");
        out.println("");
        out.println("NOT FOUND");
    }
    // Payload for the http header.
    private void sendHeader(OutputStream output) {
        PrintStream out = new PrintStream(output);
        out.println("HTTP/1.0 200 OK");
        out.println("Content_Type:text/html");
        out.println("");
    }
    // Reads a file and sends it out in binary format.
    private void sendFile(OutputStream output, File file) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            int len = (int)file.length();
            byte buf[] = new byte[len];
            in.readFully(buf);
            output.write(buf, 0, len);
        }
    }
}