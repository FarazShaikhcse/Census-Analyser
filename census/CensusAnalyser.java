package census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CensusAnalyser {
	private String csv_file_path = "";

	public CensusAnalyser() {

	}

	public CensusAnalyser(String SAMPLE_CSV_FILE_PATH) {
		this.csv_file_path = SAMPLE_CSV_FILE_PATH;
	}

	/**
	 * @return the number of data read from file
	 * Reads the csv file and maps to CsvStateCensus class.
	 * 
	 */
	public int readStateRecord() throws MyException {
		int count = 0;
		try {
			Reader reader = Files.newBufferedReader(Paths.get(csv_file_path));
			@SuppressWarnings("unchecked")
			CsvToBean<CsvStateCensus> csvToBean = new CsvToBeanBuilder(reader).withType(CsvStateCensus.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			Iterator<CsvStateCensus> csvUserIterator = csvToBean.iterator();
			while (csvUserIterator.hasNext()) {
				CsvStateCensus state = csvUserIterator.next();
				count++;

				if (state.getState() == null || state.getPopulation() <= 0 || state.getAreaInSqKm() <= 0) {
					throw new MyException(MyException.ExceptionType.INCORRECT_HEADER, "Header doesn't match");
				}
			}
		}

		catch (NoSuchFileException e) {
			if (csv_file_path.contains(".csv")) {
				throw new MyException(MyException.ExceptionType.FILE_NOT_FOUND, "File not found");
			}
			throw new MyException(MyException.ExceptionType.INCORRECT_TYPE, "Incorrect Type of file");

		} catch (RuntimeException e) {

			throw new MyException(MyException.ExceptionType.DELEMETER_NOT_FOUND, "Incorrect Delimeter in csv file");
		} catch (IOException e) {
			System.out.println(e);
		}

		return count;
	}
}
