package model.dao;

import model.dao.implement.SellerDAOJDBC;

public class DAOFactory {
	public static SellerDAO createSellerDAO () {
		return new SellerDAOJDBC();
	}
}
