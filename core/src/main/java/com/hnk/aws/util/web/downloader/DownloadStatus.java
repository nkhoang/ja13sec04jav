//
// Copyright @ 2013
//
package com.hnk.aws.util.web.downloader;

/**
 * @author Hoang Nguyen
 */
public class DownloadStatus {
    private String processId;
    // the number of unit processed.
    private Integer processedCount = 0;

    public Integer getProcessedCount() {
        return processedCount;
    }

    public void setProcessedCount(Integer processedCount) {
        this.processedCount = processedCount;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
