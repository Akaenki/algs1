import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points){
        if(points == null) throw new IllegalArgumentException();
        for(Point point : points){
            if(point == null) throw new IllegalArgumentException();
        }
        int n = points.length;
        Point[] p = points.clone();
        Arrays.sort(p, Point::compareTo);
        for(int i = 0; i<n; ++i){
            if(i != 0 && p[i].compareTo(p[i-1])==0) throw new IllegalArgumentException();
        }

        ArrayList<LineSegment> ls = new ArrayList<>();
        for(int i = 0; i<=n-4; ++i){
            Point cur = p[i];
            Point[] sorted = Arrays.copyOf(p, n);
            swap(sorted, 0, i);
            Arrays.sort(sorted,1,n,cur.slopeOrder());
            int count = 1;
            for(int j = 2; j<=n; ++j){
                if(j<n && Double.compare(cur.slopeTo(sorted[j]),cur.slopeTo(sorted[j-1]))==0) count++;
                else {
                    if(count >= 3 && sorted[j-count].compareTo(cur) > 0)
                        ls.add(new LineSegment(cur, sorted[j-1]));
                    count = 1;
                }
            }
        }
        segments = ls.toArray(new LineSegment[ls.size()]);
    }

    private void swap(Point[] points, int i , int j){
        Point temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    public int numberOfSegments(){
        return segments.length;
    }

    public LineSegment[] segments(){
        return segments.clone();
    }
}
