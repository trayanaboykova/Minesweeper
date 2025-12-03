# 💣 Minesweeper (Java)

A console-based implementation of the classic **Minesweeper** game, where players uncover safe cells, mark suspected mines, and use logical deduction to clear the field without triggering an explosion. The game supports full exploration mechanics, automatic cell expansion, and proper win/lose conditions.

Developed as part of my **[JetBrains Academy](https://www.jetbrains.com/academy/)** learning path, this project demonstrates grid-based game logic, recursion, state management, and robust user input handling in Java.

---

## 🚀 Project Overview

This project is built incrementally to mirror the behavior of the original Minesweeper game:

- **Random Mine Generation** — Creates a new 9×9 minefield each game with a user-defined number of mines.
- **Clue Calculation** — Calculates and displays numbers (1–8) representing adjacent mines.
- **Hidden Game State** — Starts with a fully unexplored field, revealing cells only as the player explores.
- **Mine Marking** — Allows players to mark and unmark suspected mine cells.
- **Recursive Cell Expansion** — Automatically reveals surrounding free cells when an empty area is explored.
- **Game End Logic** — Correctly detects both win and loss scenarios.

---

## 🎯 What I Learned

- 🗺 **Grid-Based Logic:** Managing and traversing a two-dimensional game board.
- 🔄 **Recursion:** Implementing automatic exploration (flood fill) for empty cells.
- 🧠 **Game State Management:** Separating logical and visible representations of the board.
- 🎯 **Input Validation:** Handling multiple user commands and preventing invalid actions.
- 🧩 **Incremental Design:** Building complex functionality in controlled, testable stages.

---

## 🔧 Features

- ✔ Fully interactive console gameplay
- ✔ `mine` and `free` player commands
- ✔ Safe first move — first explored cell is guaranteed not to be a mine
- ✔ Automatic reveal of connected empty areas
- ✔ Coordinate-based grid display
- ✔ Win by:
  - marking all mines correctly, or
  - revealing all safe cells
- ✔ Loss detection when a mine is explored

---

## 🛠️ Technologies Used

[![Java](https://skillicons.dev/icons?i=java&theme=light)](https://www.java.com/)

---

## 🤔 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/trayanaboykova/Minesweeper.git
2. Open the project in your Java IDE (e.g., IntelliJ IDEA)
3. Compile and run the Main.java program
4. Follow the on-screen instructions and enter moves in the format:
   ```bash
   x y free
   x y mine

📈 Learning Outcomes
By completing this project, I:

Strengthened my understanding of recursion and flood-fill algorithms

Learned how to manage complex game state transitions

Improved my ability to design interactive console applications

Practiced defensive programming and edge-case handling

Gained confidence implementing classic games from scratch

🌟 Acknowledgments

Special thanks to JetBrains Academy / Hyperskill for their structured, multi-stage approach to teaching problem-solving through game development — helping turn a classic puzzle game into a powerful learning experience.
