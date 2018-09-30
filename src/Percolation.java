import edu.princeton.cs.algs4.StdRandom;

public class Percolation {

    private final Site[] grid;

    private final int topNode;
    private final int bottomNode;
    private final int arraySize;
    private final int gridSize;
    private int openSites;

    private class Site {

        boolean isOpen;

        int root;


        public Site(boolean isOpen, int root) {
            this.isOpen = isOpen;
            this.root = root;
        }

        public void makeSiteOpen() {
            this.isOpen = true;
        }

        public void updateRoot(int newRoot) {
            this.root = newRoot;
        }
    }



    public Percolation(int n) {
        gridSize = n;
        arraySize = n * n;
        grid = new Site[arraySize + 2];
        topNode = n * n;
        bottomNode = topNode + 1;
        for (int i = 0; i < arraySize; i++) {
            if (i / n == 0) {
                grid[i] = new Site(false, topNode);
            } else if (i / n == n - 1) {
                grid[i] = new Site(false, bottomNode);
            } else {
                grid[i] = new Site(false, i);
            }

        }
        grid[topNode] = new Site(true, topNode);
        grid[bottomNode] = new Site(true, bottomNode);
    }

    public void open(int givenRow, int givenCol) {
        int row = givenRow - 1;
        int col = givenCol - 1;
        int gridIndex = row * gridSize + col;

        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) {
            throw new IllegalArgumentException("Row or Column indexes are out of bound, row: " + row + ", col: " + col);
        }
        grid[gridIndex].makeSiteOpen();
        openSites += 1;

        for (int i = row - 1; i <= row + 1; i += 2) {
                if (i >= 0 && i < gridSize) {
                    if (isOpen(i + 1, col + 1)) {
                        int site = returnArrayIndex(i, col);
                        union(gridIndex, site);
                    }
                }
        }
        for (int j = col - 1; j <= col + 1; j += 2) {
            if (j >= 0 && j < gridSize) {
                if(isOpen(row + 1, j + 1)) {
                    int site = returnArrayIndex(row, j);
                    union(gridIndex, site);
                }
            }
        }
    }

    private int returnArrayIndex(int row, int col) {
        return row * gridSize + col;
    }


    private void union(int first, int second) {
        int firstRoot = root(first);
        int secondRoot = root(second);

        if (connected(firstRoot, secondRoot)) return;
        else if (firstRoot == topNode) {
            grid[secondRoot].updateRoot(firstRoot);
        } else if (secondRoot == topNode) {
            grid[firstRoot].updateRoot(secondRoot);
        } else if (firstRoot == bottomNode) {
            grid[secondRoot].updateRoot(firstRoot);
        } else if (secondRoot == bottomNode) {
            grid[firstRoot].updateRoot(secondRoot);
        } else {
            grid[firstRoot].updateRoot(secondRoot);
        }
    }

    private int root(int index) {
        while (index != grid[index].root) index = grid[index].root;
        return index;
    }

    private boolean connected(int first, int second) {
        return root(first) == root(second);
    }

    public boolean isOpen(int givenRow, int givenCol) {
        int row = givenRow - 1;
        int col = givenCol - 1;
        int index = row * gridSize + col;

        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) {
            throw new IllegalArgumentException("Row or Column indexes are out of bound, row: " + row + ", col: " + col);
        }

        return grid[index].isOpen;
    }

    public boolean isFull(int givenRow, int givenCol) {
        return (isOpen(givenRow, givenCol) && root(returnArrayIndex(givenRow - 1, givenCol - 1)) == topNode);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return grid[bottomNode].root == topNode;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);
        percolation.open(2, 1);
        boolean percolates = percolation.percolates();
        System.out.println("percolates = " + percolates);

    }


}