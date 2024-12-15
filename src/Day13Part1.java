import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Part1 {
    
    final static Pattern numsPatt = Pattern.compile("\\d+");
        
     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day13.txt"));
        
        String[] bodies = puzzleInput.split("\\r?\\n\\r?\\n");
        
        int allTokens = 0;
        for (String body: bodies) {
            Matcher matchedNums = numsPatt.matcher(body);
            long[] nums = new long[6];

            int i = 0;
            while (matchedNums.find()) {
                nums[i] = Long.parseLong(matchedNums.group(0));
                i++;
            }
            
            long a;// = 0;
            long finalA = 0;
            long finalB = 0;
            long b = 0;
            long lowest = 1000000000000000L;
            
            for (a = 0; a < nums[4] / nums[0] + 2; a++) {
                long tempAValue = a*nums[0];
                b = (nums[4] - tempAValue) / nums[2];
                long tempBValue = b*nums[2];
                
                long tempANextValue = a*nums[1];
                long tempBNextValue = b*nums[3];

                
                if (tempAValue + tempBValue == nums[4] && (tempANextValue + tempBNextValue) == nums[5]) {
                    if (lowest > a*3 + b) {
                        finalA = a;
                        finalB = b;
                        lowest = a*3 + b;
                    }
                }
            }
            
            allTokens += finalA*3 + finalB;
            // allTokens += ;
            // System.out.println(finalA);
            // System.out.println(finalB);
            // System.out.println("Made It;");
        }
        
        System.out.println(allTokens);
     }
}


