import java.net.Socket;
import java.net.ServerSocket;
import java.io.File;
import java.io.IOException;
//import java.io.OutputStream;
import java.io.FileInputStream;
//import java.io.DataInputStream;
//import java.io.PrintStream;
//import java.util.Scanner;

public class WebServer {
    // Short hand print statment.
    public static void log(String msg) {
        System.out.println(msg);
    }
    // Check if file or directory exists.
    public static boolean checkPath(String path) {
        File file = new File(path);
        return file.exists();
    }
    
    public static void main(String args[]) {
        // Get configuration input.
        int port = Integer.parseInt(args[1]);
        String path = args[0];
        
        if (!checkPath(path)) {
            log("Invalid Path. Exiting...");
            return;
        }
        // Set configuration values.
        Config config = new Config(path, port);  
        // Set server socket.
        try (ServerSocket server = new ServerSocket(config.getPort())) {
            log("---- listening on port " + server.getLocalPort());
        
            // Loop forever, wating for clients. 
            while (true) {
                // Wait for client.
                Socket client = server.accept();
                log("-- Client " + client.getInetAddress().toString() + " connected.");
                // Spawn a thread to handle the new connection.
                ThreadSpawn thread = new ThreadSpawn(client, config);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}