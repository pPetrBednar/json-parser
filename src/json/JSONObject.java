package json;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Petr Bednář
 */
public class JSONObject extends HashMap<String, Object> implements JSONDataStructure {

    @Override
    public String toJSON(int spacing) throws JSONException {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n");
        try {

            SortedSet<String> keys = new TreeSet<>(keySet());
            keys.forEach((String key) -> {

                Object data = get(key);
                try {

                    sb.append(String.format("%" + spacing + "s", "")).append("\"").append(key).append("\"").append(": ");

                    if (data == null) {
                        sb.append("null,\n");
                        return;
                    }

                    if (data instanceof Number) {
                        sb.append(data.toString()).append(",\n");
                        return;
                    }

                    if (data instanceof Boolean) {
                        sb.append(data.toString()).append(",\n");
                        return;
                    }

                    if (data.getClass().isEnum()) {
                        sb.append(((Enum) data).name()).append(",\n");
                        return;
                    }

                    if (data instanceof JSONDataStructure) {
                        sb.append(((JSONDataStructure) data).toJSON(spacing, 1, true)).append(",\n");
                        return;
                    }

                    /*
                    if (data.getClass().isArray()) {
                        for (Object o2 : (Object[]) data) {
                            sb.append("\"").append(o2.toString()).append("\",\n");
                            return;
                        }
                    }
                     */
                    sb.append("\"").append(data.toString()).append("\",\n");

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

        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toJSON(int spacing, int indent, boolean inner) throws JSONException {
        StringBuilder sb = new StringBuilder();

        sb.append(inner ? "" : String.format("%" + (indent * spacing) + "s", "")).append("{\n");
        try {
            SortedSet<String> keys = new TreeSet<>(keySet());
            keys.forEach((String key) -> {

                Object data = get(key);
                try {

                    sb.append(String.format("%" + ((indent + 1) * spacing) + "s", "")).append("\"").append(key).append("\"").append(": ");

                    if (data == null) {
                        sb.append("null,\n");
                        return;
                    }

                    if (data instanceof Number) {
                        sb.append(data.toString()).append(",\n");
                        return;
                    }

                    if (data instanceof Boolean) {
                        sb.append(data.toString()).append(",\n");
                        return;
                    }

                    if (data.getClass().isEnum()) {
                        sb.append(((Enum) data).name()).append(",\n");
                        return;
                    }

                    if (data instanceof JSONDataStructure) {
                        sb.append(((JSONDataStructure) data).toJSON(spacing, (indent + 1), true)).append(",\n");
                        return;
                    }
                    /*
                      if (data.getClass().isArray()) {
                        for (Object o2 : (Object[]) data) {
                            sb.append("\"").append(o2.toString()).append("\",\n");
                            return;
                        }
                    }
                     */
                    sb.append("\"").append(data.toString()).append("\",\n");

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

        sb.append(String.format("%" + (indent * spacing) + "s", "")).append("}");
        return sb.toString();
    }

    @Override
    public String toJSONMinified() throws JSONException {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        try {
            forEach((String s, Object o) -> {
                try {

                    sb.append("\"").append(s).append("\"").append(":");

                    if (o == null) {
                        sb.append("null,");
                        return;
                    }

                    if (o instanceof Number) {
                        sb.append(o.toString()).append(",");
                        return;
                    }

                    if (o instanceof Boolean) {
                        sb.append(o.toString()).append(",");
                        return;
                    }

                    if (o.getClass().isEnum()) {
                        sb.append(((Enum) o).name()).append(",");
                        return;
                    }

                    if (o instanceof JSONDataStructure) {
                        sb.append(((JSONDataStructure) o).toJSONMinified()).append(",");
                        return;
                    }
                    /*
                    if (o.getClass().isArray()) {
                        for (Object o2 : (Object[]) o) {
                            sb.append("\"").append(o2.toString()).append("\",");
                            return;
                        }
                    }
                     */

                    sb.append("\"").append(o.toString()).append("\",");

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

        sb.append("}");
        return sb.toString();
    }
}
