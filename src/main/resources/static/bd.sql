use db_prueba;

create table tbl_client (
	identity_client INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	client_id varchar(100) NOT NULL UNIQUE,
    client_secret varchar(100) NULL,
    token_alfa varchar(1000) NULL,
    jwt_token varchar(1000) NULL
);

INSERT INTO tbl_client (client_id, client_secret)
values 
('abcdef', 'Erik'), ('ghijkl', 'Denisse'), ('mnopqr', 'Sergio');

select * from tbl_client