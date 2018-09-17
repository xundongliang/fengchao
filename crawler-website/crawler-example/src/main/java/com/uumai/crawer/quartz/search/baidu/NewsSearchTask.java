package com.uumai.crawer.quartz.search.baidu;

import com.google.gson.JsonObject;
import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.local.QuartzLocalAppMaster;
import com.uumai.crawer.quartz.result.QuartzXpathItem;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;

public class NewsSearchTask extends QuartzLocalAppMaster {

    @Override
    public void dobusiness() throws Exception {

        String rootUrl = "http://www.55coin.com/";
        String rootXpath = "//*[@id=\"tabNewsContent\"]/div[1]/article";
        String countXpath = "//*[@id=\"tabNewsContent\"]/div[1]/article/count()";
        String urlXpath = "/header/h2/a" + "/@href";
        String titleXpath = "/header/h2/a" + "/text()";
        String summaryXpath = "/p[1]" + "/text()";
        String imgXpath = "/div/a/img" + "/@src";
        String contentXpath = "/html/body/section/div/div/article" + "/html()";

        QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
        tasker.setUrl(rootUrl);
        QuartzCrawlerTasker cotentTasker = new QuartzCrawlerTasker();

        for (int i = 1; i <= 50; i++) {
            try {
                tasker.setXpathList(new ArrayList<QuartzXpathItem>());
                tasker.addXpath("title", rootXpath + "[" + i + "]" + titleXpath);
                tasker.addXpath("summary", rootXpath + "[" + i + "]" + summaryXpath);
                tasker.addXpath("img", rootXpath + "[" + i + "]" + imgXpath);
                tasker.addXpath("url", rootXpath + "[" + i + "]" + urlXpath);
                tasker.addXpath("count", countXpath);
                JsonObject jsonObject = myTask(tasker);

                System.out.println(jsonObject.toString());
                String title = jsonObject.get("title").getAsString();
                if (StringUtils.isEmpty(title)) {
                    continue;
                }

                if (jsonObject != null) {
                    String detailUrl = jsonObject.get("url").getAsString();
                    if (!detailUrl.contains(rootUrl) && !detailUrl.contains("http")) {
                        detailUrl = rootUrl + detailUrl;
                    }
                    cotentTasker.setUrl(detailUrl);
                    cotentTasker.setXpathList(new ArrayList<QuartzXpathItem>());
                    cotentTasker.addXpath("content", contentXpath);
                    JsonObject contentObject = myTask(cotentTasker);

                    System.out.println("content:" + contentObject.get("content").getAsString());
                    String content = jsonObject.get("content").getAsString();
                    if (StringUtils.isEmpty(content)) {
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws Exception {
        new NewsSearchTask().init().start();
    }
}