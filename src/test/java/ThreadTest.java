import org.junit.jupiter.api.AfterEach;
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
        log("Test started.");
    }

    @AfterEach
    public void tearDown(){
        log("Test finished.");
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

    }

    @Test
    public void testCachedThreadPool() throws ExecutionException, InterruptedException {

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

    }

    @Test
    public void testSingleThreadExecutor() throws ExecutionException, InterruptedException {

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

    }

    @Test
    public void testScheduleAtFixedRate() throws InterruptedException, ExecutionException {
        /*
        A synchronization aid that allows one or more threads to wait until a set of operations being performed
        in other threads completes.
        A CountDownLatch is initialized with a given count. The await methods block until
        the current count reaches zero due to invocations of the countDown method,
        after which all waiting threads are released and any subsequent invocations of await return immediately.
         */
        CountDownLatch lock = new CountDownLatch(3);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        /*
         Thread'ler çalıştırmadan önce delay koyar.
         Task'ların başlangıç zamanına göre zamanı ölçer.
         Task bitiş zamanı uzarsa task yarıda kesilmez.
         */
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            System.out.println("Hello World");
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);


        // Await, count sıfıra inene kadar bloklar. Count'u sıfıra da countDown() metodu getirir.
        lock.await(1000, TimeUnit.MILLISECONDS);
        future.cancel(true);


    }

    @Test
    public void testScheduleAtFixedDelay() throws InterruptedException {

        CountDownLatch lock = new CountDownLatch(3);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        /*
         Thread'ler çalıştırmadan önce delay koyar.
         Önceki task'ın bitişi ile yeni task'ın başlangıç zamanına göre zamanı ölçer.
         */
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
            System.out.println("Hello World");
            lock.countDown();
        }, 500, 100, TimeUnit.MILLISECONDS);

        lock.await(1000, TimeUnit.MILLISECONDS);
        future.cancel(true);
    }

    @Test
    public void testScheduleAtFixedRate2() throws ExecutionException, InterruptedException {
        CountDownLatch lock = new CountDownLatch(5);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        // Task tamamlanma süresi period'dan fazla olduğu için tüm tasklar arasında 1000ms fark vardır.
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            try {
                Thread.sleep(1000);

                Long time = System.currentTimeMillis() - startTime;
                log(String.valueOf(time));

                lock.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 500, 100, TimeUnit.MILLISECONDS);

        // wait until the latch has counted down to zero, unless the thread is interrupted,
        // or the specified waiting time elapses
        lock.await(10000, TimeUnit.MILLISECONDS);
        future.cancel(true);
    }

    @Test
    public void testScheduleAtFixedDelay2() throws ExecutionException, InterruptedException {

        CountDownLatch lock = new CountDownLatch(5);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        // Tüm tasklar arasında 1100ms fark vardır. (Task tamamlanma süresi + period)
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> {
            try {
                Thread.sleep(1000);

                Long time = System.currentTimeMillis() - startTime;
                log(String.valueOf(time));

                lock.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 500, 100, TimeUnit.MILLISECONDS);

        // wait until the latch has counted down to zero, unless the thread is interrupted,
        // or the specified waiting time elapses
        lock.await(10000, TimeUnit.MILLISECONDS);
        future.cancel(true);
    }


}