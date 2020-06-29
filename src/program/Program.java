package program;

import java.util.Date;
import java.util.Locale;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Department obj = new Department(5,"Books");
		
		Seller seller = new Seller(5, "Cristiano", "CristianoCosta@hotmail.com", new Date(), 1500.0 , obj);

		System.out.println(seller);
	}
}
