package edu.ncepu.jrslib.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import edu.ncepu.jrslib.data.DataSet;
import edu.ncepu.jrslib.data.Item;
import edu.ncepu.jrslib.data.Rate;
import edu.ncepu.jrslib.data.User;

 

public class MovieLensReader implements RateReader {

	@Override
	public DataSet readRating(String path) throws Exception {
		// TODO Auto-generated method stub
        DataSet ds=new DataSet();
		File fuser=new File(path); 
		//∂¡»°user–≈œ¢
		BufferedReader bfr=new BufferedReader(new InputStreamReader(new FileInputStream(fuser)));
		String line=null;
		while((line=bfr.readLine())!=null)
		{
			String []tokens=line.split("\t");
			int userid=Integer.parseInt(tokens[0]);
			int itemid=Integer.parseInt(tokens[1]);
			double rating=Double.parseDouble(tokens[2]);
			User user=new User(userid);
			Item item=new Item(itemid);
			
			Rate rate=new Rate(userid,itemid,rating);
			ds.addUser(user);
			ds.addItem(item);
			ds.addRateItem(rate);
			
		}
         System.out.println("read ok");
		return ds;
	}
	
	

}
