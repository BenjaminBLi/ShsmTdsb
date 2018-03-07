import java.util.HashMap;

/**
 * A class implemented in order to provide additional functionality to each "customer"
 * Contains the necessary information for each customer: name, city, postal code, and credit card number
 *
 */
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private PostalCode postalCode;
    private String creditCardNumber;

    /**
     * Default constructor of the customer object
     */
    public Customer() {

        this.id = -1;
        this.firstName = "";
        this.lastName = "";
        this.city = "";
        this.postalCode = null;
        this.creditCardNumber = "";
    }

    /**
     * constructor for the Customer object
     *
     * @param firstName        the first name of the customer
     * @param lastName         the last name of the customer
     * @param city             the city the customer lives in
     * @param postalCode       the postal code of the the customer
     * @param creditCardNumber the credit card of the customer
     */
    public Customer(int id, String firstName, String lastName, String city, String format[], String postalCode, String creditCardNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.postalCode = new PostalCode(format, postalCode);
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * checks the validity of the postal code
     *
     * @return true if the postal code is valid, false if it isn't
     */
    public boolean validatePostalCode(HashMap<String, PostalCode> validPostalCodes) {
        return postalCode.validate(validPostalCodes);
    }

    /**
     * returns the sum of the digits
     *
     * @param val desired number
     * @return the sum of the digits
     */
    public static int digitSum(int val) {

        int ret = 0;
        while (val > 0) {
            ret += val % 10;
            val /= 10;
        }

        return ret;
    }

    /**
     * check the validity of the credit card using the Lunh Algorithm
     *
     * @return true if the credit card is valid, false if it isn't
     */
    public boolean validateCreditCard() {
        boolean valid = true;

        //check if the card length is valid
        if (creditCardNumber.length() < 9) {
            valid = false;
        }

        for (char c : creditCardNumber.toCharArray()) {
            if ('0' > c || '9' < c) {
                valid = false;
            }
        }

        int sum1 = 0;

        for (int i = creditCardNumber.length() - 1; valid && i >= 0; i -= 2) {
            sum1 += Integer.parseInt("" + creditCardNumber.charAt(i));
        }

        int sum2 = 0;
        for (int i = creditCardNumber.length() - 2; valid && i >= 0; i -= 2) {
            sum2 += digitSum(Integer.parseInt("" + creditCardNumber.charAt(i)) * 2);
        }

        if ((sum1 + sum2) % 10 != 0) {
            valid = false;
        }

        return valid;
    }

    /**
     * returns the postal code
     *
     * @return customer's postal code
     */
    public PostalCode getPostalCode() {
        return postalCode;
    }

    /**
     * returns the customer's city
     *
     * @return the customer's city
     */

    public String getCity() {
        return city;
    }

    /**
     * returns the customer's credit card number
     *
     * @return the credit card number as a string
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * returns the first name of the customer
     *
     * @return the customer's first name as a string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * returns the last name of the customer
     *
     * @return the customer's last name as a string
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the customer's city as a string
     *
     * @param city desired city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * sets the customer's credit card number
     *
     * @param creditCardNumber the desired credit card number as a string
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * sets the customer's first name
     *
     * @param firstName the desired first name as a string
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * sets the customer's last name
     *
     * @param lastName the desired last name as a string
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * sets the customer's postal code
     *
     * @param postalCode the desired postal code as a PostalCode object
     */
    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        String ret = id + ",";
        ret += (this.lastName + "," + this.firstName);
        ret += ("," + this.city);
        ret += ("," + this.postalCode);
        ret += ("," + this.creditCardNumber);
        return ret;
    }
}

