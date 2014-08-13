package edu.ncepu.jrslib.math;

 
 



public class Matrix {
	
	private double[][]data;
	
	private int rowNum;
	private int colNum;
	
	public Matrix(int rowNum,int colNum)
	{
		this.rowNum=rowNum;
		this.colNum=colNum;
		this.data=new double[rowNum][colNum];
	
	}
	
	
	public Matrix(double[][] data)
	{
		this.rowNum= data.length;;
		this.colNum=data[0].length;;
		
		this.data=new double[this.rowNum][this.colNum];
		for(int i=0;i<this.rowNum;i++)
		{
			for(int j=0;j<this.colNum;j++)
			{
				this.data[i][j]=data[i][j];
			}
			
		}
		
	}
	
	public double getValue(int row,int col)
	{
		 return this.data[row][col];
		
	}
	
	
	public void setValue(int row,int col ,double value)
	{
		
		this.data[row][col]=value;
		
	}


	public SparseVector getRowVector(int row) {
		// TODO Auto-generated method stub
		
		SparseVector vec = new SparseVector(this.colNum);
		for(int i = 0;i<this.colNum;i++)
			vec.setValue(i, getValue(row, i));
		return vec;
	 
	}
	public SparseVector getColumnVector(int column) {
		SparseVector vec = new SparseVector(rowNum);
		for(int i = 0;i<rowNum;i++)vec.setValue(i, getValue(i, column));
		return vec;
	}
}
