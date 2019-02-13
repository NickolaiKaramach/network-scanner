package by.bsuir.karamach.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ProcessRunner {


    private static final String NEW_LINE_CHAR = "\n";

    private ProcessRunner() {
    }

    public static String executeCommand(String commandToExecute) throws IOException {

        Process p;
        p = Runtime.getRuntime().exec(commandToExecute);


        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(p.getInputStream())
                );

        StringBuilder output = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null) {
            output.append(currentLine);
            output.append(NEW_LINE_CHAR);
        }

        return output.toString();
    }
}
