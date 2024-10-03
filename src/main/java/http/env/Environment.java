package http.env;

public final class Environment {

    private static final class LazyHolder {
        private static final Environment INSTANCE = new Environment();
    }

    private static final int port = 4221;

    private static final String version = "HTTP/1.1";

    private String directory;

    private Environment() {
    }

    public static Environment getInstance() {
        return LazyHolder.INSTANCE;
    }

    public int getPort() {
        return port;
    }

    public String getVersion() {
        return version;
    }

    public String getDirectory() {
        return directory;
    }

    public void parseArgs(String[] args) {
        if (args.length == 0) {
            return;
        }

        for (int i = 0; i < args.length; i += 2) {
            if (!args[i].isBlank() && args[i].equals("--directory")) {
                this.directory = args[i + 1];
                break;
            }
        }
    }

}
