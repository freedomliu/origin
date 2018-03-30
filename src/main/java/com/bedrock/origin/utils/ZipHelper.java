package com.bedrock.origin.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipHelper 
{
	private String comment = "";

	public void setComment(String comment) {
		this.comment = comment;
	}

	static final int BUFFER = 8192;

	private static void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			// System.out.println("压缩：" + basedir + file.getName());
			compressDirectory(file, out, basedir);
		} else {
			// System.out.println("压缩：" + basedir + file.getName());
			compressFile(file, out, basedir);
		}
	}

	/** 压缩一个目录 */
	private static void compressDirectory(File dir, ZipOutputStream out,
			String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private static void compressFile(File file, ZipOutputStream out,
			String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			// 压缩包里包含文件夹
			//ZipEntry entry = new ZipEntry(basedir + file.getName());
			// 压缩包里不包含文件夹
			ZipEntry entry = new ZipEntry(file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void zip(String srcPathName, String zipFileName) {
		File file = new File(srcPathName);
		File zipFile = new File(zipFileName);
		if (!file.exists())
			throw new RuntimeException(srcPathName + "不存在！");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
					new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			out.setEncoding(System.getProperty("sun.jnu.encoding"));// 设置文件名编码方式
			String basedir = "";
			compress(file, out, basedir);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param src
	 *            ：要压缩的目录
	 * @param dest
	 *            ：压缩文件存档
	 * @throws Exception
	 */
	public void zip(String src, String dest, List filter) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(dest));
		File srcFile = new File(src);
		zip(out, srcFile, "", filter);
		out.close();
	}

	/**
	 * @param out
	 * @param srcFile
	 * @param base
	 *            ：根路径
	 * @param filter
	 *            ：过滤
	 * @throws Exception
	 */
	public void zip(ZipOutputStream out, File srcFile, String base, List filter)
			throws Exception {
		if (srcFile.exists() == false) {
			throw new Exception("压缩目录不存在!");
		}

		if (srcFile.isDirectory()) {
			File[] files = srcFile.listFiles();
			base = base.length() == 0 ? "" : base + "/";
			if (isExist(base, filter)) {
				out.putNextEntry(new ZipEntry(base));
			}
			for (int i = 0; i < files.length; i++) {
				zip(out, files[i], base + files[i].getName(), filter);
			}
		} else {
			if (isExist(base, filter)) {
				base = base.length() == 0 ? srcFile.getName() : base;
				ZipEntry zipEntry = new ZipEntry(base);
				zipEntry.setComment(comment);
				out.putNextEntry(zipEntry);
				FileInputStream in = new FileInputStream(srcFile);
				int length = 0;
				byte[] b = new byte[1024];
				while ((length = in.read(b, 0, 1024)) != -1) {
					out.write(b, 0, length);
				}
				in.close();
			}
		}
	}

	/**
	 * 过滤出要压缩的文件夹
	 * 
	 * @param base
	 * @param list
	 * @return
	 */
	public boolean isExist(String base, List list) {
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (base.indexOf((String) list.get(i)) >= 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param srcFile
	 *            ：压缩文件路径
	 * @param dest
	 *            ：解压到的目录
	 * @param deleteFile
	 *            ：解压完成后是否删除文件
	 * @throws Exception
	 */
	public void unZip(String srcFile, String dest, boolean deleteFile)
			throws Exception {
		File file = new File(srcFile);
		if (!file.exists()) {
			throw new Exception("解压文件不存在!");
		}
		ZipFile zipFile = new ZipFile(file);
		Enumeration e = zipFile.getEntries();
		while (e.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) e.nextElement();
			if (zipEntry.isDirectory()) {
				String name = zipEntry.getName();
				name = name.substring(0, name.length() - 1);
				File f = new File(dest + name);
				f.mkdirs();
			} else {
				File f = new File(dest + zipEntry.getName());
				f.getParentFile().mkdirs();
				f.createNewFile();
				InputStream is = zipFile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(f);
				int length = 0;
				byte[] b = new byte[1024];
				while ((length = is.read(b, 0, 1024)) != -1) {
					fos.write(b, 0, length);
				}
				is.close();
				fos.close();
			}
		}

		if (zipFile != null) {
			zipFile.close();
		}

		if (deleteFile) {
			file.deleteOnExit();
		}
	}

	/**
	 * 获取Zip文件的注释信息
	 * 
	 * @param srcFile
	 * @return
	 */
	public static String getZipComment(String srcFile) {
		String comment = "";
		try {
			ZipFile zipFile = new ZipFile(srcFile);
			Enumeration e = zipFile.getEntries();

			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();

				comment = ze.getComment();
				if (comment != null && !comment.equals("")
						&& !comment.equals("null")) {
					break;
				}
			}

			zipFile.close();
		} catch (Exception e) {
			System.out.println("获取zip文件注释信息失败:" + e.getMessage());
		}

		return comment;
	}
	public static void compressFile() throws Exception 
	{
		String inFileFullPath= "D:\\tmp\\abc.txt";
		String outFileFullPath= "D:\\tmp\\abc.zip";
		CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(outFileFullPath), new CRC32());
		ZipOutputStream zos = new ZipOutputStream(cos);
		
		File inFile = new File(inFileFullPath);
		ZipEntry entry = new ZipEntry(inFile.getName());
		zos.putNextEntry(entry);
		
		
		FileInputStream fis = new FileInputStream(inFile);
		int count = 0;
		byte[] data = new byte[1024];
		while((count = fis.read(data))!=-1)
		{
			zos.write(data, 0, count);
		}
		fis.close();
		zos.flush();
		zos.finish();
		zos.close();
		cos.flush();
		cos.close();
	}

	public static void main(String[] args)
	{
		ZipHelper.zip("D:/test", "D:/abc.zip");
	}
}
