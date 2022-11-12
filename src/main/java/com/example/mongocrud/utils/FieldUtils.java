package com.example.mongocrud.utils;

import java.util.Set;

public class FieldUtils {
    private static final Set<String> javaWords = Set.of("assert", "abstract", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "threadsafe", "throw", "throws", "transient", "true", "try", "void", "volatile", "while");
    private static final Set<String> javaNames = Set.of("CharSequence", "Cloneable", "Comparable", "Runnable", "Boolean", "Byte", "Character", "Class", "ClassLoader", "Compiler", "Double", "Float", "InheritableThreadLocal", "Integer", "Long", "Math", "Number", "Object", "Package", "Process", "Runtime", "RuntimePermission", "SecurityManager", "Short", "StackTraceElement", "StrictMath", "String", "StringBuffer", "System", "Thread", "ThreadGroup", "ThreadLocal", "Throwable", "Void", "ArithmeticException", "ArrayIndexOutOfBoundsException", "ArrayStoreException", "ClassCastException", "ClassNotFoundException", "CloneNotSupportedException", "Exception", "IllegalAccessException", "IllegalArgumentException", "IllegalMonitorStateException", "IllegalStateException", "IllegalThreadStateException", "IndexOutOfBoundsException", "InstantiationException", "InterruptedException", "NegativeArraySizeException", "NoSuchFieldException", "NoSuchMethodException", "NullPointerException", "NumberFormatException", "RuntimeException", "SecurityException", "StringIndexOutOfBoundsException", "UnsupportedOperationException", "AbstractMethodError", "AssertionError", "ClassCircularityError", "ClassFormatError", "Error", "ExceptionInInitializerError", "IllegalAccessError", "IncompatibleClassChangeError", "InstantiationError", "InternalError", "LinkageError", "NoClassDefFoundError", "NoSuchFieldError", "NoSuchMethodError", "OutOfMemoryError", "StackOverflowError", "ThreadDeath", "UnknownError", "UnsatisfiedLinkError", "UnsupportedClassVersionError", "VerifyError", "VirtualMachineError", "BigInteger", "BigDecimal", "Enum", "Date", "GDate", "GDuration", "QName", "List", "XmlObject", "XmlCursor", "XmlBeans", "SchemaType");
    private FieldUtils() {
    }

    public static boolean isInJavaNames(String value) {
        return javaNames.contains(value);
    }

    public static boolean isInJavaWords(String value) {
        return javaWords.contains(value);
    }
}
