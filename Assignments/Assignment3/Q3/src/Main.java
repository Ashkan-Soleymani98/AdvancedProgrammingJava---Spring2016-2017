import java.util.Scanner;

class Calculus implements Runnable{
    private int starting;
    private int ending;
    private double sum;
    private int t;
    private static boolean firstThreadmiddleReaching = false;
    private int threadNum;

    public Calculus(int starting, int ending , int t , int threadNum) {
        this.starting = starting;
        this.ending = ending;
        this.t = t;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        while (threadNum == 2 && !firstThreadmiddleReaching){
            System.out.println("thread 2 is waiting");
        }
        for(int i = starting * t ; i <= ending * t; i++){
            sum += Math.sin(i * .001) * (.001);
            System.out.println("thread num " + threadNum + " : " + i);
            if(i >= (ending + starting) * t / 2 && threadNum == 1)
                firstThreadmiddleReaching = true;
        }
    }

    public boolean isMiddleReaching() {
        return firstThreadmiddleReaching;
    }

    public double getSum() {
        return sum;
    }

    public int getEnding() {
        return ending;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Please Insert t:");

        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        Calculus firstCalculus = new Calculus(0 , 500 , t , 1);
        Calculus secondCalculus = new Calculus(501 , 1000 , t , 2);

        Thread firstThread = new Thread(firstCalculus);
        Thread secondThread = new Thread(secondCalculus);

        long time1= System.nanoTime();

        firstThread.start();
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long time2 = System.nanoTime();

        System.out.println("Result :" + (firstCalculus.getSum() + secondCalculus.getSum()) );
        System.out.println("takenTime : " + (time2 - time1) + " nano second!");
    }
}

