package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.Verify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for Errors.txt file.
 *
 * @author Rsl1122
 * @since 2.0.2
 */
public class ErrorLogManager {

    private final PluginLog logger;
    private final File folder;
    private final List<ErrorObject> errors;

    final private String ERRORS = "Errors.txt";

    public ErrorLogManager(PluginLog log, File folder) throws IOException {
        this.logger = log;
        this.folder = folder;
        errors = readFile();
    }

    public void addError(List<String> stackTrace) {
        ErrorObject error = new ErrorObject(stackTrace);
        if (!errors.contains(error)) {
            errors.add(error);
            toFile(openToString(errors));
        }
    }

    public List<ErrorObject> readFile() throws IOException {
        File log = new File(folder, ERRORS);
        if (!Verify.exists(log)) {
            return new ArrayList<>();
        }
        List<String> lines = Files.lines(log.toPath()).collect(Collectors.toList());

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
                .map(list -> new ErrorObject(list))
                .collect(Collectors.toList());
        return errors;
    }

    public List<String> openToString(List<ErrorObject> errors) {
        List<String> lines = new ArrayList<>();
        for (ErrorObject error : errors) {
            List<String> stackTrace = error.getStackTrace();
            for (String s : stackTrace) {
                lines.add(s);
            }
            lines.add("----");
        }
        return lines;
    }

    public void toFile(List<String> lines) {
        File log = new File(folder, ERRORS);
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            if (!log.exists()) {
                log.createNewFile();
            }
            fw = new FileWriter(log, false);
            pw = new PrintWriter(fw);
            for (String msg : lines) {
                pw.println(msg);
            }
            pw.flush();
        } catch (IOException e) {
            logger.error("Failed to create " + ERRORS + " file");
        } finally {
            logger.close(pw, fw);
        }
    }

    public String getErrorFileName() {
        return ERRORS;
    }
}
