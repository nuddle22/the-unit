package main.java.com.hwz.theunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TheUnit {
	
//	private static ArrayList<Contestant> contestants = new ArrayList<Contestant>();
//	private static ArrayList<Team> teams = new ArrayList<Team>();
	
	public static void main(String[] args) {
		Unit unit;
		
		Scanner scanIn = new Scanner(System.in);
//		readDataGirls(unit.getContestants());
//		readDataTeamGirls(unit.getContestants(), unit.getTeams());
		unit = loadFromXml();
		
		while (true) {
			Menu.mainMenu();
			System.out.print("\nOption: ");
			String input = scanIn.nextLine();
			
			if (input.matches("\\d+")) {
				switch (input) {
					case "1":
						for (int i = 0; i < unit.getContestants().size(); i++) {
							System.out.println((i+1) + ". " + unit.getContestants().get(i).getName());
						}
						System.out.println();
						System.out.print("Option: ");
						String input2 = scanIn.nextLine();
						
						getRelationship(unit.getContestants().get(Integer.parseInt(input2)-1), unit.getTeams());
						break;
						
					case "2":
						getTeammatesForAll(unit.getContestants());
						break;
						
					case "3":
						getTeammatesForTop9(unit.getContestants());
						break;
						
					case "4":
						getLeaders(unit.getContestants());
						break;
						
					case "5":
						scanIn.close();
						saveToXml(unit);
						System.out.println("Exiting Application.");
						System.exit(0);
						break;
						
					default:
						System.out.println("");
						break;
				}
			}
		}
	}
	
	public static void getLeaders(ArrayList<Contestant> contestants) {
		ArrayList<Contestant> leaderSort = new ArrayList<Contestant>();
		leaderSort.addAll(contestants);
		leaderSort.sort(Comparator.comparing(Contestant::getLeaderCount).reversed());
		
		for (int i = 0; i < leaderSort.size(); i++) {
			if (leaderSort.get(i).getLeaderCount() > 0) {
				System.out.println(leaderSort.get(i).getLeaderCount() + " - " + leaderSort.get(i).getName());
			}
		}
	}
	
	public static void getRelationship(Contestant c, ArrayList<Team> teams) {
		
		LinkedHashSet<Contestant> members = new LinkedHashSet<Contestant>();
		
		for (int i = 0; i < teams.size(); i++) {
			Contestant leader = teams.get(i).getLeader();
			ArrayList<Contestant> team = teams.get(i).getMembers();
			
			if (team.contains(c)) {
				for (int r = 0; r < team.size(); r++) {
					members.add(team.get(r));
				}
				members.add(leader);
				members.remove(c);
			}
			else if (leader.equals(c)) {
				for (int r = 0; r < team.size(); r++) {
					members.add(team.get(r));
				}
			}
		}
		
		System.out.println("Relationship List - " + c.getName() + " - " + members.size() + "\n");
		for (Contestant m:members) {
			System.out.println("- " + m.getName());
		}
	}
	
	public static void getTeammatesForAll(ArrayList<Contestant> contestants) {
		ArrayList<Contestant> teammatesSort = new ArrayList<Contestant>();
		teammatesSort.addAll(contestants);
		teammatesSort.sort(Comparator.comparing(Contestant::getTeammatesCount).reversed());
		
		System.out.println("\n # of contestants teamed up with");
		System.out.println(" ===============================");
		
		for (int i = 0; i < teammatesSort.size(); i++) {
			System.out.println(" " + teammatesSort.get(i).getTeammatesCount() + "/" + teammatesSort.get(i).getMaxTeammates() + " - " 
			+ String.format("%d", ((int)((float)teammatesSort.get(i).getTeammatesCount()/(float)teammatesSort.get(i).getMaxTeammates() * 100))) + "% - " + teammatesSort.get(i).getName());
		}
	}
	
	public static void getTeammatesForTop9(ArrayList<Contestant> contestants) {
		ArrayList<Contestant> teammatesSort = new ArrayList<Contestant>();
		
		for (int i = 0; i < 9; i++) {
			teammatesSort.add(contestants.get(i));
		}
		
		teammatesSort.sort(Comparator.comparing(Contestant::getTeammatesCount).reversed());
		
		System.out.println("\n # of contestants teamed up with");
		System.out.println(" ===============================");
		
		for (int i = 0; i < teammatesSort.size(); i++) {
			System.out.println(" " + teammatesSort.get(i).getTeammatesCount() + "/" + teammatesSort.get(i).getMaxTeammates() + " - " 
			+ String.format("%d", ((int)((float)teammatesSort.get(i).getTeammatesCount()/(float)teammatesSort.get(i).getMaxTeammates() * 100))) + "% - " + teammatesSort.get(i).getName());
		}
	}
	
	public static void readDataGirls(ArrayList<Contestant> contestants) {
		try {
			Scanner fileIn = new Scanner(new File("./src/main/resources/Girls.txt"));
			
			while (fileIn.hasNextLine()) {
				String[] contestantInfo = fileIn.nextLine().split(",");
				
				contestants.add(new Contestant(contestantInfo[0], contestantInfo[1].charAt(0)));
			}
			
			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("File not found.");
		}
	}
	
	public static void readDataTeamGirls(ArrayList<Contestant> contestants, ArrayList<Team> teams) {
		try {
			Scanner fileIn = new Scanner(new File("./src/main/resources/TeamGirls.txt"));
			Team team;
			
			while(fileIn.hasNextLine()) {
				String[] songInfo = fileIn.nextLine().split(",");
				String[] teamInfo = fileIn.nextLine().split(",");
				
				team = new Team(teamInfo[0], songInfo[0], Integer.parseInt(songInfo[1]), teamInfo[1].charAt(0));
				
				String leader = fileIn.nextLine();
				int index = findContestant(leader, contestants);
				if (index >= 0) {
					team.addLeader(contestants.get(index));
					contestants.get(index).updateLeaderCount();
				}
				
				String member = fileIn.nextLine();
				while(!member.equals(";")) {
					index = findContestant(member, contestants);
					if (index >= 0) {
						team.addMember(contestants.get(index));
					}
					
					member = fileIn.nextLine();
				}
				
				teams.add(team);
//				System.out.println("\nTeam " + song + " - " + color + ": " + team.getMembers().size() + "\n");
			}
			
			fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < contestants.size(); i++) {
			LinkedHashSet<Contestant> members = new LinkedHashSet<Contestant>();
			
			for (int j = 0; j < teams.size(); j++) {
				Contestant leader = teams.get(j).getLeader();
				ArrayList<Contestant> team = teams.get(j).getMembers();
				
				if (team.contains(contestants.get(i))) {
					for (int r = 0; r < team.size(); r++) {
						members.add(team.get(r));
					}
					members.add(leader);
					members.remove(contestants.get(i));
					contestants.get(i).updateMaxTeammates(team.size());
				}
				else if (leader.equals(contestants.get(i))) {
					for (int r = 0; r < team.size(); r++) {
						members.add(team.get(r));
					}
					contestants.get(i).updateMaxTeammates(team.size());
				}
			}
			
			contestants.get(i).setTeammatesCount(members.size());
		}
	}
	
	public static int findContestant(String name, ArrayList<Contestant> contestants) {
		for (int i = 0; i < contestants.size(); i++) {
			if (contestants.get(i).getName().equals(name)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static void saveToXml(Unit unit) {
		
		try {
			JAXBContext contextObj = JAXBContext.newInstance(Unit.class);
			
		    Marshaller marshallerObj = contextObj.createMarshaller();  
		    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
		    
		    marshallerObj.marshal(unit, new FileOutputStream("./src/main/resources/unit.xml"));
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Unit loadFromXml() {
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Unit.class);
			
	        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        return (Unit) jaxbUnmarshaller.unmarshal(new File("./src/main/resources/unit.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
        
		return null;
	}
}
