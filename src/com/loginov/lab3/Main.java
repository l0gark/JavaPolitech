package com.loginov.lab3;

import com.loginov.lab3.robots.RobotPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getSimpleName());
    private final static int ROOM_CAPACITY = 10;
    private final static int ALL_STUDENTS = 100;
    private final static String OUT_DIR = "src/com/loginov/lab3/output/";


    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        final ConcurrentLinkedQueue<Student> queue = new ConcurrentLinkedQueue<>(StudentGenerator.generateN(ROOM_CAPACITY));
        final AtomicInteger streetStudents = new AtomicInteger(ALL_STUDENTS - ROOM_CAPACITY);
        final CountDownLatch allRestStudents = new CountDownLatch(ALL_STUDENTS);

        final RobotPool robotPool = new RobotPool(allRestStudents);

        final RobotPool.OnRobotsReadyListener onRobotsReadyListener = () -> {
            logger.info("allRestStudents = " + allRestStudents.getCount());
            while (!queue.isEmpty()) {
                final Student student = queue.peek();
                if (robotPool.tryNextStudent(student)) {
                    queue.poll();
                    addStudent(streetStudents, queue);
                } else {
                    return;
                }
            }
        };
        robotPool.setOnRobotsReadyListener(onRobotsReadyListener);

        try {
            allRestStudents.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        //print robots statistic
        final CountDownLatch writtenStats = new CountDownLatch(robotPool.getRobotsCount());
        robotPool.printStats(writtenStats, OUT_DIR);

        //Print total statistic
        final File file = new File(OUT_DIR + "total.txt");
        try (final PrintWriter out = new PrintWriter(file)) {
            out.println("QUEUE = \n" + Arrays.toString(queue.toArray(new Student[0])));
            out.println("\nRest = " + allRestStudents.getCount());
            out.println("\nonStreet = " + streetStudents.get());
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, e.getLocalizedMessage(), e);
        }

        try {
            writtenStats.await();
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, e.getLocalizedMessage(), e);
        }
    }

    private void addStudent(final AtomicInteger restStudents, final ConcurrentLinkedQueue<Student> queue) {
        CompletableFuture.runAsync(() -> {
            if (restStudents.get() > 0) {
                restStudents.decrementAndGet();
                final Student student = StudentGenerator.generate();
                queue.add(student);
                logger.info("Student " + student.getName() + " is entering to room now");
            }
        }).exceptionally(e -> {
            logger.log(Level.WARNING, e, e::getLocalizedMessage);
            return null;
        });
    }
}
