package org.example.junit;

public interface TestRunListener {
    int STATUS_ERROR = 1;
    int STATUS_FAILURE = 2;

    void testRunStarted(String testSuiteName, int testCount);

    void testRunEnded(long elapsedTime);

    void testRunStopped(long elapsedTime);

    void testStarted(String testName);

    void testEnded(String testName);

    void testFailed(int status, String testName, String trace);
}
