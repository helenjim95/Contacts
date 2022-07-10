package contacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


abstract class Contacts {
    private String name;
    private String phoneNumber;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastEdit;
    private static List<Contacts> contactsRecord = new ArrayList<>();
    public Contacts(String name, String phoneNumber, LocalDateTime timeCreated) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.timeCreated = timeCreated;
        this.timeLastEdit = timeCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public boolean hasNumber(String name) {
        String phoneNumber = String.valueOf(contactsRecord
                .stream()
                .filter(contact -> (name.equals(contact.getName())))
                .findFirst()
                .orElse(null));
        return phoneNumber != null;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    static boolean checkPhoneNumber(String phoneNumber) {

        String regex = "^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) { this.timeCreated = timeCreated; }

    public LocalDateTime getTimeLastEdit() {
        return timeLastEdit;
    }

    public void setTimeLastEdit(LocalDateTime timeLastEdit) { this.timeLastEdit = timeLastEdit; }

    public static List<Contacts> getContactsRecord() {
        return contactsRecord;
    }

    public static void addToContactsRecord(Contacts contact) {
        contactsRecord.add(contact);
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[menu] Enter action (add, list, search, count, exit): ");
        String action = scanner.nextLine();
        switch (action) {
            case "add":
                addRecord();
                break;
            case "list":
                provideInfo();
                break;
            case "search":
                searchRecord();
                break;
            case "count":
                countRecord();
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }

    static void addRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the type (person, organization): ");
        String type = scanner.nextLine();
        if (type.equals("person")) {
            Person.addRecord();
        } else {
            Organization.addRecord();
        }
    }

    static void removeRecord() {
        if (contactsRecord.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            listRecord();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select a record: ");
            int record = scanner.nextInt();
            Contacts contact = contactsRecord.get(record - 1);
            contactsRecord.remove(record - 1);
            System.out.println("The record removed!");
        }
    }

    static void editRecord(Contacts contact) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, surname, birth, gender, number): ");
        String field = scanner.nextLine();
        if (contact instanceof Person) {
            Person person = (Person) contact;
            Person.editRecord(person, field);
        } else {
            Organization organization = (Organization) contact;
            Organization.editRecord(organization, field);
            }
        System.out.println("The record updated!");
    }

    static void searchRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter search query: ");
        String searchQuery = scanner.nextLine().toLowerCase();
        List<String> foundRecords = new ArrayList<>();
        for (Contacts contact : contactsRecord) {
            if (contact.name.toLowerCase().contains(searchQuery)) {
                foundRecords.add(contact.name);
            } else if (contact.phoneNumber.contains(searchQuery)) {
                foundRecords.add(contact.phoneNumber);
            }
        }
        System.out.printf("Found %d results:", foundRecords.size());
        System.out.println();
        for (String record : foundRecords) {
            System.out.printf("%d %s", foundRecords.indexOf(record), record);
        }
        System.out.println();
        System.out.println("[search] Enter action ([number], back, again): ");
        String action = scanner.nextLine();
        switch (action) {
            case "back":
                break;
            case "again":
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                int index = Integer.parseInt(action);
                System.out.println("[record] Enter action (edit, delete, menu): ");
                String actionRecord = scanner.nextLine();
                switch (actionRecord) {
                    case "edit":
                        editRecord(contactsRecord.get(index - 1));
                        System.out.println();
                        break;
                    case "delete":
                        removeRecord();
                        System.out.println();
                        break;
                    case "menu":
                        printMenu();
                        System.out.println();
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                }
        }
    }

    static void countRecord(){
        int count = getContactsRecord().size();
        System.out.println(String.format("The Phone Book has %d records.", count));
    };

    static void listRecord(){
        for (int i = 0; i < contactsRecord.size(); i++) {
            Contacts contact = contactsRecord.get(i);
            System.out.println(String.format("%d. %s", i + 1, contact.getName()));
        }
        System.out.println();
    }

    static void provideInfo() {
        if (contactsRecord.isEmpty()) {
        System.out.println("No records to provide!");
        } else {
            listRecord();
            Scanner scanner = new Scanner(System.in);
            int index = 0;
            System.out.println("[list] Enter action ([number], back): ");
            String action = scanner.nextLine();
            if (action.equals("back")) {
                printMenu();
            } else if (action.equals("exit")) {
                System.exit(0);
            } else {
                index = Integer.parseInt(action);
                Contacts contact = contactsRecord.get(index - 1);
                if (contact instanceof Person) {
                    Person person = (Person) contact;
                    person.provideInfo(person);
                } else {
                    Organization organization = (Organization) contact;
                    organization.provideInfo(organization);
                }
                System.out.println("[record] Enter action (edit, delete, menu): ");
                String actionRecord = scanner.nextLine();
                switch (actionRecord) {
                    case "edit":
                        editRecord(contactsRecord.get(index - 1));
                        break;
                    case "delete":
                        removeRecord();
                        break;
                    case "menu":
                        printMenu();
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                }
            }
        }
        System.out.println();
    }
}