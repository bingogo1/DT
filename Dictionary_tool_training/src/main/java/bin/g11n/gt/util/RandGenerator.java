package bin.g11n.gt.util;

import java.util.Random;

/**
 * generate a random alphabet string with length 
 * 
 * RandGenerator.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class RandGenerator {

    public static Random rn = new Random();
    private static int stringMaxLength = 30;
    private static int stringMinLength = 20;

    /**
     * generate random number with specified span.
     * 
     * rand 
     *
     * @param lo
     * @param hi
     * @return  int
     */
    private static int rand(int lo, int hi) {
        int n = hi - lo + 1;
        int i = rn.nextInt() % n;
        if (i < 0)
            i = -i;
        return lo + i;
    }

    /**
     * generate random string with scallable length
     * randomString 
     *
     * @param lo
     * @param hi
     * @return  String
     */
    private static String randomString(int lo, int hi) {
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++) {
            b[i] = (byte) rand('0', 'Z');
            //Skip characters between '9' and 'A'
            if (b[i] < 'A' && b[i] > '9') {
                b[i] = (byte)(b[i] - 10);
            }
        }
        return new String(b);
    }

    /**
     *  generate a random string with length global parameter
     * randomString 
     *
     * @return  String
     */
    public static String randomString() {
        return randomString(stringMinLength, stringMaxLength);
    }

}
