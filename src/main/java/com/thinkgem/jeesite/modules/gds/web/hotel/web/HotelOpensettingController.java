///**
// * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//package com.thinkgem.jeesite.modules.gds.web.hotel.web;
//
//import com.fangbaba.basic.face.bean.RoomtypeModel;
//import com.fangbaba.basic.face.service.RoomtypeService;
//import com.fangbaba.gds.po.HotelOpenSetting;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.thinkgem.jeesite.common.config.Global;
//import com.thinkgem.jeesite.common.persistence.Page;
//import com.thinkgem.jeesite.common.service.ServiceException;
//import com.thinkgem.jeesite.common.utils.StringUtils;
//import com.thinkgem.jeesite.common.web.BaseController;
//import com.thinkgem.jeesite.modules.gds.web.hotel.service.HotelOpensettingService;
//import com.thinkgem.jeesite.modules.sys.entity.Dict;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.Map;
//
///**
// * 酒店开关设置Controller
// *
// * @author zhangliang
// * @version 2016-04-12
// */
//@Controller
//@RequestMapping(value = "${adminPath}/hotel/hotelOpensetting")
//public class HotelOpensettingController extends BaseController {
//
//    @Autowired
//    private HotelOpensettingService hotelOpensettingService;
//
//    @Autowired
//    private com.fangbaba.basic.face.service.HotelService hotelServiceDubbo;
//
//    @Autowired
//    private RoomtypeService roomtypeService;
//
//    @ModelAttribute
//    public HotelOpenSetting get(@RequestParam(required = false) String id) {
//        HotelOpenSetting entity = null;
//        if (StringUtils.isNotBlank(id)) {
//            entity = hotelOpensettingService.get(id);
//        }
//        if (entity == null) {
//            entity = new HotelOpenSetting();
//        }
//        return entity;
//    }
//
//    @RequiresPermissions("hotel:hotelOpensetting:view")
//    @RequestMapping(value = {"list", ""})
//    public String list(HotelOpenSetting hotelOpensetting, String page_type, HttpServletRequest request, HttpServletResponse response, Model model) {
//        if (hotelOpensetting.getHotelid() == null) {
//            throw new ServiceException("缺少必填参数: hotelid");
//        }
//        Page<HotelOpenSetting> page = hotelOpensettingService.findPage(new Page<HotelOpenSetting>(request, response), hotelOpensetting);
//        model.addAttribute("page", page);
//
//        List<Dict> dictRoomType = getDictRoomType(hotelOpensetting.getHotelid());
//        Map<Long, String> roomTypeKey = Maps.newHashMap();
//        for (Dict dict : dictRoomType) {
//            roomTypeKey.put(Long.parseLong(dict.getValue()), dict.getLabel());
//        }
//        model.addAttribute("map_roomtype", roomTypeKey);
//        model.addAttribute("dic_roomtype", dictRoomType);
//        model.addAttribute("page_type", page_type);
//        model.addAttribute("hotelOpensetting", hotelOpensetting);
//        model.addAttribute("hotel", hotelServiceDubbo.queryById(hotelOpensetting.getHotelid()));
//        return "web/hotel/hotelOpensettingList";
//    }
//
//    @RequiresPermissions("hotel:hotelOpensetting:view")
//    @RequestMapping(value = "form")
//    public String form(HotelOpenSetting hotelOpensetting, String page_type, Model model) {
//
//        model.addAttribute("page_type", page_type);
//        model.addAttribute("dic_roomtype", getDictRoomType(hotelOpensetting.getHotelid()));
//        model.addAttribute("hotelOpensetting", hotelOpensetting);
//        return "web/hotel/hotelOpensettingForm";
//    }
//
//    @RequiresPermissions("hotel:hotelOpensetting:edit")
//    @RequestMapping(value = "save")
//    public String save(HotelOpenSetting hotelOpensetting, Long[] q_roomtype, String page_type, Model model, RedirectAttributes redirectAttributes) {
//        if (!beanValidator(model, hotelOpensetting)) {
//            return form(hotelOpensetting, page_type, model);
//        }
//        if (hotelOpensetting.getHotelid() == null) {
//            throw new ServiceException("缺少必填参数: hotelid");
//        }
//        hotelOpensettingService.save(hotelOpensetting, q_roomtype);
//        addMessage(redirectAttributes, "保存房型开关成功");
//        return "redirect:" + Global.getAdminPath() + "/hotel/hotelOpensetting/?hotelid="+hotelOpensetting.getHotelid()+"&page_type="+page_type+"&repage";
//    }
//
//    @RequiresPermissions("hotel:hotelOpensetting:edit")
//    @RequestMapping(value = "delete")
//    public String delete(HotelOpenSetting hotelOpensetting, String page_type, RedirectAttributes redirectAttributes) {
//        hotelOpensettingService.delete(hotelOpensetting);
//        addMessage(redirectAttributes, "删除房型开关成功");
//        return "redirect:" + Global.getAdminPath() + "/hotel/hotelOpensetting/?hotelid="+hotelOpensetting.getHotelid()+"&page_type="+page_type+"&repage";
//    }
//
//
//    private List<Dict> getDictRoomType(Long hotelid) {
//        List<Dict> dictList = Lists.newArrayList();
//        List<RoomtypeModel> itemlist = roomtypeService.queryByHotelId(hotelid);
//        for (RoomtypeModel roomtypeModel : itemlist) {
//            dictList.add(new Dict(String.valueOf(roomtypeModel.getId()), roomtypeModel.getName()));
//        }
//        return dictList;
//    }
//
//}