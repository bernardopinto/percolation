public class MainClass {

    public static void main(String[] args) {

        class Site {
            boolean isOpen;

            int root;


            public Site(boolean isOpen, int root) {
                this.isOpen = isOpen;
                this.root = root;
            }

            public void makeSiteOpen() {
                this.isOpen = true;
            }
        }

        Site[][] grid;


        int n = 5;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = new Site(false, count);
                count++;
                System.out.println(grid[i][j]);
            }
        }
    }
}

