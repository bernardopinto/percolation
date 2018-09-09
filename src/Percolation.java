import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private class Site {

        int id;

        boolean isOpen;

        int root;

        boolean isBottomOrTopRowSite;


        public Site(int id, boolean isOpen, int root, boolean topOrBottom) {
            this.isOpen = isOpen;
            this.root = root;
            this.isBottomOrTopRowSite = topOrBottom;
            this.id = id;
        }

        public void makeSiteOpen() {
            this.isOpen = true;
        }

        public void updateRoot(int newRoot) {
            this.root = newRoot;
        }
    }

    protected Site[] grid;

    private int topNode;
    private int bottomNode;
    private int arraySize;
    private int gridSize;
    private int openSites;

    public Percolation(int n) {
        gridSize = n;
        arraySize = n * n;
        topNode = n * n;
        bottomNode = topNode + 1;
        for (int i = 0; i < arraySize; i++) {
            if (i / n == 0) {
                grid[i] = new Site(i, false, topNode, true);
            } else if (i / n == n -1) {
                grid[i] = new Site(i, false, bottomNode, true);
            } else {
                grid[i] = new Site(i, false, i, false);
            }

        }
        grid[topNode] = new Site(topNode, true, topNode, true);
        grid[bottomNode] = new Site(bottomNode, true, bottomNode, true);
    }

    public void open(int givenRow, int givenCol) throws IllegalArgumentException {
        int row = givenRow - 1;
        int col = givenCol - 1;
        int gridIndex = row * gridSize + col;

        if (row <= 0 || col <= 0 || row > topNode || col > topNode) {
            throw new IllegalArgumentException("Row or Column indexes are out of bound");
        }
        grid[gridIndex].makeSiteOpen();
        openSites =+ 1;

        for(int i = row - 1; i <= row + 1; i =+ 2) {
            for(int j = col -1; j <= col + 1; j =+ 2) {
                if (i >= 0 && j >= 0 && i < gridSize && j < gridSize) {
                    if(isOpen(i, j)){
                        int site = returnArrayIndex(i, j);
                        union(gridIndex, site);
                    }
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
        while(index != grid[index].root) index = grid[index].root;
        return index;
    }

    private boolean connected(int first, int second) {
     return root(first) == root(second);
    }

    public boolean isOpen(int row, int col) throws IllegalArgumentException {
        int actualRow = row - 1;
        int actualCol = col - 1;
        int index = actualRow * gridSize + actualCol;

        if (actualRow <= 0 || actualCol <= 0 || actualRow > topNode || actualCol > topNode) {
            throw new IllegalArgumentException("Row or Column indexes are out of bound");
        }

        return grid[index].isOpen;
    }

    public boolean isFull(int row, int col) {
        return root(returnArrayIndex(row, col)) == topNode;
    }

    public int numberOfOpenSites() {return openSites;}

    public boolean percolates() {return grid[bottomNode].root == topNode;}

    public static void main(String[] args)


}