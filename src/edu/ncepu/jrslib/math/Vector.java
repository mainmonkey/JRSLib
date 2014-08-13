package edu.ncepu.jrslib.math;

public class Vector {

	private double[] data;
	
	public double[] getData() {
		return data;
	}

	public Vector(double[]data)
	{
		
		this.data=new double[data.length];
		for(int i=0;i<data.length;i++)
		{
			this.data[i]=data[i];
		}
		
	}
	
	public double getValue(int index)
	{
		return data[index];
	}
	
	public void  setValue(int index,double value)
	{
		this.data[index]=value;
		
	}
	

}
