package io.github.ppetrbednar.json;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * JSONObject implemented as HashMap intended to store JSONDataStructure objects and other Object types.
 *
 * Normal arrays not supported (Use JSONArray).
 *
 * @author Petr Bednář
 */
public class JSONObject extends HashMap<String, Object> implements JSONDataStructure {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    static {
        DECIMAL_FORMAT.setMaximumFractionDigits(340);
    }

    @Override
    public String getJSONString(int spacing) throws JSONException {
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
                        sb.append(DECIMAL_FORMAT.format(data)).append(",\n");
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
                        sb.append(((JSONDataStructure) data).getJSONString(spacing, 1, true)).append(",\n");
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
    public String getJSONString(int spacing, int indent, boolean inner) throws JSONException {
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
                        sb.append(DECIMAL_FORMAT.format(data)).append(",\n");
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
                        sb.append(((JSONDataStructure) data).getJSONString(spacing, (indent + 1), true)).append(",\n");
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
    public String getJSONStringMinified() throws JSONException {
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
                        sb.append(DECIMAL_FORMAT.format(o)).append(",\n");
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
                        sb.append(((JSONDataStructure) o).getJSONStringMinified()).append(",");
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

                    String text = o.toString();
                    text = text.replaceAll("\\", "\\\\");

                    sb.append("\"").append(text).append("\",");

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
