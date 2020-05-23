package json;

/**
 *
 * @author Petr Bednář
 */
public interface JSONDataStructure {

    /**
     *
     * Encodes JSONDataStructure into formatted JSON string.
     *
     * @param spacing Use 3 for best spacing
     * @return
     * @throws JSONException
     */
    public String toJSON(int spacing) throws JSONException;

    /**
     *
     * Do not use this.
     *
     * @param spacing
     * @param indent
     * @param inner
     * @return
     * @throws JSONException
     */
    public String toJSON(int spacing, int indent, boolean inner) throws JSONException;

    /**
     * Encodes JSONDataStructure into unformatted JSON string.
     *
     * @return
     * @throws JSONException
     */
    public String toJSONMinified() throws JSONException;
}
