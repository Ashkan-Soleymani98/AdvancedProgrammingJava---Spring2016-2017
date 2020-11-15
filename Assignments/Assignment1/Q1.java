import java.util.Scanner;

class Matrix{
    public int numOfColumns;

    public Matrix(int n){
        numOfColumns = n;
        matrix = new long [numOfColumns][numOfColumns];
    }

    public long matrix[][];


    void input(long array[][] , int n){
//        Scanner scanner = new Scanner (System.in);

//        scanner.close();
    }

    long det(long array[][] , int n){
        if(n == 1)
            return array[0][0];
        if(n == 2)
            return array[0][0] * array[1][1] - array[0][1] * array[1][0];
        long output = 0;
        int i = 0 , j = 0 , k = 0 , t = 0 , l = 0;
        int parity = 1;
        for(i = 0 ; i < n ; i++){
            parity = 1;
            for(j = 0 ; j < i % 2 ; j++){
                parity *= (-1);
            }
            long mat[][] = new long [n - 1][n - 1];
            int t1 = 0 , k1 = 0;
            for(k = 0 ; k < n; k++){
                if(k != 0){
                    for(t = 0 ; t < n; t++) {
                        if (t != i) {
//                            System.out.format("k = %d , t = %d \n mat[%d][%d] = %d \n",k ,t ,k1 ,t1 ,matrix[k][t]);
                            mat[k1][t1] = array[k][t];
                            t1++;
                        }
                    }
                    t1 = 0;
                    k1++;
                }
            }
//            System.out.println(det(mat , n - 1));
            output += parity * array[0][i] * det(mat , n - 1);
        }
        return output;
    }
}

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner (System.in);
        int n = scanner.nextInt();
        Matrix w = new Matrix(n);
        int i = 0 , j = 0;
        for(i = 0 ; i < n ; i++){
            for(j = 0 ; j < n ; j++){
                w.matrix[i][j] = scanner.nextLong();
            }
        }
//        w.input(w.matrix , n);
        scanner.close();
        System.out.println(w.det(w.matrix , n));
    }
}
