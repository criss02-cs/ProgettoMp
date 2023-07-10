package it.unicam.cs.mp.progettoesame.api.console;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Console {
    private static final String PATH = System.getProperty("user.home") + "/Downloads/logger.txt";
    private static final File file = new File(PATH);
    public static synchronized void writeLine(String line) {
        try {
            File file = new File(PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true);
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            fileWriter.close();
        } catch (IOException e) {
            writeLine(line);
        }
    }

    public static synchronized String readAll() throws IOException {
        StringBuilder builder = new StringBuilder();
        if (!file.exists()) {
            return "";
        }
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
        }
        return builder.toString();
    }

    public static synchronized void flushFile() throws IOException {
        if (!file.exists()) {
            return;
        }
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("");
            fw.flush();
        }
    }
}
