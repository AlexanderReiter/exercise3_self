package spw4.game2048;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameImplTest
{
    private GameImpl game;

    @BeforeEach
    private void beforeEach()
    {
        game = new GameImpl(0);
    }

    @Test
    public void testCreateGame()
    {
        assertNotNull(game);
    }

    @Test
    public void testDisplayEmptyField()
    {
        String output = "   .     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     .     .  \n";

        assertEquals(output, game.toString());
    }

    @Test
    public void testDisplayInitializedGame()
    {
        String output = "   .     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     2     .  \n";

        game.initialize();

        assertEquals(output, game.toString());
    }

    private void createOneDigitInMiddle()
    {
        game.setValueAt(1, 1, 2);
    }

    @Test
    public void testDisplayAfterMoveUp()
    {
        String output = "   .     2     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     2     .  \n";

        createOneDigitInMiddle();
        game.move(Direction.up);

        assertEquals(output, game.toString());
    }

    @Test
    public void testDisplayAfterMoveLeft()
    {
        String output = "   .     .     .     .  \n" +
                "   2     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     2     .  \n";

        createOneDigitInMiddle();
        game.move(Direction.left);

        assertEquals(output, game.toString());
    }

    @Test
    public void testDisplayAfterMoveRight()
    {
        String output = "   .     .     .     .  \n" +
                "   .     .     .     2  \n" +
                "   .     .     .     .  \n" +
                "   .     .     2     .  \n";

        createOneDigitInMiddle();
        game.move(Direction.right);

        assertEquals(output, game.toString());
    }

    @Test
    public void testDisplayAfterMoveUDown()
    {
        String output = "   .     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     .     .     .  \n" +
                "   .     2     2     .  \n";

        createOneDigitInMiddle();
        game.move(Direction.down);

        assertEquals(output, game.toString());
    }

    @Test
    public void testMoveLeftWithBlockOnLeftBorder()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        game.setValueAt(1, 0, 2);
        game.move(Direction.left);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveRightWithBlockOnRightBorder()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        game.setValueAt(1, 3, 2);
        game.move(Direction.right);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveUpWithBlockOnTopBorder()
    {
        int[][] field = {
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        game.setValueAt(0, 1, 2);
        game.move(Direction.up);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveDownWithBlockOnBottomBorder()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0}
        };

        game.setValueAt(3, 1, 2);
        game.move(Direction.down);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveLeftWithBlockOnBorderAndDirectlyAdjacent()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(1, 0, 2);
        game.setValueAt(1, 1, 2);
        game.move(Direction.left);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveRightWithBlockOnBorderAndDirectlyAdjacent()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(1, 3, 2);
        game.setValueAt(1, 2, 2);
        game.move(Direction.right);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveUpWithBlockOnBorderAndDirectlyAdjacent()
    {
        int[][] field = {
                {0, 4, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(0, 1, 2);
        game.setValueAt(1, 1, 2);
        game.move(Direction.up);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveDownWithBlockOnBorderAndDirectlyAdjacent()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 2, 0}
        };

        game.setValueAt(3, 1, 2);
        game.setValueAt(2, 1, 2);
        game.move(Direction.down);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveLeftWithBlocksInMiddle()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(1, 2, 2);
        game.setValueAt(1, 1, 2);
        game.move(Direction.left);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveRightWithBlocksInMiddle()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(1, 1, 2);
        game.setValueAt(1, 2, 2);
        game.move(Direction.right);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveUpWithBlocksInMiddle()
    {
        int[][] field = {
                {0, 4, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(1, 1, 2);
        game.setValueAt(2, 1, 2);
        game.move(Direction.up);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveDownWithBlocksInMiddle()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 2, 0}
        };

        game.setValueAt(1, 1, 2);
        game.setValueAt(2, 1, 2);
        game.move(Direction.down);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveUpWithBlocksAtBorder()
    {
        int[][] field = {
                {0, 4, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(0, 1, 2);
        game.setValueAt(3, 1, 2);
        game.move(Direction.up);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveDownWithBlocksAtBorder()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 2, 0}
        };

        game.setValueAt(0, 1, 2);
        game.setValueAt(3, 1, 2);
        game.move(Direction.down);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveLeftWithBlocksAtBorder()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(1, 0, 2);
        game.setValueAt(1, 3, 2);
        game.move(Direction.left);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testMoveRightWithBlocksAtBorder()
    {
        int[][] field = {
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 2, 0}
        };

        game.setValueAt(1, 0, 2);
        game.setValueAt(1, 3, 2);
        game.move(Direction.right);

        for (int i = 0; i < 4; i++)
            assertArrayEquals(field[i], game.getField()[i]);
    }

    @Test
    public void testGetMoves()
    {
        game.initialize();

        game.move(Direction.up);
        game.move(Direction.down);
        game.move(Direction.left);
        game.move(Direction.right);

        assertEquals(4, game.getMoves());
    }

    @Test
    public void testGetMovesNotIncWithoutMove()
    {
        game.setValueAt(0, 0, 2);
        game.move(Direction.up);

        assertEquals(0, game.getMoves());
    }

    @Test
    public void testGetValue()
    {
        game.setValueAt(0, 0, 2);
        game.setValueAt(1, 0, 2048);

        assertEquals(2, game.getValueAt(0, 0));
        assertEquals(0, game.getValueAt(0, 1));
        assertEquals(2048, game.getValueAt(1, 0));
    }

    @Test
    public void testGetScore()
    {
        game.setValueAt(0, 0, 2);
        game.setValueAt(1, 0, 2048);

        assertEquals(2050, game.getScore());
    }

    @Test
    public void testIsWon()
    {
        game.setValueAt(0, 0, 2048);
        assertTrue(game.isWon());
    }

    @Test
    public void testIsNotWon()
    {
        game.setValueAt(0, 0, 2);
        assertFalse(game.isWon());
    }

    @Test
    public void testIsOver()
    {
        int[][] field = {
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4}
        };

        game.setField(field);

        assertTrue(game.isOver());
    }
}
