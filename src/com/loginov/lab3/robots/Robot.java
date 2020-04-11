package com.loginov.lab3.robots;

import com.loginov.lab3.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Robot {
    private final Logger logger = Logger.getLogger(Robot.class.getSimpleName());

    private final AtomicBoolean ready = new AtomicBoolean(true);
    private final String subjectName;
    private final OnNextStudentListener onNextStudentListener;
    private final static int VELOCITY = 5;
    private final static long WORK_TIME = 20;

    private final ConcurrentLinkedDeque<String> stats = new ConcurrentLinkedDeque<>();
    private final AtomicInteger studentsCount = new AtomicInteger(0);


    public Robot(final String subjectName, final OnNextStudentListener onNextStudentListener) {
        this.subjectName = subjectName;
        this.onNextStudentListener = onNextStudentListener;
    }

    protected void work(final Student student) {
        ready.set(false);
        CompletableFuture.runAsync(() -> {
            if (!student.getSubjectName().equals(subjectName)) {
                throw new IllegalArgumentException("Student go to wrong robot!");
            }
            printMessage("Robot " + subjectName + " begin check student " + student.getName());

            int restLabs = student.getLabsCount();
            while (restLabs > 0) {
                try {
                    Thread.sleep(WORK_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }


                printMessage("Continue " + student.getName());
                restLabs -= VELOCITY;
            }
        }).thenAccept(aVoid -> {
            printMessage("Robot " + subjectName.toUpperCase() + " end check " + student.getName());
            ready.set(true);
            studentsCount.incrementAndGet();
            printMessage("Robot " + subjectName.toUpperCase() + " is ready");
            onNextStudentListener.next(subjectName);
        }).exceptionally(e -> {
            logger.log(Level.WARNING, e, e::getLocalizedMessage);
            return null;
        });
    }

    private void printMessage(final String msg) {
        stats.add(msg);
        logger.info(msg);
    }

    protected void printLogs(final String dirPath) {
        final String filename = dirPath + subjectName + "_stats.txt";

        try (final PrintWriter out = new PrintWriter(new File(filename))) {
            out.println("--------" + subjectName.toUpperCase() + " Statistic --------\n");
            out.println("--- Students count = " + getStudentsCount());
            for (final String s : stats) {
                out.println(s);
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, e.getLocalizedMessage(), e);
        }
    }

    public String getSubjectName() {
        return subjectName;
    }

    public boolean isReady() {
        return ready.get();
    }

    public int getStudentsCount() {
        return studentsCount.get();
    }

    @FunctionalInterface
    public interface OnNextStudentListener {
        void next(String robotName);
    }
}
