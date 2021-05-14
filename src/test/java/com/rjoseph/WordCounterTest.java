package com.rjoseph;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.TreeMap;


/**
 * Unit test for simple App.
 */
public class WordCounterTest {
	protected WordCounter wordCountinstance;
	private final static String VALID_HELLO_FILE_PATH = "src/test/resources/hello_world.txt";
	private final static String INVALID_FILE_PATH = "__INVALID_FILE_PATH__";

	protected final static String HELLO_TEXT = "Hello world & good morning. The date is 18/05/2016";

	@BeforeEach
	public void initEach() {
		//Create a word count API instance, under test
		wordCountinstance = new WordCounter();

		//Is the Hello World file available.
		File file = new File(VALID_HELLO_FILE_PATH);
		assumeTrue(file.exists());
	}

	@Test
	public void testCreateReportFromFile() {
		String wordCountReport = null;
		try {
			wordCountReport = wordCountinstance.createReportFromFile(VALID_HELLO_FILE_PATH);
		} catch(Exception ex) {

		}

		assertNotNull(wordCountReport);
		assertTrue(wordCountReport.length() > 50);

		assertTrue(wordCountReport.contains("Word Count = 9"));

		assertTrue(wordCountReport.contains("Number of words of length 4 is 2"));
	}

	@Test
	public void testCreateReportFromText() {
		String wordCountReport = null;
		try {
			wordCountReport = wordCountinstance.createReportFromText(HELLO_TEXT);
		} catch(Exception ex) {

		}

		assertNotNull(wordCountReport);
		assertTrue(wordCountReport.length() > 50);

		assertTrue(wordCountReport.contains("Word Count = 9"));

		assertTrue(wordCountReport.contains("Number of words of length 10 is 1"));
	}

	@Test
	public void testConstructReport() {
		String myText = "1 two three 123 12/06/07";
		String[] myWords = wordCountinstance.splitWords(myText);

		assertNotNull(myWords);
		assertTrue(myWords.length == 5);

		TreeMap<Integer, List<String>> testFreqMap = wordCountinstance.buildWordLengthFrequencyMap(myWords);

		//There should be 4 word frequency List entries
		assertTrue(testFreqMap.entrySet().size() == 4);

		//The word length frequency of 3 List should have 2 entries ('two' and '123')
		List<String> words = testFreqMap.get(3);
		assertNotNull(words);
		assertTrue(words.size() == 2);
	}

	@Test
	public void testAverageWordLength() {
		String threeChars = "12A";
		String fourChars = "123B";
		assertNotNull(wordCountinstance);

		float averageFor3and3 = wordCountinstance.calculateAverageWordLength(new String[]{threeChars, threeChars});
		assertTrue(averageFor3and3 == 3f);

		float averageFor3and4 = wordCountinstance.calculateAverageWordLength(new String[]{threeChars, fourChars});
		assertTrue(averageFor3and4 == 3.5f);
	}

}
