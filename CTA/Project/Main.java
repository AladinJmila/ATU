import java.util.concurrent.ThreadLocalRandom;

// Interface to enable reusing the benachmark method with all sorting algorithm classes
@FunctionalInterface
interface ArraySorter {
  void sort(int[] array);
}

public class Main {
  private ThreadLocalRandom random; // Random number generator
  final static int MAX_VALUE = 101; // Max random value generated

  // Intialize the random number generator
  public Main(){
    random = ThreadLocalRandom.current();
  }
  
  // Generate a random interger array for a given length
  private int[] genRandomArray(int arrayLength) {
    int [] array = new int[arrayLength];
    
    for (int i = 0; i < arrayLength; i++) {
      array[i] = random.nextInt(1, MAX_VALUE);
    }
    
    return array;
  }

  // Create a copy of a given array
  private int[] copyArray(int[] input) {
    int [] output = new int[input.length];
    System.arraycopy(input, 0, output, 0, input.length);
    return output;
  }

  // Benchmark a given sorting algorithm and return average execution time of n repitions
  public String benchmark(int reps, int length, ArraySorter sorter) {
    double total = 0;
    int[] array = genRandomArray(length);

    // Perform sorting and measure performance for each iteration
    for (int i = 0; i < reps; i++) {
      int[] cloned = copyArray(array);
    
      long startTime = System.nanoTime();
      sorter.sort(cloned);
      long endTime = System.nanoTime();

      long timeElapsed = endTime - startTime;
      double elapsedMillis = timeElapsed / 1000000.0;
      total += elapsedMillis;
    }

    // Calculate average execution time of all repetitions
    double average = total / reps;

    // Foramt and return result    
    return String.format("%.3f", average) ;
  }

  // Plot a row of a given label and array of results in a tabel format
  public void plotRow(String label, String[] array) {
    String format = "%-16s"; // Foramt for label column

    // Append format placeholder of each array item
    for (int i = 0; i < array.length; i++) {
      format += "%-8s";
    }

    // Combine label and input array into a single array
    Object[] args = new Object[array.length + 1];
    args[0] = label;
    System.arraycopy(array, 0, args, 1, array.length);
    
    // Print row using the given format and arguments
    System.out.printf(format + "%n", args);
  }

  // Overloaded method to handle interger arrays, convert them to strings and plot them
  public void plotRow(String label, int[] array) {
    String[] stringArray = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      stringArray[i] = String.valueOf(array[i]);
    }

    plotRow(label, stringArray);
  }
  public static void main(String[] args) {
    Main m = new Main();
    // Define input sizes for benchmarking
    int [] inputSizes = new int[]{100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750, 10000};

    // Initialize arrays to store the benchmark results for each algorithm
    String [] bubbleSortResults = new String[inputSizes.length];
    String [] selectionSortResults = new String[inputSizes.length];
    String [] insertionSortResults = new String[inputSizes.length];
    String [] countingSortResults = new String[inputSizes.length];
    String [] quickSortResults = new String[inputSizes.length];
    
    // Benchmark each algorithm for each input size
    for (int i = 0; i < inputSizes.length; i++) {
      bubbleSortResults[i] = m.benchmark(10, inputSizes[i], new BubbleSort());
      selectionSortResults[i] = m.benchmark(10,inputSizes[i], new SelectionSort());
      insertionSortResults[i] = m.benchmark(10, inputSizes[i], new InsertionSort());
      quickSortResults[i] = m.benchmark(10,inputSizes[i], new QuickSort());
      countingSortResults[i] = m.benchmark(10,inputSizes[i], new CountingSort());
    }
  
    // Plot result of benchmarking in a table format
    System.out.println();
    m.plotRow("Size", inputSizes);
    m.plotRow("Bubble Sort", bubbleSortResults);
    m.plotRow("Selection Sort", selectionSortResults);
    m.plotRow("Insertion Sort", insertionSortResults);
    m.plotRow("Quick Sort", quickSortResults);
    m.plotRow("Counting Sort", countingSortResults);
    System.out.println();
  } 
}
