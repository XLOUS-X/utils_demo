package com.example.utilsdemo.kd_tree;

import java.util.LinkedList;

public class KdTree {
    // 节点类，其中 rect 成员表示该节点所分割的平面，
    // 即它的左右孩子所表示的空间之和，该成员用于判断
    // 最邻近点
    private class Node {
        Point point;
        Rect rect;
        Node left;
        Node right;
        Node (Point p, Rect r) {
            point = p;
            rect = r;
            left = null;
            right = null;
        }
    }

    // 根节点
    private Node root;

    // 构造函数
    public KdTree() {
        root = null;
    }

    // 插入， 用同名私有方法递归实现， 默认根节点是纵向分割
    public void insert(Point point) {
        root = insert(point, root, false, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insert(Point point, Node node, boolean isVertical,
                        double x0, double y0, double x1, double y1) {

        if (node == null) {
            return new Node(point, new Rect(x0, y0, x1, y1));
        }

        // 改变分割方向
        isVertical = !isVertical;

        // 判断要插入的点在当前点的左/下还是右/上
        double value0 = isVertical ? point.x : point.y;
        double value1 = isVertical ? node.point.x : node.point.y;
        if (value0 < value1) {
            node.left = insert(point, node.left, isVertical,
                    x0, y0, isVertical ? node.point.x : x1, isVertical ? y1 : node.point.y);
        } else {
            node.right = insert(point, node.right, isVertical,
                    isVertical ? node.point.x : x0, isVertical ? y0 : node.point.y, x1, y1);
        }
        return node;
    }

    // 判断是否包含该点， 用同名私有方法递归实现
    public boolean contains(Point point) {
        return contains(point, root, false);
    }

    private boolean contains(Point point, Node node, boolean isVertical) {
        if (node == null) return false;

        if (node.point.equals(point)) return true;

        // 改变分割方向
        isVertical = !isVertical;

        // 判断要查询的点在当前点的左/下还是右/上
        double value1 = isVertical ? point.x : point.y;
        double value2 = isVertical ? node.point.x : node.point.y;
        if (value1 < value2) {
            return contains(point, node.left, isVertical);
        } else {
            return contains(point, node.right, isVertical);
        }
    }

    // 返回矩形范围内的所有点， 用同名私有方法递归实现
    public Iterable<Point> range(Rect rect) {
        LinkedList<Point> result = new LinkedList<Point>();
        range(rect, root, false, result);
        return result;
    }

    private void range(Rect rect, Node node, boolean isVertical, LinkedList<Point> bag) {
        if (node == null) return;

        // 改变分割方向
        isVertical = !isVertical;
        Point point = node.point;
        if (rect.contains(point)) bag.add(point);

        // 判断当前点所分割的两个空间是否与矩形相交
        double value = isVertical ? point.x : point.y;
        double min = isVertical ? rect.minX : rect.minY;
        double max = isVertical ? rect.maxX : rect.maxY;
        if (min < value) {
            range(rect, node.left, isVertical, bag);
        }
        if (max >= value) {
            range(rect, node.right, isVertical, bag);
        }
    }

    // 返回距离该点最近的点， 用同名私有方法递归实现
    public Point nearest(Point target) {
        return nearest(target, root, null, false);
    }

    private Point nearest(Point target, Node node, Point currentBest, boolean isVertical) {
        if (node == null) return currentBest;
        isVertical = !isVertical;
        double value1 = isVertical ? target.x : target.y;
        double value2 = isVertical ? node.point.x : node.point.y;

        // 继续搜索目标点所在的半区
        Node next = value1 < value2 ? node.left : node.right;
        Node other = value1 < value2 ? node.right : node.left;
        Point nextBest = nearest(target, next, node.point, isVertical);
        double currentDistance = 0;
        double nextDistance = nextBest.distanceSquareTo(target);
        if (currentBest == null) {
            currentBest = nextBest;
            currentDistance = nextDistance;
        } else {
            currentDistance = currentBest.distanceSquareTo(target);
            if (nextDistance < currentDistance) {
                currentBest = nextBest;
                currentDistance = nextDistance;
            }
        }
        // 判断另一半区是否可能包含更近的点
        if ((other != null) && (other.rect.distanceSquareToPoint(target) < currentDistance)) {
            currentBest = nearest(target, other, currentBest, isVertical);
        }
        return currentBest;
    }
    public static void main(String[] args) {
        // unit test
    }
}
