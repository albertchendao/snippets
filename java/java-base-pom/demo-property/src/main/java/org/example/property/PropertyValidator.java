package org.example.property;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 参数校验器
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:27 AM
 */
public interface PropertyValidator {

    /**
     * {@link PropertyValidator#validate(String)}的结果固定为{@code true}
     *
     * @return 结果固定为{@code true}的PropertyValidator
     */
    static PropertyValidator alwaysTrue() {
        return AlwaysTruePropertyValidator.INSTANCE;
    }


    /**
     * 整数校验器
     *
     * @param lowBound  下界,包含
     * @param highBound 上界, 包含
     * @return 整数校验器
     */
    static PropertyValidator number(long lowBound, long highBound) {
        return new NumberPropertyValidator(lowBound, highBound);
    }

    /**
     * Map校验器, 需要能反序列化为{@code Map<String, String>}
     *
     * @return Map校验器
     */
    static PropertyValidator map() {
        return MapPropertyValidator.INSTANCE;
    }

    /**
     * 枚举校验器, 只能为设定的枚举值
     *
     * @param value 枚举值
     * @return 枚举校验器
     */
    static PropertyValidator enums(String... value) {
        return new EnumsPropertyValidator(value);
    }

    /**
     * 2的幂次校验器, 值必须为整数,且为2的幂次
     *
     * @return 2的幂次校验器
     */
    static PropertyValidator power2() {
        return Power2PropertyValidator.INSTANCE;
    }


    /**
     * 验证property是否正确
     *
     * @param property property
     * @return true: 正确, false: 不正确
     */
    boolean validate(String property);


    class AlwaysTruePropertyValidator implements PropertyValidator {

        static final AlwaysTruePropertyValidator INSTANCE = new AlwaysTruePropertyValidator();

        @Override
        public boolean validate(String property) {
            return true;
        }

        @Override
        public String toString() {
            return "AlwaysTruePropertyValidator{}";
        }
    }


    class NumberPropertyValidator implements PropertyValidator {

        private final long lowBound;
        private final long highBound;

        NumberPropertyValidator(long lowBound, long highBound) {
            this.lowBound = lowBound;
            this.highBound = highBound;
        }

        @Override
        public boolean validate(String property) {
            if (StringUtils.isBlank(property)) {
                return false;
            }
            try {
                long longValue = Long.parseLong(property);
                if (longValue < lowBound) {
                    return false;
                }
                if (longValue > highBound) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "NumberPropertyValidator{" +
                    "lowBound=" + lowBound +
                    ", highBound=" + highBound +
                    '}';
        }
    }


    class MapPropertyValidator implements PropertyValidator {

        static final MapPropertyValidator INSTANCE = new MapPropertyValidator();

        private static final TypeReference<Map<String, String>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, String>>() {
        };

        @Override
        public boolean validate(String property) {
            try {
                JSON.parseObject(property, MAP_TYPE_REFERENCE);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "MapPropertyValidator{}";
        }
    }

    class EnumsPropertyValidator implements PropertyValidator {

        private final Set<String> values;

        EnumsPropertyValidator(String... values) {
            this.values = new HashSet<>(Arrays.asList(values));
        }

        @Override
        public boolean validate(String property) {
            return values.contains(property);
        }

        @Override
        public String toString() {
            return "EnumsPropertyValidator{" +
                    "values=" + values +
                    '}';
        }
    }

    class Power2PropertyValidator implements PropertyValidator {

        static final Power2PropertyValidator INSTANCE = new Power2PropertyValidator();

        @Override
        public boolean validate(String property) {
            try {
                int size = Integer.parseInt(property);
                return size > 1 && Integer.bitCount(size) == 1;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public String toString() {
            return "Power2PropertyValidator{}";
        }
    }

}