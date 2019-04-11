package root.users;

import java.util.*;
import java.time.*;
import root.houses.House;
import root.bookings.Booking;
import java.time.format.DateTimeFormatter;

public class Owner extends User {
	private static final int NUM_HOUSES  = 10;

	private House [] houses;
	private String bio;
	private int numberOfHouses;
	private String publicEmail;

	public Owner() throws Exception{
		houses = new House[NUM_HOUSES];
		numberOfHouses=0;

		Scanner kb = new Scanner(System.in);

		System.out.print("Bio: ");
		String bio = kb.nextLine();
		System.out.print("Public email username: ");
		String publicEmailUser = kb.nextLine();
		System.out.print("Public email domain: ");
		String publicEmailDomain = kb.nextLine();
		String publicEmail = publicEmailUser + "@" + publicEmailDomain;

		this.setPublicEmail(publicEmail);
		this.setBio(bio);
	}

	public House[] getHouses() {
		return houses;
	}
	public String getBio() {
		return bio;
	}
	public int getNumberOfHouses() {
		return numberOfHouses;
	}
	public String getPublicEmail() {
		return publicEmail;
	}

	public void addHouse(){

		Scanner kb = new Scanner(System.in);

		System.out.println("Fill out the details");
		System.out.print("Name: ");
		String name = kb.nextLine();
		System.out.print("Price per night per person: ");
		Double pricePerNightPerPerson = Double.parseDouble(kb.nextLine());
		System.out.print("Rental Feee: ");
		Double rentalFee = Double.parseDouble(kb.nextLine());
		System.out.print("Location: ");
		String location = kb.nextLine();

		House house = new House(this, name, pricePerNightPerPerson, rentalFee, location);
		if (numberOfHouses == houses.length) {
			House [] aux = new House[houses.length*2];
			for(int i=0 ; i<houses.length; i++)
				aux[i]=houses[i];
			houses=aux;
		}
		houses[numberOfHouses++] = house;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	public void setPublicEmail(String publicEmail) {
		this.publicEmail = publicEmail;
	}

	//remove house from list
	public void removeHouse(String name) {
		boolean flag=false;

		//mudar este -- e o sítio da flag e a procura nao é por nome
		numberOfHouses--;
		for(int i=0; i<houses.length; i++) {
			if (houses[i].getName().equals(name))
				flag=true;
			if(flag){ //if the house was found then next houses are shifted 1 back
				houses[i]=houses[i+1];
				if (houses[i+1]==null)
					break;
			}
		}
	}

	@Override
	public void display(){
		System.out.println("Owner Profile");
		super.display();
		System.out.println("\tBiography = " + bio);
		System.out.println("\tNumber of Houses = " + numberOfHouses);
		System.out.println("\tPublic Email = " + publicEmail);
	}

	@Override
	public void menu(){

		Scanner kb = new Scanner(System.in);

		display();

		while(true){

			System.out.println("Choose an action:");
			System.out.println("m - Manage houses");
			System.out.println("v - View bookings");
			System.out.println("a - Add house");
			System.out.println("q - quit");

			try {
				switch(kb.nextLine().charAt(0)){
					case 'm':
						manageHouses();
						break;
					case 'v':
						manageBookings();
						break;
					case 'a':
						addHouse();
						break;
					case 'q':
						return;
					default:
						System.out.println("Invalid input.");
						break;
				}
			} catch(StringIndexOutOfBoundsException e) {
				continue;
			}
		}
	}

	public void manageBookings(){

    Scanner kb = new Scanner(System.in);
    Booking[] bookings = displayBookings();

    System.out.print("Choose one to get more details: ");
    int i = Integer.parseInt(kb.nextLine());
    bookings[i].display();
  }

  public void manageHouses(){

    Scanner kb = new Scanner(System.in);
    displayHouses();

    System.out.print("Choose one to get more details: ");
    int i = Integer.parseInt(kb.nextLine());
    houses[i].display();
  }

	public void displayHouses(){
		System.out.println("Properties");
		for(int i=0; i<numberOfHouses; i++)
			System.out.println("\t" + i + " : " + houses[i].getName() + ", " + houses[i].getLocation());
	}

	public Booking[] displayBookings(){
		int i = 0;
		Booking previous = null;
		Booking[] bookings = new Booking[100];
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for(House house : houses){
			if(house == null) return bookings;
			for(Booking booking : house.getCalendar()){
				if(booking == null || booking == previous) continue;
				previous = booking;
				bookings[i] = booking; //TODO: aumentar bookings
				System.out.println("\t" + i++ + " : " + house.getName() + ", " + house.getLocation() + ": RM" + booking.getPrice());
			}
		}
		return bookings;
	}

	public House getHouse(int i){
		try{
			return houses[i];
		} catch(Exception e){
			return null;
		}
	}



	//TODO: link
	/*public void link(House house) {
		if (house != null) {
			house.unlinkHouse();
			house.set(this);
			getHouse().addHouse(_);
		}
	}

	public void unlinkHouse(House house) {
		if (house != null) {
			house.set(null);
			getHouse().removeHouse(house);
		}
	}

	public void unlinkHouse(House house, Iterator<House> it) {
		if (house != null) {
			house.setHouse(null);
			it.removeHouse();
		}
	}*/
}
