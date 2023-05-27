package ownerSerialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ownerWrappers.OwnerDataWrapper;

import java.io.IOException;

public class OwnerDataWrapperSerializer extends JsonSerializer<OwnerDataWrapper> {
    @Override
    public void serialize(OwnerDataWrapper ownerData, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", ownerData.getId());
        jsonGenerator.writeStringField("name", ownerData.getName());
        jsonGenerator.writeStringField("birthdate", ownerData.getBirthdate().toString());
        jsonGenerator.writeEndObject();
    }
}

