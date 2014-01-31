package com.nickwoll.pricetool.util;

public class QuoteRecord extends ItemRecord {
	private String listPrice;
	
	public QuoteRecord(int lineNumber, ItemRecord baseItemRecord){
		listPrice = baseItemRecord.getListPrice();
		
		fields.add(Integer.toString(lineNumber));
		fields.add("");
		fields.add("1");
		fields.add(baseItemRecord.getItemNumber());
		fields.add(baseItemRecord.getItemDescription());
		fields.add("");
		fields.add("20%");
		fields.add(calculateNetPrice());
		fields.add(calculateExtendedNetPrice());
	}
	
	@Override
	public String getListPrice(){
		return this.listPrice;
	}
	
	private String calculateNetPrice(){
		double listPrice = Double.parseDouble(getListPrice().replace("$", ""));
		double discountPrice = listPrice-(listPrice*getDiscountMultiplier()); 
		return String.format("$%.2f", discountPrice);
	}
	
	private String calculateExtendedNetPrice(){
		double netPrice = Double.parseDouble(calculateNetPrice().replace("$", ""));
		double extendedPrice = netPrice * Double.parseDouble(getQuantity());
		return String.format("$%.2f", extendedPrice);
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
	
	public String getLineNumber(){
		return fields.get(0);
	}
	
	public String getDetail(){
		return fields.get(1);
	}
	
	public String getQuantity(){
		return fields.get(2);
	}
	
	public void setQuantity(String quantity){
		String quantityString = quantity.trim();
		try{
			Integer.parseInt(quantityString);
			fields.set(2, quantityString);
		} catch (NumberFormatException nfe){
			nfe.printStackTrace();
		}		
	}
	
	public String getShipmentTime(){
		return fields.get(5);
	}
	
	public String getDiscount(){
		return fields.get(6);
	}
	
	private double getDiscountMultiplier(){
		String discountString = fields.get(6).replace("%", "").trim();
		return Double.parseDouble(discountString)/100;
	}
	
	public void setDiscount(String discount){
		String discountString = discount.replace("%", "").trim();
		try{
			if(Double.parseDouble(discountString)>=100)
				discountString="100";
			else if(Double.parseDouble(discountString)<=0)
				discountString="0";
			fields.set(6, discountString +"%");
		} catch (NumberFormatException nfe){
			nfe.printStackTrace();
		}		
	}
	
	public String getNetPrice(){
		return fields.get(7);
	}
	
	public String getExtendedNetPrice(){
		return fields.get(8);
	}
	 
}
