package com.wintv.lottery.scores.schedule;

import java.io.IOException;
import java.net.MalformedURLException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.wintv.lottery.scores.XMLConstants;
import com.wintv.lottery.scores.XMLReader;

/**
 * 
 * @author zym
 * @version v 1.0 
 */
public class ReadXmlQuartz extends QuartzJobBean{

	public void doIt() throws MalformedURLException, IOException { 
		XMLReader.read(XMLConstants.bifen);
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		arg0.getJobDetail().setRequestsRecovery(true);
		try {
			this.doIt();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

}
