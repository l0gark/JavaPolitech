package com.loginov.lab3.robots;

import com.loginov.lab3.Student;

import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotPool {
    private static final Logger logger = Logger.getLogger(RobotPool.class.getSimpleName());
    private static final String[] SUBJECT_NAMES = {"Вышмат", "ООП", "Физика"};

    private final Robot[] robots;
    private final CountDownLatch countDownLatch;

    private OnRobotsReadyListener onRobotsReadyListener;

    public RobotPool(final CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        this.robots = createRobots();
    }

    private Robot[] createRobots() {
        final Robot[] robots = new Robot[SUBJECT_NAMES.length];
        final Robot.OnNextStudentListener onNextStudentListener = robotName -> {
            if (onRobotsReadyListener == null) {
                logger.log(Level.WARNING, "Listener cant be null on this state!", new IllegalStateException());
                throw new IllegalStateException();
            }
            onRobotsReadyListener.ready();
            countDownLatch.countDown();
        };

        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(SUBJECT_NAMES[i], onNextStudentListener);
        }

        return robots;
    }

    public boolean tryNextStudent(final Student student) {
        for (final Robot robot : robots) {
            if (student.getSubjectName().equals(robot.getSubjectName())
                    && robot.isReady()) {
                robot.work(student);
                return true;
            }
        }
//        logger.log(Level.INFO, "Студент " + student.getName() + " зашёл, а робот занят!");
        return false;
    }

    public void setOnRobotsReadyListener(OnRobotsReadyListener onRobotsReadyListener) {
        this.onRobotsReadyListener = onRobotsReadyListener;
        onRobotsReadyListener.ready();
    }

    public void printStats(final PrintWriter out) {
        for (final Robot robot : robots) {
            out.println("--------" + robot.getSubjectName() + "--------\n");
            out.println("--- Students count = " + robot.getStudentsCount());
            for (final String s : robot.getStats()) {
                out.println(s);
            }
        }
        out.println("\n\n\n");
    }

    @FunctionalInterface
    public interface OnRobotsReadyListener {
        void ready();
    }
}
