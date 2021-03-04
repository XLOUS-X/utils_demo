package com.example.utilsdemo.kd_tree;

public class Point {

    public final double x;
    public final double y;

    // Point类是 immutable datatype
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // 为了减少计算量，一般使用平方和来表示距离
    public double distanceSquareTo(Point that) {
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        return dx * dx + dy * dy;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null) return false;
        if (that.getClass() != this.getClass()) return false;
        Point point = (Point) that;
        return (x == point.x) && (y == point.y);
    }
}
