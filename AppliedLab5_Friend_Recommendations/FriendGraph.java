/**
 * FriendGraph.java
 *
 * @author Hari Prakash
 * @author Hari Prakash
 * CIS 22C, Applied Lab 5
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FriendGraph {
    private Scanner scanner;
    private Graph userGraph;
    private ArrayList<String> userNames = new ArrayList<>();
    private boolean[] isFriend;


    /**
     * this is the main method of the program
     * @param args
     */
    public static void main(String[] args) {
        FriendGraph friendGraph = new FriendGraph();
        friendGraph.run();
        System.out.print("Goodbye!");
    }

    /**
     * this method is used to run the program
     */
    public void run() {
        System.out.println("Welcome to the Friend Recommender!\n");
        System.out.print("Enter the name of a file: ");
        scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        readFriendsFile(fileName);

        System.out.println("Enter your user number below: ");
        printUsers();
        System.out.print("\nEnter your choice: ");

        int currentUser = scanner.nextInt();

        LinkedList<Integer> adjacencyList = userGraph.getAdjacencyList(currentUser);
        adjacencyList.positionIterator();
        while (!adjacencyList.offEnd()) {
            int adjacentUser = adjacencyList.getIterator();
            isFriend[adjacentUser - 1] = true;
            adjacencyList.advanceIterator();
        }
        isFriend[currentUser - 1] = true;

        if (displayRecommendations(currentUser) == -1) {
            return;
        }

        int userInput = 0;
        while (userInput != -1) {
            System.out.print("\nEnter the number of a friend to add or -1 to quit:\n" + "Enter your choice: ");

            userInput = scanner.nextInt();
            if (userInput != -1) {
                userGraph.addUndirectedEdge(currentUser, userInput);
                isFriend[userInput - 1] = true;
                userInput = displayRecommendations(currentUser);
            }
        }
    }

    /**
     * this method is used to display the recommendations
     * @param currentUser
     * @return
     */
    public int displayRecommendations(int currentUser) {
        System.out.println("\nHere are your current friends:");
        LinkedList<Integer> currentFriends = userGraph.getAdjacencyList(currentUser);

        currentFriends.positionIterator();
        while (!currentFriends.offEnd()) {
            int friendId = currentFriends.getIterator();
            System.out.println(friendId + ". " + userNames.get(friendId));

            currentFriends.advanceIterator();
        }

        boolean hasRecommendations = false;
        System.out.println("\nHere are your recommended friends:");
        userGraph.BFS(currentUser);
        for (int i = 1; i < userNames.size(); i++) {
            if (userGraph.getColor(i) == 'B' && !isFriend[i - 1]) {
                System.out.println(i + ". " + userNames.get(i));
                hasRecommendations = true;
            }
        }
        if (!hasRecommendations) {
            System.out.println("Sorry! We don't have any recommendations for you at this time.");
            return -1;
        }
        return 0;
    }

    /**
     * this method is used to print the users
     */
    public void printUsers() {
        for (int i = 1; i < userNames.size(); i++) {
            System.out.println(i + ". " + userNames.get(i));
        }
    }

    /**
     * this method is used to read the friends file
     * @param fileName
     */
    public void readFriendsFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            int numUsers = Integer.parseInt(reader.readLine());
            userGraph = new Graph(numUsers);
            userNames = new ArrayList<>();
            isFriend = new boolean[numUsers + 1];

            for (int i = -1; i < numUsers; i++)
                userNames.add("");

            boolean reachedEndOfFile = false;
            while (!reachedEndOfFile) {
                String inputLine = reader.readLine();
                if (inputLine != null) {
                    int userId = Integer.parseInt(inputLine);
                    userNames.set(userId, reader.readLine());
                    String friendships = reader.readLine();
                    StringTokenizer tokenizer = new StringTokenizer(friendships);
                    while (tokenizer.hasMoreElements()) {
                        String friendId = tokenizer.nextToken();
                        userGraph.addDirectedEdge(userId,
                                Integer.parseInt(friendId.substring(0, friendId.length() - 1)));
                    }
                } else {
                    reachedEndOfFile = true;
                }
            }
        } catch (IOException ex) {
            System.out.print("Invalid file name\n" + "Enter the name of a file: ");
            readFriendsFile(scanner.nextLine());
        }
    }
}
