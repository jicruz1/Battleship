package Battleship;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class EntityTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIniciateShipsLocation() {
		Entity e = new Player();
		e.iniciateShipsLocation();
		for(int i = 0;i <e.MAX_ROW_COL;i++)
			for(int j = 0;j <e.MAX_ROW_COL;j++)
				assertEquals(' ', e.shipsLocation[i][j]);
		
	}
	@Test
	public void testPlaceShip() {
		
		//TDD
		Entity e = new Player();
		e.iniciateShipsLocation();
		Ship ship = new Crusier();
		
		assertTrue(e.placeShip(ship, 0, 0, false));
		assertFalse(e.placeShip(ship, 0, 0, true));
		assertFalse(e.placeShip(ship, 9, 9, true));
		assertFalse(e.placeShip(ship, 9, 9, false));
		
		//TEST Equivalent Partition
		e = new Player();
		e.iniciateShipsLocation();
		ship = new Crusier();
		//Valid inputs
		int[] Xvalids = {0,3,7};
		int[] Yvalids = {0,3,6};
		boolean[] BoolValids = {true, false, false};
		
		for(int i=0;i<3;i++)
		{
			assertTrue(e.placeShip(ship, Xvalids[i], Yvalids[i], BoolValids[i]));
		}
		//Invalid inputs
		e = new Player();
		e.iniciateShipsLocation();
		ship = new Crusier();
		
		int[] Xinvalids = {9,2,5};
		int[] Yinvalids = {1,8,8};
		boolean[] BoolInvalids = {true, false, false};
		for(int i=0;i<3;i++)
		{
			assertFalse(e.placeShip(ship, Xinvalids[i],Yinvalids[i], BoolInvalids[i]));
		}
		
		//TEST Boundary values Test
		
		e = new Player();
		e.iniciateShipsLocation();
		ship = new Crusier();
		
		//Limit values
		int[] Xlimit = {1,1,8,8,10,0,10,1000,0,1000};
		int[] Ylimit = {1,8,1,8,0,10,10,0, 1000,1000};
		boolean[] BoolHoriz1 = {true, true, false, true, false, false, false, false, false, false};
		for(int i=0;i<3;i++)
		{
			assertTrue(e.placeShip(ship, Xlimit[i],Ylimit[i], BoolHoriz1[i]));
		}
		for(int i=3;i<10;i++)
		{
			assertFalse(e.placeShip(ship, Xlimit[i],Ylimit[i], BoolHoriz1[i]));
		}
		
		
		//Boundary values
		e = new Player();
		e.iniciateShipsLocation();
		ship = new Crusier();
		
		int[] Xboundary = {0,0,9,9};
		int[] Yboundary = {0,9,0,9};
		boolean[] BoolHoriz2 = {true, true, false, true};
		for(int i=0;i<3;i++)
		{
			assertTrue(e.placeShip(ship, Xboundary[i],Yboundary[i], BoolHoriz2[i]));
		}
		assertFalse(e.placeShip(ship, Xboundary[3],Yboundary[3], BoolHoriz2[3]));
	}
	@Test
	public void testAskCoords() {
		
		//TDD
		//TEST 1
		String myString = "A1";
		InputStream is = new ByteArrayInputStream( myString.getBytes() );
		
		Player player = new Player();
		player.ScannerLineTester(is);
		assertTrue(player.askCoords());
		
		//TEST 2
		myString = "Z2";
		is = new ByteArrayInputStream( myString.getBytes() );
		
		player = new Player();
		player.ScannerLineTester(is);
		assertFalse(player.askCoords());
		
		//TEST Equivalent Partitions
		//Valid values
		player = new Player();
		String[] str = {"A1","C5","G8","J2"};
		for(int i=0;i<4;i++)
		{
			is = new ByteArrayInputStream( str[i].getBytes() );
			player.ScannerLineTester(is);
			assertTrue(player.askCoords());
		}
		//Invalid values
		player = new Player();
		String[] str1 = {"Z1"," ","00",".-,!","+", "1B", "1 C","A-3"};
		for(int i=0;i<7;i++)
		{
			is = new ByteArrayInputStream( str1[i].getBytes() );
			player.ScannerLineTester(is);
			assertFalse(player.askCoords());
		}
		is = new ByteArrayInputStream( str1[7].getBytes() );
		player.ScannerLineTester(is);
		assertTrue(player.askCoords());
		
		//TEST Boundary values Test
		//Limit values
		player = new Player();
		String[] str2 = {"K1","A0","A11","J11","K11","A-1","J-2"};
		for(int i=0;i<5;i++)
		{
			is = new ByteArrayInputStream( str2[i].getBytes() );
			player.ScannerLineTester(is);
			assertFalse(player.askCoords());
		}
		for(int i=5;i<7;i++)
		{
			is = new ByteArrayInputStream( str2[i].getBytes() );
			player.ScannerLineTester(is);
			assertTrue(player.askCoords());
		}
		//Boundary values
		player = new Player();
		String[] str3 = {"A1","A10","J1","J10"};
		for(int i=0;i<4;i++)
		{
			is = new ByteArrayInputStream( str3[i].getBytes() );
			player.ScannerLineTester(is);
			assertTrue(player.askCoords());
		}

	}
	@Test
	public void testHasWon() {
		//TDD
		Entity e = new Player();
		e.lifes = 5;
		assertFalse(e.hasWon());
		e.lifes = 5;
		e.hits = 5;
		assertTrue(e.hasWon());
		e.hits = 3;
		assertFalse(e.hasWon());
	}
	
}
