/* Online Java Compiler and Editor */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11Part2 {
    public static String ridTrailingZeroes(String str) {
        StringBuilder noneTrailing = new StringBuilder(str);
        
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0' || i == str.length()-1) break;

            noneTrailing.deleteCharAt(0);
        }

        return noneTrailing.toString();
    }

    public static String multiplyBy2024(String str) {
        return str;
    }

    public static int getDigits(long test) {
        int digits = 0;

        for (int k = 0; k < 100; k++) {
            test /= 10;
            digits++;
        
            if (test == 0) {
                return digits;
            }
        }

        return 0;
    }


     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day11.txt"));
        
        String[] cells = puzzleInput.split(" ");
        List<Long> rocks = new ArrayList<Long>();
        Map<Long, List<Integer>> repeated = new HashMap<>();
        Map<Long, Long> traceback = new HashMap<>();
        Map<Long, Long> doubled = new HashMap<>();
        int amount = 0;
        for (int i = 0; i < cells.length; i++) {
            rocks.add(Long.parseLong(cells[i]));
        }
        
        int loopTimes = 5;
        for (int i = 0; i < loopTimes; i++) {
            // System.out.println(rocks);
            int maxLoop = rocks.size();
            for (int j = 0; j < maxLoop; j++) {
                // System.out.printf("On: %d ", rocks.get(j));
                // System.out.println(amountStacked);
                Long rockJ = rocks.get(j);

                if (traceback.containsKey(rockJ) || i == 0) {
                    if (!repeated.containsKey(rockJ)) {
                        repeated.put(rockJ, new ArrayList<Integer>());
                    }

                    repeated.get(rockJ).add(loopTimes - i);

                    // rocks.set(j, traceback.get(rockJ));
                    // if (doubled.containsKey(rockJ)) {
                    //     rocks.add(doubled.get(rockJ));
                    // }

                    continue;
                } 
                
                
                if (rockJ == 0) {
                    traceback.put((long)0, (long)1);
                    rocks.set(j, (long)1);
                    continue;
                } 

                long current = rockJ;
                int digits = (getDigits(current));

                if (digits%2 == 0) {
                    int exponent = (int)Math.pow(10, digits/2);
                    long top = current / exponent;
                    long bottom = current - top * exponent;

                    // doubled.put(bottom, true);
                    doubled.put(current, bottom);

                    // traceback.put(current, bottom);
                    traceback.put(current, top);
                    rocks.add(bottom);
                    rocks.set(j, top);
                
                    j++;
                } else {
                    traceback.put(current, current * 2024);
                    rocks.set(j, current * 2024);
                }
            }
            // System.out.println(i);
        }

        System.out.println(rocks.size());
        System.out.println(repeated.size());

        int sumSplit = 0;

        for (Map.Entry<Long, List<Integer>> entry: repeated.entrySet()) {
            Long start = entry.getKey();

            for (Integer loopAmount: entry.getValue()) {
                Deque<Long> travel = new ArrayDeque<>();

                travel.add(start);
                System.out.println(start);
                
                int splitTimes = 1;
                for (int i = 0; i < loopAmount; i++) {
                    int travelDepth = travel.size();
                    
                    for (int j = 0; j < travelDepth; j++) {
                        Long current = travel.poll();

                        if (traceback.containsKey(current)) {
                            travel.add(traceback.get(current));
                        }

                        if (doubled.containsKey(current)) {
                            travel.add(doubled.get(current));
                            splitTimes++;
                        }
                    }
                }

                sumSplit += splitTimes;
            }
        }

        System.out.println(sumSplit);
        // System.out.println(amountStacked.entrySet());
        // System.out.println(traceback.entrySet());
     }
}


