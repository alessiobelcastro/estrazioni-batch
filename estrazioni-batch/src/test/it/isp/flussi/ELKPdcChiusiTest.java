package test.it.isp.flussi;

import java.io.FileWriter;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;


import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import main.it.isp.flussi.ElkPdcChiusi;
import main.it.isp.flussi.connection.ConnessioneDB;
import main.it.isp.flussi.dto.PdcChiuso;

@RunWith(MockitoJUnitRunner.class) 
public class ELKPdcChiusiTest {
	@InjectMocks
	private ElkPdcChiusi test;
	@Mock
	DriverManager dm;

	@Mock
	FileWriter fw;

	@Mock
	private DataSource ds;
	@Mock
	private Connection c;
	@Mock
	private Connection con;
	@Mock
	private PreparedStatement stmt;
	@Mock
	private PreparedStatement stmt1;
	@Mock
	private PreparedStatement stmt2;
	@Mock
	private Statement st;
	@Mock
	private ResultSet rs;
	@Mock
	private ResultSet rs2;
	@Mock
	ConnessioneDB dbcon;

//	@Test
//	public void test() throws Exception {	
//		String[] arg = { "", "" };
//		ElkPdcChiusi.main(arg);
//	}


	@Test
	public void test2() throws Exception {
		try {
			PdcChiuso pdc = new PdcChiuso();
			pdc.setCodPdc("PDCXXX");
			pdc.setSistInformativo("01");
			test.acronimiAggiungi(pdc,con);
		}catch (Exception e) {
			   assertEquals(e.getClass(),NullPointerException.class);
	            return;
		}
	}
	@Test
	public void test2bis() throws Exception {
		try {
			PdcChiuso pdc2 = null;
			test.acronimiAggiungi(pdc2,con);
		}catch (Exception e) {
			   assertEquals(e.getClass(),NullPointerException.class);
	            return;
		}
	}
	
	@Test
	public void test3() throws Exception {
		String a="A";
		when(rs.getString(any())).thenReturn(a);
		assertNotNull( test.setPdcChiuso(rs));
		
	}
	

	@Test
	public void test4() throws Exception {

		PdcChiuso pdc3 = new PdcChiuso();
		List<PdcChiuso> elencoAcronimi= new ArrayList<>();
		elencoAcronimi.add(pdc3);
		test.scritturaFile(fw, false,elencoAcronimi);
	
	}
	
	@Test
	public void test5() throws Exception {
		
		when(c.prepareStatement(anyString())).thenReturn(stmt);
		when(stmt.executeUpdate()).thenReturn(1);
	//TODO	test.updateDataUltimaEstrazione(c, "");
	}
	@Test
	public void test6() throws Exception {
		
		when(c.prepareStatement(anyString())).thenReturn(stmt);
		when(stmt.executeUpdate()).thenReturn(0);
		//TODO	test.updateDataUltimaEstrazione(c, "");
	}
	
	@Test
	public void test7() throws Exception {
		test.getElencoPdcChiusi(con, "", "",	true);
	}
	
	@Test
	public void test8() throws Exception {

		PdcChiuso pdc3 = new PdcChiuso();
		List<PdcChiuso> elencoAcronimi= new ArrayList<>();
		elencoAcronimi.add(pdc3);
		test.scritturaFile(fw, true,elencoAcronimi);
	
	}
	
	@Test
	public void test9() throws Exception {

		List<String> elAcr = new ArrayList<String>();
		elAcr.add("CHCI0");
		elAcr.add("ABCD0");
		List<String> elPreSoft = new ArrayList<String>();
		elPreSoft.add("S");
		elPreSoft.add("N");
		PdcChiuso pdc3 = new PdcChiuso();
		pdc3.setElencoAcronimi(elAcr);
		pdc3.setElencoPresenzaSoftware(elPreSoft);
		List<PdcChiuso> listaPdc= new ArrayList<>();
		listaPdc.add(pdc3);
		test.scritturaFile(fw, false,listaPdc);
	
	}
	
	@Test
	public void test10() throws Exception {
		test.sysouti("");
	}

