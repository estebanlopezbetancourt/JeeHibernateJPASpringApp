package com.convenio.svc.businesshelper;

import org.jasypt.util.text.BasicTextEncryptor;

import com.convenio.dal.entities.*;

public class TransactionTokenHelper {
	public String CreateToken(Transaccion tx){
		String tokenStructure = "";
		String myEncryptionPassword = "@Convenios123";
		
		//Some sort of encryption should be here for Security purpose
		//following line just for spike solution
		tokenStructure = String.format("%d-%d", tx.getId(), tx.getRutbeneficiado());
		
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(myEncryptionPassword);
		String token = textEncryptor.encrypt(tokenStructure);
		
		return token;
	}
	
	public int GetTxIdFromToken(String token){
		String myEncryptionPassword = "@Convenios123";
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(myEncryptionPassword);
		String tokenDecrypted = textEncryptor.decrypt(token);
		
		int txId = Integer.parseInt(tokenDecrypted.split("-")[0]);
		return txId;
	}
	
	

}
