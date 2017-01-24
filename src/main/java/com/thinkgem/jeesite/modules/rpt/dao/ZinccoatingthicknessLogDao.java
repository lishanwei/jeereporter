/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWeeklyReport;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWorkTeamReport;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingthicknessLog;

/**
 * 锌层测厚日志DAO接口
 * @author aiqing.chu
 * @version 2016-07-07
 */
@MyBatisDao
public interface ZinccoatingthicknessLogDao extends CrudDao<ZinccoatingthicknessLog> {
	
	/**
	 * 实时数据报表查询.
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	List<ZinccoatingthicknessLog> queryList(ZinccoatingthicknessLog zinccoatingthicknessLog);
	
	/**
	 * 查询锌层测厚班组报表.
	 * 
	 * @param zinccoatingWorkTeamReport
	 * @return
	 */
	List<ZinccoatingWorkTeamReport> queryZinccoatingworkteamReport(ZinccoatingWorkTeamReport zinccoatingWorkTeamReport);
	
	/**
	 * 查询锌层测厚周报表.
	 * 
	 * @param zinccoatingWeeklyReport
	 * @return
	 */
	List<ZinccoatingWeeklyReport> queryZinccoatingweeklyReport(ZinccoatingWeeklyReport zinccoatingWeeklyReport);
	
	/**
	 * 查询锌层测厚月报表.
	 * 
	 * @param zinccoatingMonthlyReport
	 * @return
	 */
	List<ZinccoatingWeeklyReport> queryZinccoatingmonthlyReport(ZinccoatingWeeklyReport zinccoatingMonthlyReport);
	
	/**
	 * 删除班组报表数据.
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	int delWorkteamReport(ZinccoatingthicknessLog zinccoatingthicknessLog);
	
	/**
	 * 生成班组报表数据.
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	int batchInsertWorkteamReportData(ZinccoatingthicknessLog zinccoatingthicknessLog);
	
	/**
	 * 删除异常数据.
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	int delExceptionLog(ZinccoatingthicknessLog zinccoatingthicknessLog);
	
	/**
	 * 生成异常数据.
	 * 
	 * @param zinccoatingthicknessLog
	 * @return
	 */
	int batchInsertExceptionLog(ZinccoatingthicknessLog zinccoatingthicknessLog);
	
	/**
	 * 查询班组图表数据.
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryWorkteamReportEChartData(Map<String, Object> params);
	
	/**
	 * 查询周报图表数据.
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryWeeklyReportEChartData(Map<String, Object> params);
	
	/**
	 * 查询月报图表数据.
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryMonthlyReportEChartData(Map<String, Object> params);
	
}