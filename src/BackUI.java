import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * Class BackUI provides the user interface for the  network game.
 *
 * @author  Michael Rinos
 */
public class BackUI implements ModelListener {

    // Hidden data members.
    private static final int GAP = 10;
    private static final int COL = 10;

    private int id;
    private int MahWins;
    private JFrame frame;
    private int TheirWins;
    private String MahName;
    private BackBoard board;
    private String TheirName;
    private JButton diceButton;
    private JButton newGameButton;
    private JTextField myNameField;
    private JTextField whoWonField;
    private ViewListener viewListener;
    private JTextField theirNameField;
    private BackBoardPanel boardPanel;




// Hidden constructors.

    /**
     * Construct a new Back UI.
     */
    private BackUI(String name) {
        frame = new JFrame ("Backgammon -- " + name);
        this.board = new BackBoard();

        JPanel panel = new JPanel();
        panel.setBackground(new Color(210, 180, 140));
        panel.setLayout (new BoxLayout (panel, BoxLayout.X_AXIS));
        frame.add (panel);
        panel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));

        // Panel displaying the board
        boardPanel = new BackBoardPanel (board);
        boardPanel.setAlignmentX (0.5f);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(GAP,GAP,GAP,GAP));
        panel.add(boardPanel);
        panel.add(Box.createRigidArea(new Dimension(5,0)));


        JPanel fieldPanel = new JPanel();
        fieldPanel.setBackground(new Color(210, 180, 140));
        fieldPanel.setLayout (new BoxLayout (fieldPanel, BoxLayout.Y_AXIS));
        panel.add(fieldPanel);

        //My name field
        myNameField = new JTextField (COL);
        myNameField.setEditable(false);
        myNameField.setHorizontalAlignment(JTextField.CENTER);
        myNameField.setAlignmentX(0.5f);
        fieldPanel.add(myNameField);
        fieldPanel.add (Box.createVerticalStrut (GAP));

        //Their name
        theirNameField = new JTextField (COL);
        theirNameField.setEditable (false);
        theirNameField.setHorizontalAlignment(JTextField.CENTER);
        theirNameField.setAlignmentX(0.5f);
        fieldPanel.add(theirNameField);
        fieldPanel.add (Box.createVerticalStrut (GAP));


        // Who won field
        whoWonField = new JTextField (COL);
        whoWonField.setEditable (false);
        whoWonField.setHorizontalAlignment(JTextField.CENTER);
        whoWonField.setAlignmentX(0.5f);
        fieldPanel.add(whoWonField);
        fieldPanel.add (Box.createVerticalStrut (GAP));


        //Dice button
        diceButton = new JButton ("      Roll      ");
        diceButton.setAlignmentX (0.5f);
        diceButton.setFocusable(false);
        fieldPanel.add(diceButton);
        diceButton.setEnabled(false);

        fieldPanel.add(Box.createRigidArea(new Dimension(0,5)));

        // New Game button
        newGameButton = new JButton ("New Game");
        newGameButton.setAlignmentX (0.5f);
        newGameButton.setFocusable(false);
        fieldPanel.add(newGameButton);
        newGameButton.setEnabled(false);

        frame.pack();
        frame.setVisible(true);


        // Listener for the Roll Button
        diceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roll();
            }

        });

        // Listener for the new game button
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (boardPanel.getEnabled())
                    doMouseClick(e);
            }
        });

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            /**
             * @param e Window closing event
             * Calls the quit function and exits
             */
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    viewListener.quit();
                    System.exit(0);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }

    /**
     * @param e
     */
    private synchronized void doMouseClick(MouseEvent e) {
        try {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    int y = boardPanel.clickToColumn(e);
                    int x = boardPanel.clickToRow(e);
                    int location = x * 12 + y;
                    viewListener.placed(this.id, location);
                    break;
            }
        }
        catch (IOException exc)
        {
        }
    }

// Exported operations.

    /**
     * An object holding a reference to a Back UI.
     */
    private static class UIRef {
        public BackUI ui;
    }

    /**
     * Construct a new Back UI.
     */
    public static BackUI create(String name) {
        final UIRef ref = new UIRef();onSwingThreadDo (new Runnable() {
            public void run()
            {
                ref.ui = new BackUI(name);
            }
        });
        return ref.ui;
    }
    public synchronized void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    //Temporary function used in the ui only
    public void roll(){
        try {
            this.viewListener.roll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGame(){
        try {
            this.viewListener.newgame();
        }
        catch (IOException e){

        }
    }
// Hidden operations.

    /**
     * Execute the given runnable object on the Swing thread.
     */
    private static void onSwingThreadDo(Runnable task) {
        try
        {
            SwingUtilities.invokeAndWait (task);
        }
        catch (Throwable exc)
        {

            exc.printStackTrace (System.err);
            System.exit (1);
        }
    }

    @Override
    public synchronized void id(int ids) throws IOException {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                id = ids;
                if (id == 0) {
                    boardPanel.setEnabled(false);
                }
                else boardPanel.setEnabled(true);
                boardPanel.setEnabled(false);
            }
        });
    }

    @Override
    public synchronized void name(int ids, String name) throws IOException {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                if (id == ids){
                    MahName = name + "= ";
                    myNameField.setText(name);
                }
                else {
                    newGameButton.setEnabled(true);
                    TheirName = name + "= ";
                    theirNameField.setText(name);
                }
            }
        });
    }

    @Override
    public synchronized void score(int ids, int Score) throws IOException {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                if (id == ids){
                    MahWins = Score;
                    myNameField.setText(MahName + String.valueOf(MahWins));
                }
                else {
                    TheirWins = Score;
                    theirNameField.setText(TheirName + String.valueOf(TheirWins));
                }
            }
        });
    }

    @Override
    public void distance(int die1, int die2, int howFar) throws IOException{
        this.whoWonField.setText("( " + die1 + ", " + die2 + " )");
        diceButton.setEnabled(false);

        ;
    }

    @Override
    public void move(int id, int x) throws IOException {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                Piece p = new Piece(id);
                board.setSpot(x, p);
                boardPanel.repaint();
            }
        });
    }

    @Override
    public synchronized void reset() throws IOException {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                boardPanel.clear();
                boardPanel.repaint();
            }
        });
    }


    @Override
    public synchronized void turn(int ids) throws IOException {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                if (id == ids){
                    boardPanel.setEnabled(true);
                    diceButton.setEnabled(true);

                }
                else{
                    boardPanel.setEnabled(false);
                    diceButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    public synchronized void win(int ids) throws IOException {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                boardPanel.setEnabled(false);
                if (id == ids){
                    String[] x =MahName.split("=");
                    whoWonField.setText(x[0] + " wins!");
                }
                else if ( ids == 7){
                    whoWonField.setText("Its a draw!");
                }
                else{
                    String[] x =TheirName.split("=");
                    whoWonField.setText(x[0] + " wins!");
                }

                myNameField.setText(MahName + MahWins);
                theirNameField.setText(TheirName + TheirWins);
            }
        });
    }

    @Override
    public synchronized void quit() {
        onSwingThreadDo(new Runnable() {
            @Override
            public void run() {
                System.exit(0);

            }
        });
    }


    public static void main(String[] args){
        BackBoard board = new BackBoard();
        BackUI view = BackUI.create("name");
    }
}