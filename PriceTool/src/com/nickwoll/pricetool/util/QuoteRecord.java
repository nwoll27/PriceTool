package com.nickwoll.pricetool.util;

public class QuoteRecord extends ItemRecord {
	String listPrice;
	public QuoteRecord(int lineNumber, ItemRecord baseItemRecord){
		listPrice = baseItemRecord.getListPrice();
		
		fields.add(Integer.toString(lineNumber));
		fields.add("");
		fields.add("");
		fields.add(baseItemRecord.getItemNumber());
		fields.add(baseItemRecord.getItemDescription());
		fields.add("");
		fields.add("0");
		fields.add(calculateNetPrice());
		fields.add(calculateExtendedNetPrice());
	}
	
	private String calculateNetPrice(){
		return "";
	}
	
	private String calculateExtendedNetPrice(){
		return "";
	}
	
	public void updateQuotePrices(){
		setNetPrice(calculateNetPrice());
		setExtendedNetPrice(calculateExtendedNetPrice());
	}
	
	private void setNetPrice(String netPrice){
		fields.set(7, netPrice);
	}
	
	private void setExtendedNetPrice(String extendedNetPrice){
		fields.set(8, extendedNetPrice);
	}
	 
}
