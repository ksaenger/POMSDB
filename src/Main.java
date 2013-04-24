import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;





public class Main {
	public static Connection conn;
	
	
	//insertScenerio Method (validation Checks)
	public static boolean insertScenario(Scenario currentScenario){
		
		try{
			
		PreparedStatement psInsert = conn
			    .prepareStatement("insert into Scenario values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			psInsert.setString(1,"" + (getLatestCycleNum() + 1));
			psInsert.setString(2, "" + currentScenario.getCashAssets());
			psInsert.setString(3, "" + currentScenario.getSalesPrice());
			psInsert.setString(4, "" + currentScenario.getPassword());
			psInsert.setString(5, "" + currentScenario.getDoubleShift());
			psInsert.setString(6, "" + currentScenario.getNormalShift());
			psInsert.setString(7, "" + currentScenario.getNewEmpAdv());
			psInsert.setString(8, "" + currentScenario.getNewHireCost());
			psInsert.setString(9, "" + currentScenario.getSeveranceCost());
			psInsert.setString(10, "" + currentScenario.getDemand());
			psInsert.setString(11, "" + currentScenario.getBatchSizeProdSmall());
			psInsert.setString(12, "" + currentScenario.getBatchSizeProdLarge());
			psInsert.setString(13, "" + currentScenario.getInventory());
			psInsert.setString(14, "" + currentScenario.getStorageCost());
			psInsert.setString(15, "" + currentScenario.getShrinkage());
			psInsert.setString(16, "" + currentScenario.getTotalLoss());
			psInsert.setString(17, "" + currentScenario.getLoss());
			psInsert.setString(18, "" + currentScenario.getGoodsProduced());
			psInsert.setString(19, "" + currentScenario.getDefectiveGoodsProduced());
			psInsert.setString(20, "" + currentScenario.getDefectiveProdRate());
			psInsert.setString(21, "" + currentScenario.getWaterBottles());
			psInsert.setString(22, "" + currentScenario.getLabels());
			psInsert.setString(23, "" + currentScenario.getPackingCases());
			psInsert.setString(24, "" + currentScenario.getFinProdInspection());
			psInsert.setString(25, "" + currentScenario.getFinBottleInspection());
			psInsert.setString(26, "" + currentScenario.getDemandPenalty());
			psInsert.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.print(e);
			return false;
		}
		return true;
		
	}
	//Prints current contents of Scenario table, for testing
	public static void printScenario(){
		ResultSet rs;
		
		try{
		Statement stmt2 = conn.createStatement();
		rs = stmt2.executeQuery("select * from Scenario");
		
		
		while (rs.next()) {
			for(int x = 1;x<=26;x++)
		  System.out.print(rs.getString(x) + " ");
		System.out.println();
		}
		
		}
		catch(Exception e){
			
		}
	}
	
	//Deletes everything from the Scenario Table
	public static void clearScenarioTable(){
		
		try{
		Statement stmt2 = conn.createStatement();
		stmt2.executeUpdate("delete from Scenario");
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	private static void generateDemandTemplate(String sFileName)
	   {
		try
		{
		    FileWriter writer = new FileWriter(sFileName);
	 
		    writer.append("Year =>");
		    writer.append(',');
		    writer.append("1");
		    writer.append('\n');
	 
		    writer.append("January");
	        writer.append('\n');
	        writer.append("February");
	        writer.append('\n');
	        writer.append("March");
	        writer.append('\n');
	        writer.append("April");
	        writer.append('\n');
	        writer.append("May");
	        writer.append('\n');
	        writer.append("June");
	        writer.append('\n');
	        writer.append("July");
	        writer.append('\n');
	        writer.append("August");
	        writer.append('\n');
	        writer.append("September");
	        writer.append('\n');
	        writer.append("October");
	        writer.append('\n');
	        writer.append("November");
	        writer.append('\n');
	        writer.append("December");
	 
		    //generate whatever data you want
	 
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
	    }
	
	//Reads in CSV file, checks if each line has data for all months given and years, then inserts into demand table
	public static boolean insertDemand(File demandCurve){
		try{
		Scanner fileIn = new Scanner(demandCurve);
		//Scanner lineIn = new Scanner(fileIn.nextLine());
		String[] yearLine = fileIn.nextLine().split(",");
		int totalYears = yearLine.length;
		String[] januaryArray = new String[totalYears];
		String[] februaryArray = new String[totalYears];
		String[] marchArray = new String[totalYears];
		String[] aprilArray = new String[totalYears];
		String[] mayArray = new String[totalYears];
		String[] juneArray = new String[totalYears];
		String[] julyArray = new String[totalYears];
		String[] augustArray = new String[totalYears];
		String[] septemberArray = new String[totalYears];
		String[] octoberArray = new String[totalYears];
		String[] novemberArray = new String[totalYears];
		String[] decemberArray = new String[totalYears];
		
		int currentMonth = 1;
		while(fileIn.hasNextLine()){
		String[] demandLine = fileIn.nextLine().split(",");
		if(demandLine.length != totalYears){
			System.out.println("INVALID DEMAND FILE");
			clearDemandTable();
			fileIn.close();
			return false;
		}
		else{
			for(int x = 0;x<demandLine.length;x++){
				if(demandLine[x].equals("")){
					System.out.println("INVALID DEMAND FILE");
					clearDemandTable();
					fileIn.close();
					return false;
				}
			}
		}
		for(int x = 0;x < totalYears;x++){
			if(currentMonth == 1){
				januaryArray[x] = demandLine[x];
			}
			else if(currentMonth == 2){
				februaryArray[x] = demandLine[x];
			}
			else if(currentMonth == 3){
				marchArray[x] = demandLine[x];
			}
			else if(currentMonth == 4){
				aprilArray[x] = demandLine[x];
			}
			else if(currentMonth == 5){
				mayArray[x] = demandLine[x];
			}
			else if(currentMonth == 6){
				juneArray[x] = demandLine[x];
			}
			else if(currentMonth == 7){
				julyArray[x] = demandLine[x];
			}
			else if(currentMonth == 8){
				augustArray[x] = demandLine[x];
			}
			else if(currentMonth == 9){
				septemberArray[x] = demandLine[x];
			}
			else if(currentMonth == 10){
				octoberArray[x] = demandLine[x];
			}
			else if(currentMonth == 11){
				novemberArray[x] = demandLine[x];
			}
			else if(currentMonth == 12){
				decemberArray[x] = demandLine[x];
			}
		
			
		}
		currentMonth++;
		
		}
		
		fileIn.close();
		
		int currentCycle = 1;
		for(int x=1; x< januaryArray.length; x++){
			
			PreparedStatement psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + januaryArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + januaryArray[x]);
			psInsert.executeUpdate();
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + februaryArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + februaryArray[x]);
			psInsert.executeUpdate();
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + marchArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + marchArray[x]);
			psInsert.executeUpdate();
			
			currentCycle++;
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + aprilArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + aprilArray[x]);
			psInsert.executeUpdate();
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + mayArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + juneArray[x]);
			psInsert.executeUpdate();

			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + juneArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + juneArray[x]);
			psInsert.executeUpdate();
			
			currentCycle++;

			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + julyArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + julyArray[x]);
			psInsert.executeUpdate();
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + augustArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + augustArray[x]);
			psInsert.executeUpdate();
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + septemberArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + septemberArray[x]);
			psInsert.executeUpdate();
			
			currentCycle++;

			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + octoberArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + octoberArray[x]);
			psInsert.executeUpdate();
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + novemberArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + novemberArray[x]);
			psInsert.executeUpdate();
			
			psInsert = conn
				    .prepareStatement("insert into Demand values (?,?,?,?)");
			psInsert.setString(1, "" + currentCycle);
			psInsert.setString(2, "" + decemberArray[0]);
			psInsert.setString(3, "" + x);
			psInsert.setString(4, "" + decemberArray[x]);
			psInsert.executeUpdate();
			
			currentCycle++;
		}
		
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
		
		return true;
		
	}

	//Prints current contents of the Demand table for testing
	public static void printDemand(){
		ResultSet rs;
		
		try{
		Statement stmt2 = conn.createStatement();
		rs = stmt2.executeQuery("select * from Demand");
		
		while (rs.next()) {
			for(int x = 1;x<=4;x++)
		  System.out.print(rs.getString(x) + " ");
		System.out.println();
		}
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Clears everything from the demand table
	public static void clearDemandTable(){
		
		try{
		Statement stmt2 = conn.createStatement();
		stmt2.executeUpdate("delete from Demand");
		
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static LinkedList getDemandList(int currentCycle){
	//currentCycle comes from scenario file
		//Should return linked list
		LinkedList demandList = new LinkedList();
		ResultSet rs;
		try{
			String demandString = "Select Demand from Demand where cycle = '" + currentCycle + "'";
		Statement stmt2 = conn.createStatement();
		rs = stmt2.executeQuery(demandString);
		while (rs.next()) {
		  System.out.println(rs.getInt(1));
		  demandList.add(rs.getInt(1));
		}
		}
		catch(Exception e){
			System.out.println(e);
			return demandList;
		}
		return demandList;
		
	}
	
	//Returns the max scenario number from the scenario table, which correlates to the the current cycle we are on
	public static int getLatestCycleNum(){
			ResultSet rs;
			try{
				String cycleString = "Select MAX(ScenarioNum) from Scenario";
			Statement stmt2 = conn.createStatement();
			rs = stmt2.executeQuery(cycleString);
			rs.next();
			return rs.getInt(1);
			}
			catch(Exception e){
				System.out.println(e);
				return 0;
			}
			
		}
	
	public void generateReport1(){
	
	}
	
	public void generateReport2(){
	
	}
	
	public void generateReport3(){
	
	}
	
	public static void updateScenarioData(Scenario currentScenario){
		//Called by simulator
		//Inserts the scenario after a run into the database, pulls out the next cycles demand and returns the scenario for the next run
		insertScenario(currentScenario);
		//Scenario newScenario = new Scenario(double cashAssets, double salesPrice, String password, int production, double doubleShiftCost, double doubleShiftProdAdv, double normalShiftCost, int newEmpAdv, double newHireCost, double severanceCost, int batchSizeProdSmall, int batchSizeProdLarge, int inventory, double storageCost, double shrinkage, double defectiveProdRate, double waterBottles, double labels, double packingCases, double finProdInspection, double finBottleInspection, int employees);
	
		//return newScenario;
		
		
	}
	
	public void outputGame(Scenario currentScenario){
		
	}
	
	private static void loadGame(){
		//Should return a Scenario
		ResultSet rs;
		try{
			String scenarioString = "Select * from Scenario where ScenarioNum = (Select MAX(ScenarioNum) from Scenario)";
		Statement stmt2 = conn.createStatement();
		rs = stmt2.executeQuery(scenarioString);
		rs.next();
		System.out.println(rs.getString(1));
		}catch(Exception e){
			System.out.println(e);
		}
		//return latestScenario
	}
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		String dbName = "POMSDB";
		String connectionURL = "jdbc:derby:" + dbName + ";";
		try{
		Class.forName(driver);
		conn = DriverManager.getConnection(connectionURL);
		}
		catch(Exception e){
		}
		
		Scenario test = new Scenario(.01, .01, "test", 10, .01, .01, .01, 10, .01, .01, 10, 10, 10, .01, .01, .01, .01, .01, .01, .01, .01, 20);
		clearScenarioTable();
		insertScenario(test);
		insertScenario(test);
		insertScenario(test);
		insertScenario(test);
		printScenario();
		
		File demand = new File("testDemand.csv");
		clearDemandTable();
		insertDemand(demand);
		printDemand();
		getDemandList(getLatestCycleNum());	
		generateDemandTemplate("DemandTemplate.csv"); 
		loadGame();
		
	}

}