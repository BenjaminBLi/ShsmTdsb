import java.util.HashMap;

/**
 * Class that stores all of the information given for each postal code:
 *      - Postal Code
 *      - Place Name
 *      - Province
 *      - Latitude
 *      - Longitude
 *
 * Provides a checking method to validate each postal code as well
 */
public class PostalCode {
    private String format[] = null;
    private String values[] = null;
    private int positionOfPostalCode = -1;

    public PostalCode() {
        this.format = null;
        this.values = null;
    }

    /**
     * @param format     given format of each postal code
     * @param postalCode the postal code
     */
    public PostalCode(String format[], String postalCode) {
        this.format = new String[format.length];
        for (int i = 0; i < format.length; i++) {
            this.format[i] = format[i];
            if (format[i].equals("Postal Code")) this.positionOfPostalCode = i;
        }

        values = new String[format.length];


        this.values[positionOfPostalCode] = postalCode;
    }


    /**
     * @param format the format of the postal code: contains the names of the values
     * @param values the corresponding values to the format
     */

    public PostalCode(String format[], String values[]) {
        this.format = new String[format.length];
        for (int i = 0; i < format.length; i++) {
            this.format[i] = format[i];
            if (format[i].equals("Postal Code")) this.positionOfPostalCode = i;
        }

        this.values = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            this.values[i] = values[i];
        }
    }

    /**
     * returns the format of a postal code as an array
     *
     * @return the format as a string array
     */
    public String[] getFormat() {
        return format;
    }

    /**
     * returns the values of the postal code, in the format of the format array
     *
     * @return the values of the postal code
     */
    public String[] getValues() {
        return values;
    }

    /**
     * returns where the postal code is stored in the value array
     *
     * @return the position of the postal code as an int
     */
    public int getPositionOfPostalCode() {
        return positionOfPostalCode;
    }

    /**
     * Sets the format array to the input array format
     *
     * @param format the new desired format as a string array
     */
    public void setFormat(String[] format) {
        this.format = format;
    }

    /**
     * sets the position of the postal code
     *
     * @param positionOfPostalCode the desired location as an int
     */
    public void setPositionOfPostalCode(int positionOfPostalCode) {
        this.positionOfPostalCode = positionOfPostalCode;
    }

    /**
     * sets the values of the PostalCode object
     *
     * @param values the new desired values as a string array
     */
    public void setValues(String[] values) {
        this.values = values;
    }

    /**
     * validates the current postal code and fills in missing information if not provided
     *
     * @param validCodes the possible valid postal codes
     * @return returns a boolean checking if the PostalCode is valid
     */

    public boolean validate(HashMap<String, PostalCode> validCodes) {
        boolean valid = false;

        for (String validCode : validCodes.keySet()) {
            if (validCode.equals(values[this.positionOfPostalCode].substring(0, 3))) {
                valid = true;

                //fill in remaining blanks if not given by the user
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == null) {
                        values[i] = validCodes.get(validCode).getValues()[i];
                    }
                }

            }

        }

        return valid;
    }

    @Override
    public String toString() {

        String ret = "";
        for (int i = 0; i < values.length; i++) {
            if (i != 0) ret += ",";
            ret += values[i];
        }

        return ret;
    }
}
