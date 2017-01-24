/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.annotation.DataSourceName;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rpt.dao.ZinccoatingthicknessLogDao;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWeeklyReport;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingthicknessLog;

/**
 * 锌层测厚日志Service.
 * 
 * @author aiqing.chu
 * @version 2016-07-07
 * 
 */
@Service
@DataSourceName("office")
@Transactional(readOnly = true)
public class ZinccoatingthicknessLogService extends CrudService<ZinccoatingthicknessLogDao, ZinccoatingthicknessLog> {
	
	public ZinccoatingthicknessLog get(String id) {
		return super.get(id);
	}
	
	public List<ZinccoatingthicknessLog> findList(ZinccoatingthicknessLog zinccoatingthicknessLog) {
		return super.findList(zinccoatingthicknessLog);
	}
	
	public Page<ZinccoatingthicknessLog> findPage(Page<ZinccoatingthicknessLog> page, ZinccoatingthicknessLog zinccoatingthicknessLog) {
		return super.findPage(page, zinccoatingthicknessLog);
	}
	
	@Transactional(readOnly = false)
	public void save(ZinccoatingthicknessLog zinccoatingthicknessLog) {
		super.save(zinccoatingthicknessLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(ZinccoatingthicknessLog zinccoatingthicknessLog) {
		super.delete(zinccoatingthicknessLog);
	}
	
	/**
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	public List<ZinccoatingthicknessLog> queryList(ZinccoatingthicknessLog zinccoatingthicknessLog) {
		return dao.queryList(zinccoatingthicknessLog);
	}
	
	/**
	 * 
	 * @param zinccoatingWeeklyReport
	 * @return
	 */
	public List<ZinccoatingWeeklyReport> queryZinccoatingweeklyReport(ZinccoatingWeeklyReport zinccoatingWeeklyReport) {
		return dao.queryZinccoatingweeklyReport(zinccoatingWeeklyReport);
	}
	
	/**
	 * 
	 * @param zinccoatingMonthlyReport
	 * @return
	 */
	public List<ZinccoatingWeeklyReport> queryZinccoatingmonthlyReport(ZinccoatingWeeklyReport zinccoatingMonthlyReport) {
		return dao.queryZinccoatingmonthlyReport(zinccoatingMonthlyReport);
	}
	
	/**
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	public int delWorkteamReport(ZinccoatingthicknessLog zinccoatingthicknessLog) {
		return dao.delWorkteamReport(zinccoatingthicknessLog);
	}
	
	/**
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	@Transactional(readOnly = false)
	public int genWorkteamReport(ZinccoatingthicknessLog zinccoatingthicknessLog) {
		int rows = 0;
		try {
			int dels = dao.delWorkteamReport(zinccoatingthicknessLog);
			logger.info("delete workteam report datas {} rows.", dels);
			rows = dao.batchInsertWorkteamReportData(zinccoatingthicknessLog);
			logger.info("batch insert workteam report datas {} rows.", rows);
			return rows;
		} catch (Exception e) {
			logger.error("genWorkteamReport error: {}", e);
		}
		return rows;
	}
	
	/**
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	@Transactional(readOnly = false)
	public int genExceptionLog(ZinccoatingthicknessLog zinccoatingthicknessLog) {
		int rows = 0;
		try {
			int dels = dao.delExceptionLog(zinccoatingthicknessLog);
			logger.info("delete exception log datas {} rows.", dels);
			rows = dao.batchInsertExceptionLog(zinccoatingthicknessLog);
			logger.info("batch insert exception log datas {} rows.", rows);
			return rows;
		} catch (Exception e) {
			logger.error("genExceptionLog error: {}", e);
		}
		return rows;
	}
	
}