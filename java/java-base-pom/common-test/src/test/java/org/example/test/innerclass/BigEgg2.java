package org.example.test.innerclass;

/**
 * 外部类继承时，同时继承内部类(不使用此方法内部类不能覆盖)
 */
public class BigEgg2 extends Egg2 {
    public class Yolk extends Egg2.Yolk {
        public Yolk() {
            System.out.println("BigEgg2.Yolk()");
        }

        public void f() {
            System.out.println("BigEgg2.Yolk.f()");
        }
    }

    public BigEgg2() {
        insertYolk(new Yolk());
    }

    public static void main(String[] args) {
        // 输出
        //Egg2.Yolk()
        //New Egg2()
        //Egg2.Yolk()
        //BigEgg2.Yolk()
        //BigEgg2.Yolk.f()
        Egg2 e2 = new BigEgg2();
        e2.g();
    }
}

class Egg2 {
    protected class Yolk {
        public Yolk() {
            System.out.println("Egg2.Yolk()");
        }

        public void f() {
            System.out.println("Egg2.Yolk.f()");
        }
    }

    private Yolk y = new Yolk();

    public Egg2() {
        System.out.println("New Egg2()");
    }

    public void insertYolk(Yolk yy) {
        y = yy;
    }

    public void g() {
        y.f();
    }
}
