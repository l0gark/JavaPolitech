package com.loginov.lab3.robots;

import com.loginov.lab3.Student;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Robot {
    private final Logger log = Logger.getLogger(Robot.class.getSimpleName());

    private final AtomicBoolean ready = new AtomicBoolean(true);
    private final String subjectName;
    private final OnNextStudentListener onNextStudentListener;
    private final static int VELOCITY = 5;
    private final static long WORK_TIME = 200;

    public Robot(final String subjectName, final OnNextStudentListener onNextStudentListener) {
        this.subjectName = subjectName;
        this.onNextStudentListener = onNextStudentListener;
    }

    protected void work(final Student student) {
        CompletableFuture.runAsync(() -> {
            if (!student.getSubjectName().equals(subjectName)) {
                throw new IllegalArgumentException("Student go to wrong robot!");
            }
            ready.set(false);

            int restLabs = student.getLabsCount();
            while (restLabs > 0) {
                try {
                    Thread.sleep(WORK_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                restLabs -= VELOCITY;
            }
        }).thenAccept(aVoid -> {
            ready.set(true);
            onNextStudentListener.next(subjectName);
        }).exceptionally(e -> {
            log.log(Level.WARNING, e, e::getLocalizedMessage);
            return null;
        });
    }

    public String getSubjectName() {
        return subjectName;
    }

    public boolean isReady() {
        return ready.get();
    }

    public interface OnNextStudentListener {
        void next(String robotName);
    }
}
