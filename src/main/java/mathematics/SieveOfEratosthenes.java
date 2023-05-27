package mathematics;

import java.util.ArrayList;
import java.util.List;

public class SieveOfEratosthenes {

    public static void main(String[] args) {
        List<Integer> array = new ArrayList<>();
        List<Integer> primeNumbers = new ArrayList<>();

        System.out.println("Initial array: ");
        for (int i = 1; i <= 100; i++) {
            array.add(i);
            System.out.print(array.get(i-1) + " ");
        }
        System.out.println(" ");


        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) != 0) {
                for (int j = i+1; j< array.size(); j++) {
                    if (array.get(j) % array.get(i) == 0) {
                        array.set(j, 0);
                    }
                }
            }
        }

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) != 0) {
                primeNumbers.add(array.get(i));
            }
        }

        System.out.println("Prime numbers: ");
        for (int i = 0; i < primeNumbers.size(); i++) {
            System.out.print(primeNumbers.get(i) + " ");
        }
    }

    public static int getPrimeNumber (int x, int upperLimit) {
        List<Integer> array = new ArrayList<>();
        List<Integer> primeNumbers = new ArrayList<>();

        for (int i = 1; i <= upperLimit; i++) {
            array.add(i);
        }

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) != 0) {
                for (int j = i+1; j< array.size(); j++) {
                    if (array.get(j) % array.get(i) == 0) {
                        array.set(j, 0);
                    }
                }
            }
        }

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) != 0) {
                primeNumbers.add(array.get(i));
            }
        }

        int numberOfIndexes = primeNumbers.size() - 1;
        int index;
        x = x - 1;
        if (x <= numberOfIndexes) {
            index = x;
        } else {
            x = x % numberOfIndexes;
            index = numberOfIndexes - x;
        }
        int y = primeNumbers.get(index);
//        System.out.println(y);
        return y;
    }

}
