//
// Copyright @ 2013.
//
package com.hnk.aws.util.web.downloader;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Cosmic Download uses thread to download all images of a chapter. Implementations of this class will need to create
 * task and add the task to the queue.
 * <p/>
 * The task will be checked by the downloader to get the real result.
 *
 * @author hnguyen
 */
public abstract class ComicDownloader {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    private static final int THREAD_POOL_QUEUE_SIZE = 60;
    private static final int THREAD_POOL_KEEP_ALIVE_TIME = 0;
    private static final int THREAD_POOL_MAX_SIZE = 30;
    private static final int THREAD_POOL_CORE_SIZE = 20;

    // tasks will be put to this queue to be checked later.
    protected List<Future<String>> taskQueue = new ArrayList<Future<String>>();

    // the task executor.
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

    /**
     * Check the {@code taskQueue} to get the result.
     */
    protected void checkFutures() {
        if (CollectionUtils.isNotEmpty(taskQueue)) {
            for (Future<String> future : taskQueue) {
                try {
                    String futureVal = future.get();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(MessageFormat.format("Value from future: {0}", futureVal));
                    }
                } catch (Exception ex) {
                    LOG.error("Error caught in future: ", ex);
                }
            }

            taskQueue.clear();
        }
    }

    /**
     * Concrete implementation must be provided. Implementation must create task and add it to the {@code taskQueue}.
     */
    public abstract void doRun();
}
