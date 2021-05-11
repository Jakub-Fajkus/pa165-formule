package cz.muni.pa165.teamwhite.formula1.rest;

public class RestResponse<T> {
    private String status;
    private T data;

    public RestResponse(T data, String status) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
