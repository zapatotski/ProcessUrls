package services;

import domain.Tag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Igor on 12.12.2016.
 */
public class TestMain {

    @Test(expected=RuntimeException.class)
    public void testRestServiceForValidUrlProtocol(){
        new Main().restService("http://vk.com","https://test.com","rest.ru");
    }

    @Test(expected=RuntimeException.class)
    public void testRestServiceForValidUrlFormat(){
        new Main().restService("http://vkcom","https://test","http:/test.ru");
    }

    @Test(expected=RuntimeException.class)
    public void testRestServiceForValidUrlEmptyValue(){
        new Main().restService("","","");
    }

    @Test
    public void testParseDoc(){
        List<Tag> rez=new Main().parseDoc("http://rpl-betfront.rhcloud.com/ajax/country/68");
        List<Tag> must=new ArrayList();
        must.add(new Tag("#document",""));
        must.add(new Tag("comps",""));
        must.add(new Tag("comp"," id=8"));
        must.add(new Tag("comp"," id=70"));
        assertEquals(must.toString(),rez.toString());
    }

    @Test
    public void testToJSON(){
        Map<String, List<Tag>> m=new LinkedHashMap();
        List<Tag> list=new ArrayList();
        list.add(new Tag("tag","value"));
        m.put("http://url1.ru",list);
        String rez=new Main().toJSON(m);
        assertEquals("[\n\n{\n\"url\":\"http://url1.ru\",\n\"tags\":[\n{\n\"name\":\"tag\",\n\"value\":\"value\"\n}\n]\n}\n]",rez);
    }
}
