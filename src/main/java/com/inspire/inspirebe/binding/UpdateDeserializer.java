package com.inspire.inspirebe.binding;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;

@Slf4j
public class UpdateDeserializer<T> extends StdDeserializer<Update<T>> {

    private final JavaType innerType;

    // raw type : used only for adding modules
    public UpdateDeserializer() {
        super(Update.class);
        this.innerType = null;
    }

    // contextual copy
    private UpdateDeserializer(UpdateDeserializer<?> src, JavaType innerType) {
        super(src);
        this.innerType = innerType;
    }

    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        log.debug("created contextual");
        log.debug("property: {}", property);

        if(property == null) {
            return this;
        }

        JavaType wrapper = property.getType(); // Update<T>
        log.debug("wrapper: {}", wrapper);
        JavaType actualType = wrapper.containedType(0); // T
        log.debug("actualType: {}", actualType);

        return new UpdateDeserializer<>(this, actualType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Update<T> deserialize(JsonParser p,DeserializationContext ctxt) throws JacksonException {

        // empty body
        if(p.currentToken() == null) {
            return Update.absent();
        }

        // intentional NULL
        if(p.currentToken() == JsonToken.VALUE_NULL) {
            return Update.present(null);
        }

        // check null exception
        if(innerType == null) {
            return Update.absent();
        }

        ValueDeserializer<T> delegate = (ValueDeserializer<T>) ctxt.findRootValueDeserializer(innerType);

        T value = delegate.deserialize(p, ctxt);

        return Update.present(value);
    }
}
