package DAO;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import domain.Tag;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 07.12.2016.
 */
public class DAOimplMySQL implements DAO{

    private String host="jdbc:mysql://localhost:3306/url?useUnicode=true&characterEncoding=utf8";
    private String login="root";
    private String password="55555";

    @Override
    public void saveTags(String url, List<Tag> tags) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        try(Connection result=(Connection) DriverManager.getConnection(host, login, password)){
            Statement s=(Statement) result.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS `"+url+"`(id INT AUTO_INCREMENT PRIMARY KEY, tag VARCHAR(200), value VARCHAR(1500)) CHARACTER SET utf8;");
            String sqlStatement="";
            for(Tag t:tags)
                sqlStatement+="('"+t.getName()+"','"+t.getValue()+"'),";
            //delete last ','
            sqlStatement=sqlStatement.substring(0,sqlStatement.lastIndexOf(","));
            result.setAutoCommit(false);
            s.execute("DELETE FROM `" + url + "`;");
            s.execute("INSERT INTO `" + url + "` (tag, value) VALUES " + sqlStatement + ";");
            result.commit();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tag> getTags(String url) {
        List<Tag> list=new ArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        try(Connection result=(Connection) DriverManager.getConnection(host, login, password)){
            Statement s=(Statement) result.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM `"+url+"`;");
            if(rs==null)
                throw new RuntimeException("rs is null");
            while(rs.next()){
                Tag t=new Tag(rs.getString("tag"),rs.getString("value"));
                list.add(t);
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        return list;
    }
}
