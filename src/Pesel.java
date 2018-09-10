import java.util.Random;

class Pesel {
    private Random r = new Random();

    private String first() {
        return Integer.toString(r.nextInt(2000 - 1900 + 1) + 1900);
    }

    private String second() {
        int b;
        int a = r.nextInt(2);

        do {
            if (a == 0) {
                b = r.nextInt(10);
            }
            else {
                b = r.nextInt(3);
            }
        } while (a+b == 0);

        String day1 = Integer.toString(a);
        String day2 = Integer.toString(b);

        return (day1 + day2);
    }

    private String third() {
        int b;
        int a = r.nextInt(4);

        do {
            if (a == 0 || a == 1 || a == 2) {
                b = r.nextInt(10);
            }
            else {
                b = r.nextInt(2);
            }

        } while (a+b == 0);

        String number1 = Integer.toString(a);
        String number2 = Integer.toString(b);

        return(number1+number2);
    }

    private String fourth() {
        int a = r.nextInt(10000);
        String number = Integer.toString(a);
        int b = number.length();

        if (b == 1) {
            number = "000" + number;
        } else if (b == 2) {
            number = "00" + number;
        } else if (b == 3) {
            number = "0" + number;
        }

        return(number);
    }

    private String control(String str){
        int edit;
        int result = 0;
        int[] tab = new int[10];
        int[] tab1 = {1, 3, 7, 9 ,1, 3 ,7 ,9, 1, 3};

        for (int i = 0; i <= 9; i++) {
            edit = Character.getNumericValue(str.charAt(i));
            tab[i] = edit;
        }

        for (int x = 0; x < 10 ; x++) {
            result += tab[x] * tab1[x];
        }

        result = result % 10;
        result = 10 - result;
        result = result % 10;

        str = Integer.toString(result);

        return(str);
    }

    String getPesel() {
        String year = first();
        String month = second();
        String day = third();
        String number = fourth();

        int control = Integer.parseInt(month);
        int year_sp = Integer.parseInt(year);

        if ((2000 < year_sp) && (year_sp < 2099)) {
            control += 20;
            month = Integer.toString(control);
        } else {
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