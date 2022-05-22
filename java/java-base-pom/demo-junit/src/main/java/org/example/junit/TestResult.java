package org.example.junit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class TestResult {
    protected List<TestFailure> failures;
    protected List<TestFailure> errors;
    protected List<TestListener> listeners;
    protected int alreadyRunTest;
    private boolean fStop;

    public TestResult() {
        failures = new ArrayList<TestFailure>();
        errors = new ArrayList<TestFailure>();
        listeners = new ArrayList<TestListener>();
        alreadyRunTest = 0;
        fStop = false;
    }

    public Enumeration<TestFailure> failures() {
        return Collections.enumeration(failures);
    }

    public Enumeration<TestFailure> errors() {
        return Collections.enumeration(errors);
    }

    public synchronized int failureCount() {
        return failures.size();
    }

    public synchronized int errorCount() {
        return errors.size();
    }

    public synchronized boolean wasSuccessful() {
        return failureCount() == 0 && errorCount() == 0;
    }

    protected void run(final TestCase test) {
        startTest(test);
        Protectable p = new Protectable() {
            public void protect() throws Throwable {
                test.runBare();
            }
        };
        runProtected(test, p);

        endTest(test);
    }

    public synchronized void addListener(TestListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(TestListener listener) {
        listeners.remove(listener);
    }

    interface Protectable {
        void protect() throws Throwable;
    }

    public void runProtected(final Test test, Protectable p) {
        try {
            p.protect();
        } catch (AssertionFailedError e) {
            addFailure(test, e);
        } catch (ThreadDeath e) { // don't catch ThreadDeath by accident
            throw e;
        } catch (Throwable e) {
            addError(test, e);
        }
    }

    public synchronized void addFailure(Test test, AssertionFailedError e) {
        failures.add(new TestFailure(test, e));
        for (TestListener each : cloneListeners()) {
            each.addFailure(test, e);
        }
    }

    public synchronized void addError(Test test, Throwable e) {
        errors.add(new TestFailure(test, e));
        for (TestListener each : cloneListeners()) {
            each.addError(test, e);
        }
    }

    public void startTest(Test test) {
        final int count = test.countTestCases();
        synchronized (this) {
            alreadyRunTest += count;
        }
        for (TestListener each : cloneListeners()) {
            each.startTest(test);
        }
    }

    public void endTest(Test test) {
        for (TestListener each : cloneListeners()) {
            each.endTest(test);
        }
    }

    private synchronized List<TestListener> cloneListeners() {
        List<TestListener> result = new ArrayList<TestListener>();
        result.addAll(listeners);
        return result;
    }

    public synchronized int runCount() {
        return alreadyRunTest;
    }

    public synchronized boolean shouldStop() {
        return fStop;
    }
}
