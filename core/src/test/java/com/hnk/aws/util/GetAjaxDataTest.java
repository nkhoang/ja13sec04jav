package com.hnk.aws.util;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * This class is used to support an PHP project to get data from a website.
 *
 * @author hnguyen
 */
@Ignore
public class GetAjaxDataTest {
    private static final Logger LOG = LoggerFactory.getLogger(GetAjaxDataTest.class.getCanonicalName());

    @Test
    public void testLoadWards() throws Exception {
        InputStreamReader isReader = new FileReader(new File(
                Thread.currentThread().getContextClassLoader().getResource("id_district.txt").toURI()
        ));


        BufferedReader bufferedReader = new BufferedReader(isReader);
        String districtId;
        StringBuffer sb = new StringBuffer();
        while ((districtId = bufferedReader.readLine()) != null) {
            String source = loadData(districtId, "type=loadward&district=");
            Source html = new Source(source);
            for (Element ele : html.getAllElements()) {
                String wardId = ele.getAttributeValue("value");
                String wardName = ele.getTextExtractor().toString();
                // replace special char
                wardName = StringEscapeUtils.escapeSql(wardName);
                if (StringUtils.isNotBlank(wardId)) {
                    sb.append("\n");
                    sb.append(buildInsert("`pr_ward`", new String[]{"`id_ward`", "`id_district`", "`active`"
                    }, new String[]{wardId, districtId, "1"}));
                    sb.append(buildInsertLang("`pr_ward_lang`",
                            new String[]{"`id_ward`", "`name`", "`id_lang`"}, new String[]{wardId,
                            String.format("'%s'", wardName), "0"}));
                }
            }
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                    "/Applications/MAMP/htdocs/output.txt"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception ex) {
            LOG.error("Error", ex);
        }

        // LOG.info(sb.toString());
    }

    @Test
    public void testLoadDistricts() throws Exception {
        InputStreamReader isReader = new FileReader(new File(
                Thread.currentThread().getContextClassLoader().getResource("data.txt").toURI()
        ));

        BufferedReader bufferedReader = new BufferedReader(isReader);
        String cityId;
        StringBuffer sb = new StringBuffer();
        while ((cityId = bufferedReader.readLine()) != null) {
            String source = loadData(cityId, "type=loaddistrict&city=");
            Source html = new Source(source);
            for (Element ele : html.getAllElements()) {
                String districtId = ele.getAttributeValue("value");
                String districtName = ele.getTextExtractor().toString();
                // replace special char
                districtName = StringEscapeUtils.escapeSql(districtName);
                if (StringUtils.isNotBlank(districtId)) {
                    sb.append("\n");
                    sb.append(buildInsert("`pr_district`", new String[]{"`id_district`", "`id_city`", "`active`", "`contains_wards`", "`has_ward_list`"}, new String[]{districtId, cityId, "1", "1", "1"}));
                    sb.append(buildInsertLang("`pr_district_lang`",
                            new String[]{"`id_district`", "`name`", "`id_lang`"}, new String[]{districtId,
                            String.format("'%s'", districtName), "0"}));
                }
            }
        }

        LOG.info(sb.toString());
    }

    @Test
    public void testLoadCities() throws Exception {
        String source = loadData(null, "type=loadcity");
        Source html = new Source(source);
        StringBuffer sb = new StringBuffer();
        for (Element ele : html.getAllElements()) {
            String cityId = ele.getAttributeValue("value");
            String cityName = ele.getTextExtractor().toString();
            // replace special char
            cityName = StringEscapeUtils.escapeSql(cityName);
            if (StringUtils.isNotBlank(cityId)) {
                sb.append("\n");
                sb.append(buildInsert("`pr_city`", new String[]{"`id_city`", "`id_country`", "`active`", "`contains_districts`",
                        "`has_district_list`"}, new String[]{cityId, "222", "1", "1", "1"}));
                sb.append(buildInsertLang("`pr_city_lang`",
                        new String[]{"`id_city`", "`name`", "`id_lang`"}, new String[]{cityId,
                        String.format("'%s'", cityName), "0"}));
            }
        }

        LOG.info(sb.toString());
    }

    private String buildInsertLang(String table, String[] columns, String[] values) {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 7; i++) {
            sb.append("\n");
            values[values.length - 1] = i + "";
            sb.append(buildInsert(table, columns, values));
        }
        return sb.toString();
    }

    private String buildInsert(String table, String[] columns, String[] values) {
        return String.format("insert into %s (%s) values (%s);", table, StringUtils.join(columns, ","),
                StringUtils.join(values, ","));
    }

    /**
     * @return
     * @throws Exception
     */
    private String loadData(String id, String queryString) throws Exception {
        String urlStr = "";
        // String queryStr = "type=loadward&district=" + districtId;
        String queryStr = queryString;
        if (id != null) {
            queryStr = queryString + id;
        }
        if (StringUtils.isBlank(urlStr)) {
            urlStr = "http://thoitrangdshop.vn/loadselect_ajax.aspx";
        }
        URL url = new URL(urlStr);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        connection.setRequestMethod("POST");

        //Send request
        if (StringUtils.isNotBlank(queryStr)) {
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(queryStr);
            wr.flush();
            wr.close();
        }
        // get inputStream
        InputStream is = connection.getInputStream();

        return new String(IOUtils.toByteArray(is), Charset.forName("UTF-8"));
    }
}
