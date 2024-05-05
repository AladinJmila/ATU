import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@FunctionalInterface
interface ArraySorter {
  void sort(int[] array);
}

public class Main {
  private ThreadLocalRandom random;
  final static int MAX_VALUE = 101;

  public Main(){
    random = ThreadLocalRandom.current();
  }
  
  private int[] genRandomArray(int arrayLength) {
    int [] array = new int[arrayLength];
    
    for (int i = 0; i < arrayLength; i++) {
      array[i] = random.nextInt(1, MAX_VALUE);
    }
    
    return array;
  }

  private int[] copyArray(int[] input) {
    int [] output = new int[input.length];
    System.arraycopy(input, 0, output, 0, input.length);
    return output;
  }

  public String benchmark(int reps, int length, ArraySorter sorter) {
    double total = 0;
    int[] array = genRandomArray(length);

    for (int i = 0; i < reps; i++) {
      int[] cloned = copyArray(array);
    
      long startTime = System.nanoTime();
      sorter.sort(cloned);
      long endTime = System.nanoTime();

      long timeElapsed = endTime - startTime;
      double elapsedMillis = timeElapsed / 1000000.0;
      total += elapsedMillis;
    }

    double average = total / reps;
    // return String.format("%.3f", total) ;
    return String.format("%.3f", average) ;
  }
  public static void main(String[] args) {
    Main m = new Main();
    int [] inputSizes = new int[]{100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750, 10000};
    String [] selectionortResults = new String[inputSizes.length];
    String [] bubbleSortResults = new String[inputSizes.length];
    String [] insertionSortResults = new String[inputSizes.length];
    String [] countingSortResults = new String[inputSizes.length];
    String [] quickSortResults = new String[inputSizes.length];
    
    for (int i = 0; i < inputSizes.length; i++) {
      bubbleSortResults[i] = m.benchmark(10, inputSizes[i], new BubbleSort());
      selectionortResults[i] = m.benchmark(10,inputSizes[i], new SelectionSort());
      insertionSortResults[i] = m.benchmark(10, inputSizes[i], new InsertionSort());
      quickSortResults[i] = m.benchmark(10,inputSizes[i], new QuickSort());
      countingSortResults[i] = m.benchmark(10,inputSizes[i], new CountingSort());
    }

    System.out.println(Arrays.toString(bubbleSortResults));
    System.out.println(Arrays.toString(selectionortResults));
    System.out.println(Arrays.toString(insertionSortResults));
    System.out.println(Arrays.toString(quickSortResults));
    System.out.println(Arrays.toString(countingSortResults));
  } 
}
