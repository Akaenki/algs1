import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb, rt;
        public Node(Point2D _p, RectHV _rect){
            p = _p; rect = _rect;
        }
    }

    private Node root;
    private int size;

    public KdTree(){
        root = null; size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void insert(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        root = insert(root, p, true, 0,0,1,1);
    }

    private Node insert(Node root, Point2D p, boolean vertical, double xmin, double ymin, double xmax, double ymax){
        if(root == null) {
            size++;
            return new Node(p, new RectHV(xmin,ymin,xmax,ymax));
        }
        else if(root.p.equals(p)) return root;
        RectHV lb, rt;
        if(vertical) { //verticle
            if(p.x() < root.p.x())
                root.lb = insert(root.lb, p, !vertical, xmin, ymin, root.p.x(), ymax);
            else root.rt = insert(root.rt, p, !vertical, root.p.x(), ymin, xmax, ymax);
        } else { //horizontal
            if(p.y() < root.p.y())
                root.lb = insert(root.lb, p, !vertical, xmin, ymin, xmax, root.p.y());
            else root.rt = insert(root.rt, p, !vertical, xmin, root.p.y(), xmax, ymax);
        }
        return root;
    }

    public boolean contains(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return contains(root, p, true);
    }

    private boolean contains(Node root, Point2D p, boolean vertcal){
        if(root == null) return false;
        if(root.p.equals(p)) return true;
        if(vertcal){
            if(p.x() < root.p.x()) return contains(root.lb, p, !vertcal);
            else return contains(root.rt, p, !vertcal);
        } else {
            if(p.y() < root.p.y()) return contains(root.lb, p, !vertcal);
            else return contains(root.rt, p, !vertcal);
        }
    }

    public void draw(){
        draw(root, true);
    }

    private void draw(Node root, boolean vertical){
        if(root == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.p.draw();
        if(vertical){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p.y());
        }

        draw(root.lb, !vertical);
        draw(root.rt, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null) throw new IllegalArgumentException();
        List<Point2D> list = new ArrayList<>();
        range(root, rect, list, true);
        return list;
    }

    private void range(Node root, RectHV rect, List<Point2D> list, boolean verticle){
        if(root == null) return;
        if(rect.contains(root.p)) list.add(root.p);
        if(verticle){
            if(root.p.x() > rect.xmax()) range(root.lb, rect, list, !verticle);
            else if(root.p.x() < rect.xmin()) range(root.rt, rect, list, !verticle);
            else { //intersect
                range(root.lb, rect, list, !verticle);
                range(root.rt, rect, list, !verticle);
            }
        } else{
            if(root.p.y() > rect.ymax()) range(root.lb, rect, list, !verticle);
            else if(root.p.y() < rect.ymin()) range(root.rt, rect, list, !verticle);
            else { //intersect
                range(root.lb, rect, list, !verticle);
                range(root.rt, rect, list, !verticle);
            }
        }

    }

    public Point2D nearest(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        if(root == null) return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node root, Point2D p, Point2D minp, boolean vertical){
        if(root == null) return minp;
        if(root.p.distanceSquaredTo(p) < minp.distanceSquaredTo(p)){
            minp = root.p;
        }

        /** ONLY visit the rectangular with distance closer to current closest point */
        if((vertical && (p.x() < root.p.x()))|| (!vertical && (p.y() < root.p.y()))){
            minp = nearest(root.lb, p, minp, !vertical);
            if(root.rt != null && root.rt.rect.distanceSquaredTo(p) < minp.distanceSquaredTo(p))
                minp = nearest(root.rt, p, minp, !vertical);
        } else {
            minp = nearest(root.rt, p, minp, !vertical);
            if(root.lb != null && root.lb.rect.distanceSquaredTo(p) < minp.distanceSquaredTo(p))
                minp = nearest(root.lb, p, minp, !vertical);
        }

        return minp;
    }
}
