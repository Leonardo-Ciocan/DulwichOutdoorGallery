package team3m.dulwichoutdoorgallery;

/**
 * A badge that the user can earn
 */
public class Badge {
    /**
     * The name of the badge
     */
    String title;

    /**
     * A short descrption of the badge
     */
    String description;

    /**
     * The id assigned to the badge
     */
    int badgeID;

    /**
     * Whether it is achieved or not
     */
    boolean achieved;

    public int getBadgeID() {
        return badgeID;
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

    /**
     * Create a new badge
     * @param Title The name shown to the user
     * @param Description What the badge is about
     * @param iconID The id for the icon
     * @param achiv Whether it is achieved
     */
    public Badge(String Title, String Description, int iconID, boolean achiv) {

        title = Title;
        description = Description;
        badgeID = iconID;
        achieved = achiv;
    }
}
