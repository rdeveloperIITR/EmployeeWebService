# EmployeeWebService
RestfulWeb Service for Dasboard repository (https://github.com/rdeveloperIITR/Dashboard) which can be consumed by using Employee API (REST API) created by using Jersey web service framework.

### Employee API
* Get Employee List from mysql database
http://localhost:8080/employeeService/api/employees  
Get Request

* Get Employee By Id from mysql database
http://localhost:8080/employeeService/api/employees/{id}
Get Request

* Insert an Employee data into mysql database
http://localhost:8080/employeeService/api/employees
Post Request (Pass raw data in JSON format to be added in HTTTP request body)

* Update an Employee data By Id in mysql database
http://localhost:8080/employeeService/api/employees/{id}
Put Request (Pass raw data in JSON format to be added in HTTTP request body)

* Delete an Employee from mysql database with Id
http://localhost:8080/employeeService/api/employees/{id}
Delete Request

* Get Solr Document List based on searchQuery
http://localhost:8080/employeeService/api/search?query={replace_with_your_search_query}
Get Request

* Add a Solr Document into the solr index
http://localhost:8080/employeeService/api/search
Post Request (Pass raw data in JSON format to be added in HTTTP request body)

* Delete a Solr Document from the solr index
http://localhost:8080/employeeService/api/search/delete?id={replace_with_your_id}
Delete Request

