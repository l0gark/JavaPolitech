package com.loginov.lab3.robots;

import com.loginov.lab3.Student;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RobotPool {
    private final static Logger logger = Logger.getLogger(RobotPool.class.getSimpleName());

    private static final String[] SUBJECT_NAMES = {"Вышмат", "ООП", "Физика"};

    private final Robot[] robots;
    private final OnRobotsReadyListener onRobotsReadyListener;

    private RobotPool(final OnRobotsReadyListener onRobotsReadyListener) {
        this.onRobotsReadyListener = onRobotsReadyListener;
        this.robots = createRobots();
    }

    private Robot[] createRobots() {
        final Robot[] robots = new Robot[SUBJECT_NAMES.length];
        final Robot.OnNextStudentListener onNextStudentListener = robotName -> {
            onRobotsReadyListener.ready(getReadyRobots());
        };

        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(SUBJECT_NAMES[i], onNextStudentListener);
        }

        return robots;
    }

    public void nextStudent(final Student student) {
        for (final Robot robot : robots) {
            if (student.getSubjectName().equals(robot.getSubjectName())
                    && robot.isReady()) {
                robot.work(student);
                return;
            }
        }
        logger.log(Level.WARNING, "Студент зашёл, а робот занят! ", new IllegalStateException());
    }

    private List<String> getReadyRobots() {
        if (robots == null) {
            logger.log(Level.WARNING, "Robots is null!");
            throw new IllegalStateException();
        }

        return Arrays.stream(robots)
                .filter(Robot::isReady)
                .map(Robot::getSubjectName)
                .collect(Collectors.toList());
    }

    @FunctionalInterface
    public interface OnRobotsReadyListener {
        void ready(List<String> robots);
    }
}
