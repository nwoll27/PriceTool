package com.nickwoll.pricetool.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import com.nickwoll.pricetool.util.*;
import com.nickwoll.pricetool.util.TableModels.FilterTableModel;

public class FilterDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ItemList itemList;
	private JPanel contentPane;
	private JPanel vPanel;
	private HorizontalControlWidget hControlWidget;
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
		hControlWidget = new HorizontalControlWidget(itemList.getItemGroups());
		vPanel.setLayout(new BoxLayout(vPanel, BoxLayout.Y_AXIS));
		contentPane.setOpaque(true);
		contentPane.setBackground(new Color(102,167,122));
		vPanel.setOpaque(false);
		
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
		
		//Add Event Listeners to Control Panel
        hControlWidget.getModelFilterTextField().getDocument().addDocumentListener(textFieldListener);
        hControlWidget.getItemGroupCombo().addItemListener(comboListener);
        hControlWidget.getAddToQuoteButton().addActionListener(mainWindowListener);
        
        //Add components to the horizontal panel
		vPanel.add(hControlWidget.getWidgetPanel());
        
		//Build Item list table
		FilterTableModel filterModel = new FilterTableModel(itemList.getList());
		sorter = new TableRowSorter<FilterTableModel>(filterModel);
		priceListTable = new JTable(filterModel);
		priceListTable.setRowSorter(sorter);
		TableModels.styleTableGreen(priceListTable);
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
			filters.add(RowFilter.regexFilter(hControlWidget.getModelFilterTextField().getText().toUpperCase(),0));
			if(!((String)hControlWidget.getItemGroupCombo().getSelectedItem()).equals("ALL"))
				filters.add(RowFilter.regexFilter((String)hControlWidget.getItemGroupCombo().getSelectedItem(),4));
			rf = RowFilter.andFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e){
			return;
		}
		sorter.setRowFilter(rf);
	}	

	public int[] getSelectedPriceListIndices() {
		int[] modelIndices = new int[priceListTable.getSelectedRows().length];
		
		for(int i = 0; i < priceListTable.getSelectedRows().length; i++){
			modelIndices[i]=priceListTable.convertRowIndexToModel(priceListTable.getSelectedRows()[i]);
		}	
		
		return modelIndices;		
	}
}
