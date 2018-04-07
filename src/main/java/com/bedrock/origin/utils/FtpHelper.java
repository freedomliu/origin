/*package com.bedrock.origin.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class FtpHelper {
	public Logger logger = Logger.getLogger(FtpHelper.class);

	private FTPClient ftp = null;
	*//**
	 * Ftp服务器
	 *//*
	private String server = "localhost";
	*//**
	 * 用户名
	 *//*
	private String uname = "anonymous";
	*//**
	 * 密码
	 *//*
	private String password = "";
	*//**
	 * 连接端口，默认21
	 *//*
	private int port = 21;

	private Document document;

	public FtpHelper(String server, int port, String uname, String password) {
		this.server = server;
		if (this.port > 0) {
			this.port = port;
		}
		this.uname = uname;
		this.password = password;
		// 初始化
		ftp = new FTPClient();
	}

	public FtpHelper(String server, int port, String uname, char[] password) {
		this.server = server;
		if (this.port > 0) {
			this.port = port;
		}
		this.uname = uname;
		this.password = String.valueOf(password);
		// 初始化
		System.out.println("初始化FTP服务器配置! [Server:" + this.server + "、"
				+ "port:" + this.port + "、" + "User:" + this.uname + "]");
		ftp = new FTPClient();
	}

	public FtpHelper() {
		ftp = new FTPClient();
	}

	@Override
	public synchronized FtpHelper clone() {
		try {
			FtpHelper tmpObject = (FtpHelper) super.clone();
			return tmpObject;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	*//**
	 * 连接FTP服务器
	 * 
	 * @param server
	 * @param uname
	 * @param password
	 * @return
	 * @throws Exception
	 *//*
	public FTPClient connectFTPServer() {
		// System.out.println("connectFTPServer()...");
		try {
			ftp.configure(getFTPClientConfig());
			ftp.connect(this.server, this.port);
			if (!ftp.login(this.uname, this.password)) {
				ftp.logout();
				ftp = null;
				System.out.println("登录FTP服务器失败,请检查![Server:" + this.server
						+ "、" + "port:" + this.port + "、" + "User:"
						+ this.uname + "、" + "Password:" + this.password + "]");
				logger.error("登录FTP服务器失败,请检查![Server:" + this.server + "、"
						+ "port:" + this.port + "、" + "User:" + this.uname
						+ "、" + "Password:" + this.password + "]");
				return ftp;
			}

			// 文件类型,默认是ASCII
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setControlEncoding("GBK");
			// 设置被动模式
			// ftp.enterLocalPassiveMode();
			ftp.enterLocalActiveMode();

			ftp.setDataTimeout(2000);
			ftp.setBufferSize(1024);
			// 响应信息
			int replyCode = ftp.getReplyCode();
			if ((!FTPReply.isPositiveCompletion(replyCode))) {
				// 关闭Ftp连接
				closeFTPClient();
				// 释放空间
				ftp = null;
				logger.error("登录FTP服务器失败,请检查![Server:" + this.server + "、"
						+ "port:" + this.port + "、" + "User:" + this.uname
						+ "、" + "Password:" + this.password + "]");
			} else {
				return ftp;
			}
		} catch (Exception e) {
			try {
				ftp.disconnect();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			ftp = null;
		}
		return ftp;
	}

		
	*//**
	 * 连接FTP服务器
	 * 
	 * @param server
	 * @param uname
	 * @param password
	 * @return
	 * @throws Exception
	 *//*
	
	 * public FTPClient connectFTPServer() throws Exception { //
	 * System.out.println("connectFTPServer()..."); try {
	 * ftp.configure(getFTPClientConfig()); ftp.connect(this.server, this.port);
	 * if (!ftp.login(this.uname, this.password)) { ftp.logout(); ftp = null;
	 * System.out.println("登录FTP服务器失败,请检查![Server:" + this.server + "、"+"port:"
	 * + this.port + "、" + "User:" + this.uname + "、" + "Password:" +
	 * this.password+"]"); return ftp; }
	 * 
	 * // 文件类型,默认是ASCII ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
	 * ftp.setControlEncoding("GBK"); // 设置被动模式 // ftp.enterLocalPassiveMode();
	 * ftp.enterLocalActiveMode();
	 * 
	 * ftp.setConnectTimeout(2000); ftp.setBufferSize(1024); // 响应信息 int
	 * replyCode = ftp.getReplyCode(); if
	 * ((!FTPReply.isPositiveCompletion(replyCode))) { // 关闭Ftp连接
	 * closeFTPClient(); // 释放空间 ftp = null; throw new
	 * Exception("登录FTP服务器失败,请检查![Server:" + server + "、" + "User:" + uname +
	 * "、" + "Password:" + password+"]"); } else { return ftp; } } catch
	 * (Exception e) { ftp.disconnect(); ftp = null; throw e; } }
	 
	*//**
	 * 配置FTP连接参数
	 * 
	 * @return
	 * @throws Exception
	 *//*
	public FTPClientConfig getFTPClientConfig() throws Exception {
		// System.out.println("getFTPClientConfig()...");
		String systemKey = FTPClientConfig.SYST_NT;
		String serverLanguageCode = "zh";
		FTPClientConfig conf = new FTPClientConfig(systemKey);
		conf.setServerLanguageCode(serverLanguageCode);
		conf.setDefaultDateFormatStr("yyyy-MM-dd");
		return conf;
	}

	*//**
	 * 向FTP根目录上传文件
	 * 
	 * @param localFile
	 * @param newName
	 *            新文件名
	 * @throws Exception
	 *//*
	public Boolean uploadFile(String localFile, String newName)
			throws Exception {
		System.out.println("向FTP根目录上传文件..." + newName);
		InputStream input = null;
		boolean success = false;
		try {
			File file = null;
			if (checkFileExist(localFile)) {
				file = new File(localFile);
			}
			input = new FileInputStream(file);
			success = ftp.storeFile(new String(newName.getBytes("GBK"),
					"ISO-8859-1"), input);
			if (!success) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
		System.out.println("向FTP根目录上传文件结束.");
		return success;
	}

	*//**
	 * 向FTP根目录上传文件
	 * 
	 * @param input
	 * @param newName
	 *            新文件名
	 * @throws Exception
	 *//*
	public Boolean uploadFile(InputStream input, String newName)
			throws Exception {
		boolean success = false;
		try {
			success = ftp.storeFile(newName, input);
			if (!success) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return success;
	}

	*//**
	 * 向FTP指定路径上传文件
	 * 
	 * @param localFile
	 * @param newName
	 *            新文件名
	 * @param remoteFoldPath
	 * @throws Exception
	 *//*
	public Boolean uploadFile(String localFile, String newName,
			String remoteFoldPath) throws Exception {
		System.out.println("向FTP指定路径(" + remoteFoldPath + ")上传文件...");
		InputStream input = null;
		boolean success = false;
		try {
			File file = null;
			if (checkFileExist(localFile)) {
				file = new File(localFile);
			}
			input = new FileInputStream(file);

			// 改变当前路径到指定路径
			if (!this.changeDirectory(remoteFoldPath)) {
				System.out.println("服务器路径不存!");
				return false;
			}
			success = ftp.storeFile(newName, input);
			if (!success) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return success;
	}

	*//**
	 * 向FTP指定路径上传流
	 * 
	 * @param localFile
	 * @param newName
	 *            新文件名
	 * @param remoteFoldPath
	 * @throws Exception
	 *//*
	public Boolean uploadStream(InputStream iputStream, String newName,
			String remoteFoldPath) {
		System.out.println("向FTP指定路径(" + remoteFoldPath + ")上传文件...");
		InputStream input = null;
		boolean success = false;
		try {
			input = iputStream;

			// 改变当前路径到指定路径
			if (!this.changeDirectory(new String(
					remoteFoldPath.getBytes("GBK"), "iso-8859-1"))) {
				System.out.println("服务器路径不存!");
				return false;
			}
			success = ftp.storeFile(newName, input);
			if (!success) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	*//**
	 * 向FTP指定路径上传文件
	 * 
	 * @param input
	 * @param newName
	 *            新文件名
	 * @param remoteFoldPath
	 * @throws Exception
	 *//*
	public Boolean uploadFile(InputStream input, String newName,
			String remoteFoldPath) {
		boolean success = false;
		try {
			// 改变当前路径到指定路径
			if (!this.changeDirectory(remoteFoldPath)) {
				System.out.println("服务器路径不存!");
				return false;
			}
			success = ftp.storeFile(newName, input);
			if (!success) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	*//**
	 * 从FTP服务器下载文件
	 * 
	 * @param remotePath
	 *            FTP路径(不包含文件名)
	 * @param fileName
	 *            下载文件名
	 * @param localPath
	 *            本地路径
	 *//*
	public Boolean downloadFile(String remotePath, String fileName,
			String localPath) {

		BufferedOutputStream output = null;
		boolean success = false;
		try {
			// 检查本地路径
			this.checkFileExist(localPath);
			// 改变工作路径
			if (!this.changeDirectory(new String(remotePath.getBytes("GBK"),
					"iso-8859-1"))) {
				System.out.println("服务器路径不存在");
				return false;
			}
			// 列出当前工作路径下的文件列表
			List<FTPFile> fileList = this.getFileList();
			if (fileList == null || fileList.size() == 0) {
				System.out.println("服务器当前路径下不存在文件！");
				return success;
			}
			for (FTPFile ftpfile : fileList) {
				if (ftpfile.getName().equals(fileName)) {
					File localFilePath = new File(localPath + File.separator
							+ ftpfile.getName());
					output = new BufferedOutputStream(new FileOutputStream(
							localFilePath));
					success = ftp.retrieveFile(ftpfile.getName(), output);
				}
			}
			if (!success) {
				throw new Exception("文件下载失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return success;
	}

		*//**
	 * 从FTP服务器下载文件
	 * 
	 * @param remotePath
	 *            FTP路径(不包含文件名)
	 * @param fileName
	 *            下载文件名
	 * @param localPath
	 *            本地路径
	 *//*
	
	 * public Boolean downloadFile(String remotePath, String fileName, String
	 * localPath) throws Exception {
	 * 
	 * BufferedOutputStream output = null; boolean success = false; try { //
	 * 检查本地路径 this.checkFileExist(localPath); // 改变工作路径 if
	 * (!this.changeDirectory(remotePath)) { System.out.println("服务器路径不存在");
	 * return false; } // 列出当前工作路径下的文件列表 List<FTPFile> fileList =
	 * this.getFileList(); if (fileList == null || fileList.size() == 0) {
	 * System.out.println("服务器当前路径下不存在文件！"); return success; } for (FTPFile
	 * ftpfile : fileList) { if (ftpfile.getName().equals(fileName)) { File
	 * localFilePath = new File(localPath + File.separator + ftpfile.getName());
	 * output = new BufferedOutputStream(new FileOutputStream( localFilePath));
	 * success = ftp.retrieveFile(ftpfile.getName(), output); } } if (!success)
	 * { throw new Exception("文件下载失败!"); } } catch (Exception e) { throw e; }
	 * finally { if (output != null) { output.close(); } } return success; }
	 
	*//**
	 * 从FTP服务器获取文件流
	 * 
	 * @param remoteFilePath
	 * @return
	 * @throws Exception
	 *//*
	public InputStream downloadFile(String remoteFilePath) throws Exception {

		return ftp.retrieveFileStream(remoteFilePath);
	}

	*//**
	 * 获取FTP服务器上指定路径下的文件列表
	 * 
	 * @param filePath
	 * @return
	 *//*
	public List<FTPFile> getFtpServerFileList(String remotePath)
			throws Exception {

		FTPListParseEngine engine = ftp.initiateListParsing(remotePath);
		List<FTPFile> ftpfiles = Arrays.asList(engine.getNext(25));

		return ftpfiles;
	}

	*//**
	 * 获取FTP服务器上[指定路径]下的文件列表
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 *//*
	public List<FTPFile> getFileList(String remotePath) throws Exception {

		List<FTPFile> ftpfiles = Arrays.asList(ftp.listFiles(remotePath));

		return ftpfiles;
	}

	*//**
	 * 获取FTP服务器[当前工作路径]下的文件列表
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 *//*
	public List<FTPFile> getFileList() throws Exception {

		List<FTPFile> ftpfiles = Arrays.asList(ftp.listFiles());

		return ftpfiles;
	}

	*//**
	 * 改变FTP服务器工作路径
	 * 
	 * @param remoteFoldPath
	 *//*
	public Boolean changeDirectory(String remoteFoldPath) {

		try {
			return ftp.changeWorkingDirectory(remoteFoldPath);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}

		*//**
	 * 改变FTP服务器工作路径
	 * 
	 * @param remoteFoldPath
	 *//*
	
	 * public Boolean changeDirectory(String remoteFoldPath) throws Exception {
	 * 
	 * return ftp.changeWorkingDirectory(remoteFoldPath); }
	 
	*//**
	 * 删除文件
	 * 
	 * @param remoteFilePath
	 * @return
	 * @throws Exception
	 *//*
	public Boolean deleteFtpServerFile(String remoteFilePath) throws Exception {

		return ftp.deleteFile(remoteFilePath);
	}

	*//**
	 * 创建目录
	 * 
	 * @param remoteFoldPath
	 * @return
	 *//*
	public boolean createFold(String remoteFoldPath) {
		String dir;
		try {
			dir = new String(remoteFoldPath.getBytes("GBK"), "iso-8859-1");
			boolean flag = ftp.makeDirectory(dir);
			if (!flag) {
				return false;
				// throw new Exception("创建目录失败");
			}
			return true;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	}

		*//**
	 * 创建目录
	 * 
	 * @param remoteFoldPath
	 * @return
	 *//*
	
	 * public boolean createFold(String remoteFoldPath) throws Exception {
	 * String dir=new String(remoteFoldPath.getBytes("GBK"),"iso-8859-1");
	 * boolean flag = ftp.makeDirectory(dir); if (!flag) { return false; //throw
	 * new Exception("创建目录失败"); } return false; }
	 
	*//**
	 * 删除目录
	 * 
	 * @param remoteFoldPath
	 * @return
	 * @throws Exception
	 *//*
	public boolean deleteFold(String remoteFoldPath) throws Exception {

		return ftp.removeDirectory(remoteFoldPath);
	}

	*//**
	 * 删除目录以及文件
	 * 
	 * @param remoteFoldPath
	 * @return
	 *//*
	public boolean deleteFoldAndsubFiles(String remoteFoldPath)
			throws Exception {

		boolean success = false;
		List<FTPFile> list = this.getFileList(remoteFoldPath);
		if (list == null || list.size() == 0) {
			return deleteFold(remoteFoldPath);
		}
		for (FTPFile ftpFile : list) {

			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				success = deleteFoldAndsubFiles(remoteFoldPath + "/" + name);
				if (!success)
					break;
			} else {
				success = deleteFtpServerFile(remoteFoldPath + "/" + name);
				if (!success)
					break;
			}
		}
		if (!success)
			return false;
		success = deleteFold(remoteFoldPath);
		return success;
	}

	*//**
	 * 检查本地路径是否存在
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 *//*
	public boolean checkFileExist(String filePath) throws Exception {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("本地路径不存在,请检查!");
		} else {
			flag = true;
		}
		return flag;
	}

	*//**
	 * 创建XML文件
	 * 
	 * @return
	 *//*
	public Element getCurrentElement() {
		document = DocumentHelper.createDocument();
		return document.addElement("root");
	}

	*//**
	 * 生成目录XML文件
	 *//*
	public void createDirectoryXML(String remotePath, Element fatherElement)
			throws Exception {

		List<FTPFile> list = this.getFileList();
		for (FTPFile ftpfile : list) {
			Element currentElement = fatherElement; // 当前的目录节点
			String newRemotePath = remotePath + ftpfile.getName();
			if (ftpfile.isDirectory()) {
				Element dirElement = fatherElement.addElement("dir");
				dirElement.addAttribute("name", ftpfile.getName());
				currentElement = dirElement;
				this.changeDirectory(newRemotePath); // 从根目录开始
				createDirectoryXML(newRemotePath, dirElement);
			} else {
				Element fileElement = fatherElement.addElement("file");// 文件节点
				fileElement.setText(ftpfile.getName());
			}
		}
	}

	*//**
	 * 保存xml
	 *//*
	public void saveXML() {
		XMLWriter output = new XMLWriter();
		// 输出格式化
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter("src/config/ftp/dir.xml"),
					format);
			output.write(this.document);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 关闭FTP连接
	 * 
	 * @param ftp
	 * @throws Exception
	 *//*
	public void closeFTPClient(FTPClient ftp) throws Exception {

		try {
			if (ftp.isConnected())
				ftp.logout();
			ftp.disconnect();
		} catch (Exception e) {
			throw new Exception("关闭FTP服务出错!");
		}
	}

	*//**
	 * 关闭FTP连接
	 * 
	 * @throws Exception
	 *//*
	public void closeFTPClient() {

		try {
			this.closeFTPClient(this.ftp);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

		*//**
	 * 关闭FTP连接
	 * 
	 * @throws Exception
	 *//*
	
	 * public void closeFTPClient() throws Exception {
	 * 
	 * this.closeFTPClient(this.ftp); }
	 
	*//**
	 * Get Attribute Method
	 * 
	 *//*
	public FTPClient getFtp() {
		return ftp;
	}

	public String getServer() {
		return server;
	}

	public String getUname() {
		return uname;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	*//**
	 * Set Attribute Method
	 * 
	 *//*
	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	*//** *//*
	*//**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @return 上传的状态
	 * @throws IOException
	 *//*
	public boolean download(FTPFile remoteFile, String local) {
		// 设置被动模式
		ftp.enterLocalPassiveMode();
		// 设置以二进制方式传输
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			boolean result = false;

			long lRemoteSize = remoteFile.getSize();
			// String remote=remoteFile.getName();
			String remote = remoteFile.getName().substring(
					remoteFile.getName().length() - 63);

			File f = new File(local);
			// 本地存在文件，进行断点下载
			if (f.exists()) {
				long localSize = f.length();
				// 判断本地文件大小是否大于远程文件大小
				if (localSize >= lRemoteSize) {
					System.out.println("本地文件大于远程文件，下载中止");
					return result;
				}

				// 进行断点续传，并记录状态
				FileOutputStream out = new FileOutputStream(f, true);
				ftp.setRestartOffset(localSize);
				InputStream in = ftp.retrieveFileStream(new String(remote
						.getBytes("GBK"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				long step = lRemoteSize / 100;
				long process = localSize / step;
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
					localSize += c;
					long nowProcess = localSize / step;
					if (nowProcess > process) {
						process = nowProcess;
						// if(process % 10 == 0)
						// System.out.println("下载进度："+process);
						// TODO 更新文件下载进度,值存放在process变量中
					}
				}
				in.close();
				out.close();
				boolean isDo = ftp.completePendingCommand();
				if (isDo) {
					result = true;
				} else {
					result = false;
				}
			} else {
				OutputStream out = new FileOutputStream(f);
				InputStream in = ftp.retrieveFileStream(new String(remote
						.getBytes("GBK"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				long step = lRemoteSize / 100;
				long process = 0;
				long localSize = 0L;
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
					localSize += c;
					long nowProcess = localSize / step;
					if (nowProcess > process) {
						process = nowProcess;
						// if(process % 10 == 0)
						// System.out.println("下载进度："+process);
						// TODO 更新文件下载进度,值存放在process变量中
					}
				}
				in.close();
				out.close();
				boolean upNewStatus = ftp.completePendingCommand();
				if (upNewStatus) {
					result = true;
				} else {
					result = false;
				}
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;

		}
	}

	    *//** *//*
	*//**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @return 上传的状态
	 * @throws IOException
	 *//*
	
	 * public boolean download(FTPFile remoteFile,String local) throws
	 * IOException{ //设置被动模式 ftp.enterLocalPassiveMode(); //设置以二进制方式传输
	 * ftp.setFileType(FTP.BINARY_FILE_TYPE); boolean result=false;
	 * 
	 * long lRemoteSize = remoteFile.getSize(); // String
	 * remote=remoteFile.getName(); String
	 * remote=remoteFile.getName().substring(remoteFile.getName().length()-63);
	 * 
	 * File f = new File(local); //本地存在文件，进行断点下载 if(f.exists()){ long localSize
	 * = f.length(); //判断本地文件大小是否大于远程文件大小 if(localSize >= lRemoteSize){
	 * System.out.println("本地文件大于远程文件，下载中止"); return result; }
	 * 
	 * //进行断点续传，并记录状态 FileOutputStream out = new FileOutputStream(f,true);
	 * ftp.setRestartOffset(localSize); InputStream in =
	 * ftp.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));
	 * byte[] bytes = new byte[1024]; long step = lRemoteSize /100; long
	 * process=localSize /step; int c; while((c = in.read(bytes))!= -1){
	 * out.write(bytes,0,c); localSize+=c; long nowProcess = localSize /step;
	 * if(nowProcess > process){ process = nowProcess; // if(process % 10 == 0)
	 * // System.out.println("下载进度："+process); //TODO 更新文件下载进度,值存放在process变量中 }
	 * } in.close(); out.close(); boolean isDo = ftp.completePendingCommand();
	 * if(isDo){ result =true; }else { result = false; } }else { OutputStream
	 * out = new FileOutputStream(f); InputStream in= ftp.retrieveFileStream(new
	 * String(remote.getBytes("GBK"),"iso-8859-1")); byte[] bytes = new
	 * byte[1024]; long step = lRemoteSize /100; long process=0; long localSize
	 * = 0L; int c; while((c = in.read(bytes))!= -1){ out.write(bytes, 0, c);
	 * localSize+=c; long nowProcess = localSize /step; if(nowProcess >
	 * process){ process = nowProcess; // if(process % 10 == 0) //
	 * System.out.println("下载进度："+process); //TODO 更新文件下载进度,值存放在process变量中 } }
	 * in.close(); out.close(); boolean upNewStatus =
	 * ftp.completePendingCommand(); if(upNewStatus){ result =true; }else {
	 * result = false; } } return result; }
	 
	*//**
	 * 主方法(测试)
	 * 
	 * 问题：上传时命名的新文件名不能有中文，否则上传失败.
	 * 
	 * @param args
	 *//*
	public static void main(String[] args) {
		String ftpIP = "192.168.1.115";
		int port = 21;
		String ftpUser = "mapyeah";
		String ftpPass = "12345678";
		String ftpDir = "/temp";
		String localDir = "";

		FtpHelper ftp = new FtpHelper(ftpIP, port, ftpUser, ftpPass);
		// FtpHelper ftp = new FtpHelper("localhost", 10021,"test","1");
		// FtpHelper ftp = new FtpHelper("61.4.185.77",
		// 21,"leidaupload","leidaqxj");

		try {

			ftp.connectFTPServer();

			// fu.ftp.setControlEncoding("GBK"); //设置了编码
			// fu.ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			// 转到指定下载目录
			ftp.getFtp().changeWorkingDirectory(ftpDir);
			System.out.println("Current dir: "
					+ ftp.getFtp().printWorkingDirectory());

			// 列出该目录下所有文件 ，有两种方式
			// FTPFile[] fs =ftp.getFtp().listFiles();
			// // // 遍历所有文件，找到指定的文件
			// for (FTPFile ff : fs) {
			// System.out.println("Y-> " +ff.getName());
			//
			// //下载远程文件
			// if(ftp.download(ff,localDir + "/" + ff.getName()))
			// {
			// //下载成功，删除远程文件
			// // ftp.deleteFtpServerFile(ff);
			// }
			// }

			// 方式2
			String sfiles[] = ftp.getFtp().listNames();
			for (int i = 0; i < sfiles.length; i++) { // does not enter here
				String fileName = sfiles[i];

				String localFileName = localDir + "/" + fileName;

				File f = new File(localFileName);

				if (f.exists()) {
					System.out.println("已经从远程ftp下载过 " + localFileName + ",略去。");
				} else {
					OutputStream out = new FileOutputStream(f);
					ftp.getFtp().retrieveFile(fileName, out);

					ftp.getFtp().deleteFile(fileName);
					// ftp.getFtp().deleteFile(new
					// String(fileName.getBytes("GBK"),"iso-8859-1"));
					// ftp.getFtp().sendCommand("DELE　"+fileName);

					// if(ftp.getFtp().retrieveFile(fileName,out))
					// {
					// System.out.println("下载成功-> " +fileName+" ,删除中...");
					// //删除代码有点问题，需要重新改进。
					// ftp.getFtp().deleteFile(fileName);
					// System.out.println("删除成功-> " +fileName+".");;
					// }
				}

			}
			// 直接删除整个目录吧：
			ftp.getFtp().removeDirectory(ftpDir);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("异常信息：" + e.getMessage());
		} finally {
			if (ftp.getFtp() != null) {
				try {
					ftp.closeFTPClient();
				} catch (Exception ex) {
					// DebugUtil.add(ex.getLocalizedMessage()+"\n"+ex.toString());
				}
			}
		}
	}

}
*/