package json;

import java.util.LinkedList;

/**
 * JSONArray implemented as LinkedList for converting stored JSONDataStructure objects to JSON strings.
 *
 * @author Petr Bednář
 */
public class JSONArray extends LinkedList<JSONDataStructure> implements JSONDataStructure {

    @Override
    public String getJSONString(int spacing) throws JSONException {
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        try {
            forEach((o) -> {

                try {

                    sb.append(o.getJSONString(spacing, 1, false));
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
    public String getJSONString(int spacing, int indent, boolean inner) throws JSONException {

        StringBuilder sb = new StringBuilder();

        sb.append(inner ? "" : String.format("%" + (indent * spacing) + "s", "")).append("[\n");
        try {
            forEach((o) -> {

                try {

                    sb.append(o.getJSONString(spacing, (indent + 1), false));
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
    public String getJSONStringMinified() throws JSONException {

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        try {
            forEach((o) -> {

                try {

                    sb.append(o.getJSONStringMinified());
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
