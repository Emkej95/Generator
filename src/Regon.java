import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Regon {

    Regon(){
        getRegon();
    }

    private Random r = new Random();
    private List<Integer> randomNumbers = getRandoms();
    private List<Integer> weights = getWeights();

    private List<Integer> getRandoms(){
        int randomsLength = 0;
        List<Integer> randoms = new ArrayList<>();
        while (randomsLength < 8){
            randoms.add(r.nextInt(9)+1);
            randomsLength++;
        }
        return randoms;
    }

    private List<Integer> getWeights() {
        Integer[] weightArray = {8, 9, 2, 3, 4, 5, 6, 7};
        return new ArrayList<>(Arrays.asList(weightArray));
    }

    private int getControlNumber() {
        return ((randomNumbers.get(0)*weights.get(0)) + (randomNumbers.get(1)*weights.get(1)) +
                (randomNumbers.get(2)*weights.get(2)) + (randomNumbers.get(3)*weights.get(3)) +
                (randomNumbers.get(4)*weights.get(4)) + (randomNumbers.get(5)*weights.get(5)) +
                (randomNumbers.get(6)*weights.get(6)) + (randomNumbers.get(7)*weights.get(7))) % 11;
    }

    String getRegon() {
        String regonNumber = "";
        int controlNumber;

        do {
            controlNumber = getControlNumber();
            if (controlNumber != 10) {
                regonNumber = Arrays.toString(randomNumbers.toArray()) + getControlNumber();
            }
        } while (controlNumber == 10);

        return regonNumber.replaceAll("[^a-zA-Z0-9]","");
    }
}