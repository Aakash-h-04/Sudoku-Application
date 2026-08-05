package util;

/**
 * Utility class for common bit manipulation operations.
 *
 * All methods are implemented without using Java's
 * built-in bit utility methods.
 */
public final class BitUtil {

    private BitUtil() {
        throw new AssertionError(
                "Utility class cannot be instantiated.");
    }

    /*======================================================
                        BASIC OPERATIONS
     ======================================================*/

    public static int bit(int position) {
        return 1 << position;
    }

    public static boolean isBitSet(int value, int position) {
        return (value & bit(position)) != 0;
    }

    public static int setBit(int value, int position) {
        return value | bit(position);
    }

    public static int clearBit(int value, int position) {
        return value & ~bit(position);
    }

    public static int toggleBit(int value, int position) {
        return value ^ bit(position);
    }

    /*======================================================
                        MASK OPERATIONS
     ======================================================*/

    public static int setMask(int value, int mask) {
        return value | mask;
    }

    public static int clearMask(int value, int mask) {
        return value & ~mask;
    }

    public static boolean containsMask(int value, int mask) {
        return (value & mask) == mask;
    }

    /*======================================================
                     LOWEST / HIGHEST BIT
     ======================================================*/

    public static int lowestOneBit(int value) {
        return value & (-value);
    }

    public static int highestOneBit(int value) {

        if (value == 0)
            return 0;

        int highest = 1;

        while (value > 1) {
            value >>= 1;
            highest <<= 1;
        }

        return highest;
    }

    /*======================================================
                        BIT COUNT
     ======================================================*/

    /**
     * Brian Kernighan Algorithm
     */
    public static int bitCount(int value) {

        int count = 0;

        while (value != 0) {
            value &= (value - 1);
            count++;
        }

        return count;
    }

    /*======================================================
                        BIT POSITIONS
     ======================================================*/

    /**
     * Returns index of lowest set bit.
     *
     * Example:
     * 00010000 -> 4
     */
    public static int trailingZeros(int value) {

        if (value == 0)
            return -1;

        int count = 0;

        while ((value & 1) == 0) {
            value >>= 1;
            count++;
        }

        return count;
    }

    /**
     * Returns index of highest set bit.
     *
     * Example:
     * 01010000 -> 6
     */
    public static int highestBitIndex(int value) {

        if (value == 0)
            return -1;

        int index = 0;

        while (value > 1) {
            value >>= 1;
            index++;
        }

        return index;
    }

    /*======================================================
                    POWER OF TWO
     ======================================================*/

    public static boolean isPowerOfTwo(int value) {

        return value > 0 &&
               (value & (value - 1)) == 0;
    }

    public static boolean hasSingleBit(int value) {
        return bitCount(value) == 1;
    }

    /*======================================================
                    CANDIDATE HELPERS
     ======================================================*/

    /**
     * Removes the lowest set bit.
     *
     * Example:
     *
     * 10110000
     *
     * ->
     *
     * 10100000
     */
    public static int removeLowestOneBit(int value) {
        return value & (value - 1);
    }

    /**
     * Returns next candidate mask.
     */
    public static int nextCandidate(int candidates) {
        return lowestOneBit(candidates);
    }

    /**
     * Removes current candidate.
     */
    public static int removeCandidate(int candidates,int candidateMask) {

        return candidates ^ candidateMask;
    }

    /*======================================================
                    RANGE MASKS
     ======================================================*/

    /**
     * Example:
     *
     * from = 2
     * to   = 5
     *
     * returns
     *
     * 00111100
     */
    public static int mask(int from, int to) {

        int mask = 0;

        for (int i = from; i <= to; i++) {
            mask |= bit(i);
        }

        return mask;
    }

}