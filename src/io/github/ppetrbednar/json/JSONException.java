package io.github.ppetrbednar.json;

/**
 *
 * @author Petr Bednář
 */
@SuppressWarnings("serial")
public class JSONException extends Exception {

    public JSONException() {
    }

    public JSONException(String message) {
        super(message);
    }

    public JSONException(Throwable cause) {
        super(cause);
    }

    public JSONException(String message, Throwable cause) {
        super(message, cause);
    }

}
