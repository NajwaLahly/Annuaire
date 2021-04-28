package fr.ai109.projet.annuaire;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Stack;


public class TraineeDao {

	public static ArrayList<Trainee> sortedList = new ArrayList<Trainee>();
	public static ArrayList<Trainee> Found = new ArrayList<Trainee>();
	public static ArrayList<Long> startIdxFound = new ArrayList<Long>();
	public static ArrayList<Trainee> FoundFiltered = new ArrayList<Trainee>();
	public static ArrayList<Long> idxFoundFiltered = new ArrayList<Long>();

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

	public void search(RandomAccessFile raf, Trainee trainee, BinaryTreeToFile binaryTreeToFile, int critereaIdx, String traineeToFind){
		long rootTree;
		String[] resultReadInDestFile = new String[2];
		ArrayList<Trainee> traineeFound = new ArrayList<Trainee>();
		ArrayList<Long> startIdxForTraineeFound = new ArrayList<Long>();

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
						startIdxForTraineeFound.add(rootTree);
					}
					endReadPos = Long.parseLong(resultReadInDestFile[1]);
					//root = root.right
					long posRootRight = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, binaryTreeToFile.nbreByteToRead);
					rootTree = posRootRight;
				}

			}
			Found = traineeFound;
			startIdxFound = startIdxForTraineeFound;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteTraineeInRaf(RandomAccessFile raf, long postraineeToDelete, Trainee trainee, BinaryTreeToFile binaryTreeToFile) {
		String[] resultReadTraineeToDelete = new String[2];		
		String[] resultReadTraineeToDeleteChild = new String[2];	
		String[] resultReadSuccessor = new String[2];	
		String[] resultReadParentSuccessor = new String[2];	

		try {
			resultReadTraineeToDelete = binaryTreeToFile.readTraineeInDestFile(raf, postraineeToDelete);
			long traineeToDeleteLeftChildPos = binaryTreeToFile.readCurrentChildPos(raf,Long.parseLong(resultReadTraineeToDelete[1]),0);
			long traineeToDeleteRightChildPos = binaryTreeToFile.readCurrentChildPos(raf,Long.parseLong(resultReadTraineeToDelete[1]), BinaryTreeToFile.nbreByteToRead);

			String[] resultatReadParent = new String[2];

			long[] parent = new long[2];
			parent = binaryTreeToFile.findParent(postraineeToDelete, raf, trainee);
			resultatReadParent = binaryTreeToFile.readTraineeInDestFile(raf, parent[0]);

			if (traineeToDeleteLeftChildPos==0 && traineeToDeleteRightChildPos==0){  //case TraineeToDelete has no child
				raf.seek(Long.parseLong(resultatReadParent[1]) + parent[1]);
				raf.writeLong(0);
			}

			else if (traineeToDeleteLeftChildPos != 0 && traineeToDeleteRightChildPos == 0){ //case traineeToDelete has one left child
				raf.seek(Long.parseLong(resultatReadParent[1]) + parent[1]);
				raf.writeLong(traineeToDeleteLeftChildPos);		
			}

			else if (traineeToDeleteLeftChildPos == 0 && traineeToDeleteRightChildPos != 0){ //case traineeToDelete has one left child
				raf.seek(Long.parseLong(resultatReadParent[1]) + parent[1]);
				raf.writeLong(traineeToDeleteRightChildPos);		
			}

			else {
				long traineeToDeleteLeftGrandChildPos = traineeToDeleteRightChildPos; //initialisation diff de 0
				long successor = 0;
				resultReadTraineeToDeleteChild = binaryTreeToFile.readTraineeInDestFile(raf, traineeToDeleteRightChildPos);
				while(traineeToDeleteLeftGrandChildPos != 0) {
					traineeToDeleteLeftGrandChildPos= binaryTreeToFile.readCurrentChildPos(raf, Long.parseLong(resultReadTraineeToDeleteChild[1]),0);

					resultReadTraineeToDeleteChild = binaryTreeToFile.readTraineeInDestFile(raf, traineeToDeleteLeftGrandChildPos);

					if(traineeToDeleteLeftGrandChildPos != 0) {
						successor = traineeToDeleteLeftGrandChildPos;
						resultReadSuccessor = resultReadTraineeToDeleteChild;
					}
				}

				raf.seek(Long.parseLong(resultatReadParent[1]) + parent[1]);
				raf.writeLong(successor);		

				long resultReadSuccessorRightPos = binaryTreeToFile.readCurrentChildPos(raf, Long.parseLong(resultReadSuccessor[1]),
						BinaryTreeToFile.nbreByteToRead);
				long[] parentSuccessor = new long[2];
				parentSuccessor = binaryTreeToFile.findParent(successor, raf, trainee);
				resultReadParentSuccessor = binaryTreeToFile.readTraineeInDestFile(raf, parentSuccessor[0]);

				raf.seek(Long.parseLong(resultReadParentSuccessor[1]) + parentSuccessor[1]);
				raf.writeLong(resultReadSuccessorRightPos);			
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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



	public void searchInList(Trainee trainee, int critereaIdx, String traineeToFind ,ArrayList<Trainee> list, ArrayList<Long> listIdx){
		ArrayList<Trainee> traineeFoundFiltered = new ArrayList<Trainee>();
		ArrayList<Long> idxTraineeFoundFiltered = new ArrayList<Long>();

		for(int i = 0; i < list.size(); i++) {
			String[] traineeTab = list.get(i).toString().split("\t");
			if(traineeTab[critereaIdx].compareToIgnoreCase(traineeToFind) == 0) {
				traineeFoundFiltered.add(list.get(i));
				idxTraineeFoundFiltered.add(listIdx.get(i));
			}
		}
		FoundFiltered = traineeFoundFiltered;
		idxFoundFiltered = idxTraineeFoundFiltered;
	}
}