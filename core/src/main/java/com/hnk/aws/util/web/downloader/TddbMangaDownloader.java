package com.hnk.aws.util.web.downloader;

import com.hnk.aws.util.file.FileUtils;
import com.hnk.aws.util.web.WebUtils;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.concurrent.Callable;

/**
 * TDDB Downloader.
 */
public class TddbMangaDownloader extends MangaDownloader {
    public TddbMangaDownloader() {
        super();
    }

    @Override
    protected DownloadStatus updateStatus(Object futureVal) {
        return null;
    }

    public void doRun() {
        // http://1.kangdm.com/comic_img/cj/jj/cn/SDMB/
        boolean shouldStop = false;
        int chapterCount = 217;
        int pageCount = 1;
        // String theUrl = "http://1.kangdm.com/comic_img/cj/jj/cn/SDMB";
        String theUrl = null;
        try {
        theUrl = MessageFormat.format("http://1.kangdm.com/comic_img/KYOLZ/S/{0}",
                URLEncoder.encode("四大名捕","UTF-8"));
        } catch (Exception e) {
            return;
        }

        // first create the folder
        String folderPath = "D:/download/TDDB";
        FileUtils.createFolder(folderPath);
        do {

            final String chapterPath = MessageFormat.format("{0}/{1}", folderPath, chapterCount);
            FileUtils.createFolder(chapterPath);
            boolean endChapter = false;
            do {
                final String imgUrl = MessageFormat.format("{0}/{1}/{2}.jpg", theUrl, String.format("%03d",
                        chapterCount),
                        String.format("%03d", pageCount));
                        //pageCount);
                final String localFileName = MessageFormat.format("{0}.jpg", pageCount);

                if (WebUtils.checkImageUrl(imgUrl)) {
                    result.add(taskExecutor.submit(new Callable<Object>() {
                        public String call() throws Exception {
                            WebUtils.saveFile(imgUrl, localFileName, chapterPath);
                            return null;
                        }
                    }));
                } else {
                    endChapter = true;
                    if (pageCount == 1) {
                        shouldStop = true;
                    }
                }

                if (result.size() == 20) {
                    checkFutures();
                }

                pageCount++;

            } while (!endChapter);
            chapterCount++;
            pageCount = 1;
        } while (!shouldStop);
    }

}
