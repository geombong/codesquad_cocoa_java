package boj;

import java.util.Scanner;

public class Boj2753 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int year = scan.nextInt();
        System.out.println(year % 4 == 0 && year % 100 != 0 ? "1" : year % 400 == 0 ? "1" : "0");
    }
}
