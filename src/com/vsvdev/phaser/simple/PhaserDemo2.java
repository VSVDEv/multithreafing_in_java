package com.vsvdev.phaser.simple;

// Расширить класс Phaser и переопределить метод onAdvance()
// таким образом, чтобы было выполнено только определенное
// количество фаз

import java.util.concurrent.*;

// Расширить класс MyPhaser, чтобы выполнить только
// определенное количество фаз

class MyPhaser extends Phaser {

    int numPhases;

    MyPhaser(int parties, int phaseCount) {
        super(parties);
        numPhases = phaseCount - 1;
    }

    // переопределить метод onAdvance(), чтобы выполнить
    // определенное количество фаз

    protected boolean onAdvance(int p, int regParties) {
        // Следующий оператор println() требуется только для
        // целей иллюстрации. Как правило, метод onAdvance()
        // не отображает выводимые данные
        System.out.println("Фаза " + p + " завершена.\n");

        // возратить логическое значение true,
        // если все фазы завершены
        if(p == numPhases || regParties == 0) return true;

        // В противном случает возратить логическое значение false;
        return false;
    }
}

public class PhaserDemo2 {

    public static void main(String args[]) {

        MyPhaser phsr = new MyPhaser(1, 4);
        System.out.println("Запуск потоков\n");

        new MyThread1(phsr, "A");
        new MyThread1(phsr, "B");
        new MyThread1(phsr, "C");

        // ожидать завершения определенного количества фаз
        while(!phsr.isTerminated()) {
            phsr.arriveAndAwaitAdvance();
        }

        System.out.println("Синхронизатор фаз завершен");
    }
}

// Поток исполнения, использующий синхронизатор фаз типа Phaser
class MyThread1 implements Runnable {

    Phaser phsr;
    String name;

    MyThread1(Phaser p, String n) {
        phsr = p;
        name = n;
        phsr.register();
        new Thread(this).start();
    }

    public void run() {

        while(!phsr.isTerminated()) {
            System.out.println(
                    "Поток " + name + " начинает фазу " + phsr.getPhase());
            phsr.arriveAndAwaitAdvance();

            // небольшая пауза, чтобы не нарушить порядок вывода.
            // Только для иллюстрации, но необязательно для правильного
            // функционирования синхронизатора фаз

            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
