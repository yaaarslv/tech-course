package fleaSerialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import fleaWrappers.FleaDataWrapper;

import java.io.IOException;

public class FleaDataWrapperDeserializer extends JsonDeserializer<FleaDataWrapper> {
    @Override
    public FleaDataWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
        Long id = null;
        String name = null;
        Long catId = null;

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();

            if (fieldName.equals("id")) {
                id = jsonParser.getValueAsLong();
            } else if (fieldName.equals("name")) {
                name = jsonParser.getValueAsString();
            } else if (fieldName.equals("catId")) {
                catId = jsonParser.getValueAsLong();
            }
        }

        return new FleaDataWrapper(id, name, catId);
    }
}
