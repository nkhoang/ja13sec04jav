/**
 * Copyright @ 2013
 */
package com.hnk.aws.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hnguyen
 */
public class FileUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Read line from file.
     *
     * @param filePath the path to the file.
     * @return a list of line values.
     */
    public static List<String> readLinesFromFile(String filePath) throws IOException {
        LOG.debug(MessageFormat.format("# Reading file: {0}", filePath));
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            do {
                String line = reader.readLine();
                lines.add(line);
            } while (reader.ready());
        } catch (IOException ioe) {
            LOG.warn(MessageFormat.format("# Could not open file: {0}", filePath));
            throw ioe;
        }

        return lines;
    }

    /**
     * Method to check file existence.
     *
     * @param filePath the file path.
     * @return true/false.
     */
    public static boolean checkFileExistence(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Create a new folder.
     *
     * @param folderPath the folder path.
     * @return the folder path.
     */
    public static boolean createFolder(String folderPath) {
        boolean success = (new File(folderPath)).mkdir();
        return success;
    }

}
