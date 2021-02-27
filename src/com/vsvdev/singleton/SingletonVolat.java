package com.vsvdev.singleton;

public class SingletonVolat {

    private int foo;
    private String bar;

    private static volatile SingletonVolat instance;

    private SingletonVolat() {
    }

    public static SingletonVolat getInstance() {
        if(instance==null){
        synchronized (SingletonVolat.class){
            if (instance == null) {
            instance = new SingletonVolat();
        }}}
        return instance;
    }





    public static void main(String[] args) {
SingletonVolat a = SingletonVolat.getInstance();
        System.out.println(a);
        SingletonVolat b =SingletonVolat.getInstance();
        System.out.println(b);
        System.out.println(a.equals(b));
        System.out.println(a==b);

    }
}