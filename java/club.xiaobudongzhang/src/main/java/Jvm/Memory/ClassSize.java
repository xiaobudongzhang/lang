package Jvm.Memory;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class ClassSize {

    private static Unsafe getUnsafe(){
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Unsafe unsafe = (Unsafe) field.get(null);
            return unsafe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] strings){
        //one method
        Unsafe UNSAFE = getUnsafe();
        Field[] fields = VolatileLong.class.getDeclaredFields();
        for (Field field : fields) {
               System.out.println(field.getName() + "---offSet:" + UNSAFE.objectFieldOffset(field));
        }
        //two method
        Object obj = new VolatileLong();

        //查看对象内部信息
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        //查看对象外部信息
        System.out.println(GraphLayout.parseInstance(obj).toPrintable());

        //获取对象总大小
        System.out.println("size : " + GraphLayout.parseInstance(obj).totalSize());

    }

     public  static class VolatileLong
     {
         public volatile int value = 1;
         public long p1, p2, p3, p4, p5, p6;
     }
}
