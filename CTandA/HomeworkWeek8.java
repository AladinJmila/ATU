
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


  public static void main (String[] args) {
    HomeworkWeek8 HW8 = new HomeworkWeek8();

    boolean result = HW8.linearSearchRecursion(new int[]{0,3,4,2,8,1,9}, 4, 0);
    boolean result2 = HW8.linearSearchRecursion(new int[]{0,3,4,2,8,1,9}, 12, 0);
    boolean result3 = HW8.linearSearchRecursion(new int[]{0,3,4,2,8,1,9}, 9, 0);
    System.out.println(result);
    System.out.println(result2);
    System.out.println(result3);
  }
}