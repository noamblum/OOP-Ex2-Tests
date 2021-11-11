import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class PlayerTest {

    Board b;
    Player p;
    InputStream sysInBackup;
    @BeforeEach
    void setUp() {
        b = new Board();
        sysInBackup = System.in; // backup System.in to restore it later
    }

    @AfterEach
    void tearDown(){
        System.setIn(sysInBackup);
    }

    @Test
    void playTurn_OnValidInputMarkX_MarkXIsOnBoard() {
        for (int i = 0; i < Board.SIZE; i++){
            assumeTrue(b.getMark(i,i) == Mark.BLANK);
        }
        String testInput = "11\n22\n33\n44\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);
        p = new Player();
        for (int i = 0; i < Board.SIZE; i++){
            p.playTurn(b, Mark.X);
        }
        for (int i = 0; i < Board.SIZE; i++){
            assertEquals(Mark.X, b.getMark(i,i));
        }
    }

    @Test
    void playTurn_OnValidInputMarkO_MarkOIsOnBoard() {
        for (int i = 0; i < Board.SIZE; i++){
            assumeTrue(b.getMark(i,i) == Mark.BLANK);
        }
        String testInput = "11\n22\n33\n44\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);
        p = new Player();
        for (int i = 0; i < Board.SIZE; i++) {
            p.playTurn(b, Mark.O);
        }
        for (int i = 0; i < Board.SIZE; i++){
            assertEquals(Mark.O, b.getMark(i,i));
        }
    }

    @Test
    void playTurn_OnValidInputMarkChanging_ProperIsOnBoard() {
        for (int i = 0; i < Board.SIZE; i++){
            assumeTrue(b.getMark(i,i) == Mark.BLANK);
        }
        String testInput = "11\n22\n33\n44\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);
        p = new Player();
        for (int i = 0; i < Board.SIZE; i++) {
            Mark m = i % 2 == 0 ? Mark.X : Mark.O;
            p.playTurn(b, m);
        }
        for (int i = 0; i < Board.SIZE; i++){
            Mark m = i % 2 == 0 ? Mark.X : Mark.O;
            assertEquals(m, b.getMark(i,i));
        }
    }

    @Test
    void playTurn_OnOccupiedCoordinateSameMark_DoesNotCrash() {
        assumeTrue(b.getMark(0,0) == Mark.BLANK);
        assumeTrue(b.getMark(1,1) == Mark.BLANK);
        String testInput = "11\n11\n11\n11\n22\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);
        p = new Player();
        for (int i = 0; i < 2; i++) {
            p.playTurn(b, Mark.X);
            assumeTrue(b.getMark(0,0) == Mark.X);
        }
        for (int i = 0; i < 2; i++){
            assertEquals(Mark.X, b.getMark(i,i));
        }
    }

    @Test
    void playTurn_OnOccupiedCoordinateDifferentMark_DoesNotCrash() {
        assumeTrue(b.getMark(0,0) == Mark.BLANK);
        assumeTrue(b.getMark(1,1) == Mark.BLANK);
        String testInput = "11\n11\n11\n11\n22\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);
        p = new Player();
        for (int i = 0; i < 2; i++) {
            Mark m = i == 0 ? Mark.X : Mark.O;
            p.playTurn(b, m);
            if (i == 0)
                assumeTrue(b.getMark(0,0) == Mark.X);
        }
        assertEquals(Mark.X, b.getMark(0,0));
        assertEquals(Mark.O, b.getMark(1,1));
    }

    @Test
    void playTurn_OnOutOfRange_DoesNotCrash() {
        assumeTrue(b.getMark(0,0) == Mark.BLANK);
        assumeTrue(b.getMark(1,1) == Mark.BLANK);
        String testInput = "11\n16\n61\n66\n-11\n-11111\n11111\n22\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        System.setIn(inputStream);
        p = new Player();
        for (int i = 0; i < 2; i++) {
            p.playTurn(b, Mark.X);
            assumeTrue(b.getMark(0,0) == Mark.X);
        }
        for (int i = 0; i < 2; i++){
            assertEquals(Mark.X, b.getMark(i,i));
        }
    }
}