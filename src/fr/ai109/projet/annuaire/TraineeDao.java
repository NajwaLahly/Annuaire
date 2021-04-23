package fr.ai109.projet.annuaire;

import java.io.RandomAccessFile;
import java.util.ArrayList;


public class TraineeDao {
	
	public ArrayList<Trainee> traineeList = new ArrayList<Trainee>();
	
	public ArrayList<Trainee> getAll(RandomAccessFile raf, Trainee trainee, BinaryTreeToFile binaryTreeToFile){
		 
		 try {
			long seekPos = 0;
			String[] resultatTab = new String[2];
			while (seekPos != raf.length()) {
				resultatTab = binaryTreeToFile.readTraineeInDestFile(raf, seekPos);
				traineeList.add(trainee.stringToObject(resultatTab[0]));
				seekPos = Long.parseLong(resultatTab[1]) + BinaryTreeToFile.nbreByteToRead*2;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return traineeList;
		
	}
	
}