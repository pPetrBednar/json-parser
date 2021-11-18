package json;

/**
 * Specifies class as convertable to JSONObject.
 *
 * @author petrb
 */
public interface IJSONConvertable {

    /**
     * Converts object to JSON Object.
     *
     * @return Object data as JSONObject or null
     */
    public JSONObject getJSONObject();
}
