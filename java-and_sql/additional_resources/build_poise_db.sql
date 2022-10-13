CREATE DATABASE PoisePMS;

USE PoisePMS;

CREATE TABLE project_info (
	proj_num int, 
	proj_name varchar(50), 
	erf varchar(20),
	total_fee double, 
	total_paid double, 
	proj_deadline date, 
	proj_completed varchar(20) NOT NULL, 
	architect_name varchar(50), 
	contractor_name varchar(50), 
	customer_name varchar(50), 
	proj_manager_name varchar(50), 
	engineer_name varchar(50), 
	primary key (proj_num));

CREATE TABLE site_details (
	erf varchar(20), 
	adress varchar(50) NOT NULL, 
	building_type varchar(20) NOT NULL, 
	primary key (erf));

CREATE TABLE person_details (
	name varchar(50),
	role varchar(20), 
	phone varchar(50),
	email varchar(50),
	adress varchar(50),
	primary key (name));

CREATE TABLE completed_projects (project_num int,proj_completed varchar(20), outstanding_amount double,invoice_necessary bool, primary key (project_num));

ALTER TABLE project_info ADD FOREIGN KEY (architect_name) REFERENCES person_details(name);
ALTER TABLE project_info ADD FOREIGN KEY (contractor_name) REFERENCES person_details(name);
ALTER TABLE project_info ADD FOREIGN KEY (customer_name) REFERENCES person_details(name);
ALTER TABLE project_info ADD FOREIGN KEY (proj_manager_name) REFERENCES person_details(name);
ALTER TABLE project_info ADD FOREIGN KEY (engineer_name) REFERENCES person_details(name);

ALTER TABLE project_info ADD FOREIGN KEY (erf) REFERENCES site_details(erf);

ALTER TABLE completed_projects ADD FOREIGN KEY (project_num) REFERENCES project_info(proj_num);

