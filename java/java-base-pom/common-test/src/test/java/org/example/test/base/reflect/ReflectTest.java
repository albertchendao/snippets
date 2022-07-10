package org.example.test.base.reflect;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Java 反射测试
 */
@Slf4j
public class ReflectTest {

    private final static int CAPACITY = 2;
    private final static int CAPACITY_MATH = Math.abs(-2);

    /**
     * 注意: 这个字段在编译的时候就会优化
     */
    public static int getCapacity() {
        return CAPACITY;
    }

    /**
     * 注意: 这个字段不会在编译的时候优化
     */
    public static int getCapacityMath() {
        return CAPACITY_MATH;
    }

    /**
     * 反射修改 final 属性
     */
    @Test
    public void testFinalReflect() throws Exception {
        Field field = Field.class.getDeclaredField("modifiers");
        field.setAccessible(true);

        Field capacityField = ReflectTest.class.getDeclaredField("CAPACITY");
        // 去掉 private 限制
        capacityField.setAccessible(true);
        field.setInt(capacityField, capacityField.getModifiers() & ~Modifier.FINAL);
        capacityField.set(null, 3);
        log.debug("第一次执行: 反射值与直接值不一致");
        log.debug("直接值: {}", ReflectTest.getCapacity());
        log.debug("反射值: {}", capacityField.get(null));

        // 强制进行下 JIT 优化
        for (int i = 0; i < 100000; i++) {
            ReflectTest.getCapacity();
        }
        capacityField.set(null, 4);
        log.debug("第二次执行: 反射值与直接值不一致");
        log.debug("直接值: {}", ReflectTest.getCapacity());
        log.debug("反射值: {}", capacityField.get(null));
    }

    /**
     * 反射修改 final 属性
     */
    @Test
    public void testFinalReflectJit() throws Exception {
        Field field = Field.class.getDeclaredField("modifiers");
        field.setAccessible(true);

        Field capacityField = ReflectTest.class.getDeclaredField("CAPACITY_MATH");
        // 去掉 private 限制
        capacityField.setAccessible(true);
        field.setInt(capacityField, capacityField.getModifiers() & ~Modifier.FINAL);
        capacityField.set(null, 3);
        log.debug("第一次执行: 反射值与直接值一致");
        log.debug("直接值: {}", ReflectTest.getCapacityMath());
        log.debug("反射值: {}", capacityField.get(null));

        // 强制进行下 JIT 优化
        for (int i = 0; i < 100000; i++) {
            ReflectTest.getCapacityMath();
        }
        capacityField.set(null, 4);
        log.debug("第二次执行: 反射值与直接值不一致");
        log.debug("直接值: {}", ReflectTest.getCapacityMath());
        log.debug("反射值: {}", capacityField.get(null));
    }
}
