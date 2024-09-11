
CREATE SCHEMA IF NOT EXISTS biblioteca;

/* Drop Indexes */

DROP INDEX IF EXISTS biblioteca.isbn_idx;
DROP INDEX IF EXISTS biblioteca.user_book_ind;
DROP INDEX IF EXISTS biblioteca.loan_date_idx;
DROP INDEX IF EXISTS biblioteca.estimated_date_return_idx;
DROP INDEX IF EXISTS biblioteca.document_idx;



/* Drop Tables */

DROP TABLE IF EXISTS biblioteca.loans;
DROP TABLE IF EXISTS biblioteca.book;
DROP TABLE IF EXISTS biblioteca.user;




/* Create Tables */

CREATE TABLE biblioteca.book
(
	book_id bigserial NOT NULL UNIQUE,
	isbn varchar NOT NULL,
	name varchar NOT NULL,
	author varchar NOT NULL,
	edition varchar NOT NULL,
	PRIMARY KEY (book_id)
) WITHOUT OIDS;


CREATE TABLE biblioteca.loans
(
	loan_id bigserial NOT NULL UNIQUE,
	user_id bigint NOT NULL,
	book_id bigint NOT NULL,
	loan_date date NOT NULL,
	estimated_date_return date NOT NULL,
	return_date date,
	PRIMARY KEY (loan_id)
) WITHOUT OIDS;


CREATE TABLE biblioteca.user
(
	user_id bigserial NOT NULL UNIQUE,
	document_type varchar NOT NULL,
	document_number varchar NOT NULL,
	name varchar NOT NULL,
	last_name varchar NOT NULL,
	birth_date date NOT NULL,
	address varchar NOT NULL,
	phone_number varchar,
	email varchar,
	PRIMARY KEY (user_id)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE biblioteca.loans
	ADD FOREIGN KEY (book_id)
	REFERENCES biblioteca.book (book_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE biblioteca.loans
	ADD FOREIGN KEY (user_id)
	REFERENCES biblioteca.user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Create Indexes */

CREATE UNIQUE INDEX isbn_idx ON biblioteca.book (isbn);
CREATE UNIQUE INDEX user_book_ind ON biblioteca.loans (user_id, book_id, return_date);
CREATE INDEX loan_date_idx ON biblioteca.loans (loan_date);
CREATE INDEX estimated_date_return_idx ON biblioteca.loans (estimated_date_return);
CREATE UNIQUE INDEX document_idx ON biblioteca.user (document_number, document_type);



/* Data biblioteca.user */
INSERT INTO biblioteca.user
(user_id, document_type, document_number, "name", last_name, birth_date, address, phone_number, email)
VALUES(1, 'Cedula', '98765431', 'Ernesto', 'Perez', '1985-02-03', 'Calle alguna, siempre viva', '3229876541', 'ernesto.perez@yopmail.com');
INSERT INTO biblioteca.user
(user_id, document_type, document_number, "name", last_name, birth_date, address, phone_number, email)
VALUES(2, 'Cedula', '74125896', 'Juan Manuel', 'Gomez Peralta', '1978-12-13', 'Bogotá D.C. Cedritos', '3116549872', 'juan.peralta@yopmail.com');
INSERT INTO biblioteca.user
(user_id, document_type, document_number, "name", last_name, birth_date, address, phone_number, email)
VALUES(3, 'Cedula', '19654321', 'Monica', 'Hernandez', '1989-07-07', 'Calle 45-98, Casa 158, conjunto Rosales', '3228569747', 'monica.hernandez@gmail.com');


/* Data biblioteca.book */
INSERT INTO biblioteca.book
(book_id, isbn, "name", author, edition)
VALUES(1, '8441532109', 'Código limpio / Clean code: Manual de estilo para el desarrollo ágil de software', 'Robert C. Martin', '2012');
INSERT INTO biblioteca.book
(book_id, isbn, "name", author, edition)
VALUES(2, '8420541095', 'Uml para programadores Java', 'Robert Martín', '1 ed');
INSERT INTO biblioteca.book
(book_id, isbn, "name", author, edition)
VALUES(3, '8418040505', 'Lo que los libros de Historia del Arte no quieren que sepas', 'Blanca Guilera Puig', '2024');
INSERT INTO biblioteca.book
(book_id, isbn, "name", author, edition)
VALUES(4, '8499082475', 'El nombre del viento', 'Patrick Rothfuss', '2018');


/* Data biblioteca.loans */
INSERT INTO biblioteca.loans
(loan_id, user_id, book_id, loan_date, estimated_date_return, return_date)
VALUES(1, 1, 1, '2024-09-04', '2024-09-07', '2024-09-07');