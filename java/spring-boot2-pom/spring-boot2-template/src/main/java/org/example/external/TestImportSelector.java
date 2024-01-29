package org.example.external;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 测试 ImportSelector
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 3:16 PM
 */
public class TestImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"org.example.external.ExternalSayService"};
    }
}
