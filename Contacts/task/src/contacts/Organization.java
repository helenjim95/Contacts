package contacts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Organization extends Contacts {
    private String name;
    private String phoneNumber;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastEdit;
    private String address;
    int editCount;

    public Organization(String name, String address, String phoneNumber, LocalDateTime timeCreated) {
        super(name, phoneNumber, timeCreated);
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.timeCreated = timeCreated;
        this.timeLastEdit = timeCreated;
        this.editCount = 0;
    }

    @Override
    public boolean hasNumber(String name) {
        String phoneNumber = String.valueOf(Contacts.getContactsRecord()
                .stream()
                .filter(organization -> (name.equals(organization.getName())))
                .findFirst()
                .orElse(null));
        return phoneNumber != null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String newAddress) {
        this.address = newAddress;
    }

    static void addRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the organization name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the address: ");
        String address = scanner.nextLine();
        if (address.isEmpty()) {
            System.out.println("Bad address!");
        }
        System.out.println("Enter the number: ");
        String phoneNumber = scanner.nextLine();
        if (!checkPhoneNumber(phoneNumber)) {
            System.out.println("Wrong number format!");
        }
        LocalDateTime timeCreated = LocalDateTime.now().withSecond(0).withNano(0);
        Organization organization = new Organization(name, address, phoneNumber, timeCreated);
        Contacts.addToContactsRecord(organization);
        if (!checkPhoneNumber(phoneNumber)) {
            organization.setPhoneNumber("[no data]");
        }
        if (address.isEmpty()) {
            organization.setAddress("[no data]");
        }
    }


    public static void editRecord(Organization organization, String field) {
        Scanner scanner = new Scanner(System.in);
        switch (field) {
            case "name":
                System.out.println("Enter name: ");
                String name = scanner.nextLine();
                organization.setName(name);
                break;
            case "address":
                System.out.println("Enter address: ");
                String address = scanner.nextLine();
                if (address.isEmpty()) {
                    System.out.println("Bad address!");
                    organization.setAddress("[no data]");
                } else {
                    organization.setAddress(address);
                }
                break;
            case "number":
                System.out.println("Enter number: ");
                String phoneNumber = scanner.nextLine();
                if (!checkPhoneNumber(phoneNumber)) {
                    System.out.println("Bad number!");
                    organization.setPhoneNumber("[no data]");
                } else {
                    organization.setPhoneNumber(phoneNumber);
                }
                break;
        }
        LocalDateTime timeLastEdit = LocalDateTime.now().withSecond(0).withNano(0);
        organization.setTimeLastEdit(timeLastEdit);
    }

    public static void provideInfo(Organization organization) {
        System.out.println(String.format("Organization name: %s", organization.getName()));
        System.out.println(String.format("Address: %s", organization.getAddress()));
        System.out.println(String.format("Number: %s", organization.getPhoneNumber()));
        System.out.println(String.format("Time created: %s", organization.getTimeCreated()));
        System.out.println(String.format("Time last edit: %s", organization.getTimeLastEdit()));
    }
}
