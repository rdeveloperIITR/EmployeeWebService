package com.rohitdeveloper.service.solrsearch;

import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.rohitdeveloper.service.entity.Employee;



public class SolrSearch {

	private static final String SOLR_URI = "http://localhost:8983/solr/employeeSearch"; // core is employeeSearch
	private static SolrClient Solr = new HttpSolrClient(SOLR_URI); // solr Client

	public static ArrayList<Employee> searchSolrDocument(String searchQuery) {
		ArrayList<Employee> employeeList=new ArrayList<Employee>();
		try {
			// Preparing Solr query
			SolrQuery query = new SolrQuery();
			query.setQuery("collector:" + searchQuery);
			// Executing the query
			QueryResponse queryResponse = Solr.query(query);
			// Storing the results of the query
			SolrDocumentList docs = queryResponse.getResults();
			 for(int index=0 ; index <docs.size() ;index++) {
					Employee result=new Employee();
					result.setId((Integer) docs.get(index).get("id"));
					result.setEmployeename((String) docs.get(index).get("employeename"));
					result.setDesignation((String) docs.get(index).get("designation"));
					result.setSalary((Integer) docs.get(index).get("salary"));
					employeeList.add(result);
			}
			// Saving the operations
			Solr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeList;
	}

	public static boolean addSolrDocument(Integer id, String employeename, String designation, Integer salary) {
		boolean isDocumentadded = false;
		try {
			// Preparing the Solr document
			SolrInputDocument doc = new SolrInputDocument();
			// Adding fields to the document
			doc.addField("id", id);
			doc.addField("employeename", employeename);
			doc.addField("designation", designation);
			doc.addField("salary", salary);
			//Adding the document to Solr 
		    Solr.add(doc); 
			Solr.commit();
			isDocumentadded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isDocumentadded;
	}

	public static boolean deleteSolrDocument(String id) {
		boolean isDocumentdeleted = false;
		try {   
		    //Deleting the document from Solr 
		    Solr.deleteById(id);        
			Solr.commit();
			isDocumentdeleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isDocumentdeleted;
	}
}
