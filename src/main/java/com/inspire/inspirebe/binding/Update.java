package com.inspire.inspirebe.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.function.Consumer;

@Getter
@ToString
public class Update<T> {
    private T value;
    private boolean present;

    public Update(T value, boolean present) {
        this.value = value;
        this.present = present;
    }

    public static <T> Update<T> present(T value) {
        return new Update<>(value, true);
    }

    public static <T> Update<T> absent() {
        return new Update<>(null, false);
    }

    public void ifPresent(Consumer<T> consumer) {
        if(present) {
            consumer.accept(value);
        }
    }
}
