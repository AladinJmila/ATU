package ie.atu.sw;

public class CaesarCipher {
	private int key = 0;

	public CaesarCipher(int key) {
		this.key = key;
	}

	public String encrypt(String plainText) {
		String enc = "";

		for (int i = 0; i < plainText.length(); i++) {
			char ch = (char) (plainText.codePointAt(i) + key);
			enc += ch;
		}

		return enc;
	}

	public String decrypt(String cipherText) {
		String dec = "";

		for (int i = 0; i < cipherText.length(); i++) {
			char ch = (char) (cipherText.codePointAt(i) - key);
			dec += ch;
		}

		return dec;
	}

}
