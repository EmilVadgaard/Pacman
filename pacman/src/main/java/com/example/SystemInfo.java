package com.example;

/**
 * Information about system.
 */
public class SystemInfo {

    /**
     * Returns version properties of Java.
     */
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Returns version properties of JavaFX.
     */
    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

}