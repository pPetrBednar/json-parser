package json.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import json.JSONArray;
import json.JSONDataStructure;
import json.JSONException;
import json.JSONObject;

/**
 *
 * @author Petr Bednář
 */
public class JSONParser {

    private static final Pattern VARIABLE_NAME = Pattern.compile("\"(.+?)\"", Pattern.DOTALL);
    private static final Pattern IS_INTEGER = Pattern.compile("[0-9]+");
    private static final Pattern IS_DOUBLE = Pattern.compile("-?\\d+(\\.\\d+)?");

    private RecursiveParser parser;
    private JSONDataStructure result;

    public JSONDataStructure parse(BufferedReader br) throws JSONParserException, IOException, ParseException {

        String line;
        parser = new RecursiveParser();

        while ((line = br.readLine()) != null) {
            line = line.trim();
            result = parser.parse(line);

            if (result != null) {
                return result;
            }
        }

        if (result == null) {
            throw new JSONParserException();
        }

        return result;
    }

    public JSONDataStructure parse(String json) throws JSONParserException, ParseException {

        String[] lines = json.split("\n");
        parser = new RecursiveParser();

        for (String line : lines) {
            line = line.trim();
            result = parser.parse(line);
        }

        if (result == null) {
            throw new JSONParserException();
        }

        return result;
    }

    private enum JSONDataType {

        ARRAY,
        OBJECT,
        UNDEFINED;

        private JSONDataType() {
        }
    }

    private class JSONParserCarry implements JSONDataStructure {

        private JSONDataStructure data;

        public JSONParserCarry() {
        }

        public JSONParserCarry(JSONDataStructure data) {
            this.data = data;
        }

        public JSONDataStructure getData() {
            return data;
        }

        @Override
        public String toJSON(int spacing) throws JSONException {
            return null;
        }

        @Override
        public String toJSON(int spacing, int indent, boolean inner) throws JSONException {
            return null;
        }

        @Override
        public String toJSONMinified() throws JSONException {
            return null;
        }

    }

    private class RecursiveParser {

        private RecursiveParser parser;

        private JSONDataType type = JSONDataType.UNDEFINED;
        private JSONObject tempObject = new JSONObject();
        private JSONArray tempArray = new JSONArray();

        private String tempKey;

        private boolean returnValue = false;
        private boolean passThrough = false;

        JSONDataStructure parse(String line) throws JSONParserException, ParseException {

            if (passThrough) {

                JSONDataStructure temp = parser.parse(line);

                if (temp == null) {
                    return null;
                }

                tempObject.put(tempKey, temp);
                passThrough = false;
                return null;
            }

            if (type == JSONDataType.ARRAY) {

                JSONDataStructure temp = parser.parse(line);

                if (temp == null) {
                    return null;
                }

                if (temp.getClass() == JSONParserCarry.class) {
                    return tempArray;
                }

                tempArray.add(temp);
                return null;
            }

            if (type == JSONDataType.OBJECT) {

                JSONParserCarry temp = parseObject(line);

                if (temp == null) {
                    return null;
                }

                if (returnValue) {
                    return temp;
                }

                return temp.getData();
            }

            if (line.length() == 0) {
                return new JSONParserCarry();
            }

            char t = line.charAt(0);
            switch (t) {
                case '[':
                    type = JSONDataType.ARRAY;
                    parser = new RecursiveParser();
                    break;
                case '{':
                    type = JSONDataType.OBJECT;
                    break;
                default:
                    throw new JSONParserException();
            }
            return null;
        }

        JSONParserCarry parseObject(String line) throws JSONParserException, ParseException {

            if (line.equals("{")) {
                tempObject = new JSONObject();
                return null;
            }

            if (line.equals("}") || line.equals("},")) {
                return new JSONParserCarry(tempObject);
            }

            if (line.equals("]") || line.equals("],")) {
                returnValue = true;
                return new JSONParserCarry(tempObject);
            }

            Matcher m = VARIABLE_NAME.matcher(line);

            if (m.find()) {
                tempKey = m.group(1);
            } else {
                throw new JSONParserException("Variable name not found");
            }

            if (m.find()) {
                tempObject.put(tempKey, m.group(1));
            } else {

                /**
                 *
                 * VALUE PARSING
                 *
                 */
                String part = line.split(":")[1].trim();

                if (part.equals("{") || part.equals("[")) {
                    passThrough = true;
                    parser = new RecursiveParser();
                    parser.parse(part);
                    return null;
                }

                String noComma = part.replace(",", "");

                if (noComma.equals("null")) {
                    tempObject.put(tempKey, null);
                    return null;
                }

                if (noComma.equals("true") || noComma.equals("false")) {
                    tempObject.put(tempKey, Boolean.parseBoolean(noComma));
                    return null;
                }

                if (IS_INTEGER.matcher(noComma).matches()) {
                    tempObject.put(tempKey, Integer.parseInt(noComma));
                    return null;
                }

                if (IS_DOUBLE.matcher(noComma).matches()) {
                    tempObject.put(tempKey, Double.parseDouble(noComma));
                    return null;
                }

                tempObject.put(tempKey, part);
            }

            return null;
        }
    }
}
