package ie.atu.dip;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({AccountTest.class, BankingAppTest.class})
public class AllTests {}