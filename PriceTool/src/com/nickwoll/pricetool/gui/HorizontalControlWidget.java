package com.nickwoll.pricetool.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HorizontalControlWidget {
	private JPanel widgetPanel;
	private JLabel priceListLabel;
	private JComboBox<String> priceListCombo;
	private JLabel modelFilterLabel;
	private JTextField modelFilterTextField;
	private JLabel itemGroupLabel;
	private JComboBox<String> itemGroupCombo;
	private JButton addToQuoteButton;

	public HorizontalControlWidget(String[] priceListItemGroups){
		widgetPanel = new JPanel();
		widgetPanel.setOpaque(false);
		addComponentsToWidget(priceListItemGroups);
	}
	
	private void addComponentsToWidget(String[] priceListItemGroups){
		//Build the filter control components
        priceListLabel = new JLabel("Price List:");
        priceListCombo = new JComboBox<String>(new String[]{"2009"});
        
        modelFilterLabel = new JLabel("Enter Model Number:");
        modelFilterTextField = new JTextField(20);
        
        itemGroupLabel = new JLabel("Item Group:");
        itemGroupCombo = new JComboBox<String>(priceListItemGroups);
        
        addToQuoteButton = new JButton("Add to Quote");
        addToQuoteButton.setActionCommand("addtoquote");
        
        //Add the components to the panel
        widgetPanel.add(priceListLabel);
        widgetPanel.add(priceListCombo);
        widgetPanel.add(modelFilterLabel);
        widgetPanel.add(modelFilterTextField);
        widgetPanel.add(itemGroupLabel);
        widgetPanel.add(itemGroupCombo);
        widgetPanel.add(addToQuoteButton);
	}

	public JPanel getWidgetPanel() {
		return widgetPanel;
	}

	public JLabel getPriceListLabel() {
		return priceListLabel;
	}

	public JComboBox<String> getPriceListCombo() {
		return priceListCombo;
	}

	public JLabel getModelFilterLabel() {
		return modelFilterLabel;
	}

	public JTextField getModelFilterTextField() {
		return modelFilterTextField;
	}

	public JLabel getItemGroupLabel() {
		return itemGroupLabel;
	}

	public JComboBox<String> getItemGroupCombo() {
		return itemGroupCombo;
	}

	public JButton getAddToQuoteButton() {
		return addToQuoteButton;
	}
}
