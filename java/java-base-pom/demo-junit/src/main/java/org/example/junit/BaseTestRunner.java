package org.example.junit;

import java.io.*;
import java.util.Properties;

public abstract class BaseTestRunner implements TestListener {

    private static Properties fPreferences;
    static boolean fgFilterStack = true;

    protected abstract void runFailed(String message);

    public abstract void testStarted(String testName);

    public abstract void testEnded(String testName);

    public abstract void testFailed(int status, Test test, Throwable e);

    public synchronized void addError(final Test test, final Throwable e) {
        testFailed(TestRunListener.STATUS_ERROR, test, e);
    }

    public synchronized void addFailure(final Test test, final AssertionFailedError e) {
        testFailed(TestRunListener.STATUS_FAILURE, test, e);
    }

    public synchronized void startTest(Test test) {
        testStarted(test.toString());
    }

    public synchronized void endTest(Test test) {
        testEnded(test.toString());
    }

    public String extractClassName(String className) {
        if (className.startsWith("Default package for")) {
            return className.substring(className.lastIndexOf(".") + 1);
        }
        return className;
    }

    protected Class<?> loadSuiteClass(String suiteClassName) throws ClassNotFoundException {
        return Class.forName(suiteClassName);
    }

    public static String getFilteredTrace(String stack) {
        if (showStackRaw()) {
            return stack;
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        StringReader sr = new StringReader(stack);
        BufferedReader br = new BufferedReader(sr);

        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (!filterLine(line)) {
                    pw.println(line);
                }
            }
        } catch (Exception IOException) {
            return stack; // return the stack unfiltered
        }
        return sw.toString();
    }

    protected static boolean showStackRaw() {
        return !getPreference("filterstack").equals("true") || fgFilterStack == false;
    }

    public static String getPreference(String key) {
        return getPreferences().getProperty(key);
    }

    protected static Properties getPreferences() {
        if (fPreferences == null) {
            fPreferences = new Properties();
            fPreferences.put("loading", "true");
            fPreferences.put("filterstack", "true");
            readPreferences();
        }
        return fPreferences;
    }

    private static void readPreferences() {
        InputStream is = null;
        try {
            is = new FileInputStream(getPreferencesFile());
            setPreferences(new Properties(getPreferences()));
            getPreferences().load(is);
        } catch (IOException ignored) {
        } catch (SecurityException ignored) {
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e1) {
            }
        }
    }

    private static File getPreferencesFile() {
        String home = System.getProperty("user.home");
        return new File(home, "junit.properties");
    }

    protected static void setPreferences(Properties preferences) {
        fPreferences = preferences;
    }

    static boolean filterLine(String line) {
        String[] patterns = new String[]{
                "junit.framework.TestCase",
                "junit.framework.TestResult",
                "junit.framework.TestSuite",
                "junit.framework.Assert.", // don't filter AssertionFailure
                "junit.swingui.TestRunner",
                "junit.awtui.TestRunner",
                "junit.textui.TestRunner",
                "java.lang.reflect.Method.invoke("
        };
        for (int i = 0; i < patterns.length; i++) {
            if (line.indexOf(patterns[i]) > 0) {
                return true;
            }
        }
        return false;
    }

}
