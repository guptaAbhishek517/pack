package com.packsure.dto;
import java.util.List;
import java.util.Map;




public class OrderApiResponse {


   private List<OrderDTO> data;
   private Map<String, Map<String, Map<String, Object>>> meta;
   
   
   public List<OrderDTO> getData() {
       return data;
   }
   public void setData(List<OrderDTO> data) {
       this.data = data;
   }
public Map<String, Map<String, Map<String, Object>>> getMeta() {
	return meta;
}
public void setMeta(Map<String, Map<String, Map<String, Object>>> meta) {
	this.meta = meta;
}
   
   
}
