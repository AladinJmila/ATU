package ie.atu.sw;

/*
 * This implementation was informed by material from codewithmosh.com
 */

public class QuickSort {
	// Public method that takes only the array to sort as input
	public void sort(double[][] array) {
		sort(array, 0, array.length - 1);
	}
	
	// Private method that adds the partition range needed for recursive call
	private void sort(double[][] array, int start, int end) {
		// Base case: if there is nothing left to partition, return
		if (start >= end)
			return;
		
		// Partition the array and get the index of the pivot element in its correct position
		int boundary = partition(array, start, end);
		
		// Recursively sort the left partition
		sort(array, start, boundary - 1);
		// Recursively sort the right partition
		sort(array, boundary + 1, end);
	}
	
	// Private method to partition the array around a pivot element
	private int partition(double[][] array, int start, int end) {
		// Set the pivot element as the last element in the current partition
		var pivot = array[end][1];
		// Initialize the boundary index to track where the partitioning edge is
		int boundary = start - 1;
		
		for (int i = start; i <=end; i++) {
			// If the current element is less than or equal the pivot, move it to the left side
			if (array[i][1] <= pivot) {
				// Increment boundary and swap current element with the element at boundary
				swap(array, i, ++boundary);
			}
		}
		
		// Return final boundary index that represents the pivot at its correct position
		return boundary;
	}
	
	private void swap(double[][] array, int index1, int index2) {
		var temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

}
