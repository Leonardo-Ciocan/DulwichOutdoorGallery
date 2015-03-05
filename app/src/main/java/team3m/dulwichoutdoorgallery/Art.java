package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Art {
    String Name;
    String Description;
    String Author;
    Art RelatedArt;
    ArrayList<String> Tags = new ArrayList<String>();
    double Latitude;
    double Longitude;
    LatLng position;
    String photo;

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    String isOnline;

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

    public double getLatitude(){
        return Latitude;
    }

    public double getLongitude(){
        return Longitude;
    }

    public LatLng getLocation(){ return position;}

    public String getPhoto() { return photo;}

    public Art(String name, String description, String author, Art relatedArt, ArrayList<String> tags, double latitude, double longitude, String photo) {
        Name = name;
        Description = description;
        Author = author;
        RelatedArt = relatedArt;
        Tags = tags;
        Latitude = latitude;
        Longitude = longitude;
        position = new LatLng(latitude,longitude);
        this.photo = photo;
    }

    public Art(String name, String description, String author, Art relatedArt, ArrayList<String> tags, double latitude, double longitude, String photo, String isOnline) {
        Name = name;
        Description = description;
        Author = author;
        RelatedArt = relatedArt;
        Tags = tags;
        Latitude = latitude;
        Longitude = longitude;
        position = new LatLng(latitude,longitude);
        this.photo = photo;
        this.isOnline = isOnline;
    }

    @Override
    public String toString() {
        return Author;
    }

    Drawable drawable = null;
    public Drawable getDrawable(Context c){
        if(drawable == null) {
            try{
                drawable = c.getResources().getDrawable(c.getResources().getIdentifier(getPhoto(), "drawable", c.getPackageName()));
            }
            catch (Exception ex){}
        }
        return drawable;
    }
}
