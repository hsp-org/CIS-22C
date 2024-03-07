// Following for main()
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;
// Following for console output redirection
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//---copy below this line---
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.lang.reflect.*; // Counting method names, fields

// Unit test all assigned methods for MutualFundAccount
public class zyLabsUnitTest2 { // remove number after name
    private final int SIZE = 10;
    private PrintWriter testFeedback; // for zyLab

    public int passed(PrintWriter testFeedback) {
        this.testFeedback = testFeedback;
        double ptsEarned = 0, ptVal = 0, numTst = 0, ptsPerTest = 0;
        int cntErr = 0;

        // Test constructors
        ptVal = 2;
        numTst = 12;
        ptsPerTest = ptVal / numTst;
        msg("Calling testMutualFundAccountMutual()");
        cntErr += testMutualFundAccountMutual();
        msg("Calling testMutualFundAccountDoubleMutual()");
        cntErr += testMutualFundAccountDoubleMutual();
        msg("Calling testMutualFundAccountMutualDouble()");
        cntErr += testMutualFundAccountMutualDouble();
        ptsEarned += Math.rint(ptVal - cntErr * ptsPerTest);

        // Calling mutators and getAccountSeed()
        // updateShares
        // deductFee
        ptVal = 2;
        numTst = 7;
        ptsPerTest = ptVal / numTst;
        cntErr = 0;
        msg("Calling testGetAccountSeed()");
        cntErr += testGetAccountSeed();
        msg("Calling testUpdateShares()");
        cntErr += testUpdateShares();
        msg("Calling testDeductFee()");
        cntErr += testDeductFee();
        ptsEarned += Math.rint(ptVal - cntErr * ptsPerTest);

        msg("Calling testToString()");
        ptVal = 1;
        numTst = 1;
        ptsPerTest = ptVal / numTst;
        ptsEarned += Math.rint(ptVal - testToString() * ptsPerTest);

        // Verify no added/extra/additional methods, variables or inner classes
        msg("Testing for added methods, fields or inner classes");
        final int NUM_METHODS = 9;
        final int NUM_FIELDS = 4;
        final int NUM_INNER_CLASSES = 0;
        try {
            Class<?> cls = Class.forName("MutualFund");
            Method methods[] = cls.getDeclaredMethods();
            //System.out.println("countMethods=" + methods.length);
            if (methods.length != NUM_METHODS) {
                msg("Must not add or remove MutualFund methods!");
                ptsEarned = 0;
            }
            Field fields[] = cls.getDeclaredFields();
            //System.out.println("countFields=" + fields.length);
            if (fields.length != NUM_FIELDS) {
                msg("Must not add or remove MutualFund fields!");
                ptsEarned = 0;
            }
            Class<?>[] innerClasses = HashTable.class.getDeclaredClasses();
            //System.out.println("countInnerClasses=" + innerClasses.length);
            if (innerClasses.length != NUM_INNER_CLASSES) {
                testFeedback.write("Must not add inner classes!\n");
                ptsEarned = 0;
            }
        } catch (Throwable e) {
            System.err.println(e);
        }


        return (int) (ptsEarned + 0.01); // round down
    }

    private void msg(String message) {
        testFeedback.write(message + "\n");
    }

    private int testMutualFundAccountMutual() {
        int cntErr = 0;
        String str = "";
        MutualFund mf1 = new MutualFund("tock");
        MutualFund mf2 = null;
        MutualFundAccount mfa = new MutualFundAccount(mf1);
        //assertEquals(mf1, mfa.getMf());
        mf2 =  mfa.getMf();
        if (!mf1.equals(mf2)) {
            msg("-After new MutualFundAccount(new MutualFund(\"tock\")) "
                + "getMf() must\n return MutualFund{\"No name\", \"tock\", 0.0, 0.0}"
                + "\n    not MutualFund{\"" + mf2.getFundName() + "\", \""
                + mf2.getTicker() + "\", " + mf2.getPricePerShare() + ", "
                + mf2.getTradingFee() + "}");
            cntErr++;
        }
        //assertEquals(0, mfa.getNumShares());
        double numShares = mfa.getNumShares();
        if (numShares - 0.0 > 0.001) {
            msg("-After new MutualFundAccount(new MutualFund(\"tock\")) "
                + "getNumShares() must return 0.0 not " + numShares + ".");
            cntErr++;
        }
        mf1 = new MutualFund("ABC");
        mfa = new MutualFundAccount(mf1);
        //assertEquals(mf1, mfa.getMf());
        mf2 =  mfa.getMf();
        if (!mf1.equals(mf2)) {
            msg("-After new MutualFundAccount(new MutualFund(\"ABC\")) "
                + "getMf() must\n return MutualFund{\"No name\", \"ABC\", 0.0, 0.0}"
                + "\n    not MutualFund{\"" + mf2.getFundName() + "\", \""
                + mf2.getTicker() + "\", " + mf2.getPricePerShare() + ", "
                + mf2.getTradingFee() + "}");
            cntErr++;
        }
        //assertEquals(0, mfa.getNumShares());
        numShares = mfa.getNumShares();
        if (numShares - 0.0 > 0.001) {
            msg("-After new MutualFundAccount(new MutualFund(\"ABC\")) "
                + "getNumShares() must return 0.0 not " + numShares + ".");
            cntErr++;
        }
        return cntErr;
    }

