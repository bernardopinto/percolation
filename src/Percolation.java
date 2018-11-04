import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF percolationGrid;
    private final WeightedQuickUnionUF isFullGrid;
    private final boolean[][] isOpen;

    private final int gridSize;
    private int numOfOpenSites = 0;

    private final int topNode;
    private final int bottomNode;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be at least 1");
        }
        gridSize = n;
        isOpen = new boolean[n][n];
        percolationGrid = new WeightedQuickUnionUF(n * n + 2);
        isFullGrid = new WeightedQuickUnionUF(n * n + 1);

        topNode = n * n;
        bottomNode = topNode + 1;


        // Join first row in percolation grid to top node
        for (int i = 0; i < n; i++) {
            percolationGrid.union(i, topNode);
        }

        // Join last row in percolation grid to bottom node
        for (int i = n * (n - 1); i < n * n; i++) {
            percolationGrid.union(i, bottomNode);
        }

        // Join first row in isFull grid to topNode
        for (int i = 0; i < n; i++) {
            isFullGrid.union(i, topNode);
        }


    }

    public void open(int givenRow, int givenCol) {
        int row = givenRow - 1;
        int col = givenCol - 1;

        assertIndexes(row, col);

        if (isOpen[row][col]) return;

        isOpen[row][col] = true;
        numOfOpenSites += 1;

        for (int i = row - 1; i <= row + 1; i += 2) {
            if (i >= 0 && i < gridSize) {
                if (isOpen[i][col]) {
                    int topAndBottomRowNode = getArrayIndex(i, col);
                    int currentNodeIndex = getArrayIndex(row, col);
                    percolationGrid.union(currentNodeIndex, topAndBottomRowNode);
                    isFullGrid.union(currentNodeIndex, topAndBottomRowNode);
                }
            }
        }

        for (int j = col - 1; j <= col + 1; j += 2) {
            if (j >= 0 && j < gridSize) {
                if (isOpen[row][j]) {
                    int topAndBottomRowNode = getArrayIndex(row, j);
                    int currentNodeIndex = getArrayIndex(row, col);
                    percolationGrid.union(currentNodeIndex, topAndBottomRowNode);
                    isFullGrid.union(currentNodeIndex, topAndBottomRowNode);
                }
            }
        }

    }

    public boolean isOpen(int givenRow, int givenCol) {
        int row = givenRow - 1;
        int col = givenCol - 1;

        assertIndexes(row, col);

        return isOpen[row][col];
    }

    public boolean isFull(int givenRow, int givenCol) {
        int row = givenRow - 1;
        int col = givenCol - 1;
        assertIndexes(row, col);

        int node = getArrayIndex(row, col);

        return isFullGrid.connected(node, topNode) && isOpen[row][col];
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        boolean connected = percolationGrid.connected(topNode, bottomNode);
        if (gridSize == 1)
            return isOpen[0][0] && connected;
        else return connected;
    }

    private int getArrayIndex(int actualRow, int actualCol) {
        return actualRow * gridSize + actualCol;
    }

    // Parameters must be converted from client entries to correct coordinates
    private void assertIndexes(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Insert valid cell coordinates");
        }
    }

    // psvm command intellij :)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(1, 2);
        percolation.open(2, 3);
        percolation.open(3, 3);

        boolean isFull33 = percolation.isFull(3, 3);

        boolean percolates = percolation.percolates();

        int numOpenSites = percolation.numberOfOpenSites();

        System.out.println("isFull33 = " + isFull33);


        System.out.println("percolates = " + percolates);

        System.out.println("numOpenSites = " + numOpenSites);


    }

}
