package com.loginov.lab3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private final static int ROOM_CAPACITY = 10;
    private final static int ROBOT_COUNT = 3;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        final AtomicInteger countStudentsInRoom = new AtomicInteger(0);
        final AtomicInteger countStudentsOnStreet = new AtomicInteger(100);
        final AtomicInteger countStudentsInHome = new AtomicInteger(0);

        final OnStudentLeaveRoomListener onStudentLeaveRoomListener = () -> {
            final int freePlaces = ROOM_CAPACITY - countStudentsInRoom.get();
            if (freePlaces <= countStudentsInRoom.get()) {
                countStudentsOnStreet.updateAndGet(i -> i - freePlaces);
                countStudentsInRoom.addAndGet(freePlaces);
            } else {
                countStudentsInRoom.addAndGet(countStudentsOnStreet.getAndUpdate(i -> 0));
            }
        };

        final ExecutorService teachersPool = Executors.newFixedThreadPool(3);
        while (countStudentsInHome.get() != 0) {

        }
    }


    @FunctionalInterface
    private interface OnStudentLeaveRoomListener {
        void leave();
    }
}
