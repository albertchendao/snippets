package org.example.junit;

public interface TestListener {
    void addError(Test test, Throwable e);
    void addFailure(Test test, AssertionFailedError e);
    void startTest(Test test);
    void endTest(Test test);
}
