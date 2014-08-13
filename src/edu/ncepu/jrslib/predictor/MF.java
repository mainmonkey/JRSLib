package edu.ncepu.jrslib.predictor;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.ncepu.jrslib.data.DataSet;
import edu.ncepu.jrslib.data.Rate;
import edu.ncepu.jrslib.math.MathUtil;
import edu.ncepu.jrslib.math.Matrix;
 


public class MF  {
	
	
	private DataSet dataSet;
	private List<Integer> uidList;
	private List<Integer> iidList;
	
	private HashMap<Integer, Integer>userIdMap;
	private HashMap<Integer, Integer>itemIdMap;
	
	private Matrix P;//latent user feature matrix, D*U
	private Matrix Q;//latent item feature matrix, D*I	
	private int numU;//number of users
	private int numV;//number of items
	private int numK = 15;//number of latent features
	private int steps=50;
	private double alpha=0.003;//learning rate
	 private double beta=0.1;//regularization strength
	 private int trainCount;
	
	public MF(DataSet dataSet)
	{
		this.dataSet = dataSet;
		numU = dataSet.getUserCount();
		numV = dataSet.getItemCount();
		trainCount=dataSet.getRateCount();
		//uidlist 的index ->real user id
		
		uidList = new ArrayList<Integer>(dataSet.getUserIds());
		iidList = new ArrayList<Integer>(dataSet.getItemIds());
		
		userIdMap=new HashMap<>();
		itemIdMap=new HashMap<>();
		for(int i=0;i<uidList.size();i++)
		{
			userIdMap.put(uidList.get(i),i);
		}
		
		for(int i=0;i<iidList.size();i++)
		{
			itemIdMap.put(iidList.get(i),i);
		}
		
	}

	private void InitParam() {
		P = new Matrix(numU,numK);
		Q = new Matrix(numV,numK);
		for(int i = 0;i<numU;i++){
			for(int j = 0;j<numK;j++){
				P.setValue(i, j, Math.random() * Math.sqrt(( 1.0) / (double)this.numK));
			}
		}
		for(int i = 0;i<numV;i++){
			for(int j = 0;j<numK;j++){
				Q.setValue(i, j, Math.random()*Math.sqrt(( 1.0) / (double)this.numK));
			}
		}
	}
	
	public void LearnProcess()
	{
		InitParam();
		 double lastlost=0;
		 double lost=0;
		for(int step=0;step<steps;step++)
		{
			 updateStep(step);
			 lost= costLoss(P, Q);
			 System.out.println("step :"+step+"  loss="+lost);
			
			
		}
		
		this.dataSet=null;
		
		
	}
	
	private void updateStep(int itstep)
	{
		 
		//update param
		for(int i = 0;i<numU;i++){
			int userId = uidList.get(i);
			//获得该用户的所有评分过的物品
			Set<Rate>rateSet= dataSet.getAllRatesByUser(userId);
			for(Rate irate:rateSet){
				//查找该itemid 对应矩阵的行号
				
				//int j=iidList.indexOf(irate.getItemId());
				int j= itemIdMap.get(irate.getItemId()); 
				double rating = MathUtil.dotMult(P.getRowVector(i), Q.getRowVector(j));
				double diff = irate.getRating() - rating;
				
				for(int k = 0;k<numK;k++){
					P.setValue(i,k,P.getValue(i,k)+ alpha*(2*diff*Q.getValue(j,k) -beta*P.getValue(i,k))) ;
					Q.setValue(j,k,Q.getValue(j,k)+ alpha*(2*diff*P.getValue(i,k)-beta*Q.getValue(j,k))) ;
				}
			}
		}
		
		
		
		
		
		
		
		
		
	}
 
