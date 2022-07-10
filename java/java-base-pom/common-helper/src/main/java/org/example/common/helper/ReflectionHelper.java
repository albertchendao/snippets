package org.example.common.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionHelper {

    /**
     * 分隔符 生成key 格式为 类全类名|方法名|参数所属类全类名
     **/
    private static final String DELIMITER = "|";

    /**
     * * 根据类名、方法名和参数生成Key
     * * @param clazzName
     * * @param methodName
     * * @param args
     * * @return key格式：全类名|方法名｜参数类型
     */
    public static String getKey(String methodName, Object[] args, int keyIndex) {
        StringBuilder key = new StringBuilder();
        key.append(methodName);
        key.append(DELIMITER);
        if (keyIndex < 0) {
            for (Object obj : args) {
                key.append(obj);
                key.append(DELIMITER);
            }
        } else {
            int num = 0;
            for (Object obj : args) {
                if (num > keyIndex) {
                    break;
                }
                key.append(obj);
                key.append(DELIMITER);
                num++;
            }
        }
        return key.toString();
    }
}
