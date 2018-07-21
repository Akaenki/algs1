import edu.princeton.cs.algs4.Stack;


public class Board{
    private int[][] tiles;
    private int n, openx, openy;

    public Board(int[][] tiles){
        if(tiles == null) throw new IllegalArgumentException();
        n = tiles.length;
        this.tiles = new int[n][n];
        for(int i = 0; i<n; ++i) {
            for (int j = 0; j < n; ++j) {
                if(tiles[i][j] == 0){
                    openx = i; openy = j;
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int dimension(){
        return n;
    }

    public int hamming(){
        int sum = 0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                int pos = i*n + j + 1;
                if(pos == n*n) continue;
                if(tiles[i][j] != pos) sum++;
            }
        }
        return sum;
    }

    public int manhattan(){
        int sum = 0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(tiles[i][j] == 0) continue;

                int cur = tiles[i][j] - 1;
                int x = cur / n; int y = cur % n;
                sum += Math.abs(i-x) + Math.abs(j-y);
            }
        }
        return sum;
    }

    public boolean isGoal(){
        return hamming() == 0;
    }

    public Board twin(){
        Board twin = new Board(this.copy());
        twin.tiles[(openx + 1)%n][openy] = tiles[openx][(openy + 1)%n];
        twin.tiles[openx][(openy + 1)%n] = tiles[(openx + 1)%n][openy];
        return twin;
    }

    public Iterable<Board> neighbors(){
        Stack<Board> nbs = new Stack<>();
        int[][] newTile = this.copy();

        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(Math.abs(i - openx) + Math.abs(j - openy) == 1){
                    newTile[openx][openy] = newTile[i][j];
                    newTile[i][j] = 0;
                    nbs.push(new Board(newTile));
                    newTile[i][j] = newTile[openx][openy];
                    newTile[openx][openy] = 0;
                }
            }
        }
        return nbs;
    }

    @Override
    public boolean equals(Object y){
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board t = (Board) y;
        if(n != t.dimension()) return false;
        for(int i = 0; i<t.tiles.length; i++){
            for(int j = 0; j<t.tiles[0].length; j++){
                if(tiles[i][j]!= t.tiles[i][j]) return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    private int[][] copy(){
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                copy[i][j] = tiles[i][j];
        return copy;
    }

    public static void main(String[] args){

    }

}