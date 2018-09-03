import java.util.*;

class Nip{
    Nip(){
        getNip();
    }

    private Random r = new Random();
    List<Integer> weights = new ArrayList<>();
    /To Ci się nie zeruje. Randows ma być w metodzie zadeklarowane. 
    List<Integer> randoms = new ArrayList<>();
    
    //Ta metoda ma się zajmować generowaniem tylko controlNumber. 
    //Druga metoda ma generować randoms. 
    int generateControlNumber() {
        int randomsLength = 0;
        //Tu generujesz sobie liczbę losową.
        int randomNumbers = r.nextInt(8) + 1;
        Integer[] weightArray = {6, 5, 7, 2, 3, 4, 5, 6, 7};

        while (randomsLength < 9){
            //Tu dodajesz sobie tą samą liczbę kilka razy. 
            randoms.add(randomNumbers);
            randomsLength++;
        }
        //Mam wrażenie że weights nigdy się nie zeruje tylko są dodawane kolejne tablice weightArrays.
        weights.addAll(Arrays.asList(weightArray));
        //Tu masz pobrać randoms z tej metody którą napiszesz i dopiero na niej pracować. 
        //POJEDYŃCZA ODPOWIEDZIALNOŚĆ.
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
