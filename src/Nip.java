import java.util.*;

class Nip{
    Nip(){
        getNip();
    }

    private Random r = new Random();
    List<Integer> weights = new ArrayList<>();
    List<Integer> randoms = new ArrayList<>();

    int generateControlNumber() {
        int randomsLength = 0;
        int randomNumbers = r.nextInt(8) + 1;
        Integer[] weightArray = {6, 5, 7, 2, 3, 4, 5, 6, 7};

        while (randomsLength < 9){
            randoms.add(randomNumbers);
            randomsLength++;
        }

        weights.addAll(Arrays.asList(weightArray));

        return  ((randoms.get(0) * weights.get(0)) + (randoms.get(1) * weights.get(1)) +
                (randoms.get(2) * weights.get(2)) + (randoms.get(3) * weights.get(3)) +
                (randoms.get(4) * weights.get(4)) + (randoms.get(5) * weights.get(5)) +
                (randoms.get(6) * weights.get(6)) + (randoms.get(7) * weights.get(7)) +
                (randoms.get(8) * weights.get(8))) % 11;
    }

    String getNip() {
        String nipNumber = "";
        int controlNumber;

        do {
            controlNumber = generateControlNumber();
            if (controlNumber != 10) {
                nipNumber = Arrays.toString(randoms.toArray()) + generateControlNumber();
            }
        } while (controlNumber == 10);

        return nipNumber.replaceAll("[^a-zA-Z0-9]","");
    }
}