package com.baselibrary;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class KeyUtilities extends KeySupport {

	/**
	Clicks on an element identified by its text.
	@param locator The locator for the element's text.
	@param ElementName The name of the element.
	*/
	
	public void clickText(String locator, String ElementName) {
		try {
			if(page.waitForSelector("'"+locator+"'").isEnabled()) {				
				page.locator("'"+locator+"'").first().click();
				LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, ElementName);
			} else if(page.getByRole(AriaRole.BUTTON).first().isEnabled()) {
				page.locator("'"+locator+"'").first().click();
				LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, ElementName);
			} else {
				LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, ElementName, null);
			}
		} catch (Exception e) {
			LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, ElementName, e);
		}
	}
	

	/**
	Clicks on an element identified by its locator.
	@param locator The locator for the element.
	@param ElementName The name of the element.
	*/
	
	public void clickElement(String locator, String ElementName){
		try {
			if(page.waitForSelector(locator).isEnabled()) {
				page.locator(locator).first().click();
				LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, ElementName);
			}
		} catch (Exception e) {
			LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK, ElementName, e);
		}
	}

	public String getSelectedValue(String module){
		String selectedValue = null;
		String locator;
		/*
		 * locator="div:has(h6:has-text('"+module+"')):has-text('Selected')";
		 * System.out.println(locator); try {
		 * if(page.waitForSelector(locator).isEnabled()) { selectedValue =
		 * page.locator(locator).locator("span:has-text(/[0-9]+/[0-9]+ Selected/i)").
		 * textContent(); System.out.println("selectedValue "+ selectedValue);
		 * LOGPASS(SHOULD_CLICK, SUCCESS_CLICKED, selectedValue); } } catch (Exception
		 * e) { LOGFAIL(SHOULD_CLICK, FAILED_TO_CLICK,selectedValue , e); } String
		 * selectedValue1 =
		 * page.locator(locator).locator("span:has-text(/[0-9]+/[0-9]+ Selected/i)").
		 * textContent(); System.out.println("selectedValue1 "+ selectedValue1);
		 */
		// Locate the 'General Goods' section
		Locator section = page.locator("div:has(h6:has-text('"+module+"'))");

		// Find all elements containing 'Selected' inside this section
		List<Locator> selectedValues = section.locator("span:has-text('Selected')").all();

		// Extract text and filter using Java regex
		for (Locator selected : selectedValues) {
		     selectedValue = selected.textContent();
		    if (selectedValue.matches("\\d+/\\d+ Selected")) { // Regex to match "0/6 Selected"
		        System.out.println("Selected Value for General Goods: " + selectedValue);
		        break; // Stop after finding the first match
		    }
		}
		return selectedValue;
	}
	
	/**
	Enters a value into an input field.
	@param locator The locator for the input field.
	@param value The value to enter into the input field.
	*/
	
	public void enterValue(String locator, String value){
		try {
			if(page.waitForSelector(locator).isEnabled()) {
				page.fill(locator,value);
				
				LOGPASS(SHOULD_ENTER, SUCCESSFULLY_ENTERED, value);
			}
		} catch (Exception e) {
			LOGFAIL(SHOULD_ENTER, FAILED_TO_ENTER, value, e);
		}
	}

	/**
	 Verifies that an element is displayed on the page.
	 @param ele The locator for the element.
	 @param elementName The name of the element.
	 @throws Exception If the element is not displayed or if an error occurs while verifying its display.
	 */

	public void verifyElementDisplayed(String ele, String elementName) {
		try {
			page.waitForSelector("'"+ele+"'").isVisible();
			page.locator("'"+ ele + "'").first().scrollIntoViewIfNeeded();
			LOGPASS(SHOULD_DISPLAY, SUCCESSFULLY_DISPLAYED, elementName);
		} catch (Exception e) {
			LOGFAIL(SHOULD_DISPLAY, FAILED_TO_DISPLAY, elementName, e);
		}
	}
	
	public void verifyImage(String locator,String src,String imageName) {
		

		
		List<ElementHandle> imageLocators = page.locator("img[alt='" + locator + "'][src*='" + src + "']").elementHandles();
		boolean isImageLoaded = false;
		boolean isImageVisible=false;
		// Ensure the element exists before evaluating
		
		if (imageLocators.isEmpty()) {
			 LOGFAIL(IMAGE_SHOULD_LOADED, IMAGE_FAILED_TO_LOADED, imageName);
		} else {
		    for (int i = 0; i < imageLocators.size(); i++) {
		    	 isImageVisible = (Boolean) imageLocators.get(i).evaluate("img => img.offsetParent !== null");
		         isImageLoaded = (Boolean) imageLocators.get(i).evaluate("img => img.complete && img.naturalWidth > 0");
		       // System.out.println("Image " + (i + 1) + " loaded successfully: " + isImageLoaded);
		    }
		}
		if(isImageLoaded)
      	  LOGPASS(IMAGE_SHOULD_LOADED, IMAGE_SUCCESSFULLY_LOADED, imageName);
        else
      	  LOGFAIL(IMAGE_SHOULD_LOADED, IMAGE_FAILED_TO_LOADED, imageName);
			
		if(isImageVisible)
			LOGPASS(SHOULD_DISPLAY, SUCCESSFULLY_DISPLAYED, imageName);
	     else
	      	  LOGFAIL(SHOULD_DISPLAY, FAILED_TO_DISPLAY, imageName);
          
	}
	
	/*
	 * public void verifyImage1(String locator,String src,String imageName) {
	 * 
	 * Locator imageLocator = page.locator("img[alt='" + locator + "'][src*='" + src
	 * + "']"); boolean isImageLoaded = false; // Ensure the element exists before
	 * evaluating if (imageLocator.count() > 0) { isImageLoaded = (Boolean)
	 * imageLocator.evaluate("img => img.complete && img.naturalWidth > 0");
	 * System.out.println("Image loaded successfully: " + isImageLoaded); } else {
	 * System.out.println("Image not found on the page."); } if(isImageLoaded)
	 * LOGPASS(SHOULD_DISPLAY, SUCCESSFULLY_DISPLAYED, imageName); else
	 * LOGFAIL(SHOULD_DISPLAY, FAILED_TO_DISPLAY, imageName);
	 * 
	 * }
	 */
	/**
	 Verifies that an errormessage is displayed on the page.
	 @param ele The locator for the element.
	 @param elementName The name of the element.
	 @throws Exception If the element is not displayed or if an error occurs while verifying its display.
	 */

	public void verifyErrorDisplayed(String ele, String elementName) {
		try {
			page.waitForSelector(ele).isVisible();
			page.locator(ele).first().scrollIntoViewIfNeeded();
			LOGPASS(SHOULD_DISPLAY, SUCCESSFULLY_DISPLAYED, elementName);
		} catch (Exception e) {
			LOGFAIL(SHOULD_DISPLAY, FAILED_TO_DISPLAY, elementName, e);
		}
	}
	public HashMap<String, String> readJsonFile(String jsonFN, String parentNode, String childNode, String testcaseId) throws Throwable {
		HashMap<String, String> map = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			reader = new FileReader(System.getProperty("user.dir") + "\\TestData\\"+ jsonFN + ".json");
			jsonParser = objectMapper.createParser(reader);
			rootNode = new ObjectMapper().readTree(jsonParser);
			Iterator<Map.Entry<String, JsonNode>> i = rootNode.path(parentNode).get(childNode).get(testcaseId).fields();
			map = new HashMap<>();
			while (i.hasNext()) {
				Map.Entry<String, JsonNode> e = i.next();
				map.put(e.getKey(), e.getValue().textValue());
			}
		} catch (FileNotFoundException e) {
			logger.fail(e.getMessage());
		}
		return map;
	}
	/**
     * Reads data from the specified sheet of an Excel file and stores it in a Map.
     * <p>
     * This method reads an Excel file (.xls or .xlsx) and extracts key-value pairs
     * from the first two columns of the given sheet. The first column represents
     * the keys, and the second column represents the corresponding values.
     * </p>
     *
     * @param filePath  The full path of the Excel file to read.
     * @param sheetName The name of the sheet from which data needs to be read.
     * @return A {@code Map<String, String>} containing key-value pairs extracted from the Excel sheet.
     *         If an error occurs or the file is empty, an empty map is returned.
     * @throws IOException If there is an issue accessing the file.
     * @throws IllegalArgumentException If the sheet is not found or has insufficient columns.
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException If the file format is not valid.
     */
	 public  Map<String, String> readExcelData(String filePath, String sheetName) {
	        Map<String, String> dataMap = new HashMap<>();

	        try (FileInputStream file = new FileInputStream(filePath);
	             Workbook workbook = new XSSFWorkbook(file)) {

	            Sheet sheet = workbook.getSheet(sheetName);
	            if (sheet == null) {
	                throw new RuntimeException("Sheet " + sheetName + " not found in the Excel file.");
	            }

	            for (Row row : sheet) {
	                Cell keyCell = row.getCell(0);  // Column A (Key)
	                Cell valueCell = row.getCell(1); // Column B (Value)

	                if (keyCell != null && valueCell != null) {
	                    String key = keyCell.getStringCellValue().trim();
	                    String value = valueCell.getStringCellValue().trim();
	                    dataMap.put(key, value);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return dataMap;
	    }
	
	/**
	 * Initiates a child test and provides links to TestRail and Jira (if available).
	 * 
	 * @param titleData The map containing title and link data.
	 * @throws Exception 
	 */
	
	public void callChildStartTest(Map<String, String> titleData) throws Exception {
		if(!titleData.get("JiraLink").equals("")) {
			childStartTest(titleData.get("Title") + "&nbsp; <a href='"+titleData.get("TestRailLink")+"' target='_blank'>Visit TestRail</a>"
					+ "&nbsp; <a href='"+titleData.get("JiraLink")+"' target='_blank'>Visit Jira</a>");
		}else {
			childStartTest(titleData.get("Title") + "&nbsp; <a href='"+titleData.get("TestRailLink")+"' target='_blank'>Visit TestRail</a>");
		}
	}
	
	

}
