package catSerialization;

import catWrappers.CatDataWrapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import models.CatColor;

import java.io.IOException;
import java.sql.Date;

public class CatDataWrapperDeserializer extends JsonDeserializer<CatDataWrapper> {
    @Override
    public CatDataWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Long id = null;
        String name = null;
        Date birthdate = null;
        String breed = null;
        CatColor color = null;
        long ownerId = 0;
        int tailLength = 0;

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();

            if (fieldName.equals("id")) {
                id = jsonParser.getValueAsLong();
            } else if (fieldName.equals("name")) {
                name = jsonParser.getValueAsString();
            } else if (fieldName.equals("birthdate")) {
                birthdate = Date.valueOf(jsonParser.getValueAsString());
            } else if (fieldName.equals("breed")) {
                name = jsonParser.getValueAsString();
            } else if (fieldName.equals("color")) {
                color = CatColor.valueOf(jsonParser.getValueAsString());
            } else if (fieldName.equals("ownerId")) {
                ownerId = jsonParser.getValueAsLong();
            } else if (fieldName.equals("tailLength")) {
                tailLength = jsonParser.getValueAsInt();
            }
        }

        return new CatDataWrapper(id, name, birthdate, breed, color, ownerId, tailLength);
    }
}
