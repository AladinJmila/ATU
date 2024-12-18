package ie.atu.dip;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ BankingAppFindAccountTest.class, BankingAppAddAccountTest.class, BankingAppDepositTest.class,
		BankingAppWithdrawTest.class, BankingAppApproveLoanTest.class, BankingAppRepayLoanTest.class,
		BankingAppGetBalanceTest.class, BankingAppGetLoanTest.class })
class BankingAppTestSuite {
}
