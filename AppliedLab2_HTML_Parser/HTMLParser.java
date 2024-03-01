/**
 * The HTML Parser class definition
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Lab 9.1
 */

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class HTMLParser {
	
	/**
     * Main method that drives the program. It prompts the user for a file name, reads the file, and checks for matching tags.
     * 
     * @param args the command-line arguments (not used).
     * @throws IOException if an error occurs while reading the file.
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file to check: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        Scanner fileScanner = new Scanner(file);

        Stack<String> stack = new Stack<>();
        boolean isValid = true;
        boolean isFirstOutput = true;

        while (fileScanner.hasNextLine() && isValid) {
            String line = fileScanner.nextLine();
            String[] tokens = line.split("(?=<)|(?<=>)");

            for (String token : tokens) {
                token = token.trim();
                if (token.startsWith("<") && token.endsWith(">")) {
                    token = token.substring(1, token.length() - 1).trim();
                    boolean isClosingTag = token.startsWith("/");
                    boolean isSelfClosing = token.endsWith("/");

                    String tagName = isClosingTag ? token.substring(1) : token;
                    tagName = isSelfClosing ? tagName.substring(0, tagName.length() - 1) : tagName;
                    tagName = tagName.split(" ")[0];

                    if (isTagToCheck(tagName)) {
                        if (isClosingTag) {
                            if (!stack.isEmpty() && tagName.equalsIgnoreCase(stack.peek())) {
                                outputMatch(isFirstOutput, tagName, stack);
                                isFirstOutput = false;
                                stack.pop();
                            } else {
                                handleMismatch(isFirstOutput, stack, tagName);
                                isFirstOutput = false;
                                isValid = false;
                            }
                        } else if (!isSelfClosing) {
                            stack.push(tagName.toLowerCase());
                        }
                    }
                }
            }
        }

        finalizeOutput(isValid, stack);

        fileScanner.close();
        scanner.close();
    }
    
    /**
     * Outputs the match between an opening and closing tag.
     * 
     * @param isFirstOutput Flag to determine if it's the first output for formatting purposes.
     * @param tagName The name of the tag being processed.
     * @param stack The stack of tags for tracking open tags.
     */
    private static void outputMatch(boolean isFirstOutput, String tagName, Stack<String> stack) {
        if (isFirstOutput) {
            System.out.println("Expected Actual");
        }
        System.out.println("<" + stack.peek().toLowerCase() + "> <" + tagName.toLowerCase() + ">");
    }
    
    /**
     * Handles the case where there is a mismatch between expected and actual closing tags.
     * 
     * @param isFirstOutput Flag to determine if it's the first output for formatting purposes.
     * @param stack The stack of tags for tracking open tags.
     * @param closingTagName The name of the closing tag being processed.
     */
    private static void handleMismatch(boolean isFirstOutput, Stack<String> stack, String closingTagName) {
        if (!stack.isEmpty()) {
            if (isFirstOutput) {
                System.out.println("Expected Actual");
            }
            String mismatchedOpeningTag = stack.peek();
            System.out.println("<" + closingTagName.toLowerCase() + "> <" + mismatchedOpeningTag.toLowerCase() + ">");
            System.out.println("No matching tag for <" + mismatchedOpeningTag.toLowerCase() + ">");
        } else {
            System.out.println("No opening tag for <" + closingTagName.toLowerCase() + ">");
        }
    }
    
    /**
     * Finalizes the output based on the validity of the HTML document's tag structure.
     * 
     * @param isValid Boolean indicating whether the HTML document's tags are valid.
     * @param stack The stack of tags for tracking open tags.
     */
    private static void finalizeOutput(boolean isValid, Stack<String> stack) {
        if (!isValid || !stack.isEmpty()) {
            System.out.println("Please revise your html and try again.");
        } else {
            System.out.println("All tags match. Nice work!");
        }
    }
    
    /**
     * Checks if a given tag is one of the tags that should be checked for matching.
     * 
     * @param tag The tag to check.
     * @return true if the tag should be checked, false otherwise.
     */
    private static boolean isTagToCheck(String tag) {
    	String[] tagsToCheck = {
                "html", "head", "title", "body", 
                "h1", "h2", "h3", "h4", 
                "table", "td", "tr", 
                "ol", "ul", "li", 
                "a", "p", "b", "i"
            };
        for (String validTag : tagsToCheck) {
            if (validTag.equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }
}
