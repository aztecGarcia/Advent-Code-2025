package src;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part2 {
    public static final Pattern mult = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
      int sum = 0;

      String puzzle = Files.readString(Paths.get("PuzzleInput/Day3.txt"), StandardCharsets.UTF_8);
      puzzle = puzzle.replaceAll("[\n\r]", ""); //the (?s) flag would've also worked
      puzzle = puzzle.replaceAll("don't\\(\\).*?do\\(\\)", "");
      puzzle = puzzle.replaceAll("don't\\(\\).+", ""); // if ending trims

      Matcher whole = mult.matcher(puzzle);

      while (whole.find()) {
        sum += Integer.parseInt(whole.group(1)) * Integer.parseInt(whole.group(2));
        whole.end();
      }

      System.out.println(sum);
    }
  }