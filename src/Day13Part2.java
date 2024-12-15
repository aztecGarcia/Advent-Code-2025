import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13Part2 {
    
    final static Pattern numsPatt = Pattern.compile("\\d+");
        
     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day13.txt"));
        
        String[] bodies = puzzleInput.split("\\r?\\n\\r?\\n");
        
        long allTokens = 0;
        for (String body: bodies) {
            Matcher matchedNums = numsPatt.matcher(body);
            long[] nums = new long[6];

            int i = 0;
            while (matchedNums.find()) {
                nums[i] = Long.parseLong(matchedNums.group(0));
                i++;
            }
            
            
            nums[4] += 10000000000000L;
            nums[5] += 10000000000000L;
            
            long b = (nums[1]*nums[4] - nums[0]*nums[5])/(-nums[0]*nums[3]+nums[1]*nums[2]);
            long a = (nums[5] - nums[3]*b) / nums[1];

            long tempAValue = a*nums[0];
            long tempBValue = b*nums[2];
            
            long tempANextValue = a*nums[1];
            long tempBNextValue = b*nums[3];

            
            if (tempAValue + tempBValue == nums[4] && (tempANextValue + tempBNextValue) == nums[5]) {
                allTokens += a*3 + b;
            }
            
        }
        
        System.out.println(allTokens);
     }
}


