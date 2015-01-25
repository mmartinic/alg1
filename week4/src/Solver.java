import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private final BoardComparator boardComparator;
    private Node solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        boardComparator = new BoardComparator();

        MinPQ<Node> queue = new MinPQ<Node>(boardComparator);
        MinPQ<Node> twinQueue = new MinPQ<Node>(boardComparator);

        queue.insert(new Node(initial, 0, null));
        twinQueue.insert(new Node(initial.twin(), 0, null));

        while (!queue.isEmpty() && !twinQueue.isEmpty()) {
            Node node = queue.delMin();
            if (node.board.isGoal()) {
                solution = node;
                break;
            }

            Node twinNode = twinQueue.delMin();
            if (twinNode.board.isGoal()) {
                break;
            }

            addNeighbors(queue, node);
            addNeighbors(twinQueue, twinNode);
        }
    }

    private void addNeighbors(MinPQ<Node> queue, Node node) {
        Board board = node.board;
        Board parentBoard;
        if (node.parent != null) {
            parentBoard = node.parent.board;
        } else {
            parentBoard = null;
        }
        int moves = node.moves;

        Iterable<Board> neighbors = board.neighbors();

        for (Board neighbor : neighbors) {
            if (!neighbor.equals(parentBoard)) {
                queue.insert(new Node(neighbor, moves + 1, node));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (solution != null) {
            return solution.moves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (solution == null) {
            return null;
        }

        List<Board> boards = new ArrayList<Board>();
        findSolution(solution, boards);
        return boards;
    }

    private static void findSolution(Node node, List<Board> boards) {

        if (node == null) {
            return;
        }

        findSolution(node.parent, boards);
        boards.add(node.board);
    }

    private static class Node {
        private final Board board;
        private final Node parent;
        private final int moves;

        private Node(Board board, int moves, Node parent) {
            this.board = board;
            this.moves = moves;
            this.parent = parent;
        }
    }

    private static class BoardComparator implements Comparator<Node> {

        @Override
        public int compare(Node n1, Node n2) {
            int p1 = n1.board.manhattan() + n1.moves;
            int p2 = n2.board.manhattan() + n2.moves;

            if (p1 == p2) {
                return 0;
            } else if (p1 < p2) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
