import java.util.Arrays;

public class HomeworkWeek8 {

  // how will it stop? => stop condition
  // how will it make progress?

  // for reversing a string ask yourself: What kind of string that can't be reversed?
  // The answer is: and empty string or a 1 charcter string => stop condition
  // how do we make progress then
  // The answer is: by chopping if off so it's smaller and smaller to meet that stop condition => progress

  public String reverseStringWithIndex(String str, int index) {
    if (index >= (str.length() / 2)) return str;
  
    String workString = str.substring(index + 1, (str.length() - index - 1));
    String firstPart = str.substring(0, index);
    String secondPart = str.substring((str.length() - index) , str.length());
    String endToFlip = str.substring((str.length() - 1 - index), (str.length() - index));
    String startToFlip = str.substring(index, index + 1);

    workString = firstPart + endToFlip + workString;
    workString = workString + startToFlip + secondPart;
    index++;
    return reverseStringWithIndex(workString, index);
  }

  public String reverse (String forwards) {
    if (forwards.length() == 0) return "";
    if (forwards.length() == 1) return forwards;

    String tail = forwards.substring(forwards.length() - 1);
    String rest = forwards.substring(0, forwards.length() - 1);
    

    return tail + reverse(rest);
  }

  // better version
  public String reverseBetter(String forwards) {
    if (forwards.length() <= 1) {
        return forwards; // Base case: return the string as is if length is 0 or 1
    } else {
        char tail = forwards.charAt(forwards.length() - 1); // Get the last character
        String rest = forwards.substring(0, forwards.length() - 1); // Get the substring excluding the last character
        return reverse(rest) + tail; // Recursively reverse the substring and append the last character
    }
}

  public boolean linearSearchRecursion(int[] arr, int target, int index) {
    if (index == arr.length) return false;
    if (arr[index] == target) return true;
    // As GTP explained, prefer using the ++index and pass it as an argument 
    // over index++ in the body of the function so you won't miss an iteration
    return linearSearchRecursion(arr, target, ++index);
  }

  public int sum(int[] nums, int index) {
    if (index == nums.length) return 0;
    return nums[index] + sum(nums, ++index);
  }

  // stop when string is empty or its of size 1
  // move forwards to the base case by chopping off both sides of the string
  public boolean isPalindrome(String str) {
    // if (str.length() == 1 || str.length() == 0) return true;
    if (str.length() <= 1) return true; // smarter way of doing it
    String sub = str.substring(1, str.length() - 1);
    return (str.charAt(0) == str.charAt(str.length() - 1)) && isPalindrome(sub); 
  }

  public int countSubstring(String str, String sub) {
    if (str.length() == sub.length()) return 0;
   
    String tempSub = str.substring(0, sub.length());
    if (tempSub.equalsIgnoreCase(sub)) {
      return 1 + countSubstring(str.substring(1), sub);
    }

    return countSubstring(str.substring(1), sub);
  }

  // clearer version
  public int countSubstringClearer(String str, String sub) {
    int strLength = str.length();
    int subLength = sub.length();
    
    // Base case: If str length is less than sub length, no more possible matches
    if (strLength < subLength) {
        return 0;
    }
    
    // Check if the current substring matches sub (ignoring case)
    String tempSub = str.substring(0, subLength);
    int count = tempSub.equalsIgnoreCase(sub) ? 1 : 0;
    
    // Recursively count occurrences of sub in the remainder of str
    return count + countSubstring(str.substring(1), sub);
}

  public int fibonacci (int n) {
    if (n == 1 || n == 0) return 1;

    return fibonacci(n - 1) + fibonacci(n - 2);
  }

  public int power (int n, int pow) {
    if (pow == 1) return n;

    return power(n, pow - 1) * n ;
  }

  public int[] revInPlace (int[] arr, int index) {
    if (index >= arr.length / 2) return arr;
   
    int temp = arr[index];
    arr[index] = arr[arr.length - 1 - index];
    arr[arr.length - 1 - index] = temp;
    
    return revInPlace(arr, ++index);
  }

  public static void main (String[] args) {
    HomeworkWeek8 HW8 = new HomeworkWeek8();

    int[] result = HW8.revInPlace(new int[]{1,2,3,4,5}, 0);
  
    System.out.println(Arrays.toString(result));
 
  }
}