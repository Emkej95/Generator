/*/import java.util.*;

class Nip{
    Nip(){
        getNip();
    }

    private Random r = new Random();
    private List<Integer> generatedRandoms = getRandoms();
    private List<Integer> listOfWeights = getWeights();

    private int generateControlNumber() {
        return  ((generatedRandoms.get(0) * listOfWeights.get(0)) + (generatedRandoms.get(1) * listOfWeights.get(1)) +
                (generatedRandoms.get(2) * listOfWeights.get(2)) + (generatedRandoms.get(3) * listOfWeights.get(3)) +
                (generatedRandoms.get(4) * listOfWeights.get(4)) + (generatedRandoms.get(5) * listOfWeights.get(5)) +
                (generatedRandoms.get(6) * listOfWeights.get(6)) + (generatedRandoms.get(7) * listOfWeights.get(7)) +
                (generatedRandoms.get(8) * listOfWeights.get(8))) % 11;
    }

    private List<Integer> getRandoms() {
        int randomsLength = 0;
        List<Integer> randoms = new ArrayList<>();
        while (randomsLength < 9){
            randoms.add(r.nextInt(8)+1);
            randomsLength++;
        }
        return randoms;
    }

    private List<Integer> getWeights() {
        Integer[] weightArray = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        return new ArrayList<>(Arrays.asList(weightArray));
    }

    String getNip() {
        String nipNumber = "";
        int controlNumber;

        do {
            controlNumber = generateControlNumber();
            if (controlNumber != 10) {
                nipNumber = Arrays.toString(generatedRandoms.toArray()) + generateControlNumber();
            }
        } while (controlNumber == 10);

        return nipNumber.replaceAll("[^a-zA-Z0-9]","");
    }
}/*/