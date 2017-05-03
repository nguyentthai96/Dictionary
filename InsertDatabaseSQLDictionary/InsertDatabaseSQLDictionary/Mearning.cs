using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InsertDatabaseSQLDictionary
{
    public class Mearning
    {
        public Word Word { get; set; }
        public Language Language { get; set; }
        public WordType Type { get; set; }
        public Example Example { get; set; }
        public string MearningText { get; set; }
    }
}
