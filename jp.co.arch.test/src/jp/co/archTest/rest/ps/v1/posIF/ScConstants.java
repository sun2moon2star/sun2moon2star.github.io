/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20080730 R.Furuya(TJ)      G001.00.0 新規作成
 * 20081221 K.Maikuma(TJ)     G002.00.0 M-M構成対応
 * 20090324 Y.Miyoshi(TJ)     G002.01.0 操作ログ対応
 * 20090528 T.Sawada(TJ)      G002.02.0 未実行ジョブ起動時刻変更対応
 * 20090704 J.Ochiai(TJ)      G002.03.0 同期型AddJob追加対応
 */
package jp.co.archTest.rest.ps.v1.posIF;

/**
 * SC内で共有する定数を宣言した定数クラスです.<br>
 *
 * <P>
 * Copyright(C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 *
 * @author R.Furuya_TJ
 */
public final class ScConstants { // parasoft-suppress METRICS.NOF "定数定義のため問題なし"

    /** 改行コード. */
    public static final String CRLF = "\r\n";

    /** HTTPクライアント設定ファイルのプレフィックス. */
    public static final String HTTP_CLIENT_CONF = "resources/rocky/base/sc/SC";

    /** HTTPクライアント設定ファイル. */
    public static final String HTTP_CLIENT_CONF_FILE
        = "resources/rocky/base/sc/SC_http.xml";

    /** 処理結果：正常. */
    public static final String RESULT_NORMAL = "0";

    /** 処理結果：異常. */
    public static final String RESULT_ABNORMAL = "1";

    /** 処理結果：要求が既に存在. */
    public static final String RESULT_ALLREADY_EXISTS = "2";

    /** サーバーとの通信結果がOKの際にクライアントに返すステータスです. */
    public static final String ACK = "A";

    /** 文字コード：シフトJIS. */
    public static final String SJIS = "Shift_JIS";

    /** リテラル定義""（空文字）. */
    public static final String EMPTY_STRING = "";

    /** リテラル定義"=". */
    public static final String EQUAL = "=";

    /** リテラル定義"hostname". */
    public static final String HOSTNAME = "hostname";

    /** リテラル定義"HOST". */
    public static final String HOST = "HOST";

    /** リテラル定義"timeout". */
    public static final String TIMEOUT = "timeout";

    /** リテラル定義"METHOD". */
    public static final String METHOD = "METHOD";

    /** リテラル定義"RESULT". */
    public static final String RESULT = "RESULT";

    /** リテラル定義"MESSAGE". */
    public static final String MESSAGE = "MESSAGE";

    /** 業務アプリケーション名（スケジューラ）. */
    public static final String APPID = "SC";

    /** 業務アプリケーション名（スケジューラ－マネージャ）. */
    public static final String APPID_MGR = "SCMGR";

    /** 業務アプリケーション名（スケジューラ－エージェント）. */
    public static final String APPID_AGT = "SCAGT";

    // G002.01.0 Add-Start 操作ログ対応
    /** 操作ログで出力する操作機器：サーバー. */
    public static final String APPARATUS_SVR = "SVR";
    // G002.01.0 Add-End

    /** 共通部材　プラグインID　ログ. */
    public static final String ID_COM_LOG = "Log";

    /** 共通部材　プラグインID　HTTPクライアント. */
    public static final String ID_COM_HTTP_CLIENT = "HttpClient";

    /** 共通部材　プラグインID　ＸＭＬファイル変換. */
    public static final String ID_COM_XML = "xmlLoader";

    /** 共通部材　プラグインID　ＣＳＶ変換. */
    public static final String ID_COM_CSV = "csvLine";

    /** 共通部材　プラグインID　プロパティファイル. */
    public static final String ID_COM_PROPERTY = "Property";

    /** スケジューラ　プラグインID　サーバ管理DAO. */
    public static final String ID_SC_CONTROLLER = "sc.ScController";

    /** スケジューラ　プラグインID　外部APIDAO. */
    public static final String ID_SC_MNG_CLNT_DAO = "sc.ManagerClientDao";

    /** スケジューラ　プラグインID　ユーザトランザクション. */
    public static final String ID_USER_TRANSACTION = "UserTransaction";

