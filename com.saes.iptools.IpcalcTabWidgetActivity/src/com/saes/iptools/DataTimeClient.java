package com.saes.iptools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DataTimeClient {

    /** Creates a new instance of DataTimeClient */
    public DataTimeClient() {  
	}

    public static void main(String args[]) {
        byte[] buf = new byte[256];
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();
            InetAddress address= InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buf,buf.length,address,9090);
            socket.send(packet);
            packet = new DatagramPacket(buf,buf.length);
            socket.receive(packet);
            System.out.println(new String(packet.getData()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }       
    }
}
