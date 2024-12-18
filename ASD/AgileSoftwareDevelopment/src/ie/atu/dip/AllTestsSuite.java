package ie.atu.dip;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ AccountTest.class, BankingAppTestSuite.class, InputValidatorTest.class, RunnerTestSuite.class })
public class AllTestsSuite {
}