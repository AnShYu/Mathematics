package mathematics;

public class BinomialCoefficient {

    private static int calculateBinominalCoefficientDynamical (int k, int n) {

        int [][] coefficients = new int[k+1][n+1];

        for (int i = 0; i<=k; i++) {
            for (int j = 0; j<=n; j++) {
                if (i == 0) {
                    coefficients[i][j] = 1;
                } else if (j == 0) {
                    coefficients[i][j] = 0;
                } else if (i == j) {
                    coefficients[i][j] = 1;
                } else if (i < j) {
                    coefficients[i][j] = coefficients[i][j-1] + coefficients[i-1][j-1];
                }
            }
        }
        return coefficients[k][n];
    }

    public static void main(String[] args) {

        int x = calculateBinominalCoefficientDynamical(2,10);
        System.out.println(x);

    }
}
