package org.example.junit;


public class Main extends TestCase  {

    public void testAdd1() {
        Assert.assertEquals(1 + 1, 2);
    }

    public void testAdd2() {
        Assert.assertEquals(1 + 1, 1);
    }

    public static void main(String[] args) throws Exception {
        String[] myargs = {"-c", "org.example.junit.Main"};
        TestRunner.main(myargs);
    }


}
