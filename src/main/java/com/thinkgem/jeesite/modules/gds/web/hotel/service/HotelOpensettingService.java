///**
// * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//package com.thinkgem.jeesite.modules.gds.web.hotel.service;
//
//import com.fangbaba.gds.face.service.IHotelOpenSettingService;
//import com.fangbaba.gds.po.HotelOpenSetting;
//import com.thinkgem.jeesite.common.persistence.Page;
//import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//
///**
// * 酒店开关设置Service
// *
// * @author zhangliang
// * @version 2016-04-12
// */
//@Service
//public class HotelOpensettingService {
//
//    @Autowired
//    private IHotelOpenSettingService hotelOpenSettingService;
//
//    public HotelOpenSetting get(String id) {
//        return hotelOpenSettingService.findById(Long.parseLong(id));
//    }
//
//    public Page<HotelOpenSetting> findPage(Page<HotelOpenSetting> page, HotelOpenSetting hotelOpensetting) {
//        page.setCount(hotelOpenSettingService.count(hotelOpensetting));
//        page.setList(hotelOpenSettingService.pageQuery(hotelOpensetting, page.getPageNo(), page.getPageSize()));
//        return page;
//    }
//
//    @Transactional(readOnly = false)
//    public void save(HotelOpenSetting hotelOpensetting, Long[] q_roomtype) {
//        String loginname = null;
//        if (UserUtils.getUser() != null) {
//            loginname = UserUtils.getUser().getLoginName();
//        }
//        if (hotelOpensetting.getId() != null) {
//            hotelOpensetting.setUpdateuser(loginname);
//            hotelOpensetting.setUpdatetime(new Date());
//            hotelOpenSettingService.update(hotelOpensetting);
//        } else {
//            if (q_roomtype != null && q_roomtype.length > 0) {
//                HotelOpenSetting[] hotelOpensettings = new HotelOpenSetting[q_roomtype.length];
//                for (int i = 0, n = q_roomtype.length; i < n; i++) {
//                    HotelOpenSetting newHotelOpenSetting = new HotelOpenSetting();
//                    newHotelOpenSetting.setHotelid(hotelOpensetting.getHotelid());
//                    newHotelOpenSetting.setRoomtypeid(q_roomtype[i]);
//                    newHotelOpenSetting.setBegindate(hotelOpensetting.getBegindate());
//                    newHotelOpenSetting.setEnddate(hotelOpensetting.getEnddate());
//                    newHotelOpenSetting.setCreateuser(loginname);
//                    newHotelOpenSetting.setCreatetime(new Date());
//                    hotelOpensettings[i] = newHotelOpenSetting;
//                }
//                hotelOpenSettingService.batchAdd(hotelOpensettings);
//            }
//        }
//    }
//
//    @Transactional(readOnly = false)
//    public void delete(HotelOpenSetting hotelOpensetting) {
//        String loginname = null;
//        if (UserUtils.getUser() != null) {
//            loginname = UserUtils.getUser().getLoginName();
//        }
//        hotelOpenSettingService.delete(hotelOpensetting.getId(), loginname);
//    }
//}