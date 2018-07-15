import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size,totalGrid;
    private int countOpen;
    private int[] grid;
    private WeightedQuickUnionUF uf1, uf2;
    private int top, bot;
    /** Constructor create N-by-N grid with all sites initialized
     *  generally takes O(N^2) time complexity */
    public Percolation(int N){
        if(N<=0) throw new IllegalArgumentException();
        size = N; totalGrid = N*N;
        grid = new int[totalGrid];
        for(int i = 0; i < totalGrid; i++) grid[i] = 0;

        countOpen = 0;

        /* First set with both virtual sites */
        uf1 = new WeightedQuickUnionUF(totalGrid + 2);
        /* A second set with only top virtual sites */
        uf2 = new WeightedQuickUnionUF(totalGrid + 1);

        top = totalGrid; bot = totalGrid+1;
        /* Top virtual site: count and Bottom virtual size: count+1 */
        for(int i = 0; i < N; i++){
            uf1.union(top, i);
            uf2.union(top, i);
            uf1.union(bot, totalGrid-i-1);
        }
    }

    /** All the other method must take constant time
     *  plus constant number of calls to union(), find(), connected() and count()
     */

    private int xyTo1D(int x, int y){
        return (x - 1) * size + (y - 1);
    }

    private void connectSurrounding(int row, int col){
        int cur = xyTo1D(row,col);
        if(row < size && grid[xyTo1D(row+1, col)] == 1){
            uf1.union(cur, xyTo1D(row+1, col));
            uf2.union(cur, xyTo1D(row+1, col));
        }
        if(row > 1 && grid[xyTo1D(row-1, col)] == 1){
            uf1.union(cur, xyTo1D(row-1, col));
            uf2.union(cur, xyTo1D(row-1, col));
        }
        if(col < size && grid[xyTo1D(row, col+1)] == 1){
            uf1.union(cur, xyTo1D(row, col+1));
            uf2.union(cur, xyTo1D(row, col+1));
        }
        if(col > 1 && grid[xyTo1D(row, col-1)] == 1){
            uf1.union(cur, xyTo1D(row, col-1));
            uf2.union(cur, xyTo1D(row, col-1));
        }
    }

    /** Open the site at (row,col) if it is not */
    public void open(int row, int col){
        if(row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException();
        if(grid[xyTo1D(row, col)]==0) {
            grid[xyTo1D(row, col)] = 1;
            countOpen += 1;
            connectSurrounding(row, col);
        }
    }

    /** Is the site at (row,col) open? */
    public boolean isOpen(int row, int col){
        if(row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException();
        return grid[xyTo1D(row, col)] == 1;
    }

    /** Is the site at (row,col) full? */
    public boolean isFull(int row, int col){
        if(row < 1 || col < 1 || row > size || col > size)
            throw new IllegalArgumentException();
        return isOpen(row, col) && uf2.connected(top, xyTo1D(row, col));
    }

    /** Returns the number of open sites on the grid
     *  This method must take constant time */
    public int numberOfOpenSites(){
        return countOpen;
    }

    /** Check if the system percolate */
    public boolean percolates(){
        if(size == 1 && grid[0] == 0) return false;
        return uf1.connected(top,bot);
    }

    public static void main(String[] args){
        Percolation p = new Percolation(1);
        System.out.println(p.isOpen(1,1));
    }
}
