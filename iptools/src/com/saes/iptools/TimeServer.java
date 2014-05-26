package com.saes.iptools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
/**
 *
 * @author pcrao
 */
public class TimeServer{
    static DatagramSocket socket ;
    /** Creates a new instance of Main */
    public TimeServer() {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            socket = new DatagramSocket(9090);
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            while(true) {
                System.out.println("Listening");
                socket.receive(packet);          

                String toClient=new Date().toString();
                buf = toClient.getBytes();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            }

        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
