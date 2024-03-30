import java.util.Arrays;

public class CodeRunner {

  public int add(int num1, int num2) {
    return num1 + num2;
  }

  public int[] onlyEvens(int[] arr) {
    int[] workArray = new int[arr.length];
    int counter = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] % 2 == 0) {
        workArray[counter] = arr[i];
        counter++;
      };
    }

    int[] evens = new int[counter];
    for (int i = 0; i < counter; i++) {
      evens[i] = workArray[i];
    }

    return evens;
  }

  // input: array of numbers sorted in increasing order
  // return: boolean
  // true if the array contains 3 (THREE) ADJACENT scores that differ from each other by AT MOST
  // the difference shoud be the SAME for both comparisons
  // MAN... READ TO THE END, BE MEDITATIVE AND PATIENT!!!
  // DON'T BE FANCY WITH SYNTAX MAKING THIGS SHORTER. WRTIE EVERYTHING VERBOSE!!
  
  // need a counter that will not be bigger than 3 and will be reset everytime it fails to reach 3
  // iterate through the array and compare that the differance between arr[i + 1] - arr[i] <= 2 AND arr[i + 1] - arr[i] >= 1
  // If true we increment the counter and move to the next one, else we reset the counter
  // If the counter ever reach 3 then we return true, otherwise at the end we always return false


  public boolean scoreDiff(int[] arr){
    int counter1 = 0;
    int counter2 = 0;

    for (int i = 0; i < arr.length - 1; i++) {
      if ((arr[i + 1] - arr[i]) == 1) {
        counter1++;
        counter2 = 0;
      } else if ((arr[i + 1] - arr[i]) == 2) {
        counter2++;
        counter1 = 0;
      } else {
        counter1 = 0; 
        counter2 = 0;
      }
      if (counter1 == 2 || counter2 == 2) return true;
    }

    return false;
  }


  public static void main(String[]args) {
    CodeRunner CR = new CodeRunner();
    // int[] data = new int[]{2,4,6};
    // int[] data = new int[]{2,3,5,8};
    // int[] data = new int[]{12,13,15,17,21,30};
    int[] data = new int[]{12,24,27,29,33};
    // int result = CR.add(3, 7);
    // int[] result = CR.onlyEvens(data);
    // System.out.println(Arrays.toString(result));
    boolean result = CR.scoreDiff(data);
    System.out.println(result);
  }
  
}
