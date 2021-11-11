import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class GameTest {
    Board b;
    Player p;
    Game g;
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
    void run_OnDraw_Blank(){
        String input = "11\n12\n13\n14\n" +
                "22\n21\n24\n23\n" +
                "32\n31\n34\n33\n" +
                "41\n42\n43\n44\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        p = new Player();
        g = new Game(p, p, new Renderer());

        assertEquals(Mark.BLANK, g.run());

    }

    @Test
    void run_OnXVictory_X(){
        String input = "11\n21\n12\n44\n13\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        p = new Player();
        g = new Game(p, p, new Renderer());

        assertEquals(Mark.X, g.run());
    }

    @Test
    void run_OnOVictory_O(){
        String input = "11\n21\n44\n22\n41\n23\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        p = new Player();
        g = new Game(p, p, new Renderer());

        assertEquals(Mark.O, g.run());
    }

}
