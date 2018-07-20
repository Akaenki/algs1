import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int size;

    public BruteCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException();
        for(Point point : points){
            if(point == null) throw new IllegalArgumentException();
        }
        int n = points.length;
        Point[] p = Arrays.copyOf(points, n);

        Arrays.sort(p, Point::compareTo);
        for(int i = 0; i<n; ++i){
            if(p[i] == null) throw new IllegalArgumentException();
            if(i != 0 && p[i].compareTo(p[i-1])==0) throw new IllegalArgumentException();
        }

        ArrayList<LineSegment> ls = new ArrayList<>();
        for(int a = 0; a<n-3; ++a){
            for(int b = a+1; b<n-2; ++b){
                for(int c = b+1; c<n-1; ++c){
                    for(int d = c+1; d<n; ++d){
                        if(p[a].slopeTo(p[b]) == p[a].slopeTo(p[c])
                                && p[a].slopeTo(p[c]) == p[a].slopeTo(p[d]))
                            ls.add(new LineSegment(p[a],p[d]));
                    }
                }
            }
        }
        segments = ls.toArray(new LineSegment[ls.size()]);
    }

    public int numberOfSegments(){
        return segments.length;
    }

    public LineSegment[] segments(){
        return segments.clone();
    }
}
