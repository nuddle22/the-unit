package main.java.com.hwz.theunit;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class Unit {
	
	@XmlElement(name="contestant")
	private ArrayList<Contestant> contestants;
	@XmlElement(name="team")
	private ArrayList<Team> teams;
	
	public Unit() {
		contestants = new ArrayList<Contestant>();
		teams = new ArrayList<Team>();
	}
	
	public ArrayList<Contestant> getContestants() {
		return contestants;
	}
	
	public void addContestant(Contestant c) {
		contestants.add(c);
	}
	
	public ArrayList<Team> getTeams() {
		return teams;
	}
	
	public void addTeam(Team t) {
		teams.add(t);
	}
}
