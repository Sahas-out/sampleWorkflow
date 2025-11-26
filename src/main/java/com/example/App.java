package com.example;

public class App {
    private static final String USAGE = "Usage: <add|sub|mul|div> <number1> <number2>";

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(USAGE);
            return;
        }

        String op = args[0];
        double a;
        double b;
        try {
            a = Double.parseDouble(args[1]);
            b = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Both operands must be numbers.");
            return;
        }

        Calculator calc = new Calculator();
        try {
            double result;
            switch (op.toLowerCase()) {
                case "add":
                    result = calc.add(a, b);
                    break;
                case "sub":
                    result = calc.subtract(a, b);
                    break;
                case "mul":
                    result = calc.multiply(a, b);
                    break;
                case "div":
                    result = calc.divide(a, b);
                    break;
                default:
                    System.out.println("Unknown operation: " + op);
                    return;
            }
            System.out.println(result);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
