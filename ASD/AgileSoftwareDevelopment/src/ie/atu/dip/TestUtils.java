package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TestUtils {
	public static final String MOCK_NAME = "John Smith";
	public static final double MOCK_AMOUNT = 1000.0d;
	public static final String INVALID_NAME_ERROR_MESSAGE = "Account holder name cannot be null or empty";
	public static final String INVALID_DEPOSIT_ERROR_MESSAGE = "Amount cannot be negative";
	
	public static void assertAccountDetails(BankingApp bank, String expectedName, double expectedBalance, int expectedSize,
			double expectedTotalDeposit) {
		assertEquals(expectedSize, bank.getAccounts().size());
		assertEquals(expectedName, bank.getAccounts().get(0).getAccountHolder());
		assertEquals(expectedBalance, bank.getAccounts().get(0).getBalance(), 0.1);
		assertEquals(expectedTotalDeposit, bank.getTotalDeposits(), 0.1);
	}
}
