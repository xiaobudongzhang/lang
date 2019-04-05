package Generic;

import java.util.HashMap;
import java.util.Map;

//测试hash>>> 16
public class HashTest {

    public static void main(String args[]){
        String test = "hello";
        int hashInt = test.hashCode();
        System.out.println(Integer.toBinaryString(hashInt) );

        System.out.println(Integer.toBinaryString(hashInt >>> 16));

        System.out.println(Integer.toBinaryString(hashInt ^ (hashInt >>> 16)));

        System.out.println(HashTest.hash(test));

        //碰撞测试
        Map<String,Object> user = new HashMap<>();//默认长度16
        user.put("level", 1);
        user.put("address", 2);
        System.out.println("\"level\".hashCode() & (16 - 1)	:" + ("level".hashCode() & (16 - 1)));
        System.out.println("\"address\".hashCode() & (16 - 1)	:" + ("address".hashCode() & (16 - 1)));

        System.out.println("\"level\".hashCode() ^ \"level\".hashCode() >>> 16 & (16 - 1)	:" + (("level".hashCode() ^ ("level".hashCode() >>> 16) )& (16 - 1)));
        System.out.println("\"address\".hashCode() ^ \"level\".hashCode() >>> 16 & (16 - 1)	:" + (("address".hashCode() ^ ("address".hashCode() >>> 16) ) & (16 - 1)));

    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
