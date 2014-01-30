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

	public MainWindow(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		itemList = new ItemList();
		// Set Up Content Pane
		addComponentsToPane();

		// Display Window
		pack();
		// setLocationRelativeTo(null);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Setup and Show Filter Dialog
		final FilterDialog dialog = new FilterDialog(this, title, itemList);
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
		JTable headerTable = new JTable(new HeaderTableModel());
		headerTable.setSelectionBackground(Color.white);
		vPanel.add(headerTable);
		
		//Define Quote Table
		JTable quoteTable = new JTable(new QuoteTableModel());
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
		// TODO Auto-generated method stub

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
		private List<List<String>> data;

		public QuoteTableModel() {
			data = new ArrayList<List<String>>();
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
			data.get(rowIndex).set(colIndex, (String)aValue);
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
			return data.get(rowIndex).get(columnIndex);
		}

	}

}
