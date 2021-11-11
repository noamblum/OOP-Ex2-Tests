import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class BoardTest {

    Board b;

    @BeforeEach
    void setUp() {
        b = new Board();
    }

    @Test
    void putMark_OnValidCell_true() {
        assertTrue(b.putMark(Mark.O, 0, 0));
        assertTrue(b.putMark(Mark.X, 0, 1));
        assertTrue(b.putMark(Mark.O, 0, 2));
        assertTrue(b.putMark(Mark.X, 3, 3));
    }

    @Test
    void putMark_OnOccupiedCell_false() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.X, 1, 1));
        assertFalse(b.putMark(Mark.O, 0, 0));
        assertFalse(b.putMark(Mark.X, 0, 0));
        assertFalse(b.putMark(Mark.O, 1, 1));
        assertFalse(b.putMark(Mark.X, 1, 1));
    }

    @Test
    void putMark_OnOutOfRange_false() {
        assertFalse(b.putMark(Mark.O, -1, 0));
        assertFalse(b.putMark(Mark.X, 0, -1));
        assertFalse(b.putMark(Mark.O, -1, -1));
        assertFalse(b.putMark(Mark.O, 4, 0));
        assertFalse(b.putMark(Mark.X, 0, 4));
        assertFalse(b.putMark(Mark.O, 4, 4));
        assertFalse(b.putMark(Mark.X, -1, 4));
    }

    @Test
    void getMark_OnValidIndex_CellContent() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.X, 1, 1));
        assertEquals(Mark.O, b.getMark(0, 0));
        assertEquals(Mark.X, b.getMark(1, 1));
        assertEquals(Mark.BLANK, b.getMark(2, 2));
    }

    @Test
    void getMark_OnConsecutiveCalls_CellContentUnchanged() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.X, 1, 1));
        assertEquals(b.getMark(0, 0), b.getMark(0, 0));
        assertEquals(b.getMark(1, 1), b.getMark(1, 1));
        assertEquals(b.getMark(2, 2), b.getMark(2, 2));
    }

    @Test
    void getMark_OnOutOfRange_Blank() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.X, 1, 1));
        assertEquals(Mark.BLANK, b.getMark(-1, 0));
        assertEquals(Mark.BLANK, b.getMark(0, -1));
        assertEquals(Mark.BLANK, b.getMark(-1, -1));
        assertEquals(Mark.BLANK, b.getMark(Board.SIZE, 0));
        assertEquals(Mark.BLANK, b.getMark(0, Board.SIZE));
        assertEquals(Mark.BLANK, b.getMark(Board.SIZE, Board.SIZE));


    }

    @Test
    void gameEnded_OnNonFullBoardNoWInner_False() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Mark m = ((row + col) % 2) == 0 ? Mark.X : Mark.O;
                assumeTrue(b.putMark(m, row, col));
                assertFalse(b.gameEnded());
            }
        }

        for (int row = 2; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (row == Board.SIZE - 1 && col == Board.SIZE - 1) continue;
                Mark m = ((row + col) % 2) == 0 ? Mark.O : Mark.X;
                assumeTrue(b.putMark(m, row, col));
                assertFalse(b.gameEnded());
            }
        }
    }

    @Test
    void gameEnded_OnFullBoardNoWInner_True() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Mark m = ((row + col) % 2) == 0 ? Mark.X : Mark.O;
                assumeTrue(b.putMark(m, row, col));
            }
        }

        for (int row = 2; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Mark m = ((row + col) % 2) == 0 ? Mark.O : Mark.X;
                assumeTrue(b.putMark(m, row, col));
            }
        }
        assertTrue(b.gameEnded());
    }

    @Test
    void gameEnded_OnNonFullBoardXWon_True() {
        for (int i = 0; i < Board.WIN_STREAK; i ++){
            assumeTrue(b.putMark(Mark.X, 0, i));
        }
        assertTrue(b.gameEnded());
    }

    @Test
    void gameEnded_OnFullBoardOWon_True() {
        for (int i = 0; i < Board.WIN_STREAK; i ++){
            assumeTrue(b.putMark(Mark.O, 0, i));
        }
        assertTrue(b.gameEnded());
    }

    @Test
    void getWinner_GameInProgress_Blank() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Mark m = ((row + col) % 2) == 0 ? Mark.X : Mark.O;
                assumeTrue(b.putMark(m, row, col));
                assertEquals(Mark.BLANK, b.getWinner());
            }
        }

        for (int row = 2; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (row == Board.SIZE - 1 && col == Board.SIZE - 1) continue;
                Mark m = ((row + col) % 2) == 0 ? Mark.O : Mark.X;
                assumeTrue(b.putMark(m, row, col));
                assertEquals(Mark.BLANK, b.getWinner());
            }
        }
    }

    @Test
    void getWinner_Draw_Blank() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Mark m = ((row + col) % 2) == 0 ? Mark.X : Mark.O;
                assumeTrue(b.putMark(m, row, col));
            }
        }

        for (int row = 2; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Mark m = ((row + col) % 2) == 0 ? Mark.O : Mark.X;
                assumeTrue(b.putMark(m, row, col));
            }
        }
        assumeTrue(b.gameEnded());
        assertEquals(Mark.BLANK, b.getWinner());
    }

    @Test
    void getWinner_RowOfXConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 0));
        assumeTrue(b.putMark(Mark.X, 0, 1));
        assumeTrue(b.putMark(Mark.X, 0, 2));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_RowOfXNonConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 0));
        assumeTrue(b.putMark(Mark.X, 0, 2));
        assumeTrue(b.putMark(Mark.X, 0, 3));
        assumeTrue(b.putMark(Mark.X, 0, 1));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_RowOfOConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.O, 0, 1));
        assumeTrue(b.putMark(Mark.O, 0, 2));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_RowOfONonConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.O, 0, 2));
        assumeTrue(b.putMark(Mark.O, 0, 3));
        assumeTrue(b.putMark(Mark.O, 0, 1));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_ColumnOfXConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 0));
        assumeTrue(b.putMark(Mark.X, 1, 0));
        assumeTrue(b.putMark(Mark.X, 2, 0));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_ColumnOfXNonConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 0));
        assumeTrue(b.putMark(Mark.X, 2, 0));
        assumeTrue(b.putMark(Mark.X, 3, 0));
        assumeTrue(b.putMark(Mark.X, 1, 0));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_ColumnOfOConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.O, 1, 0));
        assumeTrue(b.putMark(Mark.O, 2, 0));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_ColumnOfONonConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.O, 2, 0));
        assumeTrue(b.putMark(Mark.O, 3, 0));
        assumeTrue(b.putMark(Mark.O, 1, 0));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_DiagonalOfXConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 0));
        assumeTrue(b.putMark(Mark.X, 1, 1));
        assumeTrue(b.putMark(Mark.X, 2, 2));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_DiagonalOfXNonConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 0));
        assumeTrue(b.putMark(Mark.X, 2, 2));
        assumeTrue(b.putMark(Mark.X, 3, 3));
        assumeTrue(b.putMark(Mark.X, 1, 1));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_DiagonalOfOConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.O, 1, 1));
        assumeTrue(b.putMark(Mark.O, 2, 2));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_DiagonalOfONonConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 0));
        assumeTrue(b.putMark(Mark.O, 2, 2));
        assumeTrue(b.putMark(Mark.O, 3, 3));
        assumeTrue(b.putMark(Mark.O, 1, 1));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_AntiDiagonalOfXConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 2));
        assumeTrue(b.putMark(Mark.X, 1, 1));
        assumeTrue(b.putMark(Mark.X, 2, 0));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_AntiDiagonalOfXNonConsecutive_X() {
        assumeTrue(b.putMark(Mark.X, 0, 3));
        assumeTrue(b.putMark(Mark.X, 3, 0));
        assumeTrue(b.putMark(Mark.X, 2, 1));
        assumeTrue(b.putMark(Mark.X, 1, 2));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_AntiDiagonalOfOConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 2));
        assumeTrue(b.putMark(Mark.O, 1, 1));
        assumeTrue(b.putMark(Mark.O, 2, 0));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_AntiDiagonalOfONonConsecutive_O() {
        assumeTrue(b.putMark(Mark.O, 0, 3));
        assumeTrue(b.putMark(Mark.O, 3, 0));
        assumeTrue(b.putMark(Mark.O, 2, 1));
        assumeTrue(b.putMark(Mark.O, 1, 2));

        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }

    @Test
    void getWinner_AllDirectionsX_X() {
        assumeTrue(b.putMark(Mark.X, 0, 1));
        assumeTrue(b.putMark(Mark.X, 0, 2));
        assumeTrue(b.putMark(Mark.X, 1, 0));
        assumeTrue(b.putMark(Mark.X, 2, 0));
        assumeTrue(b.putMark(Mark.X, 1, 2));
        assumeTrue(b.putMark(Mark.X, 2, 1));
        assumeFalse(b.gameEnded());
        assumeTrue(b.putMark(Mark.X, 1, 1));
        assumeTrue(b.gameEnded());
        assertEquals(Mark.X, b.getWinner());
    }

    @Test
    void getWinner_AllDirectionsO_O() {
        assumeTrue(b.putMark(Mark.O, 0, 1));
        assumeTrue(b.putMark(Mark.O, 0, 2));
        assumeTrue(b.putMark(Mark.O, 1, 0));
        assumeTrue(b.putMark(Mark.O, 2, 0));
        assumeTrue(b.putMark(Mark.O, 1, 2));
        assumeTrue(b.putMark(Mark.O, 2, 1));
        assumeFalse(b.gameEnded());
        assumeTrue(b.putMark(Mark.O, 1, 1));
        assumeTrue(b.gameEnded());
        assertEquals(Mark.O, b.getWinner());
    }
}