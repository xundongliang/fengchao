package com.uumai.crawer.quartz.local;

import com.google.gson.JsonObject;
import com.uumai.crawer2.CrawlerTasker;
import com.uumai.crawer2.local.LocalAppMaster;

import java.util.List;

public class QuartzLocalAppMaster extends LocalAppMaster {
	
	
    public void putDistributeTask(CrawlerTasker crawlerTasker)  throws Exception {
        this.crawlerTasker=crawlerTasker;
        QuartzLocalCrawlerWorker quartzLocalCrawlerWorker=    new QuartzLocalCrawlerWorker(crawlerTasker);
        localCrawlerWorker=quartzLocalCrawlerWorker;
        quartzLocalCrawlerWorker.download();
        quartzLocalCrawlerWorker.pipeline();
    }

    public JsonObject myTask(CrawlerTasker crawlerTasker)  throws Exception {
        this.crawlerTasker=crawlerTasker;
        QuartzLocalCrawlerWorker quartzLocalCrawlerWorker=    new QuartzLocalCrawlerWorker(crawlerTasker);
        localCrawlerWorker=quartzLocalCrawlerWorker;
        quartzLocalCrawlerWorker.download();
        return quartzLocalCrawlerWorker.pipelineToList();
    }
//	@Override
//	public void dobusiness() throws Exception {
//		super.dobusiness();
//	}

}
