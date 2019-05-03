package Generic;

import java.io.*;

public class SerializableDemo {


    public static void main(String[] args) throws IOException, ClassNotFoundException{
        File f = new File("D:\\tmp\\obj.txt");
        writeObject(f);
        readObject(f);
    }

    public static void writeObject(File f) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(f);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(new SerializableDemo.User("root", "123456"));
        objectOutputStream.close();
    }

    public static void readObject(File f) throws IOException, ClassNotFoundException {
        FileInputStream inputStream = new FileInputStream(f);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        User user = (User)objectInputStream.readObject();
        System.out.println(user);
    }
    static class User implements Serializable{
        String uid;
        String pwd;

        public User(String _uid, String _pwd) {
            this.uid = _uid;
            this.pwd = _pwd;
        }
        @Override
        public String toString() {
            return "uid:" + this.uid + "pwd:" + this.pwd;
        }
    }
}
