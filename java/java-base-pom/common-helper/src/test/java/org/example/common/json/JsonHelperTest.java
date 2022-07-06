package org.example.common.json;

import lombok.Data;
import org.junit.Test;

public class JsonHelperTest {

    @Data
    static class TestDto {
        private Long id;
    }

    @Test
    public void testToJSONString() {
        TestDto dto = new TestDto();
        dto.setId(1L);
        print(dto);
        dto.setId(2L);
        print(dto);
    }

    void print(Object o) {
        System.out.println(JsonHelper.toJSONString(o));
    }
}
