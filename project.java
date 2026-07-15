
import java.util.Scanner;
import java.util.ArrayList;

public class project {

    static Scanner input = new Scanner(System.in);

    static final double baseFare = 50;
    static final double perKm = 25;

    static final int maxCabs = 20;
    static final int maxPassengers = 20;

    static String[] cabId = new String[maxCabs];
    static String[] driverName = new String[maxCabs];
    static String[] driverPass = new String[maxCabs];

    static String[] carModel = new String[maxCabs];
    static String[] carPlate = new String[maxCabs];
    static String[] carType = new String[maxCabs];

    static double[] rating = new double[maxCabs];
    static boolean[] available = new boolean[maxCabs];
    static int[] trips = new int[maxCabs];
    static double[] earnings = new double[maxCabs];

    static int[][] weekly = new int[maxCabs][7];

    static int cabCount = 0;

    static String[] passengerId = new String[maxPassengers];
    static String[] passengerName = new String[maxPassengers];
    static String[] passengerPass = new String[maxPassengers];

    static int passengerCount = 0;

    static ArrayList<String> history = new ArrayList<>();

    static double revenue = 0;

    static int driverIndex = -1;
    static int passengerIndex = -1;

    public static void main(String[] args) {

        int choice;

        do {

            System.out.println(" Cab Management System ");
            System.out.println("1. Admin");
            System.out.println("2. Driver");
            System.out.println("3. Passenger");
            System.out.println("4. Exit");

            System.out.print("Choose: ");
            choice = getInt();

            switch (choice) {

                case 1:
                    adminMenu();
                    break;

                case 2:
                    driverPortal();
                    break;

                case 3:
                    passengerPortal();
                    break;

                case 4:
                    System.out.println("Program ended.");
                    break;

                default:
                    System.out.println("Wrong choice.");
            }

        } while (choice != 4);
    }

    static void adminMenu() {

        int choice;

        do {

            System.out.println(" Admin Panel ");
            System.out.println("1. Add Cab");
            System.out.println("2. View Cabs");
            System.out.println("3. View Passengers");
            System.out.println("4. Trip History");
            System.out.println("5. Revenue Report");
            System.out.println("6. Sort Drivers");
            System.out.println("7. Search Driver");
            System.out.println("0. Back");

            System.out.print("Choose: ");
            choice = getInt();

            switch (choice) {

                case 1:
                    addCab();
                    break;

                case 2:
                    viewCabs();
                    break;

                case 3:
                    viewPassengers();
                    break;

                case 4:
                    showHistory();
                    break;

                case 5:
                    revenueReport();
                    break;

                case 6:
                    sortDrivers();
                    break;

                case 7:
                    searchDriver();
                    break;
            }

        } while (choice != 0);
    }

    static void addCab() {
        registerDriver();
    }

    static void viewCabs() {

        if (cabCount == 0) {
            System.out.println("No cabs registered.");
            return;
        }

        for (int i = 0; i < cabCount; i++) {

            System.out.println("Cab ID:  " + cabId[i]);
            System.out.println("Driver:  " + driverName[i]);
            System.out.println("Car:     " + carModel[i]);
            System.out.println("Plate:   " + carPlate[i]);
            System.out.println("Type:    " + carType[i]);
            System.out.println("Trips:   " + trips[i]);
            System.out.println("Rating:  " + String.format("%.1f", rating[i]));
            System.out.println("Status:  " + (available[i] ? "Available" : "Unavailable"));
        }
    }

    static void viewPassengers() {

        if (passengerCount == 0) {
            System.out.println("No passengers registered.");
            return;
        }

        for (int i = 0; i < passengerCount; i++) {
            System.out.println(passengerId[i] + " | " + passengerName[i]);
        }
    }

    static void showHistory() {

        if (history.isEmpty()) {
            System.out.println("No trips yet.");
            return;
        }

        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }

    static void revenueReport() {

        System.out.println("Total Revenue: Rs." + String.format("%.2f", revenue));
        System.out.println("Total Trips:   " + history.size());
    }

    static void sortDrivers() {

        for (int i = 0; i < cabCount - 1; i++) {

            for (int j = 0; j < cabCount - i - 1; j++) {

                if (rating[j] < rating[j + 1]) {
                    swap(j, j + 1);
                }
            }
        }

        System.out.println("Drivers sorted by rating (highest first).");
    }

    static void searchDriver() {

        System.out.print("Enter name: ");
        String name = input.nextLine().toLowerCase();

        boolean found = false;

        for (int i = 0; i < cabCount; i++) {

            if (driverName[i].toLowerCase().contains(name)) {
                System.out.println(cabId[i] + " | " + driverName[i] + " | " + carModel[i] + " | Rating: " + String.format("%.1f", rating[i]));
                found = true;
            }
        }

        if (!found)
            System.out.println("No driver found.");
    }