	@Test
	public void testClose() throws Exception {
		test.close(stmt,rs);
		verify(rs).close();
		verify(stmt).close();
		test.close(null, null);		
	}

	
	@Test
	public void test12() throws Exception {
		when(con.prepareStatement(any())).thenReturn(stmt);


		when(stmt.executeQuery()).thenReturn(rs);
		
		when(rs.next()).thenReturn(true,false,true,false);
	
		when(rs.getString(any())).thenReturn("12","s");
		
		//TODO	test.creazioneFile(fw, con, con);	
	}
	
	@Test
	public void test12a() throws Exception {
		when(con.prepareStatement(any())).thenReturn(stmt);


		when(stmt.executeQuery()).thenReturn(rs);
		
		when(rs.next()).thenReturn(true,false,true,false);
	
		when(rs.getString(any())).thenReturn("12",null);
		
		//TODO	test.creazioneFile(fw, con, con);	
	}
	@Test
	public void test13() throws Exception {
		when(con.prepareStatement(any())).thenReturn(stmt);


		when(stmt.executeQuery()).thenReturn(rs);
		
		when(rs.next()).thenReturn(true,false);
	
		when(rs.getString(any())).thenReturn("12");
		
		//TODO	test.creazioneFile(fw, con, con);	
	}
	@Test
	public void test14() throws Exception {

		
		//TODO	test.creazioneFile(null, con, con);	
	}
	@Test
	public void test15() throws Exception {

		
		//TODO	test.creazioneFile(fw, null, con);	
	}
	@Test
	public void test16() throws Exception {

		
		//TODO	test.creazioneFile(fw, con, null);	
	}
	@Test
	public void test17() throws Exception {
		when(con.prepareStatement(any())).thenReturn(stmt);


		when(stmt.executeQuery()).thenReturn(rs);
		
		when(rs.next()).thenReturn(true,false,true,false,true,false);
	
		when(rs.getString(any())).thenReturn("12","s","","","","","");
		
		//TODO	test.creazioneFile(fw, con, con);	
	}
	@Test
	public void test18() throws Exception {
		when(con.prepareStatement(any())).thenReturn(stmt);


		when(stmt.executeQuery()).thenReturn(rs);
		
		when(rs.next()).thenReturn(true,false,true,false,true,false,true,false);
	
		when(rs.getString(any())).thenReturn("12","s","","","","","","");
		
		//TODO	test.creazioneFile(fw, con, con);	
	}
	
	@Test
	public void test19() throws Exception {
		when(con.prepareStatement(any())).thenReturn(stmt);


		when(stmt.executeQuery()).thenReturn(rs);
		
		when(rs.next()).thenReturn(true,false,true,false,true,false,true,false);
	
		when(rs.getString(any())).thenReturn("12","s","","","","","","");
		test.getElencoPdcChiusi(con, "999", "999", false);
	}
	
	@Test
	public void test20() throws Exception {
		when(con.prepareStatement(any())).thenReturn(stmt);


		when(stmt.executeQuery()).thenReturn(rs);
		
		when(rs.next()).thenReturn(true,false,true,false,true,false,true,false);
	
		when(rs.getString(any())).thenReturn("12","s","","","","","","");
		test.getElencoPdcChiusi(con, "999", "999", true);
	}
	@Test
	public void test21() throws Exception {
		
			PdcChiuso pdc = new PdcChiuso();
			pdc.setCodPdc("PDCXXX");
			pdc.setSistInformativo("01");
			String acr = "TEST";
			test.getPresenzaSoftware(pdc,con, acr);
		
	}
	
	@Test
	public void test21bis() throws Exception {
		try {
			PdcChiuso pdc2 = null;
			String acr = null;
			test.getPresenzaSoftware(pdc2,con,acr);
		}catch (Exception e) {
			   assertEquals(e.getClass(),NullPointerException.class);
	            return;
		}
	}
	
	@Test
	public void test22() throws Exception {
		
			PdcChiuso pdc = new PdcChiuso();
			pdc.setCodPdc("PDCXXX");
			pdc.setSistInformativo("01");
			test.getCodiceRelease(pdc,con);
		
	}
	@Test
	public void test22bis() throws Exception {
		try {
			PdcChiuso pdc2 = null;
			test.getCodiceRelease(pdc2,con);
		}catch (Exception e) {
			   assertEquals(e.getClass(),NullPointerException.class);
	            return;
		}
	}

}
