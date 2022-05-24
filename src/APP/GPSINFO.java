package APP;

public class  GPSINFO {

    //private static final DecimalFormat df = new DecimalFormat("#");

    private final float latitude;
    private final float longitude;
    private final char north_south;
    private final char west_east;
    private double Easting;
    private double Northing;


    public GPSINFO(Float latitude, Float longitude, char north_south, char west_east) {
        this.latitude = ((int)(latitude/100))+((latitude % 100)/60);
        this.longitude = ((int)(longitude / 100)) + ((longitude % 100) / 60);
        this.north_south = north_south;
        this.west_east = west_east;
        LAT();
    }


    @Override
    public String toString() {
        return latitude +
                " "+ north_south+ " " +longitude+
                " " + west_east+" /// UTM " + north_south + "->"+ Northing +" m. UTM "+ west_east+"-> "+ Easting+" m.";
    }

    public void LAT() {
        float Lat = latitude;
        float Lon;
        if(west_east=='W')
            Lon =-longitude;
        else
            Lon = longitude;
        int zone = (int) Math.floor(Lon / 6 + 31);
        char letter;
        if (Lat < -72)
            letter = 'C';
        else if (Lat < -64)
            letter = 'D';
        else if (Lat < -56)
            letter = 'E';
        else if (Lat < -48)
            letter = 'F';
        else if (Lat < -40)
            letter = 'G';
        else if (Lat < -32)
            letter = 'H';
        else if (Lat < -24)
            letter = 'J';
        else if (Lat < -16)
            letter = 'K';
        else if (Lat < -8)
            letter = 'L';
        else if (Lat < 0)
            letter = 'M';
        else if (Lat < 8)
            letter = 'N';
        else if (Lat < 16)
            letter = 'P';
        else if (Lat < 24)
            letter = 'Q';
        else if (Lat < 32)
            letter = 'R';
        else if (Lat < 40)
            letter = 'S';
        else if (Lat < 48)
            letter = 'T';
        else if (Lat < 56)
            letter = 'U';
        else if (Lat < 64)
            letter = 'V';
        else if (Lat < 72)
            letter = 'W';
        else
            letter = 'X';
        Easting = 0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180.0) * Math.sin(Lon * Math.PI / 180.0 - (6 * zone - 183) * Math.PI / 180.0)) / (1 - Math.cos(Lat * Math.PI / 180.0) * Math.sin(Lon * Math.PI / 180.0 - (6 * zone - 183) * Math.PI / 180.0))) * 0.9996 * 6399593.62 / Math.pow((1 + Math.pow(0.0820944379, 2.0) * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0)), 0.5) * (1 + Math.pow(0.0820944379, 2.0) / 2.0 * Math.pow((0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180.0) * Math.sin(Lon * Math.PI / 180.0 - (6 * zone - 183) * Math.PI / 180.0)) / (1 - Math.cos(Lat * Math.PI / 180.0) * Math.sin(Lon * Math.PI / 180.0 - (6 * zone - 183) * Math.PI / 180.0)))), 2.0) * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0) / 3.0) + 500000.0;
        Easting = Math.round(Easting * 100.0) / 100.0;
        Northing = (Math.atan(Math.tan(Lat * Math.PI / 180.0) / Math.cos((Lon * Math.PI / 180.0 - (6 * zone - 183) * Math.PI / 180.0))) - Lat * Math.PI / 180.0) * 0.9996 * 6399593.625 / Math.sqrt(1 + 0.006739496742 * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0)) * (1 + 0.006739496742 / 2.0 * Math.pow(0.5 * Math.log((1 + Math.cos(Lat * Math.PI / 180.0) * Math.sin((Lon * Math.PI / 180.0 - (6 * zone - 183) * Math.PI / 180.0))) / (1 - Math.cos(Lat * Math.PI / 180.0) * Math.sin((Lon * Math.PI / 180.0 - (6 * zone - 183) * Math.PI / 180.0)))), 2.0) * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0)) + 0.9996 * 6399593.625 * (Lat * Math.PI / 180.0 - 0.005054622556 * (Lat * Math.PI / 180.0 + Math.sin(2.0 * Lat * Math.PI / 180.0) / 2.0) + 4.258201531e-05 * (3.0 * (Lat * Math.PI / 180.0 + Math.sin(2.0 * Lat * Math.PI / 180.0) / 2.0) + Math.sin(2.0 * Lat * Math.PI / 180.0) * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0)) / 4.0 - 1.674057895e-07 * (5.0 * (3.0 * (Lat * Math.PI / 180.0 + Math.sin(2.0 * Lat * Math.PI / 180.0) / 2.0) + Math.sin(2.0 * Lat * Math.PI / 180.0) * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0)) / 4.0 + Math.sin(2.0 * Lat * Math.PI / 180.0) * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0) * Math.pow(Math.cos(Lat * Math.PI / 180.0), 2.0)) / 3.0);
        if (letter < 'M')
            Northing = Northing + 10000000;
        Northing = Math.round(Northing * 100.0) / 100.0;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
