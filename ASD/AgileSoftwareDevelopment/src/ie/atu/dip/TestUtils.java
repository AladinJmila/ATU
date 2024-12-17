package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TestUtils {
	public static void assertAccountDetails(BankingApp bank, String expectedName, double expectedBalance, int expectedSize,
			double expectedTotalDeposit) {
		assertEquals(expectedSize, bank.getAccounts().size());
		assertEquals(expectedName, bank.getAccounts().get(0).getAccountHolder());
		assertEquals(expectedBalance, bank.getAccounts().get(0).getBalance(), 0.1);
		assertEquals(expectedTotalDeposit, bank.getTotalDeposits(), 0.1);
	}
}
