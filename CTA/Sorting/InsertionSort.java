// The reason it's called insertion sort, is that in each pass we take an element and insert it in the right position (like when playing cards or rami)
// Unlike the previous alogithms we're not going to do a swap. Instead, we're going to look at all the items we have have seen so for, if they are greater than the current element, we shift them to the right to open space to insert it.

// Best case:  Array already sorted
// Worst case: Array sorted in reverse
// TIME COMPLEXITIY ANALYSIS
//                Best         Worst
// ------------+-----------+----------
//      Passes |    O(n)   |   O(n)
// Comparisons |    O(1)   |   O(n)
//      Total  |    O(n)   |  O(n^2)
//                 Linear   Quadratic
// => Very slow algorithm

public class InsertionSort implements ArraySorter {
  public void sort(int[] array) {
    for (var i = 1; i < array.length; i++) {
      var current = array[i];
      var j = i - 1;
      while (j >= 0 && array[j] > current) {
        array[j + 1] = array[j];
        j--;
      }
      array[j + 1] = current;
    }
  }
}