    private int testMutualFundAccountDoubleMutual() {
        int cntErr = 0;
        String str = "";
        MutualFund mf1 = new MutualFund("tock");
        MutualFund mf2 = null;
        MutualFundAccount mfa = new MutualFundAccount(12.3, mf1);
        //assertEquals(mf1, mfa.getMf());
        mf2 =  mfa.getMf();
        if (!mf1.equals(mf2)) {
            msg("-After new MutualFundAccount(12.3, new MutualFund(\"tock\")) "
                + "getMf() must\n return MutualFund{\"No name\", \"tock\", 0.0, 0.0}"
                + "\n    not MutualFund{\"" + mf2.getFundName() + "\", \""
                + mf2.getTicker() + "\", " + mf2.getPricePerShare() + ", "
                + mf2.getTradingFee() + "}");
            cntErr++;
        }
        //assertEquals(0, mfa.getNumShares());
        double numShares = mfa.getNumShares();
        if (numShares - 12.3 > 0.001) {
            msg("-After new MutualFundAccount(12.3, new MutualFund(\"tock\")) "
                + "getNumShares() must return 12.3 not " + numShares + ".");
            cntErr++;
        }
        mf1 = new MutualFund("ABC");
        mfa = new MutualFundAccount(4.2, mf1);
        //assertEquals(mf1, mfa.getMf());
        mf2 =  mfa.getMf();
        if (!mf1.equals(mf2)) {
            msg("-After new MutualFundAccount(4.2, new MutualFund(\"ABC\")) "
                + "getMf() must\n return MutualFund{\"No name\", \"ABC\", 0.0, 0.0}"
                + "\n    not MutualFund{\"" + mf2.getFundName() + "\", \""
                + mf2.getTicker() + "\", " + mf2.getPricePerShare() + ", "
                + mf2.getTradingFee() + "}");
            cntErr++;
        }
        //assertEquals(0, mfa.getNumShares());
        numShares = mfa.getNumShares();
        if (numShares - 4.2 > 0.001) {
            msg("-After new MutualFundAccount(4.2, new MutualFund(\"ABC\")) "
                + "getNumShares() must return 4.2 not " + numShares + ".");
            cntErr++;
        }
        return cntErr;
    }

    private int testMutualFundAccountMutualDouble() {
        int cntErr = 0;
        String str = "";
        MutualFund mf1 = new MutualFund("tock");
        MutualFund mf2 = null;
        MutualFundAccount mfa = new MutualFundAccount(mf1, 12.3);
        //assertEquals(mf1, mfa.getMf());
        mf2 =  mfa.getMf();
        if (!mf1.equals(mf2)) {
            msg("-After new MutualFundAccount(new MutualFund(\"tock\"), 12.3) "
                + "getMf() must\n return MutualFund{\"No name\", \"tock\", 0.0, 0.0}"
                + "\n    not MutualFund{\"" + mf2.getFundName() + "\", \""
                + mf2.getTicker() + "\", " + mf2.getPricePerShare() + ", "
                + mf2.getTradingFee() + "}");
            cntErr++;
        }
        //assertEquals(0, mfa.getNumShares());
        double numShares = mfa.getNumShares();
        if (numShares - 12.3 > 0.001) {
            msg("-After new MutualFundAccount(new MutualFund(\"tock\"), 12.3) "
                + "getNumShares() must return 12.3 not " + numShares + ".");
            cntErr++;
        }
        mf1 = new MutualFund("ABC");
        mfa = new MutualFundAccount(mf1, 4.2);
        //assertEquals(mf1, mfa.getMf());
        mf2 =  mfa.getMf();
        if (!mf1.equals(mf2)) {
            msg("-After new MutualFundAccount(new MutualFund(\"ABC\"), 4.2) "
                + "getMf() must\n return MutualFund{\"No name\", \"ABC\", 0.0, 0.0}"
                + "\n    not MutualFund{\"" + mf2.getFundName() + "\", \""
                + mf2.getTicker() + "\", " + mf2.getPricePerShare() + ", "
                + mf2.getTradingFee() + "}");
            cntErr++;
        }
        //assertEquals(0, mfa.getNumShares());
        numShares = mfa.getNumShares();
        if (numShares - 4.2 > 0.001) {
            msg("-After new MutualFundAccount(new MutualFund(\"ABC\"), 4.2) "
                + "getNumShares() must return 4.2 not " + numShares + ".");
            cntErr++;
        }
        return cntErr;
    }

