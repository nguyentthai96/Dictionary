using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InsertDatabaseSQLDictionary
{
    class Program
    {
        DataProvider_DAL da = new DataProvider_DAL();
        static void Main(string[] args)
        {
            // Read each line of the file into a string array. Each element
            // of the array is one line of the file.
            string[] lines = System.IO.File.ReadAllLines(@"D:\Desktop\DictionaryAndroid\InsertDatabaseSQLDictionary\AnhViet.dict");

            // Display the file contents by using a foreach loop.
            System.Console.WriteLine("Contents of WriteLines2.txt = ");
            //foreach (string line in lines)
            //{
            //    // Use a tab to indent each line of the file.
            //    Console.WriteLine("\t" + line);
            //}

            List<WordType> wordTypes = new List<WordType>();
            var collection=lines.ToList().Distinct().Where(s=>(s.Length>2)&&(s.Substring(0,2).CompareTo("* ")==0));
            int typeId = 1;
            foreach (var item in collection)
            {
                WordType typeWord =new WordType() { TypeId = typeId++, TypeName = item.ToString().Substring(2) };
                wordTypes.Add(typeWord);
            }


            List<Example> examples = new List<Example>();
            List<Language> languages = new List<Language>();
            Language vi = new Language { LanguageId = "vi", LanguageName = "Tiếng Việt", Description = "Ngôn ngữ tiếng Việt" };
            Language en = new Language { LanguageId = "en", LanguageName = "Tiếng Anh", Description = "Ngôn ngữ tiếng Anh" };
            languages.Add(vi);
            languages.Add(en);
            List<Word> words = new List<Word>();
            List<Mearning> mearnings = new List<Mearning>();

            int wordId = 1;
            int exampleId = 1;
            Word word = new Word();
            Example example = new Example();
            WordType type = new WordType();
            foreach (string line in lines)
            {
                //if (wordId > 40) break;
                switch (line.ElementAtOrDefault(0))
                {
                    case '@':
                         word = new Word() { WordId = wordId++, Language = en};
                        if (line.IndexOf('/') < 0)
                        {
                            word.WordText = line.Substring(1);
                            word.Pronounce = "";
                        }
                        else
                        {
                            word.WordText = line.Substring(1, line.IndexOf('/')-1);
                              word.Pronounce = line.Substring(line.IndexOf('/'));
                        }
                        words.Add(word);
                        break;
                    case '*': //wordtype
                        type=wordTypes.SingleOrDefault(s => s.TypeName.CompareTo(line.Substring(2)) == 0);
                        break;
                    case '-': //mearning of wordtype
                        example = new Example();
                        Mearning mearning = new Mearning() { Word = word, Example = example, Language = vi, MearningText = line.Substring(1), Type = type };
                        mearnings.Add(mearning);
                        break;
                    case '=': //example of mearning
                        example.ExampleId = exampleId++;
                        try
                        {
                            example.ExampleText = line.Substring(1, line.IndexOf('+')-1);
                            example.MearningExample = line.Substring(line.IndexOf('+')+2);
                        }
                        catch (Exception)
                        {
                            example.ExampleText = line.Substring(1);
                            example.MearningExample = "";
                        }
                        examples.Add(example);
                        break;
                    case '+':
                        break;
                    case '!':
                        break;
                    default :
                        //Console.WriteLine(line);
                        break;
                }
            }

            DataProvider_DAL da = new DataProvider_DAL();
            foreach (Language item in languages)
            {
                string strErr = "";
                string strCmd = String.Format("insert into Language values('{0}',N'{1}',N'{2}')", item.LanguageId, item.LanguageName, item.Description);
                da.excuteNonQuery(strCmd, ref strErr);
            }

            foreach (WordType item in wordTypes)
            {
                string strErr = "";
                string strCmd = String.Format("insert into WordType values('{0}',N'{1}')", item.TypeId, item.TypeName);
                da.excuteNonQuery(strCmd, ref strErr);
            }

            foreach (Example item in examples)
            {
                string strErr = "";
                string strCmd = String.Format("insert into Example values('{0}',N'{1}',N'{2}')", item.ExampleId, item.ExampleText, item.MearningExample);
                da.excuteNonQuery(strCmd, ref strErr);
            }

            foreach (Word item in words)
            {
                string strErr = "";
                string strCmd = String.Format("insert into Word values('{0}',N'{1}','{2}',N'{3}')", item.WordId, item.WordText, item.Language.LanguageId, item.Pronounce.Replace("'", "''"));
                da.excuteNonQuery(strCmd, ref strErr);
            }
            foreach (Mearning item in mearnings)
            {
                string strErr = "";
                string strCmd = String.Format("insert into Mearning values('{0}','{1}','{2}','{3}',N'{4}')", item.Word.WordId, item.Language.LanguageId, item.Type.TypeId, item.Example.ExampleId, item.MearningText);
                if (item.Example.ExampleText==null)
                    strCmd = String.Format("insert into Mearning(WordId, LanguageId, TypeId, MearningText) values('{0}','{1}','{2}',N'{3}')", item.Word.WordId, item.Language.LanguageId, item.Type.TypeId, item.MearningText);
                da.excuteNonQuery(strCmd, ref strErr);
            }
        }
    }
}
