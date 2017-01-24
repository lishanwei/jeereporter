/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.service;

import java.util.List;

import com.thinkgem.jeesite.common.annotation.DataSourceName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.rpt.entity.BaseZinccoatingthicknessConfig;
import com.thinkgem.jeesite.modules.rpt.dao.BaseZinccoatingthicknessConfigDao;

/**
 * 配置上锌量偏差量Service
 * @author aiqing.chu
 * @version 2016-09-01
 */
@Service
@DataSourceName("office")
@Transactional(readOnly = true)
public class BaseZinccoatingthicknessConfigService extends CrudService<BaseZinccoatingthicknessConfigDao, BaseZinccoatingthicknessConfig> {

	public BaseZinccoatingthicknessConfig get(String id) {
		return super.get(id);
	}
	
	public List<BaseZinccoatingthicknessConfig> findList(BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig) {
		return super.findList(baseZinccoatingthicknessConfig);
	}
	
	public Page<BaseZinccoatingthicknessConfig> findPage(Page<BaseZinccoatingthicknessConfig> page, BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig) {
		return super.findPage(page, baseZinccoatingthicknessConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig) {
		super.save(baseZinccoatingthicknessConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseZinccoatingthicknessConfig baseZinccoatingthicknessConfig) {
		super.delete(baseZinccoatingthicknessConfig);
	}
	
}