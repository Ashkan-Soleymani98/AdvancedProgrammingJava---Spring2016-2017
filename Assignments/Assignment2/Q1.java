import java.util.Scanner;

class FarmPlace{
    int age = 0;
    int poisonTimes = 0;
    FarmPlace(int age){
        this.age = age;
    }
    boolean isMargin = false;
    boolean newborn = false;
    void spawn(int x , int y , FarmPlace farm[][] , FarmPlace input){
        int i = 0 , j = 0;
        for(i = x - 1 ; i <= x + 1 ; i++){
            for(j = y - 1 ; j <= y + 1 ; j++){
                if( (!farm[i][j].isMargin)  && (!(farm[i][j] instanceof Somagh || farm[i][j] instanceof Weed) ||
                        farm[i][j].newborn == true ) && ((i + j == x + y - 1) || (i + j == x + y + 1)) ){
                    if(input instanceof Weed && farm[i][j].newborn != true) {
                        farm[i][j] = new Weed(0);
                        farm[i][j].newborn = true;
                    }
                    else if(input instanceof Somagh ) {
                        farm[i][j] = new Somagh(0);
                        farm[i][j].newborn = true;
                    }
                }
            }
        }
    }
}

class Somagh extends FarmPlace{
    //    int lifeTime = 4;
    Somagh(int age){
        super(age);
    }

}

class Weed extends FarmPlace{
    //    int lifeTime = 5;
    Weed(int age){
        super(age);
    }
}

public class SomaghFarm {
    public static void main(String[] args) {
        int m , n;
        Scanner scanner = new Scanner(System.in);
        m = scanner.nextInt();
        n = scanner.nextInt();
        int numOfRaisedSomagh = 0;
        int dayNum = 0;
        int somaghNum = 0;
        int weedNum = 0;
        int i = 0, j = 0;
        FarmPlace farm[][] = new FarmPlace[m + 2][n + 2];
        for(i = 0 ; i < m + 2 ; i++) {
            for (j = 0; j < n + 2; j++) {
                farm[i][j] = new FarmPlace(0);
                if (i == 0 || i == m + 1 || j == 0 || j == n + 1)
                    farm[i][j].isMargin = true;
            }
        }

        int x = 0 , y = 0 , age = 0;
        somaghNum = scanner.nextInt();
        for(i = 0 ; i < somaghNum ; i++){
            x = scanner.nextInt();
            y = scanner.nextInt();
            age = scanner.nextInt();
            if(!farm[x][y].isMargin)
                farm[x][y] = new Somagh(age);
        }

        weedNum = scanner.nextInt();
        for(i = 0 ; i < weedNum ; i++){
            x = scanner.nextInt();
            y = scanner.nextInt();
            age = scanner.nextInt();
            if(!farm[x][y].isMargin)
                farm[x][y] = new Weed(age);
        }

        int wantedDay = scanner.nextInt();
        for(dayNum = 0 ; dayNum <= wantedDay ; dayNum++){
            for(i = 1 ; i <= m ; i++){
                for(j = 1 ; j <= n ; j++) {
                    if (farm[i][j] instanceof Somagh) {
                        if (farm[i][j].age >= 4) {
                            numOfRaisedSomagh++;
                            farm[i][j].spawn(i, j, farm, farm[i][j]);
                            farm[i][j] = new Somagh(0);
                        }
                    }
                    else if (farm[i][j] instanceof Weed) {
                        if (farm[i][j].age >= 5) {
                            farm[i][j].spawn(i, j, farm, farm[i][j]);
                            farm[i][j] = new Weed(0);
                        }
                    }
                }
            }

            for(i = 1 ; i <= m ; i++){
                for(j = 1 ; j <= n ; j++){
                    farm[i][j].age++;
                    farm[i][j].newborn = false;
                }
            }

            int k = 0, sumOfWeedInRows = 0 , tempSumOfWeedInRows = 0;
            for(j = 1 ; j < m + 1 ; j++){
                for(k = 1 ; k < n + 1 ; k++) {
                    if(farm[j][k] instanceof Weed)
                        tempSumOfWeedInRows++;
                }
                if(sumOfWeedInRows < tempSumOfWeedInRows)
                    sumOfWeedInRows = tempSumOfWeedInRows;

                tempSumOfWeedInRows = 0;
            }

            for(j = 1 ; j < m + 1 ; j++) {
                for (k = 1; k < n + 1; k++) {
                    if (farm[j][k] instanceof Weed)
                        tempSumOfWeedInRows++;
                }
                if(tempSumOfWeedInRows == sumOfWeedInRows)
                    break;
                tempSumOfWeedInRows = 0;
            }

            for(i = 1 ; i < n + 1 ; i++){
                farm[j][i].poisonTimes++;
                if(farm[j][i] instanceof Somagh || farm[j][i].poisonTimes == 2)
                    farm[j][i] = new FarmPlace(0);
            }

            int sumOfSomaghsInColoumn = 0 , tempSumOfSomaghsInColoumn = 0;
            for(j = 1 ; j < n + 1 ; j++){
                for(k = 1 ; k < m + 1 ; k++) {
                    if(farm[k][j] instanceof Somagh)
                        tempSumOfSomaghsInColoumn++;
                }
                if(tempSumOfSomaghsInColoumn > sumOfSomaghsInColoumn)
                    sumOfSomaghsInColoumn = tempSumOfSomaghsInColoumn;
                tempSumOfSomaghsInColoumn = 0;
            }

            for(j = 1 ; j < n + 1 ; j++){
                for(k = 1 ; k < m + 1 ; k++){
                    if(farm[k][j] instanceof Somagh)
                        tempSumOfSomaghsInColoumn++;
                }
                if (tempSumOfSomaghsInColoumn == sumOfSomaghsInColoumn)
                    break;
                tempSumOfSomaghsInColoumn = 0;
            }

            for(i = 0 ; i < m + 1 ; i++){
                if(farm[i][j] instanceof Somagh || farm[i][j] instanceof Weed)
                    farm[i][j].age++;
            }
        }
        System.out.print(numOfRaisedSomagh);
    }
}

