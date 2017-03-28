
    public static double getBiggestDiff(double[] data) {
        double mn = data[0];
        double biggest = Double.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            biggest = Math.max(biggest, data[i] - mn);
            mn = Math.min(mn, data[i]);
        }
        return biggest;
    }
