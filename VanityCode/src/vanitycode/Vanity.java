/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanitycode;

/**
 *
 * @author 20120093
 */
public class Vanity {

    public static final char nums[][] = {
        {' '},
        {},
        {'A', 'B', 'C'},
        {'D', 'E', 'F'},
        {'G', 'H', 'I'},
        {'J', 'K', 'L'},
        {'M', 'N', 'O'},
        {'P', 'Q', 'R', 'S'},
        {'T', 'U', 'V'},
        {'W', 'X', 'Y', 'Z'}
    };

    public static String times(String c, int n) {
        String res = "";
        for (int i = 0; i < n; i++) {
            res += c;
        }
        return res;
    }

    private static String encode(String abc, boolean adv) {
        String res = "";
        for (char c : abc.toUpperCase().toCharArray()) {
            boolean found = false;
            for (int i = 0; i < nums.length && !found; i++) {
                for (int j = 0; j < nums[i].length && !found; j++) {
                    if (nums[i][j] == c) {
                        found = true;
                        res += times("" + i, adv ? 1 : (j + 1));
                    }
                }

            }
            res += " ";
            if (!found) {
                throw new IllegalArgumentException("illegal char " + c);
            }
        }
        return res.trim();
    }

    public static String simpleencode(String abc) {
        return encode(abc, false);
    }

    public static String advancedencode(String abc) {
        return encode(abc, true);
    }

    public static String simpledecode(String abc) {
        String res = "";
        for (String s : abc.toUpperCase().split(" ")) {
            char ch = s.charAt(0);
            for (char c : s.toCharArray()) {
                if (c != ch) {
                    throw new IllegalArgumentException();
                }
            }
            res += nums[ch - '0'][s.length()-1];
        }
        return res;
    }
}
