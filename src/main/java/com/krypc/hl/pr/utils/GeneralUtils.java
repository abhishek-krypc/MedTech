package com.krypc.hl.pr.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.hyperledger.fabric.sdk.ChainCodeResponse;
import org.springframework.stereotype.Component;

@Component
public class GeneralUtils {

	private Properties prop = new Properties();
	public ChainCodeResponse chainCodeResponse;
	private GeneralUtils(){
    	init();
    }
	public void init(){
		String chaincodename = null;
		String chaincodeid = null;
		InputStream input = null;
		try {
			input = new FileInputStream("/opt/config.properties");
			prop.load(input);
			chaincodename = prop.getProperty("chaincodename");
			chaincodeid = prop.getProperty("chaincodeid");
			chainCodeResponse = new ChainCodeResponse(chaincodeid, chaincodename,null , null);
		} catch (Exception e) {
			 e.printStackTrace();
		}finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void storeChaincodeName(ChainCodeResponse chainCodeResponse ){
		OutputStream output = null;
		try {
			output = new FileOutputStream("/opt/config.properties");
            prop.setProperty("chaincodename", chainCodeResponse.getChainCodeID());
            prop.setProperty("chaincodeid", chainCodeResponse.getTransactionID());
            prop.store(output, null);
            chainCodeResponse = new ChainCodeResponse(chainCodeResponse.getChainCodeID(), chainCodeResponse.getTransactionID(),null , null);
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally{
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ChainCodeResponse  getChaincodeName(){
		return chainCodeResponse;
	}
	
	public boolean verifychaincode(){
		return (getChaincodeName()!=null);
	}
}
