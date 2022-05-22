package org.example.junit;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestRunner extends BaseTestRunner {

    private ResultPrinter fPrinter;

    public static final int SUCCESS_EXIT = 0;
    public static final int FAILURE_EXIT = 1;
    public static final int EXCEPTION_EXIT = 2;
    private static final String SUITE_METHODNAME = "suite";

    public TestRunner() {
        this(System.out);
    }

    public TestRunner(PrintStream writer) {
        this(new ResultPrinter(writer));
    }

    public TestRunner(ResultPrinter printer) {
        fPrinter = printer;
    }

    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        try {
            TestResult result = runner.start(args);
            if (result.wasSuccessful()) {
                System.exit(SUCCESS_EXIT);
            } else {
                System.exit(FAILURE_EXIT);
            }
        } catch (Exception e) {
            System.exit(EXCEPTION_EXIT);
        }
    }

    private TestResult start(String[] args) throws Exception {
        String testCase = "";
        String method = "";
        boolean wait = false;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-wait")) {
                wait = true;
            } else if (arg.equals("-c")) {
                testCase = extractClassName(args[++i]);
            } else if (arg.equals("-m")) {
                method = args[++i];
            } else if (arg.equals("-v")) {
                System.err.println("Junit 1.0");
            } else {
                testCase = arg;
            }
        }
        if ("".equals(testCase)) {
            throw new Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
        }
        try {
            Test test = null;
            if (!"".equals(method)) {
                test = getTest(testCase, method);
            } else {
                test = getSuite(testCase);
            }
            return doRun(test, wait);
        } catch (Exception e) {
            throw new Exception("Could not create and run test suite: " + e);
        }
    }

    public TestResult doRun(Test suite, boolean wait) {
        TestResult result = new TestResult();
        result.addListener(fPrinter);
        long startTime = System.currentTimeMillis();
        suite.run(result);
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;
        fPrinter.print(result, runTime);

        pause(wait);
        return result;
    }

    protected void pause(boolean wait) {
        if (!wait) return;
        fPrinter.printWaitPrompt();
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    private Test getSuite(String suiteClassName) {
        if (suiteClassName.length() <= 0) {
            return null;
        }
        Class<?> testClass = null;
        try {
            testClass = loadSuiteClass(suiteClassName);
        } catch (ClassNotFoundException e) {
            String clazz = e.getMessage();
            if (clazz == null) {
                clazz = suiteClassName;
            }
            runFailed("Class not found \"" + clazz + "\"");
            return null;
        } catch (Exception e) {
            runFailed("Error: " + e.toString());
            return null;
        }
        Method suiteMethod = null;
        try {
            suiteMethod = testClass.getMethod(SUITE_METHODNAME);
        } catch (Exception e) {
            // try to extract a test suite automatically
            return new TestSuite(testClass);
        }
        if (!Modifier.isStatic(suiteMethod.getModifiers())) {
            runFailed("Suite() method must be static");
            return null;
        }
        Test test = null;
        try {
            test = (Test) suiteMethod.invoke(null); // static method
            if (test == null) {
                return test;
            }
        } catch (InvocationTargetException e) {
            runFailed("Failed to invoke suite():" + e.getTargetException().toString());
            return null;
        } catch (IllegalAccessException e) {
            runFailed("Failed to invoke suite():" + e.toString());
            return null;
        }

        return test;
    }

    private Test getTest(String testCase, String method) throws Exception {
        Class<? extends TestCase> testClass = loadSuiteClass(testCase).asSubclass(TestCase.class);
        return TestSuite.createTest(testClass, method);
    }

    @Override
    protected void runFailed(String message) {
        System.err.println(message);
        System.exit(FAILURE_EXIT);
    }

    @Override
    public void testFailed(int status, Test test, Throwable e) {
    }

    @Override
    public void testStarted(String testName) {
    }

    @Override
    public void testEnded(String testName) {
    }
}
