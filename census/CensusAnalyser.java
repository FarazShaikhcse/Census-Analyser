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

	public <T> int readStateRecord(Class<T> className) throws MyException {
		int count = 0;
		try {
			Reader reader = Files.newBufferedReader(Paths.get(csv_file_path));
			@SuppressWarnings("unchecked")
			CsvToBean<T> csvToBean = new CsvToBeanBuilder(reader).withType(className).withIgnoreLeadingWhiteSpace(true)
					.build();
			Iterator<T> csvUserIterator = csvToBean.iterator();
			while (csvUserIterator.hasNext()) {
				T state = csvUserIterator.next();
				count++;
				if (state instanceof CsvStateCensus) { // comparing if read file is of CsvStateCensus class
					if (((CsvStateCensus) state).getState() == null || ((CsvStateCensus) state).getPopulation() <= 0
							|| ((CsvStateCensus) state).getAreaInSqKm() <= 0) {

						throw new MyException(MyException.ExceptionType.INCORRECT_HEADER, "Header doesn't match");
					}
				}

				if (state instanceof CsvStateCode) { // comparing if read file is of CsvStateCode class
					if (((CsvStateCode) state).getSrNo() == 0 || ((CsvStateCode) state).getStateName() == null
							|| ((CsvStateCode) state).getStateCode() == null) {

						throw new MyException(MyException.ExceptionType.INCORRECT_HEADER, "Header doesn't match");
					}
				}

			}
		}

		catch (NoSuchFileException e) {
			if (csv_file_path.contains(".csv")) {
				throw new MyException(MyException.ExceptionType.FILE_NOT_FOUND, "File not found");
			}
			throw new MyException(MyException.ExceptionType.INCORRECT_TYPE, "Incorrect type of file");

		} catch (RuntimeException e) {

			throw new MyException(MyException.ExceptionType.DELIMITER_NOT_FOUND, "Incorrect Delimiter in csv file");
		} catch (IOException e) {
			System.out.println(e);
		}

		return count;
	}
}
