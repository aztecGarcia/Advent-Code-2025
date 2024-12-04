package src;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3Part1 {
    public static final Pattern mult = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
      int sum = 0;

      Matcher whole = mult.matcher(Files.readString(Paths.get("PuzzleInput/Day3.txt"), StandardCharsets.UTF_8));

      while (whole.find()) {
        sum += Integer.parseInt(whole.group(1)) * Integer.parseInt(whole.group(2));
        whole.end();
      }

      System.out.println(sum);
    }
  }