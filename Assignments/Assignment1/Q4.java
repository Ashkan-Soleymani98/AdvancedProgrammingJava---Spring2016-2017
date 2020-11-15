import java.util.Scanner;

class TicTacToe {
    int [][] ticTacToeTable = new int[3][3];
//    int numOfStartingX = 0 , numOfStartingO = 0;
    int turnindex = 0; /* 0 => X trun & 1 => O turn */
    int WillWinNextPlanx = 3 , WillWinNextPlany = 3;

    public char endGameArrange() {
        int i = 0, sum1 = 0, sum2 = 0, sum3 = 0 , sum4 = 0, j = 0;
        for (i = 0; i < 3; i++) {
            sum1 = 0;
            sum4 = 0;
            for (j = 0; j < 3; j++) {
                sum1 += ticTacToeTable[i][j];
                sum4 += ticTacToeTable[j][i];
            }
            switch (sum1) {
                case 3:
                    return 'X';
                case (-3):
                    return 'O';
            }

            switch (sum4) {
                case 3:
                    return 'X';
                case (-3):
                    return 'O';
            }
            sum2 += ticTacToeTable[i][i];
            sum3 += ticTacToeTable[2 - i][2 - i];
        }
        switch (sum2) {
            case 3:
                return 'X';
            case (-3):
                return 'O';
        }

        switch (sum3) {
            case 3:
                return 'X';
            case (-3):
                return 'O';
        }
        return '-';
    }

    public int whoseTrun(){
        return turnindex == 1 ? -1 : 1;
    }

//    public nextPlan

    public void printtable(){
        int i = 0;
        char a = '!' , b = '!' , c = '!'; /* ! as false response for method */
        for(i = 0 ; i < 3 ; i++){
            switch(ticTacToeTable[i][0]){
                case 1:
                    a = 'X';
                    break;
                case -1:
                    a = 'O';
                    break;
                case 0:
                    a = '-';
                    break;
            }
            switch(ticTacToeTable[i][1]){
                case 1:
                    b = 'X';
                    break;
                case -1:
                    b = 'O';
                    break;
                case 0:
                    b = '-';
                    break;
            }
            switch (ticTacToeTable[i][2]){
                case 1:
                    c = 'X';
                    break;
                case -1:
                    c = 'O';
                    break;
                case 0:
                    c = '-';
            }
            System.out.format("(%c) (%c) (%c)\n",a ,b ,c);
        }
    }

