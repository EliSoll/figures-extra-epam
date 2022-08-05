package com.epam.rd.autotasks.figures;
import java.awt.geom.QuadCurve2D;
import java.util.Objects;

import static com.epam.rd.autotasks.figures.GFG.isConvex;
import static java.lang.Math.*;
import static java.lang.StrictMath.pow;

class Quadrilateral extends Figure {
    double epsilon = 0.000001d;
    private final Point a;
    private final Point b;
    private final Point c;
    private final Point d;
    int[][] points;


    public Quadrilateral(Point a, Point b, Point c, Point d) {


        if (a == null || b == null || c == null || d == null) {
            throw new IllegalArgumentException("Arguments can't be null");
        }
        if (collinear(a, b, c) /*|| collinear(a, c, d) || collinear(a, d, b) || collinear(b, d, c)*/) {
            throw new IllegalArgumentException("Quadriliteral is degenerative");
        }
        double[][] points = { { a.getX(), a.getY() }, { b.getX(), b.getY() },
                { c.getX(), c.getY() }, { d.getX(), d.getY() } };
        if (!isConvex(points)) {
            throw new IllegalArgumentException();
        }
        this.a =a;
        this.b =b;
        this.c =c;
        this.d =d;
}

    public boolean collinear(Point a, Point b, Point c) {
        return (a.getX() - b.getX()) * (c.getY() - b.getY()) - (a.getY() - b.getY()) * (c.getX() - b.getX()) == 0;
    }

    @Override
    public Point centroid() {
        Point center1 = centroidTriangle(a,b,c);
        Point center2 = centroidTriangle(a,c,d);
        Point center3 = centroidTriangle(a,d,b);
        Point center4 = centroidTriangle(b,d,c);

        return new Segment(center1,center2)
                .intersection(new Segment(center3,center4));
    }

    public Point centroidTriangle(Point a, Point b, Point c) {
        double xc = (((a.getX()+b.getX()+c.getX())/3));
        double yc = (((a.getY()+b.getY()+c.getY())/3));
        return new Point(xc, yc);
    }
    public double area() {
        return new Triangle(a,b,d).area() + new Triangle(b,c,d).area();
    }
    @Override
    public boolean isTheSame(Figure figure) {

        double area = this.area();
        if (figure instanceof Quadrilateral) {
            Quadrilateral q = (Quadrilateral) figure;
            double areaQ = q.area();
            return abs(area - areaQ) < epsilon;
        }
        return false;
    }

}
class Segment {

    Point start;
    Point end;

    public Segment(Point start, Point end) {
        if(Objects.isNull(start) || Objects.isNull(end) || start.equals(end))
            throw new RuntimeException();
        this.start = start;
        this.end = end;
    }

    double length() {
        return sqrt(pow(end.getX() - start.getX(), 2) + pow(end.getY() - start.getY(), 2));
    }

    Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    Point intersection(Segment another) {
        double k1 = (end.getY() - start.getY()) / (end.getX() - start.getX());
        double k2 = (another.end.getY() - another.start.getY()) / (another.end.getX() - another.start.getX());
        if (k1 == k2) return null;

        double b1 = (start.getY() * end.getX() - end.getY() * start.getX()) / (end.getX() - start.getX());
        double b2 = (another.start.getY() * another.end.getX() - another.end.getY() * another.start.getX()) /
                (another.end.getX() - another.start.getX());

        double x = (b2 - b1) / (k1 - k2);
        double y = (k1 * b2 - k2 * b1) / (k1 - k2);

        if ((x > start.getX() && x > end.getX()) || (x > another.start.getX() && x > another.end.getX()) ||
                (x < start.getX() && x < end.getX()) || (x < another.start.getX() && x < another.end.getX()) ||
                (y > start.getY() && y > end.getY()) || (y > another.start.getY() && y > another.end.getY()) ||
                (y < start.getY() && y < end.getY()) || (y < another.start.getY() && y < another.end.getY()))
            return null;


        return new Point(x, y);

    }
}

class GFG
{


    static double CrossProduct(double[][] A)
    {

        double X1 = (A[1][0] - A[0][0]);


        double Y1 = (A[1][1] - A[0][1]);


        double X2 = (A[2][0] - A[0][0]);


        double Y2 = (A[2][1] - A[0][1]);

        return (X1 * Y2 - Y1 * X2);
    }

    // Function to check if the polygon is
// convex polygon or not
    static boolean isConvex(double[][] points)
    {

        int N = points.length;


        double prev = 0;


        double curr;

        for (int i = 0; i < N; i++) {


            double[][] temp = { points[i],
                    points[((i + 1) % N)],
                    points[((i + 2) % N)] };


            curr = CrossProduct(temp);


            if (curr != 0) {


                if (curr * prev < 0) {
                    return false;
                }
                else {

                    prev = curr;
                }
            }
        }
        return true;
    }

}



