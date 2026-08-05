


package util;

/**
 * Utility class for common mathematical operations.
 */
public final class MathUtil {

    private MathUtil() {
        throw new AssertionError(
                "Utility class cannot be instantiated.");
    }

    /**
     * Returns floor(sqrt(number)).
     */
    public static int sqrt(int number) {

        if (number < 0)
            throw new IllegalArgumentException("Square root of negative number.");
    
        int low = 0;
        int high = number;
        int ans = 0;
    
        while (low <= high) {
            int mid = low + (high - low) / 2;
    
            long sq = (long) mid * mid;
    
            if (sq == number)
                return mid;
    
            if (sq < number) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
    
        return ans;
    }

    public static char toChar(int digit) {
        return (char) ('0' + digit);
    }

    public static int toDigit(char digit) {
        return digit - '0';
    }

}