package com.loginov.lab3;

import com.loginov.lab3.robots.RobotPool;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getSimpleName());
    private final static int ROOM_CAPACITY = 10;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        final ConcurrentLinkedQueue<Student> queue = new ConcurrentLinkedQueue<>(StudentGenerator.generateN(ROOM_CAPACITY));
        CountDownLatch countDownLatch = new CountDownLatch(100 - ROOM_CAPACITY);

        final RobotPool robotPool = new RobotPool();

        final RobotPool.OnRobotsReadyListener onRobotsReadyListener = robotNames -> {
            while (!queue.isEmpty()) {
                final Student student = queue.peek();
                if (!robotNames.contains(student.getSubjectName())) {
                    return;
                }
                robotPool.nextStudent(queue.poll());
                addStudent(countDownLatch, queue);
            }
        };
        robotPool.setOnRobotsReadyListener(onRobotsReadyListener);
        new Scanner(System.in).next();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }


    private void addStudent(final CountDownLatch restStudents, final ConcurrentLinkedQueue<Student> queue) {
        CompletableFuture.runAsync(() -> {
            if (restStudents.getCount() > 0) {
                restStudents.countDown();
                final Student student = StudentGenerator.generate();
                queue.add(student);
            }
        }).exceptionally(e -> {
            logger.log(Level.WARNING, e, e::getLocalizedMessage);
            return null;
        });
    }
}
