package services;

import DAO.DAOimplMySQL;
import DAO.DAO;
import domain.Tag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.ws.rs.*;
import java.io.IOException;
import java.util.*;


/**
 * Created by Igor on 06.12.2016.
 */

@Path("process")
public class Main {

    @POST
    @Produces("application/json; charset=utf-8")
    public String restService(@FormParam("url1") String url1, @FormParam("url2") String url2, @FormParam("url3") String url3){

        String regexp="^(https?:\\/\\/){1}"+                                // protocol
                "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|"+      // domain name
                "((\\d{1,3}\\.){3}\\d{1,3}))"+                           // OR ip (v4) address
                "(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*"+                       // port and path
                "(\\?[;&a-z\\d%_.~+=-]*)?"+                              // query string
                "(\\#[-a-z\\d_]*)?$";                                     // fragment locater

            if(url1.matches(regexp) && url2.matches(regexp) && url3.matches(regexp)){
                Thread thread1 = new Thread(() -> {
                    parseAndSaveDocToDB(url1);
                });
                Thread thread2 = new Thread(() -> {
                    parseAndSaveDocToDB(url2);
                });
                Thread thread3 = new Thread(() -> {
                    parseAndSaveDocToDB(url3);
                });

                thread1.start();
                thread2.start();
                thread3.start();

                try {
                    thread1.join();
                    thread2.join();
                    thread3.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Map<String, List<Tag>> map = new LinkedHashMap();
                map.put("Input 1 - " + url1, getTagsFromDB(url1));
                map.put("Input 2 - " + url2, getTagsFromDB(url2));
                map.put("Input 3 - " + url3, getTagsFromDB(url3));

                return toJSON(map);
        }
        else
            throw new RuntimeException("Url is invalid");

    }

    public void parseAndSaveDocToDB(String url){
        DAO conToDB=new DAOimplMySQL();
        conToDB.saveTags(url,parseDoc(url));
    }

    public List<Tag> parseDoc(String url){
        Document doc=null;
        try {
            doc = Jsoup.connect(url).get();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

        Elements el=doc.getAllElements();

        List<Tag> tags=new ArrayList();

        for(Element e:el) {
            tags.add(new Tag(e.nodeName(),e.attributes().html()));
        }

        return tags;
    }

    public List<Tag> getTagsFromDB(String url){
        DAO con=new DAOimplMySQL();
        return con.getTags(url);
    }

    public String toJSON(Map<String,List<Tag>> map){
        String str="[\n";
        Set<String> s=map.keySet();
        for(String key:s){
            str+="\n{\n\"url\":\""+key+"\",\n\"tags\":[\n";
            for(Tag t:map.get(key)){
                str+=t+",\n";
            }
            str=str.substring(0,str.lastIndexOf(","));
            str+="\n]\n},";
        }
        return str.substring(0,str.length()-1)+"\n]";
    }
}













