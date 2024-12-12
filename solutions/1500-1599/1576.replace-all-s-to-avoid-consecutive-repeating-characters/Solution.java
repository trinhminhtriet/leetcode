class Solution {
    public String modifyString(String s) {
        // Convert the string to a char array to modify characters in place.
        char[] charArray = s.toCharArray();
        int length = charArray.length;

        // Iterate over each character in the char array.
        for (int i = 0; i < length; ++i) {
            // Check if the current character is a question mark.
            if (charArray[i] == '?') {
                // Try replacing '?' with 'a', 'b', or 'c'.
                for (char c = 'a'; c <= 'c'; ++c) {
                    // Check if the same character is present on the left or right.
                    // Make sure not to go out of bounds by checking the index.
                    if ((i > 0 && charArray[i - 1] == c) || (i + 1 < length && charArray[i + 1] == c)) {
                        // If the same character is on either side, continue to the next character.
                        continue;
                    }
                    // Assign the character that doesn't match its neighbors.
                    charArray[i] = c;
                    // Once we have found a suitable character, break the inner loop.
                    break;
                }
            }
        }
        // Convert the char array back to a string and return it.
        return String.valueOf(charArray);
    }
}