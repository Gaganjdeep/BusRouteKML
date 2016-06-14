package ggn.com.busroutekml.UtillsG;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by gagandeep on 14 Jun 2016.
 */
public class MakerG
{
    private Double markerPositionLat, markerPositionLng;

    private String id;

    private Marker marker;

    public MakerG(LatLng markerPosition, String id, Marker marker)
    {
        this.markerPositionLat = markerPosition.latitude;
        this.markerPositionLng = markerPosition.longitude;
        this.id = id;
        this.marker = marker;
    }

    public LatLng getMarkerPosition()
    {
        return new LatLng(markerPositionLat, markerPositionLng);
    }

    public String getId()
    {
        return id;
    }

    public Marker getMarker()
    {
        return marker;
    }
}
