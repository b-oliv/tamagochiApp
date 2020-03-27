package application.model;

public class Tamagochi implements java.io.Serializable {

	private static final long serialVersionUID = 1350092881346723535L;
	private String name;
	private SATIETY satiety;
	private EMOTION emotion;
	private boolean isAlive = true;
	private int age;

	public Tamagochi() {

	}

	public Tamagochi(String name, SATIETY satiety, EMOTION emotion, boolean isAlive, int age) {
		this.name = name;
		this.satiety = satiety;
		this.emotion = emotion;
		this.isAlive = isAlive;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SATIETY getSatiety() {
		return satiety;
	}

	public void setSatiety(SATIETY satiety) {
		this.satiety = satiety;
	}

	public EMOTION getEmotion() {
		return emotion;
	}

	public void setEmotion(EMOTION emotion) {
		this.emotion = emotion;
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
