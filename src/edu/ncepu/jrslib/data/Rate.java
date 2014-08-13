package edu.ncepu.jrslib.data;


public class Rate {

	private int userId;
	
	private int itemId;
	private double rating;
	
	public Rate(int userId, int itemId, double rating) {
		 
		this.userId = userId;
		this.itemId = itemId;
		this.rating = rating;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
 
	
	
}
