package com.vsvdev.exchanger;

import java.util.concurrent.Exchanger;

/**
 *
 *Принцип действия класса Exchanger очень прост: он ожидает до тех пор, пока два отдельных потока исполнения
 *  не вызовут его метод exchange(). Как только это произойдет, он произведет обмен данны­ми,
 *  предоставляемыми обоими потоками. Такой механизм обмена данными не только изящен, но и прост в применении.
 *
 * Нетрудно представить, как воспользо­ваться классом Exchanger.
 * Например, один поток исполнения подготавливает буфер для приема данных через сетевое соединение,
 * а другой - заполняет этот бу­фер данными, поступающими через сетевое соединение. Оба потока исполнения действуют
 * совместно, поэтому всякий раз, когда требуется новая буферизация, осуществляется обмен данными.
 *
 */
public class Exchange {
    public static void main(String args[]) {
        Exchanger<String> exgr = new Exchanger<String>();
        new UseString(exgr);
        new MakeString(exgr);
    }
}

// Поток типа Thread, формирующий символьную строку

class MakeString implements Runnable {
    Exchanger<String> ex;
    String str;
    MakeString(Exchanger<String> c) {
        ex = c;
        str = new String();
        new Thread(this).start();
    }

    public void run() {
        char ch = 'A';
        for(int i = 0; i < 3; i++) {
            // заполнить буфер
            for(int j = 0; j < 5; j++) {
                str += (char) ch++;
            }
            try {
                // обменять заполненный буфер на пустой
                str = ex.exchange(str);
            } catch(InterruptedException exc) {
                System.out.println(exc);
            }
        }
    }
}

// Поток типа Thread, использующий символьную строку

class UseString implements Runnable {
    Exchanger<String> ex;
    String str;
    UseString(Exchanger<String> c) {
        ex = c;
        new Thread(this).start();
    }

    public void run() {
        for(int i = 0; i < 3; i++) {
            try {
                // обменять пустой буфер на заполненный
                str = ex.exchange(new String());
                System.out.println("Получено: " + str);
            } catch(InterruptedException exc) {
                System.out.println(exc);
            }
        }
    }
}
