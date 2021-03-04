package com.example.utilsdemo.UrbanComputing.spatial.query;

import com.example.utilsdemo.UrbanComputing.spatial.entity.KDTreeNode;
import com.example.utilsdemo.UrbanComputing.spatial.utilz.UtilZ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class KDTreeQuery
{
	 public static String fileString="POI_jiaotong.txt";
     KDTreeNode kdTreeNode;
     public static int Dimention=2;
     static int flag=-1;
    //计算中心函数
	public void StartQuery()
	{
		// TODO 自动生成的方法存根
		KDTreeQuery tree=KDTreeQuery.KDTreebuild();
	}
	//读取文件确定有多少行
	public static int FileLineNum()
	{
		int index=0;
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(new File(fileString)));
			String string=null;
			while((string=br.readLine())!=null)
			{
				index++;
			}
			return index;
		} catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return index;
	}
	//建立KDTree树的方法
	public static KDTreeQuery KDTreebuild()
	{
		int num=FileLineNum()-1;
		while(true)
		{
			System.out.print("1.KNN查找  2.范围查找 3.半径查找\n请输入功能编号:");
			Scanner scanner=new Scanner(System.in);
			int key=scanner.nextInt();
		switch (key)
		{
		case 1: ArrayList<double[]> data =new ArrayList<double[]>(num);
		        double []input=new double[2];
			    System.out.print("请输入CATEGORY:");
			    String cateGory=scanner.next();
			    System.out.print("请输入经度(E):");
			    input[0]=scanner.nextDouble();
			    System.out.print("请输入纬度(N):");
			    input[1]=scanner.nextDouble();
				//同一维度元素数量
				// TODO 自动生成的方法存根
				BuildTree(data, cateGory);
				KDTreeQuery tree = new KDTreeQuery();
				tree.kdTreeNode=new KDTreeNode();
			    tree.buildDetail(tree.kdTreeNode, data,Dimention);
			    double[] position=KNNquery(tree.kdTreeNode,input);
			    //根据经纬度查询地点
			    String nameString=getPosition(position);
			    System.out.println("地点为："+nameString+"   经纬度："+Arrays.toString(position));		   
			break;
		case 2:ArrayList<double[]> data1 =new ArrayList<double[]>(num);
		       double []LaLong=new double[4];
		       System.out.print("请输入CATEGORY:");
		       String cateGory1=scanner.next();
			   System.out.print("请输入左下角经度:");
			   LaLong[0]=scanner.nextDouble();
	           System.out.print("请输入左下角纬度:");
	           LaLong[1]=scanner.nextDouble();
	           System.out.print("请输入右上角经度:");
	           LaLong[2]=scanner.nextDouble();
	           System.out.print("请输入右上角纬度:");
	           LaLong[3]=scanner.nextDouble();
	           //根据分组编号构建KDtree
			   BuildTree(data1, cateGory1);
			   KDTreeQuery tree1 = new KDTreeQuery();
			   tree1.kdTreeNode=new KDTreeNode();
			   tree1.buildDetail(tree1.kdTreeNode, data1,Dimention);
			   ArrayList<double[]> positionData=RangeQuery(tree1.kdTreeNode,LaLong);
			   if(positionData.size()==0)
			   {
				   System.out.println("该范围内没有数据");
			   }
			   else 
			   {
				   for (double[] ds : positionData)
				{
					   String name=getPosition(ds);
					   System.out.println(name+"   经纬度："+Arrays.toString(ds));		   
				}
			   }
			break;
		case 3:ArrayList<double[]> data2 =new ArrayList<double[]>(num);
			   System.out.println("请输入中心ID:");
		       String ID=scanner.next();
		       System.out.print("请输入CATEGORY:");
		       String cateGory2=scanner.next();
		       System.out.println("请输入半径:");
		       int radium=scanner.nextInt();
		       try
			{
				   double []IDLaLong=GetLaLong(ID);
				   double minMaxLaLong[]= UtilZ.GetAround(IDLaLong[0],IDLaLong[1], radium);
				   BuildTree(data2, cateGory2);
				   KDTreeQuery tree2 = new KDTreeQuery();
				   tree2.kdTreeNode=new KDTreeNode();
				   tree2.buildDetail(tree2.kdTreeNode, data2,Dimention);
				   ArrayList<double[]> positionData1=RangeQuery(tree2.kdTreeNode,minMaxLaLong);
				   if(positionData1.size()==0)
				   {
					   System.out.println("该范围内没有数据");
				   }
				   else 
				   {
					   for (double[] ds : positionData1)
					{    
						   if(UtilZ.Geodist(ds[0], ds[1], IDLaLong[0], IDLaLong[1])<radium)
						   {
						   String name=getPosition(ds);
						   System.out.println(name+"   经纬度："+Arrays.toString(ds));	
						   }	   
					}
				   }
			} catch (Exception e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		       
			break;
		default:
			break;
		}
		}
	}
	//根据ID得到经纬度
	public static double[] GetLaLong(String ID) throws Exception
	{
		double LaLong[] =new double[2];
		BufferedReader br = new BufferedReader(new FileReader(new File(fileString)));//构造一个BufferedReader类来读取文件
        String str = null;
        String []strArray=null;
        while((str = br.readLine())!=null)
        {
        	strArray=str.split(",");
        	if(strArray[0].equals(ID))
        	{
        	    LaLong[0]=Double.valueOf(strArray[14]);
        	    LaLong[1]=Double.valueOf(strArray[15]);
        	} 
        }
		return LaLong;
	}
	//根据左下角和右上角经纬度，从树中查询 LaLong包含两个角的经纬度
	private static ArrayList<double[]> RangeQuery(KDTreeNode node, double[] LaLong)
	{
		// TODO 自动生成的方法存根
	    KDTreeNode node1=node;
		while(!node.isLeaf){
            if(LaLong[node.partitionDimention]<node.partitionValue){
                node=node.left;
            }else{    
                node=node.right;
            }
        }
		while(!node1.isLeaf){
            if(LaLong[2+node1.partitionDimention]<node1.partitionValue){
            	node1=node1.left;
            }else{    
            	node1=node1.right;
            }
        }
		ArrayList<double[]> dataArrayList=new ArrayList<double[]>(node.data.size()+node1.data.size());
		for (double[] ds : node.data)
		{
			if(ds[0]<LaLong[2]&&ds[0]>LaLong[0]&&ds[1]<LaLong[3]&&ds[1]>LaLong[1])
			{
				dataArrayList.add(ds);
			}
		}
		for (double[] ds : node1.data)
		{
			if(ds[0]<LaLong[2]&&ds[0]>LaLong[0]&&ds[1]<LaLong[3]&&ds[1]>LaLong[1])
			{
				dataArrayList.add(ds);
			}
		}
		return dataArrayList;
	}
	//根据输入编号创建KDTREE
	private static void BuildTree(ArrayList<double[]> data,String cateGory)
	{
		int index=0;
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(new File(fileString)));
			int x_index=0,y_index=0,id_index =0;//属性名称中经纬度代表的序号	
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
	            	 }
            	}
            	else
            	{
            		double[] d1 = new double[2];
            		double Latitude,Longitude;
            		strArray=null;//清空数组
            		strArray=str.split(",");
            		if(strArray[id_index].equals(cateGory))
            		{
            		Latitude=Double.parseDouble(strArray[x_index]);//经度
            		Longitude=Double.parseDouble(strArray[y_index]);//维度
            		//Latitude=Double.parseDouble(a);//经度
            		//Longitude=Double.parseDouble(b);//维度
            		//d1[0]=input[index-2][0];
            		//d1[1]=input[index-2][1];
            		//System.out.println(Arrays.toString(input[index-2]));
            		d1[0]=Latitude;
            		d1[1]=Longitude;
					data.add(d1);
            		}
            	}
            } 
		} catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//根据经纬度从文件中读取出对应地点的名称
	private static String getPosition(double[] position)
	{
		// TODO 自动生成的方法存根
		
        try
		{
        	BufferedReader br=new BufferedReader(new FileReader(new File(fileString)));
    		int x_index=0,y_index=0,index=0,name_index=0;//属性名称中经纬度代表的序号	
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
            		Latitude=Double.parseDouble(strArray[x_index]);//经度
            		Longitude=Double.parseDouble(strArray[y_index]);//维度
            		//Latitude=Double.parseDouble(a);//经度
            		//Longitude=Double.parseDouble(b);//维度
            		//d1[0]=input[index-2][0];
            		//d1[1]=input[index-2][1];
            		//System.out.println(Arrays.toString(input[index-2]));
            		if(Latitude==position[0]&&Longitude==position[1])
            		{
            			return strArray[name_index];
            		}
            		}
			}
			br.close();
		} catch (IOException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	private void buildDetail(KDTreeNode kdTreeNode, ArrayList<double[]> data,
			int dimentions)
	{
		
		//System.out.println(data.size());
		//如果是一维数据，就视为叶子节点
		if(data.size()<=3){
			//System.out.println("长度----"+data.size());
			kdTreeNode.isLeaf=true;
			kdTreeNode.data=new ArrayList<double[]>(data.size());
			for(int i=0;i<data.size();i++)
			{
			kdTreeNode.data.add(data.get(i));
			}
            return;
        }
		/*
		//遍历m个维度，找到方差最大的维度,方差大说明数据点分散比较大
		kdTreeNode.partitionDimention=-1;
		double var = -1;//方差
        double tmpvar;  //临时方差
        for(int i=0;i<dimentions;i++){
            tmpvar=UtilZ.variance(data,i);
            if (tmpvar>var){
                var = tmpvar;
                kdTreeNode.partitionDimention = i;
            }
        }*/
		
		flag*=-1;
		if(flag==1)
		{
			kdTreeNode.partitionDimention=0;
		}
		else if(flag==-1)
		{
			kdTreeNode.partitionDimention=1;
		}
   
        // System.out.println("--"+kdTreeNode.partitionDimention);
        kdTreeNode.partitionValue=FindPos(data,kdTreeNode.partitionDimention);
     //   System.out.println("划分值---"+kdTreeNode.partitionValue);
        double[][] maxmin=maxmin(data, dimentions);
        kdTreeNode.min = maxmin[0];
        kdTreeNode.max = maxmin[1];
        int size = (int)(data.size()*0.55);
        ArrayList<double[]> left = new ArrayList<double[]>(size);
        ArrayList<double[]> right = new ArrayList<double[]>(size);
        for(double[] d:data){
            if (d[kdTreeNode.partitionDimention]<kdTreeNode.partitionValue) {
                left.add(d);
            }else {
                right.add(d);
            }
        }
        /*
        System.out.print("左        ");
        for (double[] ds : left)
		{
			System.out.print(ds[kdTreeNode.partitionDimention]+" ");
		}
        System.out.println("  ");
        System.out.print("右        ");
        for (double[] ds : right)
		{
			System.out.print(ds[kdTreeNode.partitionDimention]+" ");
		}
        System.out.println("  ");*/
        KDTreeNode leftnode = new KDTreeNode();
        KDTreeNode rightnode = new KDTreeNode();
        kdTreeNode.left=leftnode;
        kdTreeNode.right=rightnode;
        buildDetail(leftnode, left, dimentions);
        buildDetail(rightnode, right, dimentions);
       // System.out.println(kdTreeNode.partitionValue);
	}
	
	//从数组中提取出最大最小的数值
	public static double[][] maxmin(ArrayList<double[]> data,int dimentions){
        double[][] mm = new double[2][dimentions];
        //初始化 第一行为min，第二行为max
        for(int i=0;i<dimentions;i++){
            mm[0][i]=mm[1][i]=data.get(0)[i];
            for(int j=1;j<data.size();j++){
                double[] d = data.get(j);
                if(d[i]<mm[0][i]){
                    mm[0][i]=d[i];
                }else if(d[i]>mm[1][i]){
                    mm[1][i]=d[i];
                }
            }
        }
        return mm;
    }

	public static double[] KNNquery(KDTreeNode node,double[] input){
        Stack<KDTreeNode> stack = new Stack<KDTreeNode>();
        double distance=Double.MAX_VALUE;
        double [] minPosition=new double[2];
        while(!node.isLeaf){
            if(input[node.partitionDimention]<node.partitionValue){
                stack.push(node.right);
                node=node.left;
            }else{
                stack.push(node.left);
                node=node.right;
            }
        }
        
      //  在树中找到一个合适的距离，然后再寻找其他节点
        for(double []ds:node.data)
        {
        	double temDistance=UtilZ.Geodist(input[0],input[1],ds[0], ds[1]);
        	
        	if(temDistance<=distance)
        	{
        		distance=temDistance;
        		minPosition[0]=ds[0];
        		minPosition[1]=ds[1];
        	}
        }
      //寻找其他节点中有没有更近的点
       double[] nearestDistance=queryNode(input, distance, stack);
       return nearestDistance==null? minPosition:nearestDistance;
    }
	
	
	//在栈中查找其余的结点，判断有没有更短的路径
	@SuppressWarnings("null")
	private static double []queryNode(double[] input,double distance,Stack<KDTreeNode> stack)
	{
		double[] nearest =null;
		KDTreeNode node = null;
        double temDistance;
        while(stack.size()!=0){
            node = stack.pop();
            //如果是叶子节点，就直接比较距离
            if(node.isLeaf){
                 for(double []ds:node.data)
                 {
                	//System.out.print(Arrays.toString(ds));
                 	temDistance=UtilZ.Geodist(input[0],input[1],ds[0], ds[1]);
                 	if(temDistance<=distance)
                 	{
                 		distance=temDistance;
                 		nearest[0]=ds[0];
                 		nearest[1]=ds[1];
                 	}
                 }
            }else { 
                double mindistance = UtilZ.minP_RDistance(input, node.max, node.min);
                if (mindistance<distance) 
                {
                    while(!node.isLeaf)
                    {
                        if(input[node.partitionDimention]<node.partitionValue)
                        {
                            stack.push(node.right);
                            node=node.left;
                        }else
                        {
                            stack.push(node.left);
                            node=node.right;
                        }
                    }
                    for(double []ds:node.data)
                    {
                    	temDistance=UtilZ.Geodist(input[0],input[1],ds[0], ds[1]);
                    	if(temDistance<=distance)
                    	{
                    		distance=temDistance;
                    		nearest[0]=ds[0];
                    		nearest[1]=ds[1];
                    	}
                    }
                }
            }
        }
        return nearest;
	}
	
	//排序以后取出中间的数值返回
	private double FindPos(ArrayList<double[]> data, int dimention)
	{
		double[] d =new double[data.size()];
        int i=0;
        for(double[] k:data){
            d[i++]=k[dimention];
        }
        UtilZ.QuickSort(d, 0, d.length-1);
        double pos=d[d.length/2];
		return pos;
	}
	

}
