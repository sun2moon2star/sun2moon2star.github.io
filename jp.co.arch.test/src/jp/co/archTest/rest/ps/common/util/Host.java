/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20090414 N.Komori(TIS)     G001.00.0 新規作成
 * 20090630 N.Komori(TIS)     G001.01.0 OS種別とアーキテクチャデータモデルの取得を追加
 * 20120718 N.Komori(TIS)     G001.02.0 Windows2008か否かの判断を追加
 *                            G001.02.1 ログを出力するように修正
 * 20131129 N.Komori(TIS)     G001.03.0 OS判断にWin2012/2012R2/8/8.1を追加
 *                            G001.03.1 ネットワーク系の情報取得をHostNetworkクラスに分離
 * 20150316 N.Komori(TIS)     G001.04.0 OS判断にWin2012/2012R2/8/8.1/10を追加
 */
package jp.co.archTest.rest.ps.common.util;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ホストクラス.
 * <P>
 * ホストの情報を提供します。
 * </P>
 * <P>
 * 以下の情報を提供します。
 * <OL type="1" start="1">
 * <LI>ホスト名</LI>
 * <LI>IPアドレス</LI>
 * <LI>OS種別</LI>
 * <LI>アーキテクチャデータモデル</LI>
 * </OL>
 * </P>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * @author N.Komori(TIS)
 */
public final class Host {

    /** デフォルトホスト名. */
    private static final String DEFAULT_HOST_NAME = "localhost";

    /** OS名:Windows 9xファミリ. */
    private static final String OS_NAME_WINDOWS_9X = "Windows 9";
    /** OS名:Windows NTファミリ. */
    private static final String OS_NAME_WINDOWS_NT = "Windows NT";
    /** OS名:Windows Server ファミリ. */
    private static final String OS_NAME_WINDOWS_SV = "Windows Server";  //Windows Server 2008以降
    /** OS名:Windows ファミリ. */
    private static final String OS_NAME_WINDOWS = "Windows";
    /** OS名:AIX. */
    private static final String OS_NAME_AIX = "AIX";
    /** OS名:HP-UX. */
    private static final String OS_NAME_HP_UX = "HP-UX";
    /** OS名:Irix. */
    private static final String OS_NAME_IRIX = "Irix";
    /** OS名:Linux. */
    private static final String OS_NAME_LINUX = "Linux";
    /** OS名:LINUX. */
    private static final String OS_NAME_LINUX_UPPER = "LINUX";
    /** OS名:Mac. */
    private static final String OS_NAME_MAC = "Mac";
    /** OS名:MAC_OSX. */
    private static final String OS_NAME_MAC_OS_X = "Mac OS X";
    /** OS名:OS2. */
    private static final String OS_NAME_OS2 = "OS/2";
    /** OS名:Solaris. */
    private static final String OS_NAME_SOLARIS = "Solaris";
    /** OS名:SunOS. */
    private static final String OS_NAME_SUN_OS = "SunOS";

    /** OSバージョン:Windows 95. */
    private static final String OS_VERSION_WIN_95 = "4.0";
    /** OSバージョン:Windows 98. */
    private static final String OS_VERSION_WIN_98 = "4.1";
    /** OSバージョン:Windows Me. */
    private static final String OS_VERSION_WIN_ME = "4.9";
    /** OSバージョン:Windows 2000. */
    private static final String OS_VERSION_WIN_2K = "5.0";
    /** OSバージョン:Windows XP. */
    private static final String OS_VERSION_WIN_XP = "5.1";
    /** OSバージョン:Windows 2003. */
    private static final String OS_VERSION_WIN_2K3 = "5.2";
    /** OSバージョン:Windows Vista / Windows 2008系. */
    private static final String OS_VERSION_WIN_VISTA = "6.0";
    /** OSバージョン:Windows 7. */
    private static final String OS_VERSION_WIN_7 = "6.1";
    //IBM VMは「6.2 build 9200」とビルド番号まで返すが、Oracle VMは「6.2」とバージョンしか返さないのでバージョンのみで比較
    /** OSバージョン:Windows 8系 / Windows 2012系. */
    private static final String OS_VERSION_WIN_8_2012 = "6.2";
    /** OSバージョン:Windows 8.1系 / Windows 2012R2系. */
    private static final String OS_VERSION_WIN_81_2012R2 = "6.3";
    /** OSバージョン:Windows 10. */
    private static final String OS_VERSION_WIN_10 = "10.0";