    static void driverPortal() {

        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Back");

        System.out.print("Choose: ");
        int choice = getInt();

        if (choice == 1) {
            registerDriver();
        } else if (choice == 2) {
            if (loginDriver()) {
                driverMenu();
            }
        }
    }

    static void registerDriver() {

        if (cabCount >= maxCabs) {
            System.out.println("Cab limit reached.");
            return;
        }

        System.out.print("Name: ");
        driverName[cabCount] = input.nextLine();

        System.out.print("Password: ");
        driverPass[cabCount] = input.nextLine();

        System.out.print("Car model: ");
        carModel[cabCount] = input.nextLine();

        System.out.print("Car plate: ");
        carPlate[cabCount] = input.nextLine();

        System.out.println("1. Economy  2. Comfort  3. Premium");
        int type = getInt();

        String[] types = {"Economy", "Comfort", "Premium"};

        if (type >= 1 && type <= 3)
            carType[cabCount] = types[type - 1];
        else
            carType[cabCount] = "Economy";

        cabId[cabCount] = "CAB" + (100 + cabCount);

        rating[cabCount] = 5.0;
        available[cabCount] = true;

        cabCount++;

        System.out.println("Registered successfully.");
        System.out.println("Your Cab ID: " + cabId[cabCount - 1]);
    }

    static boolean loginDriver() {

        System.out.print("Cab ID: ");
        String id = input.nextLine();

        System.out.print("Password: ");
        String pass = input.nextLine();

        for (int i = 0; i < cabCount; i++) {

            if (cabId[i].equals(id) && driverPass[i].equals(pass)) {

                driverIndex = i;
                System.out.println("Welcome " + driverName[i]);
                return true;
            }
        }

        System.out.println("Wrong ID or password.");
        return false;
    }

    static void driverMenu() {

        int choice;

        do {

            int i = driverIndex;

            System.out.println(" Driver Menu ");
            System.out.println("1. My Profile");
            System.out.println("2. Change Availability");
            System.out.println("3. My Earnings");
            System.out.println("4. My Weekly Trips");
            System.out.println("0. Logout");

            System.out.print("Choose: ");
            choice = getInt();

            switch (choice) {

                case 1:
                    viewDriver(i);
                    break;

                case 2:
                    available[i] = !available[i];
                    System.out.println("Status: " + (available[i] ? "Available" : "Unavailable"));
                    break;

                case 3:
                    System.out.println("Earnings: Rs." + String.format("%.2f", earnings[i]));
                    break;

                case 4:
                    viewWeekly(i);
                    break;

                case 0:
                    driverIndex = -1;
                    System.out.println("Logged out.");
                    break;
            }

        } while (choice != 0);
    }

    static void viewDriver(int i) {

        System.out.println("Cab ID:  " + cabId[i]);
        System.out.println("Name:    " + driverName[i]);
        System.out.println("Car:     " + carModel[i]);
        System.out.println("Plate:   " + carPlate[i]);
        System.out.println("Type:    " + carType[i]);
        System.out.println("Trips:   " + trips[i]);
        System.out.println("Rating:  " + String.format("%.1f", rating[i]));
        System.out.println("Status:  " + (available[i] ? "Available" : "Unavailable"));
    }

    static void viewWeekly(int i) {

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        System.out.println("Weekly Trips:");
        for (int j = 0; j < 7; j++) {
            System.out.println(days[j] + ": " + weekly[i][j]);
        }
    }

    static void passengerPortal() {

        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Back");

        System.out.print("Choose: ");
        int choice = getInt();

        if (choice == 1) {
            registerPassenger();
        } else if (choice == 2) {
            if (loginPassenger()) {
                passengerMenu();
            }
        }
    }

    static void registerPassenger() {

        if (passengerCount >= maxPassengers) {
            System.out.println("Passenger limit reached.");
            return;
        }

        System.out.print("Name: ");
        passengerName[passengerCount] = input.nextLine();

        System.out.print("Password: ");
        passengerPass[passengerCount] = input.nextLine();

        passengerId[passengerCount] = "PAS" + (200 + passengerCount);

        passengerCount++;

        System.out.println("Registered successfully.");
        System.out.println("Your Passenger ID: " + passengerId[passengerCount - 1]);
    }

    static boolean loginPassenger() {

        System.out.print("Passenger ID: ");
        String id = input.nextLine();

        System.out.print("Password: ");
        String pass = input.nextLine();

        for (int i = 0; i < passengerCount; i++) {

            if (passengerId[i].equals(id) && passengerPass[i].equals(pass)) {

                passengerIndex = i;
                System.out.println("Welcome " + passengerName[i]);
                return true;
            }
        }

        System.out.println("Wrong ID or password.");
        return false;
    }

