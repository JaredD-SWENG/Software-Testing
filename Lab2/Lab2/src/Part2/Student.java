package Part2;

/**
 * Part 2 of Lab2
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
public class Student {
    private final int index;
    private final String name;

    /**
     * Arbitrary Student Constructor
     * @param index student index
     * @param name student name
     */
    public Student(int index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * Used for {@link MyArrayList#show()}
     * @return Index, Name
     */
    public String toString() {
        return index + ", " + name;
    }
}