package com.rohitdeveloper.service.mysql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.rohitdeveloper.service.entity.Employee;


public class Mysql {

	public static PersistenceManagerFactory getInstance() {
		Properties p = new Properties();
		p.setProperty("javax.jdo.PersistenceManagerFactoryClass",
				"org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		p.setProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://localhost:3306/employeedb");
		p.setProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
		p.setProperty("javax.jdo.option.ConnectionUserName", "root");
		p.setProperty("javax.jdo.option.ConnectionPassword", "root");
		p.setProperty("datanucleus.autoCreateSchema", "true");
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(p);
		return pmf;
	}

	/**
	 * returns all Employee list from the database
	 * 
	 * @return
	 */
	public static ArrayList<Employee> selectAllQuery() {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		try {
			tx.begin();
			Query query = pm.newQuery(Employee.class);// select * from
			Collection result = (Collection) query.execute();
			java.util.Iterator iter = result.iterator();
			while (iter.hasNext()) {
				employeeList.add((Employee) iter.next());
			}
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return employeeList;
	}
    
	/**
	 * returns an employee by id from the database
	 * @param id
	 * @return
	 */
	public static Employee selectByIdQuery(Integer id) {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Employee employee = new Employee();
		try {
			tx.begin();
			Query query = pm.newQuery(Employee.class, "id == " + id);
			Collection result = (Collection) query.execute();
			if (result.size() != 0) {
				employee = (Employee) result.iterator().next();
				tx.commit();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return employee;

	}

	/**
	 *  insert Employee data into the database
	 * 
	 * @param name
	 * @return
	 */
	public static Integer insertQuery(String employeename,String designation, Integer salary) {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Integer empId=null;
		try {
			tx.begin();
			Employee employee = new Employee(); // insert object data
			employee.setEmployeename(employeename);
			employee.setDesignation(designation);
			employee.setSalary(salary);
			pm.makePersistent(employee); // insert into table
			tx.commit();
			empId=employee.getId();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return empId;
	}

	/**
	 * delete Employee data from the database
	 * @param id
	 * @return
	 */
	public static boolean deleteQuery(Integer id) {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean isDeleted = false;
		try {
			tx.begin();
			Query query = pm.newQuery(Employee.class, "id == " + id);
			Collection result = (Collection) query.execute();
			if (result.size() != 0) {
				Employee toBeDeleted = (Employee) result.iterator().next();
				pm.deletePersistent(toBeDeleted); // delete from table
				tx.commit();
				isDeleted = true;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return isDeleted;
	}
    
	/**
	 * update Employee data into the database
	 * @param id
	 * @param name
	 * @return
	 */
	public static boolean updateQuery(Integer id, String employeename,String designation, Integer salary) {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean isUpdated = false;
		try {
			tx.begin();
			Query query = pm.newQuery(Employee.class, "id == " + id);
			Collection result = (Collection) query.execute();
			if (result.size() != 0) {
				Employee toBeEdited = (Employee) result.iterator().next();
				toBeEdited.setEmployeename(employeename);
				toBeEdited.setDesignation(designation);
				toBeEdited.setSalary(salary);
				tx.commit();
				isUpdated = true;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return isUpdated;
	}
}
