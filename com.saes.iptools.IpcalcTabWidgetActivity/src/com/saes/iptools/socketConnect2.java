package com.saes.iptools;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class socketConnect2 {

	private String mIP;
	private int mPuerto;
	private boolean mEstado;
	private String mEstadoTxt;
	private Socket requestSocket;

	socketConnect2(){}
	void connSocket()
	{
			//  Intenta abrir socket en el puerto especifico
				try{
					this.requestSocket = new Socket(this.mIP, this.mPuerto);
					this.mEstadoTxt = "\nConectado al servidor "+this.mIP+" en el puerto: " + this.mPuerto;
					this.mEstado = true;
				}
				catch (UnknownHostException e) {
					this.mEstadoTxt = "Unknown host: "+mIP;
					this.mEstado = false;
				}
				catch(IOException  e){
					//Hace si no puede abrir el socket
					this.mEstadoTxt = "..." + this.mPuerto;
					this.mEstado = false;
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
