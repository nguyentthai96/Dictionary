if db_id('DictionaryDb') is not null
begin
	exec msdb.dbo.sp_delete_database_backuphistory @database_name = N'DictionaryDb'
	use [master]
	drop database DictionaryDb
end
go
create database DictionaryDb;
go
use DictionaryDb;
go 


create table WordType(
	TypeId int primary key,
	TypeName nvarchar(max)
);

create table Language(
	LanguageId varchar(20) primary key,
	LanguageName nvarchar(max),
	Description nvarchar(max)
);

create table Word(
	WordId int primary key,
	WordText nvarchar(max),
	LanguageId varchar(20),
	Pronounce nvarchar(max),
	foreign key (LanguageId) references Language,
);

create table Example(
	ExampleId int primary key,
	ExampleText nvarchar(max),
	MearningExample nvarchar(max)
);

create table Mearning(
	WordId int,
	LanguageId varchar(20),
	TypeId int,
	ExampleId int,
	MearningText nvarchar(max),
	primary key (WordId,LanguageId, TypeId),
	foreign key (WordId) references Word,
	foreign key (LanguageId) references Language,
	foreign key (TypeId) references WordType,
	foreign key (ExampleId) references Example
);

create table WordFavorites(
	WordId int primary key,
	DateFavorites date,
	foreign key (WordId) references Word
);

go
