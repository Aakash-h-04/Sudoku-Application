
import java.util.*;

public class Main {
    static class Cell {
        int row,col;
    
        Cell(int r, int c){
            this.row = r;
            this.col = c;
        }
        
    }

    static int sqrt(int n){
        if(n < 0) throw new IllegalArgumentException("-ve!");

        if(n <= 1) return n;

        int s = 0, e = n;

        while(s <= e){
            int mid = s + (e-s)/2;
            if((int)(mid * mid) <= n){
                s = mid + 1;
            }
            else{
                e = mid - 1;
            }
        }

        return e;

    }

    static int n;
    static int row[];
    static int col[];
    static int box[];

    static int MASK[];
    static Cell[] empty;
    static int emptyCount;

    static int boardDim;
    static int boxDim;
    static int VALID_MASK;

    static final char EMPTY = '.';

    static void place(int r, int c, int b, int mask){
        row[r] |= mask;
        col[c] |= mask;
        box[b] |= mask;
    }

    static void remove(int r, int c, int b, int mask){
        row[r] &= ~mask;
        col[c] &= ~mask;
        box[b] &= ~mask;
    }

    static boolean isValid(int r, int c, int b, int mask){
        return (
            (row[r]&mask) == 0
                && 
            (col[c]&mask) == 0
                && 
            (box[b]&mask) == 0
        );
    }

    // static boolean solve(char[][] BOARD, int idx){
    //     if(idx == emptyCount){
    //         return true;
    //     }

    //     int boardDim = (int) BOARD.length; // 4
    //     int boxDim = (int) Main.sqrt(boardDim); // 2

    //     int r = empty[idx].row;
    //     int c = empty[idx].col;

    //     int b = (r/boxDim)*boxDim + (c/boxDim);

    //     char digEnd = (char) ('0' + boardDim);

    //     for(char dig='1' ; dig<=digEnd ; dig++){
    //         int mask = MASK[dig - '0'];

    //         if(!isValid(r, c, b, mask)) continue;

    //         BOARD[r][c] = dig;
    //         place(r, c, b, mask);

    //             if(solve(BOARD, idx+1)) return true;
            
    //         BOARD[r][c] = EMPTY;
    //         remove(r, c, b, mask);
    //     }   
    //     return false;
    // }
    
    static int getB(int r, int c){
        return (r/boxDim)*boxDim + (c/boxDim);
    }

    static int getCandidates(int r, int c, int b){
        return ~(row[r] | col[c] | box[b]) & VALID_MASK;
    }

    static int countBits(int x){

        int cnt = 0;
    
        while(x != 0){
            cnt++;
            x &= (x-1);
        }
    
        return cnt;
    }
    
    static int findBestCell(int idx){
    
        int best = idx;
        int minChoices = n+1;
    
        for(int i=idx;i<emptyCount;i++){
    
            int r = empty[i].row;
            int c = empty[i].col;
            int b = getB(r, c);
    
            int candidates = getCandidates(r, c, b);
    
            int choices = countBits(candidates);
    
            if(choices < minChoices){
    
                minChoices = choices;
                best = i;
    
                if(choices == 1)
                    break;
            }
        }
    
        return best;
    }


    static boolean solve(char[][] BOARD, int idx){

        if(idx == emptyCount)
            return true;
    
        // ---------- MRV ----------
        int best = findBestCell(idx);
    
        Cell tempCell = empty[idx];
        empty[idx] = empty[best];
        empty[best] = tempCell;
        // -------------------------
    
        int r = empty[idx].row;
        int c = empty[idx].col;
    
        int b = getB(r, c);
    
        int candidates = getCandidates(r, c, b);
    
        while(candidates != 0){
    
            // Lowest available digit
            int mask = candidates & (-candidates);
    
            // Convert mask -> digit
            int digit = 1;
            while(MASK[digit] != mask)
                digit++;
    
            BOARD[r][c] = (char)(digit + '0');
    
            place(r, c, b, mask);
    
            if(solve(BOARD, idx + 1))
                return true;
    
            remove(r, c, b, mask);
    
            BOARD[r][c] = EMPTY;
    
            // Remove this candidate
            candidates ^= mask;
        }
    
        // Restore original order (Backtracking)
        tempCell = empty[idx];
        empty[idx] = empty[best];
        empty[best] = tempCell;
    
        return false;
    }
    
