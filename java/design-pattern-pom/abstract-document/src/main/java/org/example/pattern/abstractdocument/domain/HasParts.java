package org.example.pattern.abstractdocument.domain;

import org.example.pattern.abstractdocument.Document;

import java.util.Optional;
import java.util.stream.Stream;

public interface HasParts extends Document {
    default Stream<Part> getParts() {
        return children(PropertyEnum.PARTS.toString(), Part::new);
    }
}
