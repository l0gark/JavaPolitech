package com.loginov.lab3.robots;

import com.loginov.lab3.Student;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
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

    private final ConcurrentLinkedDeque<String> stats = new ConcurrentLinkedDeque<>();

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
            printMesage("Robot " + subjectName + " begin check student " + student.getName());

            int restLabs = student.getLabsCount();
            while (restLabs > 0) {
                try {
                    Thread.sleep(WORK_TIME);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }


                printMesage("Robot " + subjectName.toUpperCase() + " continue check " + student.getName());
                System.out.println();
                restLabs -= VELOCITY;
            }
        }).thenAccept(aVoid -> {
            printMesage("Robot " + subjectName.toUpperCase() + " end check " + student.getName());
            ready.set(true);
            printMesage("Robot " + subjectName.toUpperCase() + " is ready");
            onNextStudentListener.next(subjectName);
        }).exceptionally(e -> {
            log.log(Level.WARNING, e, e::getLocalizedMessage);
            return null;
        });
    }

    private void printMesage(final String msg) {
        stats.add(msg);
        System.out.println(msg);
    }

    public String getSubjectName() {
        return subjectName;
    }

    public boolean isReady() {
        return ready.get();
    }

    public ConcurrentLinkedDeque<String> getStats() {
        return stats;
    }

    public interface OnNextStudentListener {
        void next(String robotName);
    }
}
