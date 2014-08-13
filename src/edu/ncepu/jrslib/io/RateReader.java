package edu.ncepu.jrslib.io;

import edu.ncepu.jrslib.data.DataSet;

public interface RateReader {
  public DataSet readRating(String path) throws Exception;
}
