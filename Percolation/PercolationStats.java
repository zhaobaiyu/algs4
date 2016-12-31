import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int trials;
    private double[] x;
    private boolean hasMean, hasStddev;
    private double theMean, theStddev;
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        x = new double[trials];
        int randRow, randCol, turns, sumSites = n * n;
        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            turns = 0;
            while (true) {
                do {
                    randRow = (int) (StdRandom.uniform() * n) + 1;
                    randCol = (int) (StdRandom.uniform() * n) + 1;
                } while (p.isOpen(randRow, randCol));
                p.open(randRow, randCol);
                ++turns;
                if (p.percolates()) {
                    x[i] = (double) turns / sumSites;
                    break;
                }
            }
        }
    }

    public double mean() {
        if (!hasMean) {
            hasMean = true;
            theMean = StdStats.mean(x);
        }
        return theMean;
    }

    public double stddev() {
        if (!hasStddev) {
            hasStddev = true;
            theStddev = StdStats.stddev(x);
        }
        return theStddev;
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        StdOut.printf("%-24s", "mean");
        StdOut.printf("= %f\n", ps.mean());
        StdOut.printf("%-24s", "stddev");
        StdOut.printf("= %f\n", ps.stddev());
        StdOut.printf("%-24s", "95% confidence interval");
        StdOut.printf("= %f, %f\n", ps.confidenceLo(), ps.confidenceHi());
    }
}
