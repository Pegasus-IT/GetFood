package io.getfood.models;

import com.google.gson.Gson;

public class SwaggerApiError {
    private boolean error;
    private String message;

    public SwaggerApiError(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SwaggerApiError{" +
                "error=" + error +
                ", message='" + message + '\'' +
                '}';
    }

    public static SwaggerApiError parse(String json) {
        return new Gson().fromJson(json, SwaggerApiError.class);
    }
}
