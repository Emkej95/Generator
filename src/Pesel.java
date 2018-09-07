import java.util.Random;

class Pesel {
    private Random rand = new Random();

    private String first() {
        int result, x = 1900, y = 2000;
        result = rand.nextInt(y-x+1)+x;
        return Integer.toString(result);
    }

    private String second() {
        int a, b;
        a = rand.nextInt(2);
        do
        {
            if(a == 0) {
                b = rand.nextInt(10);
            }
            else{
                b = rand.nextInt(3);
            }
        }
        while (a+b == 0);

        String day1 = Integer.toString(a);
        String day2 = Integer.toString(b);
        return(day1+day2);
    }

    private String third() {
        int a, b;
        a = rand.nextInt(4);
        do
        {
            if(a == 0 || a == 1 || a == 2) {
                b = rand.nextInt(10);
            }
            else{
                b = rand.nextInt(2);
            }

        }
        while (a+b == 0);

        String number1 = Integer.toString(a);
        String number2 = Integer.toString(b);
        return(number1+number2);
    }

    private String fourth() {
        int a = rand.nextInt(10000);
        String number = Integer.toString(a);
        int b = number.length();
        if (b == 1){
            number = "000" + number;
        }
        else if (b == 2){
            number = "00" + number;
        }
        else if (b == 3){
            number = "0" + number;
        }
        return(number);
    }

    private String control(String str){
        int edit;
        int result = 0;
        int[] tab = new int[10];
        int[] tab1= new int[10];
        for (int i = 0; i <= 9; i++){
            edit = Character.getNumericValue(str.charAt(i));
            tab[i] = edit;
        }
        tab1[0]=1;
        tab1[1]=3;
        tab1[2]=7;
        tab1[3]=9;
        tab1[4]=1;
        tab1[5]=3;
        tab1[6]=7;
        tab1[7]=9;
        tab1[8]=1;
        tab1[9]=3;

        for(int x = 0; x < 10 ; x++){
            result += tab[x] * tab1[x];
        }

        result = result % 10;

        result = 10 - result;

        result = result % 10;
        str = Integer.toString(result);
        return(str);
    }

    String getPesel() {
        String year = first(), month = second(), day = third(), number = fourth();
        int control = Integer.parseInt(month), year_sp = Integer.parseInt(year);

        if (2000<year_sp && year_sp<2099){
            control += 20;
            month = Integer.toString(control);
        }

        else {
            if (control <10) {
                month = "0" + Integer.toString(control);
            }
        }

        year = year.substring(2);
        String enternance = year+month+day+number;
        String str = control(enternance);

        return(year + month + day + number + str);
    }
}