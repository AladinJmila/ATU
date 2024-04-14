public class Caesar {
  public String encrypt(String plainText, int offset) {
    String cipher = "";
    char[] arr = plainText.toCharArray();

    for (int i = 0; i < arr.length; i++) {
      int numericalVal = (int) arr[i];

      if (numericalVal == 32) {
        cipher += arr[i];
      } else if (Character.isUpperCase(arr[i])) {
        cipher += (char) (((numericalVal + offset - 65) % 26) + 65);
      } else {
        cipher += (char) (((numericalVal + offset - 97) % 26) + 97);
      }
    }
    return cipher;
  }

  public static void main(String[] args) {
    Caesar test = new Caesar();
    System.out.println(test.encrypt("Isnt Java Great?", 4));
    System.out.println(test.encrypt("ABCZ", 4));
    System.out.println(test.encrypt("Hello", 4));
  }
}