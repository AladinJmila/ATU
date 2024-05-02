// This is a DEVIDE AND CONQUER algorithm
// The idea of merge sort it to break down an array into smaller and smaller arrays, sort those then merge them back to build a completely sorted array.
// For this algoritm, we need to allocate addtional space for it to run where we save these sub arrays.

// Best case:  Array already sorted
// Worst case: Array sorted in reverse
// TIME COMPLEXITIY ANALYSIS
//                Best         Worst
// ------------+-----------+----------
//    Dividing |  O(log n) | O(log n)
// Comparisons |    O(n)   |   O(n)
//      Total  |O(n log n) | O(n log n)
//      Space  |    O(n)   |    O(n)     

public class MergeSort {
  public void sort(int[] array) {
    if (array.length < 2) 
      return;

    var middle = array.length / 2;

    int[] left = new int[middle];
    for (var i = 0; i < middle; i++) 
      left[i] = array[i];

    int[] right = new int[array.length - middle];
    for (var i = middle; i < array.length; i++)
      right[i - middle] = array[i];

    sort(left);
    sort(right);

    merge(left, right, array);
  }

  private void merge(int[] left, int[] right, int[] result) {
    int i = 0, j = 0, k = 0;

    while( i < left.length && j < right.length) {
      if (left[i] <= right[j]) 
        result[k++] = left[i++];
      else
        result[k++] = right[j++];
    }

    while (i < left.length) 
      result[k++] = left[i++];

    while (j < right.length)
      result[k++] = right[j++];
  }
}
