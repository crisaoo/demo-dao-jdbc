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
		
		List<Seller> sellers = sellerDAO.findByDepartment(new Department(2, "Computers"));
		sellers.forEach(System.out::println);
		
//		Seller seller = sellerDAO.findById(2);
//		System.out.println(seller);
		
		DB.closeConnection();
	}
}
