package com.example.utilsdemo.UrbanComputing.spatial.entity;

public class GridQueryEntity {
     private double MaxLatitude=0.0;//最大维度
     private double MinLatitude=180.0;//最小维度
     private double MaxLongitude=0.0;//最大经度
     private double MinLongitude=180.0;//最小经度
     private double x_wide=0;//经度的格子间隔
     private double y_wide=0;//纬度的格子间隔
     private double StartLatitude=0;//起始的经度
     private double StartLongitude=0;//起始的纬度
     private int row=0;
     private int coulmn=0;
	public double getMaxLatitude() {
		return MaxLatitude;
	}
	public int getRow()
	{
		return row;
	}
	public void setRow(int row)
	{
		this.row = row;
	}
	public int getCoulmn()
	{
		return coulmn;
	}
	public void setCoulmn(int coulmn)
	{
		this.coulmn = coulmn;
	}
	public double getX_wide()
	{
		return x_wide;
	}
	public void setX_wide(double x_wide)
	{
		this.x_wide = x_wide;
	}
	public double getY_wide()
	{
		return y_wide;
	}
	public void setY_wide(double y_wide)
	{
		this.y_wide = y_wide;
	}
	public void setMaxLatitude(double maxLatitude) {
		MaxLatitude = maxLatitude;
	}
	public double getMinLatitude() {
		return MinLatitude;
	}
	public void setMinLatitude(double minLatitude) {
		MinLatitude = minLatitude;
	}
	public double getMaxLongitude() {
		return MaxLongitude;
	}
	public void setMaxLongitude(double maxLongitude) {
		MaxLongitude = maxLongitude;
	}
	public double getMinLongitude() {
		return MinLongitude;
	}
	public void setMinLongitude(double minLongitude) {
		MinLongitude = minLongitude;
	}
	public double getStartLatitude()
	{
		return StartLatitude;
	}
	public void setStartLatitude(double startLatitude)
	{
		StartLatitude = startLatitude;
	}
	public double getStartLongitude()
	{
		return StartLongitude;
	}
	public void setStartLongitude(double startLongitude)
	{
		StartLongitude = startLongitude;
	}
	@Override
	public String toString() {
		return "GridQueryEntity [MaxLatitude=" + MaxLatitude + ", MinLatitude="
				+ MinLatitude + ", MaxLongitude=" + MaxLongitude
				+ ", MinLongitude=" + MinLongitude + "]";
	}

}
