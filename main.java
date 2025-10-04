import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<String> history = new ArrayList<>();
        boolean degreesMode = true; // default: degrees

        System.out.println("Scientific Calculator (type the number of the option and press Enter)");

        boolean running = true;
        while (running) {
            printCombinedMenu(degreesMode);
            System.out.print("Choice: ");

            String choiceLine = scan.nextLine().trim();
            if (choiceLine.isEmpty()) {
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceLine);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid menu number.");
                continue;
            }

            try {
                switch (choice) {
                    // Numeric/basic operations
                    case 1: // Add
                        double[] addOperands = readTwoDoubles(scan);
                        double addRes = addOperands[0] + addOperands[1];
                        printAndStore(history, addOperands[0] + " + " + addOperands[1] + " = " + addRes);
                        break;
                    case 2: // Subtract
                        double[] subOperands = readTwoDoubles(scan);
                        double subRes = subOperands[0] - subOperands[1];
                        printAndStore(history, subOperands[0] + " - " + subOperands[1] + " = " + subRes);
                        break;
                    case 3: // Multiply
                        double[] mulOperands = readTwoDoubles(scan);
                        double mulRes = mulOperands[0] * mulOperands[1];
                        printAndStore(history, mulOperands[0] + " * " + mulOperands[1] + " = " + mulRes);
                        break;
                    case 4: // Divide
                        double[] divOperands = readTwoDoubles(scan);
                        if (divOperands[1] == 0) {
                            System.out.println("Error: Division by zero is not allowed.");
                        } else {
                            double divRes = divOperands[0] / divOperands[1];
                            printAndStore(history, divOperands[0] + " / " + divOperands[1] + " = " + divRes);
                        }
                        break;
                    case 5: // Power
                        double[] powOperands = readTwoDoubles(scan);
                        double powRes = Math.pow(powOperands[0], powOperands[1]);
                        printAndStore(history, powOperands[0] + " ^ " + powOperands[1] + " = " + powRes);
                        break;
                    case 6: // Sqrt
                        double x = readOneDouble(scan, "Enter number for sqrt: ");
                        if (x < 0) {
                            System.out.println("Error: square root of negative number is not supported (use complex numbers).");
                        } else {
                            double s = Math.sqrt(x);
                            printAndStore(history, "sqrt(" + x + ") = " + s);
                        }
                        break;
                    case 7: // Factorial
                        int n = readNonNegativeInt(scan, "Enter non-negative integer for factorial: ");
                        long fact = factorial(n);
                        printAndStore(history, n + "! = " + fact);
                        break;
                    case 8: // Modulo
                        double[] modOperands = readTwoDoubles(scan);
                        if (modOperands[1] == 0) {
                            System.out.println("Error: Modulo by zero is not allowed.");
                        } else {
                            double modRes = modOperands[0] % modOperands[1];
                            printAndStore(history, modOperands[0] + " % " + modOperands[1] + " = " + modRes);
                        }
                        break;

                    // Scientific operations
                    case 9: // sin
                        double angle = readOneDouble(scan, "Enter angle: ");
                        double inr = degreesMode ? Math.toRadians(angle) : angle;
                        double sinv = Math.sin(inr);
                        printAndStore(history, "sin(" + angle + (degreesMode ? " deg" : " rad") + ") = " + sinv);
                        break;
                    case 10: // cos
                        angle = readOneDouble(scan, "Enter angle: ");
                        inr = degreesMode ? Math.toRadians(angle) : angle;
                        double cosv = Math.cos(inr);
                        printAndStore(history, "cos(" + angle + (degreesMode ? " deg" : " rad") + ") = " + cosv);
                        break;
                    case 11: // tan
                        angle = readOneDouble(scan, "Enter angle: ");
                        inr = degreesMode ? Math.toRadians(angle) : angle;
                        double tanv = Math.tan(inr);
                        printAndStore(history, "tan(" + angle + (degreesMode ? " deg" : " rad") + ") = " + tanv);
                        break;
                    case 12: // asin
                        double val = readOneDouble(scan, "Enter value (-1..1): ");
                        if (val < -1 || val > 1) {
                            System.out.println("Error: input out of domain for asin.");
                        } else {
                            double asinv = Math.asin(val);
                            double out = degreesMode ? Math.toDegrees(asinv) : asinv;
                            printAndStore(history, "asin(" + val + ") = " + out + (degreesMode ? " deg" : " rad"));
                        }
                        break;
                    case 13: // acos
                        val = readOneDouble(scan, "Enter value (-1..1): ");
                        if (val < -1 || val > 1) {
                            System.out.println("Error: input out of domain for acos.");
                        } else {
                            double acosv = Math.acos(val);
                            double out = degreesMode ? Math.toDegrees(acosv) : acosv;
                            printAndStore(history, "acos(" + val + ") = " + out + (degreesMode ? " deg" : " rad"));
                        }
                        break;
                    case 14: // atan
                        val = readOneDouble(scan, "Enter value: ");
                        double atanv = Math.atan(val);
                        double outAtan = degreesMode ? Math.toDegrees(atanv) : atanv;
                        printAndStore(history, "atan(" + val + ") = " + outAtan + (degreesMode ? " deg" : " rad"));
                        break;
                    case 15: // ln
                        double lnval = readOneDouble(scan, "Enter positive number for ln: ");
                        if (lnval <= 0) {
                            System.out.println("Error: ln domain is (0, +inf).");
                        } else {
                            double lnres = Math.log(lnval);
                            printAndStore(history, "ln(" + lnval + ") = " + lnres);
                        }
                        break;
                    case 16: // log10
                        double lgval = readOneDouble(scan, "Enter positive number for log10: ");
                        if (lgval <= 0) {
                            System.out.println("Error: log10 domain is (0, +inf).");
                        } else {
                            double lgres = Math.log10(lgval);
                            printAndStore(history, "log10(" + lgval + ") = " + lgres);
                        }
                        break;
                    case 17: // exp
                        double expv = readOneDouble(scan, "Enter exponent for e^x: ");
                        double exres = Math.exp(expv);
                        printAndStore(history, "exp(" + expv + ") = " + exres);
                        break;
                    case 18: // abs
                        double absn = readOneDouble(scan, "Enter number for abs: ");
                        printAndStore(history, "abs(" + absn + ") = " + Math.abs(absn));
                        break;
                    case 19: // toggle degrees/radians
                        degreesMode = !degreesMode;
                        System.out.println("Angle mode now: " + (degreesMode ? "DEGREES" : "RADIANS"));
                        break;
                    case 20: // history
                        printHistory(history);
                        break;
                    case 21: // clear history
                        history.clear();
                        System.out.println("History cleared.");
                        break;
                    case 22: // exit
                        running = false;
                        System.out.println("Exiting. Goodbye!");
                        break;
                    default:
                        System.out.println("Unknown option. Please select a number from the menu.");
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input: " + ime.getMessage());
                scan.nextLine(); // clear
            } catch (Exception ex) {
                System.out.println("An error occurred: " + ex.getMessage());
            }
        }

        scan.close();
    }

    private static void printScientificMenu(boolean degreesMode) {
        System.out.println();
        System.out.println("Angle mode: " + (degreesMode ? "DEGREES" : "RADIANS"));
        System.out.println("1) sin");
        System.out.println("2) cos");
        System.out.println("3) tan");
        System.out.println("4) asin");
        System.out.println("5) acos");
        System.out.println("6) atan");
        System.out.println("7) ln (natural log)");
        System.out.println("8) log10");
        System.out.println("9) exp (e^x)");
        System.out.println("10) abs");
        System.out.println("11) Toggle angle mode (degrees/radians)");
        System.out.println("12) Show history");
        System.out.println("13) Clear history");
        System.out.println("14) Exit");
    }

    private static void printCombinedMenu(boolean degreesMode) {
        System.out.println();
        System.out.println("Angle mode: " + (degreesMode ? "DEGREES" : "RADIANS"));
        System.out.println("--- Numeric operations ---");
        System.out.println("1) Add");
        System.out.println("2) Subtract");
        System.out.println("3) Multiply");
        System.out.println("4) Divide");
        System.out.println("5) Power (a^b)");
        System.out.println("6) Square root");
        System.out.println("7) Factorial (integer)");
        System.out.println("8) Modulo");
        System.out.println();
        System.out.println("--- Scientific operations ---");
        System.out.println("9) sin");
        System.out.println("10) cos");
        System.out.println("11) tan");
        System.out.println("12) asin");
        System.out.println("13) acos");
        System.out.println("14) atan");
        System.out.println("15) ln (natural log)");
        System.out.println("16) log10");
        System.out.println("17) exp (e^x)");
        System.out.println("18) abs");
        System.out.println("19) Toggle angle mode (degrees/radians)");
        System.out.println();
        System.out.println("20) Show history");
        System.out.println("21) Clear history");
        System.out.println("22) Exit");
    }

    private static double[] readTwoDoubles(Scanner scan) {
        double a = readOneDouble(scan, "Enter first number: ");
        double b = readOneDouble(scan, "Enter second number: ");
        return new double[]{a, b};
    }

    private static double readOneDouble(Scanner scan, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scan.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readNonNegativeInt(Scanner scan, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scan.nextLine().trim();
            try {
                int v = Integer.parseInt(line);
                if (v < 0) {
                    System.out.println("Please enter a non-negative integer.");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static long factorial(int n) {
        long res = 1L;
        for (int i = 2; i <= n; i++) {
            res = res * i;
            if (res < 0) { // overflow detection
                throw new ArithmeticException("Factorial result overflowed for n=" + n);
            }
        }
        return res;
    }

    private static void printAndStore(List<String> history, String entry) {
        System.out.println(entry);
        history.add(entry);
    }

    private static void printHistory(List<String> history) {
        if (history.isEmpty()) {
            System.out.println("History is empty.");
            return;
        }
        System.out.println("---- History ----");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ") " + history.get(i));
        }
        System.out.println("-----------------");
    }
}