package edu.ncepu.jrslib.math;

 

public class MathUtil {

	public static double dotMult(Vector x,Vector y)
	{
		int lx=x.getData().length;
		int ly=y.getData().length;
		if(lx!=ly)
		{
			throw new RuntimeException(" x and y vector must be the same length");
		}
		double result=0;
		for(int i=0;i<lx;i++)
		{
			result+=x.getValue(i)*y.getValue(i);
			
		}
		return result;
		
	}
	public static SparseVector Add(SparseVector x,SparseVector y)
	{
		int lenx=x.getLength();
		int leny=y.getLength();
		if(lenx!=leny)
		{
			throw new RuntimeException(" x and y vector must be the same length");
		}
		SparseVector result=new SparseVector(lenx);
		for(int i=0;i<lenx;i++)
		{
			result.setValue(i, x.getValue(i)+y.getValue(i));
		}
		return result;
	}
	public static void AddToFirst(SparseVector x,SparseVector y)
	{
		int lenx=x.getLength();
		int leny=y.getLength();
		if(lenx!=leny)
		{
			throw new RuntimeException(" x and y vector must be the same length");
		}
		 
		for(int i=0;i<lenx;i++)
		{
			x.setValue(i, x.getValue(i)+y.getValue(i));
		}
		 
	}
	
	public static double dotMult(SparseVector x,SparseVector y)
	{
		int lx=x.getLength();
		int ly=y.getLength();
		if(lx!=ly)
		{
			throw new RuntimeException(" x and y vector must be the same length");
		}
		double result=0;
		for(int i=0;i<lx;i++)
		{
			result+=x.getValue(i)*y.getValue(i);
			
		}
		return result;
		
	}
	public static void dotMult(SparseVector x,double scale)
	{
		int lx=x.getLength();
		 
		 
		for(int i=0;i<lx;i++)
		{
			x.setValue(i, x.getValue(i)*scale);
			
		}
		 
	}
}
