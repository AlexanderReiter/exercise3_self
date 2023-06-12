package spw4.game2048;

import java.util.Arrays;
import java.util.Random;

public class GameImpl implements Game
{
    private final int SIZE = 4;
    Random r;
    int[][] field = new int[SIZE][SIZE];
    int cntMoves = 0;

    public GameImpl()
    {
        r = new Random();
        for (int x = 0; x < SIZE; x++)
        {
            Arrays.fill(field[x], 0);
        }
    }

    public GameImpl(int seed)
    {
        r = new Random(seed);
    }

    public int getMoves()
    {
        return cntMoves;
    }

    public int getScore()
    {
        int sum = 0;

        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                sum += field[x][y];
            }
        }

        return sum;
    }

    public int getValueAt(int row, int col)
    {
        return field[row][col];
    }

    public int[][] getField()
    {
        return field;
    }

    public void setField(int[][] field)
    {
        this.field = field;
    }

    public void setValueAt(int row, int col, int value)
    {
        field[row][col] = value;
    }

    public boolean isOver()
    {
        if (isWon())
        {
            return true;
        }

        // check for empty fields
        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                if (field[x][y] == 0)
                {
                    return false;
                }
            }
        }

        // check if there are same values next to each other
        for (int x = 0; x < SIZE - 1; x++)
        {
            for (int y = 0; y < SIZE - 1; y++)
            {
                if (field[x][y] == field[x + 1][y] || field[x][y] == field[x][y + 1])
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isWon()
    {
        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                if (field[x][y] == 2048)
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                if (field[x][y] == 0)
                {
                    sb.append("   .  ");
                } else
                {
                    sb.append(String.format("%1$4s", field[x][y]));
                    sb.append("  ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void initialize()
    {
        spawnRandomBlock();
    }

    public void spawnRandomBlock()
    {
        int pos = r.nextInt(16);

        int x = pos % SIZE;
        int y = pos / SIZE;

        while (field[x][y] != 0)
        {
            pos = r.nextInt(16);
            x = pos % SIZE;
            y = pos / SIZE;
        }

        int probability = r.nextInt(10);
        field[x][y] = probability == 0 ? 4 : 2;
    }

    public void move(Direction direction)
    {
        // default directions: up and left
        int from = 0;
        int to = SIZE - 1;

        if (direction == Direction.down || direction == Direction.right) // invert for other directions
        {
            from = SIZE - 1;
            to = 0;
        }

        boolean isHorizontalMovement = direction == Direction.right || direction == Direction.left;

        boolean hasMoved = false;
        for (int i = 0; i < SIZE; i++)
        {
            if (isHorizontalMovement)
            {
                if (moveRow(i, from, to))
                    hasMoved = true;
            } else
            {
                if (moveColumn(i, from, to))
                    hasMoved = true;
            }
        }

        if (hasMoved)
        {
            spawnRandomBlock();
            cntMoves++;
        }
    }

    private boolean moveRow(int row, int from, int to)
    {
        if (from == to)
            return false;

        boolean hasChanged = false;
        int direction = from < to ? 1 : -1;

        int pos = from;
        while (pos * direction <= to && field[row][pos] == 0)
            pos += direction;

        if (!(pos * direction > to || pos == from))
        {
            field[row][from] = field[row][pos];
            field[row][pos] = 0;
            hasChanged = true;
        }

        pos += direction;

        while (pos * direction <= to && field[row][pos] == 0)
            pos += direction;

        if (pos * direction <= to && field[row][pos] == field[row][from])
        {
            field[row][from] *= 2;
            field[row][pos] = 0;
            hasChanged = true;
        }

        if (moveRow(row, from + direction, to))
            hasChanged = true;

        return hasChanged;
    }

    private boolean moveColumn(int col, int from, int to)
    {
        if (from == to)
            return false;

        boolean hasChanged = false;
        int direction = from < to ? 1 : -1;

        int pos = from;
        while (pos * direction <= to && field[pos][col] == 0)
            pos += direction;

        if (!(pos * direction > to || pos == from))
        {
            field[from][col] = field[pos][col];
            field[pos][col] = 0;
            hasChanged = true;
        }

        pos += direction;

        while (pos * direction <= to && field[pos][col] == 0)
            pos += direction;

        if (pos * direction <= to && field[pos][col] == field[from][col])
        {
            field[from][col] *= 2;
            field[pos][col] = 0;
            hasChanged = true;
        }

        if (moveColumn(col, from + direction, to))
            hasChanged = true;

        return hasChanged;
    }
}
