/*
 * This implementation is inspired by the "Data Structures & Algorithms" course
 * by Mosh Hamedani on codewithmosh.com.
 * Adaptations and comments were added for clarity and understanding.
 */

public class BubbleSort implements ArraySorter {
  public void sort(int[] array) {
    // The variable is used for optimization.
    boolean isSorted;

    for (var i = 0; i < array.length; i++) {
      // Assume that the array is sorted, so we can return early
      isSorted = true;
      
      // Inner loop bubbles the largest element to the end
      // The loop goes only up to array.length - i because the last item is in the correct position
      for (var j = 1; j < array.length - i; j++) {
        // Check if adjacent elements are out of order
        if (array[j] < array[j - 1]) {
          // Swap the elements
          swap(array, j, j - 1);
          // If a swap occurs, it means the array is not fully sorted
          isSorted = false;
        }
      }

      // If no swaps were made in this pass, it means the array is already sorted
      if (isSorted)
        return; // Exit early if the array is alreay sorted
    }
  }

  // Helper method to swap elements in the array
  private void swap (int[] array, int index1, int index2) {
    var temp = array[index1];
    array[index1] = array[index2];
    array[index2] = temp;
  }
}