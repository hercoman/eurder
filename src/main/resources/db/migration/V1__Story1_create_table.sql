SET
search_path TO eurder_project;

create table POSTAL_CODE
(
    ID       integer     NOT NULL,
    ZIP_CODE varchar(8)  NOT NULL,
    CITY     varchar(40) NOT NULL,
    CONSTRAINT PK_POSTAL_CODE primary key (ID)
);
CREATE SEQUENCE postal_code_seq start with 1 increment by 1;

create table ADDRESS
(
    ID                integer      NOT NULL,
    STREET_NAME       varchar(256) NOT NULL,
    STREET_NUMBER     VARCHAR(10)  NOT NULL,
    FK_POSTAL_CODE_ID integer      NOT NULL,
    CONSTRAINT PK_ADDRESS primary key (ID),
    CONSTRAINT FK_POSTAL_CODE_ID foreign key (FK_POSTAL_CODE_ID) references POSTAL_CODE (ID)
);
CREATE SEQUENCE address_seq start with 1 increment by 1;
CREATE TABLE customer
(
    ID            VARCHAR(36)  NOT NULL,
    FIRST_NAME    VARCHAR(100) NOT NULL,
    LAST_NAME     VARCHAR(100) NOT NULL,
    EMAIL         VARCHAR(100) NOT NULL,
    FK_ADDRESS_ID INTEGER NOT NULL,
    PHONE_NUMBER  VARCHAR(40)  NOT NULL,
    CONSTRAINT PK_CUSTOMER PRIMARY KEY (ID),
    CONSTRAINT FK_ADDRESS_ID FOREIGN KEY (FK_ADDRESS_ID) REFERENCES ADDRESS (ID)
);