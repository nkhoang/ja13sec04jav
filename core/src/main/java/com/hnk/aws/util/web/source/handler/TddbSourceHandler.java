package com.hnk.aws.util.web.source.handler;

import com.hnk.aws.util.web.exception.SourceHandlerException;
import com.hnk.aws.util.web.source.HTMLSourceHandler;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TddbSourceHandler extends HTMLSourceHandler {
    private static final String LINKS_CONTAINER_ID = "comiczj";

    /**
     * {@inheritDoc}
     */
    public List<String> extractUrls(InputStream is, Map<String, Object> data) throws SourceHandlerException {
        try {
            Source source = new Source(is);
            return extractUrls(source, data);
        } catch (IOException ioe) {
            throw new SourceHandlerException("Could not build Source from InputStream", ioe);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<String> extractUrls(Source source, Map<String, Object> data) throws SourceHandlerException {
        if (source != null) {
            List<String> result = new ArrayList<String>();
            // get links container
            Element linksContainer = source.getElementById(LINKS_CONTAINER_ID);

            if (linksContainer == null) {
                LOG.error(MessageFormat.format("# The link container id {0} has been changed. Please check it again" +
                        ".", LINKS_CONTAINER_ID));
                return result;
            }

            List<Element> links = linksContainer.getAllElements("a");
            if (CollectionUtils.isNotEmpty(links)) {
                for (Element element : links) {
                    String chapterName = element.getContent().toString();
                    // store number only
                    chapterName = chapterName.replaceAll("\\D+", "");
                    String link = element.getStartTag().getAttributeValue("href");
                    // put to the data
                    data.put(chapterName, link);
                    result.add(link);
                }
            }

            return result;
        } else {
            throw new SourceHandlerException("Source should not be null");
        }
    }

    @Override
    public List<String> extractImgUrls(Source source, Map<String, Object> data) throws SourceHandlerException {
        return null;
    }
}
