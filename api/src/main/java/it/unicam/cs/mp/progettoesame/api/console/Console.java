package it.unicam.cs.mp.progettoesame.api.console;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Console {
    private static final String path = "../logger.txt";
    private static final File file = new File(path);
    public static synchronized void writeLine(String line) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
        fileWriter.close();
    }

    public static synchronized String readAll() throws IOException {
        StringBuilder builder = new StringBuilder();
        if (!file.exists()) {
            return "";
        }
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line).append("\n");
        }
        br.close();
        return builder.toString();
    }

    public static synchronized void flushFile() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        file.delete();
    }
}
