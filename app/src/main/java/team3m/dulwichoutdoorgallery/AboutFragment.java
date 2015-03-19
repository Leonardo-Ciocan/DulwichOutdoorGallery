package team3m.dulwichoutdoorgallery;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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



        CardView lib1 = (CardView) rootView.findViewById(R.id.lib1);
        lib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Floating Action Button");
                alertDialogBuilder.setMessage("The MIT License (MIT)\n" +
                        "\n" +
                        "Copyright (c) 2014 Oleksandr Melnykov\n" +
                        "\n" +
                        "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n" +
                        "\n" +
                        "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n" +
                        "\n" +
                        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.");
                alertDialogBuilder.show();
            }
        });


        CardView lib2 = (CardView) rootView.findViewById(R.id.lib2);
        lib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Dropbox");
                alertDialogBuilder.setMessage("Copyright (c) 2013 Dropbox Inc., http://www.dropbox.com/\n" +
                        "\n" +
                        "Permission is hereby granted, free of charge, to any person obtaining\n" +
                        "a copy of this software and associated documentation files (the\n" +
                        "\"Software\"), to deal in the Software without restriction, including\n" +
                        "without limitation the rights to use, copy, modify, merge, publish,\n" +
                        "distribute, sublicense, and/or sell copies of the Software, and to\n" +
                        "permit persons to whom the Software is furnished to do so, subject to\n" +
                        "the following conditions:\n" +
                        "\n" +
                        "The above copyright notice and this permission notice shall be\n" +
                        "included in all copies or substantial portions of the Software.\n" +
                        "\n" +
                        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
                        "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF\n" +
                        "MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND\n" +
                        "NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE\n" +
                        "LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION\n" +
                        "OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION\n" +
                        "WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.");
                alertDialogBuilder.show();
            }
        });


        CardView lib3 = (CardView) rootView.findViewById(R.id.lib3);
        lib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Slidr");
                alertDialogBuilder.setMessage("The MIT License (MIT)\n" +
                        "Copyright (c) 2013 Brian Chan (http://bchanx.com)\n" +
                        "\n" +
                        "Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the \"Software\"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:\n" +
                        "\n" +
                        "The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.\n" +
                        "\n" +
                        "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.");
                alertDialogBuilder.show();
            }
        });

        aboutText.setText("England's oldest public art gallery has been an inspiration for its newest. Dulwich Outdoor Gallery, emerging in 2012, is the collection of art works on the streets in the vicinity of Dulwich Picture " +
                "Gallery, painted by some of the top street artists alive today whose works have a common theme - they are based on the 17th and 18th " +
                "century paintings in the permanent collection at Dulwich Picture Gallery.");
        return rootView;
    }
}
