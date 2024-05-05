// Optimized version
// We need mulitple passes to sort an array
// In each pass, we scan the array from left to right, and if items are out of order, we swap them
// The reason it's called bubble sort,is that the big values bubble to the top/end of the array with every pass

// Best case:  Array already sorted
// Worst case: Array sorted in reverse
// TIME COMPLEXITIY ANALYSIS
//                Best         Worst
// ------------+-----------+----------
//      Passes |    O(1)   |   O(n)
// Comparisons |    O(n)   |   O(n)
//      Total  |    O(n)   |  O(n^2)
//                Linear    Quadratic

public class BubbleSort implements ArraySorter {
  public void sort(int[] array) {
    boolean isSorted;
    for (var i = 0; i < array.length; i++) {
      isSorted = true;
      for (var j = 1; j < array.length - i; j++) 
      if (array[j] < array[j - 1]) {
        swap(array, j, j - 1);
        isSorted = false;
      }
      if (isSorted)
        return;
    }
  }

  private void swap (int[] array, int index1, int index2) {
    var temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}