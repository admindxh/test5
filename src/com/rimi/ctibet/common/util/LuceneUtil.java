package com.rimi.ctibet.common.util;

import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.View;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xiaozhen
 * lucene管理索引，包括用户的模糊查询，以及后台的修改删除时的精确查询。
 */
public class LuceneUtil {
	
	private static IndexWriter indexWriter;
	private static Analyzer analyzer;
	private static Directory directory;
	private static IndexSearcher searcher ;
	private static String path;
	static{
	    path = "E://CtibetIndex";
		analyzer = new IKAnalyzer();
		// 1. 创建（索引）目录
		try {
			directory = new SimpleFSDirectory(new File(path));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**添加索引
	 * @param obj 需要绑定索引的对象，必须含有code。
	 * @param url  索引返回的url用户点击url进入具体的页面。
	 */
	public static void addDocuemnt(Object obj,String url){
		String code = "";
		String title = "";
		String summary = "";
		if(obj instanceof Merchant){
		   Merchant merchant = (Merchant)obj;
		   code = merchant.getCode();
		   title = merchant.getMerchantName();
		   summary = merchant.getMerchantSummary();
		}else if(obj instanceof View){
			View view = (View)obj;
			code = view.getCode();
			title = view.getViewName();
			summary = view.getViewIntroduce();
		}else if(obj instanceof Content){
			Content content = (Content)obj;
			code = content.getCode();
			title = content.getContentTitle();
			summary = content.getContent();
		}
		Document d= new Document();
		d.add(new Field("code",code,Store.YES, Index.ANALYZED));
		d.add(new Field("title",title,Store.YES, Index.ANALYZED));
		d.add(new Field("summary",summary,Store.YES, Index.ANALYZED));
		d.add(new Field("url",url,Store.YES, Index.ANALYZED));
		try {
			indexWriter = new IndexWriter(directory, analyzer,new MaxFieldLength(10000));
			indexWriter.addDocument(d);
			//优化索引库
			indexWriter.optimize();
			indexWriter.close();
			//System.out.println("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**修改索引
	  * @param obj 需要绑定索引的对象，必须含有code。
	 * @param url  索引返回的url用户点击url进入具体的页面。
	 */
	public static void updateDocument(Object obj,String url){
		String code = "";
		String title = "";
		String summary = "";
		if(obj instanceof Merchant){
		   Merchant merchant = (Merchant)obj;
		   code = merchant.getCode();
		   title = merchant.getMerchantName();
		   summary = merchant.getMerchantSummary();
		}else if(obj instanceof View){
			View view = (View)obj;
			code = view.getCode();
			title = view.getViewName();
			summary = view.getViewIntroduce();
		}else if(obj instanceof Content){
			Content content = (Content)obj;
			code = content.getCode();
			title = content.getContentTitle();
			summary = content.getContent();
		}
		Document d = new Document(); 
		try {
			indexWriter = new IndexWriter(directory, analyzer,new MaxFieldLength(10000));
			d.add(new Field("code",code,Store.YES, Index.ANALYZED));
			d.add(new Field("title",title,Store.YES, Index.ANALYZED));
			d.add(new Field("summary",summary,Store.YES, Index.ANALYZED));
			d.add(new Field("url",url,Store.YES, Index.ANALYZED));
			indexWriter.updateDocument(new Term("code",code),d); 
			//优化索引库
			indexWriter.optimize();
			indexWriter.close();
			//System.out.println("修改成功");
		} catch (Exception e) {
			e.printStackTrace();}
	}
	/**批量删除索引，根据codeString(code1,code2,.....)
	 * @param code
	 */
	public static void deleteDocument(String codeString){
		try {
			String[] codes = codeString.split(",");
			indexWriter = new IndexWriter(directory, analyzer,new MaxFieldLength(10000));
			for (String code : codes) {
				Term term = new Term("code", code);
				indexWriter.deleteDocuments(term );
				//System.out.println("删除成功");
			}
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**用户模糊查询
	 * @param keyWord 关键字，在索引的title与summary中搜索
	 * @return 返回所有的匹配集合，map中包含索引的详细信息
	 */
	public  static ArrayList<HashMap<String, String>> search(String keyWord,String view,String area){
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		try {
			searcher = new IndexSearcher(directory);
			BooleanQuery query = new BooleanQuery();
			PhraseQuery query1 = new PhraseQuery();
			PhraseQuery query2 = new PhraseQuery();
			PhraseQuery query3 = new PhraseQuery();
			PhraseQuery query4 = new PhraseQuery();
			PhraseQuery query5 = new PhraseQuery();
			PhraseQuery query6 = new PhraseQuery();
			if(keyWord!=null&&!keyWord.equals("")){
				query1.add(new Term("title",keyWord));
				query2.add(new Term("summary",keyWord));
				query.add(query1, Occur.SHOULD);
				query.add(query2, Occur.SHOULD);
			}if(view!=null&&!view.equals("")){
				query3.add(new Term("title",view));
				query4.add(new Term("summary",view));
				query.add(query3, Occur.SHOULD);
				query.add(query4, Occur.SHOULD);
			}if(area!=null&&!area.equals("")){
				query5.add(new Term("title",area));
				query6.add(new Term("summary",area));
				query.add(query5, Occur.SHOULD);
				query.add(query6, Occur.SHOULD);
			}
				//********************1.创建高亮器*********************************************
				// 高亮格式化配置文件
				Formatter formatter = new SimpleHTMLFormatter("<font class='myTheme'>","</font>");
				// 设置查询打分器
				Scorer fragmentScorer = new QueryScorer(query);
				Highlighter highlighter = new Highlighter(formatter, fragmentScorer);
				// 设置最大截取长度
				Fragmenter fragmenter =new SimpleFragmenter(150);
				highlighter.setTextFragmenter(fragmenter);		
				TopDocs tds = searcher.search(query, 10);
				//System.out.println("符合条件的个数：" + tds.totalHits);
				
				// 7. 获取匹配的结果集合
				ScoreDoc[] scoreDocs = tds.scoreDocs;
				for (ScoreDoc scoreDoc : scoreDocs) {
					int docId = scoreDoc.doc; // 内部文档编号 
					Document document = searcher.doc(docId);
					// 展示
					//System.out.println("==============" + docId+"====================================");
					String title=highlighter.getBestFragment(analyzer, "title", document.get("title"));
					//System.out.println("title:"+title);
					//System.out.println(document.get("title"));
					String summary=highlighter.getBestFragment(analyzer, "summary", document.get("summary"));
					//System.out.println("summary :"+summary);
					//System.out.println(document.get("summary"));
					
					Map<String,String> dataMap = new HashMap<String,String>();
					if(title!=null)
						dataMap.put("title",title);
					else
						dataMap.put("title", document.get("title"));
					if(summary!=null)	
						dataMap.put("summary",summary);
					else
						dataMap.put("summary",document.get("summary"));
					dataMap.put("code", document.get("code"));
					dataMap.put("module", document.get("module"));
					dataMap.put("url", document.get("url"));
					dataMap.put("type", document.get("type"));
					dataList.add((HashMap<String, String>) dataMap);
				}
				// 8.结束查询
				searcher.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
  }
	
	//系统精准查询
	public static Document getDocumentByCode(String code){
		Document document = null;
		try {
			searcher = new IndexSearcher(directory);
			TermQuery query = new TermQuery(new Term("code",code));
			TopDocs tds = searcher.search(query,1);
			//System.out.println("符合条件的个数：" + tds.totalHits);
			// 获取匹配的结果集合
			ScoreDoc[] scoreDocs = tds.scoreDocs;
			int docId = scoreDocs[0].doc; // 内部文档编号 
			document = searcher.doc(docId);
			//================输出显示===================
			//System.out.println("title=="+document.get("title"));
			//System.out.println("summary=="+document.get("summary"));
			//System.out.println("module=="+document.get("module"));
			//System.out.println("url=="+document.get("url"));
			return document;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	//索引库显示
	public static void indexList(){
		//System.out.println("==============================索引库=================================");
		IndexReader indexReader;
		try {
			indexReader = IndexReader.open(directory);
			TermEnum terms = indexReader.terms();
			while(terms.next()){
				//System.out.println(terms.term());
			}
			//System.out.println("存储的文档数:" + indexReader.numDocs());
			//System.out.println("总存储量:" + indexReader.maxDoc());
			//System.out.println("被删除的文档：" + indexReader.numDeletedDocs());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @Test
    public void luceneTest(){
//        Merchant m =new Merchant();
//        m.setCode("001");
//        m.setMerchantName("牛肉汤餐馆");
//        m.setMerchantSummary("来吃牦牛肉,有点贵哈");
//    	View v= new View();
//    	v.setCode("002");
//    	v.setViewName("布达拉宫");
//    	v.setViewIntroduce("都来膜拜我吧，哈哈哈哈哈哈哈哈哈！！！！");
//    	LuceneUtil.addDocuemnt(v, "http://localhost:8080/ctibet/travel/noteManager/getNoteByCode?code='002'");
//    	LuceneUtil.addDocuemnt(m,  "http://localhost:8080/ctibet/travel/noteManager/getNoteByCode?code='001'");
//    	LuceneUtil.addDocuemnt("002", "狼图腾", "讲述草原狼的故事。", "游西藏", "http://localhost:8080/ctibet/travel/noteManager/getNoteByCode?code='002'", "note");
//        LuceneUtil.indexList();
    	LuceneUtil.search("布达","餐馆","姑娘");
//    	Content c= new Content();
//    	c.setCode("003");
//    	c.setContentTitle("这是如此美丽的草原");
//    	c.setContent("在那遥远的地方，有一个好姑娘，她那美丽的脸庞让我魂不守舍。");
//    	LuceneUtil.addDocuemnt(c, "http://localhost:8080/ctibet/travel/noteManager/getNoteByCode?code='001'");
//        LuceneUtil.deleteDocument("001,002,003");
//    	LuceneUtil.search("草原");
    }

}