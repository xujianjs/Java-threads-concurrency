package ch04.ExceptionThread.v2;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName ExceptionThread.java
 * @Description TODO
 * @createTime 2019年08月12日 16:20:00
 */
public class ExceptionThread {
    public static void main(String[] args) {
        Runnable r = () -> {
            int y = 1 / 0;
        };
        Thread thread = new Thread(r);
        //设置线程异常handler.（就近原则）

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) -> {
            //自行捕获异常
            System.out.printf("UncaughtExceptionHandler：捕获到异常\n 线程：%s-----抛出异常：%s--------", t, e);
        };
         Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = (t, e) -> {
            //全局设置的
            System.out.printf("DefaultUncaughtExceptionHandler：捕获到异常\n 线程：%s-----抛出异常：%s--------", t, e);
        };

        //利用【线程实例】设置 未捕获的异常处理程序
        thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        //利用【静态方法】设置【全局】的异常捕获处理程序
        Thread.setDefaultUncaughtExceptionHandler(defaultUncaughtExceptionHandler);
        thread.start();
    }
}
