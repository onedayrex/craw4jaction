package com.git.onedayrex.craw4jaction.imagecraw.craw;

import com.google.common.io.Files;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

public class ImageCraw extends WebCrawler {
    private static final Logger logger = LoggerFactory.getLogger(ImageCraw.class);

    private static final Pattern filters = Pattern.compile(".*(\\.(bmp|gif|jpeg|png|jpg))$");

    private static File imageFolder;
    private static String[] configDomain;

    public static void config(String[] domain,String folderName) {
        configDomain = domain;
        File file = new File(folderName);
        if (file.exists()) {
            file.delete();
        }
        File file1 = new File(folderName);
        file1.mkdir();
        imageFolder = file1;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (filters.matcher(href).matches()) {
            return true;
        }
        //循环爬取主host连接
        for (String s : configDomain) {
            if (href.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        //不是图片||不是进制流的直接返回 不处理
        if (!filters.matcher(url).matches() || !(page.getParseData() instanceof BinaryParseData)) {
            logger.info("crawler page ==> [{}]", url);
            return;
        }
        String extension = url.substring(url.lastIndexOf("."));
        String hashName = UUID.randomUUID().toString().replace("-", "") + extension;
        String fileName = imageFolder.getAbsolutePath()+File.separator+hashName;
        try {
            Files.write(page.getContentData(), new File(fileName));
            logger.info("save image date ==> [{}]", fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
