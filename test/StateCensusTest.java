package test;

import org.junit.Assert;
import org.junit.Test;
import census.MyException;
import census.CensusAnalyser;
import census.CsvStateCode;

public class StateCensusTest {

	/**
	 *  This test case pass when a file with 8 rows is read.
	 */
	@Test
	public void csvFile_with_8rows_returns8() {
		try {
			CensusAnalyser analyser = new CensusAnalyser(
					"/Users/farazshabbir/eclipse-workspace/census/src/Data/CensusData.csv");
			Assert.assertEquals(8, analyser.readStateRecord(CsvStateCode.class));
		} catch (MyException e) {
			System.out.println(e);
		}
	}

	@Test
	public void csvFile_with_incorrect_data_raises_Exception() {
		try {
			CensusAnalyser analyser = new CensusAnalyser(
					"/Users/farazshabbir/eclipse-workspace/census/src/Data/IncorrectCensusData.csv");
			analyser.readStateRecord(CsvStateCode.class);
		} catch (MyException e) {
			Assert.assertEquals("File not found", e.getMessage());
			System.out.println(e);
		}

	}

	@Test
	public void csvFile_of_different_type_raises_exception() {
		try {
			CensusAnalyser analyser = new CensusAnalyser(
					"Users/farazshabbir/eclipse-workspace/Day19_20/Data/Hashing.txt");
			analyser.readStateRecord(CsvStateCode.class);
		} catch (MyException e) {
			Assert.assertEquals("Incorrect type of file", e.getMessage());
			System.out.println(e);
		}
	}

	@Test
	public void csvFile_with_invalid_delimeter_raises_exception() {
		try {
			CensusAnalyser analyser = new CensusAnalyser(
					"/Users/farazshabbir/eclipse-workspace/census/src/Data/InvalidDelimitter.csv");
			analyser.readStateRecord(CsvStateCode.class);
		} catch (MyException e) {

			System.out.println(e);
			Assert.assertEquals("Incorrect Delimeter in csv file", e.getMessage());
		}
	}

	@Test
	public void csvFile_with_wrongHeader_raises_Exception() {
		try {
			CensusAnalyser analyser = new CensusAnalyser(
					"Users/farazshabbir/eclipse-workspace/census/src/Data/IncorrectHeader.csv");
			analyser.readStateRecord(CsvStateCode.class);
		} catch (MyException e) {

			System.out.println(e);
			Assert.assertEquals("Header doesn't match", e.getMessage());
		}
	}

}
