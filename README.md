# JSON-Parser
JAVA JSON encoder and decoder.

## Capabilities
Encode JSONDatastructure Objects into minified or formatted JSON String.
Decode JSON String back into JSONDatastructures and then get data from them.

## Limitations
### Encoder
Encode only works on JSONDatastructure Objects.
JSONObject can store only Objects or JSONArray.
JSONArray can store only JSONDatastructure.

### Decoder
Parser output needs to undertake type conversion into user specific type.
Parser can only handle formatted JSON String.
Parser can handle JSON String generated from other sources than this Encoder, but it has some limitations.
