import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.lang.Math;

public class PercolationStats {

    private Percolation[] percolation;

    private int gridSize;

    private double siteAmount;

    private int trials;

    private double[] percolationResults;

    public PercolationStats(int n, int trials) {
        percolation = new Percolation[trials];
        gridSize = n;
        this.siteAmount = n * n;
        this.trials = trials;
        percolationResults = new double[trials];

        for (int i = 0; i < trials; i++) {
            percolation[i] = new Percolation(n);
        }
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
            Percolation innerPercolation = percolation[i];
            while (!innerPercolation.percolates()) {
                int row = StdRandom.uniform(1, gridSize + 1);
                int col = StdRandom.uniform(1, gridSize + 1);
                if (!innerPercolation.isOpen(row, col)) {
                    innerPercolation.open(row, col);
                }
            }
            double openSites = innerPercolation.numberOfOpenSites();
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
