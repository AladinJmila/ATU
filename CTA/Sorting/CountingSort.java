// It assumes that we have an array of integers that range from 0 to K. To sort such array, we need to figure out how many times each items appears.
// So instead of using comparison we COUNT the occurences of each item in the input array and use that to sort the data.

// Best case:  Array already sorted
// Worst case: Array sorted in reverse
// TIME COMPLEXITIY ANALYSIS
//                     Worst    
// -----------------+-----------
// populate counts |    O(n)   
//  iterate counts |    O(K)   
//          Total  |    O(n)   
//                    Linear  
//          Space  |    O(K) 

// => time memory trade off
// all values must be positive integers
// ideally they are close in range (no item that is equal to 1 million)

import java.util.Arrays;

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
