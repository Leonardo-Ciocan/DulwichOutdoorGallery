package team3m.dulwichoutdoorgallery;

import java.util.ArrayList;

public class Art {
    String Name;
    String Description;
    //location here
    String Author;
    Art RelatedArt;
    ArrayList<String> Tags = new ArrayList<>();

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Art getRelatedArt() {
        return RelatedArt;
    }

    public void setRelatedArt(Art relatedArt) {
        RelatedArt = relatedArt;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }

    public Art(String name, String description, String author, Art relatedArt, ArrayList<String> tags) {

        Name = name;
        Description = description;
        Author = author;
        RelatedArt = relatedArt;
        Tags = tags;
    }
}
