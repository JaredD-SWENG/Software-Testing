package Lab2.Lab2.src.Part2;

/**
 * Part 2 of Lab2
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
public class MyProg {
    public static void main(String[] args) {
        Part2.MyArrayList<Part2.Student> mal = new Part2.MyArrayList<>();
        mal.insertFirst(new Part2.Student(1, "John"));
        mal.insertFirst(new Part2.Student(2, "Mary"));
        mal.insertLast(new Part2.Student(3, "Mike"));
        mal.show();
        System.out.println();
        mal.deleteLast();
        mal.show();
        mal.deleteFirst();
        System.out.println();
        mal.show();

    }
}
