import java.util.ArrayList;

// RK2 or improved Euler's method
// takes these values:
// 1. tFin - finishing time
// 2. dt
// 3. alpha(t0)
// 4. omega(t0)
// 5. l - length of the rope
// 6. g
// it returns
// 1. alpha(t0 + dt)
// 2. omega(t0 + dt)
public class RK2 {
    // constants
    private final double dt;
    private final double tFin;
    private final double l;
    private final double g;

    // current values
    private double currAlpha;
    private double currOmega;

    // output
    private ArrayList<Double> alphas;
    private ArrayList<Double> omegas;

    public RK2(double dt, double tFin, double currAlpha, double currOmega, double l, double g) {
        this.dt = dt;
        this.tFin = tFin;
        this.currAlpha = currAlpha;
        this.currOmega = currOmega;
        this.l = l;
        this.g = g;

        alphas = new ArrayList<>();
        omegas = new ArrayList<>();
    }

    public void simulate() {
        for (double t = 0; t <= tFin; t += dt) {
            double k1Alpha = DerivativeFunction.getKAlpha(currOmega);
            double k1Omega = DerivativeFunction.getKOmega(currAlpha, l, g);
            // additional alpha for
            double alpha = EulerFunction.getNewAlpha(currAlpha, k1Alpha, dt);
            double omega = EulerFunction.getNewOmega(currOmega, k1Omega, dt);
            double k2Alpha = DerivativeFunction.getKAlpha(omega);
            double k2Omega = DerivativeFunction.getKOmega(alpha, l, g);

            // newAlpha = alpha(t0 + dt)
            double newAlpha = currAlpha + (k1Alpha + k2Alpha) / 2 * dt;
            double newOmega = currOmega + (k1Omega + k2Omega) / 2 * dt;

            // updating
            alphas.add(alpha);
            omegas.add(omega);
            currAlpha = newAlpha;
            currOmega = newOmega;
        }
    }

    public ArrayList<Double> getAlphas() {
        return alphas;
    }

    public ArrayList<Double> getOmegas() {
        return omegas;
    }
}
