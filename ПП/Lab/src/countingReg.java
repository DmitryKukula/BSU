import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class countingReg {
    public static int calculate(String expression) {;
        Pattern pattern = Pattern.compile("([0-9]+|[+\\-*/])");
        Matcher matcher = pattern.matcher(expression);
        MyStack<Integer> operands = new MyStack<>();
        MyStack<String> operators = new MyStack<>();

        while (matcher.find()) {
            String token = matcher.group();
            if (token.matches("[0-9]+")) {
                operands.push(Integer.parseInt(token));
            } else {
                while (!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
                    int operand2 = operands.pop();
                    int operand1 = operands.pop();
                    String operator = operators.pop();
                    int result = performOperation(operand1, operand2, operator);
                    operands.push(result);
                }
                operators.push(token);
            }
        }
        while (!operators.isEmpty()) {
            int operand2 = operands.pop();
            int operand1 = operands.pop();
            String operator = operators.pop();
            int result = performOperation(operand1, operand2, operator);
            operands.push(result);
        }
        int finalResult = operands.pop();
        return finalResult;
    }
    private static boolean hasPrecedence(String op1, String op2) {
        return (op1.equals("/") || op1.equals("*")) && (op2.equals("+") ||  op2.equals("-"));
    }

    private static int performOperation(int operand1, int operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero!");
                }
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
    private static class MyStack<T> {
        private Node<T> top;

        public void push(T data) {
            Node<T> newNode = new Node<>(data);
            newNode.next = top;
            top = newNode;
        }

        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack if empty!");
            }
            T data = top.data;
            top = top.next;
            return data;
        }

        public T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack if empty!");
            }
            return top.data;
        }

        public boolean isEmpty() {
            return top == null;
        }

        private static class Node<T> {
            T data;
            Node<T> next;
            Node(T data) {
                this.data = data;
            }
        }
    }
}