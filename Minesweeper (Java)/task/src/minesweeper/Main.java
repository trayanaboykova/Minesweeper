package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();

        char[][] field = new char[9][9];

        // fill field with safe cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = '.';
            }
        }

        // place mines randomly
        int placed = 0;
        while (placed < mines) {
            int r = random.nextInt(9);
            int c = random.nextInt(9);

            if (field[r][c] != 'X') {
                field[r][c] = 'X';
                placed++;
            }
        }

        // print the field
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}
