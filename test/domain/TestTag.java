package domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Igor on 12.12.2016.
 */
public class TestTag {
    @Test
    public void testToString(){
        String rez=new Tag("tag","value").toString();
        assertEquals("{\n\"name\":\"tag\",\n\"value\":\"value\"\n}",rez);
    }
}
