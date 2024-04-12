import java.util.Arrays;
import java.util.stream.Stream; 

public class CRA1 {
  // prep question 1
  // input: a positive whole number
  // output: boolean true if the number contains digit 1

  // probably use recursion, use the moduls with 10 to get the right most digit
  // if moduls returns 1 return true, else work with what remains from division
  // base condition , if the number is less than or equals to 0 return false

  public boolean hasOne(int number){
    if (number <= 0) return false;
    int rightNum = number % 10;
    int theRest = number / 10;
    return rightNum == 1 || theRest == 1 || hasOne(theRest);
  }

  // prep question 2
  // input: a string that has duplicated ADJACENT characters
  // output: a new string with duplicated characters removed
  // recursion
  // base condition, empty or single character string
  // move towards condition by removing one char at a time

  public String removeDuplicates(String in){
    if (in.length() == 1) return in;
    if (in.length() == 0) return "";

    char one = in.charAt(0);
    char two = in.charAt(1);

    if (one == two) return removeDuplicates(in.substring(1));
    
    return one + removeDuplicates(in.substring(1));
  }

  // prep question 3
  // input: a string
  // output: a reversed string with a | between each internal character
  // recursion
  
  public String reverse(String string){
    if (string.length() == 0) return "";
    if (string.length() == 1) return string;

    char tail = string.charAt(string.length() - 1);
    return tail + "|" + reverse(string.substring(0, string.length() - 1));
  }

  // prep question 4
  // input: a string
  // output: array of each successive sub-string of legth 3!! the part at the end which is not of length 3 is cut off
  // recursion
  // base condition: if array length is less than 3 return empty array
  // work towards condition: make string shorter as we progress

  public String[] substrings(String string){
    if (string.length() < 3) return new String[]{};
    if (string.length() == 3) {
      return new String[]{string};
    } 

    return Stream.concat(Arrays.stream(new String[]{string.substring(0, 3)}), Arrays.stream(substrings(string.substring(3, string.length())))).toArray(String[]::new);
  }

  // Question 1
  boolean inTrouble(boolean catTalking, int time){
    return catTalking && (time < 6 || time > 21);
  }

  // Question 2
  // input: a number n
  String factorialBuzz(int number){
    int factorial = 1;
    for (int i = number; i > 1; i--) {
       factorial = factorial * i;
    }

    String factorialStr = Integer.toString(factorial);

    if (number % 5 == 0) return "Buzz! the factorial is " + factorialStr;
    else if (number % 3 == 0) return "Fizz! the factorial is " + factorialStr;
    else return factorialStr;
  }

  // Question 3
  // input: tea number and biscuits number
  // output: string good if both tea and biscuits are at leat 5
  // if (tea >= 5 || biscuits >=5) return good
  // if either tea or biscuits is at least doube the amount of the other retrun great
  // if (tea >= biscuits * 2 || biscuits >= tea * 2) return great
  // in all cases, if either tea or biscuits are less than 5 retrun bad

  String party(int tea, int biscuits){
    if (tea < 5 || biscuits < 5) return "bad";
    if (tea >= biscuits * 2 || biscuits >= tea * 2) return "great";

    return "good";
  }

  public static void main (String[] args) {
    CRA1 instance = new CRA1();
    // String result = instance.party(6,8);
    // System.out.println(result);
    // String result2 = instance.party(3,8);
    // System.out.println(result2);
    // String result3 = instance.party(20,6);
    // System.out.println(result3);
    String result4 = instance.party(1,6);
    System.out.println(result4);
    String result5 = instance.party(6,5);
    System.out.println(result5);
    // String result6 = instance.party(20,6);
    // System.out.println(result6);
  }
}