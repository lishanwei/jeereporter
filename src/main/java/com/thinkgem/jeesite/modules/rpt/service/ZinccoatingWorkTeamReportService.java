package com.thinkgem.jeesite.modules.rpt.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.annotation.DataSourceName;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rpt.dao.ZinccoatingWorkTeamReportDao;
import com.thinkgem.jeesite.modules.rpt.dao.ZinccoatingthicknessLogDao;
import com.thinkgem.jeesite.modules.rpt.entity.ZinccoatingWorkTeamReport;

/**
 * 锌层测厚班组报表Service.
 * 
 * @author aiqing.chu
 * @version 2016-07-07
 */
@Service
@DataSourceName("office")
@Transactional(readOnly = true)
public class ZinccoatingWorkTeamReportService extends CrudService<ZinccoatingWorkTeamReportDao, ZinccoatingWorkTeamReport> {
	
	@Resource
	private ZinccoatingthicknessLogDao zinccoatingthicknessLogDao;

	public ZinccoatingWorkTeamReport get(String id) {
		return super.get(id);
	}

	public ZinccoatingWorkTeamReport get(ZinccoatingWorkTeamReport entity) {
		return super.get(entity);
	}

	public List<ZinccoatingWorkTeamReport> findList(ZinccoatingWorkTeamReport entity) {
		return super.findList(entity);
	}

	public Page<ZinccoatingWorkTeamReport> findPage(Page<ZinccoatingWorkTeamReport> page,
			ZinccoatingWorkTeamReport entity) {
		return super.findPage(page, entity);
	}

	@Transactional(readOnly = false)
	public void save(ZinccoatingWorkTeamReport entity) {
		super.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(ZinccoatingWorkTeamReport entity) {
		super.delete(entity);
	}

	@Transactional(readOnly = false)
	public void update(ZinccoatingWorkTeamReport entity) {
		super.update(entity);
	}
	
	/**
	 * 
	 * @param zinccoatingWorkTeamReport
	 * @return
	 */
	public List<ZinccoatingWorkTeamReport> queryZinccoatingworkteamReport(ZinccoatingWorkTeamReport zinccoatingWorkTeamReport) {
		return zinccoatingthicknessLogDao.queryZinccoatingworkteamReport(zinccoatingWorkTeamReport);
	}
	
	/**
	 * 查询班报图表数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryWorkteamReportEChartData(Map<String, Object> params) {
		return zinccoatingthicknessLogDao.queryWorkteamReportEChartData(params);
	}
	
	/**
	 * 查询周报图表数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryWeeklyReportEChartData(Map<String, Object> params) {
		return zinccoatingthicknessLogDao.queryWeeklyReportEChartData(params);
	}

	/**
	 * 查询月报图表数据
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryMonthlyReportEChartData(Map<String, Object> params) {
		return zinccoatingthicknessLogDao.queryMonthlyReportEChartData(params);
	}
}
