package ie.atu.sw;

public class Runner {

	public static void main(String[] args) {
		CaesarCipher cc = new CaesarCipher(7);
		
		String encrypted = cc.encrypt("Gauls are smelly and dangerous");
		System.out.println(encrypted);
		System.out.println(cc.decrypt(encrypted));
		
	}
}
