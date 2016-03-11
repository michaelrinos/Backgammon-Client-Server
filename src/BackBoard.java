/**
 * Created by michael on 6/11/2015.
 */
public class BackBoard {

    private MyStack<Piece> p1Bar;
    private MyStack<Piece> p2Bar;
    private MyStack[] Board;
    private int moveCount = 0;
    public static final int MAX_PIECES = 15;
    public static final int BOARD_ROWS = 24;
    public static final Piece p1 = new Piece(0);
    public static final Piece p2 = new Piece(1);


    public BackBoard() {
        Board = new MyStack[BOARD_ROWS];
        for (int i = 0; i < Board.length; i++) {
            Board[i] = new MyStack();
        }
        p1Bar = new MyStack<Piece>();
        p2Bar = new MyStack<Piece>();
        clearBoard();
    }

    public synchronized void clearBoard() {
        for (int i = BOARD_ROWS - 1; i >= 0; i--) {
            MyStack temp = Board[i];
            while (!temp.isEmpty()) {
                temp.pop();
            }
        }
        for (int i = 0; i < 5; i++) {
            Board[11].push(p2);
            Board[5].push(p1);
            Board[BOARD_ROWS-1].push(p1);
            Board[BOARD_ROWS - 7].push(p2);

            if (i<3){
                Board[8].push(p1);
                Board[BOARD_ROWS - 4].push(p2);
            }
            if (i<2){
                Board[0].push(p2);
                Board[12].push(p1);
            }
        }
    }


    public synchronized boolean setSpot(int x, Piece p) {
        MyStack temp = Board[x];

        if (temp.size() == 0) {
            temp.push(p);
            return true;
        }
        else  if (temp.size() == 1){
            Piece pTemp = (Piece)temp.pop();
            if (pTemp.getOwner() == 0){
                p1Bar.push(pTemp);
            }
            else {
                p2Bar.push(pTemp);
            }
            temp.push(p);
            return true;
        }
        else{
            return false;
        }
    }

    public synchronized MyStack getSpot(int x) {
        if (x < 0 || x > BOARD_ROWS)
            return null;

        return new MyStack(Board[x]);
    }

    public synchronized MyStack[] getBoard() {
        return this.Board;
    }

}
