import javax.security.auth.login.CredentialException;
import java.io.*;
import java.util.*;

/**
 * Terminal interface for front-end customers:
 * Provides the options to:
 *      1. Enter Customer information
 *      2. Generate Customer data file in the form of a csv
 *      3. Report on total sales
 *      4. Check for sales fraud
 *
 * TODO: sales check, generate csv, generalize csv parsing
 */

public class Interface {

    private static HashMap<String, PostalCode> validCodeToData;
    private static HashMap<String, Long> sales;
    private static ArrayList<Customer> customers;
    private static int customerCount = 1;
    private static String[] format = {"First Name,last Name,City,Postal Code,Credit Card Number"};

    /**
     * parses all the valid postal codes into:
     * - format of the postal coes as format
     * - each postal code as a key, value pair: (postal code, PostalCode object)
     */

    public static void parseValidCodes() {
        try {

            validCodeToData = new HashMap<>();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("/home/neb/IdeaProjects/ShsmTDSB/src/postal_codes.csv")));
            String line = in.readLine();
            format = Utils.split(line, '|');

            int positionOfPostalCode = -1;

            for (int i = 0; i < format.length && positionOfPostalCode == -1; i++) {
                if (format[i].equals("Postal Code")) positionOfPostalCode = i;
            }

            do {

                line = in.readLine();
                String values[] = Utils.split(line, '|');
                validCodeToData.put(values[positionOfPostalCode], new PostalCode(format, values));

            } while (in.ready() && !line.equals(""));

            in.close();

        } catch (Exception e) {

            System.out.println(e);
            System.out.println("error parsing valid postal codes");

        }
    }

    /**
     * Parses the sales from sales.csv into the hashmap Sales as a (String, Integer) pair
     */
    public static void parseSales() {
        try {

            sales = new HashMap<>();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("/home/neb/IdeaProjects/ShsmTDSB/src/sales.csv")));
            String line;
            in.readLine();

            do {

                line = in.readLine();
                String values[] = Utils.split(line, ',');
                sales.put(values[0], Long.parseLong(values[1]));

            } while (in.ready() && !line.equals(""));

            in.close();

        } catch (Exception e) {

            System.out.println("error parsing sales file");

        }

    }


    /**
     * adds a new customer to the database
     *
     * @param in the input stream used to get user input
     */
    public static void addCustomer(BufferedReader in) throws Exception {

        try {

            boolean ok = true;

            System.out.println("Adding customer to database: ");

            System.out.print("First name: ");
            String firstName = in.readLine();

            System.out.print("Last name: ");
            String lastName = in.readLine();

            System.out.print("City: ");
            String city = in.readLine();

            System.out.print("Postal Code: ");
            String postalCode = in.readLine();

            System.out.print("Credit Card Number: ");
            String creditCardNumber = in.readLine();

            Customer customer = new Customer(customerCount, firstName, lastName, city, format,
                    postalCode, creditCardNumber);

            if (!customer.validatePostalCode(validCodeToData)) {

                ok = false;
                System.out.println("Invalid postal code. Check customer info and try again");

            }

            if (!customer.validateCreditCard()) {

                System.out.println("Invalid credit card value, please enter a valid one");
                ok = false;

            }

            if (ok) {

                System.out.println("Customer valid. Added to the database");
                customers.add(customer);

                customerCount++;
            }

        } catch (Exception e) {
            System.out.println("error adding customer");
        }
    }


    /**
     * prints the first format for the customer CSV file
     *
     * @param out the desired output stream
     */
    private static void printCustomerFormat(PrintWriter out) {

        out.print("Customer Id,First name,Last name,City,");
        for (int i = 0; i < format.length; i++) {
            if (i != 0) out.print(",");
            out.print(format[i]);
        }
        out.println("Credit Card Number");

    }


    /**
     * used to generate the customer info file
     *
     * @param in input stream for user input
     */
    private static void generateCustomerFile(BufferedReader in) {
        try {

            System.out.println("Generating Customer file...");

            System.out.print("Enter file location:");
            String location = in.readLine();

            System.out.print("Enter file name:");
            String fileName = in.readLine();

            PrintWriter out = new PrintWriter(new FileOutputStream(new File(location + fileName + ".csv")));

            printCustomerFormat(out);

            for (Customer customer : customers) {
                out.println(customer);
            }
            out.close();

            System.out.println("File successfully written.");

        } catch (Exception e) {

            System.out.println("error generating customer file");

        }
    }

    /**
     * checks the sales hashmap and sums up to get the total sales
     */
    public static void reportTotalSales() {

        long total = 0;
        for (String key : sales.keySet()) {
            total += sales.get(key);
        }

        System.out.println("The total sales amounts to: " + total);
    }

    /**
     * checks for sales fraud in the sales hashmap using Benford's law
     */
    public static void checkSalesFraud() {
        int freq[] = new int[10];

        for (long val : sales.values()) {
            int firstDigit = Long.toString(val).charAt(0) - '0';
            freq[firstDigit]++;
        }

        double firstDigitFrequency = 100.0 * freq[1] / sales.size();
        if (29 <= firstDigitFrequency && firstDigitFrequency <= 32) {
            System.out.println("sales fraud is not likely");
        } else {
            System.out.println("Sales fraud is likely, further investigation is recommended");
        }
    }

    /**
     * prints the UI commands
     */
    private static void printCommands() {
        System.out.println("Customer Sales Terminal UI:");
        System.out.println("1. Enter Customer Information");
        System.out.println("2. Generate Customer Data File");
        System.out.println("3. Report on Total Sales");
        System.out.println("4. Check for fraud in sales data");
        System.out.println("Enter 'q' to quit");
    }

    /**
     * Initializes the interface before running:
     * - generates valid postal codes
     * - reads in sales for future use
     */
    public static void run() throws Exception {

        parseValidCodes();
        parseSales();
        System.out.println(Arrays.toString(format));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String cmd = "";
        customers = new ArrayList<>();

        do {
            printCommands();
            try {
                cmd = in.readLine().trim().substring(0, 1);
                switch (cmd) {
                    case "1":
                        addCustomer(in);
                        break;

                    case "2":
                        generateCustomerFile(in);
                        break;

                    case "3":
                        reportTotalSales();
                        break;

                    case "4":
                        checkSalesFraud();
                        break;

                    case "q":
                        break;

                    default:
                        System.out.println("Please enter a valid option.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("error with user input, please try again");
            }

        } while (!cmd.equals("q"));
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
