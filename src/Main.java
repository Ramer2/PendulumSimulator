import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Double> h = new ArrayList<>();
    private static ArrayList<Double> Ek = new ArrayList<>();
    private static ArrayList<Double> Ep = new ArrayList<>();
    private static ArrayList<Double> Et = new ArrayList<>();

    public static void main(String[] args) {
//        RK2 rk2 = new RK2(0.01, 100, 0.785398163, 0, 1, -10);
//        rk2.simulate();
//        getHeight(rk2.getAlphas());
//        getKinetics(rk2.getOmegas());
//        getPotential(rk2.getAlphas());
//        getTotal();

        RK4 rk4 = new RK4(0.01, 5, 0.785398163, 0, 1, -10);
        rk4.simulate();
        getHeight(rk4.getAlphas());
        getKinetics(rk4.getOmegas());
        getPotential(rk4.getAlphas());
        getTotal();

        writeResultsToTSV("results.tsv");
    }

    public static void getHeight(ArrayList<Double> alphas) {
        for (Double alpha : alphas) {
            double height = 1 * Math.cos(alpha - 1.570796327) + 1;
            h.add(height);
        }
    }

    public static void getKinetics(ArrayList<Double> omegas) {
        for (Double oDouble : omegas) {
            double kinetic = Math.pow(oDouble, 2) * 1 * 1 * 1 * 1 / 2;
            Ek.add(kinetic);
        }
    }

    public static void getPotential(ArrayList<Double> alphas) {
        for (Double aDouble : alphas) {
            double potential = 1 * 10 * 1 * (1 - Math.cos(aDouble));
            Ep.add(potential);
        }
    }

    public static void getTotal() {
        for (int i = 0; i < Ek.size(); i++) {
            Et.add(Ek.get(i) + Ep.get(i));
        }
    }

    public static void writeResultsToTSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Ek\tEp\tEt\n"); // Header row
            for (int i = 0; i < Ek.size(); i++) {
                writer.write(Ek.get(i) + "\t" + Ep.get(i) + "\t" + Et.get(i) + "\n");
            }
            System.out.println("Results successfully written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
