import java.util.*;

public class Main {
    static int[] p1 = {1, 2, 3, 4, 5, 6, 8, 7, 0};
    static int[] p2 = {1, 8, 2, 0, 4, 3, 7, 6, 5};
    static int[] p3 = {1, 2, 3, 0, 4, 6, 7, 5, 8};
    static int[] notGoal = {1, 2, 3, 4, 5, 6, 7, 0, 8};
    static Node goal = new Node(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});

    public static void main(String[] args) {
        Solver solver = new Solver();
//        System.out.println(new Node(notGoal).isGoal());
//        if (!isSolvable(p2)) return;
        solver.solve(p3);
    }

    static class Solver {
        void solve(int[] puzzles) {
            Node head = new Node(puzzles);
            Node child;
            head.print();
            do {
                child = getChild(head);
                child.print();
                head = child;
            } while (!child.isGoal());
            child.print();
        }

        Node getChild(Node parent) {
            int minF = 20;
            Node childMin = null;
            for (int i : getElementsShouldBeSwaped(parent)) {
                Node child = new Node(swapZeroWith(parent, i));
                child.h = getDistFromGoal1(child);
                if (child.getF() < minF) {
                    minF = child.getF();
                    childMin = child;
                }
            }
            return childMin;
        }

        private int[] swapZeroWith(Node parent, int k) {
            int zeroCoordinate = getZeroCoordinate(parent);
            int[] childrenPuzzles = Arrays.copyOf(parent.puzzles, parent.puzzles.length);
            int temp = childrenPuzzles[zeroCoordinate];
            childrenPuzzles[zeroCoordinate] = childrenPuzzles[k];
            childrenPuzzles[k] = temp;
            return childrenPuzzles;
        }

        List<Integer> getElementsShouldBeSwaped(Node node) {
            int zeroCoordinate = getZeroCoordinate(node);
            List<Integer> neighboors;
            switch (zeroCoordinate) {
                case 0:
                    neighboors = new ArrayList<>(List.of(1, 3));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 1:
                    neighboors = new ArrayList<>(List.of(0, 4, 2));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 2:
                    neighboors = new ArrayList<>(List.of(1, 5));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 3:
                    neighboors = new ArrayList<>(List.of(0, 4, 6));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 4:
                    neighboors = new ArrayList<>(List.of(1, 3, 5, 7));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 5:
                    neighboors = new ArrayList<>(List.of(2, 4, 8));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 6:
                    neighboors = new ArrayList<>(List.of(3, 7));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 7:
                    neighboors = new ArrayList<>(List.of(6, 4, 8));
                    Collections.shuffle(neighboors);
                    return neighboors;
                case 8:
                    neighboors = new ArrayList<>(List.of(7, 5));
                    Collections.shuffle(neighboors);
                    return neighboors;
            }
            return null;
        }

        int getZeroCoordinate(Node node) {
            for (int i = 0; i < node.puzzles.length; i++) {
                if (node.puzzles[i] == 0) return i;
            }
            return -1;
        }
    }

    static class Node {

        private int[] puzzles;
        private int h;

        public int getF() {
            return h;
        }

        public Node(int[] puzzles) {
            this.puzzles = puzzles;
        }

        boolean isGoal() {
            return getDistFromGoal1(this) == 0;
        }

        void print() {
            System.out.println("-------------");
            System.out.println(puzzles[0] + " " + puzzles[1] + " " + puzzles[2]);
            System.out.println(puzzles[3] + " " + puzzles[4] + " " + puzzles[5]);
            System.out.println(puzzles[6] + " " + puzzles[7] + " " + puzzles[8]);
            System.out.println("-------------");
        }
    }

    static int getDistFromGoal(Node node) {
        int sum = 0;
        for (int k = 0; k < 9; k++) {
            if (node.puzzles[k] != k + 1 && node.puzzles[k] != 0) sum++;
        }
        return sum;
    }

    static int getDistFromGoal1(Node node) {
        int sum = 0;
        for (int k = 0; k < 9; k++) {
            if (node.puzzles[k] - 1 != k && node.puzzles[k] != 0)
                sum += Math.abs((node.puzzles[k] - 1) / 3 - k / 3) + Math.abs((node.puzzles[k] - 1) % 3 - k % 3);
        }
        return sum;
    }

    // A utility function to count inversions in given array 'arr[]'
    static int getInvCount(int[] arr) {
        int inv_count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                // Value 0 is used for empty space
                if (arr[j] != 0 && arr[i] != 0 && arr[i] > arr[j])
                    inv_count++;
            }
        }
        return inv_count;
    }

    // This function returns true if given 8 puzzle is solvable.
    static boolean isSolvable(int[] puzzle) {
        // Count inversions in given 8 puzzle
        int invCount = getInvCount(puzzle);

        // return true if inversion count is even.
        return (invCount % 2 == 0);
    }
}
