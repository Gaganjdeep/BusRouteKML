package ggn.com.busroutekml.UtillsG;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ggn.com.busroutekml.R;

/**
 * Created by gagandeep on 13 Jun 2016.
 */
public class ShowBusRoute extends AsyncTask<String, Void, Void>
{
    private long zindex;

    Vector<Polyline>        lines;
    Vector<PolylineOptions> border;
    Vector<Vector<LatLng>>  border_fragment;


    private Context   context;
    private GoogleMap googleMap;
//String kmlFile;

    public ShowBusRoute(Context context, GoogleMap googleMap)
    {
        this.context = context;
        this.googleMap = googleMap;


//        kmlFile= String.valueOf(context.getResources().getIdentifier("route_g", "raw", context.getPackageName()));
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        lines = new Vector<>();
        border = new Vector<>();
        border_fragment = new Vector<>();
    }

    @Override
    protected Void doInBackground(String... params)
    {
        // PARSER STARTS
        try
        {
            InputStream     inputStream = context.getResources().openRawResource(R.raw.route_g);
            DocumentBuilder docBuilder  = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document        document    = docBuilder.parse(inputStream);
            NodeList        coordList;

            if (document == null) return null;

            //Separate data by the <coordinates> tag.
            coordList = document.getElementsByTagName("coordinates");

            for (int i = 0; i < coordList.getLength(); i++)
            {
                String[] coordinatePairs = coordList.item(i)
                        .getFirstChild()
                        .getNodeValue()
                        .trim()
                        .split(" ");
                Vector<LatLng> positions = new Vector<>();
                for (String coord : coordinatePairs)
                {
                    positions.add(new LatLng(Double.parseDouble(coord.split(",")[1]),
                            Double.parseDouble(coord.split(",")[0])));
                }
                border_fragment.add(positions);
            }
        }
        catch (ParserConfigurationException | IOException | SAXException e)
        {
            e.printStackTrace();
        }
        return null;
        // PARSER ENDS
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);
        // THE PAINTING IS DONE HERE SINCE WE NEED TO ACCESS THE GUI AND WE CANNOT DO THAT
        // UNLESS WE'RE INSIDE THE onPostExecute METHOD AND WE HAVE ACCESS TO THE GUI

        int i;
        for (i = 0; i < border_fragment.size(); i++)
        {
            PolylineOptions dots = new PolylineOptions();
            for (int j = 0; j < border_fragment.get(i).size(); j++)
            {
                dots.add(border_fragment.get(i).get(j));
            }
            border.add(dots);
        }

        for (i = 0; i < border.size(); i++)
        {
            lines.add(googleMap.addPolyline(border.get(i)));
        }

        for (i = 0; i < lines.size(); i++)
        {
            // The following are up to you, just remember that if you have two lines drawn in
            // the same position, only the one with higher zindex will be visible. It's up to
            // you what you want to do with zindex.
            lines.get(i).setWidth(5);
            lines.get(i).setColor(Color.RED);
            lines.get(i).setGeodesic(false);
            lines.get(i).setVisible(true);
            lines.get(i).setZIndex(zindex);
        }
        zindex++;


        UtillJ.moveCamera(googleMap, GlobalConstant.DEFAULT_POINT);

    }

}
