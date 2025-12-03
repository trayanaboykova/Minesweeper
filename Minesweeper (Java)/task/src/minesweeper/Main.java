package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int SIZE = 9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();

        // Logical field: will contain 'X' (mines), '.', and digits '1'..'8'
        char[][] field = new char[SIZE][SIZE];

        // Initially, no mines placed, everything is '.' (empty)
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = '.';
            }
        }

        // Visible field: what the player sees
        // Initially all unexplored cells: '.'
        char[][] visible = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                visible[i][j] = '.';
            }
        }

        boolean firstMove = true;
        boolean gameOver = false;

        printField(visible);

        while (!gameOver) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            int x = scanner.nextInt();  // column (1..9)
            int y = scanner.nextInt();  // row (1..9)
            String command = scanner.next(); // "mine" or "free"

            int row = y - 1;
            int col = x - 1;

            if ("mine".equals(command)) {
                // Toggle mark only on unexplored cells ('.' or '*')
                if (visible[row][col] == '*') {
                    visible[row][col] = '.';
                } else if (visible[row][col] == '.') {
                    visible[row][col] = '*';
                }
                printField(visible);

                if (hasWon(field, visible, mines)) {
                    System.out.println("Congratulations! You found all the mines!");
                    gameOver = true;
                }

            } else if ("free".equals(command)) {

                // First free: generate mines so this cell is guaranteed not a mine
                if (firstMove) {
                    placeMines(field, mines, row, col, random);
                    calculateNumbers(field);
                    firstMove = false;
                }

                // If we step on a mine -> reveal mines & lose
                if (field[row][col] == 'X') {
                    revealMines(field, visible);
                    printField(visible);
                    System.out.println("You stepped on a mine and failed!");
                    gameOver = true;
                } else {
                    // Explore this cell and possibly expand
                    exploreCell(field, visible, row, col);
                    printField(visible);

                    if (hasWon(field, visible, mines)) {
                        System.out.println("Congratulations! You found all the mines!");
                        gameOver = true;
                    }
                }
            }
        }
    }

    // Place mines randomly, making sure (safeRow, safeCol) is not a mine
    private static void placeMines(char[][] field, int mines, int safeRow, int safeCol, Random random) {
        int placed = 0;
        while (placed < mines) {
            int r = random.nextInt(SIZE);
            int c = random.nextInt(SIZE);

            if (r == safeRow && c == safeCol) {
                continue; // don't place a mine on the first free cell
            }

            if (field[r][c] != 'X') {
                field[r][c] = 'X';
                placed++;
            }
        }
    }

    // Fill numbers for non-mine cells
    private static void calculateNumbers(char[][] field) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == 'X') {
                    continue;
                }

                int count = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) continue;

                        int ni = i + di;
                        int nj = j + dj;

                        if (ni >= 0 && ni < SIZE && nj >= 0 && nj < SIZE) {
                            if (field[ni][nj] == 'X') {
                                count++;
                            }
                        }
                    }
                }

                if (count > 0) {
                    field[i][j] = (char) ('0' + count);
                } else {
                    field[i][j] = '.'; // explicitly empty
                }
            }
        }
    }

    // Explore a cell: if it's empty ('.'), flood-fill; if number, reveal only it
    private static void exploreCell(char[][] field, char[][] visible, int row, int col) {
        // If already explored or marked as mine, we still consider exploring:
        // marks in the expansion area will be overwritten (as per spec)

        if (visible[row][col] == '/' || Character.isDigit(visible[row][col])) {
            return; // already explored
        }

        if (field[row][col] == '.') {
            // Empty cell with no mines around -> '/'
            visible[row][col] = '/';

            // Explore neighbours recursively
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (di == 0 && dj == 0) continue;

                    int ni = row + di;
                    int nj = col + dj;

                    if (ni >= 0 && ni < SIZE && nj >= 0 && nj < SIZE) {
                        if (visible[ni][nj] == '.' || visible[ni][nj] == '*') {
                            // If neighbour is empty, recurse; if number, reveal it
                            if (field[ni][nj] == '.') {
                                exploreCell(field, visible, ni, nj);
                            } else if (Character.isDigit(field[ni][nj])) {
                                visible[ni][nj] = field[ni][nj];
                            }
                        }
                    }
                }
            }
        } else if (Character.isDigit(field[row][col])) {
            visible[row][col] = field[row][col];
        }
    }

    // Reveal all mines on visible board
    private static void revealMines(char[][] field, char[][] visible) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == 'X') {
                    visible[i][j] = 'X';
                }
            }
        }
    }

    // Win condition:
    // - Either all mines correctly marked and no extra marks
    // - OR all non-mine cells are explored (no '.' or '*' on safe cells)
    private static boolean hasWon(char[][] field, char[][] visible, int mineCount) {
        boolean allMinesMarkedCorrectly = true;
        boolean allSafeCellsOpened = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boolean isMine = field[i][j] == 'X';
                char v = visible[i][j];

                if (isMine) {
                    if (v != '*') {
                        allMinesMarkedCorrectly = false;
                    }
                } else {
                    // safe cell
                    if (v == '.' || v == '*') {
                        allSafeCellsOpened = false;
                    }
                    if (v == '*') {
                        allMinesMarkedCorrectly = false; // mark on a safe cell
                    }
                }
            }
        }

        return allMinesMarkedCorrectly || allSafeCellsOpened;
    }

    // Print field with coordinate grid
    private static void printField(char[][] visible) {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(visible[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }
}
