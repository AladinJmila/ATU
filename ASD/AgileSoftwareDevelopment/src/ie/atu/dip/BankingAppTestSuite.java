package ie.atu.dip;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ BankingAppFindAccountTest.class, BankingAppAddAccountTest.class, BankingAppDepositTest.class })
class BankingAppTestSuite {
}
