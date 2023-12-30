import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class Day3 {


    public static void main(String[] args) throws Exception {
        System.out.println(read());
    }

    private static long read() throws Exception {
        List<String> list = Files.readAllLines(Path.of("./input.txt"));
        char[][] input = mapToInput(list);
        char[][] extendInput = mapToExtendInput(input);

        return sum(partNumbers,extendInput,Set.of(''));
    }

    private static char[][] mapToExtendInput(char[][] input) {
        var extendInput = new char[input.length + 2][input[0].length + 2];
        for (char[] chars : extendInput) {
            for (int i = 0; i < chars.length; i++) {
                chars[i] = '.';
            }
        }
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[y].length; x++) {
                extendInput[y + 1][x + 1] = input[y][x];
            }
        }
        return extendInput;
    }

    private static char[][] mapToInput(List<String> lines) {
        var input = new char[lines.size()][lines.get(0).length()];
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                input[y][x] = c;
            }
        }
        return input;
    }

    private static long sum(List<PartNumber> partNumbers
            , char[][] extendInput
            , Set<Character> charFilter) {
        long result = 0;
        for (PartNumber partNumber : partNumbers) {
            List<Pair<Integer, Integer>> neighbours = partNumber.generateNeighbours();
            List<Character> characters = mapToChars(neighbours, extendInput);
            if (characters.stream().anyMatch(charFilter::contains)) {
                result = result + partNumber.value;
            }
        }
        return result;
    }

    //stream
    private static List<Character> mapToChars(List<Pair<Integer, Integer>> neighbours,
                                              char[][] extendInput) {
        return neighbours.stream()
                .map(n -> extendInput[n.first][n.second])//todo
                .toList();
    }

    record PartNumber(int value, int x1, int x2, int y) {


        public List<Pair<Integer, Integer>> generateNeighbours() {
            List<Pair<Integer, Integer>> list = new ArrayList<>();
            for (int i = x1; i <= x2; i++) {
                list.add(new Pair<>(i, y - 1));
                list.add(new Pair<>(i, y + 1));
            }
            //left
            list.add(new Pair<>(x1 - 1, y - 1));
            list.add(new Pair<>(x1 - 1, y));
            list.add(new Pair<>(x1 - 1, y + 1));
            //right
            list.add(new Pair<>(x2 + 1, y - 1));
            list.add(new Pair<>(x2 + 1, y));
            list.add(new Pair<>(x2 + 1, y + 1));
            return list;
        }
    }

    record Pair<First extends Number, Second extends Number>(First first, Second second) {
    }

}
