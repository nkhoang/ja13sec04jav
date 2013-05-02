package com.hnk.aws.util.web.downloader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test class for ComicDownloader.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-service.xml"})
public class MangaDownloadTest {
    private static final Logger LOG = LoggerFactory.getLogger(MangaDownloadTest.class.getCanonicalName());

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private DownloadManager downloadManager;

    @Test
    public void testRun() {
        MangaDownloader downloader = new TddbMangaDownloader();
        downloader.run();
    }

    @Test
    public void testDownloadDDSL() throws Exception {
        MangaDownloader downloader = new VechaiDownloader(taskExecutor, "D:/download/TDDB",
                "^http://(.)*vechai\\.info(.)*(song)(.)*(long)(.)*", "http://vechai.info/Dai-duong-song-long/", "DDSL");
        downloader.run();
        Thread.sleep(2000);
    }

    @Test
    public void testDownloadManager() throws Exception {
        MangaDownloader downloader = new VechaiDownloader(taskExecutor, "D:/download/TDDB",
                "^http://(.)*vechai\\.info(.)*(song)(.)*(long)(.)*", "http://vechai.info/Dai-duong-song-long/", "DDSL");
        downloadManager.createNewTask(downloader);

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                do {
                    DownloadStatus status = downloadManager.getStatus("DDSL");
                    if (status != null) {
                        LOG.debug("Process <" + status.getProcessId() + ">: " + status.getProcessedCount());
                    }
                    ++count;
                    try {
                        Thread.sleep(3000);
                    } catch (Exception ex) {
                    }
                } while (count < 1000);
            }
        });
        a.start();

        Thread.sleep(1000000);
    }
}
