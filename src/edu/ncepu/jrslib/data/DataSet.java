package edu.ncepu.jrslib.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;



public class DataSet {
	
	 //�����û��ļ���
	 private HashMap<Integer, User> userMap;
	 //������Ʒ�ļ���
	 private HashMap<Integer, Item> itemMap;
	//���ּ��� [userid->itemid->rate]
	 private HashMap<Integer, HashMap<Integer, Rate>> rateMap;
	 //����itemid���ٲ��Ҷ�Ӧ userid  [itemid->userid]
	 private HashMap<Integer, HashSet<Integer>>bakRateSet;
	 
	 private double totalrating;
	 
	 public DataSet()
	 {
		 userMap=new HashMap<>();
		 itemMap=new HashMap<>();
		 rateMap=new HashMap<>();
		 bakRateSet=new HashMap<>();
		 
	 }
	 
	//��������userid
	public Set<Integer> getUserIds() {
			return userMap.keySet();
	}
	
	//�����û�����
	public int getUserCount() {
		return userMap.size();
	}
	
	//����û���һ�������rate ��Ŀ֮ǰ 
	public boolean addUser(User user){
		if(user == null)return false;
		int userId = user.getUserId();
		if(!userMap.containsKey(userId)){
			userMap.put(userId, user);
			return true;
		}
		return false;
	}
	//�����Ʒ��һ�������rate ��Ŀ֮ǰ 
	public boolean addItem(Item item){
		if(item == null)return false;
		int itemId = item.getItemId();
		if(!itemMap.containsKey(itemId)){
			itemMap.put(itemId, item);
			return true;
		}
		return false;
	}
	
	//����������ֵľ�ֵ
	public double getTotalAvg()
	{
		return this.totalrating/this.getRateCount();
	}
	
	
	//����userId���û� 
	public User getUser(int userId) {
		return userMap.get(userId);
	}
	
	//��������������
	public int getRateCount() {
		int sum = 0;
		for(int userId : rateMap.keySet()){
			sum += rateMap.get(userId).size();
		}
		return sum;
	}
	
	//������Ʒ����
	public int getItemCount() {
		return itemMap.size();
	}
	//����������Ʒid
	public Set<Integer> getItemIds() {
		return itemMap.keySet();
	}

	//����itemId����Ʒ
	public Item getItem(int itemId) {
		return itemMap.get(itemId);
	}
	
	
	//���һ����¼
	public boolean addRateItem(Rate rate)
	{
		if(rate == null)return false;
		int userId = rate.getUserId();
		int itemId = rate.getItemId();
		if(!rateMap.containsKey(userId))
			rateMap.put(userId, new HashMap<Integer, Rate>());
		rateMap.get(userId).put(itemId, rate);
		
		//��ӵ�bakRateSet��
		if(!bakRateSet.containsKey(itemId))
			bakRateSet.put(itemId, new HashSet<Integer>());
		bakRateSet.get(itemId).add(userId);

		totalrating+=rate.getRating();
		return true;
		
	}
	
	@Deprecated
	public double getAllavg()
	{
		
		double avgrate;
		double totalrate_sum=0;
		long   totalrate_count=0;
		Iterator iter = rateMap.entrySet().iterator();
		 while (iter.hasNext()) {
			 
			 Map.Entry entry = (Map.Entry) iter.next();
			  
			 HashMap<Integer, Rate> rate = (HashMap<Integer, Rate>)entry.getValue();
			 Iterator iterRate = rate.entrySet().iterator();
			 
			 while(iterRate.hasNext())
			 {
				 
				 Map.Entry entryRate = (Map.Entry) iterRate.next();
				 
				  Rate  rateValue = ( Rate)entryRate.getValue();
				  totalrate_sum+=rateValue.getRating();
				  totalrate_count++;
			 }
			 
			 
		 }
		
		 avgrate=totalrate_sum/totalrate_count;
		 return avgrate;
	}
	
	
	//ͳ��ÿ���û������ٵ�Ӱ������,ÿʮ��Ϊһ��
	 
