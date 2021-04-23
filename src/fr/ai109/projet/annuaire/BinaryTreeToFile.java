package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;


public class BinaryTreeToFile {

	public static Trainee root = new Trainee();
	public static long idxEndRoot;
	public static int nbreByteToRead = 8;
	public static long startPosTrainee = 0;
	public static long endPosTrainee = 0;
	//get a trainee node
	//lire ligne fichier
	//create trainee object
	public Trainee getTraineeFromSourceFile(BufferedReader reader) {

		Trainee trainee = new Trainee();
		try {
			String lastName = reader.readLine();
			String firstName = reader.readLine();
			String postCode = reader.readLine();
			String promo = reader.readLine();
			
			int year = Integer.parseInt(reader.readLine());
			trainee = new Trainee(lastName, firstName, postCode, promo, year);
			reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return trainee;
	}

	//insert trainee in raf
	//get raf position initial	
	//write trainee node sur le fichier raf
	//get raf position final
	//write 2 int (null) for left and write

	public static long getEndPosTrainee() {
		return endPosTrainee;
	}

	public static void setEndPosTrainee(long endPosTrainee) {
		BinaryTreeToFile.endPosTrainee = endPosTrainee;
	}

	public void insertTrainee(Trainee trainee, RandomAccessFile raf, BufferedReader reader) {
		long startPosTraineeInFile = 0;
		long endPosTraineeInFile = 0;
		try {
			String trainAddSep = trainee.toString() +"*";
			byte[] byteCurrentTrainee = trainAddSep.getBytes();
			raf.write(byteCurrentTrainee);
			long start = 0;
			long end = 0;
			raf.writeLong(start);			
			raf.writeLong(end);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void originFileToDestinationFile(BufferedReader reader, RandomAccessFile raf) {


		try {
			Trainee trainee = getTraineeFromSourceFile(reader);
			insertTrainee(trainee, raf, reader);		
			setRoot(raf);
			raf.seek(raf.getFilePointer() + nbreByteToRead*2);
			setEndPosTrainee(raf.getFilePointer());
			while(reader.ready()) {
				Trainee traineeCurrent = getTraineeFromSourceFile(reader);
				setStartPosTrainee(getEndPosTrainee());
				raf.seek(getStartPosTrainee());
				insertTrainee(traineeCurrent, raf, reader);	
				setEndPosTrainee(raf.getFilePointer());
				insertTraineeAsChild(getRoot(), traineeCurrent, raf);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertTraineeAsChild(Trainee root, Trainee trainee, RandomAccessFile raf) {
		Trainee parent = new Trainee();
		Trainee current = new Trainee();
		current = root;
		long idxEndCurrent = idxEndRoot;
		String[] resultatReadInFile =new String[2];
		long posCurrentChildLong = idxEndRoot;

		try {
			while(posCurrentChildLong != 0) {
				parent = current;
				if(current.toString().compareToIgnoreCase(trainee.toString()) < 0) {
					//current = current.right;
					posCurrentChildLong = readCurrentChildPos(raf, idxEndCurrent, nbreByteToRead);					
				}
				else {
					posCurrentChildLong = readCurrentChildPos(raf, idxEndCurrent, 0);
					//read right child object
				}

				//read right/left child object
				if(posCurrentChildLong != 0) {
					resultatReadInFile = readTraineeInDestFile(raf, posCurrentChildLong);
					String CurrentChildString = resultatReadInFile[0];
					idxEndCurrent = Long.parseLong(resultatReadInFile[1]);
					//current = current.R/L
					current = trainee.stringToObject(CurrentChildString);
				}
			}
			if(parent.toString().compareToIgnoreCase(trainee.toString()) < 0) {
				raf.seek(idxEndCurrent + nbreByteToRead);
				raf.writeLong(getStartPosTrainee());				
			}
			else {
				raf.seek(idxEndCurrent);
				raf.writeLong(getStartPosTrainee());
				raf.seek(getEndPosTrainee());

			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * @param raf
	 * @param idxCurrent
	 * @return
	 * @throws IOException
	 */
	public long readCurrentChildPos(RandomAccessFile raf, long idxCurrent, int sideLR) {
		long posCurrentChildLong = 0;
		try {
			raf.seek(idxCurrent + sideLR);
			posCurrentChildLong = raf.readLong();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//read right child pos

		return posCurrentChildLong;
	}
	//get root

	/**
	 * @param raf
	 * @param posTrainee
	 */
	public void setRoot(RandomAccessFile raf) {

		String[] resultReadRoot = new String[2];
		try {
			resultReadRoot = readTraineeInDestFile(raf, 0);
			root = root.stringToObject(resultReadRoot[0]);
			idxEndRoot = Long.parseLong(resultReadRoot[1]);

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public Trainee getRoot() {
		return root;
	}
	public long getIdxEndRoot() {
		return idxEndRoot;
	}

	public String[] readTraineeInDestFile(RandomAccessFile raf, long seekPos) {
		String[] resultat = new String[2];
		String resultatString = "";
		long resultatLong = 0;
		try {
			raf.seek(seekPos);

			String byteRead = "";
			while(!byteRead.equals("*")) {
				resultatString += new String(byteRead);	
				byte[] b = new byte[1];
				raf.read(b);				
				byteRead = new String(b);
				resultatLong = raf.getFilePointer();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultat[0] = resultatString;
		resultat[1] = new Long(resultatLong).toString();
		return resultat;
		//insert trainee Byte in correct pos (algo insert in binary Tree)--> arg (data)
	}

	public static long getStartPosTrainee() {
		return startPosTrainee;
	}

	public static void setStartPosTrainee(long startPosTrainee) {
		BinaryTreeToFile.startPosTrainee = startPosTrainee;
	}

}
