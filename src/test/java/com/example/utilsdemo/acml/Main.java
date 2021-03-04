package com.example.utilsdemo.acml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;


class KDTree{
    KPoint p[];
    int K,n;
    PriorityQueue<RNode> pq;
    Node root,cur;
    PrintWriter out;
    public KDTree(int K,int n,PrintWriter out) {
    	this.out=out;
        this.n=n;
        p=new KPoint[n];
        for (int i=0;i<n;i++){
            p[i]=new KPoint(K);
        }
        this.K=K;
        pq=new PriorityQueue<RNode>(10,new Bijiao());
    }
    class KPoint{
        int x[];
        int lx[],mx[];
        int k;
        public KPoint(int M) {
            this.x=new int[M];
            this.lx=new int[M];
            this.mx=new int[M];
            Arrays.fill(lx, -1<<30);
            Arrays.fill(mx, 1<<30);
            k=M;
        }
        double dis(KPoint t) {
            double ans = 0;
            for(int i=0; i<k; i++)
            ans += (x[i] - t.x[i])*(x[i] - t.x[i]);
            return Math.sqrt(ans);
        }
    }
    class Node{
        KPoint p;
        Node left,right;
        int num;
        boolean flag;
        Node(KPoint p){
            this.p=p;
            this.num=1;
            this.flag=false;
        }
    }
    class RNode{
        Node v;
        double r;
        public RNode(Node v,double r) {
            this.v=v;
            this.r=r;
        }
    }
    class Bijiao implements Comparator<RNode>{
        @Override
        public int compare(RNode o1, RNode o2) {
            if (o1.r>o2.r) {
                return -1;
            }
            return 1;
        }
        
    }
    Node buildTree(int f,int t,int depth){
        if (t-f==0) return null;
        else if (t-f==1) return new Node(p[f]);
        int mid = (f + t - 1) >> 1;
        QuickSort(depth, f, t - 1);
        Node v = new Node(p[mid]);
        v.left = buildTree(f, mid, (depth + 1) % K);
        v.right = buildTree(mid + 1, t, (depth + 1) % K);
        return v;
    }
    void QuickSort(int ii, int first, int end) {
        int i = first, j = end;
        KPoint tmp = p[first];
        while (i < j) {
            while (i < j && p[j].x[ii] >= tmp.x[ii])
                j--;
            p[i] = p[j];
            while (i < j && p[i].x[ii] <= tmp.x[ii])
                i++;
            p[j] = p[i];
        }
        p[i] = tmp;
        if (first < i - 1)
            QuickSort(ii, first, i - 1);
        if (end > i + 1)
            QuickSort(ii, i + 1, end);
    }
    int buildSqr(Node v, int depth) {
        if (v == null)
            return 0;
        if (v.left!=null) {
            for (int  i=0;i<K;i++) {
                v.left.p.mx[i] = v.p.mx[i];
                v.left.p.lx[i] = v.p.lx[i];
            }
            v.left.p.mx[depth] = v.p.x[depth];
        }
        if (v.right!=null) {

            for (int  i=0;i<K;i++) {
                v.right.p.mx[i] = v.p.mx[i];
                v.right.p.lx[i] = v.p.lx[i];
            }
            v.right.p.lx[depth] = v.p.x[depth];
        }
        int na = buildSqr(v.left, (depth + 1) % K);
        int nb = buildSqr(v.right, (depth + 1) % K);
        v.num = na + nb + 1;
        return na + nb + 1;
    }
    double searchKR(KPoint t, int k) {
        Node v = SearchKNode(root, t, k, 0);
        if (v == null)
            return -1.0;
        SearchMinr(v, t);
        while (pq.size() > k) {
            pq.poll();
        }
        if (pq.size() == 0)
            return 0.0;
        else
            return pq.peek().r;
    }
    Node SearchKNode(Node v, KPoint t, int k, int dp) {
        if (v == null)
            return null;
        if (v.num < k)
            return null;
        if ((v.left == null || v.left.num < k) && (v.right == null || v.right.num < k)) {
            v.flag = true;
            cur = v;
            return v;
        }
        if (t.x[dp] < v.p.x[dp] && v.left!=null && v.left.num >= k) {
            return SearchKNode(v.left, t, k, (dp + 1) % K);
        } else if (t.x[dp] >= v.p.x[dp] && v.right!=null && v.right.num >= k) {
            return SearchKNode(v.right, t, k, (dp + 1) % K);
        } else {
            v.flag = true;
            cur = v;
            return v;
        }
    }
    void SearchMinr(Node v, KPoint t) {
        if (v == null)
            return;
        RNode tmp=new RNode(v, v.p.dis(t));
        pq.add(tmp);
        SearchMinr(v.left, t);
        SearchMinr(v.right, t);
    }
    double r,eps=1e-8;
    void KNN(int k) {
        while (!pq.isEmpty())
            pq.poll();
        r = searchKR(aim, k);
        KFind(k, aim, root);
        cur.flag = false;
        out.println("the closest "+k +" points are:");
        Stack<Node> sta=new Stack<Node>();
        while (!sta.empty())
            sta.pop();
        while (!pq.isEmpty())
            sta.push(pq.poll().v);
        while (!sta.empty()){
            Node v=sta.pop();
            for (int i=0;i<K-1;i++){
                out.print(v.p.x[i]+" ");
            }
            out.println(v.p.x[K-1]);
        }

    }
    void KFind(int k, KPoint t,  Node v) {
        if (v.flag)
            return;
        double dd = v.p.dis(t);
        if (dd + eps < r) {
            RNode tmp=new RNode(v, dd);
            tmp.r = dd;
            tmp.v = v;
            pq.add(tmp);
            pq.poll();
            r = pq.peek().r;
        }
        if (v.left!=null) {
            if (check(v.left, t, r)) {
                KFind(k, t,  v.left);
            }
        }
        if (v.right!=null) {
            if (check(v.right, t, r)) {
                KFind(k, t,  v.right);
            }
        }
    }
    boolean check(Node v, KPoint t, double r) {
        KPoint now=new KPoint(K);
        for (int i=0;i<K;i++) {
            if (v.p.lx[i] <= t.x[i] && t.x[i] <= v.p.mx[i]) {
                now.x[i] = t.x[i];
            } else if (t.x[i] > v.p.mx[i]) {
                now.x[i] = v.p.mx[i];
            } else
                now.x[i] = v.p.lx[i];
        }
        now.k = K;
        if (now.dis(t) < eps + r)
            return true;
        return false;
    }
    public void work() {
         root=buildTree(0, n, 0);
         buildSqr(root,0);
         aim=new KPoint(K);
    }
    KPoint aim;
    
    
}
class LeftThreadTree{
	class Node{
		
	}
}
class Main{
    public static void main(String arg0[]) throws IOException{
        BufferedReader f = new BufferedReader(new FileReader("test.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("test.out")));
        Scanner in=new Scanner(f);
       // Scanner skp=new Scanner(System.in);
        while (in.hasNext()){
            int n=in.nextInt(),k=in.nextInt();
            KDTree kdTree=new KDTree(k,n,out);
            for (int i=0;i<n;i++){
                for (int j=0;j<k;j++){
                    kdTree.p[i].x[j]=in.nextInt();
                }
            }
            kdTree.work();
            int cin=in.nextInt();
            for (int ci=0;ci<cin;ci++){
                for (int i=0;i<k;i++){
                    kdTree.aim.x[i]=in.nextInt();
                }
                kdTree.KNN(in.nextInt());
               // skp.nextLine();
            }
        }
        out.close();
        
        /*
        15 2
        1 3
        2 7
        3 5
        4 2
        5 3
        5 7
        6 6
        7 3
        8 7
        11 7
        10 4
        9 2
        11 3
        8 1
        10 1
        1
        1 3
        10
         */
    }
}
