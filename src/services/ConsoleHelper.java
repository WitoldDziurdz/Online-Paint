package services;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper{
    private ConsoleHelper(){}

    private static BufferedReader reader;

    static {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static String readString()  {
        String string = null;
        try {
            string = reader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while trying to enter text. Try again.");
            string = readString();
        }
        return string;
    }

    public static int readInt(){
        int number = 0;
        try {
            number = Integer.parseInt(readString());
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while trying to enter a number. Try again.");
            number = readInt();
        }
        return number;
    }

    public static void write(String string){
        System.out.println(string);
    }
}