    static char BOARD[][];

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        sc.nextLine();
        
        while(true){
            try{
                System.out.print("Enter size of Board: ");
                n = sc.nextInt();
                BOARD = getBoard(n);
                break;
            }
            catch(IllegalArgumentException err){
                System.out.println("Error: " + err.getMessage());
            }
        }

        row = new int[n];
        col = new int[n];
        box = new int[n];

        MASK = new int[n + 1];
        empty = new Cell[n * n];

        boardDim = n;
        boxDim = sqrt(n);
        VALID_MASK = (1 << (n + 1)) - 2;
        
        // char BOARD[][] = new char[n][n];

        // int boxSize = Main.sqrt(n);

        for(int i=1 ; i<=n ; i++){
            MASK[i] = (1 << i);
        }
        
        int cnt = 0;
        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<n ; j++){

                if(BOARD[i][j] == EMPTY){
                    empty[cnt++] = new Cell(i, j);
                    continue;
                }

                int b = getB(i, j);
                int mask = MASK[BOARD[i][j] - '0'];
                place(i, j, b, mask);

            }
        }

        emptyCount = cnt;

        if(solve(BOARD, 0)){
            printBoard(BOARD);
        }
        else{
            System.out.println("\nNo Soln!\n");
        }

    }

    static void printBoard(char BOARD[][]){
        int n = BOARD.length;
        int boxSize = Main.sqrt(n);
    
        int lineLength = n * 2 + boxSize ;
    
        // Create separator line dynamically
        String separator = "";
        for(int i = 0; i < lineLength; i++){
            separator += "-";
        }
    
        System.out.println(separator);
    
        for(int i = 0; i < n; i++){

            if(i>0 && i%boxSize == 0){
                System.out.println(separator);
            }
    
            for(int j = 0; j < n; j++){
    
                if(j > 0 && j % boxSize == 0){
                    System.out.print("| ");
                }
    
                System.out.print(BOARD[i][j] + " ");
            }
    
            System.out.println();
    
        }
    
        System.out.println(separator);
    }

    static char[][] getBoard(int n){

        switch(n){
    
            case 4:
                return new char[][]{
                    {'3', '2', EMPTY, EMPTY},
                    {EMPTY, '4', EMPTY, '3'},
                    {EMPTY, '3', '1', EMPTY},
                    {'2', EMPTY, EMPTY, '4'}
                };
    
    
            case 9:
                return new char[][]{
    
                    {'5', '3', EMPTY, EMPTY, '7', EMPTY, EMPTY, EMPTY, EMPTY},
                    {'6', EMPTY, EMPTY, '1', '9', '5', EMPTY, EMPTY, EMPTY},
                    {EMPTY, '9', '8', EMPTY, EMPTY, EMPTY, EMPTY, '6', EMPTY},
    
                    {'8', EMPTY, EMPTY, EMPTY, '6', EMPTY, EMPTY, EMPTY, '3'},
                    {'4', EMPTY, EMPTY, '8', EMPTY, '3', EMPTY, EMPTY, '1'},
                    {'7', EMPTY, EMPTY, EMPTY, '2', EMPTY, EMPTY, EMPTY, '6'},
    
                    {EMPTY, '6', EMPTY, EMPTY, EMPTY, EMPTY, '2', '8', EMPTY},
                    {EMPTY, EMPTY, EMPTY, '4', '1', '9', EMPTY, EMPTY, '5'},
                    {EMPTY, EMPTY, EMPTY, EMPTY, '8', EMPTY, EMPTY, '7', '9'}
    
                };
    
    
            default:
                throw new IllegalArgumentException("Only 4x4 and 9x9 Sudoku supported!");
        }
    }
    
}