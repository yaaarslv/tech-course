package fleaSerialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fleaWrappers.FleaDataWrapper;

import java.io.IOException;

public class FleaDataWrapperSerializer extends JsonSerializer<FleaDataWrapper> {
    @Override
    public void serialize(FleaDataWrapper fleaData, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", fleaData.getId());
        jsonGenerator.writeStringField("name", fleaData.getName());
        jsonGenerator.writeStringField("catId", fleaData.getCatId().toString());
        jsonGenerator.writeEndObject();
    }
}