    /** スケジューラ　プラグインID　システム排他制御. */
    public static final String ID_SC_EX_SCHEDULER = "sc.ExScheduler";

    /** スケジューラ　プラグインID　店舗スケジュール排他制御. */
    public static final String ID_SC_EX_STORE_SCDL = "sc.ExStoreSchedule";

    /** スケジューラ　プラグインID　店舗リクエスト排他制御. */
    public static final String ID_SC_EX_STORE_REQUEST = "sc.ExStoreRequest";
    /** スケジューラ　プラグインID　ステップ実行メソッド呼出. */
    public static final String ID_SC_STEP_EXEC_CALLER
        = "sc.StepExecutionCallerImpl";
    /** スケジューラ　プラグインID　HTTP要求管理. */
    public static final String ID_SC_HTTP_CONN_REQ_DAO
        = "sc.HttpConnReqDao";

    /**************************************************************************
     * ログメッセージ
     **************************************************************************/

    /** デバッグログ接頭語（*SQL*）. */
    public static final String DEBUG_PREFIX_SQL = "*SQL*";

    /** デバッグログ接頭語（*送信*）. */
    public static final String DEBUG_PREFIX_SEND = "*送信*";

    /** デバッグログ接頭語（*受信*）. */
    public static final String DEBUG_PREFIX_RECEIVE = "*受信*";

    /** デバッグログ接頭語（*外部I/F*）. */
    public static final String DEBUG_PREFIX_PUBIF = "*外部I/F*";

    /** エラーメッセージ：指定できない引数が指定されました. */
    public static final String MSG_ARG_ERR = "指定できない引数が指定されました。";

    /** エラーメッセージ：DBアクセス時にエラーが発生しました. */
    public static final String MSG_DB_ERR = "DBアクセス時にエラーが発生しました。";

    /** エラーメッセージ：初期設定値に不備があります. */
    public static final String MSG_INIT_DATA_ERR = "初期設定値に不備があります。";

    /** エラーメッセージ：スケジューラが起動していません. */
    public static final String MSG_NOT_INIT_SC = "スケジューラが起動していません。";

    /** エラーメッセージ：起動したJOBの実行待ちでタイムアウトが発生しました。. */
    public static final String MSG_ADD_JOB_TIMEOUT = "起動したJOBの実行待ちでタイムアウトが発生しました。";

    /**************************************************************************
     * 制御実行パラメータキー
     **************************************************************************/

    /** 種別（保留・スキップ：true／解除：false）を保持するMapのキー. */
    public static final String MODE = "Mode";

    /** 起動時刻を保持するMapのキー. */
    public static final String START_TIME = "StartTime";

    /** ホストを保持するMapのキー. */
    public static final String HOST_NAME = "HostName";

    /** 状態を保持するMapのキー. */
    public static final String STATUS = "Status";

    /** ステップハンドルを保持するMapのキー. */
    public static final String STEP_HANDLE = "StepHandle";

    /** ステップ実行結果を保持するMapのキー. */
    public static final String STEP_RESULT = "StepResult";

    /** 店舗コードを保持するMapのキー. */
    public static final String STORE_CD = "StoreCd";

    /** 要求番号を保持するMapのキー. */
    public static final String REQUEST_NO = "RequestNo";

    /** 運用日を保持するMapのキー. */
    public static final String RUNNING_DATE = "RunningDate";

    /** ジョブIDを保持するMapのキー. */
    public static final String JOB_ID = "JobId";

    /** JOB引数を保持するMapのキー. */
    public static final String ARGUMENT = "Argument";

    /** タスクハンドルを保持するMapのキー. */
    public static final String TASK_HANDLE = "TaskHandle";

    /** JOBの階層情報を保持するMapのキー. */
    public static final String STRATUM = "Stratum";

    /** ステップIDを保持するMapのキー. */
    public static final String STEP_ID = "StepId";

    // G002.00.0 Add-Start アプリケーションログ対応
    /** ステップ名を保持するMapのキー. */
    public static final String STEP_NAME = "StepName";

    /** ステップの終了ステータスを保持するMapのキー. */
    public static final String EXEC_RESULT_STS = "ExecResultSts";

