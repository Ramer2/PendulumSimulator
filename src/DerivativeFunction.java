// the euler function class
// takes in 2 values:
// 1. current angle
// 2. current angle speed
// + values from the simulation itself
// outputs 2 values:
// 1. kAlpha
// 2. kOmega
public class DerivativeFunction {
    public static double getKAlpha(double omega) {
        return omega;
    }

    public static double getKOmega(double alpha, double l, double g) {
        return g / l * Math.sin(alpha);
    }
}
