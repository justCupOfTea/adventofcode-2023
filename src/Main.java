import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(read());
    }

    private static int read() throws Exception {
        return Files.readAllLines(Path.of("./input.txt"))
                .stream()
                .map(Main::clean)
                .reduce(0, Integer::sum);
    }

    private static Integer clean(String s) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                list.add(c);
            }
        }
        int res = 0;
        if (list.size() == 1) {
            res = Integer.parseInt(list.get(0).toString() + list.get(0).toString());
        }
        if (list.size() > 1) {
            res = Integer.parseInt(list.get(0).toString() + list.get(list.size() - 1).toString());
        }
        return res;
    }
}