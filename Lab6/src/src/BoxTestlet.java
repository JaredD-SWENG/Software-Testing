public class BoxTestlet implements TestletIF {
    Box b = new Box();

    @Override
    public void runTest(MyJUnit myJUnit) {
        // [1, 10] : Box
        // <1, 5, 5>, <2, 5, 5>, <5, 5, 5>  , <9, 5, 5>, <10, 5, 5>
        // <5, 1, 5>, <5, 2, 5>,            , <5, 9, 5>, <5, 10, 5>
        // <5, 5, 1>, <5, 5, 2>,            , <5, 5, 9>, <5, 5, 10>

        myJUnit.checkEqual(b.getVolume(1,5,5), 25);
        myJUnit.checkEqual(b.getVolume(2,5,5), 50);
        myJUnit.checkEqual(b.getVolume(5,5,5), 125);
        myJUnit.checkEqual(b.getVolume(9,5,5), 225);
        myJUnit.checkEqual(b.getVolume(10,5,5), 250);

        myJUnit.checkEqual(b.getVolume(5,1,5), 25);
        myJUnit.checkEqual(b.getVolume(5,2,5), 50);
        myJUnit.checkEqual(b.getVolume(5,9,5), 225);
        myJUnit.checkEqual(b.getVolume(5,10,5), 250);

        myJUnit.checkEqual(b.getVolume(5,5,1), 25);
        myJUnit.checkEqual(b.getVolume(5,5,2), 50);
        myJUnit.checkEqual(b.getVolume(5,5,9), 225);
        myJUnit.checkEqual(b.getVolume(5,5,10), 250);
    }
}