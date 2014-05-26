package com.saes.iptools;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class socketConnect {

	private String mIP;
	private int mPuerto, mtimeout;
	private boolean mEstado;
	private String mEstadoTxt;
	private Socket requestSocket;
	private InetSocketAddress mhost_port;

	socketConnect(){
		mtimeout = 1200;
	}
	void connSocket()
	{
			//  Intenta abrir socket en el puerto especifico
				try{
					mhost_port = new InetSocketAddress(mIP, mPuerto);
					requestSocket = new Socket();
					requestSocket.connect(mhost_port, mtimeout);
					mEstadoTxt = "\nConectado al servidor "+this.mIP+" en el puerto: " + this.mPuerto;
					mEstado = true;
				}
				catch (UnknownHostException e) {
					mEstadoTxt = "Unknown host: "+mIP;
					mEstado = false;
				}
				catch(IOException  e){
					//Hace si no puede abrir el socket
					mEstadoTxt = "..." + this.mPuerto;
					mEstado = false;
					//this.mEstadoTxt = e.getMessage() + "..." + this.mPuerto;
				}
	}
	public void cierraSocket(){
		try{
			requestSocket.close();
		}
		catch(Exception e){
			//ioException.printStackTrace();
		}
	}
	public void sIP(String host){
		this.mIP = host;
	}
	public void sPuerto(int port){
		this.mPuerto = port;
	}
	public void sTimeOut(int timeout){
		this.mtimeout = timeout;
	}
	public String gIP(){
		return this.mIP;
	}
	public int gPuerto() {
		return this.mPuerto;
	}
	public boolean gEstado() {
		return this.mEstado;
	}
	public String gEstadoTxt() {
		return this.mEstadoTxt;
	}

}
