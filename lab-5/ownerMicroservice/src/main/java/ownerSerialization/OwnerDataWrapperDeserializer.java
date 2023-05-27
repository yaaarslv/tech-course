package ownerSerialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import ownerWrappers.OwnerDataWrapper;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.sql.Date;

public class OwnerDataWrapperDeserializer extends JsonDeserializer<OwnerDataWrapper> {
    @Override
    public OwnerDataWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Long id = null;
        String name = null;
        Date birthdate = null;

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();

            if (fieldName.equals("id")) {
                id = jsonParser.getValueAsLong();
            } else if (fieldName.equals("name")) {
                name = jsonParser.getValueAsString();
            } else if (fieldName.equals("birthdate")) {
                String dateString = jsonParser.getValueAsString();
                birthdate = Date.valueOf(dateString);
            }
        }

        return new OwnerDataWrapper(id, name, birthdate);
    }
}
