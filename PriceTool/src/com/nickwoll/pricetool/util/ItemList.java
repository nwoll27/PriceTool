package com.nickwoll.pricetool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class ItemList {
	private List<ItemRecord> itemData;
	private Set<String> itemGroupSet;

	public ItemList() {
		this("list.txt");
	}

	public ItemList(String fileToLoad) {
		itemData = new ArrayList<ItemRecord>();
		loadCSV(fileToLoad);
		buildItemGroupSet();
	}	
	
	public String[] getItemGroups(){		
		return itemGroupSet.toArray(new String[0]);		
	}
	
	private void loadCSV(String fileName) {
		String currentLine = null;
		List<String> currentRecord;
		File inputFile = new File(fileName);

		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));

			while ((currentLine = br.readLine()) != null) {
				if (currentLine.contains(",")) {
					currentRecord = new ArrayList<String>();
					currentLine = currentLine.replaceAll("\"", "");
					StringTokenizer st = new StringTokenizer(currentLine, ",");
					while (st.hasMoreTokens()) {
						currentRecord.add(st.nextToken().trim());
					}
					itemData.add(new ItemRecord(currentRecord));
				}
			}
			br.close();			
		} catch (FileNotFoundException fnf) {
			System.out.println("Error! File " + inputFile.getName()
					+ " not found!");
			fnf.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buildItemGroupSet(){
		itemGroupSet = new LinkedHashSet<String>();
		itemGroupSet.add("ALL");
		for(ItemRecord entry : itemData){
			itemGroupSet.add(entry.getItemGroup());
		}
	}
	
	public List<ItemRecord> getList(){
		return itemData;
	}
}
