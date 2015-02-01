package team3m.dulwichoutdoorgallery;

public class Badge {
    String Title;
    String Description;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Badge(String title, String description) {

        Title = title;
        Description = description;
    }
}
