package com.example.utilsdemo.UrbanComputing.spatial.mainapp;


import com.example.utilsdemo.UrbanComputing.spatial.query.GridQuery;
import com.example.utilsdemo.UrbanComputing.spatial.query.KDTreeQuery;
import com.example.utilsdemo.UrbanComputing.test.IndexRead;

import java.io.IOException;
import java.util.Scanner;

//启动类
public class MainApp {

	public static void main(String[] args) 
	{
	  
      System.out.println("1.Grid  2.KD-Tree 3.文件索引建立时间对比");
	  Scanner scanner=new Scanner(System.in);
	  int flag=scanner.nextInt();
	  while(true)
	  {
    	  switch (flag)
    	 {
    	 case 1: GridQuery gridQuery=new GridQuery();
                try
            	{
            		gridQuery.StartQuery();
            	} catch (IOException e)
            	{
            	
            		e.printStackTrace();
            	}
    		break;
    	case 2:
			KDTreeQuery kdTreeQuery=new KDTreeQuery();
    	       kdTreeQuery.StartQuery();
    	case 3:
			IndexRead indexRead=new IndexRead();
    	       indexRead.StartIndex();
    	default:
    		break;
    	}
	  }
	 
	}
	 
}
