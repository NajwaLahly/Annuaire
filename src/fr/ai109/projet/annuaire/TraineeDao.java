package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
	
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Stack;


public class TraineeDao {

	public static ArrayList<Trainee> sortedList = new ArrayList<Trainee>();


	public TraineeDao (){
		RandomAccessFile raf= null;
		try {
			raf = new RandomAccessFile(ViewUI.destinationPath, "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Trainee trainee = new Trainee();
		BinaryTreeToFile binaryTreeToFile = new BinaryTreeToFile();
		sortTreeInOrder(raf, trainee, binaryTreeToFile);

	}

	public ArrayList<Trainee> getAllSorted(){
		return sortedList;
	}



	public void sortTreeInOrder(RandomAccessFile raf, Trainee trainee,BinaryTreeToFile binaryTreeToFile){
		long rootTree;
		ArrayList<Trainee> sortedTree = new ArrayList<Trainee>();
		String[] resultReadInDestFile = new String[2];
		try {
			long startReadPos = 0;

			Stack<Long> stack = new Stack<Long>();
			stack.push(startReadPos);
			resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, startReadPos);
			long endReadPos = Long.parseLong(resultReadInDestFile[1]);
			long posRootLeft = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, 0);
			rootTree = posRootLeft;
			while(true) {

				if (rootTree != 0) {
					stack.push(rootTree);
					//root = root.left
					resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, rootTree);
					endReadPos = Long.parseLong(resultReadInDestFile[1]);
					posRootLeft = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, 0);
					rootTree = posRootLeft;
				}
				else {
					if(stack.isEmpty()) {
						break;
					}
					rootTree = stack.pop();
					resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, rootTree);
					sortedTree.add(trainee.stringToObject(resultReadInDestFile[0]));
					endReadPos = Long.parseLong(resultReadInDestFile[1]);
					//root = root.right
					long posRootRight = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, BinaryTreeToFile.nbreByteToRead);
					rootTree = posRootRight;
				}
			}
			sortedList=sortedTree;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static void addTraineeInRaf(Trainee trainee){

		RandomAccessFile raf;
		try {

			raf = new RandomAccessFile(ViewUI.destinationPath, "rw");
			BinaryTreeToFile bf = new BinaryTreeToFile();
			BufferedReader reader = new BufferedReader(new FileReader(ViewUI.originPath));
			Trainee traineeRoot = bf.getTraineeFromSourceFile(reader);

			BinaryTreeToFile.setEndPosTrainee(raf.length());
			bf.setRoot(raf);
			raf.seek(raf.length());
			BinaryTreeToFile.setStartPosTrainee(BinaryTreeToFile.getEndPosTrainee());
			raf.seek(BinaryTreeToFile.getStartPosTrainee());
			bf.insertTrainee(trainee, raf);
			BinaryTreeToFile.setEndPosTrainee(raf.getFilePointer());
			Trainee root = bf.getRoot();
			bf.insertTraineeAsChild(root, trainee, raf);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Trainee> search(RandomAccessFile raf, Trainee trainee, BinaryTreeToFile binaryTreeToFile, int critereaIdx, String traineeToFind){
		long rootTree;
		String[] resultReadInDestFile = new String[2];
		ArrayList<Trainee> traineeFound = new ArrayList<Trainee>();
		try {
			long startReadPos = 0;
			Stack<Long> stack = new Stack<Long>();
			stack.push(startReadPos);
			resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, startReadPos);
			long endReadPos = Long.parseLong(resultReadInDestFile[1]);
			long posRootLeft = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, 0);
			rootTree = posRootLeft;
			while(true) {

				if (rootTree != 0) {
					stack.push(rootTree);
					//root = root.left
					resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, rootTree);
					endReadPos = Long.parseLong(resultReadInDestFile[1]);
					posRootLeft = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, 0);
					rootTree = posRootLeft;
				}
				else {
					if(stack.isEmpty()) {
						break;
					}
					rootTree = stack.pop();
					resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, rootTree);
					String[] resultToTab= resultReadInDestFile[0].split("\t"); 
					if (resultToTab[critereaIdx].compareToIgnoreCase(traineeToFind) == 0){
						traineeFound.add(trainee.stringToObject(resultReadInDestFile[0]));
					}
					endReadPos = Long.parseLong(resultReadInDestFile[1]);
					//root = root.right
					long posRootRight = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, binaryTreeToFile.nbreByteToRead);
					rootTree = posRootRight;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return traineeFound;
	}

	
//	public static void addTrainee(Trainee trainee, RandomAccessFile raf, BinaryTreeToFile binaryTreeToFile){
//
//        try {
//        	BinaryTreeToFile.setStartPosTrainee(BinaryTreeToFile.getEndPosTrainee());
//			raf.seek(BinaryTreeToFile.getStartPosTrainee());
//            binaryTreeToFile.insertTrainee(trainee, raf);
//            BinaryTreeToFile.setEndPosTrainee(raf.getFilePointer());
//            Trainee root = binaryTreeToFile.getRoot();
//            //System.out.println("notre root"+root);
//            binaryTreeToFile.insertTraineeAsChild(root, trainee, raf);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
	public static void addTraineeInRaf(Trainee trainee, RandomAccessFile raf){

        
        try {
        	
                       
            BinaryTreeToFile bf = new BinaryTreeToFile();
            BufferedReader reader = new BufferedReader(new FileReader(Main.originPath));
            Trainee traineeRoot = bf.getTraineeFromSourceFile(reader);
            
            BinaryTreeToFile.setEndPosTrainee(raf.length());           
			bf.setRoot(raf);
            raf.seek(raf.length());            
            BinaryTreeToFile.setStartPosTrainee(BinaryTreeToFile.getEndPosTrainee());
            raf.seek(BinaryTreeToFile.getStartPosTrainee());
            bf.insertTrainee(trainee, raf);
            BinaryTreeToFile.setEndPosTrainee(raf.getFilePointer());
            Trainee root = bf.getRoot();
            bf.insertTraineeAsChild(root, trainee, raf);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



	public ArrayList<Trainee> searchInList(Trainee trainee, int critereaIdx, String traineeToFind ,ArrayList<Trainee> list){
		ArrayList<Trainee> meetCriterea = new ArrayList<Trainee>();
		for(Trainee t:list) {
			String[] traineeTab = t.toString().split("\t");
			if(traineeTab[critereaIdx].compareToIgnoreCase(traineeToFind) == 0) {
				meetCriterea.add(t);
			}
		}
		return meetCriterea;

	}

}