    public void WinWill(int input) {
        int WillWinNextPlanInRowx = 3 , WillWinNextPlanInRowy = 3;
        int WillWinNextPlanInColoumnx = 3 , WillWinNextPlanInColoumny = 3;
        int WillWinNextPlanInMajorObliquex = 3 , WillWinNextPlanInMajorObliquey = 3;
        int WillWinNextPlanInMinorObliquex = 3 , WillWinNextPlanInMinorObliquey = 3;
        int i = 0, j = 0;
        int temp1 = 0, temp2 = 0 , temp3 = 0 , temp4 = 0;
        int coloumnInDanger = -1, rowInDanger = -1;

        for (i = 0; i < 3; i++) {
            temp1 = ticTacToeTable[i][0] + ticTacToeTable[i][1] + ticTacToeTable[i][2];
            if (temp1 == 2 * input) {/* prefrences considered */
                rowInDanger = i;
                break;
            }
        }

        if (rowInDanger >= 0) {
            WillWinNextPlanInRowx = i;
            rowInDanger = -1;
            for (j = 0; j < 3; j++) {
                if (ticTacToeTable[i][j] == 0)
                    WillWinNextPlanInRowy = j;
            }
        }

//        System.out.println("rowInDanger = " + rowInDanger );


        for (i = 0; i < 3; i++) {
            temp2 = ticTacToeTable[0][i] + ticTacToeTable[1][i] + ticTacToeTable[2][i];
            if (temp2 == 2 * input) {
                coloumnInDanger = i;
                break;
            }
        }


        if (coloumnInDanger >= 0) {
            WillWinNextPlanInColoumny = i;
            coloumnInDanger = -1;
            for (j = 0; j < 3; j++) {
                if (ticTacToeTable[j][i] == 0)
                    WillWinNextPlanInColoumnx = j;
            }
        }

        temp3 = ticTacToeTable[0][0] + ticTacToeTable[1][1] + ticTacToeTable[2][2];
        temp4 = ticTacToeTable[0][2] + ticTacToeTable[1][1] + ticTacToeTable[2][0];

        if(temp3 == 2 * input){
            for(i = 0 ; i < 3 ; i++){
                if( ticTacToeTable[i][i] == 0)
                    break;
            }
            WillWinNextPlanInMajorObliquex = i;
            WillWinNextPlanInMajorObliquey = i;
        }

        if(temp4 == 2 * input){
            for(i = 0 ; i < 3 ; i++){
                if( ticTacToeTable[i][2 - i] == 0)
                    break;
            }
            WillWinNextPlanInMinorObliquex = i;
            WillWinNextPlanInMinorObliquey = 2 - i;
        }
//        System.out.format("input = %d\n",input);
//        System.out.format( "WillWinNextPlanInColoumnx = %d, WillWinNextPlanInColoumny = %d ,\n" +
//                        "                WillWinNextPlanInMajorObliquex = %d , WillWinNextPlanInMajorObliquey = %d , \n" +
//                        "                WillWinNextPlanInMinorObliquex = %d , WillWinNextPlanInMinorObliquey = %d , \n" +
//                        "                WillWinNextPlanInRowx = %d , WillWinNextPlanInRowy = %d\n",
//                WillWinNextPlanInColoumnx, WillWinNextPlanInColoumny ,
//                WillWinNextPlanInMajorObliquex , WillWinNextPlanInMajorObliquey,
//                WillWinNextPlanInMinorObliquex , WillWinNextPlanInMinorObliquey ,
//                WillWinNextPlanInRowx , WillWinNextPlanInRowy);

        if(WillWinNextPlanInRowx < WillWinNextPlanInColoumnx) {
            WillWinNextPlanx = WillWinNextPlanInRowx;
            WillWinNextPlany = WillWinNextPlanInRowy;
        }
        else if(WillWinNextPlanInRowx > WillWinNextPlanInColoumnx){
            WillWinNextPlanx = WillWinNextPlanInColoumnx;
            WillWinNextPlany = WillWinNextPlanInColoumny;
        }
        else if(WillWinNextPlanInRowx == WillWinNextPlanInColoumnx){
            if(WillWinNextPlanInRowy <= WillWinNextPlanInColoumny){
                WillWinNextPlanx = WillWinNextPlanInRowx;
                WillWinNextPlany = WillWinNextPlanInRowy;
            }
            else if(WillWinNextPlanInRowy > WillWinNextPlanInColoumny){
                WillWinNextPlanx = WillWinNextPlanInColoumnx;
                WillWinNextPlany = WillWinNextPlanInColoumny;
            }
        }

        if(WillWinNextPlanInMinorObliquex < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInMinorObliquex;
            WillWinNextPlany = WillWinNextPlanInMinorObliquey;
        }
        else if(WillWinNextPlanInMinorObliquey == WillWinNextPlanx){
            if(WillWinNextPlanInMinorObliquey < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInMinorObliquex;
                WillWinNextPlany = WillWinNextPlanInMinorObliquey;
            }
        }

        if(WillWinNextPlanInMajorObliquex < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInMajorObliquex;
            WillWinNextPlany = WillWinNextPlanInMajorObliquey;
        }
        else if(WillWinNextPlanInMajorObliquey == WillWinNextPlanx){
            if(WillWinNextPlanInMajorObliquey < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInMajorObliquex;
                WillWinNextPlany = WillWinNextPlanInMajorObliquey;
            }
        }




    }

