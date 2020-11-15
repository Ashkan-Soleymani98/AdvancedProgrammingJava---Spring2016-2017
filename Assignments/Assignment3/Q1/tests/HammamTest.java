import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HammamTest {

    @Test
    public void addTest1() throws Exception {
        Hammam<Integer> hammam = new Hammam<>();

        hammam.add(1);
        hammam.add(3);
        hammam.add(4);
        hammam.add(5);

        List<Integer> expected = Arrays.asList(1 , 3 , 4 , 5);

        assertThat(hammam.getElementsInComparingOrder() , is(expected));
        assertThat(hammam.getElementsInInputOrder() , is(expected));
    }

    @Test
    public void addTest2() throws Exception{
        Hammam<String> hammam = new Hammam<>();

        hammam.add("ooo");
        hammam.add("qwe");
        hammam.add("das");

        List<String> expected = Arrays.asList("ooo" , "qwe" , "das");

        assertThat(hammam.getElementsInInputOrder() , is(expected));
        assertThat(hammam.getElementsInComparingOrder() , is(expected));
    }

    @Test
    public void getMinTest1() throws Exception {
        Hammam<Integer> hammam = new Hammam<>();

        hammam.add(2);
        hammam.add(0);
        hammam.add(100);
        hammam.add(-1);

        assertEquals(-1, (int)hammam.getMin());
    }

    @Test
    public void getMinTest2() throws Exception{
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");
        hammam.add("BB");

        assertEquals("AAc" , hammam.getMin());

        assertThat(hammam.getElementsInInputOrder() , not(hammam.getElementsInComparingOrder()));
    }

    @Test
    public void getLastTest1() throws Exception {
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");

        assertEquals("pot" , hammam.getLast(false));

        List<String> expected = Arrays.asList("Abc" , "AAc" , "LL" , "pot");

        assertThat(hammam.getElementsInComparingOrder() , is(expected));
        assertThat(hammam.getElementsInInputOrder() , is(expected));

    }

    @Test
    public void getLastTest2() throws Exception{
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");

        assertEquals("pot" , hammam.getLast(true));

        List<String> expected = Arrays.asList("Abc" , "AAc" , "LL");

        assertThat(hammam.getElementsInComparingOrder() , is(expected));
        assertThat(hammam.getElementsInInputOrder() , is(expected));
    }

    @Test
    public void getFirstTest1() throws Exception {
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");

        assertEquals("Abc" , hammam.getFirst(false));

        List<String> expected = Arrays.asList("Abc" , "AAc" , "LL" , "pot");

        assertThat(hammam.getElementsInComparingOrder() , is(expected));
        assertThat(hammam.getElementsInInputOrder() , is(expected));
    }

    @Test
    public void getFirstTest2() throws Exception {
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");

        assertEquals("Abc" , hammam.getFirst(true));

        List<String> expected = Arrays.asList("AAc" , "LL" , "pot");

        assertThat(hammam.getElementsInComparingOrder() , is(expected));
        assertThat(hammam.getElementsInInputOrder() , is(expected));
    }


    @Test
    public void getLessTest1() throws Exception {
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");

        String[] strings = {"AAc" , "Abc"};

        assertThat( strings , is(hammam.getLess("LL" , true)) );

        List<String> expected = Arrays.asList("LL" , "pot");

        assertThat(hammam.getElementsInComparingOrder() , is(expected));
        assertThat(hammam.getElementsInInputOrder() , is(expected));
    }

    @Test
    public void getLessTest2() throws Exception {
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");

        String[] strings = {"AAc" , "Abc"};

        assertThat( strings , is(hammam.getLess("LL" , false)) );

        List<String> expectedInComparingOrder = Arrays.asList("AAc" , "Abc" , "LL" , "pot");
        List<String> expectedInInputOrder = Arrays.asList("Abc" , "AAc" , "LL" ,"pot");

        assertThat(hammam.getElementsInComparingOrder() , is(expectedInComparingOrder));
        assertThat(hammam.getElementsInInputOrder() , is(expectedInInputOrder));
    }

    @Test
    public void getRecentlyRemoved() throws Exception {
        Hammam<String> hammam = new Hammam<>();

        hammam.add("Abc");
        hammam.add("AAc");
        hammam.add("LL");
        hammam.add("pot");

        String[] strings = {"AAc" , "Abc"};

        assertThat(strings , is(hammam.getLess("LL" , true)) );

        List<String> expected = Arrays.asList("Abc" , "AAc");

        assertThat(Arrays.asList(hammam.getRecentlyRemoved(2)) , is(expected));
        assertThat(Arrays.asList(hammam.getRecentlyRemoved(3)) , is(expected));

        expected = Arrays.asList("Abc");

        assertThat(Arrays.asList(hammam.getRecentlyRemoved(1)) , is(expected));

        hammam.getFirst(true);

        expected = Arrays.asList("LL" , "Abc" , "AAc");

        assertThat(Arrays.asList(hammam.getRecentlyRemoved(3)) , is(expected));
    }

    @Test
    public void throwsExceptionWhenInvalidNumbersAreGiven() {
        try {
            Hammam<Integer> hammam = new Hammam<>();

            hammam.getFirst(true);
            fail("Should throw an exception if ArrayList is empty!");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertTrue(e.getMessage().equals("Empty!"));
        }

        try {
            Hammam<Integer> hammam = new Hammam<>();

            hammam.add(2);

            hammam.getFirst(true);

            hammam.getLast(true);
            fail("Should throw an exception if ArrayList is empty!");
        }catch (Exception e){
            assertTrue(e instanceof IllegalStateException);
            assertTrue(e.getMessage().equals("Empty!"));
        }

        try {
            Hammam<Integer> hammam = new Hammam<>();

            hammam.getMin();
            fail("Should throw an exception if ArrayList is empty!");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertTrue(e.getMessage().equals("Empty!"));
        }
    }





}