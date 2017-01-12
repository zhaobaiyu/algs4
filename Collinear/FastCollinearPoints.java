import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int sum;
    private ArrayList<LineSegment> arraySeg;
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        int n = points.length;
        arraySeg = new ArrayList<LineSegment>();
        sum = 0;
        for (int i = 0; i < n; ++i) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
        }
        Point[] copy = points;
        Point[] tmp = new Point[points.length];
        for (int i = 0; i < n - 3; ++i) {
            tmp[0] = copy[i];
            Arrays.sort(copy, i + 1, n, copy[i].slopeOrder());
            double preSlope = copy[i].slopeTo(copy[i + 1]);
            if (preSlope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }
            int currSum = 2;
            tmp[1] = copy[i + 1];
            for (int j = i + 2; j < n; ++j) {
                double currSlope = copy[i].slopeTo(copy[j]);
                if (currSlope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                if (currSlope == preSlope) {
                    tmp[currSum++] = copy[j];
                } else {
                    if (currSum >= 4) {
                        Arrays.sort(tmp, 0, currSum);
                        arraySeg.add(new LineSegment(tmp[0], tmp[currSum - 1]));
                        ++sum;
                        tmp[0] = copy[i];
                    }
                    currSum = 2;
                    tmp[1] = copy[j];
                    preSlope = currSlope;
                }
            }
            if (currSum >= 4) {
                Arrays.sort(tmp, 0, currSum);
                arraySeg.add(new LineSegment(tmp[0], tmp[currSum - 1]));
                ++sum;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}


