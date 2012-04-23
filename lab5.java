import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

	public class lab5 
	{
		public static void main(String[] args)
		{
			try
			{
				RandomAccessFile File = new RandomAccessFile(new File("File.txt"), "rw");
				String Tmp = "";
				long NumberOfStrings = 20, Length = 5; // 4GB = 536870912 UTF-8 Chars (example 33554432 strings by 16 chars)
				for (int i = 0; i < NumberOfStrings; i++)
				{
					for (int j = 0; j < Length; j++)
						Tmp += (char)(26 * Math.random() + 64);
					Tmp += "\n";
					File.writeUTF(Tmp);
					Tmp = "";
				}
				JOptionPane.showMessageDialog(null, "Заполнение завершено.");
				long Time = System.currentTimeMillis();
				int BufferSize = 1024; // in chars
				List<String> Part = new LinkedList<String>();
				Tmp = File.readLine();
				int PartNumber = 0;
				do
				{
					Part.add(Tmp);
					if (Part.size() * Length > BufferSize) 
					{	
						Collections.sort(Part);
						WritePart(Part, "" + PartNumber);
						Part.clear();
						PartNumber++;
					}
					Tmp = File.readLine();
				}
				while (Tmp != null);
				for (int i = 0; i < PartNumber; i++)
				{
					Merge("Output.txt", PartNumber + ".txt");
				}
				Time = System.currentTimeMillis() - Time;
				JOptionPane.showMessageDialog(null, "Сортировка завершена. Время: " + Time);
				
			}
			catch(Exception e)
			{
			/*	JOptionPane.showMessageDialog(null, e.toString());
				e.printStackTrace(); 
			*/
			}
		}
		static void WritePart(List<String> Part, String i)
		{
			File file;
			try
			{
				if (i == "0") file = File.createTempFile("Output", "txt");
				else file = File.createTempFile(i, "txt");
				
			}
			catch (Exception e) {}
		}
		static void Merge(String output, String input)
		{
			
		}
	}