    public  void MaybeWillWin(int input){
        int WillWinNextPlanInRowRTLx = 3 , WillWinNextPlanInRowRTLy = 3;
        int WillWinNextPlanInRowLTRx = 3 , WillWinNextPlanInRowLTRy = 3;
        int WillWinNextPlanInColoumnDTUx = 3 , WillWinNextPlanInColoumnDTUy = 3;
        int WillWinNextPlanInColoumnUTDx = 3 , WillWInNextPlanInColoumnUTDy = 3;
        int WillWinNextPlanInMajorObliqueRTLx = 3 , WillWinNextPlanInMajorObliqueRTLy = 3;
        int WillWinNextPlanInMinorObliqueRTLx = 3 , WillWinNextPlanInMinorObliqueRTLy = 3;
        int WillWinNextPlanInMajorObliqueLTRx = 3 , WillWinNextPlanInMajorObliqueLTRy = 3;
        int WillWinNextPlanInMinorObliqueLTRx = 3 , WillWinNextPlanInMinorObliqueLTRy = 3;

        if(input == 1 || input == -1){
            int i = 0 , j = 0;
            outer : for(i = 0 ; i < 3 ; i++) {
                inner: for (j = 0; j < 2; j++) {
                    if (ticTacToeTable[i][j] == input && ticTacToeTable[i][j + 1] == 0) {
                        WillWinNextPlanInRowLTRx = i;
                        WillWinNextPlanInRowLTRy = j + 1;
                        break outer;
                    }
                }
            }

            outer : for(i = 0 ; i < 3 ; i++) {
                inner : for(j = 1 ; j < 3 ; j++){
                    if(ticTacToeTable[i][j] == input && ticTacToeTable[i][j - 1] == 0){
                        WillWinNextPlanInRowRTLx = i;
                        WillWinNextPlanInRowRTLy = j - 1;
                        break outer;
                    }
                }
            }

            outer : for(i = 0 ; i < 3 ; i++) {
                inner : for(j = 0 ; j < 2 ; j++){
                    if(ticTacToeTable[j][i] == input && ticTacToeTable[j + 1][i] == 0){
                        WillWinNextPlanInColoumnUTDx = j + 1;
                        WillWInNextPlanInColoumnUTDy = i;
                        break outer;
                    }
                }
            }

            outer : for(i = 0 ; i < 3 ; i++) {
                inner : for(j = 1 ; j < 3 ; j++){
                    if(ticTacToeTable[j][i] == input && ticTacToeTable[j - 1][i] == 0){
                        WillWinNextPlanInColoumnDTUx = j - 1;
                        WillWinNextPlanInColoumnDTUy = i;
                        break outer;
                    }
                }
            }

            for(i = 0 ; i < 2 ; i++){
                if(ticTacToeTable[i][i] == input && ticTacToeTable[i + 1][i + 1] == 0){
                    WillWinNextPlanInMajorObliqueLTRx = i + 1;
                    WillWinNextPlanInMajorObliqueLTRy = i + 1;
                    break;
                }
            }

            for(i = 1 ; i < 3 ; i++){
                if(ticTacToeTable[i][i] == input && ticTacToeTable[i - 1][i - 1] == 0){
                    WillWinNextPlanInMajorObliqueRTLx = i - 1;
                    WillWinNextPlanInMajorObliqueRTLy = i - 1;
                }
            }

            for(i = 0 ; i < 2 ; i++){
                if(ticTacToeTable[i][2 - i] == input && ticTacToeTable[i + 1][1 - i] == 0){
                    WillWinNextPlanInMinorObliqueRTLx = 1 - i;
                    WillWinNextPlanInMinorObliqueRTLy = 1 - i;
                }
            }

            for(i = 1 ; i < 3 ; i++){
                if(ticTacToeTable[i][2 - i] == input && ticTacToeTable[i - 1][3 - i] == 0) {
                    WillWinNextPlanInMinorObliqueLTRx = 3 - i;
                    WillWinNextPlanInMinorObliqueLTRy = 3 - i;
                }
            }


        }
//        System.out.format("input = %d\n", input);
//        System.out.format( "WillWinNextPlanInColoumnDTUx = %d, WillWinNextPlanInColoumnDTUy = %d ,\n" +
//                        "                WillWinNextPlanInColoumnUTDx = %d , WillWInNextPlanInColoumnUTDy = %d , \n" +
//                        "                WillWinNextPlanInMajorObliqueLTRx = %d , WillWinNextPlanInMajorObliqueLTRy = %d , \n" +
//                        "                WillWinNextPlanInMajorObliqueRTLx = %d , WillWinNextPlanInMajorObliqueRTLy = %d , \n" +
//                        "                WillWinNextPlanInMinorObliqueLTRx = %d , WillWinNextPlanInMinorObliqueLTRy = %d , \n" +
//                        "                WillWinNextPlanInMinorObliqueRTLx = %d , WillWinNextPlanInMinorObliqueRTLy = %d ,\n" +
//                        "                WillWinNextPlanInRowLTRx = %d , WillWinNextPlanInRowLTRy = %d ,WillWinNextPlanInRowRTLx = %d ,\n" +
//                        "                WillWinNextPlanInRowRTLy = %d \n" , WillWinNextPlanInColoumnDTUx, WillWinNextPlanInColoumnDTUy ,
//                WillWinNextPlanInColoumnUTDx , WillWInNextPlanInColoumnUTDy ,
//                WillWinNextPlanInMajorObliqueLTRx , WillWinNextPlanInMajorObliqueLTRy,
//                WillWinNextPlanInMajorObliqueRTLx , WillWinNextPlanInMajorObliqueRTLy ,
//                WillWinNextPlanInMinorObliqueLTRx , WillWinNextPlanInMinorObliqueLTRy ,
//                WillWinNextPlanInMinorObliqueRTLx , WillWinNextPlanInMinorObliqueRTLy ,
//                WillWinNextPlanInRowLTRx , WillWinNextPlanInRowLTRy ,WillWinNextPlanInRowRTLx ,
//                WillWinNextPlanInRowRTLy);
        if(WillWinNextPlanInColoumnDTUx < WillWinNextPlanInColoumnUTDx){
            WillWinNextPlanx = WillWinNextPlanInColoumnDTUx;
            WillWinNextPlany = WillWinNextPlanInColoumnDTUy;
        }
        else if(WillWinNextPlanInColoumnDTUx > WillWinNextPlanInColoumnUTDx){
            WillWinNextPlanx = WillWinNextPlanInColoumnUTDx;
            WillWinNextPlany = WillWInNextPlanInColoumnUTDy;
        }
        else if(WillWinNextPlanInColoumnDTUx == WillWinNextPlanInColoumnUTDx){
            if(WillWinNextPlanInColoumnDTUy < WillWInNextPlanInColoumnUTDy){
                WillWinNextPlanx = WillWinNextPlanInColoumnDTUx;
                WillWinNextPlany = WillWinNextPlanInColoumnDTUy;
            }
            else if(WillWinNextPlanInColoumnDTUy >= WillWInNextPlanInColoumnUTDy){
                WillWinNextPlanx = WillWinNextPlanInColoumnUTDx;
                WillWinNextPlany = WillWInNextPlanInColoumnUTDy;
            }
        }

        if(WillWinNextPlanInRowLTRx < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInRowLTRx;
            WillWinNextPlany = WillWinNextPlanInRowLTRy;
        }
        else if(WillWinNextPlanInRowLTRx == WillWinNextPlanx){
            if(WillWinNextPlanInRowLTRy < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInRowLTRx;
                WillWinNextPlany = WillWinNextPlanInRowLTRy;
            }
        }

        if(WillWinNextPlanInRowRTLx < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInRowRTLx;
            WillWinNextPlany = WillWinNextPlanInRowRTLy;
        }
        else if(WillWinNextPlanInRowRTLx == WillWinNextPlanx){
            if(WillWinNextPlanInRowRTLy < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInRowRTLx;
                WillWinNextPlany = WillWinNextPlanInRowRTLy;
            }
        }

        if(WillWinNextPlanInMajorObliqueLTRx < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInMajorObliqueLTRx;
            WillWinNextPlany = WillWinNextPlanInMajorObliqueLTRy;
        }
        else if(WillWinNextPlanInMajorObliqueLTRx == WillWinNextPlanx){
            if(WillWinNextPlanInMajorObliqueLTRy < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInMajorObliqueLTRx;
                WillWinNextPlany = WillWinNextPlanInMajorObliqueLTRy;
            }
        }

        if(WillWinNextPlanInMajorObliqueRTLx < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInMajorObliqueRTLx;
            WillWinNextPlany = WillWinNextPlanInMajorObliqueRTLy;
        }
        else if(WillWinNextPlanInMajorObliqueRTLx == WillWinNextPlanx){
            if(WillWinNextPlanInMajorObliqueRTLy < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInMajorObliqueRTLx;
                WillWinNextPlany = WillWinNextPlanInMajorObliqueRTLy;
            }
        }

        if(WillWinNextPlanInMinorObliqueLTRx < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInMinorObliqueLTRx;
            WillWinNextPlany = WillWinNextPlanInMinorObliqueLTRy;
        }
        else if(WillWinNextPlanInMinorObliqueLTRx == WillWinNextPlanx){
            if(WillWinNextPlanInMinorObliqueLTRy < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInMinorObliqueLTRx;
                WillWinNextPlany = WillWinNextPlanInMinorObliqueLTRy;
            }
        }

        if(WillWinNextPlanInMinorObliqueRTLx < WillWinNextPlanx){
            WillWinNextPlanx = WillWinNextPlanInMinorObliqueRTLx;
            WillWinNextPlany = WillWinNextPlanInMinorObliqueRTLy;
        }
        else if(WillWinNextPlanInMinorObliqueRTLx == WillWinNextPlanx){
            if(WillWinNextPlanInMinorObliqueRTLy < WillWinNextPlany){
                WillWinNextPlanx = WillWinNextPlanInMinorObliqueRTLx;
                WillWinNextPlany = WillWinNextPlanInMinorObliqueRTLy;
            }
        }

    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        int i = 0 ,j = 0;

        TicTacToe ticTacToe = new TicTacToe();

        for(i = 0 ; i < 3 ; i++){
            input = scanner.nextLine();
            switch (input.charAt(1)){
                case 'X':
                    ticTacToe.turnindex++;
                    ticTacToe.ticTacToeTable[i][0] = 1;
                    break;
                case 'O':
                    ticTacToe.turnindex--;
                    ticTacToe.ticTacToeTable[i][0] = -1;
                    break;
                case '-':
                    ticTacToe.ticTacToeTable[i][0] = 0;
                    break;
            }

            switch (input.charAt(5)){
                case 'X':
                    ticTacToe.turnindex++;
                    ticTacToe.ticTacToeTable[i][1] = 1;
                    break;
                case 'O':
                    ticTacToe.turnindex--;
                    ticTacToe.ticTacToeTable[i][1] = -1;
                    break;
                case '-':
                    ticTacToe.ticTacToeTable[i][1] = 0;
                    break;
            }
            switch (input.charAt(9)){
                case 'X':
                    ticTacToe.turnindex++;
                    ticTacToe.ticTacToeTable[i][2] = 1;
                    break;
                case 'O':
                    ticTacToe.turnindex--;
                    ticTacToe.ticTacToeTable[i][2] = -1;
                    break;
                case '-':
                    ticTacToe.ticTacToeTable[i][2] = 0;
            }
        }

        boolean hasRoom = true;
        outer : while(ticTacToe.endGameArrange() == '-' && hasRoom){
//            ticTacToe.printtable();
//            System.out.println();
            hasRoom = false;

            ticTacToe.WinWill(ticTacToe.whoseTrun());
            if(ticTacToe.WillWinNextPlanx != 3 && ticTacToe.WillWinNextPlany != 3){
                ticTacToe.ticTacToeTable[ticTacToe.WillWinNextPlanx][ticTacToe.WillWinNextPlany] = ticTacToe.whoseTrun();
                ticTacToe.turnindex += ticTacToe.whoseTrun();
                ticTacToe.WillWinNextPlanx = 3;
                ticTacToe.WillWinNextPlany = 3;
//                System.out.println("my 3");
                continue;
            }

            ticTacToe.WinWill(-ticTacToe.whoseTrun());
            if(ticTacToe.WillWinNextPlanx != 3 && ticTacToe.WillWinNextPlany != 3){
                ticTacToe.ticTacToeTable[ticTacToe.WillWinNextPlanx][ticTacToe.WillWinNextPlany] = ticTacToe.whoseTrun();
                ticTacToe.turnindex += ticTacToe.whoseTrun();
                ticTacToe.WillWinNextPlanx = 3;
                ticTacToe.WillWinNextPlany = 3;
//                System.out.println("your 3");
                continue;
            }

            ticTacToe.MaybeWillWin(ticTacToe.whoseTrun());
            if(ticTacToe.WillWinNextPlanx != 3 && ticTacToe.WillWinNextPlany != 3){
                ticTacToe.ticTacToeTable[ticTacToe.WillWinNextPlanx][ticTacToe.WillWinNextPlany] = ticTacToe.whoseTrun();
                ticTacToe.turnindex += ticTacToe.whoseTrun();
                ticTacToe.WillWinNextPlanx = 3;
                ticTacToe.WillWinNextPlany = 3;
//                System.out.println("my 2");
                continue;
            }

            ticTacToe.MaybeWillWin(-ticTacToe.whoseTrun());
            if(ticTacToe.WillWinNextPlanx != 3 && ticTacToe.WillWinNextPlany != 3){
                ticTacToe.ticTacToeTable[ticTacToe.WillWinNextPlanx][ticTacToe.WillWinNextPlany] = ticTacToe.whoseTrun();
                ticTacToe.turnindex += ticTacToe.whoseTrun();
                ticTacToe.WillWinNextPlanx = 3;
                ticTacToe.WillWinNextPlany = 3;
//                System.out.println("your 2");
                continue;
            }

            for(i = 0 ; i < 3 ; i++){
                for(j = 0 ; j < 3 ; j++){
                    if(ticTacToe.ticTacToeTable[i][j] == 0){
                        ticTacToe.ticTacToeTable[i][j] = ticTacToe.whoseTrun();
                        ticTacToe.turnindex += ticTacToe.whoseTrun();
//                        System.out.println("default");
                        continue outer;
                    }
                }
            }

            break;
        }

        ticTacToe.printtable();
        if(ticTacToe.endGameArrange() != '-')
            System.out.format("%c wins!\n",ticTacToe.endGameArrange());
    }
}
