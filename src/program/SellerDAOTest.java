package program;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import db.DB;
import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOTest {
	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();

		/*Test 1: Find By Id*/
		Seller seller = sellerDAO.findById(2);
		System.out.println(seller);

		
		/*Test 2: Find By Department*/
		List<Seller> sellers = sellerDAO.findByDepartment(new Department(2, null));
		sellers.forEach(System.out::println);
		
		
		/*Test 3: Find All*/		
		List<Seller> allSellers = sellerDAO.findAll();
		allSellers.forEach(System.out::println);
		
		
		/*Test 4: Insert*/
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		seller = new Seller(null, "Cassio Costa", "cassio298@hotmail.com", sdf.parse("27/07/1996"), 1500.0, new Department(2, null));
		sellerDAO.insert(seller);
		System.out.println(seller);
				
		
		/*Test 5: Delete*/
		sellerDAO.deleteById(6);
	
		
		/*Test 6: Update*/
		seller = new Seller(8, "Cassio Costa", "cassio298@hotmail.com", sdf.parse("27/07/1996"), 2500.0, new Department(2, null));
		sellerDAO.update(seller);

		DB.closeConnection();
	}
}
