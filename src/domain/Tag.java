package domain;

/**
 * Created by Igor on 06.12.2016.
 */
public class Tag {

    private String name;
    private String value;

    public Tag(String name, String value){
        this.name=name;
        this.value=value.replaceAll("'","");        //' will be incorrect when saving to DB
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //JSON
    public String toString(){
        return "{\n\"name\":\""+name+"\",\n\"value\":\""+value.replaceAll("}","").replaceAll("\"","").replaceAll("\n","").replaceAll("\r","").replaceAll("'","")+"\"\n}";
    }

}
