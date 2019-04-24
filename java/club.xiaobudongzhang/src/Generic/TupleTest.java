package Generic;

public class TupleTest{
    public static Tuple<String, Integer> f(){
        return new Tuple<>("hi", 47);
    }

    public static void main(String args[]){
        Tuple<String,Integer> ttsi = f();
        System.out.println(ttsi);
    }
}