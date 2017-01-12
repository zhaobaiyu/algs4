import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int sum;
    private ArrayList<LineSegment> arraySeg;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        int n = points.length;
        arraySeg = new ArrayList<LineSegment>();
        for (int i = 0; i < n; ++i) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }
        sum = 0;
        // Arrays.sort(points);
        Point[] tmp = new Point[4];
        for (int i1 = 0; i1 < n; ++i1) {
            tmp[0] = points[i1];
            for (int i2 = i1 + 1; i2 < n; ++i2) {
                double slope1 = points[i1].slopeTo(points[i2]);
                if (slope1 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                tmp[1] = points[i2];
                for (int i3 = i2 + 1; i3 < n; ++i3) {
                    if (slope1 != points[i1].slopeTo(points[i3]))
                        continue;
                    tmp[2] = points[i3];
                    for (int i4 = i3 + 1; i4 < n; ++i4) {
                        if (slope1 == points[i1].slopeTo(points[i4])) {
                            tmp[3] = points[i4];
                            Arrays.sort(tmp);
                            arraySeg.add(new LineSegment(tmp[0], tmp[3]));
                            ++sum;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return sum;
    }

    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[sum];
        for (int i = 0; i < sum; ++i) {
            seg[i] = arraySeg.get(i);
        }        
        return seg;
    }
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}


