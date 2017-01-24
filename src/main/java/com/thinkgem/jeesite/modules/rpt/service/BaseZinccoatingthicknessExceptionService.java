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
import com.thinkgem.jeesite.modules.rpt.entity.BaseZinccoatingthicknessException;
import com.thinkgem.jeesite.modules.rpt.dao.BaseZinccoatingthicknessExceptionDao;

/**
 * 异常历史Service
 * @author aiqing.chu
 * @version 2016-09-01
 */
@Service
@DataSourceName("office")
@Transactional(readOnly = true)
public class BaseZinccoatingthicknessExceptionService extends CrudService<BaseZinccoatingthicknessExceptionDao, BaseZinccoatingthicknessException> {

	public BaseZinccoatingthicknessException get(String id) {
		return super.get(id);
	}
	
	public List<BaseZinccoatingthicknessException> findList(BaseZinccoatingthicknessException baseZinccoatingthicknessException) {
		return super.findList(baseZinccoatingthicknessException);
	}
	
	public Page<BaseZinccoatingthicknessException> findPage(Page<BaseZinccoatingthicknessException> page, BaseZinccoatingthicknessException baseZinccoatingthicknessException) {
		return super.findPage(page, baseZinccoatingthicknessException);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseZinccoatingthicknessException baseZinccoatingthicknessException) {
		super.save(baseZinccoatingthicknessException);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseZinccoatingthicknessException baseZinccoatingthicknessException) {
		super.delete(baseZinccoatingthicknessException);
	}
	
}