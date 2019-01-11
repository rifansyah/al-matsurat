package com.stud.reef.almatsurat_dhikr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DhikrFragment extends ListFragment {
    private Dhikr dhikr;
    private int fontSize;

    public static final String[] sCheeseStrings = {
            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
            "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
            "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
            "Aragon"
    };

    static DhikrFragment newInstance(Dhikr dhikr) {
        DhikrFragment df = new DhikrFragment();

        Bundle args = new Bundle();
        args.putParcelable("dhikr", (Dhikr)dhikr.clone());
        df.setArguments(args);

        return df;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dhikr = getArguments().getParcelable("dhikr");
        } else {
            dhikr = new Dhikr("empty", new ArrayList<String>());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dhikr, container, false);
        View tv = v.findViewById(R.id.text);

        WebView wv = v.findViewById(R.id.webview);
        WebView wv2 = v.findViewById(R.id.webview_translate);

        String template_ayah = "<html>" +
                "<head>" +
                "<style type=\"text/css\">" +
                "@font-face {" +
                "    font-family: MyFont;" +
                "    src: url(\"file:///android_asset/fonts/uthmani.otf\")" +
                "}" +
                "body {" +
                "    font-family: MyFont;" +
                "    font-size: 24px;" +
                "    direction:rtl;" +
                "    text-align: justify;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "%s"
                +
                "</body>" +
                "</html>";

        String template_translation = "<html>" +
                "<head>" +
                "<style type=\"text/css\">" +
                "ul, li { " +
                "        margin:0px;" +
                "        color: #464646;" +
                "        list-style-type: none;" +
                "        padding:0; }" +
                "body {" +
                "    font-size: 14px;" +
                "    text-align: justify;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<ul>" +
                "%s" +
                "</ul>"
                +
                "</body>" +
                "</html>";

        String ayah = String.format(template_ayah, dhikr.getAyah());
        String translation = "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < dhikr.getTranslation().size(); i++) {
            sb.append("<li>");
            sb.append(dhikr.getTranslation().get(i));
            sb.append("</li>");
        }
        translation = String.format(template_translation, sb.toString());

        wv.loadDataWithBaseURL("", ayah, "text/html", "utf-8", null);
        wv2.loadDataWithBaseURL("", translation, "text/html", "utf-8", null);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, sCheeseStrings));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }


}
