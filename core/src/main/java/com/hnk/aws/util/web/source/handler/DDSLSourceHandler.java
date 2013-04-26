package com.hnk.aws.util.web.source.handler;

import com.hnk.aws.util.web.exception.SourceHandlerException;
import com.hnk.aws.util.web.source.HTMLSourceHandler;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DDSLSourceHandler extends HTMLSourceHandler {
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

            List<Element> chapterLinks = source.getAllElements("a");
            // check chapter link form
            for (Element linkElement : chapterLinks) {
                String chapterName = linkElement.getTextExtractor().toString();
                // store number only
                chapterName = chapterName.replaceAll("\\D+", "");
                String link = linkElement.getAttributeValue("href");
                if (StringUtils.isNotBlank(link)) {
                    if (link.matches("^http://(.)*vechai\\.info(.)*(song)(.)*(long)(.)*")) {
                        result.add(link);
                        data.put(chapterName, link);
                    }
                }
            }

            return result;
        } else {
            throw new SourceHandlerException("Source should not be null");
        }
    }

    public List<String> extractImgUrls(Source source, Map<String, Object> data) throws SourceHandlerException {
        if (source != null) {
            List<String> result = new ArrayList<String>();

            List<Element> imgLinkElements = source.getAllElements("img");
            // check chapter link form
            int count = 0;
            for (Element imgElement : imgLinkElements) {
                String link = imgElement.getAttributeValue("src");
                if (StringUtils.isNotBlank(link)) {
                    if (link.matches("(.)*(png|jpg)")) {
                        // http://4.bp.blogspot.com/_e9dgDKlWdtU/TbEAhDILKEI/AAAAAAAABHE/nX1TxKViYXY/Hoi%2520011-DDSLT_011_01.jpg?imgmax=1600
                        Matcher matcher = Pattern.compile("(.)*(png|jpg)(.)*").matcher(link);
                        matcher.matches();
                        String fileName = count++ + link.substring(matcher.start(1), matcher.start(1) + 4);

                        result.add(link);
                        data.put(fileName + "", link + "?imgmax=1600");
                    }
                }
            }

            return result;
        } else {
            throw new SourceHandlerException("Source should not be null");
        }

    }
}
