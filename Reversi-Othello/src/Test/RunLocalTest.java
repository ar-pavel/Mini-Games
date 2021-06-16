package Test;

import com.company.Reversi;
import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;


/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Summer 2020</p>
 *
 * @author Purdue CS
 * @version June 15, 2020
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Summer 2020</p>
     *
     * @author Purdue CS
     * @version June 15, 2020
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        private static String multiline(String... inputLines) {
            StringBuilder sb = new StringBuilder();

            for(String line : inputLines) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        }

        String BlackMoveMessage = "Place move (Black): row then column: ";
        String WhiteMoveMessage = "Place move (White): row then column: ";
        String InvalidMove = "Invalid move!";
        String ExitMessage = "Exiting!";

        String MainBoard = "\n  1 2 3 4 5 6 7 8 \n" +
                "1 _ _ _ _ _ _ _ _ \n" +
                "2 _ _ _ _ _ _ _ _ \n" +
                "3 _ _ _ * _ _ _ _ \n" +
                "4 _ _ * W B _ _ _ \n" +
                "5 _ _ _ B W * _ _ \n" +
                "6 _ _ _ _ * _ _ _ \n" +
                "7 _ _ _ _ _ _ _ _ \n" +
                "8 _ _ _ _ _ _ _ _ \n" ;

        @Test(timeout = 100)
        public void invalidMoveTestOne() {
            String input = multiline("88","exit");
            String expected = multiline(MainBoard,BlackMoveMessage, InvalidMove, BlackMoveMessage,ExitMessage);
            String message = "Check for Invalid Moves";

            receiveInput(input);
            Reversi.main(new String[] {});

            assertEquals(message, expected, getOutput());
        } // invalidMoveTestOne

    }

}