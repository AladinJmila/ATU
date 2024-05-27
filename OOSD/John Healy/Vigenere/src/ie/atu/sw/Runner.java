package ie.atu.sw;

public class Runner {
	public static void main(String[] args) {
		String key = "THEQUICKBROWNFOXJUMPEROVERTHELAZYDOGS";
		String plainText = "ATTACK THE CASTLE WALL AT DAWN";
		
		Vigenere cipher = new Vigenere(key);
		String cipherText = cipher.encrypt(plainText);
		System.out.println(cipherText);
		System.out.println(cipher.decrypt(cipherText));
	}
}
