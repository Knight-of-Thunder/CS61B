import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;


public class Percolation {
    // TODO: Add any necessary instance variables.
    WeightedQuickUnionUF sites;
    int topIdx;
    int bottomIdx;
    int[] openSites;
    int N;
    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if(N <= 0){
            throw new IllegalArgumentException("N should be greater than 0");
        }
        this.N = N;
        sites = new WeightedQuickUnionUF(N*N+2);

        topIdx = N*N;
        bottomIdx = N*N+1;
        openSites = new int[N*N];
        Arrays.fill(openSites, 0);

    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        checkValidIdx(row, col);
        openSites[getWQUIdx(row, col)] = 1;
        unionAround(row, col);
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        checkValidIdx(row, col);
        return  openSites[getWQUIdx(row, col)] == 1;
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        checkValidIdx(row, col);
        return sites.connected(topIdx, getWQUIdx(row, col));
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        int count = 0;
        for(int i : openSites){
            if(i == 1){
                count += 1;
            }
        }
        return count;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        WeightedQuickUnionUF sitesForPercolate = sites;
        for(int i = 0; i < N; i++){
            sitesForPercolate.union(bottomIdx, N * (N - 1) + i);
        }
        return sitesForPercolate.connected(topIdx, bottomIdx);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.
    public int getWQUIdx(int row, int col){
        return row * N + col;
    }

    public int getLeftIdx(int row, int col){
        return row * N + col - 1;
    }

    public int getRightIdx(int row, int col){
        return row * N + col + 1;
    }

    public int getTopIdx(int row, int col){
        return (row - 1) * N + col;
    }
    public int getBottomIdx(int row, int col){
        return (row + 1) * N + col;
    }


    public void unionAround(int row, int col){
        if(row == 0){
            sites.union(getWQUIdx(row, col), topIdx);
        }
        if(col > 0 && openSites[getLeftIdx(row, col)] == 1){
            sites.union(getWQUIdx(row, col), getLeftIdx(row, col));
        }
        if(col < N - 1 && openSites[getRightIdx(row, col)] == 1){
            sites.union(getWQUIdx(row, col), getRightIdx(row, col));
        }
        if(row > 0 && openSites[getTopIdx(row, col)] == 1){
            sites.union(getWQUIdx(row, col), getTopIdx(row, col));
        }
        if(row < N -1 && openSites[getBottomIdx(row, col)] == 1){
            sites.union(getWQUIdx(row, col), getBottomIdx(row, col));
        }

    }

    public void checkValidIdx(int row, int col){
        if(row < 0 || row >= N || col < 0 || col >= N){
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
}
