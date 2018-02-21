package main.java.com.hwz.theunit;

import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contestant {
	
	@XmlAttribute
	@XmlID
	private String id;
	
	@XmlAttribute
	private String name;
	
	@XmlAttribute
	private char gender;
	
	@XmlAttribute
	private String group;
	
	@XmlAttribute
	private int leaderCount;
	
	@XmlAttribute
	private int teammatesCount;
	
	@XmlAttribute
	private int maxTeammates;
	
	private static int counter;
	
	public Contestant() {
		leaderCount = 0;
		teammatesCount = 0;
		maxTeammates = 0;
		counter++;
	}
	
	public Contestant(String c_name, char c_gender) {
		name = c_name;
		gender = c_gender;
		leaderCount = 0;
		teammatesCount = 0;
		maxTeammates = 0;
		counter++;
		id = String.valueOf(counter);
	}
	
	public String getName() {
		return name;
	}
	
	public char getGender() {
		return gender;
	}
	
	public String getGroup() {
		return group;
	}
	
	public int getLeaderCount() {
		return leaderCount;
	}
	
	public void updateLeaderCount() {
		leaderCount++;
	}
	
	public int getTeammatesCount() {
		return teammatesCount;
	}
	
	public void setTeammatesCount(int count) {
		teammatesCount = count;
	}
	
	public int getMaxTeammates() {
		return maxTeammates;
	}
	
	public void updateMaxTeammates(int count) {
		maxTeammates += count;
	}
}