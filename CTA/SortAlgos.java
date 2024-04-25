import java.util.Arrays;

class SortAlgos {

  public void bubbleSort(int[] arr) {
    boolean isSorted = false;

    while (!isSorted) {
      isSorted = true;
      for (int i = 0; i < arr.length - 1 - i; i++ ) {
        if (arr[i] > arr[i + 1]) {
          int swap = arr[i];
          arr[i] = arr[i + 1];
          arr[i + 1] = swap;
          isSorted = false;
        }
      }
    }
  }
  public static void main (String[] args) {
    int[] input = {5, 8, 6, 2, 7, 9, 3, 4, 5, 1};
    SortAlgos SA = new SortAlgos();
    SA.bubbleSort(input);
    System.out.println(Arrays.toString(input));
  }
}