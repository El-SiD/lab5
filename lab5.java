import java.io.*;
import javax.swing.JOptionPane;

	public class lab5 
	{
		public static void main(String[] args)
		{
			try
			{
				RandomAccessFile file = new RandomAccessFile(new File("file.txt"), "rw");
				String tmp = "";
				long NumberOfStrings = 20, Length = 5; 
				for (int i = 0; i < NumberOfStrings; i++)
				{
					for (int j = 0; j < Length; j++)
						tmp += (char)(26 * Math.random() + 64);
					tmp += "\n";
					file.writeUTF(tmp);
					tmp = "";
				}
				JOptionPane.showMessageDialog(null, "Заполнение завершено.");
				long time = System.currentTimeMillis();
				String tmp1 = "";
				String tmp2 = "";
				for (int i = 0; i < NumberOfStrings; i++)
					for (int j = i; j < NumberOfStrings - 1; j++)
					{
						file.seek(j);
						tmp1 = file.readLine();
						file.seek(j + 1);
						tmp2 = file.readLine();
						if (tmp1.compareTo(tmp2) < 0)
						{
							file.seek(j);
							file.writeUTF(tmp2);
							file.seek(j + 1);
							file.writeUTF(tmp1);
						}
					}
				time = System.currentTimeMillis() - time;
				JOptionPane.showMessageDialog(null, "Сортировка завершена. Время: " + time);
				
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e.toString());
				e.printStackTrace(); 
			}
		}
	}