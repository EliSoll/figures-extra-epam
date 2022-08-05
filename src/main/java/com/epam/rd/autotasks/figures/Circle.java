package com.epam.rd.autotasks.figures;

import static java.lang.Math.*;

class Circle extends Figure {
    final static double epsilon = 0.000001d;
     Point centerPoint;
    double radius;

    public Circle(Point centerPoint, double radius) {
        if (radius > 0 && centerPoint != null) {
            this.centerPoint = centerPoint;
            this.radius = radius;
        } else throw new IllegalArgumentException();
    }

    @Override
    public Point centroid() {
        return new Point(this.centerPoint.getX(), this.centerPoint.getY());
    }

   public double area() {
       return Math.PI * Math.pow(radius, 2.0);

   }
   public double getRadius(Circle figure) {
        return figure.radius;
   }
    public static double getCenterPointX(Circle figure) {
        return figure.centerPoint.getX();
    }
    public static double getCenterPointY(Circle figure) {
        return figure.centerPoint.getY();
    }

    @Override
    public boolean isTheSame(Figure figure) {
        if (figure instanceof Circle) {
            Circle q = (Circle) figure;
          double centerX = this.centerPoint.getX();
          double centerY = this.centerPoint.getY();
          double otherX = getCenterPointX(q);
          double otherY = getCenterPointY(q);
          double radius = this.radius;

            return (abs(centerX - otherX) < epsilon) && (abs(centerY - otherY) < epsilon) && (abs(radius - q.radius) < epsilon);
            }
        return false;
    }

}

