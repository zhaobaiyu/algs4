/**
 * Baiyu Zhao, the last day of 2016
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF union;
    private boolean[][] openedSites;
    private boolean[] fullSites;
    private boolean[] hasBottom;
    private boolean isPercolates = false;
    private int sumOpened = 0;
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        union = new WeightedQuickUnionUF(n * n);
        openedSites = new boolean[n+1][n+1];
        fullSites = new boolean[n * n];
        hasBottom = new boolean[n * n];
    }
  
    private void checkIndex(int row, int col) {
        if (row > n || row < 1) {
            throw new IndexOutOfBoundsException("row index out of bounds");
        }
        if (col > n || col < 1) {
            throw new IndexOutOfBoundsException("column index out of bounds");
        }
    }

    private int xyTo1D(int x, int y) {
        return n * (x - 1) + y - 1;
    }

    public void open(int row, int col) {
        checkIndex(row, col);
        if (!openedSites[row][col]) {
            openedSites[row][col] = true;
            ++sumOpened;
        }
        int xy = xyTo1D(row, col);
        boolean hasFullRoot = false, hasBottomSite = false;
        int tmpRoot;
        if (row == 1) {
            hasFullRoot = true;
        }
        else if (row > 1 && openedSites[row - 1][col]) {
            tmpRoot = union.find(xy - n);
            if (fullSites[tmpRoot])
                hasFullRoot = true;
            if (hasBottom[tmpRoot])
                hasBottomSite = true;
            union.union(xy, xy - n);
        }
        if (row == n) {
            hasBottomSite = true;
        }
        else if (row < n && openedSites[row + 1][col]) {
            tmpRoot = union.find(xy + n);
            if (!hasFullRoot && fullSites[tmpRoot])
                hasFullRoot = true;
            if (!hasBottomSite && hasBottom[tmpRoot])
                hasBottomSite = true;
            union.union(xy, xy + n);            
        }
        if (col > 1 && openedSites[row][col - 1]) {
            tmpRoot = union.find(xy - 1);
            if (!hasFullRoot && fullSites[tmpRoot])
                hasFullRoot = true;
            if (!hasBottomSite && hasBottom[tmpRoot])
                hasBottomSite = true;
            union.union(xy, xy - 1);            
        }
        if (col < n && openedSites[row][col + 1]) {
            tmpRoot = union.find(xy + 1);
            if (!hasFullRoot && fullSites[tmpRoot])
                hasFullRoot = true;
            if (!hasBottomSite && hasBottom[tmpRoot])
                hasBottomSite = true;
            union.union(xy, xy + 1);            
        }
        tmpRoot = union.find(xy);
        if (hasFullRoot) {
            fullSites[tmpRoot] = true;
            if (hasBottomSite)
                isPercolates = true;
        }
        if (hasBottomSite) {
            hasBottom[tmpRoot] = true;
        }
    }

    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return openedSites[row][col];
    }

    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        int xy = xyTo1D(row, col);
        return fullSites[union.find(xy)];
    }

    public int numberOfOpenSites() {
        return sumOpened;
    }

    public boolean percolates() {
        return isPercolates;
    }

    public static void main(String[] args) {

    }
}
