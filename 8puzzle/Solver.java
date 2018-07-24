import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode goal;
    private boolean isSolvable;

    public Solver(Board initial){
        if(initial == null) throw new IllegalArgumentException();
        MinPQ<SearchNode> fringe = new MinPQ<>(), twinfringe = new MinPQ<>();
        SearchNode cur = new SearchNode(initial, null, 0),
                tcur = new SearchNode(initial.twin(), null, 0);
        while (!cur.board.isGoal() && !tcur.board.isGoal()) {
            for (Board next : cur.board.neighbors()) {
                if (cur.previous == null || !next.equals(cur.previous.board)) {
                    fringe.insert(new SearchNode(next, cur, cur.totalMoves + 1));
                }
            }
            for (Board next : tcur.board.neighbors()) {
                if (tcur.previous == null || !next.equals(tcur.previous.board)) {
                    twinfringe.insert(new SearchNode(next, tcur, tcur.totalMoves + 1));
                }
            }
            cur = fringe.delMin(); tcur = twinfringe.delMin();
        }

        if(cur.board.isGoal()){ goal = cur; isSolvable = true; }
        else { goal = tcur; isSolvable = false; }
    }

    public boolean isSolvable(){
        return isSolvable;
    }

    public int moves(){
        return isSolvable ? goal.totalMoves : -1;
    }

    public Iterable<Board> solution(){
        if(isSolvable) {
            Stack<Board> list = new Stack<>();
            SearchNode cur = goal;
            while (cur != null) {
                list.push(cur.board);
                cur = cur.previous;
            }
            return list;
        }
        return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode previous;
        private int totalMoves;
        private int estimateMoves;

        public SearchNode(Board _board, SearchNode _previous, int _moves){
            board = _board;
            previous = _previous;
            totalMoves = _moves;
            estimateMoves = board.manhattan();
        }
        public int compareTo(SearchNode that){
            return (this.estimateMoves + this.previous.totalMoves)
                    - (that.estimateMoves + that.previous.totalMoves);
        }
    }
}



