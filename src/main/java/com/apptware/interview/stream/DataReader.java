package com.apptware.interview.stream;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;
@Component
public interface DataReader {

  Stream<String> fetchLimitadData(int limit);

  Stream<String> fetchFullData();
}
