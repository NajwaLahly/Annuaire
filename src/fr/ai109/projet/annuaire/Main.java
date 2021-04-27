package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;

public class Main {
	
	static String originPath = "stagiaires.txt";
	static String destinationPath = "BinaryTreeFile.txt";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//File binaryfile = new File(destinationPath);
		try {
			//revoir new file pour originePath
			RandomAccessFile raf = new RandomAccessFile(destinationPath, "rw");
			BufferedReader reader = new BufferedReader(new FileReader(originPath));
			BinaryTreeToFile binaryTreeToFile = new BinaryTreeToFile();
			Trainee trainee = new Trainee();
			TraineeDao traineeDao = new TraineeDao();
			
			binaryTreeToFile.originFileToDestinationFile(reader, raf);
			Trainee trainee2 = new Trainee("aaa","jhjdh", "kj", "jh", 2000);
			//raf.seek(raf.length());
			//System.out.println("pointeur avant inser "+raf.getFilePointer());
			
			TraineeDao.addTraineeInRaf(trainee2, raf);
			
			//binaryTreeToFile.insertTrainee(trainee2, raf);
			
			//System.out.println("root " + binaryTreeToFile.getRoot());
			//binaryTreeToFile.insertTraineeAsChild(binaryTreeToFile.getRoot(), trainee2, raf);
//			traineeDao.getAll(raf, trainee, binaryTreeToFile);
//			for(Trainee traineeCurrent : traineeDao.traineeList) {
//					System.out.println(traineeCurrent);
//				}f
			long startTime = System.nanoTime();
			traineeDao.sortTreeInOrder(raf, trainee, binaryTreeToFile);
			long elapsedTime = System.nanoTime() - startTime;
			System.out.println("Total sort time in millis: "
	                + elapsedTime/1000000);
			for(Trainee traineeCurrent : traineeDao.sortedTree) {
				System.out.println(traineeCurrent);
			}
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
