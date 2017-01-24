//package com.thinkgem.jeesite.modules.gds.web.distribution;
//
//
//import com.fangbaba.basic.face.bean.CityModel;
//import com.fangbaba.basic.face.bean.HotelModel;
//import com.fangbaba.basic.face.bean.ProvinceModel;
//import com.fangbaba.basic.face.bean.Tags;
//import com.fangbaba.basic.face.service.CityService;
//import com.fangbaba.basic.face.service.HotelService;
//import com.fangbaba.basic.face.service.ProvinceService;
//import com.fangbaba.gds.Message;
//import com.fangbaba.gds.face.bean.HotelModelEsBean;
//import com.fangbaba.gds.face.service.*;
//import com.fangbaba.gds.po.DistributorConfig;
//import com.fangbaba.gds.po.GdsChannel;
//import com.fangbaba.order.service.OrderService;
//import com.fangbaba.stock.face.service.IStockService;
//import com.google.common.base.Function;
//import com.google.common.base.Splitter;
//import com.google.common.base.Strings;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.mk.settlement.enums.OTASettlementTypeEnums;
//import com.thinkgem.jeesite.common.persistence.Page;
//import com.thinkgem.jeesite.common.utils.MathUtil;
//import com.thinkgem.jeesite.common.utils.StringUtils;
//import com.thinkgem.jeesite.common.web.BaseController;
//import com.thinkgem.jeesite.modules.gds.web.ota.service.OTADistributorConfigService;
//import com.thinkgem.jeesite.modules.sys.entity.Dict;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * OTA注册管理Controller
// *
// * @author zhangliang
// * @version 2016-03-31
// */
//@Controller
//@RequestMapping(value = "${adminPath}/distributor/ota")
//public class DistributorOTAController extends BaseController {
//
//    @Autowired
//    private OTADistributorConfigService iDistributorConfigService;
//
//    @Autowired
//    private IGdsChannelService iGdsChannelService;
//
//    @Autowired
//    private IGDSHotelTagService hotelTagService;
//
//    @Autowired
//    private IOtaHotelService otaHotelService;
//
//    @Autowired
//    private ProvinceService provinceService;
//
//    @Autowired
//    private CityService cityService;
//
//    @Autowired
//    private IHotelSearchService hotelSearchService;
//
//    @Autowired
//    private HotelService hotelService;
//    
//    @Autowired
//    private IStockService iStockService;
//    
//    @Autowired
//    private OtaRoomtypeService otaroomtypeService;
//    
//    @Autowired
//    private IDistributorConfigService distributorConfigService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @ModelAttribute("distributor")
//    public DistributorConfig get(@RequestParam(required = false) String id) {
//        if (StringUtils.isNotBlank(id)) {
//            return iDistributorConfigService.get(Long.parseLong(id));
//        } else {
//            return new DistributorConfig();
//        }
//    }
//
//    @RequiresPermissions("gds:ota:view")
//    @RequestMapping(value = {"list", ""})
//    public String list(DistributorConfig distributorConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
//        Page<DistributorConfig> page = iDistributorConfigService.findPage(new Page<DistributorConfig>(request, response), distributorConfig);
//        model.addAttribute("page", page);
//        model.addAttribute("distributorconfig", distributorConfig);
//
//        List<Dict> channeldic = getDictChannelList();
//        Map<Integer, String> channeldkey = Maps.newHashMap();
//        for (Dict dict : channeldic) {
//            channeldkey.put(Integer.parseInt(dict.getValue()), dict.getLabel());
//        }
//        model.addAttribute("channeldic", channeldic);
//        model.addAttribute("channeldkey", channeldkey);
//
//        List<Dict> dictSaleType = getDictSaleType();
//        Map<String, String> saletypekey = Maps.newHashMap();
//        for (Dict dict : dictSaleType) {
//            saletypekey.put(dict.getValue(), dict.getLabel());
//        }
//        model.addAttribute("saletypedic", dictSaleType);
//        model.addAttribute("saletypekey", saletypekey);
//        return "modules/gds/ota/list";
//    }
//
//    @RequiresPermissions("gds:ota:view")
//    @RequestMapping(value = "form")
//    public String form(DistributorConfig distributorConfig, Model model) {
//        if (distributorConfig.getId() != null) {
//            distributorConfig = iDistributorConfigService.get(distributorConfig.getId());
//        } else {
//            distributorConfig.setSaletype(String.valueOf(OTASettlementTypeEnums.B2B.getIndex())); //设置缺省值
//            distributorConfig.setOtatype(MathUtil.getRandomNum());
//        }
//        model.addAttribute("distributorconfig", distributorConfig);
//        model.addAttribute("channeldic", getDictChannelList());
//        model.addAttribute("saletypedic", getDictSaleType());
//
//        model.addAttribute("Q_BUSI_MODE", this.orderService.isNewBusinessMode() ? "true" : "false");
//        return "modules/gds/ota/form";
//    }
//
//    @RequiresPermissions("gds:ota:edit")
//    @RequestMapping(value = "save")
//    public String save(DistributorConfig distributorConfig, String musimode,  Model model, RedirectAttributes redirectAttributes) {
//        if (!beanValidator(model, distributorConfig)) {
//            return form(distributorConfig, model);
//        }
//        if (distributorConfig.getId() == null) {//创建OTA, 需判断是否已存在此渠道
//            int count = iDistributorConfigService.countDistributorConfigByChannelid(distributorConfig.getChannelid());
//            if (count > 0) {
//                addMessage(model, "该渠道已经存在,不允许重复创建.");
//                return form(distributorConfig, model);
//            }
//        }
//        try {
//            if (distributorConfig.getSaletype().equals(String.valueOf(OTASettlementTypeEnums.B2B.getIndex()))) {
//                distributorConfig.setCommissionrate(0.0);
//            }
//            if(Strings.isNullOrEmpty(musimode)){
//                musimode = "false";
//            }
//            this.orderService.optNewBusinessMode("true".equalsIgnoreCase(musimode));
//            distributorConfig.setName(getDictChannelList(distributorConfig.getChannelid()));
//            iDistributorConfigService.save(distributorConfig);
//            addMessage(redirectAttributes, "保存'" + distributorConfig.getName() + "'成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            addMessage(redirectAttributes, "保存'" + distributorConfig.getName() + "'失败");
//        }
//        return "redirect:" + adminPath + "/distributor/ota/?repage";
//    }
//
//    @RequiresPermissions("gds:ota:edit")
//    @RequestMapping(value = "delete")
//    public String delete(DistributorConfig distributorConfig, RedirectAttributes redirectAttributes, String loginname) {
//        iDistributorConfigService.delete(distributorConfig);
//        addMessage(redirectAttributes, "删除成功");
//        return "redirect:" + adminPath + "/distributor/ota/?repage";
//    }
//
//
//    /*****************************************关**联**酒**店************************************************************/
//    /**
//     * 展示关联的酒店列表页面
//     *
//     * @param distributorConfig
//     * @param model
//     * @return
//     */
//    @RequiresPermissions("gds:ota:view")
//    @RequestMapping(value = "assign")
//    public String assign(DistributorConfig distributorConfig, Long q_hotelid, String q_hotelname, HttpServletRequest request, HttpServletResponse response, Model model) {
//        distributorConfig = this.iDistributorConfigService.get(distributorConfig.getId());
//
//        Page<HotelModel> page = new Page<HotelModel>(request, response);
//
//        com.fangbaba.gds.face.bean.Page gpage = new com.fangbaba.gds.face.bean.Page();
//        gpage.setPageSize(page.getPageSize());
//        gpage.setPageNo(page.getPageNo());
//        Map<String, Object> querymap = this.otaHotelService.queryByOtatype(distributorConfig.getOtatype(), gpage, q_hotelid, q_hotelname);
//
//        page.setCount((Integer) querymap.get("total"));
//        page.setList((List<HotelModel>) querymap.get("data"));
//        model.addAttribute("page", page);
//        if (distributorConfig != null) {
//            model.addAttribute("channelname", getDictChannelList(distributorConfig.getChannelid()));
//        }
//
//        Map<String, Object> searchform = Maps.newHashMap();
//        searchform.put("q_hotelid", q_hotelid);
//        searchform.put("q_hotelname", q_hotelname);
//        model.addAttribute("searchform", searchform);
//        model.addAttribute("distributorconfig", distributorConfig);
//        return "modules/gds/ota/hotelassign";
//    }
//
//    /**
//     * 移除关联酒店
//     *
//     * @param redirectAttributes
//     * @return
//     */
//    @RequiresPermissions("gds:ota:edit")
//    @RequestMapping(value = "outhotel")
//    public String outdistributorconfig(String id, String configid, RedirectAttributes redirectAttributes) {
//        if (!Strings.isNullOrEmpty(id)) {
//            otaHotelService.delete(Long.parseLong(id));
//        }
//        return "redirect:" + adminPath + "/distributor/ota/assign?id=" + configid;
//    }
//
//    /**
//     * 移除关联酒店
//     *
//     * @param redirectAttributes
//     * @return
//     */
//    @RequiresPermissions("gds:ota:edit")
//    @RequestMapping(value = "batchouthotel")
//    public String batchdelete(String ids, String configid, RedirectAttributes redirectAttributes) throws Exception {
//        if (!Strings.isNullOrEmpty(ids)) {
//            otaHotelService.batchDelete(ids);
//        }
//        return "redirect:" + adminPath + "/distributor/ota/assign?id=" + configid;
//    }
//
//    /**
//     * 弹出界面,展示待关联酒店列表
//     *
//     * @param distributorconfig
//     * @param model
//     * @return
//     */
//    @RequiresPermissions("gds:ota:edit")
//    @RequestMapping(value = "hoteltodistributor")
//    public String selectHotelToDistributorConfig(HotelModelEsExtBean hotelModelEsExtBean, DistributorConfig distributorconfig, HttpServletRequest request, HttpServletResponse response, Model model) {
//        Page<HotelModel> page = new Page<HotelModel>(request, response);
//
//        List<List<Long>> tags = new ArrayList<List<Long>>();
//        hotelModelEsExtBean.setOtatype(distributorconfig.getOtatype());
//
//        if (hotelModelEsExtBean.getBusiarea() != null && hotelModelEsExtBean.getBusiarea().size() > 0) {
//            tags.add(transform(hotelModelEsExtBean.getBusiarea()));
//        }
//        if (hotelModelEsExtBean.getFeature() != null && hotelModelEsExtBean.getFeature().size() > 0) {
//            tags.add(transform(hotelModelEsExtBean.getFeature()));
//        }
//        if (hotelModelEsExtBean.getFacilities() != null && hotelModelEsExtBean.getFacilities().size() > 0) {
//            tags.add(transform(hotelModelEsExtBean.getFacilities()));
//        }
//        if (hotelModelEsExtBean.getOther() != null && hotelModelEsExtBean.getOther().size() > 0) {
//            tags.add(transform(hotelModelEsExtBean.getOther()));
//        }
//
//
//        Message msg = otaHotelService.queryhotelsByOtatype(hotelModelEsExtBean, tags, page.getPageNo(), page.getPageSize());
//        Map<String, Object> querymap = (Map<String, Object>) msg.getRs();
//        page.setList((List<HotelModel>) querymap.get("data"));
//        page.setCount((Integer) querymap.get("total"));
//
//        model.addAttribute("page", page);
//        model.addAttribute("distributorconfig", distributorconfig);
//        model.addAttribute("hotelModelEsExtBean", hotelModelEsExtBean);
//
//        model.addAttribute("provinces", getDictProvinceList());
//        model.addAttribute("busiarea", queryTags(1l));   //商圈位置
//        model.addAttribute("feature", queryTags(2l));    //类型特色
//        model.addAttribute("facilities", queryTags(3l)); //设施服务
//        model.addAttribute("otatype", distributorconfig.getOtatype());
//        return "modules/gds/ota/queryhotel";
//    }
//
//
//    /**
//     * 分配选择的酒店至对应的OTA
//     *
//     * @param ids
//     * @param redirectAttributes
//     * @return
//     */
//    @RequiresPermissions("gds:ota:edit")
//    @RequestMapping(value = "assigndistributorconfig")
//    public String assignDistributorConfig(String configid, String ids, RedirectAttributes redirectAttributes) {
//        DistributorConfig distributorConfig = this.iDistributorConfigService.get(Long.parseLong(configid));
//
//        List<String> idsList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ids);
//        int newNum = 0;
//        for (String id : idsList) {
//            Message msg = new Message();
//            try {
//                msg = otaHotelService.bindrelation(distributorConfig.getOtatype(), id);
//                //msgstr.append("<br/>新增酒店【" + hotel.getHotelname() + "】到OTA【" + distributorConfig.getName() + "】！");
//                newNum++;
//            } catch (Exception e) {
//                msg.failure("绑定失败");
//                logger.error("分销商与酒店绑定失败", e);
//            }
//        }
//        addMessage(redirectAttributes, "正在分配 " + newNum + " 个酒店,请稍后刷新");
//        return "redirect:" + adminPath + "/distributor/ota/assign?id=" + distributorConfig.getId()+"&repage";
//    }
//
//    @RequiresPermissions("gds:ota:edit")
//    @RequestMapping(value = "relateByCondition", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity relateByCondition(HotelModelEsBean hotelModelEsBean,
//                                            @RequestParam(value = "busiarea[]", required = false) List<String> busiarea,
//                                            @RequestParam(value = "feature[]", required = false) List<String> feature,
//                                            @RequestParam(value = "facilities[]", required = false) List<String> facilities,
//                                            @RequestParam(value = "other[]", required = false) List<String> other, DistributorConfig distributorconfig, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
//        //1. 组织过滤条件
//        Page<HotelModel> page = new Page<HotelModel>(request, response);
//
//        List<List<Long>> tags = new ArrayList<List<Long>>();
//        hotelModelEsBean.setOtatype(distributorconfig.getOtatype());
//
//        if (busiarea != null && busiarea.size() > 0) {
//            tags.add(transform(busiarea));
//        }
//        if (feature != null && feature.size() > 0) {
//            tags.add(transform(feature));
//        }
//        if (facilities != null && facilities.size() > 0) {
//            tags.add(transform(facilities));
//        }
//        if (other != null && other.size() > 0) {
//            tags.add(transform(other));
//        }
//
//        //2. 数据查询
//        List<String> idsList = Lists.newArrayList();
//        int pageNo = 1;
//        while (true) {
//            Message msg = otaHotelService.queryhotelsByOtatype(hotelModelEsBean, tags, pageNo++, page.getPageSize());
//            Map<String, Object> querymap = (Map<String, Object>) msg.getRs();
//            if (querymap == null || querymap.get("data") == null || ((List<HotelModel>) querymap.get("data")).size() == 0) {
//                break;
//            }
//
//            List<String> itemList = Lists.transform((List<HotelModel>) querymap.get("data"), new Function<HotelModel, String>() {
//                @Override
//                public String apply(HotelModel hotelModel) {
//                    return String.valueOf(hotelModel.getId());
//                }
//            });
//            idsList.addAll(itemList);
//        }
//
//        //3. 绑定关联数据
//        int newNum = 0;
//        for (String id : idsList) {
//            Message msg = new Message();
//            try {
//                msg = otaHotelService.bindrelation(distributorconfig.getOtatype(), id);
//                newNum++;
//            } catch (Exception e) {
//                msg.failure("绑定失败");
//                logger.error("分销商与酒店绑定失败", e);
//            }
//        }
//        addMessage(redirectAttributes, "已成功分配 " + newNum + " 个酒店");
//        Map<String, Object> rtn = Maps.newHashMap();
//        rtn.put("resutl", "success");
//        return new ResponseEntity(rtn, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "hotel/suply", method = RequestMethod.GET)
//    public String suply(DistributorConfig distributorConfig, Long hotelid,Long otatype, Model model) {
//        String hotelname = "";
//        String channelname = "";
//
//        distributorConfig = this.get(String.valueOf(distributorConfig.getId()));
//        if (distributorConfig != null) {
//            channelname =  getDictChannelList(distributorConfig.getChannelid());
//        }
//        HotelModel hotelModel = this.hotelService.queryById(hotelid);
//        if(hotelModel != null) {
//            hotelname = hotelModel.getHotelname();
//        }
//        model.addAttribute("hotelname", hotelname);
//        model.addAttribute("channelname", channelname);
//        model.addAttribute("otatype", otatype);
//        model.addAttribute("hotelid", hotelid);
//        model.addAttribute("distributorConfig", distributorConfig);
//        return"modules/gds/ota/suplyprovisioning";
//    }
//    
//    @RequestMapping(value = "hotel/stockquery", method = RequestMethod.GET)
//    public ModelAndView stockquery(Long hotelid,Long otatype,Long id) {
//        ModelAndView modelAndView = new ModelAndView("modules/gds/ota/stockquery");
//        modelAndView.addObject("otatype", otatype);
//        modelAndView.addObject("hotelid", hotelid);
//        modelAndView.addObject("id", id);
//        return modelAndView;
//    }
//
//    /**
//     * 字典: 渠道
//     *
//     * @return
//     */
//    private List<Dict> getDictChannelList() {
//        List<GdsChannel> gdsChannels = iGdsChannelService.queryAllStateGdsChannel();
//        List<Dict> rtnList = Lists.newArrayList();
//        for (GdsChannel gdsChannel : gdsChannels) {
//            if (gdsChannel.getChannelid().intValue() == 1) continue;
//            Dict e = new Dict(String.valueOf(gdsChannel.getChannelid()), gdsChannel.getName());
//            rtnList.add(e);
//        }
//        return rtnList;
//    }
//
//    private List<Dict> queryTags(Long taggroupid) {
//        List<Tags> tagslist = hotelSearchService.queryTagsByGroupId(taggroupid);
//        List<Dict> rtnList = Lists.newArrayList();
//        for (Tags tags : tagslist) {
//            Dict e = new Dict(String.valueOf(tags.getId()), tags.getTagname());
//            rtnList.add(e);
//        }
//        return rtnList;
//    }
//
//    private List<Dict> getDictProvinceList() {
//        List<ProvinceModel> result = provinceService.queryAllProvinces();
//        List<Dict> rtnList = Lists.newArrayList();
//        for (ProvinceModel provinceModel : result) {
//            Dict e = new Dict(String.valueOf(provinceModel.getCode()), provinceModel.getName());
//            e.setDescription(String.valueOf(provinceModel.getId()));
//            rtnList.add(e);
//        }
//        return rtnList;
//    }
//
//    private List<Dict> getDictCityList(Integer proid) {
//        List<CityModel> result = cityService.queryAllCitysByProvinceCode(proid);
//        List<Dict> rtnList = Lists.newArrayList();
//        for (CityModel cityModel : result) {
//            Dict e = new Dict(String.valueOf(cityModel.getCode()), cityModel.getName());
//            rtnList.add(e);
//        }
//        return rtnList;
//    }
//
//    private String getDictChannelList(Integer channelid) {
//        if (channelid == null)
//            return "";
//
//        List<GdsChannel> gdsChannels = iGdsChannelService.queryAllStateGdsChannel();
//        for (GdsChannel gdsChannel : gdsChannels) {
//            if (gdsChannel.getChannelid().intValue() == channelid.intValue()) {
//                return gdsChannel.getName();
//            }
//        }
//        return "";
//    }
//
//    /**
//     * 字典: 销售方式
//     *
//     * @return
//     */
//    private List<Dict> getDictSaleType() {
//        List<Dict> dictList = Lists.newArrayList();
//        for (OTASettlementTypeEnums typeEnums : OTASettlementTypeEnums.values()) {
//            dictList.add(new Dict(String.valueOf(typeEnums.getIndex()), typeEnums.getDesc()));
//        }
//        return dictList;
//    }
//
//    private List<Long> transform(List<String> traslist) {
//        List<Long> rtnval = Lists.transform(traslist, new Function<String, Long>() {
//            @Override
//            public Long apply(String s) {
//                return Long.parseLong(s);
//            }
//        });
//        return rtnval;
//    }
//}