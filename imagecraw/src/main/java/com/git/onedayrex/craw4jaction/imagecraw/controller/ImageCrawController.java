package com.git.onedayrex.craw4jaction.imagecraw.controller;

import com.git.onedayrex.craw4jaction.imagecraw.craw.ImageCraw;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ImageCrawController {

    public static void main(String[] args) throws Exception {
        String crawFolder = "crawData";
        String imageFolder = "D:/images";

        //"http://www.nipic.com/",
        String[] crawDomains = {
                "https://www.bilibili.com/"};
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1");
        crawlConfig.setCrawlStorageFolder(crawFolder);
        crawlConfig.setIncludeBinaryContentInCrawling(true);
        crawlConfig.setIncludeHttpsPages(true);
        PageFetcher pageFetcher = new PageFetcher(crawlConfig);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController crawlController = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);
        for (String crawDomain : crawDomains) {
            crawlController.addSeed(crawDomain);
        }
        ImageCraw.config(crawDomains,imageFolder);
        crawlController.start(ImageCraw.class, 20);
    }
}
