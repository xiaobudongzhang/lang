package Generic;

import java.util.HashMap;
import java.util.Map;

public class LinkedHashMap {

    public static void main(String args[]){
        HashMap<Integer, Integer> m = new java.util.LinkedHashMap<>(10, 0.75f, true);
        m.put(3, 11);
        m.put(1, 12);
        m.put(5, 23);
        m.put(2, 22);
        System.out.println("-----------------");
        //3
        //1
        //5
        //2
        for (Map.Entry e : m.entrySet()) {
            System.out.println(e.getKey());
        }

        m.put(3, 26);
        System.out.println("-----------------");
        //1
        //5
        //2
        //3
        //----
        for (Map.Entry e : m.entrySet()) {
            System.out.println(e.getKey());
        }
        m.get(5);
        System.out.println("-----------------");
        //1
        //2
        //3
        //5
        for (Map.Entry e : m.entrySet()) {
            System.out.println(e.getKey());
        }
    }
}
