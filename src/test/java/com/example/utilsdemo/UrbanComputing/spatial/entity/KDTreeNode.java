package com.example.utilsdemo.UrbanComputing.spatial.entity;

import java.util.ArrayList;

public class KDTreeNode
{
	 //分割的维度
    public int partitionDimention;
    //分割的值
    public double partitionValue;
    //如果为非叶子节点，该属性为空
    //否则为数据
    public ArrayList<double[]> data;
    //是否为叶子
    public boolean isLeaf=false;
    //左子树
    public KDTreeNode left;
    //右子树
    public KDTreeNode right;
    //每个维度的最小值
    public double[] min;
    //每个维度的最大值
    public double[] max;
	
    
}
