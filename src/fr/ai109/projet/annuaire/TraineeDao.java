package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
import java.io.FileReader;
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

	//code Fatsah
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