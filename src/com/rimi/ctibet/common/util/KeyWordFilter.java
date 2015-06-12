package com.rimi.ctibet.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public final class KeyWordFilter {

	private static final HashMap KEYSMAP = new HashMap();
	private MatchType matchType = MatchType.MAX; // MIN:最小长度匹配 MAX：最大长度匹配
	
	private static boolean isInit = false;
	
	private static void init(){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					KeyWordFilter.class.getClassLoader().getResourceAsStream(
							"badWordDB.dat"),"utf-8"));
			List<String> list = new ArrayList<String>();
			String temp = "";
			while ((temp = br.readLine()) != null) {
				list.add(temp.toLowerCase());
			}
			br.close();
			addKeywords(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private KeyWordFilter(MatchType matchType) {
		if(!isInit){
			//init();
			isInit = true;
		}
		this.matchType = matchType;
	}
	
	/**
	 * 
	 * @Title: rebuild
	 * @Description: 重新构建关键字表
	 * @param 
	 * @return void
	 * @throws
	 */
	public synchronized static void rebuild(){
		try{
			KEYSMAP.clear();
			init();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KeyWordFilter getInstance() {
		return getInstance(MatchType.MAX);
	}

	public static KeyWordFilter getInstance(MatchType matchType) {
		return new KeyWordFilter(matchType);
	}
	
	/**
	 * 构造关键字树状DFA图
	 */
	public static void addKeywords(List<String> keywords) {
		int length = keywords.size();
		for (int i = 0; i < length ; i++) {
			String key = keywords.get(i);
			HashMap nowhash = KEYSMAP;
			for (int j = 0; j < key.length(); j++) {
				char word = key.charAt(j);
				word  = Character.toLowerCase(word);
				Object wordMap = nowhash.get(word);
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap newWordHash = new HashMap();
					newWordHash.put("isEnd", "0");
					nowhash.put(word, newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1) {
					nowhash.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
	 * flag 1:最小长度匹配 2：最大长度匹配
	 */
	private int checkKeyWords(String txt, int begin) {
		HashMap nowhash = KEYSMAP;
		int maxMatchRes = 0;
		int res = 0;
		for (int i = begin; i < txt.length(); i++) {
			char word = txt.charAt(i);
			word =  Character.toLowerCase(word);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (matchType == MatchType.MIN) {
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				return maxMatchRes;
			}
		}
		return maxMatchRes;
	}
	
	public HashMap getTxtKeyWords(String txt){
		return getTxtKeyWords(txt,true);
	}

	/**
	 * 返回txt中关键字的列表
	 */
	public HashMap getTxtKeyWords(String txt,boolean ignoreOther) {
		if(ignoreOther){
			txt = preExecuteText(txt);
		}
		HashMap res = new HashMap();
		res.put("all_bad_words", "");
		for (int i = 0; i < txt.length();) {
			int len = checkKeyWords(txt, i);
			if (len > 0) {
				Object obj = res.get(txt.substring(i, i + len));
				if (obj == null) {
					res.put(txt.substring(i, i + len), Integer.valueOf(1));
				} else {
					Integer count = Integer.valueOf(((Integer) obj).intValue() + 1);
					res.put(txt.substring(i, i + len), count);
				}
				res.put("all_bad_words", res.get("all_bad_words")+" "+(txt.substring(i, i + len)));
				i += len;
			} else {
				i++;
			}
		}
		return res;
	}
	
	
	
	
	public boolean isContentKeyWords(String txt){
		return isContentKeyWords(txt, true);
	}

	/**
	 * 仅判断txt中是否有关键字
	 */
	public boolean isContentKeyWords(String txt,boolean ignoreOther) {
		if(ignoreOther){
			txt = preExecuteText(txt);
		}
		
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i);
			if (len > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 方法说明：判断对象是否有关键字
	 * @param txt
	 * @param ignoreOther
	 * @return
	 */
	public boolean isContentObjectKeyWords(Object obj) {
		JSONObject json = JSONObject.fromObject(obj);
		String txt = "";
		Collection<Object> c = json.values();
	
		if( c != null){
			for(Object s:c){
				txt +=s;
			}
		}else{
			return false;
		}
	
		return isContentKeyWords(txt,true);
	}
	
	public String getFirstBadWord(String txt){
		return getFirstBadWord(txt,true);
	}
	
	
	public String getFirstBadWord(String txt,boolean ignoreOther){
		if(ignoreOther){
			txt = preExecuteText(txt);
		}
		for (int i = 0; i < txt.length(); i++) {
			String word = getFirstBadWord(txt, i);
			if(!"".equals(word)){
				return word;
			}
		}
		return "";
	}
	
	private String getFirstBadWord(String txt,int begin) {
		HashMap nowhash = KEYSMAP;
		int res = 0;
		for (int i = begin; i < txt.length(); i++) {
			char word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					return txt.substring(begin,begin+res);
				}
			} else {
				return "";
			}
		}
		return "";
	}

	private String preExecuteText(String txt) {
		txt = txt.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "").toLowerCase();	
		return txt;
	}

	public String getReplaceStrTxtKeyWords(String txt, String replacestr) {
		return getReplaceStrTxtKeyWords(txt, replacestr, true);
	}

	/**
	 * boolean: true:将txt中的关键字替换成指定字符串  false：将txt中的关键字每个字都替换成指定的字符串
	 */
	public String getReplaceStrTxtKeyWords(String txt, String replacestr,
			boolean flag) {
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < txt.length();) {
			int len = checkKeyWords(txt, i);
			if (len > 0) {
				if (!flag) {
					for (int j = 0; j < len; j++) {
						res.append(replacestr);
					}
				}else{
					res.append(replacestr);
				}
				i += len;
			} else {
				res.append(txt.charAt(i));
				i++;
			}
		}
		return res.toString();
	}

	public HashMap getKeysMap() {
		return new HashMap(KEYSMAP);
	}

	public MatchType changeModel() {
		if (matchType == MatchType.MIN) {
			matchType = MatchType.MAX;
			return MatchType.MIN;
		} else {
			matchType = MatchType.MIN;
			return MatchType.MAX;
		}
	}

	private static enum MatchType {
		MAX, MIN
	}
	
	public static void main(String[] args) {
	   
	}
	
}
