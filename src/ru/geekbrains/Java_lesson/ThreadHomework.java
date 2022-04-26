package ru.geekbrains.Java_lesson;

import java.util.Arrays;

public class ThreadHomework {

    /**
     * Необходимо написать два метода, которые делают следующее:
     * 1)	Создают одномерный длинный массив, например:
     * 2)	Заполняют этот массив единицами.
     * 3)	Засекают время выполнения: long a = System.currentTimeMillis().
     * 4)	Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
     * 5)	Проверяется время окончания метода System.currentTimeMillis().
     * 6)	В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a).
     * Код первого метода может быть вот таким:
     */

    private static final int size = 10000000;
    private static final int h = size / 2;

    public static void main(String[] args) {

        ThreadHomework threadHomework = new ThreadHomework();
        threadHomework.firstMethod();
        threadHomework.secondMethod();
        
    }

    private void firstMethod() {

        System.out.println("Stars first method");
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);

        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5.0) * Math.cos(0.2f + i / 5.0) * Math.cos(0.4f + i / 2.0));
        }
        long end = System.currentTimeMillis();

        System.out.printf("Ends first method. Lead time %s%n", end - start);
    }

    private void secondMethod() {

        System.out.println("Start second method");
        float[] arr = new float[size];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        Arrays.fill(arr, 1.0f);

        long start = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        long split = System.currentTimeMillis();

        System.out.printf("Array Fetch Execution Time %s%n", split - start);

        Thread thread1 = new Thread(() -> this.secondMethodInternal(arr1, 1));
        Thread thread2 = new Thread(() -> this.secondMethodInternal(arr2, 2));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.printf("Exception in Threads. %s%n", e.getMessage());
        }


        long connect = System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        long end = System.currentTimeMillis();

        System.out.printf("Array splicing time %s%n", end - connect);
        System.out.printf("Ends second method. Lead time %s%n", end - start);
    }

    private void secondMethodInternal(float[] arr, int number) {

        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5.0) * Math.cos(0.2f + i / 5.0) * Math.cos(0.4f + i / 2.0));
        }
        long end = System.currentTimeMillis();

        System.out.printf("Thread execution time %d = %s%n", number, end - start);
    }
}