package com.vsvdev.phaser.simple;

import java.util.concurrent.Phaser;

/**
 *
 * Класс Phaser позволяет синхронизировать потоки, представляющие отдельную фазу или стадию выполнения общего действия.
 * Phaser определяет объект синхронизации, который ждет, пока не завершится определенная фаза. Затем Phaser переходит
 * к следующей стадии или фазе и снова ожидает ее завершения.
 *
 */
public class PhaserDemo {
    public static void main(String args[]) {
        Phaser phsr = new Phaser(1);
        int curPhase;

        System.out.println("Запуск потоков");

        new MyThread(phsr, "A");
        new MyThread(phsr, "B");
        new MyThread(phsr, "C");

        // ожидать заверешния всеми потоками исполнения первой фазы
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");

        // ожидать завершения всеми потоками исполнения второй фазы
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");

        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");

        // снять основной поток исполнения с регистрации
        phsr.arriveAndDeregister();

        if(phsr.isTerminated()) {
            System.out.println("Синхронизатор фаз завершен");
        }
    }
}

// Поток исполнения, использующий синхронизатор фаз типа Phaser
class MyThread implements Runnable {
    Phaser phsr;
    String name;

    MyThread(Phaser p, String n) {
        phsr = p;
        name = n;
        phsr.register();
        new Thread(this).start();
    }

    public void run() {

        System.out.println("Поток " + name + " начинает первую фазу");
        phsr.arriveAndAwaitAdvance(); // известить о достижении фазы

        // Небольшая пауза, чтобы не нарушить порядок вывода.
        // Только для иллюстрации, но необязательно для правильного
        // функционирования синхронизатора фаз

        try {
            Thread.sleep(10);
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Поток " + name + " начинает вторую фазу");
        phsr.arriveAndAwaitAdvance(); // известить о достижении фазы

        // Небольшая пауза, чтобы не нарушить порядок вывода.
        // Только для иллюстрации, но необязательно для правильного
        // функционирования синхронизатора фаз

        try {
            Thread.sleep(10);
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Поток " + name + " начинает третью фазу");

        // известить о достижении фазы и снять потоки с регистрации
        phsr.arriveAndDeregister();
    }
}