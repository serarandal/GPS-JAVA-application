package APP;


public class CommandProcessor {
    public boolean stop = false;
    public int cont=0;
    boolean debug = false;
    boolean debug2 = true;
    GUI gui = new GUI();
    int a = 0;
    double time = 0;
    double ini = 0;
    public void processString(String str) {

        if (str.startsWith("$GPGGA")) {
            if(debug){
                System.out.println(str);
                System.out.println(cont);
            }
                String[] parts = str.split(",");
                if(!parts[2].isEmpty()){

                    Float latitude = Float.valueOf(parts[2]);
                    char north_south = parts[3].charAt(0);

                    Float longitude = Float.valueOf(parts[4]);
                    char west_east = parts[5].charAt(0);

                    GPSINFO gpsinfo = new GPSINFO(latitude, longitude, north_south, west_east);
                    if ( a == 1)
                    {
                        long fin = System.currentTimeMillis();
                        time = fin - ini;
                        time = time/1000;
                        a = 0;
                    }else
                    {
                        a = 1;
                        ini = System.currentTimeMillis();
                    }

                    gui.addPosicionRelative( gpsinfo.getLatitude(),gpsinfo.getLongitude(),time);

                    if(debug2)System.out.println(gpsinfo);
                }
                else
                if(debug2)System.out.println("No se ha encontrado la posicion GPS");
                cont++;
                if (cont == 29382 && debug)
                    System.out.println("TXT terminado sin fallos");
        }
    }
}
