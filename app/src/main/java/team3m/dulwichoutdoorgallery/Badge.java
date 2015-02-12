package team3m.dulwichoutdoorgallery;

public class Badge {
    String title;
    String description;
    int badgeID;
    boolean achieved;

    public int getBadgeID() {
        return badgeID;
    }

    public void setBadgeID(int badgeID) {
        this.badgeID = badgeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        title = Title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        description = Description;
    }

    public boolean getAchieved() {return achieved;}

    public Badge(String Title, String Description, int iconID, boolean achiv) {

        title = Title;
        description = Description;
        badgeID = iconID;
        achieved = achiv;
    }
}
