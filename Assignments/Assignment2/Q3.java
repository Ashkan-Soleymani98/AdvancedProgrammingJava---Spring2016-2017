import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Stock{
    int money = 0, date = 0;
    boolean inLottery = false;
    public Stock(int money , int date ,boolean inLottery){
        this.money = money;
        this.date = date;
        this.inLottery = inLottery;
    }
}
class Account{
    int accountID = 0 , customerID = 0 , money = 0 ,transaction = 0;
    boolean hasTransToday = false;
    ArrayList<Stock> stocks = new ArrayList<>();
    public void putMoney(int money , int date){
        this.money += money;
        Stock newStock = new Stock(this.money , date , true);
        stocks.add(newStock);
        transaction++;
        hasTransToday = true;
    }
    public void takeMoney(int money , int date){
        if(money <= this.money) {
            this.money -= money;
            Stock newStock = new Stock(this.money , date ,true);
            stocks.add(newStock);
            transaction++;
            hasTransToday = true;
        }
    }
    public Account(int firstMoney , int accountID , int customerID , int date){
        transaction++;
        money = firstMoney;
        Stock newStock = new Stock(this.money , date , true);
        stocks.add(newStock);
        this.accountID = accountID;
        this.customerID = customerID;
        hasTransToday = true;
    }
    public static Comparator<Account> accountLotteryComparator = new Comparator<Account>() {
        @Override
        public int compare(Account o1, Account o2) {
            Integer o1Money = o1.money , o2Money = o2.money , o1AccountID = o1.accountID , o2AccountID = o2.accountID;
            return o1Money == o2Money ? o1AccountID.compareTo(o2AccountID) : o1Money.compareTo(o2Money);
        }
    };
    public int findingAvgInDay(int date){
        int i = 0 , sumOfStocks = 0, numOfStocks = 0;
        for(i = 0 ; i < stocks.size() ; i++){
            if(stocks.get(i).date == date){
                numOfStocks++;
                sumOfStocks += stocks.get(i).money;
            }
        }
        return numOfStocks == 0 ? 0 : sumOfStocks / numOfStocks;
    }
}
class Customer{
    ArrayList<Account> accounts = new ArrayList<>();
    int customerID = 0, newAccountStartingMoney = 10 , numOfAccounts = 0 , startingDate = 0 , numOfMostAccounts = 0;
    Integer lotterywinner = 0;
    int lotteryPoints = 0;
    public static Comparator<Customer> customerLotteryComparator = new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            Integer sum1 = o1.lotteryPoints , sum2 = o2.lotteryPoints;
            if(o1.lotteryPoints != o2.lotteryPoints)
                return o1.lotterywinner == o2.lotterywinner ? sum2.compareTo(sum1) : o1.lotterywinner.compareTo(o2.lotterywinner);
            Integer a1 = o1.customerID, a2 = o2.customerID;
            return o1.lotterywinner == o2.lotterywinner ? a1.compareTo(a2) : o1.lotterywinner.compareTo(o2.lotterywinner);
        }
    };
    public boolean addAccount(Account account){
        findMinMoneyForNewAccount();
        if(newAccountStartingMoney <= account.money){
            accounts.add(account);
            numOfAccounts++;
            numOfMostAccounts++;
            return true;
        }
        return false;
    }
    public Customer(int customerID , int startingDate){
        this.customerID = customerID;
        this.startingDate = startingDate;
    }
    private void findMinMoneyForNewAccount(){
        int i = 0, minMoney = 10;
        boolean hasAccount = false;
        for(i = 0 ; i < accounts.size() ; i++){
            if(minMoney > accounts.get(i).money || !hasAccount) {
                minMoney = accounts.get(i).money;
                hasAccount = true;
            }
        }
        newAccountStartingMoney = minMoney;
    }
    public void putMoney(int accountID , int money , int date){
        int i = 0;
        for(i = 0 ; i < accounts.size() ; i++){
            if(accounts.get(i).accountID == accountID){
                accounts.get(i).putMoney(money , date);
                break;
            }
        }
    }
    public boolean takeMoney(int accountID , int money , int date){
        int i = 0;
        for(i = 0 ; i < accounts.size() ; i++){
            if(accounts.get(i).accountID == accountID){
                accounts.get(i).takeMoney(money , date);
                if(accounts.get(i).money == 0) {
                    accounts.remove(i);
                    numOfAccounts--;
                    return true;
                }
                break;
            }
        }
        return false;
    }
    public int transactionsFind(){
        int i = 0, sumOfTransactions = 0;
        for(i = 0 ; i < accounts.size() ; i++){
            sumOfTransactions += accounts.get(i).transaction;
        }
        return sumOfTransactions;
    }
    public int stocksFind(){
        int i = 0 , j = 0 , totalStocks = 0 , num = 0;
        for(i = 0 ; i < accounts.size() ; i++){
            for(j = 0 ; j < accounts.get(i).stocks.size() ; j++){
                if(accounts.get(i).stocks.get(j).inLottery == true) {
                    totalStocks += accounts.get(i).stocks.get(j).money;
                    num++;
                }
            }
        }
        if(num == 0)
            return 0;
        return totalStocks / num;
    }
}
class TakingMoney{
    int takingMoney = 0, accountID = 0 , customerID = 0;
    Customer customer = new Customer(0 , 0);
    public TakingMoney(int takingMoney , Customer customer , int accountID){
        this.takingMoney = takingMoney;
        this.customer = customer;
        this.accountID = accountID;
        this.customerID = customer.customerID;
    }
}
class Bank{
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<TakingMoney> customersInLine = new ArrayList<>();
    ArrayList<Account> accounts = new ArrayList<>();
    public void addNewAccount(int customerID , int money , int startingDate){
        int i = 0 ,j = 0 ,k = 0;
        boolean customerExisting = false;
        if(money <= 0)
            return;
        Customer inputCustomer = new Customer(customerID , startingDate);
        for(i = 0 ; i < customers.size() ; i++) {
            if (customers.get(i).customerID == customerID){
                customerExisting = true;
                inputCustomer = customers.get(i);
                break;
            }
        }
        Account newAccount = new Account(money , accounts.size() , customerID ,startingDate);
        boolean isAvailable = inputCustomer.addAccount(newAccount);
        if(!customerExisting)
            customers.add(inputCustomer);
        if(isAvailable)
            accounts.add(newAccount);
        takingMoneyInLine(startingDate);
    }
    public void putMoney(int customerID , int accountID , int money , int date){
        int i = 0 , j = 0 , k = 0;
        if(money <= 0)
            return;
        for(i = 0 ; i < customers.size() ; i++){
            if(customers.get(i).customerID == customerID){
                customers.get(i).putMoney(accountID , money ,date);
                break;
            }
        }
    }
    public void takeMoney(int customerID , int accountID , int money , int date , boolean input){
        int i = 0 , j = 0 , k = 0;
        if(money <= 0)
            return;
        for(i = 0 ; i < customers.size() ; i++){
            if(customers.get(i).customerID == customerID){
                if(customers.get(i).numOfAccounts > 1 || input) {
                    if(customers.get(i).takeMoney(accountID, money , date)){
                        closeAccount(accountID);
                    }
                }
                else{
                    TakingMoney newOne = new TakingMoney(money , customers.get(i) , accountID);
                    customersInLine.add(newOne);
                }
                break;
            }
        }
    }
    private void closeAccount(int accountID){
        int j = 0 , i = 0;
        outer : for(i = 0 ; i < customers.size() ; i++){
            for(j = 0 ; j < customers.get(i).accounts.size() ; j++){
                if(customers.get(i).accounts.get(j).accountID == accountID){
                    customers.get(i).accounts.remove(j);
                    customers.get(i).numOfAccounts--;
                    break outer;
                }
            }
        }
        for(j = 0 ; j < accounts.size() ; j++) {
            if (accounts.get(j).accountID == accountID) {
                accounts.remove(j);
            }
        }
        for(j = 0 ; j < accounts.size() ; j++) {
            if (accounts.get(j).accountID > accountID) {
                accounts.get(j).accountID--;
            }
        }
    }
    private void takingMoneyInLine(int date){
        int i = 0 , j = 0;
        while (i < customersInLine.size() && customersInLine.get(i).customer.numOfAccounts > 1) {
            takeMoney(customersInLine.get(i).customerID, customersInLine.get(i).accountID
                    , customersInLine.get(i).takingMoney, date ,false);
            customersInLine.remove(i);
//            i++;
        }
//        for(j = 0; j < i && j < customersInLine.size(); j++) {
//            customersInLine.remove(j);
//            j--;
//        }
    }
    public void transfer(int customerID , int srcAccountID , int desAccountID , int money , int date){
        int i = 0 , j = 0 , k = 0;
        boolean desAccountExisting = false;
        if(srcAccountID == desAccountID || money <= 0)
            return;
        for(i = 0 ; i < customers.size() ; i++){
            if(customers.get(i).customerID == customerID){
                for(j = 0 ; j < customers.get(i).accounts.size() ; j++){
                    if(customers.get(i).accounts.get(j).accountID == srcAccountID){
                        if(customers.get(i).accounts.get(j).money - money >= 10){
                            for(k = 0 ; k < accounts.size() ; k++){
                                if(accounts.get(k).accountID == desAccountID){
                                    desAccountExisting = true;
                                    putMoney(accounts.get(k).customerID , desAccountID , money , date);
                                    break;
                                }
                            }
                            if(desAccountExisting){
                                takeMoney(customerID, srcAccountID, money , date , true);
                                takeMoney(customerID, srcAccountID, customers.get(i).accounts.get(j).money / 10
                                        , date ,true);
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
    public void lottery(int money , int date){
        int i = 0 , j = 0 , k = 0 ;
        if(money <= 0)
            return;
        for(i = 0 ; i < customers.size() ; i++){
            setLotteryPoint(customers.get(i));
        }
        Collections.sort(customers , Customer.customerLotteryComparator);
        if(customers.get(0).lotterywinner == 0){
            customers.get(0).lotterywinner = 1;
            Collections.sort(customers.get(0).accounts , Account.accountLotteryComparator);
            putMoney(customers.get(0).customerID , customers.get(0).accounts.get(0).accountID , money , date);
        }
        return;
    }
    private void setLotteryPoint(Customer input){
        int i = 0 , j = 0 , k = 0;
        ArrayList<Integer> startingDateArray = new ArrayList<>();
        ArrayList<Integer> stocksArray = new ArrayList<>();
        ArrayList<Integer> transactionsArray = new ArrayList<>();
        ArrayList<Integer> mostAccountsArray = new ArrayList<>();
        input.lotteryPoints = 0;
        for(i = 0 ; i < customers.size() ; i++){
            if(!customers.get(i).equals(input) && customers.get(i).lotterywinner == 0){
                if(customers.get(i).startingDate > input.startingDate
                    && !startingDateArray.contains(customers.get(i).startingDate)){
//                    startingDateArray.add(customers.get(i).startingDate);
                    input.lotteryPoints += 2;
                }
                if(customers.get(i).stocksFind() < input.stocksFind() &&
                        !stocksArray.contains(customers.get(i).stocksFind())){
                    stocksArray.add(customers.get(i).stocksFind());
                    input.lotteryPoints++;
                }
                if(customers.get(i).transactionsFind() < input.transactionsFind() &&
                        !transactionsArray.contains(customers.get(i).transactionsFind())){
                    transactionsArray.add(customers.get(i).transactionsFind());
                    input.lotteryPoints++;
                }
                if(customers.get(i).numOfMostAccounts < input.numOfMostAccounts &&
                        !mostAccountsArray.contains(customers.get(i).numOfMostAccounts)){
                    mostAccountsArray.add(customers.get(i).numOfMostAccounts);
                    input.lotteryPoints++;
                }
            }
        }
    }
    public void decreaseProfit(int date , double profitRate){
        int i = 0 , j = 0 , k = 0;
        double decreasingMoney = 0;
        for(i = 0 ; i < customers.size() ; i++){
            for(j = 0 ; j < customers.get(i).accounts.size() ; j++) {
                decreasingMoney = customers.get(i).accounts.get(j).findingAvgInDay(date) * profitRate;
                Double desMoney = decreasingMoney;
                takeMoney(customers.get(i).customerID , customers.get(i).accounts.get(j).accountID , desMoney.intValue()
                , date , true);
                if(customers.get(i).accounts.get(j).money == 0)
                    closeAccount(customers.get(i).accounts.get(j).accountID);
            }
        }
    }
}
public class SafdarVaBank {
    public static void main(String[] args) {
        Bank bank = new Bank();
        int startingDate = 0, endingDate = 0 ;
        double profitRate = 0;
        Scanner scanner = new Scanner(System.in);
        profitRate = scanner.nextDouble();
        while(endingDate != 7){
//            for(int i = 0 ; i < bank.accounts.size() ; i++){
//                System.out.print(bank.accounts.get(i).money + " ");
//            }
//            System.out.println();
            String input = scanner.nextLine();
//            System.out.println(input);
            String[] inputs = input.split(" ");
            String pattern6 = "start\\s*";
            Pattern compiledPattern6 = Pattern.compile(pattern6);
            Matcher m6 = compiledPattern6.matcher(input);
            if(m6.matches() && endingDate == startingDate){
                startingDate++;
                for(int i = 0 ; i < bank.accounts.size() ; i++) {
                    bank.accounts.get(i).stocks.add(new Stock(bank.accounts.get(i).money, startingDate, false));
                }
                continue;
            }
            String pattern7 = "end\\s*";
            Pattern compiledPattern7 = Pattern.compile(pattern7);
            Matcher m7 = compiledPattern7.matcher(input);
            if(m7.matches() && endingDate == startingDate - 1){
                bank.decreaseProfit(startingDate , profitRate);
                endingDate++;
                continue;
            }
            String pattern1 = "\\d+ open \\d+\\s*";
            Pattern compiledPattern1 = Pattern.compile(pattern1);
            Matcher m1 = compiledPattern1.matcher(input);
            if(m1.matches()){
                bank.addNewAccount(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[2]) ,startingDate);
                continue;
            }
            String pattern2 = "\\d+ put \\d+ \\d+\\s*";
            Pattern compiledPattern2 = Pattern.compile(pattern2);
            Matcher m2 = compiledPattern2.matcher(input);
            if(m2.matches()){
                bank.putMoney(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[3]) , Integer.parseInt(inputs[2])
                        , startingDate);
                continue;
            }
            String pattern3 = "\\d+ take \\d+ \\d+\\s*";
            Pattern compiledPattern3 = Pattern.compile(pattern3);
            Matcher m3 = compiledPattern3.matcher(input);
            if(m3.matches()){
                bank.takeMoney(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[3]) , Integer.parseInt(inputs[2]) ,
                        startingDate , false);
                continue;
            }
            String pattern4 = "\\d+ transfer \\d+ \\d+ \\d+\\s*";
            Pattern compiledPattern4 = Pattern.compile(pattern4);
            Matcher m4 = compiledPattern4.matcher(input);
            if(m4.matches()){
                bank.transfer(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[3]) , Integer.parseInt(inputs[4])
                , Integer.parseInt(inputs[2]) , startingDate);
                continue;
            }
            String pattern5 = "lottery \\d+\\s*";
            Pattern compiledPattern5 = Pattern.compile(pattern5);
            Matcher m5 = compiledPattern5.matcher(input);
            if(m5.matches()){
                bank.lottery(Integer.parseInt(inputs[1]) , startingDate);
                continue;
            }
        }
        for(int i = 0 ; i < bank.accounts.size() ; i++){
            System.out.println(bank.accounts.get(i).money);
//            System.out.print(bank.accounts.get(i).money + " ");
        }
    }
}
