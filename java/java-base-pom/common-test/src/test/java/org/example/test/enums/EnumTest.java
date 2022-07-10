package org.example.test.enums;

import java.util.EnumSet;

/**
 * 支持 id 自增的枚举
 */
public class EnumTest {
    public static void main(String[] args) {
        for (MSG_TYPE msg_type : EnumSet.allOf(MSG_TYPE.class))
        {
            System.out.println(msg_type.name() + " " +
                    msg_type.ordinal() + " " +
                    msg_type.getValue());
        }
    }
}

enum MSG_TYPE {
    MSG_LOGIN(500),
    MSG_LOGOUT,
    MSG_REGISTER,
    MSG_SEARCH,
    MSG_ADD(600);

    private int value;
    private static int nextValue;
    MSG_TYPE(){
        this(Counter.nextValue);
    }
    MSG_TYPE(int value){
        this.value = value;
        Counter.nextValue = value + 1;
    }

    public int getValue()
    {
        return value;
    }

    private static class Counter
    {
        private static int nextValue = 0;
    }
}



