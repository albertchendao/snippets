package org.example.junit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestSuite implements Test {

    private String fName;
    private Vector<Test> fTests = new Vector<Test>(10);

    public TestSuite(Class theClass) {
        addTestsFromTestCase(theClass);
    }

    private void addTestsFromTestCase(final Class<?> theClass) {
        fName = theClass.getName();
        try {
            getTestConstructor(theClass); // Avoid generating multiple error messages
        } catch (NoSuchMethodException e) {
            addTest(warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()"));
            return;
        }

        if (!Modifier.isPublic(theClass.getModifiers())) {
            addTest(warning("Class " + theClass.getName() + " is not public"));
            return;
        }

        Class<?> superClass = theClass;
        List<String> names = new ArrayList<String>();
        while (Test.class.isAssignableFrom(superClass)) {
            for (Method each : getDeclaredMethods(superClass)) {
                addTestMethod(each, names, theClass);
            }
            superClass = superClass.getSuperclass();
        }
        if (fTests.size() == 0) {
            addTest(warning("No tests found in " + theClass.getName()));
        }
    }

    public static final Comparator<Method> NAME_ASCENDING = new Comparator<Method>() {
        public int compare(Method m1, Method m2) {
            final int comparison = m1.getName().compareTo(m2.getName());
            if (comparison != 0) {
                return comparison;
            }
            return m1.toString().compareTo(m2.toString());
        }
    };

    public static final Comparator<Method> DEFAULT = new Comparator<Method>() {
        public int compare(Method m1, Method m2) {
            int i1 = m1.getName().hashCode();
            int i2 = m2.getName().hashCode();
            if (i1 != i2) {
                return i1 < i2 ? -1 : 1;
            }
            return NAME_ASCENDING.compare(m1, m2);
        }
    };

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        Comparator<Method> comparator = DEFAULT;

        Method[] methods = clazz.getDeclaredMethods();
        if (comparator != null) {
            Arrays.sort(methods, comparator);
        }

        return methods;
    }

    private void addTestMethod(Method m, List<String> names, Class<?> theClass) {
        String name = m.getName();
        if (names.contains(name)) {
            return;
        }
        if (!isPublicTestMethod(m)) {
            if (isTestMethod(m)) {
                addTest(warning("Test method isn't public: " + m.getName() + "(" + theClass.getCanonicalName() + ")"));
            }
            return;
        }
        names.add(name);
        addTest(createTest(theClass, name));
    }

    private boolean isPublicTestMethod(Method m) {
        return isTestMethod(m) && Modifier.isPublic(m.getModifiers());
    }

    private boolean isTestMethod(Method m) {
        return m.getParameterTypes().length == 0 &&
                m.getName().startsWith("test") &&
                m.getReturnType().equals(Void.TYPE);
    }

    public void addTest(Test test) {
        fTests.add(test);
    }

    public int countTestCases() {
        int count = 0;
        for (Test each : fTests) {
            count += each.countTestCases();
        }
        return count;
    }

    public void run(TestResult result) {
        for (Test each : fTests) {
            if (result.shouldStop()) {
                break;
            }
            runTest(each, result);
        }
    }
    public void runTest(Test test, TestResult result) {
        test.run(result);
    }


    static public Test createTest(Class<?> theClass, String name) {
        Constructor<?> constructor;
        try {
            constructor = getTestConstructor(theClass);
        } catch (NoSuchMethodException e) {
            return warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()");
        }
        Object test;
        try {
            if (constructor.getParameterTypes().length == 0) {
                test = constructor.newInstance(new Object[0]);
                if (test instanceof TestCase) {
                    ((TestCase) test).setName(name);
                }
            } else {
                test = constructor.newInstance(new Object[]{name});
            }
        } catch (InstantiationException e) {
            return (warning("Cannot instantiate test case: " + name + " (" + Throwables.getStacktrace(e) + ")"));
        } catch (InvocationTargetException e) {
            return (warning("Exception in constructor: " + name + " (" + Throwables.getStacktrace(e.getTargetException()) + ")"));
        } catch (IllegalAccessException e) {
            return (warning("Cannot access test case: " + name + " (" + Throwables.getStacktrace(e) + ")"));
        }
        return (Test) test;
    }

    public static Constructor<?> getTestConstructor(Class<?> theClass) throws NoSuchMethodException {
        try {
            return theClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            // fall through
        }
        return theClass.getConstructor();
    }

    public static Test warning(final String message) {
        return new TestCase("warning") {
            @Override
            protected void runTest() {
                fail(message);
            }
        };
    }
}
