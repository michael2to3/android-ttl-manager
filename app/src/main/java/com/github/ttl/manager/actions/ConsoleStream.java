package com.github.ttl.manager.actions;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleStream implements AutoCloseable {
    private Process process;
    private BufferedReader reader;

    private ConsoleStream(final Process process, final BufferedReader bufferedReader) {
        this.process = process;
        this.reader = bufferedReader;
    }

    public static ConsoleStream createConsoleStream() throws IOException {
        var process = new ProcessBuilder().command("sh").start();
        var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return new ConsoleStream(process, reader);
    }

    @Override
    public void close() throws IOException {
        if (process != null) {
            process.destroy();
        }
        if (reader != null) {
            reader.close();
        }
    }

    public String exec(String command) throws IOException, InterruptedException {
        process.getOutputStream().write((command + "\n").getBytes());
        process.getOutputStream().flush();

        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Command execution failed with exit code: " + exitCode);
        }

        return output.toString();
    }
}