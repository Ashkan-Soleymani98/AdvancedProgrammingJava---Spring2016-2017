import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Subject{
    int studentID = 0 , courseID = 0 , unit = 0;
    double grade = 0;
    Subject(int studentID , int courseID , int unit){
        this.courseID = courseID;
        this.studentID = studentID;
        this.unit = unit;
    }
    public static Comparator<Subject> subjectComparator = new Comparator<Subject>() {
        @Override
        public int compare(Subject o1, Subject o2) {
            Double o1Avg = o1.grade;
            Double o2Avg = o2.grade;
            Integer o1ID = o1.studentID;
            Integer o2ID = o2.studentID;
            return Math.abs(o1Avg - o2Avg) < 0.05 ? o2ID.compareTo(o1ID) : o2Avg.compareTo(o1Avg);
        }
    };
}

class Course{
    int courseID = 0, capacity = 15, participatedStudent = 0, unit = 3;
    double avg = 0;
    Course(int courseID , int unit){
        this.courseID = courseID;
        this.unit = unit;
    }
    ArrayList<Subject> subjects = new ArrayList<>();
}

class Student{
    int studentID = 0,  NumOfAllUnits = 0 ;
    double avgGrade = 0;
    int NumOfHavingCourses = 0 , removedCoursesUnit = 0;
    Student(int studentID){
        this.studentID = studentID;
    }
    ArrayList<Subject> subjects = new ArrayList<>();
    public static Comparator<Student> studentIDComparator = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2){
            Integer o1ID = o1.studentID;
            Integer o2ID = o2.studentID;
            return o2ID.compareTo(o1ID);
        }
    };
    public static Comparator<Student> studentGradeComparator = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2){
            Double o1Avg = o1.avgGrade;
            Double o2Avg = o2.avgGrade;
            Integer o1ID = o1.studentID;
            Integer o2ID = o2.studentID;
            return Math.abs(o1Avg - o2Avg) < 0.01 ? o2ID.compareTo(o1ID) : o2Avg.compareTo(o1Avg);
        }
    };
}

class Lecturer{
    int lecturerID = 0;
    ArrayList<Course> courses = new ArrayList<>();
    Lecturer(int lectureID  , ArrayList<Course> courses , String...course){
        this.lecturerID = lectureID;
        for(String retval : course){
            boolean isRepeated = false;
            for(int i = 0 ; i < courses.size() ; i++){
                if(courses.get(i).courseID == Integer.parseInt(retval)){
                    this.courses.add(courses.get(i));
                    isRepeated = true;
                    break;
                }
            }
            if(!isRepeated){
                Course newCourse = new Course(Integer.parseInt(retval) , 3);
                courses.add(newCourse);
                this.courses.add(newCourse);
            }
        }
    }
    public static Comparator<Lecturer> lecturerIDComparator = new Comparator<Lecturer>() {
        @Override
        public int compare(Lecturer o1, Lecturer o2) {
            Integer o1ID = o1.lecturerID;
            Integer o2ID = o2.lecturerID;
            return o2ID.compareTo(o1ID);
        }
    };
}

