import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Day2 {
    //only 12 red cubes, 13 green cubes, and 14 blue cubes
    private static final int RED_BOX = 12;
    private static final int GREEN_BOX = 13;
    private static final int BLUE_BOX = 14;

    public static void main(String[] args) throws Exception {
        System.out.println(read());
    }

    private static int read() throws Exception {
        return Files.readAllLines(Path.of("./input.txt"))
                .stream()
                .map(Day2::split)
                .map(Day2::mapTo)
                .filter(Game::isValid)
                .map(Game::gameId)
                .reduce(0, Integer::sum);
    }

    //Game 73: 8 blue, 1 red, 3 green; 1 blue; 6 blue, 4 green
    private static Task split(String s) {
        String[] lines = s.split(": ");
        return new Task(getNumber(lines[0]), lines[1].split("; "));
    }


    /*
       {
        "gameId": 73,
        "strings": [
            "8 blue, 1 red, 3 green",
            "1 blue, 4 green"
        ]
       }
     */
    private static Game mapTo(Task task) {
        List<Variant> variantList = new ArrayList<>();
        for (String string : task.strings) {
            int blue = 0;
            int green = 0;
            int red = 0;
            //8 blue, 1 red, 3 green
            for (String s : string.split(", ")) {
                int count = getNumber2(s);
                if (s.contains("blue")) {
                    blue = count;
                }
                if (s.contains("green")) {
                    green = count;
                }
                if (s.contains("red")) {
                    red = count;
                }
            }

            variantList.add(new Variant(blue, green, red));
        }
        return new Game(task.gameId, variantList);
    }

    private static int getNumber(String s) {
        return Integer.parseInt(s.split(" ")[1].trim());
    }

    private static int getNumber2(String s) {
        return Integer.parseInt(s.split(" ")[0].trim());
    }

    private record Task(int gameId, String[] strings) {
    }

    private record Game(int gameId, List<Variant> variantList) {
        public boolean isValid() {
            return variantList.stream()
                    .filter(variant -> variant.blue <= BLUE_BOX && variant.red <= RED_BOX && variant.green <= GREEN_BOX)
                    .toList().size() == variantList.size();
        }
    }

    private record Variant(int blue, int green, int red) {
    }
}
