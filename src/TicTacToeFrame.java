import javax.swing.*;
import java.awt.*;


public class TicTacToeFrame extends JFrame {

    private static final int ROW = 3;
    private static final int COL = 3;
    private static final TicTacToeButton[][] Board = new TicTacToeButton[ROW][COL];

    private boolean playing = true;
    private String player;

    private final JPanel MainPnl;
    private JPanel DisplayBoard;
    private JPanel DisplayTicTacToeMove;
    private JPanel TicTacToePanel;
    private JPanel ControlMovePanel;

    public TicTacToeFrame() {
        setTitle("TicTacToe");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = (int) screenSize.getWidth() / 2;
        int height = (int) screenSize.getHeight() / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        player = "X";


        MainPnl = new JPanel();
        MainPnl.setLayout(new BorderLayout());


        CreateDisplayBoard();
        MainPnl.add(DisplayBoard, BorderLayout.CENTER);

        CreateControlMovePanel();
        MainPnl.add(ControlMovePanel, BorderLayout.SOUTH);

        CreateTicTacToeTile();
        MainPnl.add(DisplayTicTacToeMove, BorderLayout.EAST);


        add(MainPnl);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    private void CreateTicTacToeTile() {
TicTacToePanel = new JPanel(new GridLayout(ROW, COL));

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                Board[row][col] = new TicTacToeButton(row, col);
                Board[row][col].setFocusable(false);
                Board[row][col].setFocusPainted(false);
                Board[row][col].setBackground(Color.WHITE);
                Board[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                Board[row][col].setVerticalAlignment(SwingConstants.CENTER);
                Board[row][col].setHorizontalTextPosition(SwingConstants.CENTER);
                Board[row][col].setVerticalTextPosition(SwingConstants.BOTTOM);
                int finalRow = row;
                int finalCol = col;
                Board[row][col].addActionListener(e -> handleMove(finalRow, finalCol));

                TicTacToePanel.add(Board[row][col]);
            }
        }

        MainPnl.add(TicTacToePanel, BorderLayout.CENTER);
    }


    public void handleMove(int row, int col) {
        if (!playing) return;

        if (isValidMove(row, col)) {
            Board[row][col].setPlayer(player);

            if (isColWin(player) || isRowWin(player) || isDiagnalWin(player)) {
                JOptionPane.showMessageDialog(this, "Player" + player + " wins!");
                playing = false;
                playagain();
                return;
            } else if (isTie()) {
                JOptionPane.showMessageDialog(this, "Tie!");
                playing = false;
                playagain();
                return;
            }

        player = player.equals("X") ? "O" : "X";
    } else {
                JOptionPane.showMessageDialog(this, "Invalid Move!");
        }
    }

    private void playagain() {

            String[] options = {"Yes", "No"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Would you like to play again?",
                    "TicTacToe",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == 0) {
                clearBoard();
            }
            if (choice == 1) {
                System.exit(0);
            }
    }


    private static boolean isValidMove(int row, int col) {

        return Board[row][col].isEmpty();
    }

