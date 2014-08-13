package edu.ncepu.jrslib.test;

import org.junit.Test;

import edu.ncepu.jrslib.data.DataSet;
import edu.ncepu.jrslib.io.MovieLensReader;
import edu.ncepu.jrslib.io.RateReader;
import edu.ncepu.jrslib.predictor.MF;

public class PredictorTest {
	 @Test
	 public void TestMF()
	 {
		    RateReader mr=new MovieLensReader();
			DataSet dsTrain=null;
			DataSet dsTest=null;
	
			try {
				dsTrain= mr.readRating("data//ml-100k//u1.base");
				dsTest=mr.readRating("data//ml-100k//u1.test");
			} catch (Exception e) {
				e.printStackTrace();
			}
			MF mf=new   MF(dsTrain);
			mf.LearnProcess(); 
			mf.TestProcess(dsTest);
	 }
}
