public class Utils {

    /**
     * @param in    the desired String to split
     * @param delim the delimeter to split at
     * @return split array
     */
    public static String[] split(String in, char delim) {

        int cnt = 1;
        String ret[];

        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == delim) cnt++;
        }

        ret = new String[cnt];

        String current = "";
        cnt = 0;

        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) != delim) {
                current += in.charAt(i);
            } else {
                ret[cnt++] = current;
                current = "";
            }
        }

        ret[cnt] = current;
        return ret;
    }
}