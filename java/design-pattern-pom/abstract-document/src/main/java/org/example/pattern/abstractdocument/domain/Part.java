package org.example.pattern.abstractdocument.domain;

import org.example.pattern.abstractdocument.AbstractDocument;

import java.util.Map;

public class Part extends AbstractDocument implements HasModel, HasPrice, HasType {
    public Part(Map<String, Object> props) {
        super(props);
    }
}
