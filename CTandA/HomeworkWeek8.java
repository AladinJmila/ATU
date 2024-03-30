import java.util.Arrays;

public class HomeworkWeek8 {

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

  public boolean linearSearchRecursion(int[] arr, int target, int index) {
    if (index == arr.length) return false;

    if (arr[index] == target) return true;
    index++; 

    return linearSearchRecursion(arr, target, index);
  }

  public int sum(int[] nums, int index) {
    if (index == nums.length) return 0;
    return nums[index] + sum(nums, ++index);
  }

  public boolean isPalindrome(String str) {
    if (str.length() == 1 || str.length() == 0) return true;
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