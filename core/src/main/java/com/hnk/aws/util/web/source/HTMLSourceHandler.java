package com.hnk.aws.util.web.source;

import com.hnk.aws.util.web.exception.SourceHandlerException;
import net.htmlparser.jericho.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Abstract handler class. An HTML Source Handler expose utility methods that help to get a specific content from
 * a web page.
 */
public abstract class HTMLSourceHandler {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Extract image links from HTML source in form of a stream.
     *
     * @param is the {@code InputStream} instance.
     * @return a list of links.
     * @throws SourceHandlerException if any.
     */
    public abstract List<String> extractUrls(InputStream is, Map<String, Object> data) throws SourceHandlerException;

    /**
     * @param source the {@code Source} instance.
     * @return a list of links
     * @throws SourceHandlerException if any.
     * @see #extractUrls(java.io.InputStream, java.util.Map)
     */
    public abstract List<String> extractUrls(Source source, Map<String, Object> data) throws
            SourceHandlerException;


    public abstract List<String> extractImgUrls(Source source, Map<String, Object> data) throws
            SourceHandlerException;

}
