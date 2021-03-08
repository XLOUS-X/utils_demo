package com.example.utilsdemo.kd_tree.demo1;// illustrates behavior of KDTree class

import edu.wlu.cs.levy.CG.KDTree;

class kddemo {

    public static void main(String[] args) {

        double[] A = {2, 5};
        double[] B = {1, 1};
        double[] C = {3, 9};
        double[] T = {1, 10};

        // make a KD-tree and add some nodes
        KDTree kd = new KDTree(2);
        try {
            kd.insert(A, new String("A"));
            kd.insert(B, new String("B"));
            kd.insert(C, new String("C"));
        } catch (Exception e) {
            System.err.println(e);
        }


        // look for node B
        try {
            String n = (String) kd.search(B);
            System.err.println(n);

        } catch (Exception e) {
            System.err.println(e);
        }

        try {

            // find T's nearest neighbor, which should be C
            String n = (String) kd.nearest(T);
            System.err.println(n);

            // remove C from the tree
            kd.delete(C);

            // now T's nearest neighbor should be A
            n = (String) kd.nearest(T);
            System.err.println(n);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
