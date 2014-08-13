package edu.ncepu.jrslib.predictor;

import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import edu.ncepu.jrslib.data.DataSet;
import edu.ncepu.jrslib.data.Rate;
import edu.ncepu.jrslib.io.MovieLensReader;
import edu.ncepu.jrslib.io.RateReader;

 
/**
 * base line method
 * 
 * 1 全局评分值
   2 以用户评分平均值作为预测评分（相似度计算方法）
   3 以电影评分平均值作为预测评分

 * @author shining
 *
 */
public class BaseMethod {
	
	RateReader mr=new MovieLensReader();
	DataSet dsTrain=null;
	DataSet dsTest=null;

	public void loadData()
	{
		
		try {
			//train data
			dsTrain= mr.readRating("data//ml-100k//u1.base");
			dsTest=mr.readRating("data//ml-100k//u1.test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//全局评分值
	@Test
	public void globalAvgMethod()
	{
		
		//计算训练集的全局均值，然后用在测试集上
	 
		
		
		double avg=dsTrain.getTotalAvg();
		
		double rmse=0;
		double errsum=0;
		long rateCount=0;
		//获取所有user
		 Set<Integer> uids= dsTest.getUserIds();
		 
		 for(Integer uid:uids)
		 {
			Set<Rate> urates= dsTest.getAllRatesByUser(uid);
			
			for(Rate irate:urates)
			{
				errsum+=Math.pow(irate.getRating()-avg,2);
				rateCount++;
			}
			
		 }
		
		    rmse=Math.sqrt(errsum/rateCount);
		 
		   System.out.println("global avg RMSE:"+rmse);
		 
		 
	}
	
	//用用户平均分做预测值
	@Test
	public void userAvgmethod()
	{
		
	 
		 
		HashMap<Integer,Double> userAvgrate=new HashMap<>(); 
		
		//获取所有user,然后根据userid获取该user所有评分，计算平均
		 Set<Integer> uids= dsTrain.getUserIds();
		 for(Integer uid:uids)
		 {
			Set<Rate> urates= dsTrain.getAllRatesByUser(uid);
			double userrate_sum=0;
			for(Rate irate:urates)
			{
				userrate_sum+=irate.getRating();
			}
			
			userAvgrate.put(uid, userrate_sum/urates.size());
			
		 }
		 
		 double rmse=0;
		 double errsum=0;
		 int rateCount=0;
		 Set<Integer> testUids= dsTest.getUserIds();
		 for(Integer uid:testUids)
		 {
			Set<Rate> urates= dsTest.getAllRatesByUser(uid);
			
			for(Rate irate:urates)
			{
				errsum+=Math.pow(userAvgrate.get(uid)-irate.getRating(), 2);
				rateCount++;
			}
			
			 
			
		 }
		
		    rmse=Math.sqrt(errsum/rateCount);
		   
		   System.out.println("user avg RMSE:"+rmse);
		 
	}
	   
	  //用物品平均分做预测值
		@Test
		public void itemAvgmethod()
		{
			
		 
			HashMap<Integer,Double>itemAvgrate=new HashMap<>(); 
			
			//获取所有item,然后根据itemid获取该item所有评分，计算平均
			 Set<Integer> iids= dsTrain.getItemIds();
			 for(Integer iid:iids)
			 {
				Set<Rate> itemrates= dsTrain.getAllRatesbyItem(iid);
				double itemrate_sum=0;
				for(Rate irate:itemrates)
				{
					itemrate_sum+=irate.getRating();
				}
				
				itemAvgrate.put(iid, itemrate_sum/itemrates.size());
				
			 }
			 
			 double rmse=0;
			 double errsum=0;
			 int rateCount=0;
			 Set<Integer> testUids= dsTest.getUserIds();
			 for(Integer uid:testUids)
			 {
				Set<Rate> urates= dsTest.getAllRatesByUser(uid);
				
				for(Rate irate:urates)
				{
					if(itemAvgrate.get(irate.getItemId())==null)
					{
						System.out.println(irate.getItemId());
						errsum+=Math.pow(3.5-irate.getRating(), 2);
					}
					else
					{
					errsum+=Math.pow(itemAvgrate.get(irate.getItemId())-irate.getRating(), 2);
					}
					rateCount++;
				}
				
				 
				
			 }
			
			    rmse=Math.sqrt(errsum/rateCount);
			  
			   System.out.println("item avg RMSE:"+rmse);
			 
		}

}
