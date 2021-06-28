import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ilkayaktas on 28.06.2021 at 09:16.
 */
class ThreadTest {
    Long startTime;

    private void log(String str){
        System.out.println((System.currentTimeMillis()-startTime)+" ms" +"\t"+str);
    }

    @BeforeEach
    public void setUp(){
        startTime = System.currentTimeMillis();
    }

    @Test
    public void testExecutor(){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> System.out.println("Hello World"));
    }

    @Test
    public void testExecutorFuture(){

        /*
            ExecutorService'de submit edince thread çalıştırılır ve sonucu Future'a verilir.
            Aradan zaman geçtikten sonra Future'dan sonuç alınıp işlenebilir.
            Future blocking code'dur.
         */
        log("Test started.");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> {
            return String.valueOf(System.currentTimeMillis()-startTime);
        });


        try {
            Thread.sleep(2000);

            log("Before Future call.");

            String result = future.get(); // Eğer thread çalışmayı bitirmemişse, sonuç için beklenir.

            log("Test finished. Thread Result: " + result+" ms");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExecutorFutureWithDelay(){

        /*
            ExecutorService'de submit edince thread çalıştırılır fakat sonucu Future'a verilir.
            Aradan zaman geçtikten sonra Future'dan sonuç alınıp işlenebilir.
            Future blocking code'dur.
         */
        log("Test started.");

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(5000);
            return String.valueOf(System.currentTimeMillis()-startTime);
        });


        try {
            Thread.sleep(2000);

            log("Before Future call.");

            String result = future.get(); // Eğer thread çalışmayı bitirmemişse, sonuç için beklenir.

            log("Test finished. Thread Result: " + result+" ms");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFixedSizeThreadPool() throws ExecutionException, InterruptedException {

        log("Test started.");

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        Future f1 = executor.submit(() -> {
            Thread.sleep(1000);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 1 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        Future f2 = executor.submit(() -> {
            Thread.sleep(2000);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 2 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        Future f3 = executor.submit(() -> {
            Thread.sleep(3000);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 3 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        Future f4 = executor.submit(() -> {
            Thread.sleep(1500);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 4 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        f1.get();
        f2.get();
        f3.get();
        f4.get();

        log("Test finished.");

    }

    @Test
    public void testCachedThreadPool() throws ExecutionException, InterruptedException {

        log("Test started.");

        /*
         İhtiyaç halinde thread cache büyür
         Herşey SynchronousQueue'ya atılır. Burada insert ve remove her zaman aynı anda yapıldığı için queue sıfır görünür.
         */
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Future f1 = executor.submit(() -> {
            Thread.sleep(1000);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 1 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        Future f2 = executor.submit(() -> {
            Thread.sleep(2000);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 2 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        Future f3 = executor.submit(() -> {
            Thread.sleep(3000);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 3 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        Future f4 = executor.submit(() -> {
            Thread.sleep(1500);
            Long time = System.currentTimeMillis() - startTime;
            log("Thread 4 finished at " + time+ "\t"+"Pool Size:" + executor.getPoolSize() +"\t\tQueue:"+executor.getQueue().size()+"\t\tCore Pool:"+executor.getCorePoolSize()+"\t\tActive Count:"+executor.getActiveCount()+"\t\tCompleted:"+executor.getCompletedTaskCount());
            return String.valueOf(time);
        });

        f1.get();
        f2.get();
        f3.get();
        f4.get();

        log("Test finished.");

    }

    @Test
    public void testSingleThreadExecutor() throws ExecutionException, InterruptedException {
        log("Test started.");

        AtomicInteger counter = new AtomicInteger();

        /*
         Threadler eklendiği sıra ile çalıştırılır. Aynı anda sadece tek thread çalıştırılır.
         */
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future f1 = executor.submit(() -> {
            Long time = System.currentTimeMillis() - startTime;
            log("1.Executed at "+time);
            counter.set(1);
        });
        Future f2 = executor.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Long time = System.currentTimeMillis() - startTime;
            log("2.Executed at "+time);

            counter.compareAndSet(1, 2);
        });
        Future f3 = executor.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Long time = System.currentTimeMillis() - startTime;
            log("3.Executed at "+time);

            counter.compareAndSet(2, 3);
        });
        Future f4 = executor.submit(() -> {
            Long time = System.currentTimeMillis() - startTime;
            log("4.Executed at "+time);

            counter.compareAndSet(3, 4);
        });

        // Random get() call
        f4.get();
        f1.get();
        f3.get();
        f2.get();

        log("Result:" + counter.toString());
        log("Test finished.");

    }
}