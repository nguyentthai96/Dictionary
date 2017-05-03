create procedure spMyTest
@word nvarchar(max)
as
select *
from Word, Mearning, WordType
where LOWER(Word.WordText) like + lower(@word) + '%'
	and Word.WordId=Mearning.WordId
	and Mearning.TypeId=WordType.TypeId
order by WordText ASC

exec spMyTest N'accent'

alter procedure spWordAndMeaning
@word nvarchar(max)
as
select top 50 Word.WordId, WordText, MearningText, Pronounce, TypeName, Mearning.LanguageId
from Word, Mearning, WordType
where lower(Word.WordText) like + lower(@word) + '%'
	and Word.WordId=Mearning.WordId
	and Mearning.TypeId=WordType.TypeId

exec spWordAndMeaning N'can'


alter procedure spMeaningWordTypeList
@wordid int
as
select WordType.TypeId, WordType.TypeName
from (select TypeId
	from Mearning
	where WordId=@wordid and LanguageId='vi') as MearningWordId
inner join WordType ON MearningWordId.TypeId=WordType.TypeId

exec spMeaningWordTypeList 264


alter procedure spMeaningWordTypeList
@wordid int
as
select WordType.TypeId, WordType.TypeName
from (select TypeId
	from Mearning
	where WordId=@wordid and LanguageId='vi') as MearningWordId
inner join WordType ON MearningWordId.TypeId=WordType.TypeId


create procedure spListMeaningWithType
@wordid int, @languageid varchar(20), @typeid int
as
	select MearningText, ExampleId
	from Mearning
	where WordId=@wordid and LanguageId=@languageid and TypeId=@typeid

exec spListMeaningWithType 6, 'vi', 2

create procedure spListExample
@exampleGroupId int
as
	select *
	from Example
	where ExampleId=@exampleGroupId;

exec spListExample 1

create procedure spWordWithId
@wordid int
as
	select *
	from Word
	where WordId=@wordid;

select count(WordId) as WordExist from WordFavorites where WordId=1

insert into WordFavorites(WordId, DateFavorites) values (2,'12/13/2012')

delete from WordFavorites where WordId=1