package program;

import java.util.List;

import db.DB;
import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDAOTest {
	public static void main(String[] args) {
		DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();
		
		/*Test 1: Find By Id*/
//		Department department = departmentDAO.findById(1);
//		System.out.println(department);
		
		/*Test 2: Find All*/
//		List<Department> allDepartments = departmentDAO.findAll();
//		allDepartments.forEach(System.out::println);
		
		/*Test 3: Insert*/
		Department department = new Department(null, "Cosmetics");
		departmentDAO.insert(department);
		System.out.println(department);
		
		DB.closeConnection();
	}
}
