// the euler function class
// takes in 5 values:
// 1. current angle
// 2. current angle speed
// 3. kAlpha value
// 4. kOmega value
// 5. dt value
// outputs 2 values:
// 1. newAlpha
// 2. newOmega
public class EulerFunction {
    public static double getNewAlpha(double currAlpha, double kAlpha, double dt) {
        return currAlpha + kAlpha * dt;
    }

    public static double getNewOmega(double currOmega, double kOmega, double dt) {
        return currOmega + kOmega * dt;
    }
}
