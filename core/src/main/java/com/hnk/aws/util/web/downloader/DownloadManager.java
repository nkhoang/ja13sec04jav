//
// Copyright @ 2013
//
package com.hnk.aws.util.web.downloader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * Manage manga download queue and expose API to get progress information of each task.
 *
 * @author Hoang Nguyen
 */
@Service
public class DownloadManager {
    private final static Logger LOG = LoggerFactory.getLogger(DownloadManager.class.getCanonicalName());

    @Autowired
    private ThreadPoolTaskExecutor downloadExecutor;

    /**
     *
     * @param downloader
     */
    public void createNewTask(final MangaDownloader downloader) {
        downloadExecutor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                downloader.run();
                return null;
            }
        });
    }

}
