package model.dao.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOJDBC implements SellerDAO {
	private Connection conn;
	
	public SellerDAOJDBC (Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement pst = null;
		
		try {
			pst = conn.prepareStatement("insert into seller (name, email, birthdate, baseSalary, departmentId)"
							+ "values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1,obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new Date(obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = pst.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if(rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			}
			else
				throw new DbException("Unexpected error! No rows affected.");
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}
	
	
	@Override
	public void update(Seller obj) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT s.*, d.name AS DepartmentName "
									+ "FROM seller AS s   INNER JOIN department AS d "
									+ "ON s.departmentId = d.id  	"
									+ "WHERE s.id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			if (rs.next()) 
				return instantiateSeller(rs);
			
			return null;
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department dep){
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
			        "FROM seller INNER JOIN department " +
			        "ON seller.DepartmentId = department.Id " +
			        "WHERE DepartmentId = ? " +
			        "ORDER BY Name");
						
			List <Seller> sellers = new ArrayList<>();
			pst.setInt(1, dep.getId());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				sellers.add(instantiateSeller(rs,dep));
			}
			
			return sellers;
		} 
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("select seller.*, department.name as DepartmentName "
						+ "from seller join department "
						+ "on seller.departmentid = department.id order by seller.id");
			
			rs = pst.executeQuery();
			
			List <Seller> allSellers = new ArrayList<>();
			Map<Integer, Department> departments = new HashMap<>();
			
			while(rs.next()) {
				int key = rs.getInt("departmentId");
				
				if (!departments.containsKey(key)) {
					departments.put(key, instantiateDepartment(rs));
				}
				
				allSellers.add(instantiateSeller(rs, departments.get(key)));
			}
			
			return allSellers;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}
	
	
	private Seller instantiateSeller (ResultSet rs) throws SQLException {
		Integer id = rs.getInt("Id");
		String name = rs.getString("Name");
		String email = rs.getString("Email");
		Date birthDate = rs .getDate("birthDate");
		Double baseSalary = rs.getDouble("BaseSalary");
		Department department = instantiateDepartment(rs);
		
		return new Seller(id, name, email, birthDate, baseSalary, department);
	}
	
	private Seller instantiateSeller (ResultSet rs, Department department) throws SQLException {
		Integer id = rs.getInt("Id");
		String name = rs.getString("Name");
		String email = rs.getString("Email");
		Date birthDate = rs .getDate("birthDate");
		Double baseSalary = rs.getDouble("BaseSalary");
		
		return new Seller(id, name, email, birthDate, baseSalary, department);
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("DepartmentId"), rs.getString("DepartmentName"));		
	}
}