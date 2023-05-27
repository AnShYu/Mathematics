package mathematics;

import java.math.BigDecimal;
import java.util.*;

public class ArithmeticCoding {

    public static class Interval {
        private BigDecimal startingValue;
        private BigDecimal endingValue;

        public Interval(BigDecimal startingValue, BigDecimal endingValue) {
            this.startingValue = startingValue;
            this.endingValue = endingValue;
        }

        public BigDecimal getStartingValue() {
            return startingValue;
        }

        public BigDecimal getEndingValue() {
            return endingValue;
        }

        public void setStartingValue(BigDecimal startingValue) {
            this.startingValue = startingValue;
        }

        public void setEndingValue(BigDecimal endingValue) {
            this.endingValue = endingValue;
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "startingValue=" + startingValue +
                    ", endingValue=" + endingValue +
                    '}';
        }
    }

    public static class CharProbability implements Comparable<CharProbability> {
        private char ch;
        private BigDecimal probability;

        public CharProbability(char ch, BigDecimal probability) {
            this.ch = ch;
            this.probability = probability;
        }

        public char getCh() {
            return ch;
        }

        public BigDecimal getProbability() {
            return probability;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CharProbability that = (CharProbability) o;
            return ch == that.ch;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ch, probability);
        }

        @Override
        public String toString() {
            return "CharProbability{" +
                    "ch=" + ch +
                    ", probability=" + probability +
                    '}';
        }

        @Override
        public int compareTo(CharProbability o) {
            if (this.getProbability().compareTo(o.getProbability()) > 0) {return -1;
            } else if (this.getProbability().compareTo(o.getProbability()) < 0) {return 1;
            } else return 0;
        }
    }

    public static class Message {
        private BigDecimal resultingPoint;
        private List<CharProbability> probabilities;
        private int initialTextLength;

        public Message(BigDecimal resultingPoint, List<CharProbability> probabilities, int initialTextLength) {
            this.resultingPoint = resultingPoint;
            this.probabilities = probabilities;
            this.initialTextLength = initialTextLength;
        }

        public BigDecimal getResultingPoint() {
            return resultingPoint;
        }

        public List<CharProbability> getProbabilities() {
            return probabilities;
        }

        public int getInitialTextLength() {
            return initialTextLength;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите сообщение для кодирования:");
        String inputData = scanner.nextLine();

        Message message = encode(inputData);

        BigDecimal resultingPoint = message.getResultingPoint();
        System.out.println("Кодирующая точка: " + resultingPoint);

        List<Character> decodedLine = decode(message);
        System.out.println("Декодировка: " + decodedLine);
    }

    public static Message encode (String inputData) {
        Interval resultingInterval = new Interval(new BigDecimal(0), new BigDecimal(1));
        char[] chars = inputData.toCharArray();
        List<CharProbability> probabilities = new ArrayList<>();

        Map<Character, Integer> numbersOfUseOfChars = new HashMap<>();
        for (Character ch: chars) {
            if (numbersOfUseOfChars.containsKey(ch)) {
                numbersOfUseOfChars.replace(ch, numbersOfUseOfChars.get(ch) + 1);
            } else {
                numbersOfUseOfChars.put(ch, 1);
            }
        }

        for (Character ch: numbersOfUseOfChars.keySet()) {
            double prob = (double) numbersOfUseOfChars.get(ch) / chars.length;
            BigDecimal probability = new BigDecimal(prob);
            CharProbability charProbability = new CharProbability(ch, probability);
            probabilities.add(charProbability);
        }

//        System.out.println(probabilities);
        Collections.sort(probabilities);
//        System.out.println(probabilities);

        for (Character ch: chars) {
            CharProbability charProbability = new CharProbability(ch, new BigDecimal(0));
            if (probabilities.contains(charProbability)) {
                BigDecimal resultingIntervalLength = resultingInterval.getEndingValue().subtract(resultingInterval.getStartingValue());
                BigDecimal startingValueIncrementCoefficient = new BigDecimal(0);
                int indexOfChInProbabilities = probabilities.indexOf(charProbability);
                for (int c = 0; c < indexOfChInProbabilities; c++) {
                    startingValueIncrementCoefficient = startingValueIncrementCoefficient.add(probabilities.get(c).getProbability());
                }
                BigDecimal resultingIntervalStartingValue = resultingInterval.getStartingValue().add(resultingIntervalLength.multiply(startingValueIncrementCoefficient));
                resultingInterval.setStartingValue(resultingIntervalStartingValue);

                BigDecimal resultingIntervalEndingValue = resultingInterval.getStartingValue().add(resultingIntervalLength.multiply(probabilities.get(indexOfChInProbabilities).getProbability()));
                resultingInterval.setEndingValue(resultingIntervalEndingValue);
            }
        }

//        System.out.println(resultingInterval);
        BigDecimal resultingPointSub1 = resultingInterval.getEndingValue().subtract(resultingInterval.getStartingValue());
        BigDecimal resultingPointSub2 = resultingPointSub1.divide(new BigDecimal(2));
        BigDecimal resultingPoint = resultingInterval.getStartingValue().add(resultingPointSub2);

        Message message = new Message(resultingPoint, probabilities, chars.length);
        return message;
    }

    public static List<Character> decode (Message message) {
        BigDecimal resultingPoint = message.getResultingPoint();
        List<CharProbability> probabilities = message.getProbabilities();
        int initialTextLength = message.getInitialTextLength();
        List<Character> decodedLine = new ArrayList<>();
        Interval decodingInterval = new Interval(new BigDecimal(0), new BigDecimal(1));
        for (int k = 0; k<initialTextLength; k++) {
            for (int i = 0; i < probabilities.size(); i++) {
                BigDecimal lineSegment = decodingInterval.getEndingValue().subtract(decodingInterval.getStartingValue());
                Interval charsInterval = new Interval(new BigDecimal(0), new BigDecimal(0));
                BigDecimal startingValue = new BigDecimal(0);

                for (int j = 0; j < i; j++) {
                    startingValue = startingValue.add(probabilities.get(j).getProbability());
                }
                startingValue = decodingInterval.getStartingValue().add(lineSegment.multiply(startingValue));
                BigDecimal endingValue = startingValue.add(lineSegment.multiply(probabilities.get(i).getProbability()));

                charsInterval.setStartingValue(startingValue);
                charsInterval.setEndingValue(endingValue);
                if (isWithinInterval(resultingPoint, charsInterval)) {
                    decodedLine.add(probabilities.get(i).getCh());
                    decodingInterval.setStartingValue(startingValue);
                    decodingInterval.setEndingValue(endingValue);
                    break;
                }
            }

        }
        return decodedLine;
    }

    public static boolean isWithinInterval (BigDecimal point, Interval interval) {
        if (point.compareTo(interval.getStartingValue()) > 0 && point.compareTo(interval.getEndingValue()) <= 0) return true;
        return false;
    }


}
