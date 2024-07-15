package com.example.java;

public class CaseLineJoining {
    public CaseLineJoining(final int foo, final Runnable bar, final Runnable baz) {
        switch (foo) {
            case 0: bar.run();
            case 1:
                bar.run();
                break;
            default:
                baz.run();
                break;
        }
    }
}