    /** OSチェックパラメータ:OS種別. */
    private static final String PARAM_KEY_OS_DIV = "os";
    /** OSチェックパラメータ:OS名. */
    private static final String PARAM_KEY_OS_NAME = "name";
    /** OSチェックパラメータ:OSバージョン. */
    private static final String PARAM_KEY_OS_VERSION = "version";

    /** サーバー構成:構成情報ファイル保存先. */
    private static final String SERVER_CONSTRUCT_INFO_ROOT = "C:\\Rocky";
    /** サーバー構成:構成情報ファイル[センターサーバー]. */
    private static final String SERVER_CONSTRUCT_INFO_FILE_CENTER_SERVER = "center.txt";
    /** サーバー構成:構成情報ファイル[店舗サーバー]. */
    private static final String SERVER_CONSTRUCT_INFO_FILE_STORE_SERVER = "store.txt";
    /** サーバー構成:構成情報ファイル[POSマスター機]. */
    private static final String SERVER_CONSTRUCT_INFO_FILE_MASTER_POS = "posm.txt";

    /** システムプロパティ：アーキテクチャデータモデル. */
    private static String systemDataBits = null;

    /** システムプロパティ：JVM名. */
    private static String jvmName = null;

    /** システムプロパティ：OS名. */
    private static String osName = null;

    /** システムプロパティ：OSバージョン. */
    private static String osVersion = null;

    /** ホストのOS種別. */
    private static OPERATING_SYSTEMS hostOs = OPERATING_SYSTEMS.UNDEFINE;

    /** ホストのサーバー構成. */
    private static SERVER_CONSTRUCT serverConstruct = SERVER_CONSTRUCT.UNDEFINE;


