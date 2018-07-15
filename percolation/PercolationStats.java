import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    public PercolationStats(int N, int T){
        if(N <= 0 || T <= 0) throw new IllegalArgumentException();
        thresholds = new double[T];
        for(int t = 0; t < T; ++t){
            Percolation p = new Percolation(N);
            while(!p.percolates()){
                p.open(StdRandom.uniform(1,N+1), StdRandom.uniform(1,N+1));
            }
            thresholds[t] = p.numberOfOpenSites() * 1.0 / (N * N);
        }
    }

    public double mean(){
        return StdStats.mean(thresholds);
    }

    public double stddev(){
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo(){
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    public double confidenceHi(){
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    public static void main(String[] args) {
        int N = 200;// Integer.parseInt(args[0]);
        int T = 100;//Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        System.out.println(
                "\nmean                    = " + ps.mean() +
                "\nstddev                  = " + ps.stddev() +
                "\n95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]"
        );
    }
}
