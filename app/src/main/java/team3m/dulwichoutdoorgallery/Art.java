package team3m.dulwichoutdoorgallery;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Represents a single piece of art
 */
public class Art {
    String shareID;

    /**
     * The title of the art
     */
    String Name;

    /**
     * A short description
     */
    String Description;

    /**
     * The artist of the art
     */
    String Author;

    /**
     * This is the picture gallery's art that this was inspired from
     */
    Art RelatedArt;

    /**
     * The latitude section of the location
     */
    double Latitude;

    /**
     * The longitude section of the location
     */
    double Longitude;

    /**
     * An object used to retrieve both latitude and longitude , for use with the API
     */
    LatLng position;

    /**
     * The corresponding drawable name
     */
    String photo;

    /**
     * If on dropbox , this will be a local path to the picture
     */
    String onlinePicture;

    public String getOnlinePicture() {
        return onlinePicture;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }


    public String getAuthor() {
        return Author;
    }


    public Art getRelatedArt() {
        return RelatedArt;
    }

    public void setRelatedArt(Art relatedArt) {
        RelatedArt = relatedArt;
    }

    public double getLatitude(){
        return Latitude;
    }

    public double getLongitude(){
        return Longitude;
    }

    public LatLng getLocation(){ return position;}

    public String getPhoto() { return photo;}

    public String getShareID() {
        return shareID;
    }

    public Art(String name, String description, String author, Art relatedArt, double latitude, double longitude, String photo, String shareID) {
        Name = name;
        Description = description;
        Author = author;
        RelatedArt = relatedArt;
        Latitude = latitude;
        Longitude = longitude;
        position = new LatLng(latitude,longitude);
        this.photo = photo;
        this.shareID = shareID;
    }

    public Art(String name, String description, String author, Art relatedArt, double latitude, double longitude, String photo, String onlinePicture, String shareID) {
        Name = name;
        Description = description;
        Author = author;
        RelatedArt = relatedArt;
        Latitude = latitude;
        Longitude = longitude;
        position = new LatLng(latitude,longitude);
        this.photo = photo;
        this.onlinePicture = onlinePicture;
        this.shareID = shareID;
    }

    @Override
    public String toString() {
        return Author+" "+Name;
    }

    Drawable drawable = null;
    public Drawable getDrawable(Context c){
        if(drawable == null) {
            try{
                drawable = c.getResources().getDrawable(c.getResources().getIdentifier(getPhoto()+"_thumb", "drawable", c.getPackageName()));
            }
            catch (Exception ex){}
        }
        return drawable;
    }
}