    /**
     * OS種別列挙型.
     * @author 41791
     *
     */
    public enum OPERATING_SYSTEMS {
        /** OS種別:AIX. */
        AIX,
        /** OS種別:HP-UX. */
        HP_UX,
        /** OS種別:Irix. */
        IRIX,
        /** OS種別:Linux. */
        LINUX,
        /** OS種別:Mac. */
        MAC,
        /** OS種別:Mac OS X. */
        MAC_OS_X,
        /** OS種別:OS/2. */
        OS2,
        /** OS種別:Solaris. */
        SOLARIS,
        /** OS種別:SunOS. */
        SUN_OS,
        /** OS種別:Windows 95. */
        WINDOWS_95,
        /** OS種別:Windows NT. */
        WINDOWS_NT,
        /** OS種別:Windows 98. */
        WINDOWS_98,
        /** OS種別:Windows Me. */
        WINDOWS_ME,
        /** OS種別:Windows 2000. */
        WINDOWS_2K,
        /** OS種別:Windows XP. */
        WINDOWS_XP,
        /** OS種別:Windows 2003 Server および Windows XP x64. */
        WINDOWS_2K3,
        /** OS種別:Windows VISTA. */
        WINDOWS_VISTA,
        /** OS種別:Windows 2008 Server. */
        WINDOWS_2K8,
        /** OS種別:Windows 2008 Server R2. */
        WINDOWS_2K8R2,
        /** OS種別:Windows 7. */
        WINDOWS_7,
        /** OS種別:Windows 8. */
        WINDOWS_8,
        /** OS種別:Windows 8.1. */
        WINDOWS_81,
        /** OS種別:Windows 2012 Server. */
        WINDOWS_2012,
        /** OS種別:Windows 2012 Server R2. */
        WINDOWS_2012R2,
        /** OS種別:Windows 10. */
        WINDOWS_10,
        /** OS種別:不明. */
        UNKNWON,
        /** OS種別:未調査. */
        UNDEFINE;
    }
    /**
     * サーバー構成列挙型.
     * @author 41791
     */
    public enum SERVER_CONSTRUCT {
        /** サーバー構成:センターサーバ. */
        CENTER_SERVER,
        /** サーバー構成:店舗サーバ. */
        STORE_SERVER,
        /** サーバー構成:POSマスター機. */
        MASTER_POS,
        /** サーバー構成:不明. */
        UNKNWON,
        /** サーバー構成:未調査. */
        UNDEFINE;
    }
    static {
        try {

            //システムプロパティからアーキテクチャデータモデルを取得
            systemDataBits = System.getProperty("sun.arch.data.model");
            //システムプロパティからJVM名を取得
            jvmName = System.getProperty("java.vm.name");
            //システムプロパティからOS名を取得
            osName = System.getProperty("os.name");
            //システムプロパティからOSバージョンを取得
            osVersion = System.getProperty("os.version");

            // G001.02.1 add-begin
            logComment("javaVersion:" + System.getProperty("java.version"));
            logComment("javaRuntimeVersion:" + System.getProperty("java.runtime.version"));
            logComment("systemDataBits:" + systemDataBits);
            logComment("jvmName:" + jvmName);
            logComment("osName:" + osName);
            logComment("osVersion:" + osVersion);
            // G001.02.1 add-end
        } catch (NullPointerException npe) {
            //key が null の場合
            // G001.02.1 add
            logError("システムプロパティの取得に失敗しました。", npe);
            npe = null;
        } catch (IllegalArgumentException iae) {
            //key が空の場合
            // G001.02.1 add
            logError("システムプロパティの取得に失敗しました。", iae);
            iae = null;
        } catch (SecurityException se) {
            //セキュリティマネージャが存在し、セキュリティマネージャの checkPropertiesAccess メソッドが、指定されたシステムプロパティへのアクセスを許可しない場合
            // G001.02.1 add
            logError("システムプロパティの取得に失敗しました。", se);
            se = null;
        }
    }
    /**
     * コンストラクタ.
     * <P>
     * ユーティリティクラスはインスタンスを作成する必要がないのでコンストラクタを不可視化しています。
     * </P>
     */
    private Host() {
    }

    /**
     * ホスト名の取得.
     * <P>
     * ホスト名を取得します。<BR>
     * ホスト名の取得ができなかった場合はデフォルト値「LocalHost」を返します。
     * </P>
     * @return  ホスト名
     */
    public static String getHostName() {
        String hostName = DEFAULT_HOST_NAME;
        try {
            // ホスト名を取得する.
            InetAddress inet;
            inet = InetAddress.getLocalHost();
            hostName = inet.getHostName();
        } catch (UnknownHostException e) {
            e = null;
        }
        return hostName;
    }

    /**
     * IPアドレスの取得.
     * <P>
     * IPアドレスを取得します。<BR>
     * 複数のネットワークインターフェイスがインストールされているケースおよび<BR>
     * インターフェイスに複数のIPアドレスが設定されているケースの場合<BR>
     * ループバックアドレス「127.0.0.1」を除き、最初に列挙されたネットワークインターフェイスの<BR>
     * 最初に列挙されたIPアドレスが返ります。<BR>
     * アドレスの取得は最初に1度のみ行われるため、動作中にIPアドレスが変更された場合は変更内容は反映されません。
     * </P>
     * <p>この機能は{@linkplain HostNetwork#getIpAddress()}に委譲されました。こちらを使用してください。</p>
     * @return  IPアドレスの文字列
     */
    public static String getIpAddress() {
        return HostNetwork.getIpAddress();
    }

    /**
     * ホストのJVMが32bit環境か否か.
     * <P>
     * ホストのJVMが32bitか否かを判断します。<BR>
     * システムプロパティからアーキテクチャデータモデルを取得し、判断します。<BR>
     * データモデルが取得できない場合は、JVM名から判断します。
     * </P>
     * @return  true:32bit環境    false:64bit環境
     */
    public static boolean is32BitJVM() {
        if (systemDataBits != null) {
            //データモデルを優先して判断
            if (systemDataBits.equals("32")) {
                //32bit環境
                return true;
            } else if (systemDataBits.equals("64")) {
                //64bit環境
                return false;
            }
            //データモデルが取得できないときはJVM名で判断
            if (jvmName != null) {
                if (jvmName.toLowerCase().indexOf("64-bit") >= 0) {
                    //64bit環境
                    return false;
                }
            }
        }
        //上記で判断できないときは基本的に32bit環境とする
        return true;
    }

