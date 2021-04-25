package fr.ai109.projet.annuaire;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TraineeDao {
	
	public List<Trainee> traineeList = new ArrayList<Trainee>();
	public List<Trainee> sortedTree = new ArrayList<Trainee>();
	
	public List<Trainee> getAll(RandomAccessFile raf, Trainee trainee, BinaryTreeToFile binaryTreeToFile){
		 
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
	
	public static void addTrainee(Trainee trainee, RandomAccessFile raf, BinaryTreeToFile binaryTreeToFile){

        try {
            raf.seek(raf.length());
            binaryTreeToFile.insertTrainee(trainee, raf);
            Trainee root = binaryTreeToFile.getRoot();
            //System.out.println("notre root"+root);
            binaryTreeToFile.insertTraineeAsChild(root, trainee, raf);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
//	public void sortTreeInOrder(RandomAccessFile raf, Trainee trainee,BinaryTreeToFile binaryTreeToFile){
//        long rootTree;
//        String[] resultReadInDestFile = new String[2];
//        try {
//            long startReadPos = 0;
//
//            Stack<Long> stack = new Stack<Long>();
//            stack.push(startReadPos);
//            resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, startReadPos);
//            long endReadPos = Long.parseLong(resultReadInDestFile[1]);
//            long posRootLeft = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, 0);
//            rootTree = posRootLeft;
//            while(true) {
//
//                if (rootTree != 0) {
//                    stack.push(rootTree);
//                    //root = root.left
//                    resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, rootTree);
//                    endReadPos = Long.parseLong(resultReadInDestFile[1]);
//                    posRootLeft = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, 0);
//                    rootTree = posRootLeft;
//                }
//                else {
//                    if(stack.isEmpty()) {
//                        break;
//                    }
//                    rootTree = stack.pop();
//                    resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, rootTree);
//                    sortedTree.add(trainee.stringToObject(resultReadInDestFile[0]));
//                    endReadPos = Long.parseLong(resultReadInDestFile[1]);
//                    //root = root.right
//                    long posRootRight = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, BinaryTreeToFile.nbreByteToRead);
//                    rootTree = posRootRight;
//                }
//            }
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
	
	public void sortTreeInOrder(RandomAccessFile raf, Trainee trainee, BinaryTreeToFile binaryTreeToFile){
		String rootTree;
		String[] resultReadInDestFile = new String[2];
		
		try {
			resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, 0);
			long endReadPos = raf.getFilePointer();
			rootTree = resultReadInDestFile[0];
			Stack<String> stack = new Stack<String>();
			Stack<Long> stackLong = new Stack<Long>();
			long posRootRight;
			while(true) {
				if (!rootTree.equals("")) {
					posRootRight = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, binaryTreeToFile.nbreByteToRead);
					stackLong.push(posRootRight);
					stack.push(rootTree);
					//root = root.left
					long posRootLeft = binaryTreeToFile.readCurrentChildPos(raf, endReadPos, 0);
					if (posRootLeft == 0){
						rootTree = "";
						continue;
					}
					resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, posRootLeft);
					rootTree = resultReadInDestFile[0];
					endReadPos = Long.parseLong(resultReadInDestFile[1]);
				}
				else {
					if(stack.isEmpty()) {
						break;
					}
					rootTree = stack.pop();
					sortedTree.add(trainee.stringToObject(rootTree));
					//root = root.right
					posRootRight = stackLong.pop();
					if (posRootRight == 0){
						rootTree = "";
						continue;
					}
					resultReadInDestFile = binaryTreeToFile.readTraineeInDestFile(raf, posRootRight);
					rootTree = resultReadInDestFile[0];
					endReadPos = Long.parseLong(resultReadInDestFile[1]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}