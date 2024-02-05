import java.util.Scanner;

public class queen {
    public static int count = 0;
    public static void main(String[] args) {
        int size = 8;
        String input = "((1 6 8 3 7 4 2 5),8)";
        if(isLegalPosition(input, size)){
            System.out.println("valid");
        }
        else{
            System.out.println("invalid");
        }

        int[] queens = NextLegalPosition(input, size);

        for (int i = 0; i < size; i++) {
            System.out.print(queens[i] + 1 + " ");
        }
        System.out.println();

        for(int i = 0; i < 2; i++){
            int[] board = new int[(i+4)];
            for (int index = 0; index < (i+4); index++) {
                board[index] = -1;
            }
            boolean solution = findSol(board, 0, (i+4));
            if(!solution){
                System.out.println("no solution");
            }
        }
            
        Scanner myScan = new Scanner(System.in);
        System.out.println("What n value would you like to calculate?");
        int n = myScan.nextInt();
        int[] fBoard = new int[n];
        for (int index = 0; index < n; index++) {
            fBoard[index] = -1;
        }
        countSol(fBoard, 0, n);
        System.out.println(queen.count);
        myScan.close();


    }

    public static boolean isLegalPosition(String input, int size){
        int[] queens = getPos(input);
        //boolean[][] board = new boolean[size][size];

        for(int i = 0; i < size; i++){
            if(queens[i] >= 0){
                if(checkDiag(queens, size, i, queens[i]) >= 0){
                    return false;
                }
                if(checkCol(queens, size, i, queens[i]) >= 0){
                    return false;
                }
            }
        }   
        
        return true;
        
    }

    public static boolean isLegal(int[] queens, int size){
        for(int i = 0; i < size; i++){
            if(queens[i] >= 0){
                if(checkDiag(queens, size, i, queens[i]) >= 0){
                    return false;
                }
                if(checkCol(queens, size, i, queens[i]) >= 0){
                    return false;
                }
            }
        }   
        return true;
    }

    public static int[] getPos(String input){
        String posNums = input.replaceAll("[^0-9]", "");
        int size = posNums.charAt(posNums.length()-1) - '0';
        int[] queens = new int[size];

        for(int i = 0; i < size; i++){
            queens[i] = input.charAt((2 + (i * 2))) - '0';
        }

        return editQueens(queens);
    }

    public static int[] editQueens(int[] queens){
        for(int i = 0; i < queens.length; i++){
            queens[i] = (queens[i] - 1);
        }

        return queens;
    }

    public static int checkDiag(int[] queens, int size, int row, int col){
        int dif = row - col;
        for(int i = row+1; i < size; i++){
            if(queens[i] >= 0){
                int dif2 = i - queens[i];
                if(dif == dif2){
                    return i;
                }
            }
        }

        dif = row - (size-col);
        for(int i = row+1; i < size; i++){
            if(queens[i] >= 0){
                int dif2 = i - (size - queens[i]);
                if(dif == dif2){
                    return i;
                }
            }
        }
        return -1;
    }

    public static int checkCol(int[] queens, int size, int row, int col){
        for(int i = row+1; i < size; i++){
            if(queens[i] == col){
                return i;
            }
        }
        return -1;
    }

    public static int[] NextLegalPosition(String input, int size){
        int last;
        int[] queens = getPos(input);

        if(isLegalPosition(input, size)){
            last = findLast(queens, size);
            if(last == -1){
                int find = size-1;
                int[] tempQueens = new int[size];
                for(int i = 0; i < size; i++){
                    tempQueens[i] = queens[i];
                }

                while(find > 0){
                    for(int i = find; i < size; i++){
                        tempQueens[i] = -1;
                    }
                    tempQueens = findNext(tempQueens, find, size, queens[find]);
                    if(queens[find] != tempQueens[find]){
                        find = 0;
                    }
                    find--;
                }
                queens = tempQueens;
            }
            else{
                queens = findNext(queens, last, size, -1);
            }
        }
        else{
            for(int i = 0; i < size; i++){
                if(queens[i] >= 0){
                    if(checkDiag(queens, size, i, queens[i]) >= 0){
                        queens = findNext(queens, checkDiag(queens, size, i, queens[i]), size, -1);
                    }
                    if(checkCol(queens, size, i, queens[i]) >= 0){
                        queens = findNext(queens, checkCol(queens, size, i, queens[i]), size, -1);
                    }
                }
            } 

        }
        return queens;
    }

    public static int[] findNext(int[] queens, int find, int size, int not){
        boolean[] open = new boolean[size];

        for(int i = 0; i < size; i++){
            if(queens[i] >= 0){
                open[queens[i]] = true;
            }
        }

        int dif;
        int tempDif;
        int sum;
        int tempSum;

        for(int i = 0; i < size; i++){
            if(!open[i]){
                int j = 0;
                dif = find - i;
                sum = find + i;
                while((j < find)){
                    tempDif = j - queens[j];
                    tempSum = j + queens[j];
                    if(dif == tempDif || sum == tempSum){
                        j = Integer.MAX_VALUE;
                    }
                    else{
                        j++;
                        if(j == not){
                            j++;
                        }
                    }
                }
                if(j < Integer.MAX_VALUE){
                    queens[find] = i;
                    return queens;
                }
            }
        }   
        queens[find] = -1;
        return findNext(queens, (find-1), size, queens[find-1]);
    }

    public static int findLast(int[] queens, int size){
        for(int i = 0; i < size; i++){
            if(queens[i] < 0){
                return i;
            }
        }
        return -1;
    }

    public static boolean findSol(int[] board, int cur, int size){
        if(cur == size){
            if(isLegal(board, size)){
                for (int j = 0; j < size; j++) {
                    System.out.print(board[j]+1 + " ");
                }
                System.out.println();
                return true;
            }
            else{
                return false;
            }
        }
        else{
            for(int i = 0; i < size; i++){
                if(isLegal(board, size)){
                    board[cur] = i;
                    if(findSol(board, (cur+1), size)){
                        return true;
                    }
                    board[cur] = -1;
                }
            }
        }
        return false;
    }

    public static void countSol(int[] board, int cur, int size){
        if(cur == size){
            if(isLegal(board, size)){
                queen.count++;
            }
        }
        else{
            for(int i = 0; i < size; i++){
                if(isLegal(board, size)){
                    board[cur] = i;
                    countSol(board, (cur+1), size);
                    board[cur] = -1;
                }
                else{
                    return;
                }
            }
        }
    }
}