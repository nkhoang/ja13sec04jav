package com.hnk.aws.util.web;

import com.hnk.aws.util.file.FileUtils;
import com.hnk.aws.util.web.source.HTMLSourceHandler;
import net.htmlparser.jericho.Source;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Web Utils class.
 *
 * @author hnguyen.
 */
public class WebUtils {
    private static final Logger LOG = LoggerFactory
            .getLogger(WebUtils.class.getCanonicalName());

    private final static int BUFFER_SIZE = 1024;


    /**
     * Get all URLS via HTTP GET method.
     *
     * @param theUrl the url to fetch images.
     * @return the list of image urls.
     */
    public static Map<String, String> getLinks(String theUrl, HTMLSourceHandler handler) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(MessageFormat.format("# Connecting to {0} to get links", theUrl));
            }
            URL url = new URL(theUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");

            // get inputStream
            InputStream is = connection.getInputStream();
            Map<String, String> result = new HashMap<String, String>();
            Map<String, Object> data = new HashMap<String, Object>();
            handler.extractImgUrls(new Source(is), data);
            // check if the link is relative
            if (MapUtils.isNotEmpty(data)) {
                for (String key : data.keySet()) {
                    if (!data.get(key).toString().matches("http(.)*")) {
                        result.put(key, theUrl + data.get(key));
                    } else {
                        result.put(key, data.get(key).toString());
                    }
                }
            }
            return result;
        } catch (Exception e) {
            LOG.error(MessageFormat.format("Could not links from {0}.", theUrl), e);
            return null;
        }
    }

    /**
     * Get all URLS via HTTP GET method.
     *
     * @param theUrl the url to fetch images.
     * @return the list of image urls.
     */
    public static Map<String, String> getLinkTitleMapping(String theUrl, String matchedPattern,
            HTMLSourceHandler handler) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(MessageFormat.format("# Connecting to {0} to get links", theUrl));
            }
            URL url = new URL(theUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");

            // get inputStream
            InputStream is = connection.getInputStream();
            Map<String, String> result = new HashMap<String, String>();
            Map<String, Object> data = new HashMap<String, Object>();
            handler.extractUrls(is, data, matchedPattern);
            // check if the link is relative
            if (MapUtils.isNotEmpty(data)) {
                for (String key : data.keySet()) {
                    if (!data.get(key).toString().matches("http(.)*")) {
                        result.put(key, theUrl + data.get(key));
                    } else {
                        result.put(key, data.get(key).toString());
                    }
                }
            }
            return result;
        } catch (Exception e) {
            LOG.error(MessageFormat.format("Could not links from {0}.", theUrl), e);
            return null;
        }
    }


    public static boolean deleteFile(String localFilePath) {
        if (FileUtils.checkFileExistence(localFilePath)) {
            File f = new File(localFilePath);
            return f.delete();
        }
        return false;
    }

    /**
     * Save file from an URL to local disk path: {@code destinationDir } with the file name as {@code localFileName}
     *
     * @param fAddress       the address URL.
     * @param localFileName  the local file name.
     * @param destinationDir the destination directory.
     */
    public static void saveFile(String fAddress, String
            localFileName, String destinationDir) throws FileNotFoundException {
        if (FileUtils.checkFileExistence(destinationDir + "/" + localFileName)) {
            return;
        }
        OutputStream outStream = null;
        URLConnection uCon = null;

        InputStream is = null;
        int byteWritten = 0;
        try {
            URL Url;
            byte[] buf;
            int byteRead;
            Url = new URL(fAddress);
            outStream = new BufferedOutputStream(new
                    FileOutputStream(destinationDir + "/" + localFileName));

            uCon = Url.openConnection();
            is = uCon.getInputStream();
            if (is != null) {
                buf = new byte[1000];
                while ((byteRead = is.read(buf)) != -1) {
                    outStream.write(buf, 0, byteRead);
                    byteWritten += byteRead;
                }

                outStream.flush();
                outStream.close();

                if (byteWritten < 100000) {
                    // small image. delete

                    LOG.debug("Small image -> size: " + byteWritten);
                    // delete
                    WebUtils.deleteFile(destinationDir + "/" + localFileName);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug(MessageFormat.format("Downloaded and saved file: {0}", localFileName));
                }
            } else {
                throw new FileNotFoundException("File not found");
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found");
        } finally {
            try {
                if (is != null)
                    is.close();

                if (outStream != null)
                    outStream.close();

            } catch (IOException e) {

            }
        }
    }

    public static boolean checkImageUrl(String theUrl) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(MessageFormat.format("Checking url: {0}", theUrl));
        }
        try {
            URL Url;
            byte[] buf;
            int byteRead, byteWritten = 0;

            Url = new URL(theUrl);

            URLConnection uCon = Url.openConnection();
            InputStream is = uCon.getInputStream();
            if (is == null) {
                return false;
            }

            is.close();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