    static void passengerMenu() {

        int choice;

        do {

            System.out.println(" Passenger Menu ");
            System.out.println("1. Book Ride");
            System.out.println("2. My History");
            System.out.println("0. Logout");

            System.out.print("Choose: ");
            choice = getInt();

            switch (choice) {

                case 1:
                    bookRide();
                    break;

                case 2:
                    passengerHistory();
                    break;

                case 0:
                    passengerIndex = -1;
                    System.out.println("Logged out.");
                    break;
            }

        } while (choice != 0);
    }

    static void bookRide() {

        if (countAvailable() == 0) {
            System.out.println("No cabs available.");
            return;
        }

        String pid  = passengerId[passengerIndex];
        String name = passengerName[passengerIndex];

        System.out.print("Pickup: ");
        String pickup = input.nextLine();

        System.out.print("Drop: ");
        String drop = input.nextLine();

        System.out.print("Distance in km: ");
        double distance = getDouble();

        System.out.println("1. Economy  2. Comfort  3. Premium");
        int type = getInt();

        String chosen;

        if (type == 2)
            chosen = "Comfort";
        else if (type == 3)
            chosen = "Premium";
        else
            chosen = "Economy";

        int cab = findCab(chosen);

        if (cab == -1)
            cab = findAvailable();

        if (cab == -1) {
            System.out.println("No cab available.");
            return;
        }

        double multi = 1.0;

        if (chosen.equals("Comfort"))
            multi = 1.3;
        else if (chosen.equals("Premium"))
            multi = 1.7;

        double fare = (baseFare + distance * perKm) * multi;

        
        System.out.println("Driver: " + driverName[cab]);
        System.out.println("Car:    " + carModel[cab] + " (" + carType[cab] + ")");
        System.out.println("Fare:   Rs." + String.format("%.2f", fare));
        System.out.print("Confirm booking? (1=Yes / 0=No): ");
        int confirm = getInt();

        if (confirm != 1) {
            System.out.println("Booking cancelled.");
            return;
        }

        
        available[cab] = false;

        trips[cab]++;
        revenue += fare;
        earnings[cab] += fare * 0.8;

        
        System.out.print("Enter day (1=Mon, 2=Tue, 3=Wed, 4=Thu, 5=Fri, 6=Sat, 7=Sun): ");
        int day = getInt();
        if (day >= 1 && day <= 7) {
            weekly[cab][day - 1]++;
        }

        
        String trip = pid + " | " + name + " | " +
                      pickup + " -> " + drop +
                      " | Driver: " + driverName[cab] +
                      " | Fare: Rs." + String.format("%.2f", fare);

        history.add(trip);

        System.out.println("\nRide booked successfully.");

        System.out.print("Rate ride (1-5): ");
        int r = getInt();

        if (r >= 1 && r <= 5) {
            
            rating[cab] = ((rating[cab] * (trips[cab] - 1)) + r) / trips[cab];
        }
    }

    static void passengerHistory() {

        String pid = passengerId[passengerIndex];
        boolean found = false;

        for (int i = 0; i < history.size(); i++) {

            if (history.get(i).startsWith(pid + " | ")) {
                System.out.println(history.get(i));
                found = true;
            }
        }

        if (!found)
            System.out.println("No rides found.");
    }

    static int findAvailable() {

        for (int i = 0; i < cabCount; i++) {

            if (available[i])
                return i;
        }

        return -1;
    }

    static int findCab(String type) {

        for (int i = 0; i < cabCount; i++) {

            if (available[i] && carType[i].equals(type))
                return i;
        }

        return -1;
    }

    static int countAvailable() {

        int c = 0;

        for (int i = 0; i < cabCount; i++) {

            if (available[i])
                c++;
        }

        return c;
    }

    static void swap(int a, int b) {

        String s;
        double d;
        boolean bool;
        int n;
        int[] w;

        s = cabId[a];        cabId[a] = cabId[b];        cabId[b] = s;
        s = driverName[a];   driverName[a] = driverName[b];   driverName[b] = s;
        s = driverPass[a];   driverPass[a] = driverPass[b];   driverPass[b] = s;
        s = carModel[a];     carModel[a] = carModel[b];     carModel[b] = s;
        s = carPlate[a];     carPlate[a] = carPlate[b];     carPlate[b] = s;
        s = carType[a];      carType[a] = carType[b];      carType[b] = s;

        d = rating[a];       rating[a] = rating[b];       rating[b] = d;
        d = earnings[a];     earnings[a] = earnings[b];     earnings[b] = d;

        bool = available[a]; available[a] = available[b]; available[b] = bool;

        n = trips[a];        trips[a] = trips[b];        trips[b] = n;

        w = weekly[a];       weekly[a] = weekly[b];       weekly[b] = w;
    }

    static int getInt() {

        while (true) {

            try {
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.print("Enter a number: ");
            }
        }
    }

    static double getDouble() {

        while (true) {

            try {
                return Double.parseDouble(input.nextLine());
            } catch (Exception e) {
                System.out.print("Enter a number: ");
            }
        }
    }
}

