///**
// * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//package com.thinkgem.jeesite.modules.gds.web.ota.service;
//
//import com.fangbaba.gds.face.bean.DistributionQueryBean;
//import com.fangbaba.gds.face.service.IDistributorConfigService;
//import com.fangbaba.gds.po.DistributorConfig;
//import com.google.common.base.Strings;
//import com.thinkgem.jeesite.common.persistence.Page;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * OTA注册管理Service
// *
// * @author zhangliang
// * @version 2016-03-31
// */
//@Service
//public class OTADistributorConfigService {
//
//    @Autowired
//    private IDistributorConfigService iDistributorConfigService;
//
//    public DistributorConfig get(Long id) {
//        return iDistributorConfigService.getDistributorConfig(id);
//    }
//
//    public Page<DistributorConfig> findPage(Page<DistributorConfig> page, DistributorConfig distributorConfig) {
//        com.fangbaba.gds.face.bean.Page gpage = new com.fangbaba.gds.face.bean.Page();
//        gpage.setPageSize(page.getPageSize());
//        gpage.setPageNo(page.getPageNo());
//
//        DistributionQueryBean querybean = new DistributionQueryBean();
//        if (!Strings.isNullOrEmpty(distributorConfig.getName())) {
//            querybean.setName(distributorConfig.getName());
//        }
//        if (!Strings.isNullOrEmpty(distributorConfig.getSaletype())) {
//            querybean.setSaletype(distributorConfig.getSaletype());
//        }
//        if (distributorConfig.getCommissionrate() != null) {
//            querybean.setCommissionrate(distributorConfig.getCommissionrate());
//        }
//        if (distributorConfig.getOtatype() != null) {
//            querybean.setOtatype(String.valueOf(distributorConfig.getOtatype()));
//        }
//        if(distributorConfig.getChannelid()!=null){
//            querybean.setChannelid(distributorConfig.getChannelid());
//        }
//        Map<String, Object> map = iDistributorConfigService.pageQuery(gpage, querybean, false);
//
//        page.setCount((Integer) map.get("total"));
//        page.setList((List<DistributorConfig>) map.get("data"));
//        return page;
//    }
//
//    public void save(DistributorConfig distributorConfig) throws Exception {
//        if (distributorConfig.getId() == null) {
//            this.iDistributorConfigService.addDistributorConfig(distributorConfig);
//        } else {
//            this.iDistributorConfigService.updateDistributorConfig(distributorConfig);
//        }
//    }
//
//    public void delete(DistributorConfig distributorConfig) {
//        this.iDistributorConfigService.delDistributorConfig(distributorConfig.getId());
//    }
//
//    public int countDistributorConfigByChannelid(Integer channelid) {
//        List itemlist = this.iDistributorConfigService.queryByChannelId(channelid);
//        return itemlist != null ? itemlist.size() : 0;
//    }
//}