	public void histEachuserRate()
	{
		HashMap<Integer,Integer> user_itemCountMap=new HashMap<>();
		
		Iterator iter = rateMap.entrySet().iterator();
		 while (iter.hasNext()) {
			 
			 Map.Entry entry = (Map.Entry) iter.next();
			 HashMap<Integer, Rate> rate = (HashMap<Integer, Rate>)entry.getValue();
			
			int  col= rate.entrySet().size()/10;
			 
			
			if(user_itemCountMap.containsKey(col))
			{
				user_itemCountMap.put(col, user_itemCountMap.get(col)+1);
			}
			else
			{
				user_itemCountMap.put(col,1);
			}
		 
		 }
		 
		 
		 //������ӡuser_itemCountMap
		 Iterator iterhist = user_itemCountMap.entrySet().iterator();
		while(iterhist.hasNext())
		{
			 Map.Entry entry = (Map.Entry) iterhist.next();
			 int col=(Integer)entry.getKey();
			 int personCount=(Integer)entry.getValue();
			 
			 System.out.println(col+":"+personCount);
			
		}
		
		
	}
	public void histEachItemRate()
	{
		HashMap<Integer,Integer> user_itemCountMap=new HashMap<>();
		
		Iterator iter = bakRateSet.entrySet().iterator();
		 while (iter.hasNext()) {
			 
			 Map.Entry entry = (Map.Entry) iter.next();
			 HashSet<Integer> rate = (HashSet<Integer>)entry.getValue();
			
			int  col= rate.size()/10;
			 
			
			if(user_itemCountMap.containsKey(col))
			{
				user_itemCountMap.put(col, user_itemCountMap.get(col)+1);
			}
			else
			{
				user_itemCountMap.put(col,1);
			}
		 
		 }
		 
		 
		 //������ӡuser_itemCountMap
		 Iterator iterhist = user_itemCountMap.entrySet().iterator();
		while(iterhist.hasNext())
		{
			 Map.Entry entry = (Map.Entry) iterhist.next();
			 int col=(Integer)entry.getKey();
			 int personCount=(Integer)entry.getValue();
			 
			 System.out.println(col+":"+personCount);
			
		}
		
		
	}
	
	
	//���ĳ���û���ĳ����Ʒ������
	public Rate getRate(int userid,int itemid)
	{
		return rateMap.get(userid).get(itemid);
		
	}
	
	
	//���ĳ���û�������
	public Set<Rate> getAllRatesByUser(int userId) {
		HashSet<Rate> rates = new HashSet<>();
		if(rateMap.containsKey(userId)){
			rates.addAll(rateMap.get(userId).values());
		}
		return rates;
	}

	 
	//���ĳ����Ʒ����������
	public Set<Rate> getAllRatesbyItem(int itemId) {
		
		HashSet<Rate> rates = new HashSet<>();
		
		HashSet<Integer> userSet= bakRateSet.get(itemId);
		
		for(int userId : userSet){
			rates.add(rateMap.get(userId).get(itemId));
			
		}
		return rates;
	}
	//���ĳ����Ʒ����������û�
	public Set<Integer> getAllRelateUsersbyItem(int itemId) {
		
		HashSet<Integer> userSet= bakRateSet.get(itemId);
		 
		return userSet;
	}
	//���ĳ����Ʒ����������û�
	public Set<Integer> getAllRelateItemsbyUser(int userId) {
		
		 Set<Integer> itemSet= rateMap.get(userId).keySet();
     		return itemSet;
	}
	
	
	
	 //���ĳ���û����ֵľ�ֵ
	public double avgRateByUser(int userid)
	{
		Set<Rate> urates= getAllRatesByUser(userid);
		double sumrate=0;
		for(Rate urate:urates)
		{
			sumrate+=urate.getRating();
		}
		return sumrate/urates.size();
	}
	 //���ĳ����Ʒ���ֵľ�ֵ
	public double avgRateByItem(int itemid)
	{
		Set<Rate> irates= getAllRatesbyItem(itemid);
		double sumrate=0;
		for(Rate irate:irates)
		{
			sumrate+=irate.getRating();
		}
		return sumrate/irates.size();
	}
	
	
	

}
