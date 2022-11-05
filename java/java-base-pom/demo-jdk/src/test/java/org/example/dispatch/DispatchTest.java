package org.example.dispatch;

import org.junit.Test;

/**
 * 分派测试
 *
 * @author Albert
 * @version 1.0
 * @since 2022/7/4 7:17 PM
 */
public class DispatchTest {

    static class Person {
        public void say() {
            System.out.println("this is " + this.getClass().getSimpleName());
        }

        /**
         * 子类实际有自己的 exe.print(this), 而在子类中 this 代表了子类自己
         */
        public void accept(Execute exe) {
            exe.print(this);
        }

        /**
         * exe.print 实际还是静态分派, 强转类型后又变成固定的了
         */
        public void accept2(Execute exe) {
            exe.print((Person) this);
        }
    }

    static class Man extends Person {
        public void say() {
            System.out.println("this is " + this.getClass().getSimpleName());
        }
    }

    static class Woman extends Person {
        public void say() {
            System.out.println("this is " + this.getClass().getSimpleName());
        }
    }

    static class Execute {
        void checkPerson(Person p) {
            System.out.println("check Person");
        }

        void print(Person p) {
            System.out.println("this is Person");
        }

        void print(Man p) {
            System.out.println("this is Man");
        }

        void print(Woman p) {
            System.out.println("this is Woman");
        }
    }

    /**
     * 静态绑定: 在编译期就已经确定执行哪一个方法.
     * 方法的重载(方法名相同而参数不同)就是静态绑定的, 重载时, 执行哪一个方法在编译期就已经确定下来.
     */
    @Test
    public void testStaticBound() {
        Execute execute = new Execute();
        Person p = new Person();
        Person man = new Man();

        Person woman = new Woman();
        execute.print(p);
        execute.print(man);
        execute.print(woman);

        Man man2 = new Man();
        Woman woman2 = new Woman();
        execute.print(man2);
        execute.print(woman2);

        execute.checkPerson(man2);
    }


    /**
     * 动态绑定: 程执行期间(而不是在编译期间)判断所引用对象的实际类型, 根据其实际的类型调用其相应的方法.
     */
    @Test
    public void testDynamicBound() {
        Person person = new Man();
        person.say();
    }

    /**
     * 双重分派: 在选择一个方法的时候, 不仅仅要根据消息接收者(receiver)的运行时型别(Runtime type), 还要根据参数的运行时型别(Runtime type)
     *
     * 通俗的解释一下, 就是重载是静态绑定, 重写是动态绑定, 双分派把重写放在重载之前, 以实现在运行时动态判断执行那个子类的方法.
     */
    @Test
    public void testDoubleBound() {
        Execute execute = new Execute();
        Person p = new Person();
        Person man = new Man();
        Person woman = new Woman();
        p.accept(execute);
        man.accept(execute);
        woman.accept(execute);
    }

    @Test
    public void testDoubleBound2() {
        Execute execute = new Execute();
        Person p = new Person();
        Person man = new Man();
        Person woman = new Woman();
        p.accept2(execute);
        man.accept2(execute);
        woman.accept2(execute);
    }

}