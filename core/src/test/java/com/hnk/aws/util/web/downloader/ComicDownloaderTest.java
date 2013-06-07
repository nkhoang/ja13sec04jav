package com.hnk.aws.util.web.downloader;

import org.junit.Test;

/**
 * Test class for ComicDownloader.
 */
public class ComicDownloaderTest {
    @Test
    public void testDownloadDDSL() throws Exception {
        ComicDownloader downloader = new VechaiDownloader("/Users/nkhoangit/Downloads/cosmic/XTCH/",
                "http://vechai.info/Xuan-thu-chien-hung/", "^http://(.)*vechai\\.info(.)*(song)(.)*(long)(.)*");
        downloader.run();
        Thread.sleep(2000);
    }

    @Test
    public void testDownloadXTCH() throws Exception {
        ComicDownloader downloader = new VechaiDownloader("/Users/nkhoangit/Downloads/cosmic/XTCH/",
                "http://vechai.info/Xuan-thu-chien-hung/", "^http://(.)*vechai\\.info(.)*(chien)(.)*(hung)(.)*");
        downloader.run();
        Thread.sleep(2000);
    }
}
