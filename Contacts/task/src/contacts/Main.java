package contacts;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        String pathToFile = args[0];
//        try {
//            Contacts.readFileAsString(pathToFile);
//        } catch (IOException e) {
//            System.out.println("Cannot read file: " + e.getMessage());
//        }
        while (true) {
            Contacts.printMenu();
            System.out.println();
        }
    }
}

