package com.apptware.interview.stream.impl;

import com.apptware.interview.stream.DataReader;
import com.apptware.interview.stream.PaginationService;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class DataReaderImpl implements DataReader {

	@Autowired
	private PaginationService paginationService;
	private static int FULL_DATA_SIZE = 10000;

	@Override
	public Stream<String> fetchLimitadData(int limit) {
		return fetchPaginatedDataAsStream().limit(limit);
	}

	@Override
	public Stream<String> fetchFullData() {
		return fetchPaginatedDataAsStream();
	}
	  //This method fetches paginated data as a stream.
	private @Nonnull Stream<String> fetchPaginatedDataAsStream() {
		log.info("Fetching paginated data as stream.");

		// Create a stream of data items based on FULL_DATA_SIZE
		Stream<String> dataStream = Stream.iterate(1, n -> n + 1).limit(FULL_DATA_SIZE).map(n -> "Item" + n); 
		// Replace with actual data fetching logic

		return dataStream.peek(item -> log.info("Fetched Item: {}", item));
	}
}
