package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();

        // Logical field: contains 'X' (mines), '.', and digits '1'..'8'
        char[][] field = new char[9][9];

        // 1) Fill field with safe cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = '.';
            }
        }

        // 2) Place mines randomly
        int placed = 0;
        while (placed < mines) {
            int r = random.nextInt(9);
            int c = random.nextInt(9);

            if (field[r][c] != 'X') {
                field[r][c] = 'X';
                placed++;
            }
        }

        // 3) Calculate numbers for non-mine cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (field[i][j] == 'X') {
                    continue; // skip mines
                }

                int count = 0;

                // check all 8 neighbors
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) {
                            continue; // skip self
                        }

                        int ni = i + di;
                        int nj = j + dj;

                        // stay inside the 9x9 field
                        if (ni >= 0 && ni < 9 && nj >= 0 && nj < 9) {
                            if (field[ni][nj] == 'X') {
                                count++;
                            }
                        }
                    }
                }

                if (count > 0) {
                    field[i][j] = (char) ('0' + count);
                }
            }
        }

        // 4) Visible field: what the player actually sees
        //    - digits are shown
        //    - other cells are '.' at the start
        char[][] visible = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Character.isDigit(field[i][j])) {
                    visible[i][j] = field[i][j];  // show numbers
                } else {
                    visible[i][j] = '.';          // hidden (could be mine or empty)
                }
            }
        }

        // 5) Print initial field
        printField(visible);

        // 6) Game loop: mark/unmark until all mines are correctly marked
        while (true) {
            System.out.print("Set/delete mines marks (x and y coordinates): ");
            int x = scanner.nextInt(); // column
            int y = scanner.nextInt(); // row

            int row = y - 1;
            int col = x - 1;

            // If it's a number, cannot mark here
            if (Character.isDigit(field[row][col])) {
                System.out.println("There is a number here!");
                continue;
            }

            // Toggle mark:
            // '.' -> '*', '*' -> '.'
            if (visible[row][col] == '*') {
                visible[row][col] = '.';
            } else {
                visible[row][col] = '*';
            }

            printField(visible);

            if (hasWon(field, visible)) {
                System.out.println("Congratulations! You found all the mines!");
                break;
            }
        }
    }

    // Print field with coordinate grid
    private static void printField(char[][] visible) {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < 9; j++) {
                System.out.print(visible[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    // Win condition:
    // - all mines ('X') are marked with '*'
    // - no non-mine cells are marked with '*'
    private static boolean hasWon(char[][] field, char[][] visible) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean isMine = field[i][j] == 'X';
                boolean isMarked = visible[i][j] == '*';

                if (isMine && !isMarked) {
                    return false; // unmarked mine
                }
                if (!isMine && isMarked) {
                    return false; // extra mark on a safe cell
                }
            }
        }
        return true;
    }
}
