package APP;

import com.fazecast.jSerialComm.*;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        long ini;
        long fin;
        boolean offline = false;
        if (args.length > 0 && args[0].equals("offline"))
            offline = true;
        InputStream stream;
        SerialPort sp = null;
        if (offline) {
            System.out.println("Estoy offline");
            try {
                stream = new FileInputStream("Ejemplo_GGA.txt");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            //Crear Objeto Serial Port
            sp = SerialPort.getCommPort("COM3");
            //Configurar Serial Port
            if (!sp.openPort()) {
                System.out.println("No se ha abierto el puerto");
                return;
            }
            sp.setComPortParameters(4800, 8, 1, SerialPort.NO_PARITY);
            sp.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
            sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            stream = sp.getInputStream();
        }

        Scanner scanner = new Scanner(stream);
        try {
            CommandProcessor commandProcessor = new CommandProcessor();
            do {
                commandProcessor.processString(scanner.next());
            } while (!commandProcessor.stop && scanner.hasNext());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!offline) {
            sp.closePort();
        }
    }
}
