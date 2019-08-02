CREATE TABLE
    TEST_USER
    (
        id INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(30),
        password VARCHAR(30),
        email VARCHAR(100),
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO test_user (id, name, password, email) VALUES (1, 'admin', 'admin', '1111@qq.com');

INSERT INTO test_user (id, name, password, email) VALUES (2, 'hehe', 'hehe', '119@qq.com');


    