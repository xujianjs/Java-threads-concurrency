package ch04.ExceptionThread.v1;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName ExceptionThread.java
 * @Description TODO
 * @createTime 2019年08月12日 16:11:00
 */
public class ExceptionThread {
    public static void main(String[] args) {
        Runnable r = () -> {
            int y = 1 / 0;
        };
        new Thread(r).start();
    }
}
