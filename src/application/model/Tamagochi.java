package application.model;

public class Tamagochi implements java.io.Serializable {

	private static final long serialVersionUID = 1350092881346723535L;
	private String name;
	private STATE state;
	private boolean isAlive = true;
	private int age;

	public Tamagochi() {

	}

	public Tamagochi(String name, STATE state, boolean isAlive, int age) {
		this.name = name;
		this.state = state;
		this.isAlive = isAlive;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
