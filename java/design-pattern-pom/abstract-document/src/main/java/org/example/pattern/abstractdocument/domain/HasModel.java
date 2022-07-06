package org.example.pattern.abstractdocument.domain;

import org.example.pattern.abstractdocument.Document;

import java.util.Optional;

public interface HasModel extends Document {
    default Optional<String> getModel() {
        return Optional.ofNullable((String) get(PropertyEnum.MODEL.toString()));
    }
}
