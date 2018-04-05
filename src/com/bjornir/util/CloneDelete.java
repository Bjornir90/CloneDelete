package com.bjornir.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CloneDelete {
	private HashMap<String, File> targetList;
	private ArrayList<File> originList;
	private File[] originTable, targetTable;
	private File origin, target;

	public CloneDelete(String originPath, String targetPath){
		origin = new File(originPath);
		target = new File(targetPath);
		originList = new ArrayList<>();
		targetList = new HashMap<>();
		init();
	}

	private void init(){
		originTable = origin.listFiles();
		targetTable = target.listFiles();
		System.out.println(" Found files (" +originTable.length+") : ");
		for(int i = 0; i<originTable.length; i++){
			originList.add(originTable[i]);
			System.out.println(originTable[i].getName());
		}

		for(int i = 0; i<targetTable.length; i++){
			System.out.println("Found file in target : "+targetTable[i].getName());
			targetList.put(targetTable[i].getName(), targetTable[i]);
		}
	}
	public void deleteClone(){
		for(File f : originList){
			if(f.isFile()){
				File toDelete = targetList.get(f.getName());
				if(toDelete != null) {
					toDelete.delete();
				}
			} else if (f.isDirectory()){
				File targetDirectory = new File(target.getAbsolutePath()+"\\"+f.getName());
				CloneDelete cd = new CloneDelete(f.getAbsolutePath(), targetDirectory.getAbsolutePath());
				cd.deleteClone();
				if(targetDirectory.listFiles().length == 0){
					targetDirectory.delete();
				}
			}
		}
	}

	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Usage : clonedelete originFolder targetFolder");
			return;
		}
		CloneDelete cd = new CloneDelete(args[0], args[1]);
		cd.deleteClone();
		System.out.println("Deleted all files in target");
	}
}
