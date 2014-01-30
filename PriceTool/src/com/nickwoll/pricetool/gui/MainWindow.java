package com.nickwoll.pricetool.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import com.nickwoll.pricetool.util.*;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String applicationName = "Price Tool Application Demo";
	private ItemList itemList;
	private List<QuoteRecord> quoteList;
	private JTable headerTable;
	private JTable quoteTable;
	private HeaderTableModel headerModel;
	private QuoteTableModel quoteModel;
	final FilterDialog dialog;
	

	public MainWindow(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		itemList = new ItemList();
		quoteList = new ArrayList<QuoteRecord>();
		// Set Up Content Pane
		addComponentsToPane();

		// Display Window
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Setup and Show Filter Dialog
		dialog = new FilterDialog(this, title, itemList, this);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				try {
					// Set Look and Feel
					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());

					new MainWindow(applicationName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void addComponentsToPane() {
		// Define Panel
		JPanel vPanel = new JPanel();
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));
		vPanel.setAlignmentX(LEFT_ALIGNMENT);

		// Define Header Table
		headerTable = new JTable(headerModel = new HeaderTableModel());
		headerTable.setSelectionBackground(Color.white);
		vPanel.add(headerTable);
		
		//Define Quote Table
		quoteTable = new JTable(quoteModel = new QuoteTableModel(quoteList));
		JScrollPane scrollPane = new JScrollPane(quoteTable);
		quoteTable.setFillsViewportHeight(true);
		vPanel.add(scrollPane);

		// Test Block
		JLabel label = new JLabel("Hello World!");
		vPanel.add(label);
		vPanel.add(new JLabel("Test Text"));
		getContentPane().add(vPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("addtoquote".equals(e.getActionCommand())){
			int startListIndex, endListIndex;
			
			startListIndex = quoteList.size() - 1;
			int[] itemListModelIndices = dialog.getSelectedPriceListIndices();
			for(int i=0; i < itemListModelIndices.length; i++){
				addRecordToQuoteTable(itemList.getList().get(itemListModelIndices[i]));
				System.out.println();
			}
			endListIndex = quoteList.size() - 1; 
			quoteModel.fireTableRowsInserted(startListIndex, endListIndex);			
		}

	}
	
	public void addRecordToQuoteTable(ItemRecord itemRecord){
		quoteList.add(new QuoteRecord(quoteList.size()+1, itemRecord));
	}

	class HeaderTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		// private String[] columnNames = {};

		private Object[][] data;

		public HeaderTableModel() {
			this(new Object[][] { { "Date:", "", "Quote #:", "" },
					{ "To E-Mail:", "", "Prepared By:", "" },
					{ "End Customer Company:", "", "E-Mail:", "" },
					{"Attention:", "","","" }});
		}

		public HeaderTableModel(Object[][] data) {
			this.data = data;
			setFocusable(false);
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
			data[rowIndex][colIndex] = aValue;
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

	class QuoteTableModel extends AbstractTableModel {
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
			data.get(rowIndex).getList().set(colIndex, (String)aValue);
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

}
