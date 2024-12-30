package ie.atu.sw;

import java.util.List;

/**
 * A utility class that implements the QuickSort algorithm for sorting lists
 * of double arrays.
 * 
 * This implementation was informed by material from codewithmosh.com
 */

public final class QuickSort {
	private QuickSort() {
	}

	/**
	 * Sorts a list of double arrays using the QuickSort algorithm.
	 * The sorting is based on the second element (index 1) of each double array.
	 *
	 * @param list the list of double arrays to be sorted
	 */
	public static void sort(List<double[]> list) {
		sort(list, 0, list.size() - 1);
	}

	/**
	 * Recursive helper method that implements the QuickSort algorithm.
	 * 
	 * @param list  the list of double arrays to be sorted
	 * @param start the starting index of the partition to sort
	 * @param end   the ending index of the partition to sort
	 */
	private static void sort(List<double[]> list, int start, int end) {
		// Base case: if there is nothing left to partition, return
		if (start >= end)
			return;

		// Partition the array and get the index of the pivot element in its correct
		// position
		int boundary = partition(list, start, end);

		// Recursively sort the left partition
		sort(list, start, boundary - 1);
		// Recursively sort the right partition
		sort(list, boundary + 1, end);
	}

	/**
	 * Partitions the list around a pivot element.
	 * The pivot is chosen as the last element of the partition.
	 * 
	 * @param list  the list of double arrays to be partitioned
	 * @param start the starting index of the partition
	 * @param end   the ending index of the partition
	 * @return the final position of the pivot element
	 */
	private static int partition(List<double[]> list, int start, int end) {
		// Set the pivot element as the last element in the current partition
		var pivot = list.get(end)[1];
		// Initialize the boundary index to track where the partitioning edge is
		int boundary = start - 1;

		for (int i = start; i <= end; i++) {
			// If the current element is less than or equal the pivot, move it to the left
			// side
			if (list.get(i)[1] <= pivot) {
				// Increment boundary and swap current element with the element at boundary
				swap(list, i, ++boundary);
			}
		}

		// Return final boundary index that represents the pivot at its correct position
		return boundary;
	}

	/**
	 * Swaps two elements in the list.
	 * 
	 * @param list   the list containing the elements to swap
	 * @param index1 the index of the first element
	 * @param index2 the index of the second element
	 */
	private static void swap(List<double[]> list, int index1, int index2) {
		var temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}

}
