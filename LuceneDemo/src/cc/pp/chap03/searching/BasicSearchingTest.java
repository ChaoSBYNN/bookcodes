package cc.pp.chap03.searching;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class BasicSearchingTest extends TestCase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * TermQuery测试
	 * @throws IOException
	 */
	public void testTerm() throws IOException {

		//		Directory dir = TestUtil.getBookIndexDirectory(); // 需要测试
		Directory dir = FSDirectory.open(new File("index/chap03index/"));
		IndexSearcher searcher = new IndexSearcher(dir);

		Term t = new Term("subject", "ant");
		Query query = new TermQuery(t);
		TopDocs docs = searcher.search(query, 10);
		assertEquals("Ant in Action", 1, docs.totalHits);

		t = new Term("subject", "junit");
		docs = searcher.search(new TermQuery(t), 10);
		assertEquals("Ant in Action, JUnit in Action, Second Edition", 2, docs.totalHits);

		searcher.close();
		dir.close();
	}

	/**
	 * 测试关键词isbn
	 * @throws IOException
	 */
	public void testKeyword() throws IOException {

		Directory dir = FSDirectory.open(new File("index/chap03index/"));
		IndexSearcher searcher = new IndexSearcher(dir);

		Term t = new Term("isbn", "9781935182023");
		Query query = new TermQuery(t);
		TopDocs docs = searcher.search(query, 10);
		assertEquals("JUnit in Action, Second Edition", 1, docs.totalHits);

		searcher.close();
		dir.close();
	}

	/**
	 * 测试QueryParser类
	 * @throws ParseException
	 * @throws IOException
	 */
	public void testQueryParser() throws ParseException, IOException {

		Directory dir = FSDirectory.open(new File("index/chap03index/"));
		IndexSearcher searcher = new IndexSearcher(dir);

		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new SimpleAnalyzer());
		Query query = parser.parse("+JUNIT +ANT -MOCK");
		TopDocs docs = searcher.search(query, 10);
		assertEquals(1, docs.totalHits);
		Document doc = searcher.doc(docs.scoreDocs[0].doc);
		assertEquals("Ant in Action", doc.get("title"));

		query = parser.parse("mock OR junit");
		docs = searcher.search(query, 10);
		assertEquals("Ant in Action, JUnit in Action, Second Edition", 2, docs.totalHits);

		searcher.close();
		dir.close();
	}

}
