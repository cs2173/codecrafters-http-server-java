import http.env.Environment;
import http.server.HttpServer;

public class Main {

    public static void main(String[] args) {
        Environment.getInstance().parseArgs(args);
        HttpServer server = new HttpServer();
        server.start();
    }

}