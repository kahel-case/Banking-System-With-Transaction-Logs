package sys.utility;

import sys.main.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class FileHandler {
    private static final String RUNTIME_LOGS = "runtime.txt";
    private static final String DATA_FOLDER = "program performance/";

    static {
        File dir = new File(DATA_FOLDER);
        if (!dir.exists()) dir.mkdir();
    }

    public static void saveRuntime(String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FOLDER + RUNTIME_LOGS, true))) {
            bw.write(text);
            bw.newLine();
        } catch (IOException e) {
            System.out.println(Objects.requireNonNull(Main.class.getResource("/sys")).toExternalForm() + "data");
        }
    }

    public static void test(String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/sys/data/text.txt", true))) {
            bw.write(text);
            bw.newLine();
        } catch (IOException e) {
            System.out.println(Objects.requireNonNull(Main.class.getResource("/sys/data/text.txt")).toExternalForm());
        }
    }
}
