package org.example.compiler.brainfuck;

import java.util.Scanner;

/**
 * Brainfuck 语言解析器
 */
public class BrainfuckRun {

    char[] code = new char[1000];
    int codeP = 0;

    int[] stack = new int[1000];
    int stackP = 0;

    char[] data = new char[1000];
    int dataP = 0;

    public void setup(String sourceCode) {
        char[] chars = sourceCode.toCharArray();
        System.arraycopy(chars, 0, code, 0, chars.length);
    }

    public void run() {
        while (code[codeP] != 0) {
            switch (code[codeP]) {
                case '+':
                    data[dataP]++;
                    break;
                case '-':
                    data[dataP]--;
                    break;
                case '>':
                    dataP = (dataP + 1) % 1000;
                    break;
                case '<':
                    dataP = (dataP - 1) % 1000;
                    break;
                case '.':
                    System.out.print(data[dataP]);
                    break;
                case ',':
                    Scanner scanner = new Scanner(System.in);
                    String next = scanner.next();
                    data[dataP] = next.toCharArray()[0];
                    break;
                case '[':
                    if (data[dataP] != 0) {
                        stack[stackP] = codeP - 1;
                        stackP++;
                    } else {
                        int c = 1;
                        for (codeP++; code[codeP] != 0 && c != 0; codeP++) {
                            if (code[codeP] == '[') {
                                c++;
                            } else if (code[codeP] == ']') {
                                c--;
                            }
                        }
                    }
                    break;
                case ']':
                    if (data[dataP] != 0) {
                        stackP--;
                        codeP = stack[stackP];
                    }
                    break;
                default:
                    break;
            }
            codeP++;
        }
    }


    public static void main(String[] args) {
        BrainfuckRun run = new BrainfuckRun();
        run.setup("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.");
//        run.setup(",[.,]");
        run.run();
    }
}
