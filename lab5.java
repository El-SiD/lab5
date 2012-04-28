import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

	public class lab5 
	{
		public static void main(String[] args)
		{
			try
			{
				Merge("1.txt", "2.txt", "3.txt");
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
				JOptionPane.showMessageDialog(null, "���������� ���������.");
				long Time = System.currentTimeMillis();
				int BufferSize = 550; // in strings
				List<String> Part = new LinkedList<String>();
				Tmp = File.readLine();
				int PartNumber = 0;
				do
				{
					Part.add(Tmp);
					if (Part.size() >= BufferSize) 
					{	
						Collections.sort(Part);
						Part.clear();
						PartNumber++;
					}
					Tmp = File.readLine();
				}
				while (Tmp != null);
				for (int i = 0; i < PartNumber - 1; i++)
				{
					Merge(i + ".txt", i + 1 + ".txt", PartNumber + i + ".txt");
				}
				Time = System.currentTimeMillis() - Time;
				JOptionPane.showMessageDialog(null, "���������� ���������. �����: " + Time);
				
			}
			catch(Exception e)
			{
			/*	JOptionPane.showMessageDialog(null, e.toString());
				e.printStackTrace(); 
			*/
			}
		}
		static void Merge(String input1, String input2, String output) throws IOException
		{
			FileReader file_in1 = new FileReader(input1);
			BufferedReader reader1 = new BufferedReader(file_in1);
			FileReader file_in2 = new FileReader(input2);
			BufferedReader reader2 = new BufferedReader(file_in2);
			FileWriter file_out = new FileWriter(output);
			BufferedWriter writer = new BufferedWriter(file_out);
			String m1, m2;
			List<String> Part = new LinkedList<String>();
			m1 = reader1.readLine();
			m2 = reader2.readLine();
			do
			{
				Part.add(m2);
				m2 = reader2.readLine();
			}
			while (m2 != null);
			do
			{
				if (Part.size() == 0 || m1.compareTo(Part.get(0)) <= 0)
				{
					writer.write(m1 + "\n");
					m1 = reader1.readLine();
				}
				else
				{
					writer.write(Part.get(0) + "\n");
					Part.remove(0);
					writer.write(m1 + "\n");
					m1 = reader1.readLine();
				}
			}
			while (m1 != null);
			for (;;)
				if (Part.size() != 0)
				{	
					writer.write(Part.get(0) + "\n");
					Part.remove(0);
				}
				else break;
			Part.clear();
			reader1.close();
			reader2.close();
			writer.close();
		}
	}