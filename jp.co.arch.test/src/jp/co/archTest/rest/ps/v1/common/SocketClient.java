/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20180308 FanMH(bbasoft)    G000.00.0 仮想POS通信改善
 * 20180316 FanMH(bbasoft)    G001.00.0 Socket通信でタイムアウト例外が発生すると、セマフォのreleaseが実行されず永久に排他になります。
 * 20180316 Kobayashi(TEC)    G002.00.0 Socket通信でタイムアウトを60秒に変更。
 * 20180711 SUNwc(bbasoft)    G003.00.0 標準版対応、Socket通信でバッファサイズを変更。
 */
package jp.co.archTest.rest.ps.v1.common;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//プレゼンタへのソケット接続を行います
public class SocketClient {
	private static Logger logger = LoggerFactory.getLogger(SocketClient.class);
	// G003.00.0 Mainte-Start 標準版対応、Socket通信でバッファサイズを変更。
	private final int BUFFERSIZE = 1024 * 1024 * 3;
	// G003.00.0 Mainte-End 標準版対応、Socket通信でバッファサイズを変更。
	private String ipAddress;
	private int port;
	private Socket socket;

	/**
	 * コンストラクタ
	 * 
	 * @param ipAddress
	 * @param port
	 */
	public SocketClient(String ipAddress, int port) {
		this.ipAddress = ipAddress;
		this.port = port;
	}

	public String SendAndRecieve(String strMessage) throws Exception {
		// G001.00.0 Mainte-Start Socket通信でタイムアウト例外が発生すると、セマフォのreleaseが実行されず永久に排他になります。
		// G000.00.0 Mainte-Start 仮想POS通信改善
		SocketSemaphore.acquire(this.ipAddress + ":" + this.port);
		String strRet = "";
		try {
		    strRet = this.sendRequest(strMessage);
		} catch (Exception e) {
			throw e;
		} finally {
			SocketSemaphore.release(this.ipAddress + ":" + this.port);
		}
		// G000.00.0 Mainte-End 
		// G001.00.0 Mainte-End
		return strRet;
	}

	/**
	 * POSにリクエストを送信します（MTITプロト用）
	 * 
	 * @param clientIp
	 * @param message
	 * @throws Exception
	 */
	private String sendRequest(String strMessage) throws Exception {
		String messageString = "";
		// レスポンス受信
		InputStream is = null;
		BufferedInputStream inFromClient = null;
		DataOutputStream dos = null;
		OutputStream os = null;
		try {
			// so
			// 文字列をバイト列に変換し、電文生成
			byte[] message = strMessage.getBytes(Charset.forName("Shift_JIS"));

			// プレゼンタに接続
			socket = new Socket(ipAddress, port);

			// 出力ストリーム
			os = (OutputStream) socket.getOutputStream();
			dos = new DataOutputStream(os);

			// 入力ストリーム
			is = (InputStream) socket.getInputStream();
			inFromClient = new BufferedInputStream(is);

			// プレゼンタにコマンドを送信
			dos.write(message);
			dos.flush();

			// G001.00.0 Mainte-Start タイムアウトを60秒に修正
			socket.setSoTimeout(60 * 1000);
			ByteBuffer bf = ByteBuffer.allocate(BUFFERSIZE);
			// G001.00.0 Mainte-End タイムアウトを60秒に修正
			while (true) {
				int b = inFromClient.read();
				if (b == -1) {
					break;
				}
				bf.put((byte) b);
			}

			// バッファからbyte列へ
			bf.flip();
			byte[] messageByte = new byte[bf.limit()];

			for (int i = 0; i < bf.limit(); i++) {
				messageByte[i] = bf.get();
			}

			// パラメータをデコード
			// javaでwindowsのshift-jisをエンコードの際、変換が正常にできない、MS932に修正します
			// 例えば「－」、「～」
			messageString = new String(messageByte, "MS932");

		} catch (SocketTimeoutException e) {
			logger.error("受信タイムアウト。", e);
			throw e;
		} catch (IOException e) {
			logger.error("受信エラーが発生しました。", e);
			throw e;
		} finally {
			// ストリームを閉じる
			close(dos);
			close(os);

			close(is);
			close(inFromClient);
			this.close();
		}

		return messageString;
	}

	private void close(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close(OutputStream os) {
		try {
			if (os != null) {
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
