package appa.ch01;

import java.lang.Thread.State;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import sun.util.resources.LocaleData;

/**
 * The type Int sleep.
 */
public class IntSleep {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    //定义一个runnable接口
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        //启动线程后  执行的操作
        System.out.println("启动线程后执行 匿名内部类。。。。");
        while (true) {
          System.out.println("我的心跳永不停息。。。。" + System.currentTimeMillis());

          //睡一觉
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("啊 心跳快停了！");
            break;
          }

        }
      }
    };

    // runnable接口为指定的线程指定运行代码
    Thread thread = new Thread(runnable);
    thread.start();

    //线程启动后  主线程睡一哈哈儿
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //中断线程
    thread.interrupt();

  }

  /**
   * Test 1.
   */
  @Test
  void test1() {
    String msg = Thread.currentThread().getName() + ":lambda λ的形式定义runnable";

//    Runnable r=()->System.out.println(msg);
    Runnable r = () -> System.out
        .println(Thread.currentThread().getName() + ":lambda λ的形式定义runnable");
    Runnable r2 = () -> System.out
        .println(Thread.currentThread().getName() + ":lambda λ的形式定义runnable");
    Thread t = new Thread(r);
    Thread t2 = new Thread(r2);
    t.start();
//    t.start(); //线程只能启动一次
    t2.start();
//    t2.start();

  }

  /**
   * Test 2.
   */
  @Test
  void test2() {
    Thread t1 = new Thread("线程1");
    Thread t2 = new Thread("");
    t2.setName("线程2");
    System.out.println(t1.getName());
    System.out.println(t2.getName());

  }

  /**
   * Test 3.
   */
  @Test
  void test3() {
    Thread thread = new Thread();
    System.out.println(thread.isAlive());
    thread.start();
    System.out.println(thread.isAlive());

  }


  /**
   * Test 4.
   */
  @Test
  void test4() {
    Thread t1 = new Thread();
    System.out.println(t1.getState());//NEW 线程还没有执行

    t1.start();
    System.out.println(t1.getState());//RUNNABLE 该线程正在JVM中运行

    Runnable runnable = () -> System.out.println("正在执行线程操作");
    Thread t2 = new Thread(runnable);
    System.out.println(t2.getState());//NEW 线程还没有执行

    t2.start();
    System.out.println(t2.getState());//RUNNABLE 该线程正在JVM中运行
  }


  /**
   * Test 5.
   */
  @Test
  void test5() {
    Runtime runtime = Runtime.getRuntime();
    int i = runtime.availableProcessors();
    System.out.printf("本宝宝的机器核心数：%d",i);//本宝宝的机器核心数：12
  }

  /**
   * Test 6.
   */
  @Test
  void test6() {
    Thread t = new Thread();
    //获取线程优先级 多个线程 优先级高的先执行
    //优先级范围介于[Thread.MIN_PRIORITY,Thread.MAX_PRIORITY]
    System.out.println(t.getPriority());
    t.setPriority(Thread.MAX_PRIORITY);
    System.out.println(t.getPriority());
  }

  /**
   * Test 7.
   */
  @Test
  void test7() {
    Thread t = new Thread();
    //守护线程
    System.out.println(t.isDaemon());
    t.setDaemon(true);
    System.out.println(t.isDaemon());
  }

}
