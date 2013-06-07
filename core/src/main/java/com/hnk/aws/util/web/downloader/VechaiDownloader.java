package com.hnk.aws.util.web.downloader;

import com.hnk.aws.util.file.FileUtils;
import com.hnk.aws.util.web.WebUtils;
import com.hnk.aws.util.web.source.handler.DDSLSourceHandler;
import org.apache.commons.collections.MapUtils;

import java.util.*;
import java.util.concurrent.Callable;

public class VechaiDownloader extends ComicDownloader {

    private String rootDir;
    private String homeLink;
    private String linkPattern;

    // private static final String COSMIC_HOME_LINK = "http://vechai.info/Dai-duong-song-long/";

    /**
     * Construct Downloader with configured root directory.
     *
     * @param rootDir
     */
    public VechaiDownloader(String rootDir, String cosmicHomeLink, String linkPattern) {
        this.rootDir = rootDir;
        this.homeLink = cosmicHomeLink;
        this.linkPattern = linkPattern;
    }

    @Override
    public void doRun() {
        Map<String, String> links = WebUtils.getLinkTitleMapping(homeLink, new DDSLSourceHandler(linkPattern));
        FileUtils.createFolder(rootDir);
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
        for (String chapterKey : chapterNumbers) {
            final String chapterPath = rootDir + "/" + chapterKey;
            if (!FileUtils.checkFileExistence(chapterPath)) {
                FileUtils.createFolder(chapterPath);
                final Map<String, String> imgMap = WebUtils.getLinks(links.get(chapterKey), new DDSLSourceHandler(linkPattern));
                if (MapUtils.isNotEmpty(imgMap)) {
                    for (final String imgName : imgMap.keySet()) {
                        if (WebUtils.checkImageUrl(imgMap.get(imgName))) {
                            taskQueue.add(executor.submit(new Callable<String>() {
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
