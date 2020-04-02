package jp.co.archTest.rest.ps.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowsCommand {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static List<String> execCommand(String[] command, int timeout) {

		logger.debug("コマンド \"" + String.join(" ", command) + "\"を実行します");

		List<String> result = new ArrayList<>();

		// プロセスの準備
		ProcessBuilder pb = new ProcessBuilder(command);
		//pb.redirectErrorStream(true);

		BufferedReader br = null;

		try {
			// プロセス実行
			Process p = pb.start();

			br = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = null;
			while((line = br.readLine()) != null) {
				logger.debug( "->" + line);
				result.add(line);
			}

			// プロセス終了待機
			p.waitFor(timeout,TimeUnit.MILLISECONDS);

			logger.debug("コマンド終了");

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				br.close();
			}
			catch (IOException ignore) {

			}

		}

		return result;
	}


	public static String[] execPowerShellScript(String filePath, int timeout) {

		logger.debug("PowerShellスクリプト" + filePath + "を実行します");

		// スクリプトファイル存在チェック
		File file = new File(filePath);
		if(!file.exists()) {
			logger.debug("ファイルが存在しない");
			return null;
		}

		// プロセスの準備
		ProcessBuilder pb = new ProcessBuilder(new String[]{"powershell", "-Command", filePath});
		pb.redirectErrorStream(true);

		BufferedReader br = null;

		try {
			// プロセス実行
			Process p = pb.start();
			// プロセスへの出力を最初に閉じておかないとプロセスが終了しない
			p.getOutputStream().close();

			br = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = null;
			while((line = br.readLine()) != null) {
				logger.debug( "->" + line);
			}

			// プロセス終了待機
			// boolean r = p.waitFor(timeout,TimeUnit.MILLISECONDS);

		}catch(Exception ex) {

		}
		finally {
			try {
				br.close();
			}
			catch (IOException ignore) {
			}

		}

		return null;
	}
}
