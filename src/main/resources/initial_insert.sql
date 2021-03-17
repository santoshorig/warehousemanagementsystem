SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE bank;
TRUNCATE TABLE bank_branch;
TRUNCATE TABLE business_type;
TRUNCATE TABLE client;
TRUNCATE TABLE client_address;
TRUNCATE TABLE client_bank;
TRUNCATE TABLE client_commodity;
TRUNCATE TABLE client_constitution;
TRUNCATE TABLE client_document;
TRUNCATE TABLE client_mode_of_operation;
TRUNCATE TABLE client_nature_of_business;
TRUNCATE TABLE client_signatory;
TRUNCATE TABLE client_type;
TRUNCATE TABLE commodity;
TRUNCATE TABLE district;
TRUNCATE TABLE document;
TRUNCATE TABLE document_purpose;
TRUNCATE TABLE document_type;
TRUNCATE TABLE employee;
TRUNCATE TABLE employee_position;
TRUNCATE TABLE highmark_risk_category;
TRUNCATE TABLE location;
TRUNCATE TABLE region;
TRUNCATE TABLE region_location;
TRUNCATE TABLE state;
TRUNCATE TABLE takeover_type;
TRUNCATE TABLE tender;
TRUNCATE TABLE user;
TRUNCATE TABLE warehouse;
TRUNCATE TABLE warehouse_type;


INSERT INTO employee_position
VALUES (1, 'Director');

INSERT INTO business_type
VALUES (1, 'Government');

INSERT INTO state
VALUES (1, 'Haryana');

INSERT INTO district
VALUES (1, 'Sohna', 1);

INSERT INTO location
VALUES (1, 'Nirvana Country', '122018', 1);

INSERT INTO employee
VALUES (1, 'KAM01', 'Mr', 'Kiran', 'Kowlgi', 1, NULL, 1, 1, 1);

INSERT INTO user
VALUES (1, 'kiran.kowlgi@gmail.com', '0a6395fd55f0fda9240a27d4ae597379', 'ROLE_ADMIN', 1, 1);

INSERT INTO region
VALUES (1, 'Sohna Road', 1);