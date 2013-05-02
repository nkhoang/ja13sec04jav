package com.hnk.aws.util.web.downloader;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public abstract class MangaDownloader {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    protected final int FUTURE_CHECK_SIZE = 10;

    protected String folderPath;
    protected String matchedPattern;
    protected String mangaHomeLink;
    // manga title.
    protected String title;

    protected ThreadPoolTaskExecutor taskExecutor;
    protected List<Future<Object>> result = new ArrayList<Future<Object>>();
    protected DownloadStatus status;

    public MangaDownloader() {
    }

    /**
     * @param matchedPattern e.g. ^http://(.)*vechai\\.info(.)*(song)(.)*(long)(.)*
     * @param mangaHomeLink e.g. http://vechai.info/Dai-duong-song-long/
     */

    public MangaDownloader(String title, String folderPath, String matchedPattern, String mangaHomeLink) {
        this.title = title;
        this.folderPath = folderPath;
        this.matchedPattern = matchedPattern;
        this.mangaHomeLink = mangaHomeLink;

        status = new DownloadStatus();
        status.setProcessId(title);
    }


    public Object run() {
        LOG.info(this.getClass().getCanonicalName() + " started...");
        doRun();

        return status;
    }

    protected abstract DownloadStatus updateStatus(Object futureVal);


    protected void checkFutures() {
        if (CollectionUtils.isNotEmpty(result)) {
            for (Future<Object> future : result) {
                try {
                    Object futureVal = future.get();
                    updateStatus(futureVal);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(MessageFormat.format("Value from future: {0}", futureVal.toString()));
                    }
                } catch (Exception ex) {
                    LOG.error("Exception", ex);
                }
            }

            result.clear();
        }
    }

    /**
     * Do actual work.
     */
    public abstract void doRun();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }
}
