package ie.atu.dip;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ RunnerHelperMethodsTest.class, RunnerTest.class })
public class RunnerTestSuite {
}
