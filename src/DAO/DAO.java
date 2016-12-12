package DAO;

import domain.Tag;

import java.util.List;

/**
 * Created by Igor on 07.12.2016.
 */
public interface DAO {

    void saveTags(String url, List<Tag> tags);
    List<Tag> getTags(String url);
}
