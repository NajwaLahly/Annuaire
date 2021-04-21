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
			//binaryTreeToFile.originFileToDestinationFile(reader, raf);
			Trainee trainee = binaryTreeToFile.getTraineeFromSourceFile(reader);
			//System.out.println(binaryTreeToFile.getRoot());
			binaryTreeToFile.insertTrainee(trainee, raf, reader);
			//System.out.println(trainee.getPositionInFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
