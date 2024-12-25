package Part2;

/**
 * Part 2 of Lab2
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
// ***  MyArrayList is limited to 100 elements.  ***
public class MyArrayList<E> extends java.util.ArrayList<E> {

    public MyArrayList() {
        super();
        // assert postcondition
        assert this != null;
        assert this.size() == 0: "Size is not 0";
    }

    @Override
    public int size() {
        // assert postcondition
        assert super.size()>=0:"Size is less than 0";
        assert super.size()<=100:"Size is greater than 100";
        // code
        return super.size();
    }

    // Insert e as a new first element to mal
    public void insertFirst(E e) {
        // assert precondition
        assert e!=null:"Object is null";
        assert this.size()<100:"Array full";
        // code
        super.add(0,e);
        // assert postcondition
        assert this.size()>0:"Object not inserted";
        assert super.get(0).equals(e):"Object is not first";
    }

    // Insert e as a new last element
    public void insertLast(E e) {
        // assert precondition
        assert e != null:"Object is null";
        assert this.size() < 100:"Array is full";
        // code
        super.add(this.size(), e); // ?? Why does this work
        // assert postcondition
        assert this.size() > 0:"Object not inserted";
        assert super.get(this.size()-1).equals(e):"Object is not last";
    }

    // Delete my first element
    public void deleteFirst() {
        // assert precondition
        assert this!=null:"Array is null";
        assert this.size()>0:"Array is empty";
        // code
        int previousSize = this.size();
        super.remove(0);
        // assert postcondition
        assert previousSize-1==this.size():"Object not deleted";
    }

    // Delete my last element
    public void deleteLast() {
        // assert precondition
        assert this!=null:"Array is null";
        assert this.size()>0:"Array is empty";
        // code
        int previousSize = this.size();
        super.remove(this.size()-1);
        // assert postcondition
        assert previousSize-1==this.size():"Object not deleted";
    }

    public void show() {
        for (E e : this) {
            System.out.println(e);
        }
    }
}
