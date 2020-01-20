package com.wutolas;

import com.wutolas.exception.CommandNotSupportedByCalculatorException;
import com.wutolas.memory.BasicMemory;
import com.wutolas.memory.Memory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    private static Calculator calculator;
    private static List<String> results;

    public static void main(String[] args) {

        MathOperation add = (a, b) -> a + b;
        MathOperation sub = (a, b) -> a - b;
        MathOperation mul = (a, b) -> a * b;
        MathOperation div = (a, b) -> {
            if (b == 0) throw new ArithmeticException("Division by zero");
            return a / b;
        };

        Map<String, MathOperation> calculatorOperations = new HashMap<String, MathOperation>() {{
            put("add", add);
            put("sub", sub);
            put("mul", mul);
            put("div", div);
        }};

        Memory memory = new BasicMemory();
        calculator = new Calculator(memory, calculatorOperations);
        results = new LinkedList<>();

        processFile(args[0]);
        writeFile(args[0]);
    }

    private static void processFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                String[] arr = line.split(" ");

                if (arr[0].equals("run")) {
                    processFile(arr[1]);
                    results.add("res: " + calculator.getLatestResult());
                    continue;
                }

                try {
                    int result = calculator.process(arr);
                    results.add("res: " + result);
                } catch (ArithmeticException | CommandNotSupportedByCalculatorException ex) {
                    results.add("error");
                }
            }
        } catch (IOException e) {
            results.add("error");
        }
    }

    private static void writeFile(String fileName) {
        String newFileName = fileName.replace(".in", ".out");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(newFileName)))) {
            for (String res : results) {
                bw.write(res);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
