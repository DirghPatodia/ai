import java.util.*;

public class CryptArith {

    public static boolean solveCryptarithmetic(String s1, String s2, String s3) {
        Set<Character> charSet = new HashSet<>();
        for (char c : s1.toCharArray())
            charSet.add(c);
        for (char c : s2.toCharArray())
            charSet.add(c);
        for (char c : s3.toCharArray())
            charSet.add(c);

        if (charSet.size() > 10) {
            return false;
        }

        List<Character> charList = new ArrayList<>(charSet);
        return solveCryptarithmeticHelper(s1, s2, s3, charList, 0, new HashMap<>());
    }

    private static boolean solveCryptarithmeticHelper(String s1, String s2, String s3,
                                                      List<Character> charList, int index,
                                                      Map<Character, Integer> charToDigit) {

        if (index == charList.size()) {
            int num1 = convertToNumber(s1, charToDigit);
            int num2 = convertToNumber(s2, charToDigit);
            int num3 = convertToNumber(s3, charToDigit);

            return num1 + num2 == num3;
        }

        char currentChar = charList.get(index);
        if (charToDigit.containsKey(currentChar)) {
            return solveCryptarithmeticHelper(s1, s2, s3, charList, index + 1, charToDigit);
        }

        for (int digit = 0; digit <= 9; digit++) {
            if (!charToDigit.containsValue(digit)) {
                charToDigit.put(currentChar, digit);
                if (solveCryptarithmeticHelper(s1, s2, s3, charList, index + 1, charToDigit)) {
                    return true;
                }
                charToDigit.remove(currentChar);
            }
        }
        return false;
    }

    private static int convertToNumber(String s, Map<Character, Integer> charToDigit) {
        int number = 0;
        for (char c : s.toCharArray()) {
            number = number * 10 + charToDigit.get(c);
        }
        return number;
    }

    // private static int convertToNumber(String s, Map<Character, Integer> charToDigit) {
    //     int number = 0;
    //     for (int i = 0; i < s.length(); i++) {
    //         char c = s.charAt(i);
    //         int digit = charToDigit.get(c);
    //         number = number * 10 + digit;
    //     }
    //     return number;
    // }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first word (s1): ");
        String s1 = scanner.nextLine();

        System.out.print("Enter the second word (s2): ");
        String s2 = scanner.nextLine();

        System.out.print("Enter the third word (s3): ");
        String s3 = scanner.nextLine();

        if (solveCryptarithmetic(s1, s2, s3)) {
            System.out.println("Solution found!");
        } else {
            System.out.println("No solution exists.");
        }
    }
}