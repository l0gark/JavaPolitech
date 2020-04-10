package com.loginov.lab3;

public class Student {
    private final int labsCount;
    private final String subjectName;

    public Student(int labsCount, String subjectName) {
        this.labsCount = labsCount;
        this.subjectName = subjectName;
    }

    public int getLabsCount() {
        return labsCount;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
