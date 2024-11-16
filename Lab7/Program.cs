using System.Runtime.Serialization.Formatters.Binary;

public class Program
{
    public static void Main(string[] args)
    {
        string directoryPath = args[0];
        DirectoryInfo directory = new DirectoryInfo(directoryPath);

        DisplayDirectoryContents(directory);
        FileInfo newestFile = GetNewestItem(directory);
        Console.WriteLine($"\nThe newest file is: {newestFile.Name}\nFile location: {newestFile.FullName}\nCreation date: {newestFile.CreationTime}");
        Console.WriteLine();

        var sortedItems = new SortedDictionary<string, long>(new StringLengthComparer());
        var items = directory.GetFileSystemInfos();
        for (int i = 0; i < items.Length; i++)
        {
            var item = items[i];
            long value;
            if (item is FileInfo file)
            {
                value = file.Length;
            }
            else
            {
                value = ((DirectoryInfo)item).GetFileSystemInfos().Length;
            }
            sortedItems.Add(item.Name, value);
        }

        BinaryFormatter formatter = new BinaryFormatter();
        using (FileStream stream = File.Create("myData.bin"))
        {
            formatter.Serialize(stream, sortedItems);
        }

        SortedDictionary<string, long> deserializedItems;
        using (FileStream stream = File.OpenRead("myData.bin"))
        {
            deserializedItems = (SortedDictionary<string, long>)formatter.Deserialize(stream);
        }

        var keysList = new List<string>(deserializedItems.Keys);

        for (int i = 0; i < deserializedItems.Count; i++)
        {
            var key = keysList[i];
            var value = deserializedItems[key];
            Console.WriteLine($"{key} -> {value}");
        }

    }

    static void DisplayDirectoryContents(DirectoryInfo directory, string indent = "")
    {
        var files = directory.GetFiles();
        for (int i = 0; i < files.Length; i++)
        {
            var file = files[i];
            Console.WriteLine($"{indent}{file.Name} {file.Length} bytes {GetDOSAttributes(file)}");
        }

        var subDirectories = directory.GetDirectories();
        for (int i = 0; i < subDirectories.Length; i++)
        {
            var subDirectory = subDirectories[i];
            Console.WriteLine($"{indent}{subDirectory.Name} ({subDirectory.GetFileSystemInfos().Length}) {GetDOSAttributes(subDirectory)}");
            DisplayDirectoryContents(subDirectory, indent + "    ");
        }
    }

    static FileInfo GetNewestItem(DirectoryInfo directory)
    {
        DateTime newestDate = DateTime.MinValue;
        FileInfo newestFile = null;

        FileInfo[] files = directory.GetFiles();
        for (int i = 0; i < files.Length; i++)
        {
            if (files[i].CreationTime > newestDate)
            {
                newestDate = files[i].CreationTime;
                newestFile = files[i];
            }
        }

        DirectoryInfo[] subDirectories = directory.GetDirectories();
        for (int i = 0; i < subDirectories.Length; i++)
        {
            FileInfo subDirectoryNewestFile = GetNewestItem(subDirectories[i]);
            if (subDirectoryNewestFile != null && subDirectoryNewestFile.CreationTime > newestDate)
            {
                newestDate = subDirectoryNewestFile.CreationTime;
                newestFile = subDirectoryNewestFile;
            }
        }

        return newestFile;
    }

    static string GetDOSAttributes(FileSystemInfo item)
    {
        string attributes = "";

        attributes += item.Attributes.HasFlag(FileAttributes.ReadOnly) ? "r" : "-";
        attributes += item.Attributes.HasFlag(FileAttributes.Archive) ? "a" : "-";
        attributes += item.Attributes.HasFlag(FileAttributes.Hidden) ? "h" : "-";
        attributes += item.Attributes.HasFlag(FileAttributes.System) ? "s" : "-";

        return attributes;
    }
}

[Serializable]
public class StringLengthComparer : IComparer<string>
{
    public int Compare(string x, string y)
    {
        int lengthDifference = x.Length - y.Length;
        if (lengthDifference != 0)
        {
            return lengthDifference;
        }
        else
        {
            return string.Compare(x, y, StringComparison.InvariantCulture);
        }
    }
}
