/**
 * CardApp.java
 * @author Hari Prakash
 * CIS 22C, Applied Lab 1
 */

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CardApp {
    private LinkedList<Card> list;

    /**
     * User interface prompts user, reads and writes files.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input file path: ");
        String inputFile = scanner.nextLine();
        scanner.close();

        CardApp cardApp = new CardApp();

        try {
            File file = new File(inputFile);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String rank = line.substring(0, line.length() - 1);
                String suit = line.substring(line.length() - 1);
                cardApp.addCard(new Card(rank, suit));
            }
            fileScanner.close();
        } catch (Exception exception) {
            throw new RuntimeException("Error reading file");
        }

        cardApp.shuffle();

        try {
            File shuffleFile = new File("shuffled.txt");
            PrintWriter out = new PrintWriter(shuffleFile);
            cardApp.list.positionIterator();
            while (!cardApp.list.offEnd()) {
                out.println(cardApp.list.getIterator().toString());
                cardApp.list.advanceIterator();
            }
            out.close();
            System.out.println("Shuffled deck: shuffled.txt");
        } catch (IOException exception) {
            System.out.println("An error occurred while writing to the file.");
            exception.printStackTrace();
        }

        cardApp.sort();

        try {
            File sortFile = new File("sorted.txt");
            PrintWriter out = new PrintWriter(sortFile);
            cardApp.list.positionIterator();
            while (!cardApp.list.offEnd()) {
                out.println(cardApp.list.getIterator().toString());
                cardApp.list.advanceIterator();
            }
            out.close();
            System.out.println("Sorted deck: sorted.txt");
        } catch (IOException exception) {
            System.out.println("An error occurred while writing to the file.");
            exception.printStackTrace();
        }
    }




    /**
     * Default constructor to initialize the deck
     */
    public CardApp() {
    	list = new LinkedList<Card>();
    }

    /**
     * Inserts a new Card into the deck
     * @param card a playing Card
     */
    public void addCard(Card card) {
    	list.addLast(card);
    }

    /**
     * Shuffles cards following this algorithm:
     * First swaps first and last card
     * Next, swaps every even card with the card 3 nodes away from that card. Stops when it reaches the 3rd to last node
     * Then, swaps ALL cards with the card that is 2 nodes away from it, starting at the 2nd card and stopping stopping at the 3rd to last node
     */
    public void shuffle() {
        final int OFFSET_THREE = 3; 
        final int OFFSET_TWO = 2;

        int length = list.getLength();

        Card[] cards = new Card[length];
        list.positionIterator();
        for (int i = 0; i < length; i++) {
            cards[i] = list.getIterator();
            list.advanceIterator();
        }

        // Swap first and last cards
        Card tempCard = cards[0];
        cards[0] = cards[length - 1];
        cards[length - 1] = tempCard;

        for (int i = 1; i < length - OFFSET_THREE; i += 2) {
            if (i + OFFSET_THREE < length) {
                tempCard = cards[i];
                cards[i] = cards[i + OFFSET_THREE];
                cards[i + OFFSET_THREE] = tempCard;
            }
        }

        for (int i = 1; i < length - OFFSET_TWO; i++) {
            tempCard = cards[i];
            cards[i] = cards[i + OFFSET_TWO];
            cards[i + OFFSET_TWO] = tempCard;
        }

        list.clear();
        for (Card card : cards) {
            list.addLast(card);
        }
    }




    /**
     * Implements the bubble sort algorithm
     * to sort cardList into sorted order, first by suit
     * (alphabetical order)
     * then by rank from 2 to A
     */
    public void sort() {
        int length = list.getLength();

        Card[] cards = new Card[length];
        list.positionIterator();
        for (int i = 0; i < length; i++) {
            cards[i] = list.getIterator();
            list.advanceIterator();
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (cards[j].compareTo(cards[j + 1]) > 0) {
                    Card temp = cards[j];
                    cards[j] = cards[j + 1];
                    cards[j + 1] = temp;
                }
            }
        }

        list.clear();
        for (Card card : cards) {
            list.addLast(card);
        }
    }



    /**
     * Returns the deck of cards with each card separated
     * by a blank space and a new line character at the end.
     * @return The deck of cards as a string.
     */
    @Override public String toString() {
    	return list.toString();
    }
}
