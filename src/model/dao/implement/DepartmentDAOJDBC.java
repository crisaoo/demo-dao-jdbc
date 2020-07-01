package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDAOJDBC implements DepartmentDAO{
	private Connection conn;
	
	public DepartmentDAOJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		PreparedStatement pst = null;
		
		try {
			pst = conn.prepareStatement("insert into department (name) values (?)", Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, obj.getName());
			int rowsAffected = pst.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next())
					obj.setId(rs.getInt(1));
				DB.closeResultSet(rs);
			}
			else 
				throw new DBException("Unexpected error! No rows affected!");
				
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement pst = null;
		
		try {
			conn.setAutoCommit(false);
			
			pst = conn.prepareStatement("update department set name = ? where id = ?");
			pst.setString(1, obj.getName());
			pst.setInt(2, obj.getId());
			int rowsAffected = pst.executeUpdate();
			
			if (rowsAffected == 0)
				throw new SQLException("Error: ID not found.");				
			if (rowsAffected > 1)
				throw new SQLException("Error: Safe update is enabled.");
			
			conn.commit();
		}
		catch (SQLException e) {
			try {
				conn.rollback();
			}
			catch (SQLException f) {
				f.printStackTrace();
			}
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;
		
		try {
			conn.setAutoCommit(false);
			pst = conn.prepareStatement("delete from department where id = ?");
			pst.setInt(1, id);
			int rowsAffected = pst.executeUpdate();
			
			if (rowsAffected == 0)
				throw new SQLException("Error: ID not found.");				
			if (rowsAffected > 1)
				throw new SQLException("Error: Safe update is enabled.");
			
			conn.commit();
		}
		catch (SQLException e) {
			try {
				conn.rollback();
			}
			catch (SQLException f) {
				f.printStackTrace();
			}
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
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
			throw new DBException(e.getMessage());
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
			throw new DBException(e.getMessage());
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
			throw new DBException(e.getMessage());
		}
	}
}
