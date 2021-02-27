package com.vsvdev.concurrentcollect;

import java.util.*;
import java.util.concurrent.*;

/**
 * ConcurrentLinkedQueue LinkedList
 * ConcurrentLinkedDeque LinkedList ArrayDeque
 * CopyOnWriteArrayList ArrayList
 * CopyOnWriteArraySet
 * ConcurrentHashMap (+ newKeySet ()) HashMap HashSet
 * ConcurrentSkipListMap TreeMap
 * ConcurrentSkipListSet TreeSet
 * <p>
 * <p>
 * Принципы
 * Простые операции атомарны
 * Пакетные операции ( addAll , removeAll ) могут быть неатомарны
 * Как правило, длина не хранится ( isEmpty
 * Не кидает ConcurrentModificationException
 * Обычно weakly consistent ( изменения после создания итератора могут быть видны или не видны)
 * <p>
 * <p>
 * <p>
 * CopyOnWriteArrayList(Set)
 * Содержимое хранится в массиве
 * Модифицирующие операции синхронизированы
 * Операции на чтение и iterator не синхронизированы
 * Любое изменение копирует массив
 * Итератор, forEach обходят снимок коллекции на момент начала
 * итерации итератор не может модифицировать
 * Сортировать можно с Java 8
 * CopyOnWriteArraySet делегат к CopyOnWriteArrayList
 * Опасайтесь методов с множественными модификациями
 * <p>
 * <p>
 * <p>
 * <p>
 * ConcurrentHashMap
 * До Java 8: совокупность сегментов, каждый из которых
 * отдельный HashMap
 * В Java 8 переписан заново, больше похож на обычный HashMap
 * reduce/search/forEach*создают параллельные задачи
 * Реализация занимает>6000строк!
 * putIfAbsent,computeIfAbsent,merge и т.д.любят вас!
 * <p>
 * <p>
 * ConcurrentSkipListMap
 * <p>
 * Конкуррентная замена TreeMap
 * Структура данных: skip list. Она рандомизирована, но этого не видно!
 * Есть ConcurrentSkipListSet
 * computeIfAbsent может вызвать функцию несколько раз!
 */
public class NonBlocking {

    void addArrayToList1(List<String> list, String[] array) {
        list.addAll(Arrays.asList(array));
    }//better than next

    //if we use CopyOnWriteArrayList we spent too much memory
    // each call add will be create new copy array for example 100 elem 1st 1 and copy-> 2d 2 and copy....
    void addArrayToList2(List<String> list, String[] array) {
        Collections.addAll(list, array);
    }

    //Collections.addAll
    public static <T> boolean addAll(Collection<? super T> c, T... elements) {
        boolean result = false;
        for (T element : elements)
            result |= c.add(element);
        return result;
    }

    public static void main(String[] args) {
        ConcurrentLinkedQueue l = new ConcurrentLinkedQueue();
        ConcurrentLinkedDeque l1 = new ConcurrentLinkedDeque();
        CopyOnWriteArrayList l2 = new CopyOnWriteArrayList();
        CopyOnWriteArraySet l3 = new CopyOnWriteArraySet();
        ConcurrentHashMap l4 = new ConcurrentHashMap();
        ConcurrentSkipListMap l5 = new ConcurrentSkipListMap();
        ConcurrentSkipListSet l6 = new ConcurrentSkipListSet();
        CopyOnWriteArrayList l7 = new CopyOnWriteArrayList();
        ConcurrentHashMap l8 = new ConcurrentHashMap();
        ConcurrentSkipListMap<String, Integer> l9 = new ConcurrentSkipListMap<>();

    }
}
