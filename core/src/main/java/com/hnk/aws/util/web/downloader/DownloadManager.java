//
// Copyright @ 2013
//
package com.hnk.aws.util.web.downloader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Manage manga download queue and expose API to get progress information of each task.
 *
 * @author Hoang Nguyen
 */
@Service
public class DownloadManager {
    private final static Logger LOG = LoggerFactory.getLogger(DownloadManager.class.getCanonicalName());

    @Autowired()
    private ThreadPoolTaskExecutor downloaderExecutor;

    private Map<String, MangaDownloader> runningDownloader = new HashMap<String, MangaDownloader>();

    /**
     *
     * @param downloader
     */
    public void createNewTask(final MangaDownloader downloader) {
        downloaderExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                runningDownloader.put(downloader.getTitle(), downloader);
                return downloader.run();
            }
        });
    }

    /**
     * Get status of a download by title.
     *
     * @param title the title which may has a downloader running in background.
     * @return
     */
    public DownloadStatus getStatus(String title) {
        MangaDownloader downloader = runningDownloader.get(title);
        if (downloader != null) {
            return downloader.getStatus();
        }

        return null;
    }

}
