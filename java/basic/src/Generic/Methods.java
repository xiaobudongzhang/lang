package Generic;
//泛型方法
public class Methods {
    public <T> void f(T x){
        System.out.println(x.getClass().getName());
    }

    public static void main(String[] args){
        Methods gm = new Methods();
        gm.f("");
        gm.f(1);
        /**
         * output:
         * java.lang.String
         * java.lang.Integer
         */
    }
}
