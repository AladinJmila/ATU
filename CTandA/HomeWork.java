import java.util.Arrays;

public class HomeWork {
  /* exercice 2 */
  public String concat(String str1, String str2) {
    String newStr = "";
    char[] arr1 = str1.toCharArray();
    char[] arr2 = str2.toCharArray();

    for (int i = 0; i < arr2.length; i++) {
      newStr += arr1[i];
      newStr += arr2[i];

      if (i == arr2.length - 1) {
        for (int j = i + 1; j < arr1.length; j++) {
          newStr += arr1[j];
        }
      }
    }
    return newStr;
  }

   public String merge(String str1, String str2) {
    String newStr = "";
    
    for (int i = 0; i < str2.length(); i++) {
      newStr += str1.charAt(i) + "" + str2.charAt(i);
    }

    for (int i = str2.length(); i < str1.length(); i++) {
      newStr += str1.charAt(i);
    }

    return newStr;
  }

  /* exercice 3 */
  public boolean doubleX(String str) {
    for (int i = 0; i < str.length() - 1; i++) {
      if (Character.toLowerCase(str.charAt(i)) == 'x' && Character.toLowerCase(str.charAt(i + 1)) == 'x') {
        return true;
      } else if (Character.toLowerCase(str.charAt(i)) == 'x') {
        return false;
      }
    }

    return false;
  }

  /* exercice 4 */
  // Input: array of INTS of ODD length
  // output: new arry of length 3 containing the elements from the middle of the array
  // Extra info: The array length will be at least 3

  // Find the middled index then grab +1 and -1

  public int[] middle(int[] arr) {
    int[] newArr = new int[3];
    int middleIndex = arr.length / 2;

    newArr[0] = arr[middleIndex - 1];
    newArr[1] = arr[middleIndex];
    newArr[2] = arr[middleIndex + 1];
    
    return newArr;
  }

  /* exercice 5 */
  public int[] change(int[]arr) {
    int[] newArr = new int[3];
    int biggestIndex = 0;
    if (arr[2] > arr[0]) biggestIndex = 2;

    for (int i = 0; i < arr.length; i++) {
      newArr[i] = arr[biggestIndex];
    }

    return newArr;
  }

  /* exercice 6 */
  public boolean check(int[]arr) {
    if (arr[0] == 2 || arr[1] == 2) return true;
    if (arr[0] == 3 || arr[1] == 3) return true;

    return false;
  }
  
  public static void main(String[] args) {
    int[] data = new int[]{2,4};

    HomeWork HM = new HomeWork();
    boolean result = HM.check(data);
    System.out.println(result);
  }
}
