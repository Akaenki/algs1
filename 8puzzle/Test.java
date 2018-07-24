import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Test {
    public static void main(String[] args) {
        In in = new In("puzzle4x4-80.txt");
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        System.out.println(initial.toString());
        System.out.println(initial.twin().toString());
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board ws : solver.solution()) {
            StdOut.println(ws);
        }
    }
}
