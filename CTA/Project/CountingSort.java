/*
 * This implementation was informed by material from codewithmosh.com
 */

public class CountingSort implements ArraySorter {
  public void sort(int[] array) {
    // Find the max value in the array to determine the legth of the counting array  
    int max = array[0];
    for (int num : array) {
        if (num > max) {
            max = num;
        }
    }

    // Create the counting array
    int[] counts = new int[max + 1];

    // Count the occurences of each element in the input array
    for (var item: array) {
      counts[item]++;
    }

    // Reconstruct the sorted array using the counts from the counting array
    var k = 0; // Track the position in the origianl array
    for (var i = 0; i < counts.length; i++) {
      // Iterate through each count of the current element i in counting array
      for (var j = 0; j < counts[i]; j++) {
        // Place the current element i back into the original array
        array[k++] = i;
      }
    }
  }
}
