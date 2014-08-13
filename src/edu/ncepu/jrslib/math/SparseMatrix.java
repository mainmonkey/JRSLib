package edu.ncepu.jrslib.math;

import java.util.HashMap;

public class SparseMatrix {
	
	protected HashMap<Integer, HashMap<Integer, Double>> dataMap;
	
	private int rowNum;
	private int colNum;
	
	
	public SparseMatrix()
	{
		this.dataMap=new HashMap<>();
		
	}
	public SparseMatrix(int rowNum,int colNum)
	{
		this.dataMap=new HashMap<>();
		this.colNum=colNum;
		this.rowNum=rowNum;
	}
	
	
	public SparseMatrix(double [][]data)
	{
		//data[i][j]不为0才设置值
		this.colNum=data[0].length;
		this.rowNum=data.length;
		
		for(int row=0;row<this.rowNum;row++)
		{
			
			 
			for(int col=0;col<this.colNum;col++)
			{
				//设置非零值
				if(data[row][col]!=0)
				{
				   if(!dataMap.containsKey(row))dataMap.put(row, new HashMap<Integer, Double>());
				  dataMap.get(row).put(col, data[row][col]);
				}
				//设置零值
				else
				{
					
					if(dataMap.containsKey(row)){
						dataMap.get(row).remove(col);//set zero value
						if(dataMap.get(row).size() == 0)dataMap.remove(row);
					}
					
				}
				
			}
			
		}
		
	}
	
    public double getValue(int row,int col)
    {
    	
    	if(dataMap.containsKey(row) && dataMap.get(row).containsKey(col))
    	{
    		return dataMap.get(row).get(col);
    	}
    	else
    		return 0;
    }
    public void setValue(int row,int col,double value)
    {
    	//设置非零值
		if(value!=0)
		{
		   if(!dataMap.containsKey(row))dataMap.put(row, new HashMap<Integer, Double>());
		   dataMap.get(row).put(col, value);
		}
		//设置零值
		else
		{
			
			if(dataMap.containsKey(row)){
				dataMap.get(row).remove(col); 
				if(dataMap.get(row).size() == 0)dataMap.remove(row);
			}
			
		}
    }
    
	public SparseVector getRowVector(int row) {
		// TODO Auto-generated method stub
		
		SparseVector vec = new SparseVector();
	//	HashMap<Integer, Double>itemMap=  dataMap.get(row);
		
		for(int i = 0;i<this.colNum;i++)vec.setValue(i, getValue(row, i));
		return vec;
	 
	}
    
    
	  
}
