package boilerPackage.testPackage;

import javax.swing.*;

public class Book extends JFrame implements testInterface, anotherI {
    private static String title = "TEST";
    private String author;
    private static int counter = 0;
    private static final long TIMER = 250;

    public Book(String title, String author) {
        Book.title = title;
        this.author = author;
        Test t = new Test();
        t.m1();
        test();
    }

    public static void main(String[] args) throws Exception {
        String myString = "HI!!!";
        System.out.println(myString);
        System.out.println("TEST");
        Thread.sleep(TIMER);
        System.out.println("TEST2");
        Thread.sleep(TIMER);
        System.out.println("TEST3");
        new Book("T","Tt");
    }

    @Override
    public void test() {

    }
}
