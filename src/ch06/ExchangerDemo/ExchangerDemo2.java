package ch06.ExchangerDemo;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExchangerDemo2 {

  public static Exchanger<String> exchanger = new Exchanger();
  public static ExecutorService executorService = Executors.newFixedThreadPool(2);

  public static void main(String[] args) {
    // 模拟银行流水对账
    executorService.execute(()->{
      try {
        String A = "银行流水A";
        String B = exchanger.exchange(A);//得到交换数据的内容==========从A拿过来的
        System.out.println(System.currentTimeMillis()+"--A和B数据是否一致："+A.equals(B)+" A录入的是："+A+" B录入的是："+B);
      } catch (InterruptedException e) {
        e.printStackTrace();
        executorService.shutdown();

      }
    });

    executorService.execute(()->{
      try {
        String B = "银行流水B";
        String A = exchanger.exchange("从A拿过来的");//得到交换数据的内容==========银行流水A
        System.out.println(System.currentTimeMillis()+"------A和B数据是否一致："+A.equals(B)+" A录入的是："+A+" B录入的是："+B);

      } catch (InterruptedException e) {
        e.printStackTrace();
        executorService.shutdown();
      }
    });


  }

}
