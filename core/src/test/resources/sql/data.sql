INSERT INTO TBL_ACCOUNT(ACCOUNT_ID, ACCOUNT_TYPE) VALUES (1, 'GOOGLE');

INSERT INTO TBL_USER(USER_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, PASSWORD, ACCOUNT_ID) VALUES (1, 'USER-01', 'M', 'L', 'user01@user.com', 'dJ/Hy09vNJkLwMtnAUWx0Dyw7MwTFuyPvud9DvYOQ+M=', 1);

INSERT INTO TBL_USER_GROUP(ID, GROUP_NAME) VALUES (1, 'GROUP 1');

INSERT INTO TBL_USER_LOG(ID, USER_ID) VALUES (1, 1);