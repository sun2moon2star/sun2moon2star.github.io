package jp.co.archTest.rest.ps.v1.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

//http://etc9.hatenablog.com/entry/20100204/1265528529から丸パクリしたクラス
public class SocketServer extends Thread {

    private static final int DEFAULT_PORT = 8000;
    private ServerSocket server;

    public SocketServer() throws IOException {
        this(DEFAULT_PORT);
    }
    public SocketServer(int port) throws IOException {
        this.server = new ServerSocket();
        this.server.setReuseAddress(true);
        this.server.bind(new InetSocketAddress(port));
    }

    public void start() {
        new Thread() {
            @Override public void run() {
                while(true) {
                    try {
                        //プレゼンタ接続待ち(ブロック)
                    	System.out.println("▼accept");
                    	Socket socket = server.accept(); // block
                    	System.out.println("▲accept");
                    	
                    	//ソケットを管理テーブルに追加
                    	
                    	//プレゼンタとのコネクション初期処理
                    	
                    	
                    	//初期処理に失敗したらソケットを破棄
                    	
                        new Thread(new ConnectionHandler(socket)).start();
                    } catch (SocketException e) {
                        if (e.getMessage().equalsIgnoreCase("Socket closed")) {
                            System.out.println("Socket closed");
                            break;
                        }
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        System.out.println("ソケットサーバスタート");
    }

    class ConnectionHandler implements Runnable {
        private final Socket socket;
        public ConnectionHandler(final Socket socket) {
            this.socket = socket;
        }

        @Override public void run() {
            System.out.println("accepted " + socket.getInetAddress()
                    + ":" + socket.getPort());
            try {
                socket.setTcpNoDelay(true);
            } catch (SocketException e) {
                // do nothing
            }
            try {
                handleConversation();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        void handleConversation() throws IOException {
            InputStream in = new BufferedInputStream(socket.getInputStream());
            OutputStream out = new BufferedOutputStream(socket.getOutputStream());
            byte[] buffer = new byte[8192];
            int count;
            while((count = in.read(buffer)) >= 0) {
                System.out.println("received " + count
                        + " bytes from " + socket.getInetAddress()
                        + ":" + socket.getPort());
                System.out.println(new String(buffer).trim());
                out.write(buffer, 0, count);
                out.flush();
            }
            socket.shutdownOutput(); // half close
        }
    }
}
