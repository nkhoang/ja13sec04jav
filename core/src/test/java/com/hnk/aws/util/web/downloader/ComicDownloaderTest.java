package com.hnk.aws.util.web.downloader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test class for ComicDownloader.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-service.xml"})
public class ComicDownloaderTest {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Test
    public void testRun() {
        MangaDownloader downloader = new TddbMangaDownloader();
        downloader.run();
    }

    @Test
    public void testDownloadDDSL() throws Exception {
        MangaDownloader downloader = new VechaiDownloader(taskExecutor, "D:/download/TDDB",
                "^http://(.)*vechai\\.info(.)*(song)(.)*(long)(.)*", "http://vechai.info/Dai-duong-song-long/");
        downloader.run();
        Thread.sleep(2000);
    }
}
