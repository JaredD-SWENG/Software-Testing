package testPackage;

import javax.swing.*;

public class Book extends JFrame implements testInterface, anotherI{
    private static String title = "TEST";
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public static void main(String[] args) throws Exception {
        String myString = "HI!!!";
        System.out.println(myString);
    }

    @Override
    public void test() {

    }
}
