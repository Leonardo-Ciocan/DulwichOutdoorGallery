package team3m.dulwichoutdoorgallery;

import java.util.ArrayList;

public class Core {
    public ArrayList<Art> Gallery = new ArrayList<Art>(){
        {
            add(new Art("name" , "description" , "Author" , null , new ArrayList<String>()));
        }
    };

    public ArrayList<Badge> badges = new ArrayList<Badge>(){
        {
            add(new Badge("title" , "description"));
        }
    };


}