    private static boolean isTie() {
        {
            boolean xFlag = false;
            boolean oFlag = false;
            // Check all 8 win vectors for an X and O so
            // no win is possible
            // Check for row ties
            for(int row=0; row < ROW; row++)
            {
                if(Board[row][0].getPlayer().equals("X") ||
                        Board[row][1].getPlayer().equals("X") ||
                        Board[row][2].getPlayer().equals("X"))
                {
                    xFlag = true; // there is an X in this row
                }
                if(Board[row][0].getPlayer().equals("O") ||
                        Board[row][1].getPlayer().equals("O") ||
                        Board[row][2].getPlayer().equals("O"))
                {
                    oFlag = true; // there is an O in this row
                }

                if(! (xFlag && oFlag) )
                {
                    return false; // No tie can still have a row win
                }

                xFlag = oFlag = false;

            }
            // Now scan the columns
            for(int col=0; col < COL; col++)
            {
                if(Board[0][col].getPlayer().equals("X") ||
                        Board[1][col].getPlayer().equals("X") ||
                        Board[2][col].getPlayer().equals("X"))
                {
                    xFlag = true; // there is an X in this col
                }
                if(Board[0][col].getPlayer().equals("O") ||
                        Board[1][col].getPlayer().equals("O") ||
                        Board[2][col].getPlayer().equals("O"))
                {
                    oFlag = true; // there is an O in this col
                }

                if(! (xFlag && oFlag) )
                {
                    return false; // No tie can still have a col win
                }
            }
            // Now check for the diagonals
            xFlag = oFlag = false;

            if(Board[0][0].getPlayer().equals("X") ||
                    Board[1][1].getPlayer().equals("X") ||
                    Board[2][2].getPlayer().equals("X") )
            {
                xFlag = true;
            }
            if(Board[0][0].getPlayer().equals("O") ||
                    Board[1][1].getPlayer().equals("O") ||
                    Board[2][2].getPlayer().equals("O") )
            {
                oFlag = true;
            }
            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a diag win
            }
            xFlag = oFlag = false;

            if(Board[0][2].getPlayer().equals("X") ||
                    Board[1][1].getPlayer().equals("X") ||
                    Board[2][0].getPlayer().equals("X") )
            {
                xFlag =  true;
            }
            if(Board[0][2].getPlayer().equals("O") ||
                    Board[1][1].getPlayer().equals("O") ||
                    Board[2][0].getPlayer().equals("O") )
            {
                oFlag =  true;
            }
            return xFlag && oFlag; // No tie can still have a diag win

            // Checked every vector so I know I have a tie
        }
    }
    private static boolean isColWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (Board[0][col].getPlayer().equals(player) &&
                    Board[1][col].getPlayer().equals(player) &&
                    Board[2][col].getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (Board[row][0].getPlayer().equals(player) &&
                    Board[row][1].getPlayer().equals(player) &&
                    Board[row][2].getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagnalWin(String player) {
        if (Board[0][0].getPlayer().equals(player) &&
                Board[1][1].getPlayer().equals(player) &&
                Board[2][2].getPlayer().equals(player)) {
            return true;
        }
        return Board[0][2].getPlayer().equals(player) &&
                Board[1][1].getPlayer().equals(player) &&
                Board[2][0].getPlayer().equals(player);
    }


    private void CreateDisplayBoard() {
        DisplayBoard = new JPanel(new BorderLayout());
        JPanel displayTA = new JPanel(new BorderLayout());
        if (DisplayTicTacToeMove == null) {
            DisplayTicTacToeMove = new JPanel();}

        displayTA.add(DisplayTicTacToeMove, BorderLayout.CENTER);

        if (TicTacToePanel == null) {
            TicTacToePanel = new JPanel();
        }

        displayTA.add(TicTacToePanel, BorderLayout.SOUTH);
        DisplayBoard.add(displayTA, BorderLayout.CENTER);


    }

    private void CreateControlMovePanel() {

        ControlMovePanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Courier", Font.BOLD, 20));
        quitButton.addActionListener(e -> System.exit(0));
        ControlMovePanel.add(quitButton);



    }




    private void clearBoard() {
    for (int row = 0; row < ROW; row++) {
        for (int col = 0; col < COL; col++) {
            Board[row][col].setPlayer("");
        }
    }
player = "X";
        playing = true;
    }

private static class TicTacToeButton extends JButton {
        private int row;
        private int col;
        private String player;

        public TicTacToeButton(int row, int col) {
            this.row = row;
            this.col = col;
            this.player = "";
            setText("");
        }

        public int getRow() {
            return row;
        }
        public int getCol() {
            return col;
        }
        public String getPlayer() {
            return player;
        }
        public void setPlayer(String player) {
            this.player = player;
            setText(player);
        }
        public boolean isEmpty() {
            return player.isEmpty();
        }
}



}