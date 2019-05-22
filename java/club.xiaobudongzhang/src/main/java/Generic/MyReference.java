package Generic;

import java.lang.ref.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.IdentityHashMap;

class MyReference {
    public static void main(String args[]){
        MyReference  ref = new MyReference();
        Object t1;
        t1 = new String("x");
        ref.Phantom(t1);
    }
    //强引用
    public void Strong() {
        Object counter = new Object();
        ReferenceQueue refQueue = new ReferenceQueue<>();
        counter = null;
        try {
            System.gc();//垃圾回收将引用加入队列
            //这时一个阻塞队列，单位毫秒
            Reference<Object> ref = refQueue.remove(1000L);
            if (ref != null) {
                System.out.println("strong ref not null");
            }
        } catch (InterruptedException e) {
            System.out.println("error");
        }
        //output
        //
    }
    //软引用(在内存不足时会回收)
    public void Soft() {
        Object counter = new Object();
        ReferenceQueue refQueue = new ReferenceQueue<>();
        SoftReference<Object> p = new SoftReference<>(counter, refQueue);
        counter = null;
        try {
            System.out.println("soft ref get " + p.get());
            System.gc();//垃圾回收将引用加入队列
            System.out.println("soft ref get2 " + p.get());
            Reference<Object> ref = refQueue.remove(500L);

            if (ref != null) {
                System.out.println("soft ref not null" + ref);
            }
        } catch (InterruptedException e) {
            System.out.println("error");
        }
        //output 由于没能模拟出内存不足，结果如下
        //soft ref get java.lang.Object@1b6d3586
        //soft ref get2 java.lang.Object@1b6d3586
    }
    //弱引用(垃圾收集器扫描到就会被回收)
    public void Weak() {
        Object counter = new Object();
        ReferenceQueue refQueue = new ReferenceQueue<>();
        WeakReference<Object> p = new WeakReference<>(counter, refQueue);
        counter = null;//保证解除强引用

        try {
            System.out.println("weak ref get " + p.get());
            System.gc();//垃圾回收将引用加入队列
            System.out.println("weak ref get2 " + p.get());
            Reference<Object> ref = refQueue.remove(1000L);
            if (ref != null) {
                System.out.println("weak ref not null " + ref);
            }
        } catch (InterruptedException e) {
            System.out.println("error");
        }

        //output
        //weak ref get java.lang.Object@1b6d3586
        //weak ref get2 null
        //weak ref not null java.lang.ref.WeakReference@4554617c
    }
    private Object t(Object t1){
        return t1;
    }
    //幻想引用（finalize后做某些事的规则）
    public void Phantom(Object counter1){
        

        Object counter  = new String("x");
        //Object c2 = counter;
        ReferenceQueue refQueue = new ReferenceQueue<>();
        PhantomReference<Object> p = new PhantomReference<>(counter, refQueue);
        IdentityHashMap<String,Object> _ref2Cxt = new IdentityHashMap<>();

        //String st1 = new String("ee");
        String st = new String("hello");
        _ref2Cxt.put(st, counter);
        _ref2Cxt.remove(st);

        counter = null;//可以被垃圾收集器收集了
       // counter1 = null;
        //p = null;
        attemptGC();
        try {
            //System.out.println("phantom ref get " + p.get());
       /*    Reference<Object> ref1 = refQueue.remove(1000L);
            if (ref1 != null) {
                System.out.println("phantom ref not null");
            }*/
            Reference<Object> ref;
            while ((ref = refQueue.poll()) != null){
                System.out.println("ref  " + ref);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        //output
        //phantom ref get null
        //phantom ref not null
    }

    private static void attemptGC()
    {
        // in my experience, it's not enough to call System.gc(); allocating
        // chunks of memory makes it actually do some works
        ArrayList<byte[]> foo = new ArrayList<byte[]>();
        for (int ii = 0 ; ii < 10000 ; ii++)
            foo.add(new byte[10240]);
        System.gc();
    }
}