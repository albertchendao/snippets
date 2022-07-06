package org.example.pattern.abstractdocument.domain;

import org.example.pattern.abstractdocument.Document;

import java.util.Optional;

public interface HasType extends Document {
    default Optional<String> getType() {
        return Optional.ofNullable((String) get(PropertyEnum.TYPE.toString()));
    }
}