    /**
     * OS種別の取得.
     * <P>
     * ホストのOS種別を取得します。<BR>
     * ホストのOSがUnix系であるか、Windows系であるか程度の判断が必要な場合は<BR>
     * {@linkplain #isUnix()}メソッドまたは<BR>
     * {@linkplain #isWindows()}(Windows系)<BR>
     * {@linkplain #isWindows9x()}(Windowx9x系)<BR>
     * {@linkplain #isWindowsNT()}(WindowsNT系)メソッドを使用してください。
     * </P>
     * @return OS種別
     */
    public static OPERATING_SYSTEMS getOperatingSystem() {
        if (hostOs.equals(OPERATING_SYSTEMS.UNDEFINE)) {
            //OS情報が未取得の時のみ取得を行う。
            hostOs = searchOperatingSystem();
        }
        return hostOs;
    }
    /**
     * OSのWindows系統チェック.
     * <P>
     * ホストのOSがWindows系列であるか否かを取得します。<BR>
     * 9x系(95,98,Me)とNT系(NT,2K,XP,2003,VISTA,2008,7)の時にtrue、それ以外の時にfalseを返します。<BR>
     * 9x系とNT系の判断が必要な場合は{@linkplain #isWindows9x()}メソッドまたは{@linkplain #isWindowsNT()}メソッドを使用してください。
     * </P>
     * @return  true:Windows系 false:Windows系以外
     */
    public static boolean isWindows() {
        return isWindows9x() || isWindowsNT();
    }
    /**
     * OSのWindows9x系統チェック.
     * <P>
     * ホストのOSがWindows9x系列であるか否かを取得します。<BR>
     * 9x系(95,98,Me)の時にtrue、それ以外の時にfalseを返します。<BR>
     * 9x系とNT系の判断が必要ない場合は{@linkplain #isWindows()}メソッドを使用してください。
     * </P>
     * @return  true:Windows9x系 false:Windows9x系以外
     */
    public static boolean isWindows9x() {
        getOperatingSystem();   //必要に応じてOS種別を取得
        return hostOs.equals(OPERATING_SYSTEMS.WINDOWS_95)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_98)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_ME);
    }
    /**
     * OSのWindowsNT系統チェック.
     * <P>
     * ホストのOSがWindowsNT系列であるか否かを取得します。<BR>
     * NT系(NT,2000,XP,2003,VISTA,2008,7)の時にtrue、それ以外の時にfalseを返します。<BR>
     * 9x系とNT系の判断が必要ない場合は{@linkplain #isWindows()}メソッドを使用してください。
     * </P>
     * @return  true:WindowsNT系 false:WindowsNT系以外
     */
    public static boolean isWindowsNT() {
        getOperatingSystem();   //必要に応じてOS種別を取得
        return hostOs.equals(OPERATING_SYSTEMS.WINDOWS_NT)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2K)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_XP)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2K3)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_VISTA)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2K8)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2K8R2)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_7)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_8)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_81)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2012)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2012R2)
            || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_10);
    }
    // G001.02.0 add-function
    /**
     * OSのWindows2008チェック.
     * <P>
     * ホストのOSがWindows2008系列であるか否かを取得します。<BR>
     * Windows 2008の時にtrue、それ以外の時にfalseを返します。
     * </P>
     * @return  true:Windows2008系 false:Windows2008系以外
     */
    public static boolean isWindows2K8() {
        getOperatingSystem();   //必要に応じてOS種別を取得
        return hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2K8)
                || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2K8R2);
    }
    /**
     * OSのWindows2012チェック.
     * <P>
     * ホストのOSがWindows2012系列であるか否かを取得します。<BR>
     * Windows 2008の時にtrue、それ以外の時にfalseを返します。
     * </P>
     * @return  true:Windows2012系 false:Windows2012系以外
     */
    public static boolean isWindows2K12() {
        getOperatingSystem();   //必要に応じてOS種別を取得
        return hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2012)
                || hostOs.equals(OPERATING_SYSTEMS.WINDOWS_2012R2);
    }
    /**
     * OSのUnix系統チェック.
     * <P>
     * ホストのOSがUnix系列であるか否かを取得します。<BR>
     * ホストのOSがAIX,HP-UX,Irix,Linux,Mac OS X, Solaris, SunOSの時にtrue、それ以外の時にfalseを返します。<BR>
     * </P>
     * @return  true:Unix系 false:Unix系以外
     */
    public static boolean isUnix() {
        getOperatingSystem();   //必要に応じてOS種別を取得
        return hostOs.equals(OPERATING_SYSTEMS.AIX)
            || hostOs.equals(OPERATING_SYSTEMS.HP_UX)
            || hostOs.equals(OPERATING_SYSTEMS.IRIX)
            || hostOs.equals(OPERATING_SYSTEMS.LINUX)
            || hostOs.equals(OPERATING_SYSTEMS.MAC_OS_X)
            || hostOs.equals(OPERATING_SYSTEMS.SOLARIS)
            || hostOs.equals(OPERATING_SYSTEMS.SUN_OS);
    }

    /**
     * サーバ構成の取得.
     * <P>
     * ホストのサーバー構成を取得します。<BR>
     * チェックの優先順位は「POSマスター機」→「店舗サーバー」→「センターサーバー」の順です。
     * </P>
     * @return  サーバー構成
     */
    public static SERVER_CONSTRUCT getServerConstract() {
        if (serverConstruct.equals(SERVER_CONSTRUCT.UNDEFINE)) {
            //未調査の場合は調査する
            searchServerConstruct();
        }
        return serverConstruct;
    }

    /**
     * ホストの単一サーバー構成.
     * <P>
     * ホストの構成が単一サーバーであるかどうかを取得します。<BR>
     * なお、サーバー構成が不明な場合は単一サーバー構成とみなします。<BR>
     * つまり、センターサーバー構成時にfalseを返し、それ以外はtrueを返します。
     * </P>
     * @return  true:センターサーバー以外  false:センターサーバー
     */
    public static boolean isSingleServer() {
        return !getServerConstract().equals(SERVER_CONSTRUCT.CENTER_SERVER);
    }

    /**
     * OS種別検索.
     * <P>
     * システムプロパティのOS名とOSバージョンから該当するOS種別を選択する。
     * </P>
     * @return  OS種別
     */
    private static OPERATING_SYSTEMS searchOperatingSystem() {
        OPERATING_SYSTEMS thisOs = OPERATING_SYSTEMS.UNKNWON;
        ArrayList<Map<String, Object>> checkParams = new ArrayList<Map<String, Object>>();

        //なるべく確率の高い順に判断

        //Windows サーバーOS系
        //Windows2003Server系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2K3, OS_NAME_WINDOWS, OS_VERSION_WIN_2K3));
        //WindowsXP系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_XP, OS_NAME_WINDOWS, OS_VERSION_WIN_XP));
        //Windows2008Server系
        //OS名は先頭一致で判断しているので、Windows2008([Windows Server ～][6.0])は必ずVISTA([Windows ～][6.0])より先に判断する。
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2K8, OS_NAME_WINDOWS_SV, OS_VERSION_WIN_VISTA));
        //Windows2008ServerR2系
        //OS名は先頭一致で判断しているので、Windows2008R2([Windows Server ～][6.1])は必ずWindows7([Windows ～][6.1])より先に判断する。
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2K8R2, OS_NAME_WINDOWS_SV, OS_VERSION_WIN_7));
        //Windows2012Server系
        //OS名は先頭一致で判断しているので、Windows2012([Windows Server ～][6.2])は必ずWindows8([Windows ～][6.2])より先に判断する。
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2012, OS_NAME_WINDOWS_SV, OS_VERSION_WIN_8_2012));
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2012, OS_NAME_WINDOWS_NT, OS_VERSION_WIN_8_2012)); //古いVMは「Windows NT (unknown)」と返すことがある
        //Windows2012ServerR2系
        //OS名は先頭一致で判断しているので、Windows2012R2([Windows Server ～][6.3])は必ずWindows8.1([Windows ～][6.3])より先に判断する。
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2012R2, OS_NAME_WINDOWS_SV, OS_VERSION_WIN_81_2012R2));
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2012R2, OS_NAME_WINDOWS_NT, OS_VERSION_WIN_81_2012R2)); //古いVMは「Windows NT (unknown)」と返すことがある

        //Windoss クライアントOS系
        //WindowsVISTA系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_VISTA, OS_NAME_WINDOWS, OS_VERSION_WIN_VISTA));
        //Windows7系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_7, OS_NAME_WINDOWS, OS_VERSION_WIN_7));
        //Windows8系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_8, OS_NAME_WINDOWS, OS_VERSION_WIN_8_2012));
        //Windows8.1系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_81, OS_NAME_WINDOWS, OS_VERSION_WIN_81_2012R2));
        //Windows10系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_10, OS_NAME_WINDOWS, OS_VERSION_WIN_10));

        //その他、Linux系や旧OS系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.LINUX, OS_NAME_LINUX, ""));
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.LINUX, OS_NAME_LINUX_UPPER, ""));
        //Windows2000系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_2K, OS_NAME_WINDOWS, OS_VERSION_WIN_2K));
        //Solaris
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.SOLARIS, OS_NAME_SOLARIS, ""));
        //SunOS
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.SUN_OS, OS_NAME_SUN_OS, ""));
        //Mac OS X系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.MAC_OS_X, OS_NAME_MAC_OS_X, ""));  //Mac OS Xは必ずMacよりも先に判断する。
        //WindowsNT系
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_NT, OS_NAME_WINDOWS_NT, ""));
        //WindowsMe
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_ME, OS_NAME_WINDOWS, OS_VERSION_WIN_ME));  //WindowsMeのOS名は2000系(以降)を使用
        //Windows98
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_98, OS_NAME_WINDOWS_9X, OS_VERSION_WIN_98));
        //Windows95
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.WINDOWS_95, OS_NAME_WINDOWS_9X, OS_VERSION_WIN_95));
        //AIX
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.AIX, OS_NAME_AIX, ""));
        //HP-UX
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.HP_UX, OS_NAME_HP_UX, ""));
        //Irix
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.IRIX, OS_NAME_IRIX, ""));
        //Mac
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.MAC, OS_NAME_MAC, ""));
        //OS/2
        checkParams.add(makeCheckParam(OPERATING_SYSTEMS.OS2, OS_NAME_OS2, ""));

        for (Map<String, Object> param : checkParams) {
            String name = param.get(PARAM_KEY_OS_NAME).toString();
            String version = param.get(PARAM_KEY_OS_VERSION).toString();
            if (getOSMatches(name, version)) {
                //OS種別検索にHit
                thisOs = ( OPERATING_SYSTEMS ) param.get(PARAM_KEY_OS_DIV);
                break;
            }
        }
        return thisOs;
    }

    /**
     * OS種別一致判断.
     * <p>
     * システムプロパティの情報が指定されたOS名、OSバージョンに一致するかをチェックします。<BR>
     * OS名・OSバージョンとも大文字・小文字を区別し、前方一致で判断します。<BR>
     * また、OSバージョンにnullまたは「""」が指定された場合は、OSバージョンのチェックは行いません。
     * </p>
     * @param osNamePrefix    OS名
     * @param osVersionPrefix OSバージョン
     * @return  条件に一致した場合にtrue, それ以外はfalseを返します。
     */
    private static boolean getOSMatches(String osNamePrefix, String osVersionPrefix) {
        boolean isMatch = false;
        if (osNamePrefix != null && osNamePrefix.length() > 0) {
            if (osName != null) {
                //OS名で判断
                isMatch = osName.startsWith(osNamePrefix);
                if (osVersionPrefix != null && osVersionPrefix.length() > 0) {
                    //OS名とバージョンで判断
                    if (osVersion != null) {
                        isMatch = isMatch && osVersion.startsWith(osVersionPrefix);
                    } else {
                        //OSバージョン情報なし
                        isMatch = false;
                    }
                }
            }
        }
        return isMatch;
    }

    /**
     * OS種別検索パラメータ作成.
     * <P>
     * OS種別を検索するためのパラメータマップを作成します。
     * </P>
     * @param os    OS種別
     * @param name    OS名
     * @param version OSバージョン
     * @return  パラメータマップ
     */
    private static Map<String, Object> makeCheckParam(OPERATING_SYSTEMS os, String name, String version) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put(PARAM_KEY_OS_DIV, os);
        param.put(PARAM_KEY_OS_NAME, name);
        param.put(PARAM_KEY_OS_VERSION, version);
        return param;
    }

    /**
     * サーバー構成情報の検索.
     * <P>
     * サーバー構成情報ファイルを検索し、サーバー構成を取得します。
     * </P>
     */
    private static void searchServerConstruct() {
        final String keyConstruct = "construct";
        final String keyFile = "file";

        String infoRoot = SERVER_CONSTRUCT_INFO_ROOT.concat(File.separator);
        Map<String, Object> checkMap;
        List<Map<String, Object>> checkList = new ArrayList<Map<String, Object>>();

        serverConstruct = SERVER_CONSTRUCT.UNKNWON;

        //チェック優先順に定義（M機→店舗サーバー→センターサーバー)
        //POSマスター機
        checkMap = new HashMap<String, Object>();
        checkMap.put(keyConstruct, SERVER_CONSTRUCT.MASTER_POS);
        checkMap.put(keyFile, infoRoot.concat(SERVER_CONSTRUCT_INFO_FILE_MASTER_POS));
        checkList.add(checkMap);

        //店舗サーバー
        checkMap = new HashMap<String, Object>();
        checkMap.put(keyConstruct, SERVER_CONSTRUCT.STORE_SERVER);
        checkMap.put(keyFile, infoRoot.concat(SERVER_CONSTRUCT_INFO_FILE_STORE_SERVER));
        checkList.add(checkMap);

        //センターサーバ
        checkMap = new HashMap<String, Object>();
        checkMap.put(keyConstruct, SERVER_CONSTRUCT.CENTER_SERVER);
        checkMap.put(keyFile, infoRoot.concat(SERVER_CONSTRUCT_INFO_FILE_CENTER_SERVER));
        checkList.add(checkMap);

        for (Map<String, Object> map : checkList) {
            if (new File(map.get(keyFile).toString()).exists()) {
                serverConstruct = ( SERVER_CONSTRUCT ) map.get(keyConstruct);
                break;
            }
        }
    }

    // G001.02.1 add-function
    /**
     * コメントログ.
     * <p>
     * コメントログを出力します。
     * </p>
     * @param msg ログメッセージ
     */
    private static void logComment(String msg) {
//        MDC.put(Log.MDC_KEY_APP_ID, "Host");
//        MDC.put(Log.MDC_KEY_CLASS_ID, "rocky.base.common.util.Host");
//        log.trace("0000," + msg);
    }

   // G001.02.1 add-function
    /**
     * エラーログ.
     * <p>
     * エラーログを出力します。
     * </p>
     * @param msg ログメッセージ
     */
    private static void logError(String msg) {
//        MDC.put(Log.MDC_KEY_APP_ID, "Host");
//        MDC.put(Log.MDC_KEY_CLASS_ID, "rocky.base.common.util.Host");
//        log.error("9999," + msg);
    }

   // G001.02.1 add-function
    /**
     * 詳細エラーログ.
     * <p>
     * 詳細エラーログを出力します。
     * </p>
     * @param msg ログメッセージ
     * @param e 例外
     */
    private static void logError(String msg, Throwable e) {
        logError(msg);
//        MDC.put(Log.MDC_KEY_APP_ID, "Host");
//        MDC.put(Log.MDC_KEY_CLASS_ID, "rocky.base.common.util.Host");
//        log.fatal("9999," + msg, e);
    }
}
