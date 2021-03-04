package com.example.utilsdemo.UrbanComputing.test;

import com.example.utilsdemo.UrbanComputing.spatial.entity.Information;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IndexRead
{
	public String fileString="POI_jiaotong.txt";
     public void StartIndex()
     {
    	 long startTime,endTime;
    	 Map<Integer, Long[]> hashmap;
    	 startTime=System.currentTimeMillis();
    	 hashmap=BuildFile();
    	 endTime=System.currentTimeMillis();
    	 System.out.println("对象序列化文件建立时间： "+(endTime-startTime)+"ms");
    	 startTime=System.currentTimeMillis();
    	 BuildFile1();
    	 endTime=System.currentTimeMillis();
    	 System.out.println("Json文件建立时间： "+(endTime-startTime)+"ms");
    	 Scanner scanner=new Scanner(System.in);
    	 while(true)
    	 {
	         System.out.println("对象序列化方式索引");
	    	 System.out.println("请输入查询的索引号:");
	    	 int index=scanner.nextInt();
	    	 startTime=System.currentTimeMillis();
	    	 ByteObject(hashmap.get(index));
	    	 endTime=System.currentTimeMillis();
	    	 System.out.println("对象序列化方式索引时间： "+(endTime-startTime)+"ms");
    	     System.out.println("Json数组方式索引");
    	     System.out.println("请输入查询的索引号:");
    	     int index1=scanner.nextInt();
    	     startTime=System.currentTimeMillis();
    	     JsonRead(index1-1);
    	     endTime=System.currentTimeMillis();
    	     System.out.println("Json数组索引时间： "+(endTime-startTime)+"ms");
    	 }
    	 
     }
     //对象序列化读取
     public void ByteObject(Long[] integers)
     {
    	 Information information=new Information();
    	 try {  
             // bytearray to object
    		 byte[] bytes=new byte[300];
    		 FileInputStream fis=new FileInputStream(new File("data.dat"));
    		 fis.skip(integers[0]);
    		 BufferedInputStream bis=new BufferedInputStream(fis);
    		 bis.read(bytes, 0, integers[1].intValue());
    		 //将byte数组转换成对象
             ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
             ObjectInputStream oi = new ObjectInputStream(bi);  
             information = (Information) oi.readObject(); 
             System.out.println(information.nameString);
             bi.close();  
             oi.close();  
         } catch (Exception e) {  
             System.out.println("translation" + e.getMessage());  
             e.printStackTrace();  
         } 
     }
     //json文件读取
     public void JsonRead(int index)
     {
    	 try
		{
    		StringBuilder sb = new StringBuilder();
			BufferedReader br=new BufferedReader(new FileReader(new File("data.json")));
			String s=null; 
			while ((s = br.readLine()) != null) { 
				 sb.append(s);
			 }
			JSONArray jsonArray=JSONArray.fromObject(sb.toString());
			System.out.println(jsonArray.getJSONObject(index).get("Name"));
	     br.close();
		} catch ( IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
     }
     public Map<Integer, Long[]> BuildFile()
     {
    	 Information information=new Information();
    	 Map<Integer, Long[]> hashmap=new HashMap<Integer,Long[]>(2020);
    	 int index=0;
    	 try
 		{
 			BufferedReader br=new BufferedReader(new FileReader(new File(fileString)));
            BufferedOutputStream bw=new BufferedOutputStream(new FileOutputStream(new File("data.dat")));    
            int x_index=0,y_index=0,id_index =0,name_index=0;//属性名称中经纬度代表的序号	
 			String str = null;
             String []strArray=null;
             //根据维度创建集合
             // 
             int flag=0;
             long  sum=0;
			while((str = br.readLine())!=null&&flag<2020)
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
 	            		 else if(strArray[i].equals("CATEGORY"))
 	            		 {
 	            			 id_index = i;
 	            		 }
 	            		 else if(strArray[i].equals("NAME"))
 	            		 {
 	            			 name_index=i;
 	            		 }
 	            	 }
             	}
             	else
             	{
             		Long[] byteInfor=new Long[2];
             		flag++;
             		//将对象转换成为byte
             		ByteArrayOutputStream baos=new ByteArrayOutputStream();
         			ObjectOutputStream oos=new ObjectOutputStream(baos);
             		byte[] bytes=new byte[300];
             		double[] d1 = new double[2];
             		double Latitude,Longitude;
             		strArray=null;//清空数组
             		strArray=str.split(",");
             		information.Latitude=Double.parseDouble(strArray[x_index]);//经度
             		information.Longitude=Double.parseDouble(strArray[y_index]);//维度
             		information.IdString=strArray[id_index];
             		information.nameString=strArray[name_index];
             		oos.writeObject(information);
             		bytes = baos.toByteArray();
             		long length=bytes.length;
             		baos.close();oos.close();
             		bw.write(bytes,0,(int)length);
             		byteInfor[0]=sum;
             		byteInfor[1]=length;
             		hashmap.put(flag,byteInfor);
             		sum=sum+length;
             		//将数据存入json对象，然后添加进jsonarray
             		
             	}
             }
			bw.close();
			
 		} catch (Exception e)
 		{
 			// TODO 自动生成的 catch 块
 			e.printStackTrace();
 		}
    	 
    	 return hashmap;
     }
     
     public void BuildFile1()
    {
    	 int index=0;
    	 JSONArray JsonArray = new JSONArray();
 		try
 		{
 			
 			BufferedReader br=new BufferedReader(new FileReader(new File(fileString)));
 			 int x_index=0,y_index=0,id_index =0,name_index=0;//属性名称中经纬度代表的序号	
 			String str = null;
             String []strArray=null;
             //根据维度创建集合
             // 
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
 	            		 else if(strArray[i].equals("CATEGORY"))
 	            		 {
 	            			 id_index = i;
 	            		 }
 	            		 else if(strArray[i].equals("NAME"))
 	            		 {
 	            			 name_index=i;
 	            		 }
 	            	 }
             	}
             	else
             	{
             		double[] d1 = new double[2];
             		double Latitude,Longitude;
             		strArray=null;//清空数组
             		strArray=str.split(",");
             		JSONObject Json = new JSONObject();
             		Json.put("Latitude",Double.parseDouble(strArray[x_index]));
             		Json.put("Longitude",Double.parseDouble(strArray[y_index]));
             		Json.put("Id", strArray[id_index]);
             		Json.put("Name", strArray[name_index]);
             		JsonArray.add(Json);	 		
             		
             	}
             }
            PrintStream ps=new PrintStream(new FileOutputStream(new File("data.json")));
 			ps.print(JsonArray);
 			ps.close();
 		} catch (Exception e)
 		{
 			// TODO 自动生成的 catch 块
 			e.printStackTrace();
 		}
    }
}
