// It's fairly efficient algorithm and is built-in into most programming languages.
// Unlike Merge Sort, it doesn't require additional space, it can sort and array in-place
// The way it works is: start by selecting an item that we call the pivot, then we rearrange the items such that all items smaller than the pivot are on the left, and the item that are greater are on the right side of the pivot.This is called "Partioning". After this happens, the pivot is placed in the right position

// Best case:  Array already sorted
// Worst case: Array sorted in reverse
// TIME COMPLEXITIY ANALYSIS
//                Best         Worst
// ------------+-----------+----------
//Partitioning |    O(n)   |   O(n)
//   # of time | O(log n)  |   O(n)
//      Total  |O(n log n) |  O(n^2)
//      Space  | O(log n)  |   O(n)

public class QuickSort {
  public void sort(int[] array) {
    sort(array, 0, array.length - 1);
  }

  private void sort(int[] array, int start, int end) {
    if (start >= end) 
      return;

    var boundary = partition(array, start, end);
    sort(array, start, boundary - 1);
    sort(array, boundary + 1, end);
  }

  private int partition(int[] array, int start, int end) {
    var pivot = array[end];
    var boundary = start - 1;
    for (var i = start; i <= end; i++)
      if (array[i] <= pivot)
        swap(array, i, ++boundary);

    return boundary;
  }

  private void swap (int[] array, int index1, int index2) {
    var temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}
