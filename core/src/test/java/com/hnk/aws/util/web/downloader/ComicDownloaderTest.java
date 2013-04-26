package com.hnk.aws.util.web.downloader;

import org.junit.Test;

/**
 * Test class for ComicDownloader.
 */
public class ComicDownloaderTest {
    @Test
    public void testRun() {
        ComicDownloader downloader = new TddbComicDownloader();
        downloader.run();
    }

    @Test
    public void testDownloadDDSL() throws Exception {
        ComicDownloader downloader = new DDSLDownloader();
        downloader.run();
        Thread.sleep(2000);
    }
}
