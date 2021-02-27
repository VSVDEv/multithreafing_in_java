package com.vsvdev.volatil;

/**
 *
 *Атомарность
 * Операция атомарна , если невозможно наблюдать частичный
 * результат её выполнения. Любой наблюдатель видит либо
 * состояние системы до атомарной операции, либо после.
 *
 * В Java:
 *  * Запись в поле типа boolean , byte, short, char, int , Object всегда атомарна
 *  * Запись в поле типа long/double : атомарна запись старших и младших 32бит
 *  * Запись в поле типа long/double , объявленное volatile , атомарна
 *
 * Арифметические операции не атомарны
 *
 * Простые правила
 * happens before
 * 
 * Для двух операций A и B в одном потоке A hb B , если A раньше B
 * в тексте программы ( program order).
 * 
 * Завершение конструктора объекта X hb начало finalize X
 * 
 * Вызов thread.start () hb первое действие в потоке thread
 * 
 * Последнее действие в потоке thread hb thread.join
 * 
 * Инициализация объекта по умолчанию hb любое другое
 * действие
 ****************
 * volatile
 * 
 * Запись и чтение в поле, объявленное volatile, называется volatile
 * read, volatile write
 * 
 * Речь идёт непосредственно о записи, а не о записи
 * членов/элементов массива
 * 
 * volatile int []
 * x = new int 10 ]; // volatile write
 * x[ 0 ] = 1 ; // volatile read, plain write
 */
public class VolatVariable {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100000; i++) {
            Foo foo = new Foo();
            Thread t1 = new Thread(() -> {
                foo.x = 1;
                foo.y = 1;
            });
            Thread t2 = new Thread(()->{
            while (foo.y !=1 );
            System.out.println(foo.x);
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }
    }

}

class Foo {
    int x = 0;
    volatile int y = 0;}
