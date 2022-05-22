package org.example;

public class Main {

    static class MyObj {
        private String k;
        public MyObj(String key) {
            this.k = key;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof MyObj) {
                MyObj key = (MyObj) obj;
                return k.equals(key.k);
            }
            return false;
        }
    }
}
