
public class HomeworkWeek8 {

  public String reverseStringWithIndex(String str, int index) {
    if (index >= (str.length() / 2)) return str;
  
    String workString = str.substring(index + 1, (str.length() - index - 1));
    String firstPart = str.substring(0, index);
    String secondPart = str.substring(str.length() - index , str.length());
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

  public static void main (String[] args) {
    HomeworkWeek8 HW8 = new HomeworkWeek8();

    int result = HW8.countSubstring("he and herself", "he");
    int result2 = HW8.countSubstring("I love CTA, CTA is the best", "CTA");
    int result3 = HW8.countSubstring("he and herself", "Alan");

    System.out.println(result);
    System.out.println(result2);
    System.out.println(result3);
 
  }
}