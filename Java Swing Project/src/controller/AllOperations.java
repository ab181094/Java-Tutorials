package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Student;

public class AllOperations {
	private String fileName = "studentData.ser";
	private String duplicateFileName = "duplicate.ser";
	
	File file = new File(fileName);
	File duplicateFile = new File(duplicateFileName);

	public void writeToFile(Student student, boolean append) {
		// TODO Auto-generated method stub
		ObjectOutputStream outputStream = null;
		
		try {
			if (!file.exists() || !append) {
				outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			} else {
				outputStream = new AppendFile(new FileOutputStream(fileName, append));
			}
			outputStream.writeObject(student);
			outputStream.flush();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Error");
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				System.err.println("Error");
			}
		}
	}

	public ArrayList<Student> readFromFile() {
		// TODO Auto-generated method stub
		ArrayList<Student> list = new ArrayList();
		
		if (file.exists()) {
			ObjectInputStream inputStream = null;
			
			try {
				inputStream = new ObjectInputStream(new FileInputStream(fileName));
				
				while (true) {
					Student student = (Student) inputStream.readObject();
					list.add(student);
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return list;
	}

	public void updateIntoFile(ArrayList<Student> list, boolean append) {
		// TODO Auto-generated method stub
		for (Student student : list) {
			ObjectOutputStream outputStream = null;
			
			try {
				if (!duplicateFile.exists() || !append) {
					outputStream = new ObjectOutputStream(new FileOutputStream(duplicateFileName));
				} else {
					outputStream = new AppendFile(new FileOutputStream(duplicateFileName, append));
				}
				outputStream.writeObject(student);
				outputStream.flush();
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Error");
			} finally {
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
					System.err.println("Error");
				}
			}
		}
		
		if (!file.delete()) {
			System.out.println("Can't delete the original file");
		}
		
		if (!duplicateFile.renameTo(file)) {
			System.out.println("Can't rename the duplicate file");
		}
	}
}
