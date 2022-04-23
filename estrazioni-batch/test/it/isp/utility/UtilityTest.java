package it.isp.utility;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import main.it.isp.utility.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@ExtendWith(MockitoExtension.class)

public class UtilityTest {
	@Mock
	private Connection connection;
	@Mock
	ResultSetMetaData rsMetaD ;
	@Mock
	ResultSet rs;
	@InjectMocks
	private Utility ut;
	@Mock
	private PreparedStatement stmt;
	
	@Test
	public void test() throws Exception {

		assertEquals("d dd.d d  aa", Utility.replaceSpecialChar("d-dd;d,d\r\naa"));

		assertNotNull(Utility.getCurrentTimestamp());



	}

	@Test
	public void checkNulltest()  {
		assertEquals("", Utility.checkNull(""));
		assertEquals(" ", Utility.checkNull(null));
		assertEquals(" ", Utility.checkNull("null"));

	}
	
	@Test
	public void replaceNulltest()  {
		assertEquals("", Utility.replaceNull(""));
		assertEquals("", Utility.replaceNull("null"));
		assertEquals("", Utility.replaceNull(null));

	}
	
	@Test
	public void setParametriStrInStmTest()  {
		String[] parametri = new String[]{"1", "2", "3"};

		try {
			Utility.setParametriStrInStm(stmt, null);
			Utility.setParametriStrInStm(stmt, parametri);
		} catch (Exception e) {
			assertEquals(e.getClass(),NullPointerException.class);
		}
	}
	
	@Test
	public void getIntestazioneTest()  {
		try {
		Utility.getIntestazione(null);
		}catch (Exception e) {
			   assertEquals(e.getClass(),NullPointerException.class);
	            return;
		}
		
		assertNotNull(Utility.getIntestazione(rs));
	}

	@Test
	public void getValoreFromConfigurazioni05testconnectionNull()  {
		try {
			Utility.getValoreFromConfigurazioni05(null,"");
		}catch (Exception e) {
			assertEquals(e.getClass(),NullPointerException.class);
		}
	}
	
	
	@Test
	public void getLabel_01() throws Exception  {
			String chiave="chiave";
			String value="value";
			PreparedStatement ps= mock(PreparedStatement.class);
			when(connection.prepareStatement(any())).thenReturn(ps);
			when(ps.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getString("VALORE_EN")).thenReturn(value);
			assertEquals(value,Utility.getLabel(connection,chiave,true));
			verify(ps).setString(1, chiave);	
	}
	@Test
	public void getLabel_02() throws Exception  {
		String chiave="chiave";
		String value="value";
		PreparedStatement ps= mock(PreparedStatement.class);
		when(connection.prepareStatement(any())).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true).thenReturn(false);
		when(rs.getString("VALORE_IT")).thenReturn(value);
		assertEquals(value,Utility.getLabel(connection,chiave,false));
		verify(ps).setString(1, chiave);	
	}
	@Test
	public void getLabel_03() throws Exception  {
		try {
			Utility.getLabel(null,"",true);
		}catch (Exception e) {
			assertEquals(e.getClass(),Exception.class);
		}
	}
	
	
	
	@Test
	public void getParametriPerOutputTest()  {
		String[] parametri = new String[]{"1", "2", "3"};
		String[] parametri2 = new String[0];

		try {
			Utility.getParametriPerOutput(null);
			Utility.getParametriPerOutput(parametri);
			Utility.getParametriPerOutput(parametri2);
		} catch (Exception e) {
			assertEquals(e.getClass(),NullPointerException.class);
		}
	}
	
	@Test
	public void getStrRepeatTest()  {
		try {
			Utility.getStrRepeat("a",5);
			Utility.getStrRepeat("a",0);
			Utility.getStrRepeat("",0);
			Utility.getStrRepeat(null,5);
		} catch (Exception e) {
			assertEquals(e.getClass(),NullPointerException.class);
		}
	}
	
	@Test
	public void replaceSpecialCharForDb_01()  {
		String newString = Utility.replaceSpecialCharForDb("AA");
		newString = Utility.replaceSpecialCharForDb(null);
	}
	
	@Test
	public void substringAfterLastSeparator_01()  {
		String newString = Utility.substringAfterLastSeparator("AA_BB_CC", "_");
		newString = Utility.substringAfterLastSeparator("AA_BB_CC", "K");
		newString = Utility.substringAfterLastSeparator(null, "K");
		newString = Utility.substringAfterLastSeparator("AA_BB_CC", null);
		newString = Utility.substringAfterLastSeparator("", "A");
		newString = Utility.substringAfterLastSeparator("A", "K");
		newString = Utility.substringAfterLastSeparator("AA_BB_CC", "");
		newString = Utility.substringAfterLastSeparator("A", "__");
		newString = Utility.substringAfterLastSeparator("AA", "AA");
	}
	
	@Test
	public void dataAdesso_01()  {
		String newString = Utility.dataAdesso();
	}
	
	@Test
	public void readProperty_01()  {
		String newString = "";
		newString = Utility.readProperty("oracle_DB_URL");
		newString = Utility.readProperty("nessuna");
	}
	
	@Test
	public void closePsRs_01()  {
		try {
			Utility.closePsRs(stmt, rs);
			Utility.closePsRs(null, rs);
			Utility.closePsRs(stmt, null);
			Utility.closePs(stmt);
			Utility.closePs(null);
		} catch (Exception e) {	//
		}
	}
	
	@Test
	public void getValoreFromConfigurazioni05test() throws Exception  {
			String chiave="chiave";
			String value="value  ";
			PreparedStatement ps= mock(PreparedStatement.class);
			when(connection.prepareStatement("select VALORE from t_prova where chiave =? ")).thenReturn(ps);
			when(ps.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true).thenReturn(false);
			when(rs.getString("VALORE")).thenReturn(value);
			assertEquals(value.trim(),Utility.getValoreFromConfigurazioni05(connection,chiave));
			verify(ps).setString(1, chiave);	
	}
	
	@Test
	public void getIntestazioneTest2() throws Exception  {
		when(rs.getMetaData()).thenReturn(rsMetaD);
	    when(rsMetaD.getColumnCount()).thenReturn(2);
	    when(rsMetaD.getColumnName(1)).thenReturn("campo");
		Utility.getIntestazione(rs);
		verify(rs).getMetaData();	
		
	}
	
	@Test
	public void getIntestazioneTest3() throws Exception  {
		when(rs.getMetaData()).thenReturn(rsMetaD);
	    when(rsMetaD.getColumnCount()).thenReturn(2);
	    when(rsMetaD.getColumnName(1)).thenReturn("campo");
		Utility.getIntestazione(rs, true);
		verify(rs).getMetaData();	
		
	}

	
	@Test
	public void getIntestazioneTest4() throws Exception {	
		when(rs.getMetaData()).thenReturn(rsMetaD);
	    when(rsMetaD.getColumnCount()).thenReturn(2);
	    when(rsMetaD.getColumnName(1)).thenReturn("campo");
		assertNotNull(Utility.getIntestazione(rs,false));
	}
	
	
	@Test
	public void readPropertyNull() {
		try{
			assertEquals(Utility.readProperty(""), Utility.checkNull(""));
		}catch (Exception e) {
			   assertEquals(e.getClass(),IllegalArgumentException.class);
		}
	}
	
	/*@Test
	public void test1() throws Exception{
		
		when(Utility.getIntestazione(null)).thenThrow(new RuntimeException());
		assertEquals(Utility.getIntestazione(null),new RuntimeException());
	}*/
}
