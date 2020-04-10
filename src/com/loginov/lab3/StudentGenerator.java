package com.loginov.lab3;

public final class StudentGenerator {
    private static final String[] SUBJECT_NAMES = {"Вышмат", "ООП", "Физика"};
    private static final int[] LAB_COUNTS = {10, 20, 100};

    private StudentGenerator() {
    }

    public static Student generate() {
        final int subjectNameIndex = RandomUtils.index(SUBJECT_NAMES);
        final int labCountsIndex = RandomUtils.index(LAB_COUNTS.length);
        final String subjectName = SUBJECT_NAMES[subjectNameIndex];
        final int labsCount = LAB_COUNTS[labCountsIndex];
        return new Student(labsCount, subjectName);
    }
}