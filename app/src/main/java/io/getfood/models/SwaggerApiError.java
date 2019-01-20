package io.getfood.models;

import com.google.gson.Gson;

public class SwaggerApiError {

    /**
     * Check if there are is a error
     */
    private boolean error;

    /**
     * Error message
     */
    private String message;

    /**
     * Swaggers api error
     * @param error check if there is a error
     * @param message error message
     */
    public SwaggerApiError(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    /**
     * Check if there is an error
     * @return error
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets error check
     * @param error check if there is a error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Get error message
     * @return error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets error message
     * @param message error message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creates a string of the given error
     * @return error string
     */
    @Override
    public String toString() {
        return "SwaggerApiError{" +
                "error=" + error +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * Parses json error string
     * @param json error string
     * @return parsed swagger error
     */
    public static SwaggerApiError parse(String json) {
        return new Gson().fromJson(json, SwaggerApiError.class);
    }
}
