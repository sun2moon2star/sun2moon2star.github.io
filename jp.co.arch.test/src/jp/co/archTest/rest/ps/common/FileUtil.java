/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20171201 FanMH(bbasoft)    G001.00.0 新規作成
 * 20180131 FanMH(bbasoft)    G002.00.0 受入課題No8（プリセット画像が取り込まれない）を対応致します.
 */
package jp.co.archTest.rest.ps.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.archTest.rest.ps.common.csv.CSVLine;
import jp.co.archTest.rest.ps.common.csv.impl.CSVLineImpl;

/**
 * ファイル操作関連ユーティリティ.
 * 
 * <P>
 * Copyright(C) 2008 TOSHIBA TEC Corporation. All Rights Reserved
 * </P>
 * 
 * @author ryu_JSE
 * @version
 */
public final class FileUtil {

	/** ファイル移動失敗. */
	public static final int FILEMOVE_FAILED = 0;
	/** ファイル移動成功. */
	public static final int FILEMOVE_SUCCESS = 1;
	/** ファイル移動既存ファイル上書き. */
	public static final int FILEMOVE_OVERWRITE = 2;

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * デフォルトコンストラクタ.
	 */
	private FileUtil() {
	}

	/**
	 * ファイル移動.
	 * <p>
	 * 指定されたファイルと同名のファイルが移動先に存在している場合は上書きする.<br>
	 * 引数の移動元、移動先ディレクトリパスは、最後に"\"まで指定すること.
	 * </p>
	 * 
	 * @param fromDir
	 *            移動元ディレクトリパス
	 * @param toDir
	 *            移動先ディレクトリパス
	 * @param fileName
	 *            移動ファイル名
	 * @return 移動結果
	 * @throws Exception
	 *             業務例外・システム例外含めFacadeに処理をさせる
	 */
	public static int fileMove(final String fromDir, final String toDir, final String fileName) throws Exception {

		int result = FILEMOVE_FAILED;
		File fromFile = new File(fromDir + fileName);
		File toFile = new File(toDir + fileName);

		// 移動先に指定されたファイルがあるか確認する
		if (!toFile.exists()) {
			result = FILEMOVE_SUCCESS;
		} else {
			// 移動先にファイルが存在しているため上書き処理を行う
			fileDelete(toDir + fileName);
			result = FILEMOVE_OVERWRITE;
		}
		// ファイル移動実行
		try {
			fromFile.renameTo(toFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ファイル内容取得.
	 * 
	 * @param fileFullPath
	 *            ファイル名のフルパス
	 * @return 1行ごとに読み取ったファイル内容の配列
	 * @throws IOException
	 *             ファイル読み取りエラー
	 */
	public static String[] fileRead(final String fileFullPath) throws IOException {

		FileInputStream fi = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		List<String> dataLineList = new ArrayList<String>();

		try {
			File file = new File(fileFullPath);
			fi = new FileInputStream(file);

			// Reader生成（Encode指定Shift_JIS）
			ir = new InputStreamReader(fi, "Shift_JIS");
			br = new BufferedReader(ir);

			// ファイルを1行ごと読み込む
			String line = "";
			while ((line = br.readLine()) != null) {
				dataLineList.add(line);
				Thread.yield();
			}

		} finally {
			fi.close();
			ir.close();
			br.close();
		}
		return (String[]) dataLineList.toArray(new String[0]);
	}

	/**
	 * タグデータ抽出.
	 * <p>
	 * タグ付き文字列を解析しタグ内容のみを取得する.
	 * </p>
	 * 
	 * @param startTag
	 *            開始タグ
	 * @param endTag
	 *            終了タグ
	 * @param line
	 *            行文字列
	 * @return タグ内容
	 */
	public static String tagDataExtract(final String startTag, final String endTag, final String line) {

		String data = null;
		// 正規表現パターンを生成（開始タグもしくは終了タグを含むこと）
		StringBuffer regExpStr = new StringBuffer();
		regExpStr.append(startTag);
		regExpStr.append("|");
		regExpStr.append(endTag);
		Pattern pattern = Pattern.compile(regExpStr.toString());
		Matcher matcher = pattern.matcher(line);
		// タグを外す
		data = matcher.replaceAll("");
		return data;
	}

	/**
	 * 渡されたファイル名のファイルをオープンします。.
	 * 
	 * @param fileFullPath
	 *            ファイル名のフルパス
	 * @return BufferedReaderオブジェクト
	 * @throws FileNotFoundException
	 *             ファイルの存在しない例外
	 */
	public static BufferedReader openBufferedReader(String fileFullPath) throws FileNotFoundException {
		FileInputStream fileStream = new FileInputStream(fileFullPath);
		InputStreamReader inputStream = new InputStreamReader(fileStream);
		BufferedReader br = new BufferedReader(inputStream);
		return br;
	}

	/**
	 * 渡されたBufferedReaderをクローズします。.
	 * 
	 * @param br
	 *            br
	 * @throws IOException
	 *             IO例外
	 */
	public static void closeBufferedReader(BufferedReader br) throws IOException {

		if (br != null) {
			br.close();
		}
	}

	/**
	 * 渡されたファイル名のBufferedWriterオブジェクトを生成します.
	 * 
	 * @param fileFullPath
	 *            ファイル名のフルパス
	 * @param append
	 *            true の場合、バイトはファイルの先頭ではなく最後に書き込まれる
	 * @return BufferedWriter オブジェクト
	 * @throws FileNotFoundException
	 *             ファイルの存在しない場合の例外
	 */
	public static BufferedWriter openBufferedWriter(String fileFullPath, boolean append) throws FileNotFoundException {
		FileOutputStream fileStream = new FileOutputStream(fileFullPath, append);
		OutputStreamWriter outputStream = new OutputStreamWriter(fileStream);
		BufferedWriter bw = new BufferedWriter(outputStream);
		return bw;
	}

	/**
	 * 渡されたBufferedWriterをクローズします。.
	 * 
	 * @param bw
	 *            writer
	 * @throws IOException
	 *             IO例外
	 */
	public static void closeBufferedWriter(BufferedWriter bw) throws IOException {
		if (bw != null) {
			bw.close();
		}
	}

	/**
	 * 明細データリストのＣＳＶ変換.<BR>
	 * 帳票データ明細リストの内容をＣＳＶ形式に変換し、ファイル出力する。
	 * 
	 * @param detailList
	 *            明細データリスト
	 * @param csvName
	 *            CSVファイル名（相対パスつき）
	 * @return CSVファイルのFileオブジェクト
	 * @throws Exception
	 *             業務例外・システム例外含めFacadeに処理をさせる
	 */
	public static File csvConvert(List<List<String>> detailList, String csvName) throws Exception {

		if (csvName == null) {
			throw new Exception();
		}

		CSVLine csvLine = new CSVLineImpl();
		File csvFile = new File(csvName);

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(csvFile));
			for (int i = 0; i < detailList.size(); i++) {
				for (String item : detailList.get(i)) {
					csvLine.addItem(item);
				}
				bw.write(csvLine.getLine());
				bw.newLine();
				Thread.yield();
			}
			bw.flush();
		} finally {
			closeBufferedWriter(bw);
		}
		return csvFile;
	}

	/**
	 * ファイル削除.<BR>
	 * 指定パスのファイルを削除する。
	 * 
	 * @param fileName
	 *            明細データリスト
	 * @return ファイル削除結果
	 * @throws Exception
	 *             業務例外・システム例外含めFacadeに処理をさせる
	 */
	public static boolean fileDelete(String fileName) throws Exception {

		if (fileName == null) {
			throw new Exception();
		}

		File file = new File(fileName);
		boolean delfile = file.delete();
		return delfile;
	}

	/**
	 * 対象ディレクトリのファイル一覧を取得します
	 * 
	 * @param path
	 *            ファイルパス
	 * @return false：ディレクトリが存在しない。 true：ディレクトリが存在する。
	 */
	public static File[] getFileList(String path, String endFilterName) {
		File dir = new File(path);
		return dir.listFiles(getFileExtensionFilter(endFilterName));
	}

	/**
	 * フィルターオブジェクトの取得
	 * 
	 * @param extension
	 * @return
	 */
	public static FilenameFilter getFileExtensionFilter(String extension) {
		final String _extension = extension;
		return new FilenameFilter() {
			public boolean accept(File file, String name) {
				boolean ret = name.endsWith(_extension) || _extension.equals("*");
				return ret;
			}
		};
	}

	// G002.00.0  Maintenance-Start 受入課題No8（プリセット画像が取り込まれない）を対応致します.
	/**
	 * フォルダコピー
	 * 
	 * @param src
	 *            元フォルダ
	 * @param des
	 *            目標フォルダ
	 */
	public static void folderCopy(String src, String des, String fiter) {
		File file1 = new File(src);
		if (!file1.exists()) {
			logger.error("フォルダ「" + src + "」が存在しないです。");
			return;
		}
		File[] fs = file1.listFiles();
		if (fs == null || fs.length <= 0) {
			logger.error("フォルダ「" + src + "」中にファイル存在しません。");
			return;
		}

		File file2 = new File(des);
		if (!file2.exists()) {
			try {
				file2.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (fiter == null || fiter.equals("*") || fiter.equals("*.*")) {
			for (File f : fs) {
				if (f.isFile()) {
					fileCopy(f.getPath(), des + "\\" + f.getName());
				} else if (f.isDirectory()) {
					folderCopy(f.getPath(), des + "\\" + f.getName(), fiter);
				}
			}
		} else {
			for (File f : fs) {
				if (f.isFile()) {
					String[] arr = fiter.split(",");
					for (String temp : arr) {
						if (f.getName().toLowerCase().endsWith(temp)) {
							fileCopy(f.getPath(), des + "\\" + f.getName());
							break;
						}
					}
				} else if (f.isDirectory()) {
					folderCopy(f.getPath(), des + "\\" + f.getName(), fiter);
				}
			}
		}
	}
	// G002.00.0 Maintenance-End

	/**
	 * ファイルコピー
	 * 
	 * @param src
	 *            元ファイル
	 * @param des
	 *            目標ファイル
	 */
	public static void fileCopy(String src, String des) {
		logger.info("ファイル「" + src + "」を" + des +"へコピーします");
		File source = new File(src);
		File dest = new File(des);
		try {
			if (dest.exists()) {
				try {
					dest.delete();
				} catch (Exception e){
					
				}
			}
		    Files.copy(source.toPath(), dest.toPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		BufferedReader br = null;
//		PrintStream ps = null;
//
//		try {
//			br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
//			ps = new PrintStream(new FileOutputStream(des));
//			String s = null;
//			while ((s = br.readLine()) != null) {
//				ps.println(s);
//				ps.flush();
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (br != null) {
//					br.close();
//				}
//				if (ps != null) {
//					ps.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
	}

	/**
	 * フォルダ削除
	 * 
	 * @param path
	 *            削除元フォルダ
	 */
	public static void folderDel(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					folderDel(path + "/" + tmp[i].getName());
				} else {
					tmp[i].delete();
				}
			}
			dir.delete();
		}
	}
}
