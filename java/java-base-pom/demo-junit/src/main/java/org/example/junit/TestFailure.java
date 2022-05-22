package org.example.junit;

public class TestFailure {
    private Test test;
    protected Throwable e;

    public TestFailure(Test test, Throwable e) {
        this.test = test;
        this.e = e;
    }

    /**
     * Gets the failed test.
     */
    public Test failedTest() {
        return test;
    }

    public String trace() {
        return Throwables.getStacktrace(thrownException());
    }

    public Throwable thrownException() {
        return e;
    }
}
