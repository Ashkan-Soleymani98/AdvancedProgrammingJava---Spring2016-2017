import java.util.Scanner;
import java.util.Arrays;

class Base{
    public int[] stateAgainstRoutes ;
    double x = 0 , y = 0;
    public Base(int numOfRoutes , double x , double y){
        stateAgainstRoutes = new int[numOfRoutes];
        this.x = x;
        this.y = y;
    }

    public void setStateAgainstRoutes(double[][] routes){
        int i = 0;
        for(i = 0 ; i < routes.length ; i++){
            double a = routes[i][0];
            double b = routes[i][1];
            double c = routes[i][2];
            if( a * this.x + b * this.y + c > 0)
                stateAgainstRoutes[i] = 1;
            else
                stateAgainstRoutes[i] = -1;
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double radius = scanner.nextDouble();
        int numOfElephantRoutes = scanner.nextInt();
        int numOfBases = scanner.nextInt();


        Base bases[] = new Base[numOfBases];
        int i = 0 , j = 0;
        double x = 0 , y = 0;

        for( i = 0 ; i < numOfBases ; i++){
            x = scanner.nextDouble();
            y = scanner.nextDouble();
            bases[i] = new Base(numOfElephantRoutes , x , y);
        }

        double routes[][] = new double[numOfElephantRoutes][3];

        for(i = 0 ; i < numOfElephantRoutes ; i++){
            routes[i][0] = scanner.nextDouble();
            routes[i][1] = scanner.nextDouble();
            routes[i][2] = scanner.nextDouble();
        }

        scanner.close();

        for(i = 0 ; i < numOfBases ; i++){
            bases[i].setStateAgainstRoutes(routes);
        }

//        for(i = 0 ; i < numOfBases ; i++){
//            for(j = 0 ;j < numOfElephantRoutes ; j++){
//                System.out.print(bases[i].stateAgainstRoutes[j] + " ");
//            }
//            System.out.println();
//        }

        int numOfStatesWithBase = 1;
        boolean riskprint = false;
        boolean hasSameBase = false;
        for(i = 0 ; i < numOfBases - 1 ; i++){
            for(j = i + 1 ; j < numOfBases ; j++){
                if(Arrays.equals(bases[i].stateAgainstRoutes , bases[j].stateAgainstRoutes)){
                    if(!riskprint)
                        System.out.println("Risk of being detected");
                    riskprint = true;
                    hasSameBase = true;
                }
            }
            if(!hasSameBase){
                numOfStatesWithBase++;
            }
            hasSameBase = false;
        }

        if(!riskprint)
            System.out.println("Alright");
        System.out.println(numOfStatesWithBase);
    }
}
