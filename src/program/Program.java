package program;

import java.util.List;
import java.util.Locale;

import db.DB;
import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();

		/*Test 1: Find By Id*/
//		Seller seller = sellerDAO.findById(2);
//		System.out.println(seller);

		
		/*Test 2: Find By Department*/
//		List<Seller> sellers = sellerDAO.findByDepartment(new Department(2, "Computers"));
//		sellers.forEach(System.out::println);
		
		
		/*Test 3: Find All*/		//ERROOOOOOOOO
		List<Seller> allSellers = sellerDAO.findAll();
		allSellers.forEach(System.out::println);
		
		DB.closeConnection();
	}
}
