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
public class VanityCode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String result = "";
        String str = Vanity.advancedencode("Hallo Welt");
        for (String s : str.split(" ")) {
            if (s.length() != 1) {
                throw new IllegalArgumentException("Input is malformed " + s);
            }
            int i = s.charAt(0) - '0';
            for (char c : Vanity.nums[i]) {
                result += c;
            }
            result += "\n";
        }

        String lines[] = result.split("\n");
        boolean repeat = true;
        for (int i = 0; repeat; i++) {
            repeat = false;
            for (int j = 0; j < lines.length; j++) {
                if (!lines[j].equals("")) {
                    repeat = true;
                    System.out.print(lines[j].charAt(0));
                    lines[j] = lines[j].substring(1);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
        for(String sub : result.split("\n \n")) {
            doit("", sub.split("\n"), 0);
        }
    }

    public static void doit(String pre, String[] l, int n) {
        if (n >= l.length) {
            System.out.println(pre);
        } else {
            for (char c : l[n].toCharArray()) {
                doit(pre + c, l, n + 1);
            }
        }
    }

}
