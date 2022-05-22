package org.example.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestCase implements Test {

    private String name;

    public TestCase() {
    }

    public TestCase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int countTestCases() {
        return 1;
    }

    public void run(TestResult result) {
        result.run(this);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    protected void runTest() throws Throwable {
        Assert.assertNotNull("TestCase.fName cannot be null", name); // Some VMs crash when calling getMethod(null,null);
        Method runMethod = null;
        try {
            // use getMethod to get all public inherited
            // methods. getDeclaredMethods returns all
            // methods of this class but excludes the
            // inherited ones.
            runMethod = getClass().getMethod(name, (Class[]) null);
        } catch (NoSuchMethodException e) {
            fail("Method \"" + name + "\" not found");
        }
        if (!Modifier.isPublic(runMethod.getModifiers())) {
            fail("Method \"" + name + "\" should be public");
        }

        try {
            runMethod.invoke(this);
        } catch (InvocationTargetException e) {
            e.fillInStackTrace();
            throw e.getTargetException();
        } catch (IllegalAccessException e) {
            e.fillInStackTrace();
            throw e;
        }
    }

    public void runBare() throws Throwable {
        Throwable exception = null;
        setUp();
        try {
            runTest();
        } catch (Throwable running) {
            exception = running;
        } finally {
            try {
                tearDown();
            } catch (Throwable tearingDown) {
                if (exception == null) exception = tearingDown;
            }
        }
        if (exception != null) throw exception;
    }

    public static void fail(String message) {
        Assert.fail(message);
    }
}
