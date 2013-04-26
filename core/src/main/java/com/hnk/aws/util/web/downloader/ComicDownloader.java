package com.hnk.aws.util.web.downloader;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class ComicDownloader {
    private static final int THREAD_POOL_QUEUE_SIZE = 60;
    private static final int THREAD_POOL_KEEP_ALIVE_TIME = 0;
    private static final int THREAD_POOL_MAX_SIZE = 30;
    private static final int THREAD_POOL_CORE_SIZE = 20;

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    protected List<Future<String>> result = new ArrayList<Future<String>>();

    protected ExecutorService executor;

    public ComicDownloader() {
        executor = new ThreadPoolExecutor(
                THREAD_POOL_CORE_SIZE, THREAD_POOL_MAX_SIZE, THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(THREAD_POOL_QUEUE_SIZE), new ThreadPoolExecutor.CallerRunsPolicy());
    }


    public void run() {
        LOG.info(this.getClass().getCanonicalName() + " started...");
        doRun();
        checkFutures();
    }

    protected void checkFutures() {
        if (CollectionUtils.isNotEmpty(result)) {
            for (Future<String> future : result) {
                try {
                    String futureVal = future.get();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(MessageFormat.format("Value from future: {0}", futureVal));
                    }
                } catch (Exception ex) {
                    LOG.error("Exception", ex);
                }
            }

            result.clear();
        }
    }

    public abstract void doRun();
}
