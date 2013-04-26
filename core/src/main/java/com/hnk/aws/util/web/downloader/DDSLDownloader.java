package com.hnk.aws.util.web.downloader;

import com.hnk.aws.util.file.FileUtils;
import com.hnk.aws.util.web.WebUtils;
import com.hnk.aws.util.web.source.handler.DDSLSourceHandler;
import org.apache.commons.collections.MapUtils;

import java.util.Map;
import java.util.concurrent.Callable;

public class DDSLDownloader  extends ComicDownloader {

    private static final String COSMIC_HOME_LINK = "http://vechai.info/Dai-duong-song-long/";

    @Override
    public void doRun() {
        Map<String, String> links = WebUtils.getLinkTitleMapping(COSMIC_HOME_LINK, new DDSLSourceHandler());
        String folderPath = "D:/download/TDDB";
        FileUtils.createFolder(folderPath);
        for(String chapterKey: links.keySet()) {
            final String chapterPath = folderPath + "/" + chapterKey;
            if (!FileUtils.checkFileExistence(chapterPath)) {
                FileUtils.createFolder(chapterPath);
                final Map<String, String> imgMap = WebUtils.getLinks(links.get(chapterKey), new DDSLSourceHandler());
                if (MapUtils.isNotEmpty(imgMap)) {
                    for (final String imgName : imgMap.keySet()) {
                        if (WebUtils.checkImageUrl(imgMap.get(imgName))) {
                            result.add(executor.submit(new Callable<String>() {
                                public String call() throws Exception {
                                    WebUtils.saveFile(imgMap.get(imgName), imgName, chapterPath);
                                    return null;
                                }
                            }));
                        }
                    }
                }
            }
        }
    }
}
