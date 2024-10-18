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

    private static final int PAGE_SIZE = 10000; 

    @Override
    public Stream<String> fetchLimitadData(int limit) {
        return fetchPaginatedDataAsStream().limit(limit);
    }

    @Override
    public Stream<String> fetchFullData() {
        return fetchPaginatedDataAsStream();
    }

    // fetches paginated data as stream using PaginationService
    private @Nonnull Stream<String> fetchPaginatedDataAsStream() {
        log.info("Fetching paginated data as a stream.");

        // Start from page 0 and continue until all data is fetched
        Stream<String> dataStream = Stream.iterate(0, page -> page + 1) 
            .takeWhile(page -> page * PAGE_SIZE < PaginationService.FULL_DATA_SIZE)
            .flatMap(page -> {
                // Fetch paginated data for the current page
                log.info("Fetching data for page {}", page + 1);
                List<String> pageData = paginationService.getPaginatedData(page + 1, PAGE_SIZE);
                // Convert the list of data into a stream
                return pageData.stream();
            });

        // Log each fetched item and return the stream
        return dataStream.peek(item -> log.info("Fetched Item: {}", item));
    }

}