	//计算R矩阵与真实评分的误差
	private double costLoss(Matrix P, Matrix Q)
	{
		double loss=0;
		for(int i = 0;i<numU;i++){
			int userId = uidList.get(i);
			
			//获得该用户的所有评分过的物品
			Set<Rate>rateSet= dataSet.getAllRatesByUser(userId);
			for(Rate irate:rateSet){
				
				//查找该itemid 对应矩阵的行号
				int j=itemIdMap.get(irate.getItemId());
				//int j=iidList.indexOf(irate.getItemId());
				double rating = MathUtil.dotMult(P.getRowVector(i), Q.getRowVector(j));
				loss += (irate.getRating()-rating)*(irate.getRating()-rating);
				//L2Norm
				for(int k=0;k<numK;k++)
				{
					loss+=(beta/2)*(P.getValue(i, k)*P.getValue(i, k)+Q.getValue(j, k)*Q.getValue(j, k));
				}
				
			}
		}
		 loss=Math.sqrt(loss/this.trainCount);
		 
		 
		
		return loss;
	}
   public void saveModel() throws Exception
   {
 		//save uf
	    File fileUf = new File("model//uf.txt");
		BufferedWriter bw= new BufferedWriter(new FileWriter(fileUf));;
		bw.write(numU+"\t"+numK+"\n");
		for(int i = 0;i<numU;i++){
			bw.write(uidList.get(i)+"\t");
			for(int j = 0;j<numK;j++){
				bw.write(P.getValue(i,j)+"\t");
			}
			bw.write("\n");
		}
		bw.flush();
		bw.close();
		//save vf
		File fileVf = new File("model//vf.txt");
		BufferedWriter bwv= new BufferedWriter(new FileWriter(fileVf));
		bwv.write(numV+"\t"+numK+"\n");
		for(int i = 0;i<numV;i++){
			bwv.write(iidList.get(i)+"\t");
			for(int j = 0;j<numK;j++){
				bwv.write(Q.getValue(i,j)+"\t");
			}
			bwv.write("\n");
		}
		bwv.flush();
		bwv.close();
   }
	
	 
	public double predictRate(int userId, int itemId) {
		// TODO Auto-generated method stub
		return MathUtil.dotMult(this.P.getRowVector(userId),this.Q.getRowVector(itemId));
		 
	}
	
	 public double TestProcess(DataSet testSet)
	 {
		 
		 
		// System.out.println(testSet.getUserCount()+","+testSet.getItemCount());
		 double error=0;
	      double rmse=0;
		 int totalCount=testSet.getRateCount();
		 
		 int testUCount= testSet.getUserCount();
		  ArrayList<Integer>tuserlist=  new ArrayList<Integer>(testSet.getUserIds());
		// ArrayList<Integer>titemlist=  new ArrayList<Integer>(testSet.getItemIds());
		 for(int i=0;i<testUCount;i++)
		 {
				int userId = tuserlist.get(i);
				//int urowid=uidList.indexOf(userId);
				int urowid=userIdMap.get(userId);
				
				//id转换问题？？？？
				
				//获得该用户的要预测的物品
				Set<Rate>rateSet= testSet.getAllRatesByUser(userId);
				
				
				
				for(Rate irate:rateSet){
					
					//查找该itemid 对应矩阵的行号
					 
					 
					int	irowid;// iidList.indexOf(irate.getItemId());
					try {
						irowid = itemIdMap.get(irate.getItemId());
					} catch (Exception e) {
						// TODO: handle exception
						irowid=-1;
					}
					double rating=0;
				   if(irowid==-1)
				   {
					   rating=3;
				   }
				   else
					rating = MathUtil.dotMult(P.getRowVector(urowid), Q.getRowVector(irowid));
					
				   if(rating>5)
						rating=5;
					else if(rating<0)
						rating=0;
					error+=Math.pow((rating-irate.getRating()),2);
				}
			 
		 }
		 rmse=Math.sqrt(error/totalCount);
		 System.out.println("RMSE "+rmse);
		 return rmse;
		
		
	 }
	

}
