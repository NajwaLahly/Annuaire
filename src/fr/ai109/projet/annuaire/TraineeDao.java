package fr.ai109.projet.annuaire;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Stack;


public class TraineeDao {
	
	public static ArrayList<Trainee> sortedTree = new ArrayList<Trainee>();
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
	public void sortTreeInOrder(RandomAccessFile raf, Trainee trainee,BinaryTreeToFile binaryTreeToFile){
	    long rootTree;
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
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}

//	public ArrayList<Trainee> sortTreeInOrder(RandomAccessFile raf, Trainee trainee,BinaryTreeToFile binaryTreeToFile){
//
//		try {
//			long root = 1;
//			Stack<Long> stack = new Stack<Long>();
//			String[] res = new String[2];
//			res = binaryTreeToFile.readTraineeInDestFile(raf, 0);
//			long index = raf.getFilePointer();
//			while(true) {
//				if (root!=0) {
//					stack.push(root);
//
//					//root = root.left
//					root =  binaryTreeToFile.readCurrentChildPos(raf, index, 0);
//					res = binaryTreeToFile.readTraineeInDestFile(raf, root);
//					index = raf.getFilePointer();
//				}
//				else {
//					if(stack.isEmpty()) {
//						break;
//					}
//					root = stack.pop();
//					if (root==1) {
//						res = binaryTreeToFile.readTraineeInDestFile(raf, 0);
//					}
//					else {
//						res= binaryTreeToFile.readTraineeInDestFile(raf, root);
//					}
//					String rootString= res[0];
//					sortedTree.add(trainee.stringToObject(rootString));
//					index = raf.getFilePointer();
//
//					//root = root.right
//					root = binaryTreeToFile.readCurrentChildPos(raf, index, BinaryTreeToFile.nbreByteToRead);
//					res = binaryTreeToFile.readTraineeInDestFile(raf, root);
//					index = raf.getFilePointer();
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return sortedTree;
//	}



	public static void addTraineeInRaf(Trainee trainee){

		RandomAccessFile raf;	
		try {
			raf = new RandomAccessFile(ViewUI.destinationPath, "rw");
			raf.seek(raf.length());
			BinaryTreeToFile bf = new BinaryTreeToFile();
			bf.insertTrainee(trainee, raf);
			Trainee root = bf.getRoot();
			System.out.println("notre root"+root);
			bf.insertTraineeAsChild(root, trainee, raf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}