package ie.atu.sw;

import java.util.List;

/**
 * 
 * This implementation was informed by material from codewithmosh.com
 */

public final class QuickSort {
	private QuickSort() {
	}

	// Method that takes only the array to sort as input
	public static void sort(List<double[]> list) {
		sort(list, 0, list.size() - 1);
	}

	// Method that adds the partition range needed for recursive call

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

	// Method to partition the array around a pivot element
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

	private static void swap(List<double[]> list, int index1, int index2) {
		var temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}

}
