import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day9Part2 {

    public static void display(List<Integer[]> mem) {
        for (int i = 0; i < mem.size(); i++) {
            if (mem.get(i)[1] == null) {
                for (int j = 0; j < mem.get(i)[0]; j++) {
                    System.out.print(".");
                }
                continue;
            }

            for (int j = 0; j < mem.get(i)[0]; j++) {
                System.out.print(mem.get(i)[1]);
            }
        }

        System.out.println();
    }

     public static void main(String []args) throws IOException {
        String puzzleInput = Files.readString(Path.of("PuzzleInput/Day9.txt"));
        
        List<Integer[]> mem = new ArrayList<>();
        int id = 0;
        
        
        // add to memories
        for (int i = 0; i < puzzleInput.length(); i++) {
            Character symbol = puzzleInput.charAt(i);
            Integer size = Character.getNumericValue(symbol);
            
            if (size != 0) {
                Integer toAdd = null;
                if (i%2 == 0) {
                    toAdd = id;
                    id++;
                }
                
                mem.add(new Integer[] { size, toAdd });
            }
        }

        
        // dedrag ment the memories
        for (int i = mem.size()-1; i >= 0; i--) {
            if (mem.get(i)[1] == null) continue;

            // find a spot from left to right (make sure it doesn't go over to limit i)
            int pickedIdx = -1 ;
            for (int j = 0; j < i; j++) {
                if (mem.get(j)[1] == null && mem.get(j)[0] >= mem.get(i)[0]) {
                    // mem.set(ii, removed);
                    pickedIdx = j;
                    break;
                }
            }
            
            if (pickedIdx == -1) {
                continue;
            }
            
            // subtract memory size and add one before
            int toMoveIdx = i;
            Integer[] removed = new Integer[] {mem.get(toMoveIdx)[0], mem.get(toMoveIdx)[1]}; 
            
            mem.get(toMoveIdx)[1] = null;
            mem.get(pickedIdx)[0] -= removed[0];
            mem.add(pickedIdx, removed);
            i++; // because we add one so we shift everythiung by 1
            
        }
        
        // checksum
        long sum = 0;
        int multt = 0;
        for (int i = 0; i < mem.size(); i++) { // why is different from part 1 :skull:
            for (int j = 0; j < mem.get(i)[0]; j++) {
                if (mem.get(i)[1] == null) { 
                    multt++;
                    continue; 
                }
                sum += multt * mem.get(i)[1];
                multt++;
            }
        }
        System.out.println(sum);
     }
}
