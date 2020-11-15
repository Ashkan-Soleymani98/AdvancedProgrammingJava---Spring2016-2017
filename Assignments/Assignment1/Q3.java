import java.util.Scanner;

public class Main {
    public static boolean isVowel (char input){
        if(input == 'a' || input == 'o' || input == 'i' || input == 'u' || input == 'e')
            return true;
        return false;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String printedWords[] = new String[10000];
        int numOfPrintedWords = 0;
        while (scanner.hasNextLine() == true){
            input = scanner.nextLine();
            input = input.replaceAll("!" , " ");
            input = input.replace('?' , ' ');
            input = input.replace('(' , ' ');
            input = input.replace(')' , ' ');
            input = input.replaceAll("\\." , " ");
            input = input.replaceAll("`" , " ");
            input = input.replace('\"' , ' ');
            input = input.replaceAll("[0-9]" , " ");
            input = input.replaceAll("-" , " ");
            input = input.replace(';' , ' ');
            input = input.replace(',' , ' ');
            input = input.replace('\'' , ' ');
            input = input.replace('ØŒ' , ' ');
            input = input.toLowerCase();
//            System.out.print(input + " ");
            for(String retval : input.split(" ")){
                boolean change = false , repeated = false;
                int i = 0;
                String changedRetval = new String();
                changedRetval = retval;

                /* th -> a */
                if(changedRetval.startsWith("th")){
                    change = true;
                    changedRetval = "d" + retval.substring(2);
                }

                /* vowel */
                for(i = 0 ; i < changedRetval.length() - 1 ; i++){
                    if(isVowel(changedRetval.charAt(i)) && isVowel(changedRetval.charAt(i + 1)) &&
                            changedRetval.charAt(i) != changedRetval.charAt(i + 1) ){
                        String vowelTempString = new String();
                        change = true;
//                        char vowelTEmpChar = changedRetval.charAt(i);
                        vowelTempString = changedRetval.substring(i , i + 1);
//                        System.out.print(vowelTempString);
                        while (i < changedRetval.length() && isVowel(changedRetval.charAt(i))){
//                            System.out.print(vowelTempString);
                            changedRetval = changedRetval.substring(0 , i) + vowelTempString +
                                    ((changedRetval.length() - 1 == i) ? "" : changedRetval.substring(i + 1));
                            i++;
                        }
                    }
                }

                /* consonantal */
                for(i = 1 ; i < changedRetval.length() - 2 ; i++){
                    if(!isVowel(changedRetval.charAt(i)) && changedRetval.charAt(i) == changedRetval.charAt(i + 1)){
                        changedRetval = changedRetval.substring(0 , i + 1) +
                                ((changedRetval.length() - 1 == i + 1) ? "" : changedRetval.substring(i + 2));
                        change = true;
                    }
                }

                /* w -> v */
                if( !changedRetval.startsWith("what") && !changedRetval.startsWith("who") &&
                        !changedRetval.startsWith("whom") && !changedRetval.startsWith("whose") &&
                        !changedRetval.startsWith("where") && !changedRetval.startsWith("when") &&
                        !changedRetval.startsWith("which") && !changedRetval.startsWith("why") ) {

                    if(changedRetval.length() > 0 && changedRetval.charAt(0) == 'w'){
                        change = true;
                        changedRetval = "v" + changedRetval.substring(1);
                    }
                    for(i = 1 ; i < changedRetval.length() ; i++){
                        if(changedRetval.charAt(i) == 'w' && !isVowel(changedRetval.charAt(i - 1))){
                            change = true;
                            changedRetval = changedRetval.substring(0 , i) + "v" +
                                    ((changedRetval.length() - 1 == i) ? "" : changedRetval.substring(i + 1));
                        }
                    }
                }

                /* he & she & it -> hit */
                if(changedRetval.equals("she") || changedRetval.equals("it") ||
                        changedRetval.equals("he")){
                    change = true;
                    changedRetval = "hit";
                }

                /* y -> i */
                if(changedRetval.length() > 0 && changedRetval.charAt(changedRetval.length() - 1) == 'y'){
                    change = true;
                    changedRetval = changedRetval.substring(0 , changedRetval.length() - 1) + "i";
                }

                /* repeating check */
                for(i = 0 ; i < numOfPrintedWords ; i++){
                    if(retval.equals(printedWords[i])){
                        repeated = true;
                        break;
                    }
                }

                if(change && !repeated) {
                    System.out.format("%s -> %s\n", retval, changedRetval);
                    printedWords[numOfPrintedWords] = retval;
                    numOfPrintedWords++;
                }
            }
        }
    }
}


