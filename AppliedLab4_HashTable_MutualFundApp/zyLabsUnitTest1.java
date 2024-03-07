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

// Unit test all assigned methods for MutualFund
public class zyLabsUnitTest1 { // remove number after name
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
        msg("Calling testMutualFundTicker()");
        cntErr += testMutualFundTicker();
        msg("Calling testMutualFundNameTicker()");
        cntErr += testMutualFundNameTicker();
        msg("Calling testMutualFund4Arg()");
        cntErr += testMutualFund4Arg();
        ptsEarned += Math.rint(ptVal - cntErr * ptsPerTest);

        // Calling mutators
        ptVal = 1;
        numTst = 4;
        ptsPerTest = ptVal / numTst;
        cntErr = 0;
        msg("Calling testSetPricePerShare()");
        cntErr += testSetPricePerShare();
        msg("Calling testSetTradingFee()");
        cntErr += testSetTradingFee();
        ptsEarned += Math.rint(ptVal - cntErr * ptsPerTest);

        msg("Calling testToString()");
        ptVal = 1;
        numTst = 2;
        ptsPerTest = ptVal / numTst;
        ptsEarned += Math.rint(ptVal - testToString() * ptsPerTest);

        msg("Calling testEqualsObject()");
        ptVal = 1;
        numTst = 5;
        ptsPerTest = ptVal / numTst;
        ptsEarned += Math.rint(ptVal - testEqualsObject() * ptsPerTest);

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

    private int testMutualFundTicker() {
        int cntErr = 0;
        String str = "";
        MutualFund mf = new MutualFund("tock");
        //assertEquals("tock", mf.getTicker());
        str =  mf.getTicker();
        if (!str.equals("tock")) {
            msg("-After new MutualFund(\"tock\") getTicker() must return "
                + "\"tock\" not \"" + str + "\".");
            cntErr++;
        }
        //assertEquals("tock", mf.getFundName());
        str =  mf.getFundName();
        if (!str.equals("No name")) {
            msg("-After new MutualFund(\"tock\") getFundName() must return "
                + "\"No name\" not \"" + str + "\".");
            cntErr++;
        }
        //assertEquals(0.0, mf.getPricePerShare());
        double pps = mf.getPricePerShare();
        if (Math.abs(pps - 0.0) >= 0.001) {
            msg("-After new MutualFund(\"tock\") getPricePerShare() must "
                + "return 0.0 not " + pps);
            cntErr++;
        }
        //assertEquals(0.0, mf.getTradingFee());
        double fee = mf.getTradingFee();
        if (Math.abs(fee - 0.0) >= 0.001) {
            msg("-After new MutualFund(\"tock\") getTradingFee() must "
                + "return 0.0 not " + fee);
            cntErr++;
        }
        return cntErr;
    }

    private int testMutualFundNameTicker() {
        int cntErr = 0;
        String str = "";
        MutualFund mf = new MutualFund("Test", "tock2");
        //assertEquals("tock", mf.getTicker());
        str =  mf.getTicker();
        if (!str.equals("tock2")) {
            msg("-After new MutualFund(\"Test\", \"tock2\") getTicker() must "
                + "return \"tock2\" not \"" + str + "\".");
            cntErr++;
        }
        //assertEquals("tock", mf.getFundName());
        str =  mf.getFundName();
        if (!str.equals("Test")) {
            msg("-After new MutualFund(\"Test\", \"tock2\") getFundName() must "
                + "return \"Test\" not \"" + str + "\".");
            cntErr++;
        }
        //assertEquals(0.0, mf.getPricePerShare());
        double pps = mf.getPricePerShare();
        if (Math.abs(pps - 0.0) >= 0.001) {
            msg("-After new MutualFund(\"Test\", \"tock2\") getPricePerShare() "
                + "must return 0.0 not " + pps);
            cntErr++;
        }
        //assertEquals(0.0, mf.getTradingFee());
        double fee = mf.getTradingFee();
        if (Math.abs(fee - 0.0) >= 0.001) {
            msg("-After new MutualFund(\"Test\", \"tock2s\") getTradingFee() "
                + "must return 0.0 not " + fee);
            cntErr++;
        }
        return cntErr;
    }