public class SamanehAmoozesh {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        ArrayList<Student> students = new ArrayList<>(1);
        ArrayList<Lecturer> lecturers = new ArrayList<>(1);
        ArrayList<Course> courses = new ArrayList<>(1);
        int i = 0 , j = 0 , k = 0;
        while( !((input = scanner.nextLine()).equals("endShow")) ){
            String patternm1 = "\\S+( \\d+)+\\s*";
            Pattern compiledpatternm1 = Pattern.compile(patternm1);
            Matcher m1 = compiledpatternm1.matcher(input);
            String patternm2 = "\\S+( \\d{5} \\d+)\\s*";
            Pattern compiledpatternm2 = Pattern.compile(patternm2);
            Matcher m2 = compiledpatternm2.matcher(input);
            String patternm3 = "\\S+ \\d{5}\\s*";
            Pattern compiledpatternm3 = Pattern.compile(patternm3);
            Matcher m3 = compiledpatternm3.matcher(input);
            String[] inputs = input.split(" ");
            if(inputs[0].equals("addStudent") && m1.matches()){
                addStudent(students , inputs[1]);
                continue;
            }
            if(inputs[0].equals("addCourse") && (m2.matches() || m3.matches()) ){
                if(inputs.length == 3)
                    addCourse(courses , inputs[1] , Integer.parseInt(inputs[2]));
                else if(inputs.length == 2)
                    addCourse(courses , inputs[1] , 3);
                continue;
            }
            if(inputs[0].equals("addLecturer") && m1.matches()){
                addLecturer(lecturers , courses, inputs );
                continue;
            }
            String patternn1 = "W \\d+ \\d+\\s*";
            Pattern compiledpatternn1 = Pattern.compile(patternn1);
            Matcher n1 = compiledpatternn1.matcher(input);
            String patternn2 = "\\d+ reg( \\d+)+\\s*";
            Pattern compiledpatternn2 = Pattern.compile(patternn2);
            Matcher n2 = compiledpatternn2.matcher(input);
            String patternn3 = "\\d+ cap \\d+ \\d+\\s*";
            Pattern compiledpatternn3 = Pattern.compile(patternn3);
            Matcher n3 = compiledpatternn3.matcher(input);
            if(n1.matches()){
                W( Integer.parseInt(inputs[1]) , Integer.parseInt(inputs[2]) , students , courses);
                continue;
            }
            if(n2.matches()){
                int[] regCourses = new int[inputs.length - 2];
                for(i = 2 ; i < inputs.length ; i++){
                    regCourses[i - 2] = Integer.parseInt(inputs[i]);
                }
                reg(Integer.parseInt(inputs[0]) , courses , students , regCourses);
                continue;
            }
            if(n3.matches()){
                cap(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[2]) , Integer.parseInt(inputs[3]) , lecturers);
                continue;
            }
            String patternn4 = "endReg\\s*";
            Pattern compiledpatternn4 = Pattern.compile(patternn4);
            Matcher n4 = compiledpatternn4.matcher(input);
            if(n4.matches()) {
                for (i = 0; i < courses.size(); i++) {
                    if (courses.get(i).participatedStudent < 3) {
                        for (j = 0; j < courses.get(i).subjects.size(); j++) {
                            if (courses.get(i).subjects.get(j).courseID == courses.get(i).courseID) {
                                courses.get(i).subjects.remove(j);
                            }
                        }
                        for (j = 0; j < students.size(); j++) {
                            for (k = 0; k < students.get(j).subjects.size(); k++) {
                                if (students.get(j).subjects.get(k).courseID == courses.get(i).courseID) {
                                    students.get(j).subjects.remove(k);
                                    students.get(j).NumOfHavingCourses--;
                                    students.get(j).NumOfAllUnits -= courses.get(i).unit;
                                }
                            }
                        }
                        for (j = 0; j < lecturers.size(); j++) {
                            for (k = 0; k < lecturers.get(j).courses.size(); k++) {
                                if (lecturers.get(j).courses.get(k).courseID == courses.get(i).courseID) {
                                    lecturers.get(j).courses.remove(k);
                                }
                            }
                        }
                        courses.remove(i);
                        i--;
                    }
                }
                for (i = 0; i < students.size(); i++) {
                    if (students.get(i).NumOfAllUnits < 12) {
                        for (j = 0; j < courses.size(); j++) {
                            for (k = 0; k < courses.get(j).subjects.size(); k++) {
                                if (courses.get(j).subjects.get(k).studentID == students.get(i).studentID) {
                                    courses.get(j).subjects.remove(k);
                                }
                            }
                        }
                        students.remove(i);
                        i--;
                    }
                }
            }
            String patternl1 = "\\d+ mark \\d+( \\d+ \\d*\\.?\\d*){2,}\\s*";
            Pattern compiledpatternl1 = Pattern.compile(patternl1);
            Matcher l1 = compiledpatternl1.matcher(input);
            String patternl2 = "\\d+ mark \\d+ \\d*\\.?\\d* -all\\s*";
            Pattern compiledpatternl2 = Pattern.compile(patternl2);
            Matcher l2 = compiledpatternl2.matcher(input);
            String patternl3 = "\\d+ pureOghde( \\d+)+ \\d+\\s*";
            Pattern compiledpatternl3 = Pattern.compile(patternl3);
            Matcher l3 = compiledpatternl3.matcher(input);
            String patternl4 = "\\d+ pureOghde \\d+ -all\\s*";
            Pattern compiledpatternl4 = Pattern.compile(patternl4);
            Matcher l4 = compiledpatternl4.matcher(input);
            String patternl5 = "\\d+ pureOghde -all\\s*";
            Pattern compiledpatternl5 = Pattern.compile(patternl5);
            Matcher l5 = compiledpatternl5.matcher(input);
            String patternl6 = "EZ \\d+\\s*";
            Pattern compiledpatternl6 = Pattern.compile(patternl6);
            Matcher l6 = compiledpatternl6.matcher(input);
            if(l1.matches()){
                double outputs[] = new double[inputs.length - 3];
                for(i = 0 ; i < inputs.length - 3 ; i++){
                    outputs[i] = Double.parseDouble(inputs[i + 3]);
                }
                mark(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[2]) , lecturers , outputs);
                continue;
            }
            if(l2.matches()){
                markall(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[2]) , lecturers , Double.parseDouble(inputs[3]));
                continue;
            }
            if(l3.matches()){
                double outputs[] = new double[2 * (inputs.length - 3)];
                for(i = 0 ; i < outputs.length - 1; i += 2) {
                    outputs[i] = Double.parseDouble(inputs[(i / 2) + 2]);
                    outputs[i + 1] = 9.9;
                    boolean printSath = true;
                    for (j = 0; j < students.size(); j++) {
                        if (students.get(j).studentID == Math.round(outputs[i])) {
                            printSath = false;
                            break;
                        }
                    }
                    if (printSath)
                        System.out.println("sathsathsath");
                }
                mark(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[inputs.length - 1]) , lecturers , outputs);
                continue;
            }
            if(l4.matches()){
                markall(Integer.parseInt(inputs[0]) , Integer.parseInt(inputs[2]) , lecturers , 9.9);
                continue;
            }
            if(l5.matches()){
                allPureOghde(Integer.parseInt(inputs[0]) , lecturers);
                continue;
            }
            if(l6.matches()){
                EZ(Integer.parseInt(inputs[1]) , students , courses);
                continue;
            }
            String patternl7 = "endSem\\s*";
            Pattern compiledpatternl7 = Pattern.compile(patternl7);
            Matcher l7 = compiledpatternl7.matcher(input);
            if(l7.matches()) {
                Avg(students);
                continue;
            }
            String patternk1 = "showCourse \\d+ (\\w)+\\s*";
            Pattern compiledpatternk1 = Pattern.compile(patternk1);
            Matcher k1 = compiledpatternk1.matcher(input);
            String patternk2 = "showAvg \\d+\\s*";
            Pattern compiledpatternk2 = Pattern.compile(patternk2);
            Matcher k2 = compiledpatternk2.matcher(input);
            String patternk3 = "showEZSt \\d+\\s*";
            Pattern compiledpatternk3 = Pattern.compile(patternk3);
            Matcher k3 = compiledpatternk3.matcher(input);
            String patternk4 = "showEZSt -all\\s*";
            Pattern compiledpatternk4 = Pattern.compile(patternk4);
            Matcher k4 = compiledpatternk4.matcher(input);
            String patternk5 = "showRanks \\d+\\s*";
            Pattern compiledpatternk5 = Pattern.compile(patternk5);
            Matcher k5 = compiledpatternk5.matcher(input);
            String patternk6 = "showRanks -all\\s*";
            Pattern compiledpatternk6 = Pattern.compile(patternk6);
            Matcher k6 = compiledpatternk6.matcher(input);
            if(k1.matches()){
                showCourse(Integer.parseInt(inputs[1]) , inputs[2] , lecturers , students , courses);
                continue;
            }
            if(k2.matches()){
                showAvg(Integer.parseInt(inputs[1]) , students);
                continue;
            }
            if(k3.matches()){
                showEZSt(Integer.parseInt(inputs[1]) , courses);
                continue;
            }
            if(k4.matches()){
                showEZStAll(students);
                continue;
            }
            if(k5.matches()){
                showRanks(Integer.parseInt(inputs[1]) , courses);
                continue;
            }
            if(k6.matches()){
                showRanksAll(students);
                continue;
            }
        }
    }

    public static void addCourse(ArrayList<Course> courses , String courseID , int unit){
        Course newCourse = new Course(Integer.parseInt(courseID) , unit);
        for(int i = 0 ; i < courses.size() ; i++){
            if(courses.get(i).courseID == newCourse.courseID)
                return;
        }
        courses.add(newCourse);
    }

    public static void addStudent(ArrayList<Student> students , String studentID){
        for(int i = 0 ; i < students.size() ; i++){
            if(students.get(i).studentID == Integer.parseInt(studentID))
                return;
        }
        Student newStudent = new Student(Integer.parseInt(studentID));
        students.add(newStudent);
    }

    public static void addLecturer(ArrayList<Lecturer> lecturers , ArrayList<Course> courses , String... inputs){
        for(int i = 0 ; i < lecturers.size() ; i++){
            if(lecturers.get(i).lecturerID == Integer.parseInt(inputs[1]))
                return;
        }
        String output[] = new String[inputs.length - 2];
        for(int i = 2 ; i < inputs.length ; i++){
            output[i - 2] = inputs[i];
        }
        Lecturer newLecturer = new Lecturer(Integer.parseInt(inputs[1]) , courses , output);
        lecturers.add(newLecturer);
    }

    public static void W(int CourseID , int StudentID , ArrayList<Student> students , ArrayList<Course> courses){
        int i = 0, j = 0 , k = 0;
        boolean studentExisting = false;
        for(i = 0 ; i < students.size() ; i++){
            if(students.get(i).studentID == StudentID){
                studentExisting = true;
                break;
            }
        }
        if(!studentExisting || (students.get(i).removedCoursesUnit > 3))
            return;
        for(j = 0 ; j < students.get(i).subjects.size() ; j++){
            if(students.get(i).subjects.get(j).courseID == CourseID){
                for(k = 0 ; k < courses.size() ; k++){
                    if(courses.get(k).courseID == CourseID){
                        for(int x = 0 ; x < courses.get(k).subjects.size() ; x++){
                            if(courses.get(k).subjects.get(x).studentID == StudentID){
                                courses.get(k).subjects.remove(x);
                                break;
                            }
                        }
                        break;
                    }
                }
                if(k == courses.size())
                    break;
                students.get(i).removedCoursesUnit += students.get(i).subjects.get(j).unit;
                courses.get(k).participatedStudent--;
                students.get(i).NumOfAllUnits -= students.get(i).subjects.get(j).unit;
                students.get(i).subjects.remove(j);
                students.get(i).NumOfHavingCourses--;
                break;
            }
        }
    }

    public  static void reg(int StudentID , ArrayList<Course> courses , ArrayList<Student> students ,int... CourseIDs){
        int i = 0 , j = 0 , k = 0 , x = 0;
        boolean studentExisting = false;
        for(i = 0 ; i < students.size() ; i++){
            if(students.get(i).studentID == StudentID){
                studentExisting = true;
                break;
            }
        }
        if(!studentExisting)
            return;
        outer : for(k = 0 ; k < CourseIDs.length ; k++) {
            for(x = 0 ; x < students.get(i).subjects.size() ; x++){
                if(students.get(i).subjects.get(x).courseID == CourseIDs[k])
                    continue outer;
            }
            for(j = 0 ; j < courses.size() ; j++) {
                if(courses.get(j).courseID == CourseIDs[k]){
                    if(courses.get(j).capacity > courses.get(j).participatedStudent) {
                        Subject newSubject = new Subject(StudentID , CourseIDs[k] , courses.get(j).unit);
                        students.get(i).subjects.add(newSubject);
                        courses.get(j).subjects.add(newSubject);
                        students.get(i).NumOfHavingCourses++;
                        students.get(i).NumOfAllUnits += courses.get(j).unit;
                        courses.get(j).participatedStudent++;
                    }
                    break;
                }
            }
        }
    }

    public static void cap(int LecturerID , int CourseID , int increasingCap , ArrayList<Lecturer> lecturers){
        int i = 0, k = 0 , j = 0;
        boolean lecturerExisting = false;
        for(i = 0 ; i < lecturers.size() ; i++){
            if(lecturers.get(i).lecturerID == LecturerID){
                lecturerExisting = true;
                break;
            }
        }
        if(!lecturerExisting)
            return;
        for(j = 0 ; j < lecturers.get(i).courses.size() ; j++){
            if(lecturers.get(i).courses.get(j).courseID == CourseID){
                lecturers.get(i).courses.get(j).capacity += increasingCap;
                break;
            }
        }
    }

    public static void mark(int LecturerID , int CourseID , ArrayList<Lecturer> lecturers , double... inputs){
        int i = 0 , j = 0 , k = 0 , x = 0;
        for(x = 0 ; x < inputs.length - 1 ; x += 2){
            for(i = 0 ; i < lecturers.size() ; i++){
                if(lecturers.get(i).lecturerID == LecturerID){
                    for(j = 0 ; j < lecturers.get(i).courses.size() ; j++){
                        if(lecturers.get(i).courses.get(j).courseID == CourseID){
                            for(k = 0 ; k < lecturers.get(i).courses.get(j).subjects.size() ; k++){
                                if(lecturers.get(i).courses.get(j).subjects.get(k).studentID == Math.round(inputs[x])){
                                    lecturers.get(i).courses.get(j).subjects.get(k).grade = inputs[x + 1];
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void markall(int LecturerID , int CourseID , ArrayList<Lecturer> lecturers , double mark){
        int i = 0 ,j = 0 ,k = 0 ,x = 0;
        for(i = 0 ; i < lecturers.size() ; i++){
            if(lecturers.get(i).lecturerID == LecturerID){
                for(j = 0 ; j < lecturers.get(i).courses.size() ; j++){
                    if(lecturers.get(i).courses.get(j).courseID == CourseID){
                        for(k = 0 ; k < lecturers.get(i).courses.get(j).subjects.size() ; k++){
                            lecturers.get(i).courses.get(j).subjects.get(k).grade = mark;
                        }
                    }
                }
                break;
            }
        }
    }

    public  static void allPureOghde(int LectureID , ArrayList<Lecturer> lecturers){
        int i = 0 , j = 0 , k = 0;
        Boolean lecturerExisting = false;
        for(i = 0 ; i < lecturers.size() ; i++){
            if(lecturers.get(i).lecturerID == LectureID){
                lecturerExisting = true;
                break;
            }
        }
        if(!lecturerExisting)
            return;
        for(j = 0 ; j < lecturers.get(i).courses.size() ; j++){
            for(k = 0 ; k < lecturers.get(i).courses.get(j).subjects.size() ; k++){
                lecturers.get(i).courses.get(j).subjects.get(k).grade = 9.9;
            }
        }
    }

    public static void EZ(int StudentID , ArrayList<Student> students , ArrayList<Course> courses){
        int i = 0 ,j = 0, k = 0;
        for(i = 0 ; i < students.size() ; i++){
            if(students.get(i).studentID == StudentID){
                for(j = 0 ; j < courses.size() ; j++){
                    for(k = 0 ; k < courses.get(j).subjects.size() ; k++){
                        if(courses.get(j).subjects.get(k).studentID == StudentID){
                            courses.get(j).subjects.remove(k);
                            k--;
                            courses.get(j).participatedStudent--;
                        }
                    }
                }
                students.remove(i);
                break;
            }
        }
    }

    public static void showCourse(int CourseID , String chosen , ArrayList<Lecturer> lecturers , ArrayList<Student> students ,
                                  ArrayList<Course> courses){
        int i = 0 , j = 0 , k = 0;
        boolean courseExisting = false;
        for(i = 0 ; i < courses.size() ; i++){
            if(courses.get(i).courseID == CourseID){
                courseExisting = true;
                break;
            }
        }
        if(!courseExisting){
            System.out.println("sathsathsath");
            return;
        }
        switch(chosen){
            case "cap":
                System.out.println(courses.get(i).capacity);
                return;
            case "avg":
                for(j = 0 ; j < courses.get(i).subjects.size() ; j++){
                    courses.get(i).avg += courses.get(i).subjects.get(j).grade;
                }
                courses.get(i).avg /= courses.get(i).subjects.size();
                System.out.format("%.1f\n",courses.get(i).avg);
                return;
            case "lt":
                Collections.sort(lecturers , Lecturer.lecturerIDComparator);
                boolean hasLecturerInTotal = false;
                for(i = 0 ; i < lecturers.size() ; i++){
                    boolean hasLecturer = false;
                    for(j = 0 ; j < lecturers.get(i).courses.size() ; j++){
                        if(lecturers.get(i).courses.get(j).courseID == CourseID){
                            System.out.print(lecturers.get(i).lecturerID);
                            hasLecturer = true;
                            break;
                        }
                    }
                    if(hasLecturer)
                        System.out.print(" ");
                    hasLecturerInTotal = hasLecturerInTotal ? true : hasLecturer;
                }
                if(hasLecturerInTotal)
                    System.out.println();
                return;
            case "st":
                Collections.sort(students , Student.studentIDComparator);
                for(i = 0 ; i < students.size() ; i++){
                    for(j = 0 ; j < students.get(i).subjects.size() ; j++){
                        if(students.get(i).subjects.get(j).courseID == CourseID) {
                            System.out.print(students.get(i).studentID + " ");
                            break;
                        }
                    }
                }
                System.out.println();
                return;
        }
    }

    public static void Avg(ArrayList<Student> students){
        int i = 0 , j = 0;
        int sumOfUnits = 0;
        double sumOfGrades = 0;
        for(i = 0 ; i < students.size() ; i++){
            for(j = 0 ; j < students.get(i).subjects.size() ; j++){
                sumOfUnits += students.get(i).subjects.get(j).unit;
                sumOfGrades += students.get(i).subjects.get(j).grade * students.get(i).subjects.get(j).unit;
            }
            students.get(i).avgGrade = sumOfGrades / sumOfUnits;
            sumOfGrades = 0;
            sumOfUnits = 0;
        }
    }

    public static void showAvg(int StudentID , ArrayList<Student> students){
        int i = 0 , j = 0;
        for(i = 0 ; i < students.size() ; i++){
            if(students.get(i).studentID == StudentID){
                System.out.format("%.1f\n" , students.get(i).avgGrade);
                return;
            }
        }
        System.out.println("sathsathsath");
    }

    public static void showEZSt(int CourseID , ArrayList<Course> courses){
        int i = 0 , j = 0 , k = 0;
        boolean finalFailed = false;
        boolean hasCourse = false;
        int numOfPrintfed = 0;
        for(i = 0 ; i < courses.size() ; i++){
            if(courses.get(i).courseID == CourseID){
                Collections.sort(courses.get(i).subjects , Subject.subjectComparator);
                for(j = 0 ; j < courses.get(i).subjects.size() ; j++){
                    if(courses.get(i).subjects.get(j).grade < 10) {
                        System.out.print(courses.get(i).subjects.get(j).studentID + " ");
                        finalFailed = true;
                        numOfPrintfed++;
                    }
                    if(numOfPrintfed >= 3)
                        break;
                }
                hasCourse = true;
                break;
            }
        }
        if(finalFailed)
            System.out.println();
        if(!finalFailed && hasCourse)
            System.out.println("nost");
    }

    public static void showEZStAll(ArrayList<Student> students){
        int i = 0 , j = 0 , k = 0;
        boolean hasFailed = false;
        int numOfPrinted = 0;
        Collections.sort(students , Student.studentGradeComparator);
        for(i = 0 ; i < students.size() ; i++){
            if(students.get(i).avgGrade < 10){
                System.out.print(students.get(i).studentID + " ");
                numOfPrinted++;
                hasFailed = true;
            }
            if(numOfPrinted >= 3)
                break;
        }
        if(hasFailed)
            System.out.println();
        if(!hasFailed)
            System.out.println("nost");
    }

    public static void showRanks(int CourseID , ArrayList<Course> courses){
        int i = 0 , j = 0 , k = 0;
        boolean hasCourse = false;
        for(i = 0 ; i < courses.size() ; i++){
            if(courses.get(i).courseID == CourseID){
                Collections.sort(courses.get(i).subjects , Subject.subjectComparator);
                for(j = 0 ;
                    j < (courses.get(i).subjects.size() >= 3 ? 3 : courses.get(i).subjects.size()) ; j++){
                    System.out.print(courses.get(i).subjects.get(j).studentID + " ");
                    hasCourse = true;
                }
                break;
            }
        }
        if(hasCourse)
            System.out.println();
    }

    public static void showRanksAll(ArrayList<Student> students){
        int i = 0;
        boolean hasStudent = false;
        Collections.sort(students , Student.studentGradeComparator);
        for(i = 0 ; i < (students.size() >= 3 ? 3 : students.size()) ; i++){
            System.out.print(students.get(i).studentID + " ");
            hasStudent = true;
        }
        if(hasStudent)
            System.out.println();
    }
}
