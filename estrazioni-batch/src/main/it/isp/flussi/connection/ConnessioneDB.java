package main.it.isp.flussi.connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import main.it.isp.utility.Utility;

public class ConnessioneDB {
	
	private Statement stmt;
	private ResultSet rs;
	
	
	protected static String oracle_DB_URL= "";
	protected static String oracle_DB_USER= "";
	protected static String oracle_DB_PASS= "";
	protected static String db2_DB_URL= "";
	protected static String db2_DB_USER= "";
	protected static String db2_DB_PASS= "";
	protected static String outputPath = "";
	protected static String inputPath = "";
	protected static  Connection connOracle;
	protected static Connection connDB2;
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public void readParametriConfigurazione() {
		try {
			oracle_DB_URL = Utility.readProperty("OracleJDBCURL");
			oracle_DB_USER = Utility.readProperty("OracleOwner");
			oracle_DB_PASS = Utility.readProperty("OraclePassword");
			db2_DB_URL = Utility.readProperty("Db2JDBCURL");
			db2_DB_USER = Utility.readProperty("Db2Owner");
			db2_DB_PASS = Utility.readProperty("Db2Password");
			outputPath = Utility.readProperty("OutputPath");
		} catch (Exception e) {
			logger.error("Non è stato possibile recuperare i valori delle variabili per il database Oracle");
			System.exit(1);
		}
	}

	public void readParametriConfigurazioneInput() {
		try {
			inputPath = Utility.readProperty("InputPath");
		} catch (Exception e) {
			logger.error("Non è stato possibile recuperare i valori delle variabili per il database Oracle");
			System.exit(1);
		}
	}
	
	/*Metodo utilizzato dai batch preesistenti: non usare questo metodo, usare getOracleDataSource*/
	public static Connection openConnessioneOracle() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connOracle = DriverManager.getConnection(oracle_DB_URL, oracle_DB_USER, oracle_DB_PASS);
		} catch (Exception e) {
			logger.error(e);
		}
		
		return connOracle;
	}
	
	public static BasicDataSource getOracleDataSource() {
		BasicDataSource ds = new BasicDataSource();
	        ds.setDriverClassName("oracle.jdbc.OracleDriver");
	        ds.setUrl(oracle_DB_URL);
	        ds.setUsername(oracle_DB_USER);
	        ds.setPassword(oracle_DB_PASS);
		
		return ds;
	}
	
	public static BasicDataSource getDB2DataSource() {
		BasicDataSource  ds = new BasicDataSource();
	        ds.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
	        ds.setUrl(db2_DB_URL);
	        ds.setUsername(db2_DB_USER);
	        ds.setPassword(db2_DB_PASS);
		
		return ds;
	}
	
	public void close(PreparedStatement ps, ResultSet rs) throws SQLException{
		if(rs !=null)
			rs.close();
		if(ps != null)
			ps.close();
	}
	
	public static void closeConnessioneOracle(){
		try {
			if(connOracle!=null)
				if(!connOracle.isClosed()) {
					connOracle.close();
					System.out.println("connessione Oracle chiusa");
				}else {
					logger.debug("connessione Oracle già chiusa");
				}
			else
				logger.debug("nessuna operazione effettuata. La connessione Oracle non era aperta");
					
		} catch (Exception e) {
			logger.error("Errore nella connessione Oracle, eccezione: " + e.toString());
		}
	}

	/*Metodo utilizzato dai batch preesistenti: non usare questo metodo, usare getDB2DataSource*/
	public static Connection openConnessioneDB2() {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			connDB2 = DriverManager.getConnection(db2_DB_URL, db2_DB_USER, db2_DB_PASS);
		} catch (Exception e) {
			logger.error(e);
		}
		
		return connDB2;
	}
	
	public ResultSet GetQuery(String sql) throws Exception {
		stmt = getConnDB2().createStatement();
		String sqlQueryString = sql;
		rs = stmt.executeQuery(sqlQueryString);
		return rs;
	}
	
	public static void closeConnessioneDB2() {
		try {
			if(connDB2!=null)
				if(!connDB2.isClosed()) {
					connDB2.close();
					System.out.println("connessione DB2 chiusa");
				}else {
					logger.debug("connessione DB2 già chiusa");
				}
			else
				logger.debug("Nessuna operazione effettuata. La connessione DB2 non era aperta");
					
		} catch (Exception e) {
			logger.error("Errore nella connessione DB2, eccezione: " + e.toString());
		}
	}
	
	public static Connection getConnOracle() {
		return connOracle;
	}

	public static String getOracle_DB_URL() {
		return oracle_DB_URL;
	}

	public static void setOracle_DB_URL(String oracle_DB_URL) {
		ConnessioneDB.oracle_DB_URL = oracle_DB_URL;
	}

	public static String getOracle_DB_USER() {
		return oracle_DB_USER;
	}

	public static void setOracle_DB_USER(String oracle_DB_USER) {
		ConnessioneDB.oracle_DB_USER = oracle_DB_USER;
	}

	public static String getOracle_DB_PASS() {
		return oracle_DB_PASS;
	}

	public static void setOracle_DB_PASS(String oracle_DB_PASS) {
		ConnessioneDB.oracle_DB_PASS = oracle_DB_PASS;
	}

	public static String getOutputPath() {
		return outputPath;
	}

	public static void setOutputPath(String outputPath) {
		ConnessioneDB.outputPath = outputPath;
	}

	public static String getInputPath() {
		return inputPath;
	}

	public static void setInputPath(String inputPath) {
		ConnessioneDB.inputPath = inputPath;
	}
	
	public static String getDb2_DB_URL() {
		return db2_DB_URL;
	}

	public static void setDb2_DB_URL(String db2_DB_URL) {
		ConnessioneDB.db2_DB_URL = db2_DB_URL;
	}

	public static String getDb2_DB_USER() {
		return db2_DB_USER;
	}

	public static void setDb2_DB_USER(String db2_DB_USER) {
		ConnessioneDB.db2_DB_USER = db2_DB_USER;
	}

	public static String getDb2_DB_PASS() {
		return db2_DB_PASS;
	}

	public static void setDb2_DB_PASS(String db2_DB_PASS) {
		ConnessioneDB.db2_DB_PASS = db2_DB_PASS;
	}

	public static Connection getConnDB2() {
		return connDB2;
	}

	public static void setConnDB2(Connection connDB2) {
		ConnessioneDB.connDB2 = connDB2;
	}

	public static void setConnOracle(Connection connOracle) {
		ConnessioneDB.connOracle = connOracle;
	}

	 protected String sqlOf(String path) throws Exception {
	        String fullpath = sqlBasePath() + path + ".sql";
	        System.out.println("Executing SQL script: {}"+ fullpath);
	        try (BufferedReader br = new BufferedReader(
	                new InputStreamReader(new ClassPathResource(fullpath).getInputStream()))) {
	            return br.lines().collect(Collectors.joining(System.lineSeparator()));
	        } catch (final IOException e) {
	            throw new Exception(
	                    "Could not read query for path " + fullpath, e);
	        }
	    }
	 
	    public String sqlBasePath() {
	        return "/sql/";
	    }

}
