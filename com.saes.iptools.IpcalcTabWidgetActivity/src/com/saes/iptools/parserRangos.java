package com.saes.iptools;

import java.util.StringTokenizer;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;

public class parserRangos {
	private String mArgs,msgTxt;
	//private String[] mArrArgs;
	private int[][] mRangos;
	private int mNumRangos, mNumArgs, mError;
	//Collection<Integer[]> colRgo = new HashSet<Integer[]>();
	//Integer[] aa = {1,2};
	
	
	parserRangos(){
		mError = 0;
	}
	void setArgs(String args){
		mArgs = args;
		if(mArgs.length()>0){
			try{
				this.creaRangos();
			}
			catch(Exception e){
				msgTxt = "Error al crear rangos... \n";
				e.printStackTrace();
			}
		}
	}
	public String getMArgs() {
		return this.mArgs;
	}
	public int getNumRangos(){
		return mNumRangos;
	}
	public int getNumArgs(){
		return mNumArgs;
	}
	public int[][] getRangos(){
		return mRangos;
	}
	public String getMsgTxt(){
		return msgTxt;					//To do
	}
	public String getRangosTxt(){
		return "";					//To do
	}
	private void separaArgs(){
									//To do
	}
	private void creaRangos(){
		//String[] stRango;
		int t=0,tmp1=0,tmp2=0;
		mError = 0;

		StringTokenizer ids = null;
		try {
		ids = new StringTokenizer(mArgs, "[,|\b|\t|:]");
		mNumArgs = ids.countTokens();
		mRangos = new int[mNumArgs][2];
		//msgTxt = "RANGOS: " + mNumArgs + "\n";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		while(ids.hasMoreTokens()){
			String [] tmp = ids.nextToken().split("-",2);
			if (tmp.length == 1){
				try{
					tmp1 = Integer.parseInt(tmp[0]);
					tmp2 = Integer.parseInt(tmp[0]);
					//msgTxt += "Rango: " + tmp1 + " - " + tmp2 + "\n";
				}
				catch(Exception e){
					mError = 1;
					e.printStackTrace();
				}
			}else{
				try{
					tmp1 = Integer.parseInt(tmp[0]);
					tmp2 = Integer.parseInt(tmp[1]);
					//msgTxt += "Rango: " + tmp1 + " - " + tmp2 + "\n";
				}
				catch(Exception e){
					mError = 2;
					e.printStackTrace();
				}
			}
			if(mError == 0){
				//msgTxt += "t: "+ t +" tmp1: "+ tmp1+ " tmp2: "+tmp2+"\n";
				try{
					mRangos[t][0] = tmp1;
					mRangos[t][1] = tmp2;
					//msgTxt += "\n ["+t+"][0]: "+mRangos[t][0]+"\n";
					//msgTxt += "\n ["+t+"][1]: "+mRangos[t][1]+"\n";
				}
				catch(ArrayStoreException e){
					mError = 3;
					e.printStackTrace();
					System.out.println(e);	
				}
				t++;
			}
		}
		mNumRangos = t;

	}
	private void ordenaRangos(){
		//To do
	}
	private void fusionaRangos(){
		//To do
	}
	public int getError(){
		return mError;
	}
}
