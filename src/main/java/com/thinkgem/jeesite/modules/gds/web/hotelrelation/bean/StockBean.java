/**
 * 2016年4月26日下午4:19:45
 * zhaochuanbin
 */
package com.thinkgem.jeesite.modules.gds.web.hotelrelation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaochuanbin
 *
 */
public class StockBean  implements Serializable{

    private static final long serialVersionUID = -6566111276651840342L;
    
    private Long roomtypeid;
    private String roomtypename;
    private List<Long> days;
    private List<Integer> roomnum;
    public Long getRoomtypeid() {
        return roomtypeid;
    }
    public void setRoomtypeid(Long roomtypeid) {
        this.roomtypeid = roomtypeid;
    }
    public String getRoomtypename() {
        return roomtypename;
    }
    public void setRoomtypename(String roomtypename) {
        this.roomtypename = roomtypename;
    }
    public List<Long> getDays() {
        return days;
    }
    public void setDays(List<Long> days) {
        this.days = days;
    }
    public List<Integer> getRoomnum() {
        return roomnum;
    }
    public void setRoomnum(List<Integer> roomnum) {
        this.roomnum = roomnum;
    }
    
}
