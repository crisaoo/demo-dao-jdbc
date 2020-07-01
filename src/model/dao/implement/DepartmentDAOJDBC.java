package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDAOJDBC implements DepartmentDAO{
	private Connection conn;
	
	public DepartmentDAOJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		
	}

	@Override
	public void update(Department obj) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("select * from department where id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			if (rs.next())
				return instantiateDepartment(rs);
			
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
	public List<Department> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("select * from department order by id");
			rs = pst.executeQuery();
			
			List<Department> allDepartments = new ArrayList<>();
			
			while(rs.next()) 
				allDepartments.add(instantiateDepartment(rs));
			
			return allDepartments;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}
	}
	
	
	private Department instantiateDepartment (ResultSet rs) {
		try {
			Integer id = rs.getInt("id");
			String name = rs.getString("name");		
			return new Department(id, name);
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
}
