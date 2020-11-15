import java.util.Arrays;
import java.util.Scanner;
import java.util.Comparator;

class Team implements Comparable<Team>{
    String name;
    int score = 0;
    int goal = 0;

    public Team (String inputName){
        name = new String();
        name = inputName;
    }

    public void game (int point){
        if(point == 0)
            score += 1;
        else if(point > 0)
            score += 3;
        goal += point;
    }

    public int compareTo(Team compareteam) {

        int comparescore = ((Team)compareteam).score;
        int comparegoal = ((Team)compareteam).goal;
        //ascending order
        return this.score != comparescore ? comparescore - this.score : comparegoal - this.goal;

    }

    public static Comparator<Team> TeamComparator
            = new Comparator<Team>() {

        public int compare(Team team1, Team team2) {

            //ascending order
            return team1.compareTo(team2);

        }
    };
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] teamNames = new String[1000];
        String teamNamesInput = scanner.nextLine();

        int numOfTeams = 0;

        for (String retval: teamNamesInput.split(" ")) {
            retval = retval.length() > 20 ? retval.substring(0 , 17) + "..." : retval;
            while(retval.length() < 20)
                retval = retval + " ";
            teamNames[numOfTeams] = retval;
            numOfTeams++;
//            System.out.println(retval);
        }

        Team[] team = new Team[numOfTeams];

        int i = 0;

        for(i = 0 ; i < numOfTeams ; i++){
            team[i] = new Team(teamNames[i]);
        }

        String result = new String();
        int num = 0;
        String team1 = new String();
        boolean team1Bool = false;
        String team2 = new String();
        boolean team2Bool = false;
        int goal1 = 0;
        boolean goal1Bool = false;
        int goal2 = 0;
        boolean goal2Bool = false;

        int j = 0;

        int numOfInputsInLine = 0;
        while(j < (numOfTeams * (numOfTeams - 1) / 2)){
            result = scanner.nextLine();
            j++;
            numOfInputsInLine = 0;
            team1Bool = false;
            team2Bool = false;
            goal1Bool = false;
            goal2Bool = false;

            for(String temp : result.split(" ")){
                numOfInputsInLine++;
                if(team1Bool == false){
                    temp = temp.length() > 20 ? temp.substring(0 , 17) + "..." : temp;
                    while(temp.length() < 20)
                        temp = temp + " ";
                    team1 = temp;
                    team1Bool = true;
                }
                else if(team2Bool == false){
                    temp = temp.length() > 20 ? temp.substring(0 , 17) + "..." : temp;
                    while(temp.length() < 20)
                        temp = temp + " ";
                    team2 = temp;
                    team2Bool = true;
                }
                else if(goal1Bool == false){
                    goal1 = Integer.parseInt(temp);
                    goal1Bool = true;
                }
                else if(goal2Bool == false){
                    goal2 = Integer.parseInt(temp);
                    goal2Bool = true;
                }
            }

            if(numOfInputsInLine == 3)
                goal2 = goal1;

            for(i = 0 ; i < team.length ; i++){
                if(team[i].name.equals(team1) == true){
                    team[i].game(goal1 - goal2);
                }
                else if(team[i].name.equals(team2) == true){
                    team[i].game(goal2 - goal1);
                }
            }
        }

        Arrays.sort(team);

        for(i = 0 ; i < numOfTeams ; i++){
            String strI = "" + (i + 1);
            while(strI.length() < 3)
                strI = strI + " ";
            String strScore = "" + team[i].score;
            while(strScore.length() < 4)
                strScore = strScore + " ";
            System.out.format("%s%s %s%s\n",strI ,team[i].name ,strScore ,(team[i].goal > 0 ? "+" + team[i].goal : "" + team[i].goal )); ;
        }

        scanner.close();
    }
}
