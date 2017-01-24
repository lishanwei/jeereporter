//package com.thinkgem.jeesite.modules.gds.web.wash;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Hashtable;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.fangbaba.basic.face.bean.HotelModel;
//import com.fangbaba.basic.face.service.HotelService;
//import com.fangbaba.gds.face.service.IHotelQRCodeService;
//import com.fangbaba.gds.po.HotelQRCode;
//import com.google.gson.Gson;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.thinkgem.jeesite.common.utils.DateUtil;
//import com.thinkgem.jeesite.common.web.BaseController;
//
//@Controller
//@RequestMapping(value = "${adminPath}/qrcode")
//public class QRCodeController  extends BaseController{
//	private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);
//    /**
//     * 二维码宽度
//     */
//    private static final int width = 150;
//    /**
//     * 二维码高度
//     */
//    private static final int height = 150;
//    /**
//     * icon x坐标
//     */
//    private static final int x = 58;
//    /**
//     * icon y坐标
//     */
//    private static final int y = 58;
//    
//    
//    public void  queryHotel(){
//    	//查询可以打印的酒店
//    	
//    }
//    
//    @Autowired
//    private HotelService hotelService;
//    @Autowired
//    private IHotelQRCodeService hotelQRCodeService;
//    
//
//    /**
//     * 保存二维码，并且在页面中展示
//     * @param hotelQRCodeIn
//     * @return
//     * @throws UnsupportedEncodingException 
//     */
//    @RequestMapping(value = "/saveQRCode")
//    public ModelAndView  saveQRCode(HotelQRCode hotelQRCodeIn) throws UnsupportedEncodingException{
//    	
//    	String url="modules/gds/wash/qrcode";
//    	ModelAndView mav = new ModelAndView(url);
//    	
//    	List<HotelModel> hotelList = hotelService.queryAllHotels();
//    	mav.addObject("hotelList",hotelList);
//    	if(hotelQRCodeIn==null){
//    		return mav;
//    	}
//    	String batchno = DateUtil.dateToStr(new Date(), "yyyyMMdd");
//    	
//    	//查询可以打印的酒店
//    	List<HotelQRCode> list = new ArrayList<HotelQRCode>();
//    	for (int i = 0; i < hotelQRCodeIn.getNum(); i++) {
//    		HotelQRCode code = new HotelQRCode();
//    		code.setHotelid(hotelQRCodeIn.getHotelid());
//    		code.setHotelname(hotelQRCodeIn.getHotelname());
//    		code.setQrtype(hotelQRCodeIn.getQrtype());
//    		
////    		code.setSerialno(UUID.randomUUID().toString());
//    		code.setBatchno(batchno);
//    		
//    		//save
//    		hotelQRCodeService.saveHotelQRCode(code);
//
////    		code.setHotelname(URLEncoder.encode(hotelQRCodeIn.getHotelname(),"UTF-8"));
//    		
//    		
//    		list.add(code);
//		}
//    	mav.addObject("list",list);
//    	return mav;
//    }
//
//    /**
//     * @return
//     * @throws UnsupportedEncodingException 
//     */
//    @RequestMapping(value = "/paint", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> paintQRCode(HotelQRCode hotelQRCodeIn) throws UnsupportedEncodingException {
//    	
//
//    	HotelModel hotel = hotelService.queryById(hotelQRCodeIn.getHotelid());
//    	StringBuilder sf = new StringBuilder();
//    	sf.append("hotelid=").append(hotelQRCodeIn.getHotelid()).append("&");
//    	sf.append("type=").append(hotelQRCodeIn.getQrtype()).append("&");
////    	sf.append("hotelname=").append(URLDecoder.decode(hotelQRCodeIn.getHotelname(), "UTF-8")).append("&");
//    	sf.append("hotelname=").append(hotel.getHotelname()).append("&");
//    	sf.append("id=").append(hotelQRCodeIn.getId());
//    	
//    	String content = sf.toString();
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//
//        String icon = "";
//        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//        // 指定纠错等级
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//        // 指定编码格式
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        // 边框留白，默认为4
//        hints.put(EncodeHintType.MARGIN, 0);
//        try {
//        	
//        	logger.info("二维码内容={}",content);
//            BitMatrix byteMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//            logger.debug("byteMatrix={}",new Gson().toJson(byteMatrix));
//            BufferedImage image = MatrixToImageWriter.toBufferedImage(byteMatrix);
//            Graphics2D gs = image.createGraphics();
//            gs.setBackground(Color.WHITE);
//            gs.clearRect(x, y, 33, 33);
//            gs.setFont(new Font(null, Font.PLAIN, 30));
//            gs.setPaint(Color.BLACK);
//            gs.drawRoundRect(x + 1, y + 1, 30, 30, 5, 5);
//            gs.drawString(icon, x + 7, y + 28);
//            gs.dispose();
//
//            image.flush();
//            ImageIO.write(image, "png", bao);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<byte[]>(bao.toByteArray(), HttpStatus.OK);
//    }
//}
