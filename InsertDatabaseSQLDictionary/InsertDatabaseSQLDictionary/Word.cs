using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InsertDatabaseSQLDictionary
{
    public class Word
    {
        public int WordId { get; set; }
        public string WordText { get; set; }
        public Language Language { get; set; }

        public string Pronounce { get; set; }

    }
}
