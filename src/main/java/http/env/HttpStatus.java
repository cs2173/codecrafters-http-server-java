package http.env;

public enum HttpStatus {
    OK("OK", 200),
    NOT_FOUND("Not Found", 404);

    private final String status;
    private final int code;

    HttpStatus(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

}
