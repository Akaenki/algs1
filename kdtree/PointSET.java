import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private SET<Point2D> set;
    public PointSET(){
        set = new SET<>();
    }

    public boolean isEmpty(){
        return set.isEmpty();
    }

    public int size(){
        return set.size();
    }

    public void insert(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    public boolean contains(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw(){
        for(Point2D p : set) p.draw();
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null) throw new IllegalArgumentException();
        List<Point2D> list = new ArrayList<>();
        for(Point2D p : set){
            if(rect.contains(p)) list.add(p);
        }
        return list;
    }

    public Point2D nearest(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        if(isEmpty()) return null;
        Point2D nerest = null; double dist = Double.POSITIVE_INFINITY;
        for(Point2D n : set){
            if(n.distanceSquaredTo(p) < dist){
                dist = n.distanceSquaredTo(p);
                nerest = n;
            }
        }
        return nerest;
    }

}
