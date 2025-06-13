package days.series;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    int maxattempt = 3;
    int attempt = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (attempt <= maxattempt && result.getStatus() != ITestResult.SUCCESS) {
            attempt++;
            return true;
        }
        return false;
    }
}