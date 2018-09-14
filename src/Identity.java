import java.util.Random;

class Identity {

    String getIdentity(){
        Random random = new Random();
        StringBuilder series = new StringBuilder();
        StringBuilder number = new StringBuilder();

        int sum = 0;
        int weight[] = {7,3,1,7,3};

        for (int i = 0; i < 3; i++) {
            series.append((char) (random.nextInt('Z' - 'A') + 'A'));
            sum += weight[i] * (series.charAt(i)-'A'+10);
        }

        for (int i = 0; i < 5; i++) {
            number.append((char) (random.nextInt('9' - '0') + '0'));
            sum += weight[i] * (number.charAt(i)-'0');
        }
        return series.toString() + sum % 10 + number;
    }
}