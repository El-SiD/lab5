import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

	public class lab5 
	{
		static long NumberOfStrings = 20, Length = 5; // 4GB = 536870912 UTF-8 Chars (example 33554432 strings by 16 chars)
		static int BufferSize = 5; // in strings (optimal 500 - 600)
		
		public static void main(String[] args) throws IOException
		{
			File Text = new File("File");
			if (Text.exists()) Text.delete();
			Text.createNewFile();
			FileWriter FileOut = new FileWriter(Text);
			BufferedWriter Writer = new BufferedWriter(FileOut);
			String Tmp = "";
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
			Sort(Text);
			//Text.delete();
			Time = System.currentTimeMillis() - Time;
			JOptionPane.showMessageDialog(null, "Сортировка завершена. Время: " + Time);
			if (IsSorted(Text)) JOptionPane.showMessageDialog(null, "Файл отсортирован верно"); else JOptionPane.showMessageDialog(null, "Файл отсортирован НЕ верно");
			//Output.delete();
		}
		static void Sort(File file) throws IOException
		{
			if (file.length() / (Length + 1) <= BufferSize) 
			{
				List<String> Part = new LinkedList<String>();
				FileReader FileIn = new FileReader(file);
				BufferedReader Reader = new BufferedReader(FileIn);
				String Tmp = Reader.readLine();
				do
				{
					Part.add(Tmp);
					Tmp = Reader.readLine();
				}
				while (Tmp != null);
				Reader.close();
				file.delete();
				file.createNewFile();
				Collections.sort(Part);
				FileWriter FileOut = new FileWriter(file);
				BufferedWriter Writer = new BufferedWriter(FileOut);
				while (Part.size() != 0)
				{	
					Writer.write(Part.get(0) + "\n");
					Part.remove(0);
				}
				Writer.close();
			}
			else
			{
				File file0 = new File(file.getName() + "0");
				FileWriter FileOut0 = new FileWriter(file0);
				BufferedWriter Writer0 = new BufferedWriter(FileOut0);
				File file1 = new File(file.getName() + "1");
				FileWriter FileOut1 = new FileWriter(file1);
				BufferedWriter Writer1 = new BufferedWriter(FileOut1);
				FileReader FileIn = new FileReader(file);
				BufferedReader Reader = new BufferedReader(FileIn);
				String Tmp = Reader.readLine();
				long i = 0;
				do
				{
					Writer0.write(Tmp + "\n");
					Tmp = Reader.readLine();
					i++;
				}
				while (i < file.length() / ( 2 * Length + 2));
				Writer0.close();
				do
				{
					Writer1.write(Tmp + "\n");
					Tmp = Reader.readLine();
				}
				while (Tmp != null);
				Writer1.close();
				Reader.close();
				file.delete();
				file.createNewFile();
				Sort(file0);
				Sort(file1);
				Merge(file0, file1, file);
			}
				
		}
		static void Merge(File input1, File input2, File output) throws IOException
		{
			FileReader file_in1 = new FileReader(input1);
			BufferedReader reader1 = new BufferedReader(file_in1);
			FileReader file_in2 = new FileReader(input2);
			BufferedReader reader2 = new BufferedReader(file_in2);
			FileWriter file_out = new FileWriter(output);
			BufferedWriter writer = new BufferedWriter(file_out);
			String tmp1, tmp2;
			tmp1 = reader1.readLine();
			tmp2 = reader2.readLine();
			while (tmp1 != null && tmp2 != null)
			{
				if (tmp1.compareTo(tmp2) <= 0)
				{
					writer.write(tmp1 + "\n");
					tmp1 = reader1.readLine();
				}
				else
				{
					writer.write(tmp2 + "\n");
					tmp2 = reader2.readLine();
				}
			}
			if (tmp1 != null)
			{
				while (tmp1 != null)
				{
					writer.write(tmp1 + "\n");
					tmp1 = reader1.readLine();
				}
			}
			if (tmp2 != null)
			{
				while (tmp2 != null)
				{
					writer.write(tmp2 + "\n");
					tmp2 = reader2.readLine();
				}
			}
			reader1.close();
			reader2.close();
			writer.close();
			input1.delete();
			input2.delete();
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