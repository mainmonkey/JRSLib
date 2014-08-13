package edu.ncepu.jrslib.math;

import java.util.HashMap;


public class SparseVector {
	
	private HashMap<Integer, Double> data;
	private int length;
	public SparseVector()
	{
		data=new HashMap<>();
		
	}
 
	public SparseVector(double[] data)
	{
		this.length=data.length;
		this.data=new HashMap<>();
		for(int i=0;i<data.length;i++)
		{
			if( data[i]!=0)
			{
				this.data.put(i, data[i]);
			}
			 
		
			
		}
		
	}
	
	public SparseVector(int length) {
		// TODO Auto-generated constructor stub
		data=new HashMap<>();
		this.length=length;
		
	}

	public double getValue(int index)
	{
		if(this.data.containsKey(index))
		   return this.data.get(index);
		else return 0;
		
	}
	public void setValue(int index,double value)
	{
		//index 不能大于length
		if(index<length)
		{
			if(value!=0)
			{
			  this.data.put(index, value);
			}
			else
			{
				 this.data.remove(index);
			}
		}
		else
		{
			System.out.println("indexout SparseVector");
		}
		
	}

	public int getLength() {
		return length;
	}
	
	

}
