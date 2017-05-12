create procedure sp_AddAccount
	@account nvarchar(20),
	@password nvarchar(20)
as
BEGIN
	BEGIN transaction
	declare @resultLogCreateLogin nvarchar(max)
	set @resultLogCreateLogin = 'CREATE LOGIN [' + @account + '] WITH PASSWORD=''' + @password + ''''
	+', DEFAULT_DATABASE=[DictionaryDb], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=ON, CHECK_POLICY=ON;'
	exec(@resultLogCreateLogin)

	declare @resultLogCreateUser nvarchar(max)
	set @resultLogCreateUser='CREATE USER ['+@account+'] FOR LOGIN ['+@account+']'
	exec(@resultLogCreateUser)

	if(@@ERROR<>0)
		BEGIN
			raiserror('Error create login and user!', 16, 1)
			rollback transaction
			return
		end
	commit transaction
END



exec sp_AddAccount N'ntts', N'ntt'

grant select, INSERT,UPDATE,DELETE  on WordFavorites to ntts


create trigger tg_checkUserExsiting
on WordFavorites
for insert
as  
begin
    declare @userName varchar(20)
	declare @wordId int
declare @d1 int
    set @d1=0 
    Select @userName=userName from inserted
    Select @d1=Count(userName) from WordFavorites where userName=@userName and WordId=@wordId
    If (@d1>1)
    Begin
        Raiserror('User Name Exsited',16,1)
        rollback transaction
        RETURN
    End
end



insert into WordFavorites(WordId, DateFavorites, UserName) values (122, '2/2/2017', 'sa');



alter procedure spGetIdWord
@word nvarchar(max)
as
select top(1) Word.WordId
from Word
where LOWER(Word.WordText) = + lower(@word)

exec spGetIdWord N'best'



create FUNCTION checkIsLoginUserPass (@userName varchar(max), @password varchar(max))
RETURNS bit
AS 
BEGIN
 	
	IF NOT EXISTS (SELECT name
		FROM DictionaryDb.dbo.syslogins
		WHERE name = @userName)
	BEGIN
		RETURN 0
	END
		RETURN 1
END;

Select * 
from DictionaryDb.dbo.syslogins

exec sp_AddAccount N'best', N'ntt'

select  dbo.checkIsLoginUserPass('best', 'ntt') as Exist



alter procedure spWordListBookMark
@userName nvarchar(max)
as
select top 50 Word.WordId, WordText, MearningText, Pronounce, TypeName
from Word, Mearning, WordType, WordFavorites
where WordFavorites.WordId = Word.WordId and WordFavorites.UserName=@userName
	and Word.WordId=Mearning.WordId
	and Mearning.TypeId=WordType.TypeId

exec spWordListBookMark N'can'







exec spWordListBookMark N'thai'

exec spWordListBookMark N'nguyenthai'

Select * 
from DictionaryDb.dbo.syslogins

exec sp_AddAccount N'thai', N'123'

grant select, INSERT,UPDATE,DELETE  on WordFavorites to thanhthai

exec sp_AddAccount N'thanhthai', N'123'

grant select, INSERT,UPDATE,DELETE  on WordFavorites to thanhthai