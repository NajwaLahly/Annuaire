package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinaryTreeToFile {

	public static Trainee root;
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
			String[] tab = promo.split(" ");
			promo = tab[0];
			int promoNumbre = Integer.parseInt(tab[1]);
			int year = Integer.parseInt(reader.readLine());
			trainee = new Trainee(lastName, firstName, postCode, promo, promoNumbre, year);
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

	public long[] insertTrainee(Trainee trainee, RandomAccessFile raf, BufferedReader reader) {
		long initialPosTrainee = 0;
		long finalPosTrainee = 0;
		long[] posTrainee = {initialPosTrainee, finalPosTrainee};
		try {
			initialPosTrainee = raf.getFilePointer();
			//Trainee currentTrainee = getTraineeFromSourceFile(reader);
			byte[] byteCurrentTrainee = trainee.toString().getBytes();
			raf.write(byteCurrentTrainee);
			finalPosTrainee = raf.getFilePointer();
			posTrainee[0] = initialPosTrainee;
			posTrainee[1] = finalPosTrainee;
			long left = 0;
			long right = 0;
			raf.writeLong(left);
			raf.writeLong(right);
			trainee.setPositionInFileLeft(posTrainee[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posTrainee;
	}


	public void originFileToDestinationFile(BufferedReader reader, RandomAccessFile raf) {
		Trainee trainee = getTraineeFromSourceFile(reader);
		long[] posTrainee = insertTrainee(trainee, raf, reader);
		setRoot(raf, posTrainee);
		try {
			while(reader.ready()) {
				trainee = getTraineeFromSourceFile(reader);
				posTrainee = insertTrainee(trainee, raf, reader);
				getParent(getRoot(), trainee, raf);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void getParent(Trainee root, Trainee trainee, RandomAccessFile raf) {
		Trainee parent = null;
		Trainee current = root;
		while(current != null) {
			parent = current;
			if(current.toString().compareTo(trainee.toString()) < 0) {
				trainee.getPositionInFileLeft();

			}
		}
	}
	//get root

	/**
	 * @param raf
	 * @param posTrainee
	 */
	public void setRoot(RandomAccessFile raf, long[] posTrainee) {
		Trainee root = new Trainee();
		try {
			raf.seek(posTrainee[0]);
			long sizeTrainee = posTrainee[1] - posTrainee[0];
			byte[] b = new byte[(int)sizeTrainee];
			raf.read(b);
			String rootString = new String(b);
			root = root.stringToObject(rootString);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.root = root;
	}
	
	public Trainee getRoot() {
		return this.root;
	}


	//insert trainee Byte in correct pos (algo insert in binary Tree)--> arg (data)






}
