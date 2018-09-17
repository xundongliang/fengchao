package com.uumai.crawer.quartz.search.baidu;

import com.uumai.crawer.quartz.QuartzCrawlerTasker;
import com.uumai.crawer.quartz.local.QuartzLocalAppMaster;

public class TestSearch extends QuartzLocalAppMaster {

	@Override
	public void dobusiness() throws Exception {

		QuartzCrawlerTasker tasker = new QuartzCrawlerTasker();
		tasker.setUrl("https://www.jinse.com/");
		tasker.addXpath("result", "//*[@id=\"app\"]/div[3]/div/div[1]/div[2]/div[2]/section/ol[1]/ul/h3/a");
		putDistributeTask(tasker);

	}

	public static void main(String[] args) throws Exception {
		new TestSearch().init().start();
	}
}