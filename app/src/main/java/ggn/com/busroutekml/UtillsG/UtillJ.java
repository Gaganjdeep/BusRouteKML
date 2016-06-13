package ggn.com.busroutekml.UtillsG;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gagandeep on 13 Jun 2016.
 */
public class UtillJ
{

    public static void moveCamera(GoogleMap googleMap, LatLng latLng)
    {
//        LatLng centerSpain = new LatLng(35.610842, -77.404970);
        CameraPosition spainfocus = new CameraPosition.Builder().target(latLng)
                .zoom(12f)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(spainfocus));
    }
    public static Toast toastG;


    public static void showToast(String msg, Context context, boolean center)
    {
        if (toastG != null)
        {
            toastG.cancel();
        }
        toastG = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        if (center)
        {
            toastG.setGravity(Gravity.CENTER, 0, 0);
        }

        toastG.show();
    }











}
