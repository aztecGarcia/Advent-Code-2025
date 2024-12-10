import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day9Part1 {

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day9.txt"));
        
        List<Integer> mem = new ArrayList<>();
        int id = 0;
        
        // add the memories
        for (int i = 0; i < puzzleInput.length(); i++) {
            Character symbol = puzzleInput.charAt(i);
            Integer size = Character.getNumericValue(symbol);
            
            if (size != 0) {
                Integer toAdd = null;
                if (i%2 == 0) {
                    toAdd = id;
                    id++;
                }
                
                for (int m = 0; m < size; m++) {
                    mem.add(toAdd);
                }
            }
        }
        
        // dedrag ment the memories
        for (int i = 0; i < mem.size(); i++) {
            if (mem.get(i) != null) continue;

            // find a spot right to left to defrag
            int pickedIdx = -1 ;
            for (int j = mem.size() - 1; j >= 0; j--) {
                if (mem.get(j) != null) {
                    // mem.set(ii, removed);
                    pickedIdx = j;
                    break;
                } else {
                    mem.remove(j);
                }
            }
            
            if (pickedIdx == -1) {
                break;
            }
            
            int toMoveIdx = mem.size() - 1;
            Integer removed = mem.get(toMoveIdx); 
            mem.remove(toMoveIdx);
            mem.set(i, removed);
            
        }
        
        // checksum
        long sum = 0;
        int multt = 0;
        for (int i = 0; i < mem.size(); i++) {
            if (mem.get(i) == null) { continue; }

            sum = sum + multt * mem.get(i);
            multt++;
        }
        System.out.println(sum);
     }
}
