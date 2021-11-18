package json;

import java.util.ArrayList;

/**
 *
 * @author Petr Bednář <https://www.facebook.com/petrexis>
 */
public class JSONArray extends ArrayList<JSONDataStructure> implements JSONDataStructure {

    @Override
    public String toJSON(int spacing) throws JSONException {
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        try {
            forEach((o) -> {

                try {

                    sb.append(o.toJSON(spacing, 1, false));
                    sb.append(",\n");

                } catch (JSONException e) {
                    throw new RuntimeException(e.getMessage(), e.getCause());
                }
            });

            if (size() > 0) {
                sb.setLength(Math.max(sb.length() - 2, 0));
            }
            
            sb.append("\n");
        } catch (RuntimeException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toJSON(int spacing, int indent, boolean inner) throws JSONException {

        StringBuilder sb = new StringBuilder();

        sb.append(inner ? "" : String.format("%" + (indent * spacing) + "s", "")).append("[\n");
        try {
            forEach((o) -> {

                try {

                    sb.append(o.toJSON(spacing, (indent + 1), false));
                    sb.append(",\n");

                } catch (JSONException e) {
                    throw new RuntimeException(e.getMessage(), e.getCause());
                }
            });

            if (size() > 0) {
                sb.setLength(Math.max(sb.length() - 2, 0));
            }
            
            sb.append("\n");
        } catch (RuntimeException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        }

        sb.append(String.format("%" + (indent * spacing) + "s", "")).append("]");
        return sb.toString();
    }

    @Override
    public String toJSONMinified() throws JSONException {

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        try {
            forEach((o) -> {

                try {

                    sb.append(o.toJSONMinified());
                    sb.append(",");

                } catch (JSONException e) {
                    throw new RuntimeException(e.getMessage(), e.getCause());
                }
            });

            if (size() > 0) {
                sb.setLength(Math.max(sb.length() - 1, 0));
            }
        } catch (RuntimeException e) {
            throw new JSONException(e.getMessage(), e.getCause());
        }

        sb.append("]");
        return sb.toString();
    }
}
