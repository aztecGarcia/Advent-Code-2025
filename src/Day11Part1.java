/* Online Java Compiler and Editor */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day11Part1 {
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

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day11.txt"));
        
        String[] cells = puzzleInput.split(" ");
        List<String> rocks = new ArrayList<String>();
        for (int i = 0; i < cells.length; i++) {
            rocks.add(cells[i].trim());
        }
        
        for (int i = 0; i < 75; i++) {
            for (int j = 0; j < rocks.size(); j++) {
                int strLength = rocks.get(j).length();
                
                if (Character.compare(rocks.get(j).charAt(0), '0') == 0) {
                    rocks.set(j, rocks.get(j).replace("0", "1"));
               
                } else if (strLength%2 == 0) {
                    rocks.add(j +1, ridTrailingZeroes(rocks.get(j).substring(strLength/2)));
                    rocks.set(j, ridTrailingZeroes(rocks.get(j).substring(0, strLength/2)));
                
                    j++;
                } else {
                    rocks.set(j, String.valueOf(Long.parseLong(rocks.get(j).trim()) * 2024));
                }
            }
            System.out.println(i);
        }
        System.out.println(rocks.size());
     }
}


