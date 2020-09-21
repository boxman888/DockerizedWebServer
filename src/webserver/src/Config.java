// Setter and getter class for WebServer configuration.

public class Config {
    private static final String ROOT = System.getProperty("user.dir");
    private String path = ".";
    private int port = 8080;

    public Config(String path, int port){
        this.path = path;
        this.port = port;
    }

    String getRoot() {
        return ROOT;
    }
    
    String getPath() {
        return path;
    }

    int getPort() {
        return port;
    }
}