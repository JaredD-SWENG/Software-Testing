public class RectangleTestlet implements TestletIF {
    Rectangle r = new Rectangle();

    @Override
    public void runTest(MyJUnit myJUnit) {
        // [1, 10] : Rectangle
        // <1, 5>, <2, 5>, <5, 5>, <9, 5>, <10, 5>
        // <5, 1>, <5, 2>,       , <5, 9>, <5, 10>

        myJUnit.checkEqual(r.getArea(1,5),5);
        myJUnit.checkEqual(r.getArea(2,5),10);
        myJUnit.checkEqual(r.getArea(5,5),25);
        myJUnit.checkEqual(r.getArea(9,5),45);
        myJUnit.checkEqual(r.getArea(10,5),50);

        myJUnit.checkEqual(r.getArea(5,1),5);
        myJUnit.checkEqual(r.getArea(5,2),10);
        myJUnit.checkEqual(r.getArea(5,9),45);
        myJUnit.checkEqual(r.getArea(5,10),50);
    }
}