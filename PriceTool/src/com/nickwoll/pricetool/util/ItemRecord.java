package com.nickwoll.pricetool.util;

import java.util.List;

public class ItemRecord {
	private List<String> fields;
		
	public ItemRecord(List<String> recordData){
		fields = recordData;		
	}
	
	public List<String> getList(){
		return fields;
	}
	
	public String getItemNumber(){
		return fields.get(0);
	}
	
	public void setItemNumber(String itemNumber){
		fields.set(0, itemNumber);
	}
	
	public String getItemDescription(){
		return fields.get(1);
	}
	
	public void setItemDescription(String itemDescription){
		fields.set(1, itemDescription);
	}
	
	public String getListPrice(){
		return fields.get(2);
	}
	
	public void setListPrice(String listPrice){
		fields.set(2, listPrice);
	}
	
	public String getDiscountCode(){
		return fields.get(3);
	}
	
	public void setDiscountCode(String discountCode){
		fields.set(3, discountCode);
	}
	
	public String getItemGroup(){
		return fields.get(4);
	}
	
	public void setItemGroup(String itemGroup){
		fields.set(4, itemGroup);
	}
	

}
