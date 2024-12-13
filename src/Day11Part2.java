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
        Map<Long, Long> traceback = new HashMap<Long, Long>();
        Map<Long, List<Integer>> amountStacked = new HashMap<Long, List<Integer>>();
        Map<Long, Long> doubled = new HashMap<>();
        int amount = 0;
        for (int i = 0; i < cells.length; i++) {
            rocks.add(Long.parseLong(cells[i]));
        }
        
        int loopTimes = 5;
        for (int i = 0; i < loopTimes; i++) {
            System.out.println(rocks);
            for (int j = 0; j < rocks.size(); j++) {
                // System.out.printf("On: %d ", rocks.get(j));
                // System.out.println(amountStacked);
                if (amountStacked.containsKey(rocks.get(j)) == true) {
                    amountStacked.get(rocks.get(j)).add(loopTimes-i);

                    System.out.printf("Converged: %d\n", rocks.get(j));

                    rocks.remove(j);
                    j--;
                    continue;
                }

                if (!amountStacked.containsKey(rocks.get(j))) { // making history! oh yeah!
                    
                    final int bruh = i;
                    List<Integer> stackList = new ArrayList<>();
                    if (i == 0) {// the originators must have boostraps!
                        stackList.add(loopTimes-bruh);
                    }
                    amountStacked.put(rocks.get(j), stackList); // cached this hoe!

                }
                if (rocks.get(j) == 0) {
                    traceback.put((long)0, (long)1);
                    rocks.set(j, (long)1);
                    continue;
                } 

                long current = rocks.get(j);
                int digits = (getDigits(current));

                if (digits%2 == 0) {
                    int exponent = (int)Math.pow(10, digits/2);
                    long top = current / exponent;
                    long bottom = current - top * exponent;

                    // doubled.put(bottom, true);
                    doubled.put(current, bottom);

                    // traceback.put(current, bottom);
                    traceback.put(current, top);
                    rocks.add(j +1, bottom);
                    rocks.set(j, top);
                
                    j++;
                } else {
                    traceback.put(current, current * 2024);
                    rocks.set(j, current * 2024);
                }
            }
            System.out.println(i);
        }

        System.out.println(rocks);
        System.out.println(amountStacked.entrySet());
        System.out.println(traceback.entrySet());
        int sumSize = 0;
        for (Map.Entry<Long, List<Integer>> entry: amountStacked.entrySet()) {
            int sizeFrom = 0;

            if (entry.getValue().size() == 0) { continue; };
            System.out.println("Current Entry:");
            System.out.println(entry);
            

            for (int amountLoop: entry.getValue()) {
                Deque<Long> toTravel = new ArrayDeque<Long>();
                // int i = 0;
                toTravel.add(entry.getKey());

                if (amountLoop == loopTimes) {

                    sizeFrom++; // pioneers!
                }
                
                // int amountInDepth = 1;
                // int incrementCheck = 0;
                
                for (int i = 0; i < amountLoop; i++) {
                    for (int j = 0; j < toTravel.size(); j++) {
                        Long current = toTravel.poll();
                        // i++;
                        // if (amountInDepth <= i) {
                        //     i = 0;
                        //     incrementCheck++;
                        // }
    
    
                        System.out.println(current);
                        if (traceback.containsKey(current)) {
                            toTravel.add(traceback.get(current));
                            // sizeFrom++;
                        } //else { amountInDepth--; }
                        if (doubled.containsKey(current)) {
                            toTravel.add(doubled.get(current));
                            sizeFrom++;
                            // i--;
                            // amountInDepth++;
                            System.out.printf("Split from: %d\n", current);
                        } //else { amountInDepth--; }
                    }
                }
            }


            
            sumSize += sizeFrom;
        }
        System.out.println(sumSize);
     }
}


