package program;

import java.util.Locale;

import db.DB;
import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);

		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		Seller seller = sellerDAO.findById(7);
		System.out.println(seller);
		
		DB.closeConnection();
	}
}
