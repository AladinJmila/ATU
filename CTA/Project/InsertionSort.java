/*
 * This implementation was informed by material from codewithmosh.com
 */

public class InsertionSort implements ArraySorter {
  public void sort(int[] array) {
    // Iterate through each element starting from the second
    for (var i = 1; i < array.length; i++) {
      var current = array[i];
      var j = i - 1;
      // Iterate and shift all elements greater than current to the right
      while (j >= 0 && array[j] > current) {
        array[j + 1] = array[j];
        j--;
      }
      // Insert current element into its correct position
      array[j + 1] = current;
    }
  }
}
