import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UltimateTicTacToe extends JFrame {
    private SmallBoard[][] boards = new SmallBoard[3][3];
    private char currentPlayer = 'X';
    private int activeRow = -1, activeCol = -1;

    public UltimateTicTacToe() {
        setTitle("Ultimate Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boards[i][j] = new SmallBoard(i, j);
                add(boards[i][j]);
            }
        }

        setVisible(true);
    }

    private class SmallBoard extends JPanel {
        private JButton[][] cells = new JButton[3][3];
        private int boardRow, boardCol;
        private char winner = '-';
        private boolean full = false;

        public SmallBoard(int row, int col) {
            boardRow = row;
            boardCol = col;
            setLayout(new GridLayout(3, 3));
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    JButton cell = new JButton("");
                    cell.setFont(new Font("Arial", Font.BOLD, 20));
                    final int r = i, c = j;
                    cell.addActionListener(e -> makeMove(r, c));
                    cells[i][j] = cell;
                    add(cell);
                }
            }
        }

        public void makeMove(int r, int c) {
            if (!cells[r][c].getText().equals("")) return;
            if (winner != '-' || full) return;
            if (activeRow != -1 && activeCol != -1 && (boardRow != activeRow || boardCol != activeCol)) return;

            cells[r][c].setText(String.valueOf(currentPlayer));
            checkWinner();

            activeRow = r;
            activeCol = c;
            if (boards[activeRow][activeCol].winner != '-' || boards[activeRow][activeCol].full)
                activeRow = activeCol = -1;

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            checkGameWinner();
        }

        private void checkWinner() {
            for (int i = 0; i < 3; i++) {
                if (!cells[i][0].getText().equals("") &&
                    cells[i][0].getText().equals(cells[i][1].getText()) &&
                    cells[i][0].getText().equals(cells[i][2].getText())) {
                    winner = cells[i][0].getText().charAt(0);
                }
                if (!cells[0][i].getText().equals("") &&
                    cells[0][i].getText().equals(cells[1][i].getText()) &&
                    cells[0][i].getText().equals(cells[2][i].getText())) {
                    winner = cells[0][i].getText().charAt(0);
                }
            }
            if (!cells[0][0].getText().equals("") &&
                cells[0][0].getText().equals(cells[1][1].getText()) &&
                cells[0][0].getText().equals(cells[2][2].getText())) {
                winner = cells[0][0].getText().charAt(0);
            }
            if (!cells[0][2].getText().equals("") &&
                cells[0][2].getText().equals(cells[1][1].getText()) &&
                cells[0][2].getText().equals(cells[2][0].getText())) {
                winner = cells[0][2].getText().charAt(0);
            }

            // Check if full
            full = true;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (cells[i][j].getText().equals("")) full = false;

            if (winner != '-') {
                setBackground(winner == 'X' ? Color.CYAN : Color.PINK);
            }
        }
    }

    private void checkGameWinner() {
        char[][] meta = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                meta[i][j] = boards[i][j].winner;

        for (int i = 0; i < 3; i++) {
            if (meta[i][0] != '-' && meta[i][0] == meta[i][1] && meta[i][1] == meta[i][2]) {
                announceWinner(meta[i][0]);
                return;
            }
            if (meta[0][i] != '-' && meta[0][i] == meta[1][i] && meta[1][i] == meta[2][i]) {
                announceWinner(meta[0][i]);
                return;
            }
        }
        if (meta[0][0] != '-' && meta[0][0] == meta[1][1] && meta[1][1] == meta[2][2]) {
            announceWinner(meta[0][0]);
            return;
        }
        if (meta[0][2] != '-' && meta[0][2] == meta[1][1] && meta[1][1] == meta[2][0]) {
            announceWinner(meta[0][2]);
        }
    }

    private void announceWinner(char player) {
        JOptionPane.showMessageDialog(this, "Player " + player + " wins the game!");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UltimateTicTacToe::new);
    }
}
