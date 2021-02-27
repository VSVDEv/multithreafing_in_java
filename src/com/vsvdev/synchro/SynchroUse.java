package com.vsvdev.synchro;

import java.util.concurrent.TimeUnit;

/**
 *
 *Синхронизованный метод
 *Синхронизованный блок внутри метода
 *
 * synchronized( obj ) {…}: монитор объект obj
 *
 * synchronized void method(): монитор объект this
 *
 * synchronized static void method(): монитор объект .class
 *
 *
 * Допустимо только внутри synchronized:
 * void wait()
 * void wait(long millis)
 * void wait(long millis, int nanos)
 * void notify()
 * void notifyAll()
 */
public class SynchroUse {
    static int counter;

    public static void main(String[] args) {

        //block by class and by obj
        StringBuilder info = new StringBuilder();
        new Thread(() -> {
            synchronized (info) { //we can use instead synchronized(StringBuilder.class)
                //synchronized(StringBuilder.class){
                do {
                    info.append('A');
                    System.out.println(info);
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } while (counter++ < 2) ;
            }
        }).start();
        new Thread(() -> {
            synchronized (info) {
                while(counter++ < 6) {
                    info.append('Z');
                    System.out.println(info);
                }
            }
        }).start();

//method
        Ticket[] t = {
                new Ticket(10),
                new Ticket(20),
                new Ticket(30),
                new Ticket(40)
        };

        Cashier c1 = new Cashier(1, t);
        Cashier c2 = new Cashier(2, t);
        Cashier c3 = new Cashier(3, t);
        Cashier c4 = new Cashier(4, t);

        c1.start();
        c2.start();
        c3.start();
        c4.start();
    }
}


class Ticket {
    boolean isBought;
    int place;

    Ticket(int place) {
        this.place = place;
    }

    // Билет будет покупатся покупаться кассиром
    synchronized void buy(Cashier c) {
        if (!isBought) {
            try {
                // Если билет не куплен, на 1 сек засыпаем
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            // Билет куплен
            isBought = true;
            System.out.println("Cashier " + c.id + " ticket " + this.place);
        }
    }
}

class Cashier extends Thread {
    int id;
    Ticket[] t;

    Cashier(int id, Ticket[] t) {
        this.id = id;
        this.t = t;
    }

    // synchronized указывает на то, что все что происходит в методе run делается как единное целое
    @Override
    public synchronized void run() {
        for (int i = 0; i < t.length; i++) {
//            // Кассир просматривает массив билетов, если какой-то билет не куплен, меняет флаг на true
try
{sleep(10);
    System.out.println("sleep");
}catch (InterruptedException e){
    System.out.println(e);}
            t[i].buy(this);
        }
    }
}