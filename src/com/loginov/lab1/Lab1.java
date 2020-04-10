package com.loginov.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Lab1 {
    private static final String filenameIn = "input.txt";
    private static final String filenameOut = "output.txt";

    // THIS IS SOLUTION!!!!!!!
    // LOOK HERE!
    // HACK ME PLEASE, if you can!!!

    void solve() {

        int n = nextInt();
        int[] numbers = nextIntArray(n);

        Arrays.stream(numbers)
                .filter(x -> x > 0)
                .filter(this::isSimpleNumber)
                .mapToObj(x -> x + " ")
                .forEach(out::print);

        out.println();
    }

    private boolean isSimpleNumber(int number) {
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * ШАБЛОН
     */

    public static void main(String[] args) {
        new Lab1().run();
    }

    void init() {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }

    void run() {
        try {
            long timeStart = System.currentTimeMillis();

            init();
            solve();

            out.close();

            long timeEnd = System.currentTimeMillis();
            System.err.println("Time = " + (timeEnd - timeStart));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private int ceilSearch(long arr[], int low, int high, long x) {
        int mid;

        if (x <= arr[low]) {
            return low;
        }

        if (x > arr[high]) {
            return -1;
        }

        mid = (low + high) / 2;  /* low + (high - low)/2 */

        if (arr[mid] == x) {
            return mid;
        } else if (arr[mid] < x) {
            if (mid + 1 <= high && x <= arr[mid + 1]) {
                return mid + 1;
            } else {
                return ceilSearch(arr, mid + 1, high, x);
            }
        } else {
            if (mid - 1 >= low && x > arr[mid - 1]) {
                return mid;
            } else {
                return ceilSearch(arr, low, mid - 1, x);
            }
        }
    }

    private int floorSearch(long arr[], int low, int high, long x) {
        if (low > high) {
            return -1;
        }

        if (x >= arr[high]) {
            return high;
        }

        int mid = (low + high) / 2;
        if (arr[mid] == x) {
            return mid;
        }
        if (mid > 0 && arr[mid - 1] <= x && x <
                arr[mid]) {
            return mid - 1;
        }

        if (x < arr[mid]) {
            return floorSearch(arr, low, mid - 1, x);
        }

        return floorSearch(arr, mid + 1, high, x);
    }


    /**
     * FAST_SCANNER
     */

    static final boolean ONLINE_JUDGE = System.getProperty("ONLINE_JUDGE") != null;

    BufferedReader in;
    PrintWriter out;
    StringTokenizer st;

    String nextLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String delimiter = " ";

    String next() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return st.nextToken(delimiter);
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    int[] nextIntArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = nextInt();
        }
        return a;
    }

    int[][] nextIntMatrix(int n, int m) {
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            a[i] = nextIntArray(m);
        }
        return a;
    }

    int getMaxArray(int[] a) {
        int max = Integer.MIN_VALUE;
        for (int x : a) {
            max = Math.max(max, x);
        }
        return max;
    }

    private void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

}
