import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Double> h = new ArrayList<>();
    private static ArrayList<Double> Ek = new ArrayList<>();
    private static ArrayList<Double> Ep = new ArrayList<>();
    private static ArrayList<Double> Et = new ArrayList<>();

    private static double dt;
    private static int tFin;
    private static double startingAngle;
    private static double startingW;
    private static double length;
    private static double mass;

    // example: dt = 0.05, tFin = 20, angle = 30, starting W = 0, length = 2, mass = 5

    public static void main(String[] args) {
        getInputFromConsole();

        RK2 rk2 = new RK2(dt, tFin, startingAngle, startingW, length, -9.81);
        rk2.simulate();
        computeEnergies(rk2.getAlphas(), rk2.getOmegas());
        ArrayList<Double> Ek_RK2 = new ArrayList<>(Ek);
        ArrayList<Double> Ep_RK2 = new ArrayList<>(Ep);
        ArrayList<Double> Et_RK2 = new ArrayList<>(Et);
        clearData();

        RK4 rk4 = new RK4(dt, tFin, startingAngle, startingW, length, -9.81);
        rk4.simulate();
        computeEnergies(rk4.getAlphas(), rk4.getOmegas());
        ArrayList<Double> Ek_RK4 = new ArrayList<>(Ek);
        ArrayList<Double> Ep_RK4 = new ArrayList<>(Ep);
        ArrayList<Double> Et_RK4 = new ArrayList<>(Et);

        writeResultsToTSV("results.tsv", Ek_RK2, Ep_RK2, Et_RK2, Ek_RK4, Ep_RK4, Et_RK4);
    }

    public static void getInputFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter time step (dt): ");
        dt = scanner.nextDouble();

        System.out.print("Enter total time (tFin): ");
        tFin = scanner.nextInt();

        System.out.print("Enter starting angle (radians): ");
        startingAngle = scanner.nextDouble();

        System.out.print("Enter starting angular velocity: ");
        startingW = scanner.nextDouble();

        System.out.print("Enter length of the string: ");
        length = scanner.nextDouble();

        System.out.print("Enter mass: ");
        mass = scanner.nextDouble();

        scanner.close();
    }

    public static void computeEnergies(ArrayList<Double> alphas, ArrayList<Double> omegas) {
        for (Double alpha : alphas) {
            h.add(length * Math.cos(alpha - 1.570796327) + length);
            Ep.add(mass * 10 * length * (1 - Math.cos(alpha)));
        }
        for (Double omega : omegas) {
            Ek.add(Math.pow(omega, 2) * 2 * mass * length / 2);
        }
        for (int i = 0; i < Ek.size(); i++) {
            Et.add(Ek.get(i) + Ep.get(i));
        }
    }

    public static void clearData() {
        h.clear();
        Ek.clear();
        Ep.clear();
        Et.clear();
    }

    public static void writeResultsToTSV(String filename, ArrayList<Double> Ek_RK2, ArrayList<Double> Ep_RK2, ArrayList<Double> Et_RK2,
                                         ArrayList<Double> Ek_RK4, ArrayList<Double> Ep_RK4, ArrayList<Double> Et_RK4) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("RK2_Ek\tRK2_Ep\tRK2_Et\tRK4_Ek\tRK4_Ep\tRK4_Et\n"); // Header row

            int maxSize = Math.max(Ek_RK2.size(), Ek_RK4.size());
            for (int i = 0; i < maxSize; i++) {
                String rk2Ek = (i < Ek_RK2.size()) ? String.format("%.2f", Ek_RK2.get(i)) : "";
                String rk2Ep = (i < Ep_RK2.size()) ? String.format("%.2f", Ep_RK2.get(i)) : "";
                String rk2Et = (i < Et_RK2.size()) ? String.format("%.2f", Et_RK2.get(i)) : "";
                String rk4Ek = (i < Ek_RK4.size()) ? String.format("%.2f", Ek_RK4.get(i)) : "";
                String rk4Ep = (i < Ep_RK4.size()) ? String.format("%.2f", Ep_RK4.get(i)) : "";
                String rk4Et = (i < Et_RK4.size()) ? String.format("%.2f", Et_RK4.get(i)) : "";

                writer.write(String.join("\t", rk2Ek, rk2Ep, rk2Et, rk4Ek, rk4Ep, rk4Et) + "\n");
            }
            System.out.println("Results successfully written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
