package com.rjoseph;

import com.google.common.annotations.VisibleForTesting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class WordCounter {
	private final static String WORD_REGEX = "[\\^\\s,.!?:;]+";

	public String createReportFromFile(String filePathString) throws Exception {
		String fileText = null;
		if (filePathString != null && filePathString.length() > 0) {
			Path filePath = Paths.get(filePathString);
			try {
				fileText = new String(Files.readAllBytes(filePath));
			} catch (IOException ioe) {
				//TODO - Should really log this
				String errorMSG = "Error reading file: " + filePathString;
				System.out.println(errorMSG);
				throw new Exception(errorMSG, ioe);
			}
		}

		//return reporting
		return this.createReportFromText(fileText);
	}

	public String createReportFromText(String originalText) throws Exception {
		if (originalText == null || originalText.length() <= 0) {
			return "No text to report on!";
		}

		String[] allWords = splitWords(originalText);

		TreeMap<Integer, List<String>> wordFrequencyMap = buildWordLengthFrequencyMap(allWords);

		return constructReport(allWords, wordFrequencyMap);
	}

	@VisibleForTesting
	protected String[] splitWords(String originalText) {
		return Pattern.compile(WORD_REGEX).split(originalText);
//		return originalText.split(WORD_REGEX);
	}

	@VisibleForTesting
	protected TreeMap<Integer, List<String>> buildWordLengthFrequencyMap(String[] allWords) {
		TreeMap<Integer, List<String>> wordFrequencyMap = new TreeMap<>();
		if (allWords != null && allWords.length > 0) {
			//Add words to frequency map
			for (String word : allWords) {
				int wordlength = word.length();
				//Retrieve list of words of this length OR add a new List, for this length
				if(wordlength > 0) {
					List<String> wordsOfThisLength = wordFrequencyMap.get(wordlength);
					if (wordsOfThisLength == null) {
						wordsOfThisLength = new ArrayList<>();
						wordFrequencyMap.put(wordlength, wordsOfThisLength);
					}
					//Add word to List for this length
					wordsOfThisLength.add(word);
				}
			}
		}
		return wordFrequencyMap;
	}

	/**
	 * Example report
	 * <p>
	 * Word count = 9
	 * Average word length = 4.556
	 * Number of words of length 1 is 1
	 * Number of words of length 2 is 1
	 * Number of words of length 3 is 1
	 * Number of words of length 4 is 2
	 * Number of words of length 5 is 2
	 * Number of words of length 7 is 1
	 * Number of words of length 10 is 1
	 * The most frequently occurring word length is 2, for word lengths of 4 & 5
	 *
	 * @param wordFrequencyMap
	 * @return
	 */
	@VisibleForTesting
	protected String constructReport(String[] allWords, TreeMap<Integer, List<String>> wordFrequencyMap) throws Exception {
		if ((allWords == null || allWords.length == 0) || (wordFrequencyMap == null || wordFrequencyMap.entrySet().isEmpty())) {
			//We don't have valid word content to report on.
			//TODO - Should really log this
			String errorMSG = "Unable to process word content (empty?)";
			System.out.println(errorMSG);
			throw new Exception(errorMSG);
		}

		//Let's build the report
		StringBuffer reportSB = new StringBuffer();

		reportSB.append(constructNumberOfWordsText(allWords));

		reportSB.append("Average word length = " + calculateAverageWordLength(allWords) + "\n");

		reportSB.append(constructNumberWordsForLengthText(wordFrequencyMap));

		return reportSB.toString();
	}

	@VisibleForTesting
	protected String constructNumberWordsForLengthText(TreeMap<Integer, List<String>> wordFrequencyMap) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<Integer, List<String>> entry : wordFrequencyMap.entrySet()) {
			int wordLengthKey = entry.getKey();
			List<String> wordsOfThisLength = entry.getValue();
			sb.append("Number of words of length " + wordLengthKey + " is " + wordsOfThisLength.size() + "\n");
		}
		return sb.toString();
	}

	@VisibleForTesting
	protected String constructNumberOfWordsText(String[] allWords) {
		return "Word Count = " + allWords.length + "\n";
	}

	@VisibleForTesting
	protected float calculateAverageWordLength(String[] allWords) {
		int letterTotal = 0;

		for(String word: allWords) {
			letterTotal += word.length();
		}

		return new Float(letterTotal)/allWords.length;
	}

	public static void main(String[] args) {
		String reportOut = null;
		boolean doWordCount = true;
		if(args.length == 0) {
			reportOut = "Argument error: No filename provided!";
			doWordCount = false;
		}

		if(doWordCount) {
			String fileNameArg = args[0];
			System.out.println("Creating report for file: " + fileNameArg);
			WordCounter wordCounter = new WordCounter();
			try {
				reportOut = wordCounter.createReportFromFile(fileNameArg);
			} catch (Exception ex) {
				reportOut = "Unable to create word count report:\n" + ex.getMessage();
			}
		}
		System.out.println(reportOut);
	}

}
