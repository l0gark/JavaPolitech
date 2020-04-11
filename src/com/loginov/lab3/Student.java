package com.loginov.lab3;

import java.util.concurrent.atomic.AtomicInteger;

public class Student {
    private static final AtomicInteger ALL_STUDENTS_COUNT = new AtomicInteger(0);
    private final int labsCount;
    private final String subjectName;
    private final String name;


    public Student(int labsCount, String subjectName) {
        this.labsCount = labsCount;
        this.subjectName = subjectName;
        name = "STUDENT_" + (ALL_STUDENTS_COUNT.incrementAndGet());
    }

    public int getLabsCount() {
        return labsCount;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
