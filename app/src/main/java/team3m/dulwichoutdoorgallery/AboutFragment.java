package team3m.dulwichoutdoorgallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        TextView aboutText = (TextView) rootView.findViewById(R.id.aboutText);
        aboutText.setText("England's oldest public art gallery has been an inspiration for its newest. Dulwich Outdoor Gallery, emerging in 2012, is the collection of art works on the streets in the vicinity of Dulwich Picture " +
                "Gallery, painted by some of the top street artists alive today whose works have a common theme - they are based on the 17th and 18th " +
                "century paintings in the permanent collection at Dulwich Picture Gallery.");
        return rootView;
    }
}
