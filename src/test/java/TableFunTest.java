import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static junit.framework.TestCase.assertEquals;

public class TableFunTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    TableFun expected = new TableFun();

    PrintStream oldOut;

    @Before
    public void setUpStreams() {
        expected.add(8,9);
        oldOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @After
    public void cleanUpStreams() {
        expected.clear();
        System.setOut(oldOut);
    }

    @Test
    public void add() throws IllegalArgumentException {
        assertEquals(9.0, expected.getY(8), 0.001);

        expected.clear();
        expected.add(0,0);
        expected.add(3,6);
        assertEquals(0.0, expected.getY(0), 0.001);
        assertEquals(6, expected.getY(3), 0.001);

        expectedException.expect(IllegalArgumentException.class); //пытаемся добавить х, который уже есть
        expectedException.expectMessage("This x already exists. Cannot be repeated");
        expected.add(0,10);
    }

    @Test
    public void remove() {
        TableFun actual = new TableFun();
        expected.remove(8,9);
        assertEquals(expected.size(), actual.size());

        expected.add(0, 9);
        expectedException.expect(IllegalArgumentException.class); //пытаемся удалить пару, которой нет
        expectedException.expectMessage("This x does not have such y");
        expected.remove(0, 10);
    }

    @Test
    public void showAll() {
        expected.add(7,9);
        expected.showAll();
        assertEquals("Pair 1: (8.0 ,9.0)\r\nPair 2: (7.0 ,9.0)\r\n", outContent.toString());
    }

    @Test
    public void findX() {
        expected.add(1,1);
        expected.add(6,7);
        assertEquals(1, expected.findX(2).getX(), 0.01);

        expected.clear();
        expectedException.expect(IllegalArgumentException.class); //не можем найти x, если таблица пуста
        expectedException.expectMessage("Table is empty");
        expected.findX(5);
    }

    @Test
    public void interpolate() {
        expected.clear();
        expected.add(0, 0);
        expected.add(1,1);
        expected.add(2,2);

        assertEquals(3.0, expected.interpolate(3), 0.001);

        expected.clear();
        expected.add(0, 0);
        expected.add(1,1);
        expected.add(2,4);
        expected.add(10, 100);

        assertEquals(25.0, expected.interpolate(5), 0.001);

        expected.clear();
        expected.add(1, 2);
        expected.add(6,-1.5);
        expected.add(8.7,12.55);

        assertEquals(14.8011, expected.interpolate(9), 0.001);

        expected.clear();
        expectedException.expect(IllegalArgumentException.class); //не можем применять интерполяцию, когда размер таблицы меньше 2
        expectedException.expectMessage("Table size is very small");
        expected.interpolate(5);
    }
}