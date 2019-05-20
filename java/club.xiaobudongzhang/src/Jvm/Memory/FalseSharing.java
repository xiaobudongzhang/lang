package Jvm.Memory;
//伪共享问题
public class FalseSharing implements Runnable
{
    public final static int NUM_THREADS = 4;
    public final static long ITERNATIONS = 500L * 1000L * 100L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static //只被加载一次
    {
        for (int i = 0; i < longs.length; i++)
        {
            longs[i] = new VolatileLong();
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
            longs[arrayIndex].value = 1;
        }
    }
    //对象头8字节
   // 需要在jvm启动时设置-XX:-RestrictContended才会生效
    @sun.misc.Contended
    public final static class VolatileLong
    {
        public volatile long value = 0L;
        //public long p1, p2, p3, p4, p5, p6;
    }
}
