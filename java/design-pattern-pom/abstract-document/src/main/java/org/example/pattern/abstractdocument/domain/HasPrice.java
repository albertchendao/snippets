package org.example.pattern.abstractdocument.domain;

import org.example.pattern.abstractdocument.Document;

import java.util.Optional;

public interface HasPrice extends Document {
    default Optional<Long> getPrice() {
        return Optional.ofNullable((Long) get(PropertyEnum.PRICE.toString()));
    }
}
