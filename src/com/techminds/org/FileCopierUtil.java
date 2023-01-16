package com.techminds.org;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class FileCopierUtil {

	public static int filesCopy(String srcFoldLoc, String destFoldLoc, boolean isYearWise) {
		List<File> files = null;
		try {
			files = Files.list(Paths.get(srcFoldLoc)).filter(Files::isRegularFile).map(Path::toFile)
					.collect(Collectors.toList());
			if (isYearWise) {
				for (File file : files) {
					System.out.println(file.getName());
					createFolderWithYear(file.getPath(), destFoldLoc);
				}

			} else {
				for (File file : files) {
					System.out.println(file.getName());
					createFolderWithDate(file.getPath(), destFoldLoc);
				}
			}
			System.out.println("Total Files Moved:" + files.size());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return files.size();
	}

	public static void createFolderWithDate(String sourcePath, String destPath) {
		String dateModified = "";
		System.out.println("textPath:" + sourcePath);
		try {
			Path file = Paths.get(sourcePath);
			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
			FileTime date = attr.lastModifiedTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dateModified = df.format(date.toMillis());

			System.out.println("Date Modified:" + dateModified);
			pathExistOrCreateDirectory(dateModified, destPath);
			movingFileToDestination(sourcePath, dateModified, destPath);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void createFolderWithYear(String sourcePath, String destPath) {
		String dateModified = "";
		System.out.println("textPath:" + sourcePath);
		try {
			Path file = Paths.get(sourcePath);
			BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
			FileTime date = attr.lastModifiedTime();
			DateFormat df = new SimpleDateFormat("yyyy");
			dateModified = df.format(date.toMillis());

			System.out.println("Date Modified:" + dateModified);
			pathExistOrCreateDirectory(dateModified, destPath);
			movingFileToDestination(sourcePath, dateModified, destPath);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void pathExistOrCreateDirectory(String year, String destPath) {

		Path path = Paths.get(destPath + "\\" + year);
		boolean isDir = Files.isDirectory(path);
		System.out.println("IS DIR:" + isDir + "   Year:" + year);
		if (!isDir) {
			File file = new File(destPath + "\\" + year);
			file.mkdir();
		}
	}

	public static String parseFileName(String sourcePath) {
		File file = new File(sourcePath);
		return file.getName();
	}

	public static void movingFileToDestination(String sourceFilePath, String year, String destinationPath) {
		String fileName = parseFileName(sourceFilePath);

		String destinationPathtxt = destinationPath + "\\" + year + "\\" + fileName;
		System.out.println("Destinationpath txt:" + destinationPathtxt);
		Path sPath = Paths.get(sourceFilePath);
		Path dPath = Paths.get(destinationPathtxt);
		try {
			Files.move(sPath, dPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
