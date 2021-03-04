package com.example.utilsdemo.UrbanComputing.spatial.query;

import com.example.utilsdemo.UrbanComputing.spatial.entity.GridQueryEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GridQuery {
	   GridQueryEntity gridQueryEntity=new GridQueryEntity();
	   public String fileString="POI_jiaotong.txt";
	   public static String file1String="La-Longitude.txt";
     //读取文件，并将索引号以及对应经纬度存入新的文件，同时得到最大最小经纬度
	    public void HandleFile(File file,File file1)
	    {
	    	
	    	int index=0;
	    	int x_index=0,y_index=0,id_index=0,name_index=0;//属性名称中经纬度代表的序号
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
	            BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
	            String str = null;
	            String []strArray=null;
	            while((str = br.readLine())!=null)
	            {//使用readLine方法，一次读一行
	            	index++;
	            	if(index==1)
	            	{
	            	 //System.out.println(str);
	            	 strArray=str.split(",");
	            	 for(int i=0;i<strArray.length;i++)
	            	 {
	            		 if(strArray[i].equals("X_COORD"))
	            		 {
	            			 x_index=i;//提取出经度的索引号，以便以后从分割数组获取数据
	            		 }
	            		 else if(strArray[i].equals("Y_COORD"))
	            		 {
	            			 y_index=i;//提取出维度的索引号，以便以后从分割数组获取数据
	            		 }
	            		 else if(strArray[i].equals("ID"))
	            		 {
	            			 id_index=i;
	            		 }
	            		 else if(strArray[i].equals("NAME"))
	            		 {
	            			 name_index=i;
	            		 }
	            	 }
	            	}
	            	else
	            	{
	            		String spellString,Latitude,Longitude,id,name;
	            		strArray=null;//清空数组
	            		strArray=str.split(",");
	            		Latitude=strArray[x_index];//经度
	            		Longitude=strArray[y_index];//维度
	            		id=strArray[id_index];
	            		name=strArray[name_index];//名称
	            		spellString=String.valueOf(index)+","+id+","+name+","+Latitude+","+Longitude;
	            		bw.write(spellString);
	            		bw.newLine();
	            		bw.flush();	
	            		//确定最大最小的索引值
	            		GetMaxMin(Latitude,Longitude,gridQueryEntity);
					}
	            }
	            br.close(); 
	            bw.close();
	            System.out.println("最大经度："+gridQueryEntity.getMaxLatitude()+
	            		"最小经度："+gridQueryEntity.getMinLatitude()+"最大维度："+gridQueryEntity.getMaxLongitude()
	            		+"最小维度："+gridQueryEntity.getMinLongitude());
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	    }
		//得到最大最小的索引值
		private void GetMaxMin(String latitude, String longitude,
					GridQueryEntity gridQueryEntity)
		{
				// TODO 自动生成的方法存根
		     double Latitude=Double.valueOf(latitude),Longitude=Double.valueOf(longitude);
		     if(Latitude>=gridQueryEntity.getMaxLatitude())
		     {
		    	 gridQueryEntity.setMaxLatitude(Latitude);
		     }
		     else if(Latitude<gridQueryEntity.getMinLatitude())
		     {
		    	 gridQueryEntity.setMinLatitude(Latitude);
		     }
		     if(Longitude>=gridQueryEntity.getMaxLongitude())
		     {
		    	 gridQueryEntity.setMaxLongitude(Longitude);
		     }
		     else if(Longitude<gridQueryEntity.getMinLongitude())
		     {
		    	 gridQueryEntity.setMinLongitude(Longitude);
		     }
		}
		//将文件中需要的数据读出来，存入新的文件
		private void HandleData(double maxLatitude, double minLatitude,
				double maxLongitude, double minLongitude, int row, int column,
				ArrayList<Integer>[][] dataArrayList)
		{
			// TODO 自动生成的方法存根
			double x_wide,y_wide;//宽度
			x_wide=(maxLatitude-minLatitude)/column;  
			gridQueryEntity.setX_wide(x_wide);
			y_wide=(maxLongitude-minLongitude)/row;
			gridQueryEntity.setY_wide(y_wide);
			try
			{
				int m=0,n=0;//数组的行列号
				String str=null;
				String []strArray=null;//存放分割数据的数组
				BufferedReader br=new BufferedReader(new FileReader(new File(file1String)));
				str = br.readLine();
				while((str = br.readLine())!=null)
				{
					strArray=str.split(",");
				 //   System.out.println(Arrays.deepToString(strArray));
					m=(int)Math.ceil((Double.valueOf(strArray[3])-minLatitude)/x_wide);
					n=(int)Math.ceil((Double.valueOf(strArray[4])-minLongitude)/y_wide);
				//	System.out.println("宽度："+x_wide+"--"+y_wide);
				 //   System.out.println("行列号："+m+"--"+n);
				//	System.out.println(Integer.valueOf(strArray[0]));
					dataArrayList[m-1][n-1].add(Integer.valueOf(strArray[0]));
				}
				br.close();
			} catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
		//读取指定行的数据
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
	
		//根据ID得到对应地点的经纬度
		public static double[] GetLaLong(String ID) throws Exception
		{
			double LaLong[] =new double[2];
			BufferedReader br = new BufferedReader(new FileReader(new File(file1String)));//构造一个BufferedReader类来读取文件
	        String str = null;
	        String []strArray=null;
	        while((str = br.readLine())!=null)
	        {
	        	strArray=str.split(",");
	        	if(strArray[1].equals(ID))
	        	{
	        	
	        	    LaLong[0]=Double.valueOf(strArray[3]);
	        	    LaLong[1]=Double.valueOf(strArray[4]);
	        	}
	        }
			return LaLong;
		}
		//最邻近算法
		public void KNNQuery(String addressName, String ID)
		{
			//构造一个BufferedReader类来读取文件
	        String str = null;
	        String []strArray=null;
	        int Index;
	        double MinDistance=Double.MAX_VALUE;
			// TODO 自动生成的方法存根
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(new File(file1String)));
				//根据ID得到经纬度
				double LaLong[]=GetLaLong(ID);
				//System.out.println(LaLong[0]+"--"+LaLong[1]);
				while((str = br.readLine())!=null)
		        {
		        	strArray=str.split(",");
		        	//System.out.println(addressName+"--"+strArray[2]);
		        	if(match(addressName,strArray[2])==true)
		        	{   
		        		if(Geodist(LaLong[0],LaLong[1],Double.valueOf(strArray[3]),Double.valueOf(strArray[4]))<=MinDistance)
		        		{
		        			MinDistance=Geodist(LaLong[0],LaLong[1],Double.valueOf(strArray[3]),Double.valueOf(strArray[4]));
		        		    Index=Integer.valueOf(strArray[0]);
		        		    System.out.println(strArray[2]);
		        		    System.out.println("距离："+MinDistance);
		        		}
		        	}
		        }
				
			} catch (Exception e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			  
			 
		}
		//字符串模糊识别
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
		//根据两点的经纬度获得两点的距离
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
         //根据经纬度确定格子的位置
        public int[] GetGridIndex(double Latitude,double Longitude)
        {
        	int m=0,n=0;
        	int gridIndex[]=new int[2];
        	double minLatitude=gridQueryEntity.getStartLatitude();
        	double minLongitude=gridQueryEntity.getStartLongitude();
        	double x_wide=gridQueryEntity.getX_wide();
        	double y_wide=gridQueryEntity.getY_wide();
        	//System.out.println(minLatitude+"   "+minLongitude);
        	m=(int)Math.ceil((Latitude-minLatitude)/x_wide);
			n=(int)Math.ceil((Longitude-minLongitude)/y_wide);
			//System.out.println(m);
	        gridIndex[0]=m-1;
	        gridIndex[1]=n-1;
	        return gridIndex;
        }
        //范围查询某一地点的数目
        public void RangeQuery(int k, File file2, String addressName,double lat1, double lon1, double lat2, double lon2, ArrayList[][] dataArrayList) throws IOException
        {
        	int minM,minN,maxM,maxN;//最小的格子位置以及最大的格子位置
        	minM=GetGridIndex(lat1,lon1)[0];
            minN=GetGridIndex(lat1,lon1)[1];
        	maxM=GetGridIndex(lat2,lon2)[0];
        	maxN=GetGridIndex(lat2,lon2)[1];
        	//System.out.println(minM+"--"+minN+"--"+maxM+"--"+maxN);
        	/*
        	 *   minM,maxN                  maxM,maxN   
        	 * 
        	 * 
        	 * 
        	 *   minM，minN                  maxM.minN                 
        	 * */
        	BufferedWriter bw=new BufferedWriter(new FileWriter(file2));
        	for(int i=minN;i<=maxN;i++)
        	{
        		for(int j=minM;j<=maxM;j++)
        		{
        			//System.out.println(i+"--"+j);
        			for(int n = 0 ; n < dataArrayList[j][i].size(); n++) 
        			{
        				
        				 int lineNum=(int) dataArrayList[j][i].get(n);
        				// System.out.println(lineNum);
        				 File file=new File(file1String);
        				 try
						{  
        					
							String str=readAppointedLineNumber(file,lineNum-1);
							String array[]=str.split(",");
							double Lat=Double.valueOf(array[3]);
							double Long=Double.valueOf(array[4]);
							if(match(addressName,array[2])==true)//输入的和矩形框索引里的地址名称匹配
							{
								System.out.println(Lat+"--"+lat1+"--"+lat2);
								System.out.println(Long+"--"+lon1+"--"+lon2);
								if((Lat<=lat2&&Lat>=lat1&&Long<=lon2&&Long>=lon1))
								{
									bw.write(str);
				            		bw.newLine();
				            		bw.flush();	
								}
							}
							
						} catch (IOException e)
						{
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
        			}
        			
        		}
        	}
        	bw.close();
        	if(k==0)
        	{
	        	String str=null;
	        	boolean flag=false;
	        	BufferedReader br=new BufferedReader(new FileReader(file2));
	        	System.out.println("查询中，请稍后");
	        	while((str = br.readLine())!=null)
	        	{
	        		flag=true;
	        		System.out.println(str);
	        	}
	        	if(str==null&&flag==false)
	        	{
	        		System.out.println("未查询到结果");
	        	}
	        	br.close();
        	}	
        }
        //按照半径查找
        private void RadiusQuery(File file3, String address, String idString, int radius,
				ArrayList[][] dataArrayList)
		{
			// TODO 自动生成的方法存根
			//根据ID得到经纬度
			try
			{
				double LaLong[]=GetLaLong(idString);
				//System.out.println(LaLong[0]+"--"+LaLong[1]);
				double minMaxLaLong[]=GetAround(LaLong[0],LaLong[1],radius);
				//System.out.println(minMaxLaLong[0]+"-"+minMaxLaLong[1]+"-"+minMaxLaLong[2]+"-"+minMaxLaLong[3]);
				if(minMaxLaLong[0]<gridQueryEntity.getMinLatitude())
				{
					minMaxLaLong[0]=gridQueryEntity.getMinLatitude();
				}
				if(minMaxLaLong[1]<gridQueryEntity.getMinLongitude())
				{
					minMaxLaLong[1]=gridQueryEntity.getMinLongitude();
				}
				if(minMaxLaLong[2]>gridQueryEntity.getMaxLatitude())
				{
					minMaxLaLong[2]=gridQueryEntity.getMaxLatitude();
				}
				if(minMaxLaLong[3]>gridQueryEntity.getMaxLongitude())
				{
					minMaxLaLong[3]=gridQueryEntity.getMaxLongitude();
				}
				//System.out.println(minMaxLaLong[0]+"-"+minMaxLaLong[1]+"-"+minMaxLaLong[2]+"-"+minMaxLaLong[3]);
				RangeQuery(1,file3,address,minMaxLaLong[0],minMaxLaLong[1],minMaxLaLong[2],minMaxLaLong[3],dataArrayList);
				//从新文件中读取
				BufferedReader br=new BufferedReader(new FileReader(file3));
				BufferedWriter bw=new BufferedWriter(new FileWriter(new File("RadiusQuery.txt")));
;				String str=null;
				String array[];
				boolean flag=false;
				while((str = br.readLine())!=null)
				{
					array=str.split(",");
					double distance=Geodist(Double.valueOf(array[3]),Double.valueOf(array[4]),LaLong[0],LaLong[1]);
				    if(distance<=radius)
				    {
				    	bw.write(str);
				    	bw.newLine();
				    	bw.flush();
				    	flag=true;
				    	System.out.println("查询到结果：");
				    	System.out.println(str);
				    }
				}
				if(flag==false)
				{
					System.out.println("该范围内没有查询到结果");
				}
				bw.close();
				br.close();
			} catch (Exception e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			file3.delete();
		}
        //根据半径和经纬度计算最大最小经纬度
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
		//计算中枢
        public void StartQuery() throws IOException
        {
        	
        	double maxLatitude,minLatitude,maxLongitude,minLongitude;
        	int row=0,column=0;
        	//1.
        	File file = new File(fileString);
        	File file1 = new File(file1String);
            HandleFile(file,file1);
        	//2.划分网格
            maxLatitude=gridQueryEntity.getMaxLatitude();
            minLatitude=gridQueryEntity.getMinLatitude()-0.0000001;
            gridQueryEntity.setStartLatitude(minLatitude);
            maxLongitude=gridQueryEntity.getMaxLongitude();
            minLongitude=gridQueryEntity.getMinLongitude()-0.00000001;
            gridQueryEntity.setStartLongitude(minLongitude);
           // System.out.println("最大经度范围："+maxLatitude+
            	//	"最小经度范围："+minLatitude+"最大维度范围："+maxLongitude
            	//	+"最小维度范围："+minLongitude);
            Scanner scanner=new Scanner(System.in);
            System.out.println("请输入需要划分格子的行列号");
            row=scanner.nextInt();
            column=scanner.nextInt();
			@SuppressWarnings("unchecked")
			ArrayList[][]dataArrayList=new ArrayList[row][column];
			for(int i=1;i<=row;i++)
			{
				for(int j=1;j<=column;j++)
					dataArrayList[i-1][j-1]=new ArrayList<>();
			}
            HandleData(maxLatitude,minLatitude,maxLongitude,minLongitude,row,column,dataArrayList);
            //3.输入中心ID，得到中心的格子号。以中心开始检索，首先本格子中，其次周围一圈
            System.out.println("1.最邻近算法     2.矩形范围查找算法  3.圆范围查找");
            System.out.print("请输入序号：");
            int flag=scanner.nextInt();
            switch (flag)
			{
			case 1:	System.out.print("请输入中心的ID:");
                    String ID=scanner.next();
                    System.out.print("请输入查询的地点名称:");
                    String addressName=scanner.next();
                    //4.最邻近算法
                    KNNQuery(addressName,ID);
				break;
            case 2: //5.矩形框查找
                	System.out.println("请输入左下角经度"+"("+gridQueryEntity.getStartLatitude()+"~"+gridQueryEntity.getMaxLatitude()+"):");
                	double bottomLeftLa=scanner.nextDouble();
                	System.out.println("请输入左下角纬度"+"("+gridQueryEntity.getStartLongitude()+"~"+gridQueryEntity.getMaxLongitude()+"):");
                	double bottomLeftLong=scanner.nextDouble();
                	System.out.println("请输入右上角经度"+"("+gridQueryEntity.getStartLatitude()+"~"+gridQueryEntity.getMaxLatitude()+"):");
                	double topRightLa=scanner.nextDouble();
                	System.out.println("请输入右上角纬度"+"("+gridQueryEntity.getStartLongitude()+"~"+gridQueryEntity.getMaxLongitude()+"):");
                	double topRightLong=scanner.nextDouble();
                	System.out.println("请输入要查找的地址名称：");
                	String addressString=scanner.next();
                	File file2=new File("RangeQuery.txt");
                	RangeQuery(0,file2,addressString,bottomLeftLa,bottomLeftLong,topRightLa,topRightLong,dataArrayList);
				break;
            case 3:
            	    System.out.println("请输入中心的ID：");
            	    String idString=scanner.next();
            	    System.out.println("请输入查找的半径：");
            	    int radius=scanner.nextInt();
            	    System.out.println("请输入查找的地点名称：");
            	    String address=scanner.next();
            	    File file3=new File("RangeQuery1.txt");
            	    RadiusQuery(file3,address,idString,radius,dataArrayList);
			default:
				break;
			}
            
           
            		
        }
		
		
		
     
}
