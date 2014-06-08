package com.glpo.gateway.oc;

public class ValidationResultUtils {
    private static final String CSS_STYLES = "padding: 1px 5px; margin-right: 25px;";
    private static final String ASR_VALIDATION_CSS = "max-width: 1000px;";
    private static final String RED_BACK = "background-color: #ffcccc;";
    
    public static String p(String content, String additionalCss) {
        return "<p style=\"" + CSS_STYLES + additionalCss + "\">" + content + "</p>";
    }

    public static String p(String content) {
        return "<p style=\"" + CSS_STYLES + " " + RED_BACK +"\">" + content + "</p>";
    }

}
