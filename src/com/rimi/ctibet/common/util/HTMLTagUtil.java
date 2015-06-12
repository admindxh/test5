package com.rimi.ctibet.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLTagUtil {
	private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String REGEX_HTML = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";//定义空格回车换行符

    public static String delHTMLTag(String htmlStr) {
    	String htmlStrRes = htmlStr;
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStrRes);
        htmlStrRes = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStrRes);
        htmlStrRes = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStrRes);
        htmlStrRes = m_html.replaceAll(""); // 过滤html标签


        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStrRes);
        htmlStrRes = m_space.replaceAll(""); // 过滤空格回车标签

        return htmlStrRes.trim(); // 返回文本字符串
    }


}
