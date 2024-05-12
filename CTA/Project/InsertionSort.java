/*
 * This implementation is inspired by the "Data Structures & Algorithms" course
 * by Mosh Hamedani on codewithmosh.com.
 * Adaptations and comments were added for clarity and understanding.
 */

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
