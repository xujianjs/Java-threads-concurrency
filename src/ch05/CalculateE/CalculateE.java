package ch05.CalculateE;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;


/**
 * The type Calculate e.
 * 因为Executor接口没有返回值也不能追踪任务执行状况  也不能管理线程  所以就希望把他包装下 使得用起来更加方便 ExecutorService就诞生了
 * ExecutorService接口可以管理线程的状态  也可以通过一个Future来追踪任务状态
 */
public class CalculateE
{

   /**
    * The constant LASTITER.
    */
   final static int LASTITER = 17;

   /**
    * The entry point of application.
    *
    * @param args the input arguments
    */
   public static void main(String[] args)
   {
      ExecutorService executor = Executors.newFixedThreadPool(1);
      //Runnable 既不能返回值也不能抛出异常
      //Callable 调用call方法既有返回值又可以抛出异常
      Callable<BigDecimal> callable;
      callable = new Callable<BigDecimal>()
                 {
                    @Override
                    public BigDecimal call()
                    {
                       MathContext mc =
                         new MathContext(100, RoundingMode.HALF_UP);
                       BigDecimal result = BigDecimal.ZERO;
                       for (int i = 0; i <= LASTITER; i++)
                       {
                          BigDecimal factorial =
                             factorial(new BigDecimal(i));
                          BigDecimal res = BigDecimal.ONE.divide(factorial,
                                                                 mc);
                          result = result.add(res);
                       }
                       return result;
                    }

                    public BigDecimal factorial(BigDecimal n)
                    {
                       if (n.equals(BigDecimal.ZERO))
                          return BigDecimal.ONE;
                       else
                          return n.multiply(factorial(n.
                                   subtract(BigDecimal.ONE)));
                    }
                 };

      //Future    它代表着一种异步计算 的结果（ A {@code Future} represents the result of an asynchronous computation）
      Future<BigDecimal> taskFuture = executor.submit(callable);
      try
      {
         while (!taskFuture.isDone())
            System.out.println("任务还在执行中");
         System.out.println(taskFuture.get());

      }
      catch(ExecutionException ee)
      {
         System.err.println("任务丢出了异常");
         System.err.println(ee);
      }
      catch(InterruptedException ie)
      {
         System.err.println("等待执行时被中断");
      }
      executor.shutdownNow();
   }

   /**
    * Test 1.
    *
    * @throws ExecutionException the execution exception
    * @throws InterruptedException the interrupted exception
    */
   @Test
   public void test1() throws ExecutionException, InterruptedException {
      ExecutorService executorService = Executors.newFixedThreadPool(1);
      Callable<BigDecimal> callable;

      callable = () -> {
         BigDecimal result = BigDecimal.ZERO;
         Thread.sleep(3000);
         return result;
      };

      long lo = System.currentTimeMillis();//提交任务前
      Future<BigDecimal> bigDecimalFuture = executorService.submit(callable);
      System.out.println(bigDecimalFuture.get());//阻塞
      long hi = System.currentTimeMillis();//任务结果返回后


      System.out.printf("主线程耗时：%d\n",hi-lo);
   }

   /**
    * Test 2.
    *
    * @throws ExecutionException the execution exception
    * @throws InterruptedException the interrupted exception
    */
   @Test
   public void test2() throws ExecutionException, InterruptedException {
      ExecutorService executorService = Executors.newFixedThreadPool(1);
      Callable<BigDecimal> callable;

      callable = () -> {
         BigDecimal result = BigDecimal.ZERO;
         Thread.sleep(3000);
         return result;
      };

      long lo = System.currentTimeMillis();//提交任务前
      Future<BigDecimal> bigDecimalFuture = executorService.submit(callable);


       System.out.printf("取消任务执行？%s\n",bigDecimalFuture.isCancelled());
       System.out.printf("A:任务执行完毕？%s\n",bigDecimalFuture.isDone());
       bigDecimalFuture.cancel(true);
       System.out.printf("取消任务执行？%s\n",bigDecimalFuture.isCancelled());
       System.out.printf("B:任务执行完毕？%s\n",bigDecimalFuture.isDone());//取消了任务 也算作任务执行完毕

      System.out.println(bigDecimalFuture.get());//java.util.concurrent.CancellationException  因为先调用了cancel方法取消了任务的执行
      long hi = System.currentTimeMillis();//任务结果返回后


      System.out.printf("主线程耗时：%d\n",hi-lo);
   }


}