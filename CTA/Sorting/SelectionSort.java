// We need muliple passes to sort an array
// In each pass, we should find the next smallest item and move it to the correct position in the sorted part
// The reason it's called selection sort, is that in each pass we find, or select the next minimum value and move it to the right place

// Best case:  Array already sorted
// Worst case: Array sorted in reverse
// TIME COMPLEXITIY ANALYSIS
//                Best         Worst
// ------------+-----------+----------
//      Passes |    O(n)   |   O(n)
// Comparisons |    O(n)   |   O(n)
//      Total  |   O(n^2)  |  O(n^2)
//               Quadratic   Quadratic
// => Very slow algorithm

public class SelectionSort implements ArraySorter {
  public void sort (int[] array) {
    for (var i = 0; i < array.length; i++) {
      var minIndex = i;
      for (var j = i; j < array.length; j++) {
        if (array[j] < array[minIndex])
          minIndex = j;
        swap(array, minIndex, i);
      }
    }
  }

   private void swap (int[] array, int index1, int index2) {
    var temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}
