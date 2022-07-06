package org.example.pattern.abstractdocument.domain;

import org.example.pattern.abstractdocument.AbstractDocument;

import java.util.Map;

public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {
    public Car(Map<String, Object> props) {
        super(props);
    }
}
