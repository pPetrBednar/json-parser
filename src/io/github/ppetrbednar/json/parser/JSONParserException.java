package io.github.ppetrbednar.json.parser;

/**
 *
 * @author Petr Bednář
 */
@SuppressWarnings("serial")
public class JSONParserException extends Exception {

    public JSONParserException() {
    }

    public JSONParserException(String message) {
        super(message);
    }

    public JSONParserException(Throwable cause) {
        super(cause);
    }

    public JSONParserException(String message, Throwable cause) {
        super(message, cause);
    }

}
