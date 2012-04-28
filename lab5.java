import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

	public class lab5 
	{
		public static void main(String[] args) throws IOException
		{
			File Text = new File("File.txt");
			if (Text.exists()) Text.delete();
			Text.createNewFile();
			File Output = new File("Output.txt");
			if (Output.exists()) Output.delete();
			Output.createNewFile();
			FileWriter FileOut = new FileWriter(Text);
			BufferedWriter Writer = new BufferedWriter(FileOut);
			String Tmp = "";
			long NumberOfStrings = 20, Length = 5; // 4GB = 536870912 UTF-8 Chars (example 33554432 strings by 16 chars)
			for (int i = 0; i < NumberOfStrings; i++)
			{
				for (int j = 0; j < Length; j++)
					Tmp += (char)(26 * Math.random() + 64);
				Tmp += "\n";
				Writer.write(Tmp);
				Tmp = "";
			}
			Writer.close();
			JOptionPane.showMessageDialog(null, "Заполнение завершено.");
			long Time = System.currentTimeMillis();
			int BufferSize = 5; // in strings (optimal 500 - 600)
			List<String> Part = new LinkedList<String>();
			FileReader FileIn = new FileReader(Text);
			BufferedReader Reader = new BufferedReader(FileIn);
			Tmp = Reader.readLine();
			do
			{
				Part.add(Tmp);
				if (Part.size() >= BufferSize) 
				{	
					Collections.sort(Part);
					Merge(new File(Output.getName()), Part, new File("Output.tmp"));
					Part.clear();
				}
				Tmp = Reader.readLine();
			}
			while (Tmp != null);
			Reader.close();
			//Text.delete();
			Time = System.currentTimeMillis() - Time;
			JOptionPane.showMessageDialog(null, "Сортировка завершена. Время: " + Time);
			if (IsSorted(Output)) JOptionPane.showMessageDialog(null, "Файл отсортирован верно"); else JOptionPane.showMessageDialog(null, "Файл отсортирован НЕ верно");
			//Output.delete();
		}
		static void Merge(File input, List<String> Part, File output) throws IOException
		{
			output.createNewFile();
			FileReader file_in = new FileReader(input);
			BufferedReader reader = new BufferedReader(file_in);
			FileWriter file_out = new FileWriter(output);
			BufferedWriter writer = new BufferedWriter(file_out);
			String tmp;
			tmp = reader.readLine();
			while (tmp != null)
			{
				if (Part.size() == 0 || tmp.compareTo(Part.get(0)) <= 0)
				{
					writer.write(tmp + "\n");
					tmp = reader.readLine();
				}
				else
				{
					writer.write(Part.get(0) + "\n");
					Part.remove(0);
				}
			}
			while (Part.size() != 0)
			{	
				writer.write(Part.get(0) + "\n");
				Part.remove(0);
			}
			Part.clear();
			reader.close();
			writer.close();
			input.delete();
			output.renameTo(input);
		}
		static boolean IsSorted(File file) throws IOException
		{
			String tmp1, tmp2;
			FileReader file_in = new FileReader(file);
			BufferedReader reader = new BufferedReader(file_in);
			tmp1 = reader.readLine();
			tmp2 = reader.readLine();
			while (tmp2 != null)
			{
				if (tmp1.compareTo(tmp2) > 0)
				{
					reader.close();
					return false;
				}
				else
				{
					tmp1 = reader.readLine();
					tmp2 = reader.readLine();
				}
			}
			reader.close();
			return true;
		}
	}