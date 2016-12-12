package DAO;

import domain.Tag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Igor on 12.12.2016.
 */
public class TestDAOimplMySQL {
    @Test
    public void testSaveToDBAndGetFromDB(){
        List<Tag> must=new ArrayList();
        must.add(new Tag("#document",""));
        must.add(new Tag("comps",""));
        must.add(new Tag("comp"," id=8"));
        must.add(new Tag("comp"," id=70"));
        DAOimplMySQL dao=new DAOimplMySQL();
        dao.saveTags("http://test.class",must);
        assertEquals(must.toString(),dao.getTags("http://test.class").toString());
    }
}