    private int testMutualFund4Arg() {
        int cntErr = 0;
        String str = "";
        MutualFund mf = new MutualFund("Test4", "tock3", 1.2, 0.1);
        //assertEquals("tock", mf.getTicker());
        str =  mf.getTicker();
        if (!str.equals("tock3")) {
            msg("-After new MutualFund(\"Test4\", \"tock3\", 1.2, 0.1) "
                + "getTicker() must return \"tock3\" not \"" + str + "\".");
            cntErr++;
        }
        //assertEquals("Test4", mf.getFundName());
        str =  mf.getFundName();
        if (!str.equals("Test4")) {
            msg("-After new MutualFund(\"Test4\", \"tock3\", 1.2, 0.1) "
                + "getFundName() must return \"Test4\" not \"" + str + "\".");
            cntErr++;
        }
        //assertEquals(0.0, mf.getPricePerShare());
        double pps = mf.getPricePerShare();
        if (Math.abs(pps - 1.2) >= 0.001) {
            msg("-After new MutualFund(\"Test4\", \"tock3\", 1.2, 0.1) "
                + "getPricePerShare() must return 1.2 not " + pps);
            cntErr++;
        }
        //assertEquals(0.0, mf.getTradingFee());
        double fee = mf.getTradingFee();
        if (Math.abs(fee - 0.1) >= 0.001) {
            msg("-After new MutualFund(\"Test4\", \"tock3\", 1.2, 0.1) "
                + "getTradingFee() must return 0.1 not " + fee);
            cntErr++;
        }
        return cntErr;
    }

    private int testSetPricePerShare() {
        int cntErr = 0;
        double pps = 0;
        MutualFund mf = new MutualFund("abc");
        //assertEquals(0.0, mf.getPricePerShare());
        pps = mf.getPricePerShare();
        if (Math.abs(pps - 0.0) >= 0.001) {
            msg("-After new MutualFund(\"abc\") getPricePerShare() must "
                + "return 0.0 not " + pps);
            cntErr++;
        }
        mf.setPricePerShare(12.34);
        //assertEquals(0.0, mf.getPricePerShare());
        pps = mf.getPricePerShare();
        if (Math.abs(pps - 12.34) >= 0.001) {
            msg("-After setPricePerShare(12.34), getPricePerShare() must "
                + "return 12.34 not " + pps);
            cntErr++;
        }
        return cntErr;
    }

    private int testSetTradingFee() {
        int cntErr = 0;
        double pps = 0;
        MutualFund mf = new MutualFund("abc");
        //assertEquals(0.0, mf.getTradingFee());
        pps = mf.getTradingFee();
        if (Math.abs(pps - 0.0) >= 0.001) {
            msg("-After new MutualFund(\"abc\") getTradingFee() must "
                + "return 0.0 not " + pps);
            cntErr++;
        }
        mf.setTradingFee(1.23);
        //assertEquals(0.0, mf.getTradingFee());
        pps = mf.getTradingFee();
        if (Math.abs(pps - 1.23) >= 0.001) {
            msg("-After setTradingFee(1.23), getTradingFee() must "
                + "return 1.23 not " + pps);
            cntErr++;
        }
        return cntErr;
    }

    private int testToString() {
        int cntErr = 0;
        String str = "";
        MutualFund mf = new MutualFund("tock");
        //assertEquals("No name\ntock\nShare Price: $.00\nTrading Fee: 0.0%",
        //    mf.toString());
        str =  mf.toString();
        if (!str.equals("No name\ntock\nShare Price: $.00\nTrading Fee: 0.0%")) {
            msg("-After new MutualFund(\"tock\") toString() must return:\n"
                + "No name\ntock\nShare Price: $.00\nTrading Fee: 0.0%"
                + "\nNOT:\n" + str);
            cntErr++;
        }
        mf = new MutualFund("Test4", "tock2", 1.2, 0.1);
        //assertEquals("Test4\ntock2\nShare Price: $1.20\nTrading Fee: 0.1%",
        //    mf.toString());
        str =  mf.toString();
        if (!str.equals("Test4\ntock2\nShare Price: $1.20\nTrading Fee: 0.1%")) {
            msg("-After new MutualFund(\"Test4\", \"tock2\", 1.2, 0.1) "
                + "toString() must return:\n"
                + "Test4\ntock2\nShare Price: $1.20\nTrading Fee: 0.1%"
                + "\nNOT:\n" + str);
            cntErr++;
        }
        return cntErr;
    }

