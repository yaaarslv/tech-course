package catSerialization;

import catWrappers.CatDataWrapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CatDataWrapperSerializer extends JsonSerializer<CatDataWrapper> {
    @Override
    public void serialize(CatDataWrapper catData, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", catData.getId());
        jsonGenerator.writeStringField("name", catData.getName());
        jsonGenerator.writeStringField("birthdate", catData.getBirthdate().toString());
        jsonGenerator.writeStringField("breed", catData.getBirthdate().toString());
        jsonGenerator.writeStringField("color", catData.getColor().name());
        jsonGenerator.writeStringField("ownerId", catData.getOwnerId().toString());
        jsonGenerator.writeStringField("tailLength", String.valueOf(catData.getTailLength()));
        jsonGenerator.writeEndObject();
    }
}

