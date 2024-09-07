DROP SCHEMA IF EXISTS 'recipes-app';

CREATE SCHEMA 'recipes_app';

USE 'recipes_app';

CREATE TABLE 'users' (
  'id' int NOT NULL AUTO_INCREMENT,
  'username' varchar(64) DEFAULT NULL,
  'description' TEXT DEFAULT NULL,
  PRIMARY KEY ('id')
) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE 'recipes' (
  'id' int NOT NULL AUTO_INCREMENT,
  'name' varchar(64) DEFAULT NULL,
  ''
);

CREATE TABLE 'categories' ();

