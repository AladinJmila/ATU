package ie.atu.sw;

public class CaesarCipher {
	private int key = 0;

	public CaesarCipher(int key) {
		this.key = key;
	}

	public String encrypt(String plainText) {
		return encryptDecrypt(plainText, true);
	}

	public String decrypt(String cipherText) {
		return encryptDecrypt(cipherText, false);
	}

	private String encryptDecrypt(String s, boolean encrypt) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			if (encrypt) {
				sb.append((char)(s.codePointAt(i) + key));
			} else {
				sb.append((char)(s.codePointAt(i) - key));
			}
		}

		return sb.toString();
	}

}
