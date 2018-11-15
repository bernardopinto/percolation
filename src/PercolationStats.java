import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private final int trials;

    private final double mean;

    private final double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("PercolationStats parameters must be greater than zero");
        }

        int siteAmount = n * n;
        this.trials = trials;
        final double[] percolationResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            double openSites = percolation.numberOfOpenSites();
            percolationResults[i] = openSites / siteAmount;
        }

        // Store mean and stddev in variable so that it is only computed once.
        mean = StdStats.mean(percolationResults);
        stddev = StdStats.stddev(percolationResults);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - confidenceAggregator();
    }

    public double confidenceHi() {
        return mean + confidenceAggregator();
    }

    private double confidenceAggregator() {
        return (1.96 * stddev) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Need two arguments in order to run the experiment");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + "[" + percolationStats.confidenceLo() +
                ", " + percolationStats.confidenceHi() + "]");
    }
}
