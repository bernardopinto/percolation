import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int gridSize;

    private final double siteAmount;

    private final int trials;

    private double[] percolationResults;

    public PercolationStats(int n, int trials) {
        gridSize = n;
        this.siteAmount = n * n;
        this.trials = trials;
        percolationResults = new double[trials];
    }

    public double mean() {
        return StdStats.mean(percolationResults);
    }

    public double stddev() {
        return StdStats.stddev(percolationResults);
    }

    public double confidenceLo() {
        return mean() - confidenceAggregator();
    }

    public double confidenceHi() {
        return mean() + confidenceAggregator();
    }

    private double confidenceAggregator() {
        return (1.96 * stddev()) / Math.sqrt(trials);
    }

    private double[] runPercolation() {
        for (int i = 0; i < trials; i++) {
            Percolation loopPercolation = new Percolation(gridSize);
            while (!loopPercolation.percolates()) {
                int row = StdRandom.uniform(1, gridSize + 1);
                int col = StdRandom.uniform(1, gridSize + 1);
                if (!loopPercolation.isOpen(row, col)) {
                    loopPercolation.open(row, col);
                }
            }
            double openSites = loopPercolation.numberOfOpenSites();
            percolationResults[i] = openSites / siteAmount;
        }
        return percolationResults;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Need two arguments in order to run the experiment");
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        percolationStats.runPercolation();
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + "[" + percolationStats.confidenceLo() +
                ", " + percolationStats.confidenceHi() + "]");
    }
}
