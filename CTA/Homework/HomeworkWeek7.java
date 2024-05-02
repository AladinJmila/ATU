import java.util.Arrays;

public class HomeworkWeek7 {
  /* code runner 2 */
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

  // better version
  public boolean scoreDiffBetter(int[] arr) {
    if (arr.length < 3) {
        return false; // Array must have at least 3 elements to find three adjacent scores
    }

    int consecutiveCount = 1; // To track the count of consecutive scores
    int prevDiff = arr[1] - arr[0]; // Initialize with the difference between the first two elements

    for (int i = 1; i < arr.length - 1; i++) {
        int currentDiff = arr[i + 1] - arr[i];
        
        if (currentDiff <= 2 && currentDiff == prevDiff) {
            consecutiveCount++;
            if (consecutiveCount == 3) {
                return true; // Found three consecutive scores with valid differences
            }
        } else {
            consecutiveCount = 1; // Reset the count if the sequence breaks
        }

        prevDiff = currentDiff; // Update the previous difference
    }

    return false; // No three consecutive scores found
}

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

  // better version
  public String mergeBetter(String str1, String str2) {
    StringBuilder merged = new StringBuilder();

    int len2 = str2.length();
    for (int i = 0; i < len2; i++) {
        merged.append(str1.charAt(i)).append(str2.charAt(i));
    }

    // Append the remaining characters from str1 (if any)
    merged.append(str1.substring(len2));

    return merged.toString();
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

  // better version
  public boolean doubleXBetter(String str) {
    int firstXIndex = str.indexOf('x');
    
    // Check if the first 'x' exists and is not the last character in the string
    if (firstXIndex != -1 && firstXIndex < str.length() - 1) {
        // Check if the next character after the first 'x' is also 'x'
        return str.charAt(firstXIndex + 1) == 'x';
    }
    
    return false; // No 'x' found or first 'x' is the last character
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

  // better version
  public int[] changeBetter(int[] arr) {
    int maxValue = Math.max(arr[0], arr[2]); // Determine the larger value between first and last element
    
    // Set all elements in the original array to the determined maxValue
    arr[0] = maxValue;
    arr[1] = maxValue;
    arr[2] = maxValue;
    
    return arr; // Return the modified input array
}

  /* exercice 6 */
  public boolean check(int[]arr) {
    if (arr[0] == 2 || arr[1] == 2) return true;
    if (arr[0] == 3 || arr[1] == 3) return true;

    return false;
  }

  // better version
  public boolean checkBetter(int[] arr) {
    // Check if either arr[0] or arr[1] is equal to 2 or 3
    return (arr[0] == 2 || arr[0] == 3 || arr[1] == 2 || arr[1] == 3);
}
  
  public static void main(String[] args) {
    int[] data = new int[]{2,4};

    HomeworkWeek7 HM = new HomeworkWeek7();
    boolean result = HM.check(data);
    System.out.println(result);
  }
}
