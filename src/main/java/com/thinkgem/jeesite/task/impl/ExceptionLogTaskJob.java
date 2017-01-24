package com.thinkgem.jeesite.task.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.DateUtil;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingthicknessLog;
import com.thinkgem.jeesite.modules.rpt.service.ZinccoatingthicknessLogService;
import com.thinkgem.jeesite.task.ITaskJob;

/**
 * 定期生成异常数据.
 * 
 * @author aiqing.chu
 *
 */
@Service(value = "exceptionLogTaskJob")
public class ExceptionLogTaskJob implements ITaskJob {

	Logger logger = LoggerFactory.getLogger(ExceptionLogTaskJob.class);

	@Autowired
	private ZinccoatingthicknessLogService zinccoatingthicknessLogService;

	@Override
	public void execute() {
		// 从实时表生产班组报表数据
		Date date = new Date();
		try {
			ZinccoatingthicknessLog zinccoatingthicknessLog = new ZinccoatingthicknessLog();
			String beginLogtime = DateUtil.dateToStr(DateUtil.addDateOneDay(date, -1), DateUtil.DateFormat);
			String endLogtime = DateUtil.dateToStr(date, DateUtil.DateFormat);
			logger.info("beginLogtime: {}", beginLogtime);
			logger.info("endLogtime: {}", endLogtime);
			zinccoatingthicknessLog.setBeginLogtime(beginLogtime);
			zinccoatingthicknessLog.setEndLogtime(endLogtime);
			int rows = zinccoatingthicknessLogService.genExceptionLog(zinccoatingthicknessLog);
			logger.info("genExceptionLog success: {} rows.", rows);
		} catch (Exception e) {
			logger.error("genExceptionLog error: {}", e);
		}
	}

}