    /** ディスパッチ区分を保持するMapのキー. */
    public static final String DISPATCH_DIV = "DispatchDiv";
    // G002.00.0 Add-End

    /** ステップタイプを保持するMapのキー. */
    public static final String STEP_TYPE = "StepType";

    /** 監視区分を保持するMapのキー. */
    public static final String MONITOR_DIV = "MonitorDiv";

    /** ハンドルを保持するMapのキー. */
    public static final String HANDLE = "Handle";

    // G002.00.0 Add-Start アプリケーションログ対応
    /** HTTP通信要求通番号を保持するMapのキー. */
    public static final String HTTP_REQUEST_NO = "HttpRequestNo";
    // G002.00.0 Add-End

    /**
     * 起動区分をプロパティ設定する場合のキー値です.
     *
     * <P>
     * ジョブ登録リクエストを発行する場合、次のいずれかをプロパティ設定して下さい。<br>
     * 未設定の場合は
     * {@link Valuator#SCHEDULE_MANUAL}として処理されます。
     *
     * <pre>
     * {@link Valuator#SCHEDULE_AUTO}
     * {@link Valuator#SCHEDULE_MANUAL}
     * </pre>
     * </P>
     */
    public static final String STARTUP_DIV = "StartUpDiv";

    /**
     * タスクパス内容をプロパティ設定する場合のキー値です.
     *
     * <P>
     * タスクパスリクエストを発行する場合、次のいずれかをプロパティ設定する必要があります。
     * <pre>
     * {@link Valuator#ACTION_SKIP}
     * {@link Valuator#ACTION_STOP}
     * </pre>
     * </P>
     */
    public static final String TASKPASS_DIV = "TaskPassDiv";

    /**
     * 同期対象のエージェントのサーバ一覧をプロパティ設定する場合のキー値です.
     *
     * <P>
     * 死活状態が変化したエージェントのサーバ名を「:」区切りで設定します。
     * </P>
     */
    public static final String SYNC_AGENTS = "SyncAgents";

    /**
     * タスクの実行結果をプロパティ設定する場合のキー値です.
     *
     * <P>
     * 次のいずれかをプロパティ設定する必要があります。
     * <pre>
     * {@link Valuator#RESULT_NORMAL}
     * {@link Valuator#RESULT_WARNING}
     * {@link Valuator#RESULT_ERROR}
     * </pre>
     * </P>
     */
    public static final String EXEC_RESULT = "ExecResult";

    /**
     * ステップ再起エージェントリストをプロパティ設定する場合のキー値です.
     *
     * @see rocky.base.sc.manager.scheduler.wa.StepSynchronizer
     */
    public static final String SYNC_SERVER_LIST = "SyncServers";


    /**************************************************************************
     * 終了結果ステータス
     **************************************************************************/
    /** 正常の場合の終了ステータス. */
    public static final String END_STATUS_NORMAL = "0000";
    /** 警告の場合の終了ステータス. */
    public static final String END_STATUS_WARNING = "0010";
    /** 異常の場合の終了ステータス(プロセス実行エラー). */
    public static final String END_STATUS_ERROR_PS = "9900";
    /** 異常の場合の終了ステータス. */
    public static final String END_STATUS_ERROR = "9999";


    // G002.00.0 Add-Start
    /**************************************************************************
     * 起動時刻変更結果ステータス
     **************************************************************************/
    /** 起動時刻変更に成功. */
    public static final int MOD_ST_TIME_SUCCESS = 0;
    /** ジョブが既に実行中のため、起動時刻の変更は不可. */
    public static final int MOD_ST_TIME_FAIL_ALREADY_EXECUTING = 1;
    /** ジョブの実行が間近に迫っているため、起動時刻の変更は不可. */
    public static final int MOD_ST_TIME_FAIL_SOON_EXECUTE = 2;
    // G002.00.0 Add-End


    /** 自動再起動フラグ：OFFを表す定数.*/
    public static final String AUTO_RESTART_OFF = "0";
    /** 実行制御シャットダウン時のスリープ時間. */
    public static final int SLEEP_TIME = 1000;


    /**
     * コンストラクタ.
     */
    private ScConstants() {
        //
    }
}
