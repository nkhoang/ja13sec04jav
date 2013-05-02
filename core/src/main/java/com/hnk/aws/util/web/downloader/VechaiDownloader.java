package com.hnk.aws.util.web.downloader;

import com.hnk.aws.util.file.FileUtils;
import com.hnk.aws.util.web.WebUtils;
import com.hnk.aws.util.web.source.handler.VechaiSourceHandler;
import org.apache.commons.collections.MapUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Downloader for Dai Duong Song Long cosmic title.
 */
@Service
public class VechaiDownloader extends MangaDownloader {

    private int limit = -1;

    public VechaiDownloader(ThreadPoolTaskExecutor taskExecutor, String folderPath,
            String matchedPattern, String cosmicHomeLink, String title) {
        super(title, folderPath, matchedPattern, cosmicHomeLink);
        this.taskExecutor = taskExecutor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRun() {
        Map<String, String> links = WebUtils.getLinkTitleMapping(mangaHomeLink, matchedPattern,
                new VechaiSourceHandler());
        FileUtils.createFolder(folderPath);
        // sort chapter key
        List<String> chapterNumbers = new ArrayList<String>(links.keySet());
        Collections.sort(chapterNumbers, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (Integer.parseInt(o1) > Integer.parseInt(o2)) return -1;
                else if (Integer.parseInt(o1) == Integer.parseInt(o2)) return 0;
                else if (Integer.parseInt(o1) < Integer.parseInt(o2)) return 1;
                return 0;
            }
        });
        int limitCount = 0;
        int taskCount = 0;
        for(String chapterKey: chapterNumbers) {
            final String chapterPath = folderPath + "/" + chapterKey;
            if (!FileUtils.checkFileExistence(chapterPath)) {
                FileUtils.createFolder(chapterPath);
                final Map<String, String> imgMap = WebUtils.getLinks(links.get(chapterKey), new VechaiSourceHandler());
                if (MapUtils.isNotEmpty(imgMap)) {
                    for (final String imgName : imgMap.keySet()) {
                        if (WebUtils.checkImageUrl(imgMap.get(imgName))) {
                            result.add(taskExecutor.submit(new Callable<Object>() {
                                public Object call() throws Exception {
                                    try {
                                        WebUtils.saveFile(imgMap.get(imgName), imgName, chapterPath);
                                    } catch (FileNotFoundException fnfex) {
                                        LOG.error("File not found.", fnfex);
                                        return false;
                                    }
                                    return true;
                                }
                            }));
                            // periodically check the futures.
                            if (result.size() == FUTURE_CHECK_SIZE) {
                                checkFutures();
                            }
                        }
                    }
                    if (result.size() > 0) {
                        // last check futures
                        checkFutures();
                    }
                }
                ++limitCount;
                if (limit != -1 && limitCount > limit)
                    break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public DownloadStatus updateStatus(Object futureVal) {
        status.setProcessedCount(status.getProcessedCount() + 1);

        return status;
    }
}
