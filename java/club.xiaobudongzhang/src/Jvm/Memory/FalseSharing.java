package Jvm.Memory;

import java.util.concurrent.atomic.AtomicLong;


//伪共享问题
public class FalseSharing implements Runnable
{
    public final static int NUM_THREADS = 4;
    public final static long ITERNATIONS = 500L * 1000L * 100L;
    private final int arrayIndex;

    private static PaddedAtomicLong[] longs = new PaddedAtomicLong[NUM_THREADS];
    private static long[] longs2 = new long[NUM_THREADS];

    static //只被加载一次
    {
        for (int i = 0; i < longs.length; i++)
        {
            longs[i] = new PaddedAtomicLong();
            longs2[i] = sumPaddingToPreventOptimisation(i);
        }
    }

    public FalseSharing(final int arrayIndex)
    {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception
    {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException
    {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i =0; i < threads.length; i++)
        {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    public void run()
    {
        long i = ITERNATIONS + 1;
        while (0 != --i)
        {
            longs[arrayIndex].set(i);
        }
    }
    //PaddedAtomicLong类如果只对final的FalseSharing类可见（就是说PaddedAtomicLong不能再被继承了）。
    // 这样一来编译器就会“知道”它正在审视的是所有可以看到这个填充字段的代码，
    // 这样就可以证明没有行为依赖于p1到p7这些字段。那么“聪明”的JVM会把上面这些丝毫不占地方的字段统统优化掉。
    public static long sumPaddingToPreventOptimisation(final int index)
    {
        PaddedAtomicLong v = longs[index];
        return v.p1 + v.p2 + v.p3 + v.p4 + v.p5 + v.p6;
    }
    //对象头8字节
    // 需要在jvm启动时设置-XX:-RestrictContended才会生效
    //@sun.misc.Contended
    public static class PaddedAtomicLong extends AtomicLong
    {
        public volatile long p1, p2, p3, p4, p5, p6 = 7L;
    }

   // public  static class VolatileLong
   // {
   //     public volatile long value = 0L;
   //     public long p1, p2, p3, p4, p5, p6;
   // }
}
