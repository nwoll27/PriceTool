package com.nickwoll.pricetool.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import com.nickwoll.pricetool.util.*;

public class FilterDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ItemList itemList;
	private JLabel priceListLabel;
	private JComboBox<String> priceListCombo;
	private JLabel modelFilterLabel;
	private JTextField modelFilterTextField;
	private JLabel itemGroupLabel;
	private JComboBox<String> itemGroupCombo;
	private JPanel contentPane;
	private JPanel vPanel;
	private JPanel hControllerWidget;
	private JTable priceListTable;
	private TableRowSorter<FilterTableModel> sorter;
	private ActionListener mainWindowListener;
	
	public FilterDialog(JFrame frame, String title, ItemList itemList, ActionListener mainWindowListener){
		super(frame, title, false);
		this.mainWindowListener = mainWindowListener;
		this.itemList = itemList;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addComponentsToPane();
		setSize(new Dimension(800, 500));
	}

	private void addComponentsToPane() {
		//Initialize Panels
		contentPane = new JPanel(new BorderLayout());
		vPanel = new JPanel();
		hControllerWidget = new JPanel();
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));
		
		//DocumentListener for Filter Updates
		DocumentListener textFieldListener = new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateFilters();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateFilters();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateFilters();
			}			
		};
		
		ItemListener comboListener = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateFilters();				
			}			
		};
		
		//Build the filter control components
        priceListLabel = new JLabel("Price List:");
        priceListCombo = new JComboBox<String>(new String[]{"2009"});
        modelFilterLabel = new JLabel("Enter Model Number:");
        modelFilterTextField = new JTextField(20);
        modelFilterTextField.getDocument().addDocumentListener(textFieldListener);
        itemGroupLabel = new JLabel("Item Group:");
        itemGroupCombo = new JComboBox<String>(itemList.getItemGroups());
        itemGroupCombo.addItemListener(comboListener);
        
        JButton addToQuoteButton = new JButton("Add to Quote");
        addToQuoteButton.setActionCommand("addtoquote");
        addToQuoteButton.addActionListener(mainWindowListener);
        
        //Add components to the horizontal panel
        hControllerWidget.add(priceListLabel);
		hControllerWidget.add(priceListCombo);
		hControllerWidget.add(modelFilterLabel);
		hControllerWidget.add(modelFilterTextField);
		hControllerWidget.add(itemGroupLabel);
		hControllerWidget.add(itemGroupCombo);
		hControllerWidget.add(addToQuoteButton);
		vPanel.add(hControllerWidget);
        
		//Build Item list table
		FilterTableModel filterModel = new FilterTableModel(itemList.getList());
		sorter = new TableRowSorter<FilterTableModel>(filterModel);
		priceListTable = new JTable(filterModel);
		priceListTable.setRowSorter(sorter);
		JScrollPane scrollPane = new JScrollPane(priceListTable);
		priceListTable.setFillsViewportHeight(true);
		
		vPanel.add(scrollPane);
		//contentPane.add(closePanel, BorderLayout.PAGE_END);
        contentPane.add(vPanel);
		contentPane.setOpaque(true);
        this.setContentPane(contentPane);		
	}
	
	private void updateFilters(){
		List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
		RowFilter<Object,Object> rf = null;
		try{
			filters.add(RowFilter.regexFilter(modelFilterTextField.getText().toUpperCase(),0));
			if(!((String)itemGroupCombo.getSelectedItem()).equals("ALL"))
				filters.add(RowFilter.regexFilter((String)itemGroupCombo.getSelectedItem(),4));
			rf = RowFilter.andFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e){
			return;
		}
		sorter.setRowFilter(rf);
	}
	
	class FilterTableModel extends AbstractTableModel {
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

	public int[] getSelectedPriceListIndices() {
		int[] modelIndices = new int[priceListTable.getSelectedRows().length];
		
		for(int i = 0; i < priceListTable.getSelectedRows().length; i++){
			modelIndices[i]=priceListTable.convertRowIndexToModel(priceListTable.getSelectedRows()[i]);
		}	
		
		return modelIndices;		
	}
}
