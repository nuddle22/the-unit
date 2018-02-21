package main.java.com.hwz.theunit;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

public class Team {
	
	@XmlAttribute
	private String color;
	
	@XmlAttribute
	private String song;
	
	@XmlAttribute
	private char gender;
	
	@XmlAttribute
	private int round;
	
	@XmlIDREF
	private Contestant leader;
	
	@XmlElement(name="member")
	@XmlIDREF
	private ArrayList<Contestant> members;
	
	public Team() {
		members = new ArrayList<Contestant>();
	}
	
	public Team(String t_color, String t_song, int t_round, char t_gender) {
		color = t_color;
		song = t_song;
		round = t_round;
		gender = t_gender;
		members = new ArrayList<Contestant>();
	}
	
	public Team(String t_color, String t_song, char t_gender, int t_round, Contestant t_leader, ArrayList<Contestant> t_members) {
		color = t_color;
		song = t_song;
		gender = t_gender;
		round = t_round;
		leader = t_leader;
		members = t_members;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getSong() {
		return song;
	}
	
	public char getGender() {
		return gender;
	}
	
	public int getRound() {
		return round;
	}
	
	public Contestant getLeader() {
		return leader;
	}
	
	public ArrayList<Contestant> getMembers() {
		return members;
	}
	
	public void addLeader(Contestant lead) {
		leader = lead;
	}
	
	public void addMember(Contestant member) {
		members.add(member);
//		System.out.println(member.getName() + " added to " + song + " - " + color + ".");
	}
}
