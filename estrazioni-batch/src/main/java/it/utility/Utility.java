package main.java.it.utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Properties;

public class Utility {
//versione 1.1
	
	public static String replaceSpecialChar(String str) {
		StringBuilder sb = new StringBuilder(str);
		for (int index = 0; index < sb.length(); index++) {
			char carattere = sb.charAt(index);
			switch (carattere) {
			case '-':
				sb.setCharAt(index, ' ');
				break;
			case ',':
				sb.setCharAt(index, ' ');
				break;
			case ';':
				sb.setCharAt(index, '.');
				break;
			case '\n': // nuova linea
				sb.setCharAt(index, ' ');
				break;
			case '\r': // carattere di ritorno
				sb.setCharAt(index, ' ');
				break;
			}
		}
		return sb.toString();
	}
	
	public static String replaceSpecialCharForDb(String oldString) {
		String newString = oldString;
		if(newString!=null)
			newString = newString.replace("'", "'|| chr(39)||'");
		return newString;
	}
	
	public static String replaceNull(String in) {
		String risultato = "";
		try {
			if (in == null || in.trim().equalsIgnoreCase("null"))
				risultato = "";
			else
				risultato = in;
		} catch (Exception e) {//
			
		}
		return risultato;
	}
	
    public static String substringAfterLastSeparator(String str, String separator) {
        if (str==null || str.length()<1 ) return str;
        if (separator==null || separator.length()<1 ) return "";
        int pos = str.lastIndexOf(separator);
        if (pos == -1) return "";
        if (pos == (str.length() - separator.length())) return "";
       	return str.substring(pos + separator.length());
    }
	
	public static String dataAdesso() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSSSSS");
		LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
	}
	
	public static void setParametriStrInStm (PreparedStatement pstmA, String[] parametri) throws Exception  {
		if (parametri != null) {
			for (int i = 0; i < parametri.length; i++) {pstmA.setString(i+1, parametri[i]);}			
		} else {
			System.out.println("Nessun parametro ricevuto");
		}
	}
    
    public static String getParametriPerOutput (String[] parametri)  {
		StringBuilder parametriSt = new StringBuilder("");
		if (parametri != null) {
			if (parametri.length> 0) parametriSt.append(";--Parametri: ");
			for (int i = 0; i < parametri.length; i++) {parametriSt.append( (i+1) + ":" + parametri[i] + " ");}			
		}
		return parametriSt.toString();
	}
    
    public static String getStrRepeat (String caratteri, int ricorrenze)  {
		StringBuilder result = new StringBuilder("");
		if (caratteri !=null && !caratteri.isEmpty() && ricorrenze>0) {
			for (int i = 1; i <= ricorrenze; i++) result.append(caratteri);
		}
		return result.toString();
	}
    
	
	//Restituisce l'orario corrente con formato HH24. Es: 09/08/2020 13:54:12
	public static String getCurrentTimestamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
	}
	
	public static String getValoreFromConfigurazioni05(Connection conn, String chiave) {
		String result = "";
		String query="";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			query="select VALORE from t_prova where chiave =? ";
			ps= conn.prepareStatement(query);
			ps.setString(1, chiave);
			rs = ps.executeQuery();
			while(rs.next()) {
				result = checkNull(rs.getString("VALORE")).trim();
			}
		} catch (Exception e) {
			System.out.println("errore: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closePsRs(ps,rs);
		}
		return result;
	}

	public static String getLabel(Connection conn, String chiave, boolean isEng ) {
		String result = "";
		String query="";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			query="select * from t_pp where chiave = ? ";
			ps= conn.prepareStatement(query);
			ps.setString(1, chiave);
			rs = ps.executeQuery();
			if (rs.next()) {
				if( isEng) result = replaceNull(rs.getString("VALORE_EN")).trim(); else result = replaceNull(rs.getString("VALORE_IT")).trim();  
			}
		} catch (Exception e) {
			System.out.println("errore: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closePsRs(ps,rs);
		}
		return result;
	}
	
	
	public static String checkNull(String in) {
		String risultato = " ";
		try {
			if (in == null || in.trim().equals("null"))
				risultato = " ";
			else
				risultato = in;
		} catch (Exception e) {	//risultato = " ";
		}
		return risultato;
	}
	
	public static StringBuilder getIntestazione(ResultSet rs) {
		StringBuilder intestazione = new StringBuilder();
		try {
			ResultSetMetaData rsMetaD = rs.getMetaData();
			int numColonne = rsMetaD.getColumnCount();
			
			
			for (int i= 1; i<numColonne; i++) {
				intestazione.append(rsMetaD.getColumnName(i) + ";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return intestazione;
	}

	public static StringBuilder getIntestazione(ResultSet rs, boolean separatoreFinaleSi) {
		StringBuilder intestazione = new StringBuilder();
		try {
			ResultSetMetaData rsMetaD = rs.getMetaData();
			int numColonne = rsMetaD.getColumnCount();
			for (int i= 1; i<=numColonne; i++) {
				intestazione.append(rsMetaD.getColumnName(i) + ";");
			}
			if (intestazione.length()>0 && !separatoreFinaleSi) {
				intestazione.setLength(intestazione.length()-1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return intestazione;
	}
	
	public static String readProperty(String property) {
		String returnValue = "";
		String wantedProperty =  System.getProperty(property);
	    returnValue = wantedProperty;

		return returnValue;
	}
	
	public static void closePsRs( PreparedStatement pstm, ResultSet rs)  {
		try {
			if (rs != null)
				rs.close();
			if (pstm != null)
				pstm.close();
		} catch (Exception e) {//System.out.println("Errore durante la chiusura dei ps - rs");
		}
	}
	
	public static void closePs( PreparedStatement pstm)  {
		try {
			if (pstm != null) pstm.close();
		} catch (Exception e) {	//System.out.println("Errore durante la chiusura dei ps");
		}
	}

}
