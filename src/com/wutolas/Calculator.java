package com.wutolas;

import com.wutolas.exception.CommandNotSupportedByCalculatorException;
import com.wutolas.memory.Memory;

import java.util.Map;

class Calculator {

    private Memory memory;
    private Map<String, MathOperation> mathOperations;

    Calculator(Memory memory, Map<String, MathOperation> mathOperations) {
        this.memory = memory;
        this.mathOperations = mathOperations;
    }

    int process(String[] arr) throws ArithmeticException {
        int result;
        int argument1;
        int argument2 = resolveArgument(arr[2]);

        if (arr[0].equals("set")) {
            result = this.memory.set(arr[1], argument2);
        } else {
            argument1 = resolveArgument(arr[1]);
            MathOperation operation = this.mathOperations.get(arr[0]);

            if (operation == null) {
                throw new CommandNotSupportedByCalculatorException();
            }

            result = this.mathOperations.get(arr[0]).calculate(argument1, argument2);
        }

        this.memory.set("$", result);

        return result;
    }

    int getLatestResult() {
        return this.memory.get("$");
    }

    private boolean isNumber(String argument) {
        try {
            Integer.parseInt(argument);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private int resolveArgument(String argument) {
        int resolvedArgument;

        if (!isNumber(argument)) {
            resolvedArgument = this.memory.get(argument);
        } else {
            resolvedArgument = Integer.valueOf(argument);
        }

        return resolvedArgument;
    }


}
