package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Person extends Contacts {
    private String name;
    private String phoneNumber;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastEdit;
    private String birthDate;
    private String gender;
    int editCount;

    public Person(String name, String birthDate, String gender, String phoneNumber, LocalDateTime timeCreated) {
        super(name, phoneNumber, timeCreated);
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.timeCreated = timeCreated;
        this.timeLastEdit = timeCreated;
        this.editCount = 0;
    }

    @Override
    public boolean hasNumber(String name) {
        String phoneNumber = String.valueOf(Contacts.getContactsRecord()
                .stream()
                .filter(person -> (name.equals(person.getName())))
                .findFirst()
                .orElse(null));
        return phoneNumber != null;
    }

    public String getFirstName() {
        return name.split(" ")[0];
    }

    public void setFirstName(String firstName) {
        this.name = firstName + " " + name.split(" ")[1];
    }

    public String getLastName() {
        return name.split(" ")[1];
    }

    public void setLastName(String lastName) {
        this.name = name.split(" ")[0] + " " + lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String newBirthDate) {
        this.birthDate = newBirthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String newGender) {
        this.gender = newGender;
    }

    public static boolean checkBirthDate(String birthDate) {
        if (birthDate == null) {
            return false;
        } else {
            String regex = "(19[0-9]{2}|20([0-1][0-9]|2[0-1]))-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(birthDate);
            return matcher.matches();
        }
    }

    public static boolean checkGender(String gender) {
        return gender.equals("M") || gender.equals("F");
    }
    
    public static void addRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter the surname: ");
        String lastName = scanner.nextLine();
        String name = firstName + " " + lastName;
        System.out.println("Enter the birth date: ");
        String birthDate = scanner.nextLine();
        if (!checkBirthDate(birthDate)) {
            System.out.println("Bad birth date!");
        }
        System.out.println("Enter the gender (M, F): ");
        String gender = scanner.nextLine();
        if (!checkGender(gender)) {
            System.out.println("Bad gender!");
        }
        System.out.println("Enter the number: ");
        String phoneNumber = scanner.nextLine();
        if (!checkPhoneNumber(phoneNumber)) {
            System.out.println("Wrong number format!");
        }
        LocalDateTime timeCreated = LocalDateTime.now().withSecond(0).withNano(0);
        Person person = new Person(name, birthDate, gender, phoneNumber, timeCreated);
        Contacts.addToContactsRecord(person);
        if (!checkPhoneNumber(phoneNumber)) {
            person.setPhoneNumber("[no data]");
        }
        if (!checkBirthDate(birthDate)) {
            person.setBirthDate("[no data]");
        }
        if (!checkGender(gender)) {
            person.setGender("[no data]");
        }
    }

    public static void editRecord(Person person, String field) {
        Scanner scanner = new Scanner(System.in);
        switch (field) {
            case "name":
                System.out.println("Enter name: ");
                String firstName = scanner.nextLine();
                person.setFirstName(firstName);
                break;
            case "surname":
                System.out.println("Enter surname: ");
                String lastName = scanner.nextLine();
                person.setLastName(lastName);
                break;
            case "birth":
                System.out.println("Enter birth date: ");
                String birthDate = scanner.nextLine();
                if (!checkBirthDate(birthDate)) {
                    System.out.println("Bad birth date!");
                    person.setBirthDate("[no data]");
                } else {
                    person.setBirthDate(birthDate);
                }
                break;
            case "gender":
                System.out.println("Enter gender: ");
                String gender = scanner.nextLine();
                if (!checkGender(gender)) {
                    System.out.println("Bad gender!");
                    person.setGender("[no data]");
                } else {
                    person.setGender(gender);
                }
                break;
            case "number":
                System.out.println("Enter number: ");
                String phoneNumber = scanner.nextLine();
                if (!checkPhoneNumber(phoneNumber)) {
                    System.out.println("Bad number!");
                    person.setPhoneNumber("[no data]");
                } else {
                    person.setPhoneNumber(phoneNumber);
                }
                break;
        }
        LocalDateTime timeLastEdit = LocalDateTime.now().withSecond(0).withNano(0);
        person.setTimeLastEdit(timeLastEdit);
    }

    public static void provideInfo(Person person) {
        System.out.println(String.format("Name: %s", person.getFirstName()));
        System.out.println(String.format("Surname: %s", person.getLastName()));
        System.out.println(String.format("Birth date: %s", person.getBirthDate()));
        System.out.println(String.format("Gender: %s", person.getGender()));
        System.out.println(String.format("Number: %s", person.getPhoneNumber()));
        System.out.println(String.format("Time created: %s", person.getTimeCreated()));
        System.out.println(String.format("Time last edit: %s", person.getTimeLastEdit()));
    }
}
