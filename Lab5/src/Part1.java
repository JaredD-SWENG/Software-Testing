import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Part 1 of Lab5
 *
 * @author Haris Siddiqui, Jared Daniel, Yifan Liu
 */
public class Part1
{
    private static ArrayList<ArrayList<int[]>> equivalenceClasses = new ArrayList<>();
    private static ArrayList<ArrayList<int[]>> result = new ArrayList<>();

    private static ArrayList<ArrayList<ArrayList<Integer>>> randoms = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> generatedNum = new ArrayList<>();

    private static ArrayList<ArrayList<Integer>> counters = new ArrayList<>();
    private static Random rand = new Random();
    private static int variable = 0;
    public void readIn(){
        String path = "src/Eq.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] ranges = line.split(";");
                ArrayList<int[]> argumentClasses = new ArrayList<>();
                ArrayList<ArrayList<Integer>> eqCollections = new ArrayList<>();
                ArrayList<Integer> count = new ArrayList<>();
                for (String range : ranges) {
                    String[] bounds = range.split(",");
                    int lowerBound = Integer.parseInt(bounds[0].trim());
                    int upperBound = Integer.parseInt(bounds[1].trim());
                    argumentClasses.add(new int[]{lowerBound, upperBound});
                    ArrayList<Integer> collection = collectionCreate(lowerBound, upperBound);
                    eqCollections.add(collection);
                    count.add(0);
                }
                randoms.add(eqCollections);
                equivalenceClasses.add(argumentClasses);
                counters.add(count);
            }

            //144 different test cases
            //System.out.println(count);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> collectionCreate(int low, int hi){
        ArrayList<Integer> shuffledNumbers = new ArrayList<>();
        for (int i = low; i <= hi; i++) {
            shuffledNumbers.add(i);
        }
        Collections.shuffle(shuffledNumbers);
        return shuffledNumbers;
    }

    public static void generateCartesianProduct(int levelIndex, ArrayList<int[]> currentCombination) {
        if (levelIndex == equivalenceClasses.size()) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        ArrayList<int[]> currentLevel = equivalenceClasses.get(levelIndex);
        for (int[] array : currentLevel) {
            currentCombination.add(array);
            generateCartesianProduct(levelIndex + 1, currentCombination);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
    //ArrayList<int[]> product
    public static void generateCombinations(){
        ArrayList<Integer> temp = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (ArrayList<int[]> combination : result) {

            for (int[] array : combination) {
                for(ArrayList<int[]>var : equivalenceClasses) {
                    i = equivalenceClasses.indexOf(var);
                    j = var.indexOf(array);
                    if(i!=-1 && j!=-1) {
                        //System.out.println(i + " " + j);
                        break;
                    }
                }
                if(counters.get(i).get(j) == randoms.get(i).get(j).size()){
                    //int c = counters.get(i).get(j);
                    counters.get(i).set(j, 0);
                    ArrayList<Integer> newCollect = collectionCreate(array[0], array[1]);
                    randoms.get(i).set(j, newCollect);
                }
                //System.out.println(counters.get(i).get(j));
                //System.out.println(array[1]);
                int val = randoms.get(i).get(j).get(counters.get(i).get(j));

                int c = counters.get(i).get(j);
                counters.get(i).set(j, ++c);

                //int val = rand.nextInt(array[1] - array[0])+array[0];

                temp.add(val);
            }
            generatedNum.add(temp);
            temp = new ArrayList<>();
        }
    }

   /* public static boolean testExistingValues(int col, int[] array){
        int sum = 0;
        for (ArrayList<Integer> row : generatedNum) {
            sum += row.get(col);
        }

        int n = array[1] - array[0] + 1;
        int total = (n * (n + 1))/ 2;
        if(sum >= total)
            return true;
        else
            return false;
    }*/

    private static int check(int val){

        ArrayList<int[]> var = equivalenceClasses.get(variable);
        for (int i = 0; i < var.size(); i++) {
            int[] arr = var.get(i);
           // for (int j : arr) {
                //System.out.print(j+ " ");
                if(val>=arr[0] && val<=arr[1])
                    return i+1;
            //    }
            //System.out.println();
            }

        return 0; // Value not found (optional, you can handle it differently if needed).
    }


    private static int foo(ArrayList<Integer> gn){
        int sum = 0;
        for (Integer i : gn) {
            sum += check(i);
            variable ++;
        }
        variable =0;

        return sum;
    }

    public static void main(String [] args){
        Part1 p1 = new Part1();
        p1.readIn();
        generateCartesianProduct(0, new ArrayList<>());
        for(ArrayList<ArrayList<Integer>> r: randoms){
            for(ArrayList<Integer> v: r) {
                for (Integer i : v) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

        generateCombinations();
        //uncomment this code to visualize the results of the generateCartesianProduct recursive function
        /*for (ArrayList<int[]> combination : result) {
            for (int[] array : combination) {
                for (int value : array) {
                    System.out.print(value + " ");
                }
                System.out.print("| ");
            }
            System.out.println();
        }*/
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"))) {
            for (ArrayList<Integer> testCase : generatedNum) {
                int s = foo(testCase);

                for (Integer i : testCase) {
                    writer.write(i + " ");
                }
                writer.write(s + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        for (int i = 0;i < equivalenceClasses.size(); i++){
            for(int j = 0; j<equivalenceClasses.get(i).size() ; j++)
                for(int k = 0; k<equivalenceClasses.get(i).get(j).length; k++)
            System.out.print(equivalenceClasses.get(i).get(j)[k]);
            System.out.println();
        }


        for(ArrayList<Integer> i: counters){
            for(Integer j: i){
                System.out.print(j+" ");
            }
            System.out.println();
        }
*/

    }

}
