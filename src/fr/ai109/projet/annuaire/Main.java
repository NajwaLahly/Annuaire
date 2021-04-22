package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;

public class Main {
	
	static String originPath = "stagiaires.txt";
	static String destinationPath = "BinaryTreeFile.txt";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File binaryfile = new File(destinationPath);
		try {
			//revoir new file pour originePath
			RandomAccessFile raf = new RandomAccessFile(binaryfile, "rw");
			BufferedReader reader = new BufferedReader(new FileReader(originPath));
			BinaryTreeToFile binaryTreeToFile = new BinaryTreeToFile();
			binaryTreeToFile.originFileToDestinationFile(reader, raf);
//     		Trainee trainee1 = binaryTreeToFile.getTraineeFromSourceFile(reader);
//			binaryTreeToFile.insertTrainee(trainee1, raf, reader);
//			binaryTreeToFile.readCurrentChildPos(raf, 28, 8);
			//System.out.println(trainee);
			
			//binaryTreeToFile.readTraineeInDestFile(raf, 0);
//			Trainee trainee2 = binaryTreeToFile.getTraineeFromSourceFile(reader);
//			binaryTreeToFile.insertTrainee(trainee2, raf, reader);
//			System.out.println(binaryTreeToFile.readCurrentChildPos(raf, 28, 0));
//			for(long idx: BinaryTreeToFile.startIdx) {
//				System.out.println(idx);
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
