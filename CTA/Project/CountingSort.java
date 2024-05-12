/*
 * This implementation is inspired by the "Data Structures & Algorithms" course
 * by Mosh Hamedani on codewithmosh.com.
 * Adaptations and comments were added for clarity and understanding.
 */

public class CountingSort implements ArraySorter {
  public void sort(int[] array) {
    int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
    int[] counts = new int[max + 1];

    for (var item: array)
      counts[item]++;

    var k = 0;
    for (var i = 0; i < counts.length; i++) 
      for (var j = 0; j < counts[i]; j++)
        array[k++] = i;
  }
}
