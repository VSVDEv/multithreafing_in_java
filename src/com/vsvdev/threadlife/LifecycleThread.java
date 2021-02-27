package com.vsvdev.threadlife;

/**
 *
 *NEW — поток создан, но еще не запущен;
 * RUNNABLE — поток выполняется;
 * BLOCKED — поток блокирован;
 * WAITING — поток ждет окончания работы другого потока;
 * TIMED_WAITING — поток некоторое время ждет окончания другого по-
 * тока или просто в ожидании истечения времени;
 * TERMINATED — поток завершен.
 *
 * @site: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Thread.State.html
 * Enum Thread.State
 *
* NEW	 - which has not yet started.
* BLOCKED - blocked waiting for a monitor lock.
Object.wait().
* RUNNABLE - runnable thread.
start()
notify() or Object.notifyAll()
* TERMINATED	- terminated thread.
end task
we can stop interrupt()
* TIMED_WAITING- waiting thread with a specified waiting time.
Thread.sleep()
Object.wait() with timeout
Thread.join() with timeout
LockSupport.parkNanos()
LockSupport.parkUntil()

* WAITING -waiting thread
Object.wait()
Thread.join()
LockSupport.park()


 THREAD DUMP to see all threads and their state
Список всех потоков с их состояниями и stack trace’ами
1. CONSOLE
Windows: Ctrl+Break или
Linux: Ctrl+\
2. jps -l     (show processes), затем jstack PID
Кнопка в IDE IntellijiIdea (Ctrl+Break)

 */
public class LifecycleThread {
    public static void main(String[] args) throws Exception {
        Thread worker = new WorkerThread();
        Thread sleeper = new SleeperThread();

        System.out.println("Starting threads");
        worker.start();
        sleeper.start();

        System.out.println("Interrupting threads");
        worker.interrupt();
        sleeper.interrupt();

        System.out.println("Joining threads");
        worker.join();
        sleeper.join();

        System.out.println("All done");

    }
}

 class WorkerThread extends Thread {

    @Override
    public void run() {
        long sum = 0;
        for (int i = 0; i < 10000000; ++i) {
            sum += i;
            if (i % 100 == 0 && isInterrupted()) {
                System.out.println("Loop interrupted at i = " + i);
                break;
            }
        }
    }
}

 class SleeperThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
        }
    }
}
