package com.nickwoll.pricetool.util;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class TableModels {
	public static void styleTableGreen(JTable tableToStyle){
		tableToStyle.getTableHeader().setBackground(new Color(3,136,84));
		tableToStyle.getTableHeader().setForeground(Color.white);
		tableToStyle.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		tableToStyle.setSelectionBackground(new Color(49,106,197));
		tableToStyle.setSelectionForeground(Color.white);
	}
	
	public static class HeaderTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		private Object[][] data;

		public HeaderTableModel() {
			this(new Object[][] { { "Date:", "", "Quote #:", "" },
					{ "To E-Mail:", "", "Prepared By:", "" },
					{ "End Customer Company:", "", "E-Mail:", "" },
					{"Attention:", "","","" }});
		}

		public HeaderTableModel(Object[][] data) {
			this.data = data;
			this.data[0][1] = dateFormat.format(new Date());	
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			if ((col + 1) % 2 == 0 && row != data.length-1)
				return true;
			else if(row == data.length-1 && col==1)
				return true;
			else
				return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int colIndex) {
			if("Date:".equals((String)data[rowIndex][colIndex-1])){
				try{
					data[rowIndex][colIndex] = dateFormat.format(dateFormat.parse((String)aValue));
				} catch (Exception e){
					e.printStackTrace();
				}
			} else {
				data[rowIndex][colIndex] = aValue;
			}			
			fireTableRowsUpdated(rowIndex, rowIndex);
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return data[0].length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
	}
	
	public static class QuoteTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private String[] columnNames = { "Line#", "Detail", "Qty",
				"Item Number", "Item Description", "Shipment(Buss. Days)",
				"Discount", "Net Price Each", "Extended Net Price", };
		private List<QuoteRecord> data;

		public QuoteTableModel(List<QuoteRecord> quoteList) {
			data = quoteList;
		}

		@Override
		public String getColumnName(int column) {
			return this.columnNames[column];
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			if (columnNames[col].equals("Qty") || columnNames[col].equals("Discount"))
				return true;
			else
				return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int colIndex) {
			if(columnNames[colIndex].equals("Qty")){
				data.get(rowIndex).setQuantity((String)aValue);
			} else if(columnNames[colIndex].equals("Discount")){
				data.get(rowIndex).setDiscount((String)aValue);
			}
			data.get(rowIndex).updateQuotePrices();
			fireTableRowsUpdated(rowIndex, rowIndex);
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex).getList().get(columnIndex);
		}

	}

	public static class FilterTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {
				"Item Number", 
				"Item Description", 
				"List Price", 
				"Discount Code", 
				"Item Group"
				};
		private List<ItemRecord> data;
		
		public FilterTableModel(List<ItemRecord> itemData){
			this.data = itemData;
		}
		
		@Override
		public String getColumnName(int column){
			return this.columnNames[column];			
		}
	
		@Override
		public int getRowCount() {
			return data.size();
		}
	
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
	
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex).getList().get(columnIndex);
		}
		
		public ItemRecord getRowRecord(int rowIndex){
			return data.get(rowIndex);
		}
		
	}

}
