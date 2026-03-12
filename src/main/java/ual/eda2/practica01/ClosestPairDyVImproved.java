package ual.eda2.practica01;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairDyVImproved {

	// A structure to represent a Point in 2D plane
	public static class Point {
		public int x;
		public int y;
	 
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static double distance(Point p1, Point p2) {
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) +
						(p1.y - p2.y) * (p1.y - p2.y));
	  }
	
	static class PointXComparator implements Comparator<Point> {
		// Needed to sort array of points according to X coordinate
		@Override
		public int compare(Point pointA, Point pointB) {
			return Integer.compare(pointA.x, pointB.x);
		}
	}

	static class PointYComparator implements Comparator<Point> {	 
		// Needed to sort array of points according to Y coordinate
		@Override
		public int compare(Point pointA, Point pointB) {
			return Integer.compare(pointA.y, pointB.y);
		} 
	}
	
    private Point best1, best2;
    private double bestDistance = Double.POSITIVE_INFINITY;
    
    public void ClosestPair(Point[] points) {
        if (points == null) throw new IllegalArgumentException("constructor argument is null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("array element " + i + " is null");
        }

        int n = points.length;
        if (n <= 1) return;

        // sort by x-coordinate (breaking ties by y-coordinate via stability)
        Point[] pointsByX = new Point[n];
        for (int i = 0; i < n; i++)
            pointsByX[i] = points[i];
        Arrays.sort(pointsByX, new PointYComparator());
        Arrays.sort(pointsByX, new PointXComparator());
        
        // check for coincident points
        for (int i = 0; i < n-1; i++) {
            if (pointsByX[i].equals(pointsByX[i+1])) {
                bestDistance = 0.0;
                best1 = pointsByX[i];
                best2 = pointsByX[i+1];
                return;
            }
        }

        // sort by y-coordinate (but not yet sorted)
        Point[] pointsByY = new Point[n];
        for (int i = 0; i < n; i++)
            pointsByY[i] = pointsByX[i];

        // auxiliary array
        Point[] aux = new Point[n];

        closest(pointsByX, pointsByY, aux, 0, n-1);
    }
    
    // find closest pair of points in pointsByX[lo..hi]
    // precondition:  pointsByX[lo..hi] and pointsByY[lo..hi] are the same sequence of points
    // precondition:  pointsByX[lo..hi] sorted by x-coordinate
    // postcondition: pointsByY[lo..hi] sorted by y-coordinate
    public double closest(Point[] pointsByX, Point[] pointsByY, Point[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        Point median = pointsByX[mid];

        // compute closest pair with both endpoints in left subarray or both in right subarray
        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid+1, hi);
        double delta = Math.min(delta1, delta2);

        // merge back so that pointsByY[lo..hi] are sorted by y-coordinate
        merge(pointsByY, aux, lo, mid, hi);

        // aux[0..m-1] = sequence of points closer than delta, sorted by y-coordinate
        int m = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].x - median.x) < delta)
                aux[m++] = pointsByY[i];
        }

        // compare each point to its neighbors with y-coordinate closer than delta
        for (int i = 0; i < m; i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i+1; (j < m) && (aux[j].y - aux[i].y < delta); j++) {
                double distance = distance(aux[i], aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance) {
                        bestDistance = delta;
                        best1 = aux[i];
                        best2 = aux[j];
                        // StdOut.println("better distance = " + delta + " from " + best1 + " to " + best2);
                    }
                }
            }
        }
        return delta;
    }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
    private void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (aux[j].y < aux[i].y)  a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
    }

	public static void main(String[] args) {
		Point[] P = new Point[]{
	    	      new Point(2, 3),
	    	      new Point(12, 30),
	    	      new Point(40, 50),
	    	      new Point(5, 1),
	    	      new Point(12, 10),
	    	      new Point(3, 4)
	    	      };

	    ClosestPairDyVImproved best = new ClosestPairDyVImproved();
	    best.ClosestPair(P);
	    System.out.println("The smallest distance is " + best.bestDistance);
	    System.out.println("The point1 X is " + best.best1.x);
	    System.out.println("The point1 Y is " + best.best1.y);
	    System.out.println("The point2 X is " + best.best2.x);
	    System.out.println("The point2 Y is " + best.best2.y);
	}

}
