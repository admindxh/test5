package com.rimi.ctibet.common.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.dic.*;

public class LuceneUtil2 {
	public static String path = "";
	private static IndexWriter writer;
	private static IndexReader reader;
	private final static Logger LOG = Logger.getLogger(LuceneUtil2.class);
	private String keywords;//关键词，搜索参数。搜索的时候用到！
	
	private static boolean addAtom(WebSearch webSearch) {
		try {
			IndexWriter writer = getIndexWriter();
			Document doc = createDoc(webSearch);
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 为内容添加搜索索引。
	 * 
	 * @param webSearchs
	 * <br>
	 *            code必须设置
	 * @return 成功与否
	 */
	public static boolean add(WebSearch... webSearchs) {
		for (WebSearch webSearch : webSearchs) {
			if (!addAtom(webSearch)) {
				return false;
			}
			LOG.info("add...LWS..." + webSearch);
		}
		try {
			getIndexWriter().optimize();
			getIndexWriter().commit();
			getIndexWriter().close();
			writer = null;
		} catch (Exception e) {
			writer = null;
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static Directory getDirectory() {
		Directory directory = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			directory = new SimpleFSDirectory(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directory;
	}

	private static IndexWriter getIndexWriter() {
		if (writer == null) {
			try {

				if (getDirectory().listAll().length == 0) {

					writer = new IndexWriter(getDirectory(), new IKAnalyzer(),
							true, MaxFieldLength.UNLIMITED);

				} else {
					writer = new IndexWriter(getDirectory(), new IKAnalyzer(),
							false, MaxFieldLength.UNLIMITED);
				}
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (LockObtainFailedException e) {
				try {
					if (writer.isLocked(getDirectory())) {
						writer.unlock(getDirectory());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return writer;
	}

	// 全局 尽量少创建indexReander，打开一次消耗魔法值较高！
	private static IndexReader getIndexReader() {
		try {
			if (reader == null) {
				if (null != (reader = openReader(0))) {
					// System.out.println("打开成功！");
				} else {
					// System.out.println("打开索引失败");
				}
			}
		} catch (Exception e) {

		}
		return reader;

	}

	private static IndexReader openReader(Integer retry) {

		try {
			retry++;
			return IndexReader.open(getDirectory(), true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (retry > 10) {

				return null;
			}
			return openReader(retry);
		}
	}

	private static Document createDoc(WebSearch webSearch) {
		Document doc = new Document();
		// Store.YES 存储与否
		// Field.Index.NO 不索引，如果存储选项为YES，一般用于只保存不搜索的字段；
		// Field.Index.ANALYZED 分词建索引；
		// Field.Index.NOT_ANALYZED
		// 建索引但不分词，字段虽然被索引但是没有任何分析器对字段进行分析，只能整词精确搜索，可保存唯一性字段（例如ID）并用于更新索引
		// Field.Index.*_NORMS特殊用途 搞起来消耗内存大，施法速度快
		doc.add(new Field(WebSearch.KEY_CODE, webSearch.getCode(), Store.YES,
				Index.NOT_ANALYZED));
		doc.add(new Field(WebSearch.KEY_TYPE, webSearch.getType(), Store.YES,
				Index.NOT_ANALYZED));
		doc.add(new Field(WebSearch.KEY_TITLE, webSearch.getTitle(), Store.YES,
				Index.ANALYZED));
		doc.add(new Field(WebSearch.KEY_CONTENT, webSearch.getContent(),
				Store.YES, Index.ANALYZED));
		doc.add(new Field(WebSearch.KEY_IMAGEURL, webSearch.getImageUrl(),
				Store.YES, Index.NO));
		doc.add(new Field(WebSearch.KEY_URL, webSearch.getUrl(), Store.YES,
				Index.NO));
		doc.add(new Field(WebSearch.KEY_DATE, webSearch.getDate() + "",
				Store.YES, Index.NO));
		return doc;
	}

	private static boolean deleteAtom(WebSearch webSearch) {
		Term term = new Term(WebSearch.KEY_CODE, webSearch.getCode());
		try {
			getIndexWriter().deleteDocuments(term);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除内容的索引
	 * 
	 * @param webSearchs
	 * <br>
	 *            code必须设置
	 * @return 成功与否
	 */
	public static boolean delete(WebSearch... webSearchs) {
		for (WebSearch webSearch : webSearchs) {
			if (!deleteAtom(webSearch)) {
				return false;
			}
			LOG.info("delete...LWS..." + webSearch);
		}
		try {
			getIndexWriter().commit();
			getIndexWriter().close();
			writer = null;
		} catch (Exception e) {
			writer = null;
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean update(WebSearch... webSearchs) {
		for (WebSearch webSearch : webSearchs) {
			if (!updateAtom(webSearch)) {
				return false;
			}
			LOG.info("update...LWS..." + webSearch);
		}
		try {
			getIndexWriter().optimize();
			getIndexWriter().commit();
			getIndexWriter().close();
			writer = null;
		} catch (Exception e) {
			writer = null;
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private static boolean updateAtom(WebSearch webSearch) {
		try {
			getIndexWriter().updateDocument(
					new Term(WebSearch.KEY_CODE, webSearch.getCode()),
					createDoc(webSearch));
		} catch (LockObtainFailedException e) {
			try {
				if (writer.isLocked(getDirectory())) {
					writer.unlock(getDirectory());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Pager search(Pager pager, WebSearch webSearch) {
		String fields[] = { webSearch.KEY_TITLE, webSearch.KEY_CONTENT };
		MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(
				Version.LUCENE_30, fields, new IKAnalyzer());
		try {
			keywords=webSearch.getKeywords();
			Query query = multiFieldQueryParser.parse(webSearch.getKeywords());
			IndexSearcher searcher = new IndexSearcher(getIndexReader());
			Sort sort = new Sort(new SortField(WebSearch.KEY_DATE,
					SortField.LONG, true));
			pager.setTotalCount(searcher.search(query, null, 5 * 100).totalHits);
			TopDocs topDocs = searcher.search(query, null, pager.getEndIndex(),
					sort);
			//
			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
					"<span style=\"color: red;\" >", "</span>");
			/** 创建QueryScorer */
			QueryScorer scorer = new QueryScorer(query);
			/** 创建Fragmenter */
			Highlighter highlight = new Highlighter(formatter, scorer);
			//
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			// 查询起始记录位置
			int begin = pager.getStartIndex();
			// 查询终止记录位置
			int end = Math.min(pager.getEndIndex(), scoreDocs.length);
			for (int i = begin; i < end; i++) {
				// System.out.println(scoreDocs[i].doc);
				Document doc = searcher.doc(scoreDocs[i].doc);
				pager.getDataList().add(doc2WebSearch(highlight, doc));
			}
			reader = null;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return pager;
	}

	public Pager searchOneWord(Pager pager, WebSearch webSearch) {
		String fields[] = { webSearch.KEY_TITLE, webSearch.KEY_CONTENT };
		MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(
				Version.LUCENE_30, fields, new StandardAnalyzer(
						Version.LUCENE_30));
		try {
			Query query = multiFieldQueryParser.parse(webSearch.getKeywords());
			IndexSearcher searcher = new IndexSearcher(getIndexReader());
			Sort sort = new Sort(new SortField(WebSearch.KEY_DATE,
					SortField.LONG, true));
			pager.setTotalCount(searcher.search(query, null, 100 * 100).totalHits);
			TopDocs topDocs = searcher.search(query, null, pager.getEndIndex(),
					sort);

			//
			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
					"<span style=\"color: red;\" >", "</span>");
			/** 创建QueryScorer */
			QueryScorer scorer = new QueryScorer(query);
			/** 创建Fragmenter */
			Highlighter highlight = new Highlighter(formatter, scorer);
			//
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			// 查询起始记录位置

			int begin = pager.getStartIndex();
			// 查询终止记录位置
			int end = Math.min(pager.getEndIndex(), scoreDocs.length);
			for (int i = begin; i < end; i++) {
				// System.out.println(scoreDocs[i].doc);
				Document doc = searcher.doc(scoreDocs[i].doc);
				pager.getDataList().add(doc2WebSearch(highlight, doc));
			}
			reader = null;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return pager;
	}

	private  WebSearch doc2WebSearch(Highlighter highlight, Document doc) {

		WebSearch webSearch = new WebSearch();
		try {
			webSearch.setCode(doc.get(WebSearch.KEY_CODE));
			String title = doc.get(WebSearch.KEY_TITLE);
			title=title.replaceAll(keywords,"<span style=\"color: red;\" >"+keywords+"</span>");
			webSearch.setTitle(title);
			webSearch.setImageUrl(doc.get(WebSearch.KEY_IMAGEURL));
			webSearch.setType(doc.get(WebSearch.KEY_TYPE));
			webSearch.setUrl(doc.get(WebSearch.KEY_URL));
			//
			String value = doc.get(WebSearch.KEY_CONTENT);
			TokenStream tokenStream = new IKAnalyzer().tokenStream(
					WebSearch.KEY_CONTENT, new StringReader(value));
			Fragmenter fragmenter = new SimpleFragmenter(150);
			highlight.setTextFragmenter(fragmenter);
			String str1 = highlight.getBestFragment(tokenStream, value);
			if (StringUtils.isBlank(str1)) {
				str1 = value;
				str1 = str1.substring(0, Math.min(str1.length(), 150));
				if (str1.length() == 150) {
					str1 += "...";
				}
			} else {
				str1 += "...";
			}
			webSearch.setRcon(str1);
			webSearch.setDate(Long.valueOf(doc.get(WebSearch.KEY_DATE)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webSearch;
	}
}
