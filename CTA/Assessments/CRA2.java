import java.util.Arrays;

public class CRA2 {
  
  public String encrypt2(String plainText, int offset){
    String cipher = "";
    char[] arr = plainText.toCharArray();

    for (int i = 0; i < arr.length; i++) {
      int numericalVal = (int) arr[i];

      if (numericalVal == 32) {
        cipher += "";
      } else if (Character.isUpperCase(arr[i])) {
        cipher += (char) (((numericalVal + offset - 65) % 26) + 65);
      } else {
        cipher += (char) (((numericalVal + offset - 97) % 26) + 97);
      }
    }

    return cipher;
  }

  // input: two sorted arrays in increasing order, outer is longer than inner
  // output: true if all the numbers in inner appear in outer
  // requirment: only one linear pass to find the solution


  // proposal:
  // how can I ustilize the fact that they are both sorted?
  // I should pass itirate through them at the same time without getting out of bound.
  // so if end of inner is still bigger than the current item we need to go futher, otherwise we return
  // 
  public boolean linearIn(int[] outer, int[] inner) {

    int innerCounter = 0;
    boolean allAppear = true;
    for (int i = 0; i < outer.length; i++) {

      if (innerCounter >= inner.length) return allAppear;
      
      if (outer[i] < inner[innerCounter]) {
        continue;
      }
       else if (outer[i] == inner[innerCounter]) {
        innerCounter++;
      } 
      else {
        allAppear = false;
      }
    }

    return allAppear;
  }

  // input: an int n
  // output: an int array with a specific patter

  // create the empty array with the known length n(n+1)/2
  // iterate and in each iteration add element that ar bewteen 1 and i

  public int[] seriesUp2(int n) {
    int[] output = new int[n*(n + 1) / 2];

    int step = 1;
    for (int i = 0; i < output.length; i += step) {
      for (int j = 1; j <= n; j++) {
        output[i - j] = j;

      }
    }

    return null;
  };

  // at index 0 I put 1
  // at index 1 I put 1, 2
  // at index 3 I put 1, 2, 3
  public int[] seriesUp(int n) {
    int[] output = new int[n*(n + 1) / 2];
    int index = 0;

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= i; j++) {
       output[index] = j;
       index++;
      }
    }

    return output;
  };

  public static void main (String[]args) {
    CRA2  exam = new CRA2();
    int[] result1 = exam.seriesUp(4);
    int[] result2 = exam.seriesUp(2);
    int[] result3 = exam.seriesUp(0);
 

    System.out.println(Arrays.toString(result1));
    System.out.println(Arrays.toString(result2));
    System.out.println(Arrays.toString(result3));


  }
}
