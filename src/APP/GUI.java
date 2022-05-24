package APP;

import com.sun.xml.internal.bind.v2.model.core.NonElement;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Toolkit;
import java.text.DecimalFormat;

public class GUI {
    private JFrame frame = new JFrame();
    private JLayeredPane lpane = new JLayeredPane();
    private JPanel panelBlue = new JPanel();
    private JPanel panelGreen = new JPanel();
    private JPanel panelText = new JPanel();
    private Image mapa;
    private Image icono;
    private final double R = 6371e3;
    private double oldlat = 0;
    private double oldlon = 0;

    public GUI()
    {
        try{
           // BufferedImage mapa = ImageIO.read(new File("mapa.jpg"));
            BufferedImage icono = ImageIO.read(new File("icon.png"));
            Image map = Toolkit.getDefaultToolkit().getImage(new URL("https://maps.googleapis.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=640x640&maptype=roadmap&key=YOURAPIKEYHERE"));
            Image resizeIcono = icono.getScaledInstance(30,30,Image.SCALE_DEFAULT);
            this.mapa = map;
            this.icono = resizeIcono;
            panelBlue.add(new JLabel(new ImageIcon(mapa)));
            panelGreen.add(new JLabel(new ImageIcon(resizeIcono)));
            panelText.add(new JLabel("speed"));
            frame.setPreferredSize(new Dimension(800, 640));
            frame.setLayout(new BorderLayout());
            frame.add(lpane, BorderLayout.CENTER);
            lpane.setBounds(0, 0, 800, 640);
            panelBlue.setBackground(Color.WHITE);
            panelBlue.setBounds(0, 0, 800, 640);
            panelBlue.setOpaque(true);
            panelGreen.setBounds(400, 100, 30, 40);
            panelGreen.setOpaque(false);
            panelText.setBounds(0,0,100,30);
            panelText.setOpaque(true);
            lpane.add(panelBlue, new Integer(0), 0);
            lpane.add(panelGreen, new Integer(1), 0);
            lpane.add(panelText,new Integer(2),0);
            frame.pack();
            frame.setVisible(true);

        }catch(IOException e)
        {
            e.printStackTrace();
        }

    }
    public void newPosition(double x , double y,double speed,int maxspeedStatus)
    {

        panelGreen.setBounds(400,320,30,40);
        try
        {
            this.mapa = Toolkit.getDefaultToolkit().getImage(new URL("https://maps.googleapis.com/maps/api/staticmap?center="+y+",-"+x+"&zoom=20&size=640x640&maptype=roadmap&key=YOURAPIKEYHERE"));
            if (this.mapa == null)
            {
                System.out.println("Error peticion");

            }
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        panelBlue.removeAll();
        panelText.removeAll();
        if(maxspeedStatus == 0)
        {
            panelText.setBackground(Color.green);
        }
        if(maxspeedStatus == 1)
        {
            panelText.setBackground(Color.yellow);
        }
        if(maxspeedStatus == 2)
        {
            panelText.setBackground(Color.red);
        }
        panelBlue.add(new JLabel(new ImageIcon(this.mapa)));
        DecimalFormat df = new DecimalFormat("###.##");
        String Sspeed = df.format(speed);
        panelText.add(new JLabel("speed: "+Sspeed+"km/h"));
        lpane.add(panelGreen, new Integer(0), 0);
        //lpane.add(panelText,new Integer(2),0);
        frame.pack();
        frame.repaint();
    }
    public void addPosicionRelative(double lat, double longitude,double time) {
        double distancia = 0;
        double speed = 0;
        double maxspeed = 0;
        int maxspeedStatus = 0;
        System.out.println(time);

        if ((lat >= 3.631898 && lat <= 3.632387) && (longitude <=40.387655 && longitude >= 40.387591 ))
        {
            maxspeed = 10;
        }
        else if ((lat >= 3.632403 && lat <= 3.633228 ) && (longitude <= 40.387575 && longitude >= 40.386430))
        {
            maxspeed = 20;
        }
        else if ((lat >= 3.632755 && lat <= 3.6333304) && (longitude >= 40.385968 && longitude <= 40.386828))
        {
            maxspeed = 15;
        }
        else if ((lat >=3.632445 && lat <=3.632798 ) && (longitude >=40.386853 && longitude <=40.387490 ))
        {
            maxspeed = 18;
        }
        else if ((lat >= 3.631817 && lat <=3.632432 )&&(longitude >=40.387465 && longitude <=40.387636 ))
        {
            maxspeed = 5;
        }
        else
        {
            maxspeed = 70;
        }

        double y = lat;
        double x = longitude;
        distancia = getDistancia(lat,longitude,oldlat,oldlon);
        speed = distancia / time;
        speed = speed *3.6;
        System.out.println(speed);
        oldlat = lat;
        oldlon = longitude;
        if (speed < maxspeed - (maxspeed/10))
        {
            maxspeedStatus = 0;
        }
        if (speed > (maxspeed-(maxspeed/10)) && speed < (maxspeed+(maxspeed/10)) )
        {
            maxspeedStatus = 1;
        }
        if (speed > maxspeed + (maxspeed/10))
        {
            maxspeedStatus = 2;
        }

        newPosition(x,y,speed,maxspeedStatus);//AQUI CAMBIAR
    }

    public double getDistancia(double lat,double longitude, double oldlat, double oldlon)
    {
       double lat1 = lat *Math.PI/180;
       double lat2 = oldlat *Math.PI/180;
       double difflat = (lat-oldlat) *Math.PI/180;
       double difflon = (longitude-oldlon) *Math.PI/180;

       double a = Math.sin(difflat/2) * Math.sin(difflat/2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(difflon/2) * Math.sin(difflon/2);
       double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
       return  this.R *c;

    }

}
