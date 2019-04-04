package Generic;

//测试hash>>> 16
public class HashTest {

    public static void main(String args[]){
        String test = "hello";
        int hashInt = test.hashCode();
        System.out.println(Integer.toBinaryString(hashInt) );

        System.out.println(Integer.toBinaryString(hashInt >>> 16));

        System.out.println(Integer.toBinaryString(hashInt ^ (hashInt >>> 16)));
        //静态方法使用方式
        System.out.println(HashTest.hash(test));
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
