package com.thinkgem.jeesite.task.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.task.ITaskJob;


/**
 * 
 * @author aiqing.chu
 *
 */
@Service(value="demoTaskJob")
public class DemoTaskJob implements ITaskJob {
	
	Logger logger = LoggerFactory.getLogger(DemoTaskJob.class);
	
	@Override
	public void execute() {
		logger.debug("hello, task job.");
	}

}
