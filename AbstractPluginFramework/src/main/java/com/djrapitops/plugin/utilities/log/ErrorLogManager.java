package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.Verify;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class responsible for Errors.txt file.
 *
 * @author Rsl1122
 * @since 2.0.2
 */
public class ErrorLogManager {

    private final PluginLog logger;
    private File folder;
    private List<ErrorObject> errors;

    final private String ERRORS = "Errors.txt";

    /**
     * Constructor.
     *
     * @param log PluginLog this ErrorLogManager is used in.
     * @throws IOException If File can not be read or written to.
     */
    public ErrorLogManager(PluginLog log) throws IOException {
        this.logger = log;
    }

    /**
     * Adds a new ErrorObject to errors by using the stacktrace.
     *
     * @param stackTrace Full Stacktrace containing the name of the class that caught the throwable.
     */
    public void addError(List<String> stackTrace) {
        if (errors == null) {
            try {
                errors = readFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ErrorObject error = new ErrorObject(stackTrace);
        if (!errors.contains(error)) {
            errors.add(error);
            logger.getDebug(error.getException()).addLines(error.getStackTrace()).toLog();
            toFile(openToString(new ArrayList<>(errors)));
        }
    }

    /**
     * Reads the Errors.txt file.
     *
     * @return List with ErrorObject for each stacktrace found in the file.
     * @throws IOException If file can not be read.
     */
    public List<ErrorObject> readFile() throws IOException {
        if (folder == null) {
            folder = logger.getFolder();
        }
        File log = new File(folder, ERRORS);
        if (!Verify.exists(log)) {
            return new ArrayList<>();
        }
        List<String> lines;
        try (Stream<String> lineStream = Files.lines(log.toPath(), StandardCharsets.UTF_8)) {
            lines = lineStream.collect(Collectors.toList());
        }

        List<List<String>> split = new ArrayList<>();
        split.add(new ArrayList<>());
        for (String line : lines) {
            if ("----".equals(line)) {
                split.add(new ArrayList<>());
                continue;
            }
            List<String> error = split.get(split.size() - 1);
            error.add(line);
        }

        List<ErrorObject> errors = split.stream()
                .filter(trace -> !Verify.isEmpty(trace))
                .map(trace -> new ErrorObject(trace))
                .collect(Collectors.toList());
        return errors;
    }

    /**
     * Opens ErrorObjects to a list of lines that can be written to the log.
     *
     * @param errors List of ErrorObjects with stack traces.
     * @return List of Strings "lines" of the file.
     */
    public List<String> openToString(List<ErrorObject> errors) {
        List<String> lines = new ArrayList<>();
        for (ErrorObject error : errors) {
            if (error == null) {
                continue;
            }
            List<String> stackTrace = error.getStackTrace();
            for (String s : stackTrace) {
                lines.add(s);
            }
            lines.add("----");
        }
        return lines;
    }

    /**
     * Overwrites Errors.txt with the list of lines.
     *
     * @param lines New content of the file.
     */
    public void toFile(List<String> lines) {
        File log = new File(folder, ERRORS);
        OutputStreamWriter oSW = null;
        PrintWriter pw = null;
        try {
            if (!log.exists()) {
                log.createNewFile();
            }

            oSW = new OutputStreamWriter(new FileOutputStream(log), StandardCharsets.UTF_8);
            pw = new PrintWriter(oSW);
            for (String msg : lines) {
                pw.println(msg);
            }
            pw.flush();
        } catch (IOException e) {
            logger.error("Failed to create " + ERRORS + " file");
        } finally {
            logger.close(pw, oSW);
        }
    }

    /**
     * Used to get the name of the Error log file.
     *
     * @return Errors.txt
     */
    public String getErrorFileName() {
        return ERRORS;
    }
}
