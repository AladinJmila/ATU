/*
 * This implementation is inspired by the "Data Structures & Algorithms" course
 * by Mosh Hamedani on codewithmosh.com.
 * Adaptations and comments were added for clarity and understanding.
 */

public class SelectionSort implements ArraySorter {
  public void sort (int[] array) {
    for (var i = 0; i < array.length; i++) {
      // Assume the current indexd has the minimum value
      var minIndex = i;

      // Find the index of the smallest element in the remaining unsorted portion
      // Start with j = i becuase the first portion of the array is already sorted
      for (var j = i; j < array.length; j++) {
        if (array[j] < array[minIndex])
        // Update the index of the smallest element
          minIndex = j;
      }

      // Swap the smallest element with the current element
      swap(array, minIndex, i);
    }
  }

   // Helper method to swap elements in the array
   private void swap (int[] array, int index1, int index2) {
    var temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}
