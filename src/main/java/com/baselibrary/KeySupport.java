package com.baselibrary;

import com.aventstack.extentreports.Status;


/*
 * KeySupport class for setting up logger actions and managing key utility configurations.
 */


public class KeySupport extends Configurations {

    public static final String BREAK = "<br/>";
    public static final String SHOULD_ENTER = "Should enter ";
    public static final String SUCCESSFULLY_ENTERED = "Successfully entered ";
    public static final String FAILED_TO_ENTER = "Failed to enter ";
    public static final String SHOULD_CLICK = "Should click ";
    public static final String SUCCESS_CLICKED = "Succesfully clicked ";
    public static final String FAILED_TO_CLICK = "Failed to click ";
    public static final String SHOULD_DISPLAY = "Should display ";
    public static final String SUCCESSFULLY_DISPLAYED = "Successfully displayed ";
    public static final String FAILED_TO_DISPLAY = "Failed to display ";
    public static final String IMAGE_SHOULD_LOADED = "Should LOAD Image ";
    public static final String IMAGE_SUCCESSFULLY_LOADED = "Successfully Loaded the Image ";
    public static final String IMAGE_FAILED_TO_LOADED = "Failed to load the image ";

    public String logMessage(String expected, String actual, String elementName) {
        String logMessage = "<div style='display: flex; justify-content: space-between;'>" +
                "<div style='flex: 1;'>" + expected + " " + elementName + "</div>" +
                "<div style='flex: 1;'>" + actual + " " + elementName + "</div>" +
                "</div>";
        String screenshot = "  <div class='row mb-3'>"
                + "<div class='col-md-3'></div>"
                + "<div class='col-md-3'></div>"
                + "<div class='col-md-3'>"
                + "<img data-featherlight='" + takeScreenShot() + "' src='" + takeScreenShot() + "'>"
                + "    </div></div>";

        return logMessage + screenshot;
    }

    private static int exceptionCounter = 0;

    public String exceptionAndCopyButton(Exception exception) {
        exceptionCounter++;
        return "<details style=\"border: 1px solid #ddd; padding: 5px; border-radius: 5px;\">" +
                "<summary style=\"cursor: pointer; background-color: #f1f1f1; padding: 5px;\">Click to see exception details</summary>" + "<pre id=\"extentReportException" + exceptionCounter + "\">" + exception + "</pre>" + "<button onclick=\"copyToClipboard('extentReportException" + exceptionCounter + "')\">Copy to Clipboard</button>" +
                "</details>" +
                BREAK +
                "<script>" +
                "function copyToClipboard(elementId) {" +
                "  var textarea = document.createElement('textarea');" +
                "  textarea.value = document.getElementById(elementId).innerText;" +
                "  document.body.appendChild(textarea);" +
                "  textarea.select();" +
                "  document.execCommand('copy');" +
                "  document.body.removeChild(textarea);" +
                "}" +
                "</script>";
    }

    public void LOGPASS(String expected, String actual, String elementName) {
//        logger.log(Status.PASS, logMessage(expected, actual, elementName));
        logger.pass(logMessage(expected, actual, elementName));
    }

    public void LOGFAIL(String expected, String actual, String elementName, Exception exception) {
//        logger.log(Status.FAIL, logMessage(expected, actual, elementName) + exceptionAndCopyButton(exception));
        logger.fail(logMessage(expected, actual, elementName));
    }
    public void LOGFAIL(String expected, String actual, String elementName) {
//      logger.log(Status.FAIL, logMessage(expected, actual, elementName) + exceptionAndCopyButton(exception));
      logger.fail(logMessage(expected, actual, elementName));
  }
    public String BELOW(String uiText) { return "*:below(:text('" + uiText + "'))"; }

    public String BELOW(String uiText, String tagName) { return tagName + ":below(:text('" + uiText + "'))"; }
   
    public String BELOWKnowMe(String uiText1, String uiText2) { return "*:below(:text('" + uiText1 + "')):text('" + uiText2 + "')"; }

    //public String BELOWKnowMePartialText(String uiText1, String uiText2) { return "*:below(:has-text('" + uiText1 + "')).locator(:has-text('" + uiText2 + "'))"; }

    
	
    
    
    public String RightOf(String uiText, String tagName) {
        return tagName + ":right-of(:text('" + uiText + "'))";
    }
    
    public String LeftOf(String uiText, String tagName) {
        return tagName + ":left-of(:text('" + uiText + "'))";
    }
}