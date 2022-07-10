package org.example.test.innerclass;

//匿名内部类 只有默认构造方法，但可以使用默认值或实例代码块初始化
// 若试图定义一个匿名内部类，并想使用在匿名内部类外部定义的一个对象，则编译器要求外部对象为final
// 属性。这正是我们将dest()的自变量设为final 的原因。如果忘记这样做，就会得到一条编译期出错提示。
public class Parcel7 {
    public Wrapping wrap(int x) {
// Base constructor call:
        return new Wrapping(x) {
            public int value() {
                return super.value() * 47;
            }
        }; // Semicolon required
    }
    public static void main(String[] args) {
        Parcel7 p = new Parcel7();
        Wrapping w = p.wrap(10);
    }
}

class Wrapping {
    private int i;
    public Wrapping(int x) { i = x; }
    public int value() { return i; }
}