    private int testGetAccountSeed() {
        int cntErr = 0;
        MutualFund mf = new MutualFund("tock");
        MutualFundAccount mfa = new MutualFundAccount(mf);
        //assertNotEquals(-1, mfa.getAccountSeed());
        int seed =  MutualFundAccount.getAccountSeed();
        if (seed == -1) {
            msg("-getAccountSeed() must not return -1.");
            cntErr++;
        }
        if (seed < 100000000) {
            msg("-getAccountSeed() must return values >= accountSeed "
                + "starting value 100000000");
            cntErr++;
        }
        int seed2 =  MutualFundAccount.getAccountSeed();
        if (seed == seed2) {
            msg("-getAccountSeed() must return different values every method "
                + "call.");
            cntErr++;
        }
        return cntErr;
    }

    private int testUpdateShares() {
        int cntErr = 0;
        double numShares = 0;
        MutualFund mf = new MutualFund("abc");
        MutualFundAccount mfa = new MutualFundAccount(mf, 12.3);
        //assertEquals(0.0, mfa.getNumShares());
        numShares = mfa.getNumShares();
        if (numShares - 12.3 > 0.001) {
            msg("-After new MutualFundAccount(new MutualFund(\"abc\"), 12.3) "
                + "getNumShares() must return 12.3 not " + numShares + ".");
            cntErr++;
        }
        mfa.updateShares(42);
        //assertEquals(42.0, mfa.getNumShares());
        numShares = mfa.getNumShares();
        if (numShares - 54.3 > 0.001) {
            msg("-After updateShares(42) getNumShares() must return 54.3 not "
                + numShares + ".");
            cntErr++;
        }
        return cntErr;
    }

    private int testDeductFee() {
        int cntErr = 0;
        double numShares = 0;
        MutualFund mf = new MutualFund("abc", "tock", 10, 0.1);
        MutualFundAccount mfa = new MutualFundAccount(mf, 100);
        numShares = mfa.getNumShares();
        if (numShares - 100.0 > 0.001) {
            msg("-New MutualFundAccount(new MutualFund(\"abc\"), "
                + "\"tock\", 10, 0.1)\n getNumShares() must return 100.0 not "
                + numShares + ".");
            cntErr++;
        }
        mfa.deductFee();
        numShares = mfa.getNumShares();
        if (numShares - 90.0 > 0.001) {
            msg("-With 100 shares at $10/share with a fee of 0.1,\n after "
                +"deducting the fee the shares should be 90.0 not "
                + numShares + ".");
            cntErr++;
        }
        return cntErr;
    }

    private int testToString() {
        int cntErr = 0;
        String str = "";
        MutualFund mf = new MutualFund("abc", "tock", 10, 0.1);
        MutualFundAccount mfa = new MutualFundAccount(mf, 100);
        //assertEquals("No name\ntock\nShare Price: $.00\nTrading Fee: 0.0%",
        //    mfa.toString());
        str =  mfa.toString();
        if (!str.equals("abc\ntock\nShare Price: $10.00\nTrading Fee: 0.1%\n\n"
            + "Total Shares: 100.0\nValue: $1,000.00\n")) {
            msg("-After New MutualFundAccount(new MutualFund(\"abc\"), "
                + "\"tock\", 10, 0.1), 100) toString() must return:\n"
                + "abc\ntock\nShare Price: $10.00\nTrading Fee: 0.1%\n\n"
                + "Total Shares: 100.0\nValue: $1,000.00\n" + "\nNOT:\n" + str);
            cntErr++;
        }
        return cntErr;
    }

    // ---copy above this line but add closing } ---
    public static void main(String[] args) throws Exception {
        File file = new File("testfeedback.txt");
        PrintWriter testFeedback = new PrintWriter(file);
        zyLabsUnitTest2 zy = new zyLabsUnitTest2();
        int points = zy.passed(testFeedback);
        testFeedback.close();
        System.out.println("Total points: " + points);
        // Following two lines print testfeedback.txt
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
        lines.forEach(System.out::println);
    }
}