    private int testEqualsObject() {
        int cntErr = 0;
        String str = "";
        MutualFund mf1 = new MutualFund("tock");
        MutualFund mf2 = new MutualFund("tock");
        //assertTrue(mf1.equals(mf2));
        if (!mf1.equals(mf2)) {
            msg("-MutualFund{" + mf1.getFundName() + ", "
                + mf1.getTicker() + ", " + mf1.getPricePerShare() + ", "
                + mf1.getTradingFee() +  "}.equals(\n MutualFund{"
                + mf2.getFundName() + ", " + mf2.getTicker() + ", "
                + mf2.getPricePerShare() + ", " + mf2.getTradingFee()
                + "}) must return true.");
            cntErr++;
        }
        mf2 = new MutualFund("No name", "tock", 0.0, 0.0);
        //assertTrue(mf1.equals(mf2));
        if (!mf1.equals(mf2)) {
            msg("-MutualFund{" + mf1.getFundName() + ", "
                + mf1.getTicker() + ", " + mf1.getPricePerShare() + ", "
                + mf1.getTradingFee() +  "}.equals(\n MutualFund{"
                + mf2.getFundName() + ", " + mf2.getTicker() + ", "
                + mf2.getPricePerShare() + ", " + mf2.getTradingFee()
                + "}) must return true.");
            cntErr++;
        }
        mf2 = new MutualFund("Test3", "tock", 1.2, 0.1);
        //assertTrue(mf1.equals(mf2));
        if (!mf1.equals(mf2)) {
            msg("-xMutualFund{" + mf1.getFundName() + ", "
                + mf1.getTicker() + ", " + mf1.getPricePerShare() + ", "
                + mf1.getTradingFee() +  "}.equals(\n MutualFund{"
                + mf2.getFundName() + ", " + mf2.getTicker() + ", "
                + mf2.getPricePerShare() + ", " + mf2.getTradingFee()
                + "}) must return true.");
            cntErr++;
        }
        mf2 = new MutualFund("Test4", "tock2", 1.2, 0.1);
        //assertFalse(mf1.equals(mf2));
        if (mf1.equals(mf2)) {
            msg("-MutualFund{" + mf1.getFundName() + ", "
                + mf1.getTicker() + ", " + mf1.getPricePerShare() + ", "
                + mf1.getTradingFee() +  "}.equals(\n MutualFund{"
                + mf2.getFundName() + ", " + mf2.getTicker() + ", "
                + mf2.getPricePerShare() + ", " + mf2.getTradingFee()
                + "}) must return false.");
            cntErr++;
        }
        mf2 = null;
        //assertFalse(mf1.equals(mf2));
        if (mf1.equals(mf2)) {
            msg("-MutualFund{" + mf1.getFundName() + ", "
                + mf1.getTicker() + ", " + mf1.getPricePerShare() + ", "
                + mf1.getTradingFee() + "}.equals(MutualFund{"
                + "null}) must return false.");
            cntErr++;
        }
        return cntErr;
    }

    // ---copy above this line but add closing } ---
    public static void main(String[] args) throws Exception {
        File file = new File("testfeedback.txt");
        PrintWriter testFeedback = new PrintWriter(file);
        zyLabsUnitTest1 zy = new zyLabsUnitTest1();
        int points = zy.passed(testFeedback);
        testFeedback.close();
        System.out.println("Total points: " + points);
        // Following two lines print testfeedback.txt
        List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
        lines.forEach(System.out::println);
    }
}
