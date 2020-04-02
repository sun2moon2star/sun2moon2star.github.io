/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * YYYYMMDD X.Xxxxxxxx(xxx)   G999.99.9 新規作成
 */
package rocky.base.common.transfer.impl;

/**
 * 通信機能における例外に関するエラーメッセージを定義するクラスです。.
 * <P>
 * E_で始まるものはエラー、W_で始まるものはワーニングに該当するコード番号です。
 * </P>
 */
public class MsgAgainstException { // parasoft-suppress METRICS.NOF "問題がない為"
    /**
     * TECLANヘッダのMD指示部の入力値異状。.
     * 
     */
    public static final String E_MD = "0002";


    /**
     * TECLANヘッダの通番指示部の入力値異状。.
     * 
     */
    public static final String E_SEQ_NO = "0003";


    /**
     * 応答データ受信後のread処理の結果の異状。.
     * 
     */
    public static final String E_READ_RESILT = "0004";


    /**
     * ソケットタイムアウト例外。.
     * 
     */
    public static final String E_SOCKET_TIMEOUT = "0005";


    /**
     * 外部XMLファイル読み込み時例外。.
     * 
     */
    public static final String E_READ = "0006";


    /**
     * ソケットの接続が確立していない異状。.
     * 
     */
    public static final String E_SOCKET_CONNECTION = "0007";


    /**
     * 外部定義ファイル中に指定されたユーザＩＤが存在しない異状。.
     * 
     */
    public static final String E_NOID_MATCHED = "0008";


    /**
     * 外部定義ファイルの構文にエラーが存在。.
     * 
     */
    public static final String E_XML_PARSE = "0009";


    /**
     * 外部定義ファイルが存在しない異状。.
     * 
     */
    public static final String E_XML_NOT_FOUND = "0010";


    /**
     * サーバーが起動していない、あるいはネットワーク上に障害が発生している異状。.
     * 
     */
    public static final String E_NETWORK = "0011";


    /**
     * 外部XMLファイルが正しく指定されていない異状。.
     * 
     */
    public static final String E_XML_NOT_POINTED = "0012";


    /**
     * 外部XMLファイル用ユーザＩＤが正しく指定されていない異状。.
     * 
     */
    public static final String E_USERID_NOT_POINTED = "0013";


    /**
     * TECLANヘッダ指定のバイトサイズ超過異状。.
     * 
     */
    public static final String E_LANHEADER_OUT_OF_BOUNDS = "0014";


    /**
     * 指定リモートホストへのルート確立失敗。.
     * 
     */
    public static final String E_NO_ROUTE = "0015";


    /**
     * 指定ホスト名の解決失敗。.
     * 
     */
    public static final String E_UNKNOWN_HOST = "0016";


    /**
     * 接続要求の拒絶。.
     * 
     */
    public static final String E_CONNECT_REFUSED = "0017";


    /**
     * サポートされていない文字エンコーディング。.
     */
    public static final String E_ENCODING = "0018";


    /**
     * ソケット、ストリームのオープン・クローズの異状。.
     */
    public static final String E_STREAM = "0019";


    /**
     * 接続情報の取得異状。.
     * 
     */
    public static final String E_CONNECT_INFO = "0020";


    /**
     * 変数の型の変換異状。.
     * 
     */
    public static final String E_NUMBER_FORMAT = "0021";


    /**
     * 入出力・通信処理異状。.
     * 
     */
    public static final String E_IO = "0022";


    /**
     * TECLANヘッダの各項目読み込み異状。.
     * 
     */
    public static final String E_READ_LANHEADER = "0023";


    /**
     * 外部XMLファイルにおける、指定されたノードリストが見つからない異状。.
     * 
     */
    public static final String E_XML_NODELIST = "0024";


    // HTTP通信時の例外(0101～)
    /**
     * HTTP通信結果異状。.
     */
    public static final String E_HTTP_TRANSFER = "0101";


    // RuntimeException系(9001～)
    /**
     * メソッドに指定した引数の形式異状。.
     * 
     */
    public static final String E_ARGUMENT = "9001";


    /**
     * 実行時例外。.
     * 
     */
    public static final String E_RUNTIME = "9999";



    /**
     * デフォルトコンストラクタ。.
     */
    public MsgAgainstException() {

    }
}