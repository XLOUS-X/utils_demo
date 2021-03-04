package com.example.utilsdemo.UrbanComputing.spatial.utilz;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilZ
{
	 /**
     * 读取指定文件指定行号的内容
     * @param sourceFile 文件
     * @param lineNumber 行号
     * @return 字符串内容
     */
	 public String readAppointedLineNumber(File sourceFile, int lineNumber)  
		            throws IOException {  
		        FileReader in = new FileReader(sourceFile);  
		        LineNumberReader reader = new LineNumberReader(in);  
		        String s = "";  
		        int lines = 0;  
		        while (s != null) {  
		            lines++;  
		            s = reader.readLine();  
		            if((lines - lineNumber) == 0) {  
		               // System.out.println(s); 
		                return s;  
		            }  
		        }  
		        reader.close();  
		        in.close();  
		        return s;
		    } 
		/**
	     * 字符串模糊匹配
	     * @param adressName 匹配的字符串  比如ATM
	     * @param text 查询的内容 比如中国银行ATM机
	     * @return 字符串内容
	     */
		private static boolean match(String adressName,String text){
	        Pattern pattern = Pattern.compile("("+adressName+")");
	         Matcher matcher = pattern.matcher(text);
	        if(matcher.find()){
	           // System.out.println("匹配到了："+matcher.group(1));
	            return true;
	        }
	       // System.out.println("没有匹配到");
	        return false;
	    }
		/**
	     * 根据两点经纬度获得两点距离
	     * @param lat 经度
	     * @param lon 纬度
	     * @return 两点距离
	     */
		public static double Geodist(double lat1, double lon1, double lat2, double lon2)
        {
            double radLat1 = Rad(lat1);
            double radLat2 = Rad(lat2);
            double delta_lon = Rad(lon2 - lon1);
            double top_1 = Math.cos(radLat2) * Math.sin(delta_lon);
            double top_2 = Math.cos(radLat1) * Math.sin(radLat2) - Math.sin(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
            double top = Math.sqrt(top_1 * top_1 + top_2 * top_2);
            double bottom = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(delta_lon);
            double delta_sigma = Math.atan2(top, bottom);
            double distance = delta_sigma * 6378137.0;
            return distance;
        }

        public static double Rad(double d)
        {
            return d * Math.PI / 180.0;
        }
        /**
	     * 根据某点经纬度和半径，获取圆周内最大最小经纬度
	     * @param lat 经度
	     * @param lon 纬度
	     * @param 半径
	     * @return 数组minLat, minLng, maxLat, maxLng
	     */
        public static double[] GetAround(double lat, double lon, int raidus)
        {
            Double latitude = lat;
            Double longitude = lon;
            Double degree = (24901 * 1609) / 360.0;
            double raidusMile = raidus;
            Double dpmLat = 1 / degree;
            Double radiusLat = dpmLat * raidusMile;
            Double minLat = latitude - radiusLat;
            Double maxLat = latitude + radiusLat;
            Double mpdLng = degree * Math.cos(latitude * (3.14159265 / 180));
            Double dpmLng = 1 / mpdLng;
            Double radiusLng = dpmLng * raidusMile;
            Double maxLng = longitude - radiusLng;
            Double minLng = longitude + radiusLng;
            return new double[] { minLat, minLng, maxLat, maxLng };
        }
        /**
	     * 求一个一维数组的方差
	     * @param data 数据
	     * 
	     * @return 方差
	     */
        public static double variance(ArrayList<double[]> data,int dimention){
            double vsum = 0;
            double sum = 0;
            for(double[] d:data){
                sum+=d[dimention];
                vsum+=d[dimention]*d[dimention];
            }
            int n = data.size();
            return vsum/n-Math.pow(sum/n, 2);
        }
       
        /**
	     * 递归实现快速排序算法
	     * @param data 数据
	     * @param low 低位置
	     * @param high 高位置
	     * @return 方差
	     */
        public static void QuickSort(double[] data,int low,int high)
    	{
    		// TODO 自动生成的方法存根
    		if(low<high)
    		{
    			int middle=GetMiddle(data,low,high);
    			QuickSort(data,low,middle-1);
    			QuickSort(data,middle+1,high);
    		}
    	}
        //将数组拆分
    	public static int GetMiddle(double[] data, int low, int high)
    	{
    		// TODO 自动生成的方法存根
    		//将数组位置最小的元素赋值给中轴
    		double temp=data[low];
    		while(low<high)
    		{
    			 while(low < high && data[high] >= temp)
    		        {
    		            high--;
    		        }
    		        data[low] = data[high];//比中轴小的记录移到低位置
    		        while(low < high && data[low] <= temp)
    		        {
    		            low++;
    		        }
    		        data[high] = data[low] ; //比中轴大的记录移到高位置
    	   }
    		     data[low] = temp ; //将中轴元素放入中轴
    		   return low;
    	}
    	
    	/**
	     * 求矩形外一点到矩形最小的距离
	     * @param input 输入的点的数据
	     * @param max 横纵坐标的最大值
	     * @param min 横纵坐标的最小值
	     * @return 最小距离
	     */
		public static double minP_RDistance(double []input,double []max,double min[])
		{
			double point_x=input[0];
			double point_y=input[1];
			double max_x=max[0];
			double max_y=max[1];
			double min_x=min[0];
            double min_y=min[1];
            double mindistance=0;
            //如果在矩形的左右两边
            if(point_y>min_y&&point_y<max_y)
            {
            	if(point_x>max_x)
            	{
            		mindistance=Geodist(point_x,point_y, max_x, point_y);
            	}
            	else if(point_x<min_x)
            	{
            		mindistance=Geodist(point_x,point_y, min_x, point_y);
            	}
            }
            else if(point_x>min_x&&point_x<max_x)
            {
            	if(point_y>max_y)
            	{
            		mindistance=Geodist(point_x,point_y, point_x, max_y);
            	}
            	else if(point_y<min_y)
            	{
            		mindistance=Geodist(point_x,point_y, point_x, min_y);
            	}
            }//在四个角上
            else
            {  //左上
            	if(point_x<min_x&&point_y>max_y)
            	{
            		mindistance=Geodist(point_x,point_y,min_x,max_y);
            	}//左下
            	else if(point_x<min_x&&point_y<min_y)
            	{
            		mindistance=Geodist(point_x,point_y,min_x,min_y);
            	}//右上
            	else if(point_x>max_x&&point_y>max_y)
            	{
            		mindistance=Geodist(point_x,point_y,max_x,max_y);
            	}//右下
            	else if(point_x>max_x&&point_y<min_y)
            	{
            		mindistance=Geodist(point_x,point_y,max_x,min_y);
            	}
            }
			return mindistance;
		}
}
