package com.nickwoll.pricetool.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import com.nickwoll.pricetool.util.*;
import com.nickwoll.pricetool.util.TableModels.HeaderTableModel;
import com.nickwoll.pricetool.util.TableModels.QuoteTableModel;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String applicationName = "Price Tool Application Demo";
	private ItemList itemList;
	private List<QuoteRecord> quoteList;
	private JTable headerTable;
	private JTable quoteTable;
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
		headerTable = new JTable(new HeaderTableModel());
		headerTable.setFocusable(false);
		headerTable.setSelectionBackground(Color.white);
		vPanel.add(headerTable);
		
		//Define Quote Table
		quoteTable = new JTable(quoteModel = new QuoteTableModel(quoteList));
		TableModels.styleTableGreen(quoteTable);
		JScrollPane scrollPane = new JScrollPane(quoteTable);
		vPanel.add(scrollPane);
		
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

	

	

}
