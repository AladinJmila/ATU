// We divide our array in buckets. How many buckets do we need? There is no magic number... The more buckets we have, the more memory we need to allocate, but we can sort each bucket in less time because we'll have fewer items in each bucket.

// TIME COMPLEXITIY ANALYSIS
//                     Best         Worst
// -----------------+-----------+----------
//     Distribution |    O(n)   |   O(n)    -> put them in separate buckets
//iterating buckets |    O(k)   |   O(k)
//           Sorting|    O(1)   |  O(n^2)
//             Total|  O(n + k) |  O(n^2)
//                     Linear    Quadratic
//             Space|  O(n + k) |  O(n^2)
// k in the number of buckets
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSort {
  public void sort(int[] array, int numberOfBuckets) {
    var buckets = createBuckets(array, numberOfBuckets);
    var i = 0;
    for (var bucket: buckets) {
      Collections.sort(bucket);
      for (var item: bucket)
      array[i++] = item;
    }
  }

  private List<List<Integer>> createBuckets (int[] array, int numberOfBuckets) {
    List<List<Integer>> buckets = new ArrayList<>();
    for (var i = 0; i < numberOfBuckets; i++)
      buckets.add(new ArrayList<>());

    for (var item: array)
      buckets.get(item / numberOfBuckets).add(item);

      return buckets;
  }
}
