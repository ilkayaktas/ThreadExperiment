import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * Created by ilkayaktas on 28.06.2021 at 09:16.
 */
class ThreadTest {
    Long startTime;

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

    private void log(String str){
        System.out.println((System.currentTimeMillis()-startTime)+" ms" +"\t"+str);
    }

}