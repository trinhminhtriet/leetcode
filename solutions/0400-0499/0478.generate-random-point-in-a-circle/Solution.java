import java.util.Random;

public class Solution {
    private double radius;
    private double xCenter;
    private double yCenter;
    private Random random;

    public Solution(double radius, double xCenter, double yCenter) {
        this.radius = radius;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.random = new Random(); // Initialize the random instance for generating random numbers.
    }

    public double[] randPoint() {
        double length = Math.sqrt(this.radius * this.radius * random.nextDouble());
      
        double angle = random.nextDouble() * 2 * Math.PI;
      
        double x = this.xCenter + length * Math.cos(angle); // Horizontal offset from the center.
        double y = this.yCenter + length * Math.sin(angle); // Vertical offset from the center.
      
        return new double[]{x, y};
    }
}