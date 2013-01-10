package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;

public class Files_Execute {
	
	private static class WindowsFile extends File {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static WindowsFile[] makeList(File[] files) {
			WindowsFile[] res = new WindowsFile[files.length];
			
			for(int i = 0; i < files.length; i++) {
				res[i] = new WindowsFile(files[i].toString());
			}
			
			return res;
		}
		
		private Date createdAt = null;
		
		public static Comparator<WindowsFile> COMPARATOR_CREATED_DATE = new Comparator<WindowsFile>() {

			@Override
			public int compare(WindowsFile arg0, WindowsFile arg1) {
				if(arg0 == null || arg1 == null) {
					return 0;
				} else {
					return arg0.createdAt.compareTo(arg1.createdAt);
				}
			}
			
		};
		
		public WindowsFile(String pathname) {
			super(pathname);
			try 
			{ 
				Process ls_proc = Runtime.getRuntime().exec("cmd.exe /c dir \"" + pathname + "\" /tc");
				BufferedReader in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));
				for (int i = 0; i < 5; i++ )
				{
					in.readLine(); 
				}

				String stuff = in.readLine();
				StringTokenizer st = new StringTokenizer(stuff);
				String dateC = st.nextToken();//DATE CREATED 
				String timeC = st.nextToken();//TIME CREATED
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
				this.createdAt = format.parse(dateC + " " + timeC);
				
				in.close();
			}
			catch (IOException e1) 
			{
				System.out.println("Error in getting create time");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File directory = new File("I:/iTunes/iTunes Music/Podcasts/Extra3/");
		File[] files = directory.listFiles();
		WindowsFile[] winFiles = WindowsFile.makeList(files);
		

		Arrays.sort(winFiles, WindowsFile.COMPARATOR_CREATED_DATE);
		DecimalFormat format = new DecimalFormat("000");
		
		for(int i = 1; i < winFiles.length; i++) {
			File newName = new File(winFiles[i - 1].getParent() + "\\" + format.format(i) + " - " + winFiles[i].getName());
			System.out.println(newName);
			winFiles[i].renameTo(newName);
		}
	}

}
