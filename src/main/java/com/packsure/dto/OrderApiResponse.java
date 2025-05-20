package com.packsure.dto;
import java.util.List;
public class OrderApiResponse {
	
   private List<OrderDTO> data;
   public List<OrderDTO> getData() {
       return data;
   }
   public void setData(List<OrderDTO> data) {
       this.data = data;
   }
}
