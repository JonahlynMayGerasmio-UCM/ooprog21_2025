public class CharacterInfo {
    public static void main(String[] args) {

        char aChar = 'C';

        System.out.println("The character is " + aChar);

        if (Character.isUpperCase(aChar))
            System.out.println(aChar + " is uppercase");
        else
            System.out.println(aChar + " is not uppercase");

        if (Character.isLowerCase(aChar))
            System.out.println(aChar + " is lowercase");
        else
            System.out.println(aChar + " is not lowercase");

        System.out.println("After toLowerCase(), aChar is " + Character.toLowerCase(aChar));
        System.out.println("After toUpperCase(), aChar is " + Character.toUpperCase(aChar));

        if (Character.isLetterOrDigit(aChar))
            System.out.println(aChar + " is a letter or digit");
        else
            System.out.println(aChar + " is not a letter or digit");

        if (Character.isWhitespace(aChar))
            System.out.println(aChar + " is whitespace");
        else
            System.out.println(aChar + " is not whitespace");
    }
}
