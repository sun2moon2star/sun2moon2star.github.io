/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20131129 N.Komori(TIS)     G001.00.0 新規作成(ネットワーク系の情報取得をHostクラスから分離)
 */
package jp.co.archTest.rest.ps.common.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ホストネットワーククラス.
 * <P>
 * ホストのネットワーク情報を提供します。
 * </P>
 * <P>
 * Copyright (C) 2008 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * @author N.Komori(TIS)
 */
public final class HostNetwork {


    /** サーバーに設定されているすべてのネットワークアドレス情報. */
    private static List<HostNetworkAddressInfo> serverAddresses = new ArrayList<HostNetworkAddressInfo>();
    /** 内部キャッシュ(サーバーと同じサブネットのアドレスと判断したアドレスのキャッシュ). */
    private static Map<String, InetAddress> innerAddressCache = new ConcurrentHashMap<String, InetAddress>();
    /** 外部キャッシュ(サーバーと異なるサブネットのアドレスと判断したアドレスのキャッシュ). */
    private static Map<String, InetAddress> outerAddressCache = new ConcurrentHashMap<String, InetAddress>();
    /** IPアドレス. */
    private static String ipAddress = "";

    static {
        // ログ初期化.
    }

    /**
     * 同一サブネット判定.
     * <p>
     * 指定されたネットワークアドレスがサーバーのサブネットと同一であるか判断します。
     * </p>
     * @param address テキスト表現の IP アドレス文字列
     * @return  true:同一サブネット    false:異なるサブネット
     */
    public static boolean isInnerAddress(String address) {
        //文字列型のアドレスはキャッシュのキーを検索
        if (innerAddressCache.containsKey(address)) {
            //内部キャッシュにヒット
            return true;
        } else if (outerAddressCache.containsKey(address)) {
            //外部キャッシュにヒット
            return false;
        } else {
            //キャッシュにヒットしない場合は検索
            try {
                return isInnerAddress(InetAddress.getByName(address));
            } catch (UnknownHostException e) {
                logError(String.format("指定されたアドレス[%1$s]を正しく認識できませんでした。", address), e);
                return false;
            }
        }
    }

    /**
     * 同一サブネット判定.
     * <p>
     * 指定されたネットワークアドレスがサーバーのサブネットと同一であるか判断します。
     * </p>
     * @param address IPアドレス
     * @return  true:同一サブネット    false:異なるサブネット
     */
    public static boolean isInnerAddress(InetAddress address) {
        //配列型のアドレスはキャッシュの値を検索
        if (innerAddressCache.containsValue(address)) {
            //内部キャッシュにヒット
            return true;
        } else if (outerAddressCache.containsValue(address)) {
            //外部キャッシュにヒット
            return false;
        } else {
            //アドレス列挙を行っておく必要があるのでアドレス取得を実行する。
            getIpAddress();
            synchronized (serverAddresses) {
                //サーバーのすべてのアドレス情報と比較
                for (HostNetworkAddressInfo adrInfo : serverAddresses) {
                    if (adrInfo.isInnerAddress(address)) {
                        //内部キャッシュに登録
                        innerAddressCache.put(address.getHostAddress(), address);
                        return true;
                    }
                }
            }
        }
        //外部キャッシュに登録
        outerAddressCache.put(address.getHostAddress(), address);
        return false;
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
     * @return  IPアドレスの文字列
     */
    public static String getIpAddress() {
        if (ipAddress.length() == 0) {
            ipAddress = getNifAddress();
        }
        return ipAddress;
    }

    /**
     * ネットワークインターフェイスアドレスの取得.
     * <P>
     * ネットワークインターフェイスを列挙し、さらにそのインターフェイスに設定されているIPアドレスを列挙します。<BR>
     * 列挙したアドレスのうち、ループバックアドレス「127.0.0.1」を除き、最初に列挙されたIPアドレスを返します。<BR>
     * IPアドレスが取得できなかった場合はループバックアドレス「127.0.0.1」を返します。
     * <P>
     * @return  IPアドレス
     */
    private static String getNifAddress() {
        synchronized (serverAddresses) {
            String nifAddress = "";
            try {
                //アドレス列挙リスト初期化
                serverAddresses = new ArrayList<HostNetworkAddressInfo>();

                //インストールされているネットワークインターフェイスを列挙
                Enumeration<NetworkInterface> enumNif = NetworkInterface.getNetworkInterfaces();
				if (enumNif != null) {
					while (enumNif.hasMoreElements()) {
						NetworkInterface nif = enumNif.nextElement();
						// 表示名（デバイス名）
						String nifDisplayName = nif.getDisplayName();
						// インターフェイス名
						String nifName = nif.getName();
						try {
							// 日本語が化けるの文字コードを変換
							nifDisplayName = new String(nif.getDisplayName().getBytes("iso-8859-1"));
						} catch (UnsupportedEncodingException e) {
							nifDisplayName = nif.getDisplayName();
						}
						// インターフェイスに設定されているIPアドレスを列挙
						for (InterfaceAddress ifAdr : nif.getInterfaceAddresses()) {
							HostNetworkAddressInfo nefAdrInfo = new HostNetworkAddressInfo(nifName, nifDisplayName,
									ifAdr);
							if (!nefAdrInfo.isLoopBack()) {
								// ループバック以外をリストアップ
								serverAddresses.add(nefAdrInfo);
							}
						}
					}
				}

                //アドレスの選択
                int findCount = serverAddresses.size(); //見つかったアドレス件数
                if (findCount < 1) {
                    //アドレスなし
                    //ループバックアドレス「127.0.0.1」を返す。
                    nifAddress = getLoopBackAddress();
                    // G001.02.1 mod
                    logError(String.format("IPアドレスが見つかりませんでした。ループバックアドレス「%1$s」を設定します。", nifAddress));
                } else {
                    //アドレスあり
                    nifAddress = serverAddresses.get(0).getHostAddress();
                    if (findCount > 1) {
                        //アドレス複数
                        // G001.02.1 mod-begin
                        logComment(String.format("IPアドレスが複数[%1$d件]見つかりました。以下の候補から「%2$s/%3$d」が選択されました。",
                                findCount,
                                nifAddress,
                                serverAddresses.get(0).getPrefixLength()));
                        int no = 1;
                        for (HostNetworkAddressInfo adrInf : serverAddresses) {
                            logComment(String.format("アドレス候補[%1$d]IPアドレス[%2$s/%3$d] デバイス名「%4$s」",
                                    no++,
                                    adrInf.getHostAddress(),
                                    adrInf.getPrefixLength(),
                                    adrInf.getDisplayName()));
                        }
                        // G001.02.1 mod-end
                    }
                }
            } catch (SocketException e) {
                //ネットワークインターフェイスの列挙に失敗
                nifAddress = getLoopBackAddress();
                // G001.02.1 add
                logError(String.format("ネットワークインターフェイスの列挙に失敗しました。ループバックアドレス「%1$s」を設定します。", nifAddress), e);
            }
            return nifAddress;
        }
    }

    /**
     * ループバックアドレスの取得.
     * <P>
     * ループバックアドレスのIPアドレス文字列を返します。
     * </P>
     * @return  ループバックのIPアドレス文字列
     */
    private static String getLoopBackAddress() {
        StringBuilder lbAdr = new StringBuilder();
        for (int index = 0; index < HostNetworkAddressInfo.LOOP_BACK_ADDRESS_V4.length; index++) {
            lbAdr.append(HostNetworkAddressInfo.LOOP_BACK_ADDRESS_V4[index]);
            if (index < HostNetworkAddressInfo.LOOP_BACK_ADDRESS_V4.length - 1) {
                lbAdr.append(".");
            }
        }
        return lbAdr.toString();
    }


    /**
     * コメントログ.
     * <p>
     * コメントログを出力します。
     * </p>
     * @param msg ログメッセージ
     */
    private static void logComment(String msg) {
//        MDC.put(Log.MDC_KEY_APP_ID, "HostNetwork");
//        MDC.put(Log.MDC_KEY_CLASS_ID, "rocky.base.common.util.Host");
//        log.trace("0000," + msg);
    }

    /**
     * エラーログ.
     * <p>
     * エラーログを出力します。
     * </p>
     * @param msg ログメッセージ
     */
    private static void logError(String msg) {
//        MDC.put(Log.MDC_KEY_APP_ID, "HostNetwork");
//        MDC.put(Log.MDC_KEY_CLASS_ID, "rocky.base.common.util.Host");
//        log.error("9999," + msg);
    }

    /**
     * 詳細エラーログ.
     * <p>
     * 詳細エラーログを出力します。
     * </p>
     * @param msg ログメッセージ
     * @param e 例外
     */
    private static void logError(String msg, Throwable e) {
//        logError(msg);
//        MDC.put(Log.MDC_KEY_APP_ID, "HostNetwork");
//        MDC.put(Log.MDC_KEY_CLASS_ID, "rocky.base.common.util.Host");
//        log.fatal("9999," + msg, e);
    }
}
/**
 * ネットワークインターフェイスのアドレス情報クラス.
 * @author za2411te
 *
 */
class HostNetworkAddressInfo {

    /** ループバックアドレス IPv4. */
    public static final byte[] LOOP_BACK_ADDRESS_V4 = {127, 0, 0, 1};
    /** ループバックアドレス IPv6. */
    private static final byte[] LOOP_BACK_ADDRESS_V6 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};

    /** インターフェイス名. */
    private String name_ = "";
    /** インターフェイス表示名(デバイス名). */
    private String displayName_ = "";
    /** 完全修飾ドメイン名. */
    private String canonicalHostName_ = "";
    /** 生のIPアドレス. */
    private byte[] address_;
    /** テキスト表現の IP アドレス文字列. */
    private String hostAddress_ = "";
    /** ホスト名. */
    private String hostName_ = "";
    /** ネットワーク接頭辞長(サブネットマスク長). */
    private short prefixLength_ = 0;

    /**
     * コンストラクタ.
     * @param name インターフェイス名
     * @param displayName インターフェイス表示名(デバイス名)
     * @param ifAdr ネットワークインタフェースアドレス
     */
    public HostNetworkAddressInfo(String name, String displayName, InterfaceAddress ifAdr) {
        //インターフェイス名
        name_ = name;
        //インターフェイス表示名(デバイス名)
        displayName_ = displayName;
        //完全修飾ドメイン名
        canonicalHostName_ = ifAdr.getAddress().getCanonicalHostName();
        //IPアドレス
        hostAddress_ = ifAdr.getAddress().getHostAddress();
        //ホスト名
        hostName_ = ifAdr.getAddress().getHostName();
        //ネットワーク接頭辞長(サブネットマスク長)
        prefixLength_ = ifAdr.getNetworkPrefixLength();
        //生のIPアドレス
        address_ = ifAdr.getAddress().getAddress();
    }

    /**
     * ループバックアドレス比較.
     * <P>
     * 指定されたアドレスがループバックアドレスと一致するかを調査します。
     * </P>
     * @return  true:ループバックアドレス false:ループバックアドレス以外
     */
    public boolean isLoopBack() {
        boolean loopBack = false;
        if (address_.length == LOOP_BACK_ADDRESS_V4.length) {
            loopBack = true;
            for (int index = 0; index < LOOP_BACK_ADDRESS_V4.length; index++) {
                loopBack = loopBack && (address_[index] == LOOP_BACK_ADDRESS_V4[index]);
                if (!loopBack) {
                    break;
                }
            }
        } else if (address_.length == LOOP_BACK_ADDRESS_V6.length) {
            loopBack = true;
            for (int index = 0; index < LOOP_BACK_ADDRESS_V6.length; index++) {
                loopBack = loopBack && (address_[index] == LOOP_BACK_ADDRESS_V6[index]);
                if (!loopBack) {
                    break;
                }
            }
        }
        return loopBack;
    }

    /**
     * 同一サブネット判定.
     * <p>
     * 指定されたネットワークアドレスがサーバーのサブネットと同一であるか判断します。
     * </p>
     * @param address  IPアドレス
     * @return  true:同一サブネット    false:異なるサブネット
     */
    public boolean isInnerAddress(InetAddress address) {
        byte[] addArray = address.getAddress();
        int prefixOcted = prefixLength_ / 8;    //サブネット長(オクテッド単位)
        if (prefixOcted > addArray.length) {
            //IPv6が有効な環境ではIPv4に対し、4バイト(32ビット)を超えるプレフィックス長が設定されることがある
            //(たぶんアドレス取得に失敗して一時IPを割り当てたときにマスクにフルビット割り当てていると思う)ので
            //32ビット以上のプレフィックス長のときは32ビットとする
            prefixOcted = addArray.length;
        }
        int previxSubBit = prefixLength_ % 8;   //サブネット長(オクテッド単位からはみ出る分)
        //オクテッド単位で比較
        for (int oct = 0; oct < prefixOcted; oct++) {
            if (address_[oct] != addArray[oct]) {
                return false;
            }
        }
        if (previxSubBit == 0) {
            //オクテッド単位外のマスクがなければサブネット一致で処理終了
            return true;
        } else {
            //オクテッド単位未満で比較
            int argSubAdr = ( int ) addArray[prefixOcted];
            int curSubAdr = ( int ) address_[prefixOcted];
            //マスク分を残し右にビットシフト
            argSubAdr = argSubAdr >> (8 - previxSubBit);
            curSubAdr = curSubAdr >> (8 - previxSubBit);
            //シフトを戻す
            argSubAdr = argSubAdr << (8 - previxSubBit);
            curSubAdr = curSubAdr << (8 - previxSubBit);
            //マスク後の値が一致すればサブネット一致
            return curSubAdr == argSubAdr;
        }
    }

    /**
     * インターフェイス名の取得.
     * <p>
     * ネットワークインターフェイス名を取得します。
     * </p>
     * @return  インターフェイス名
     */
    public String getName() {
        return name_;
    }

    /**
     * インターフェイス表示名(デバイス名)の取得.
     * <p>
     * ネットワークインターフェイス表示名(デバイス名)を取得します。
     * </p>
     * @return  インターフェイス表示名(デバイス名)
     */
    public String getDisplayName() {
        return displayName_;
    }

    /**
     * 完全修飾ドメイン名の取得.
     * <p>
     * ネットワークインターフェイスの完全修飾ドメイン名を取得します。
     * </p>
     * @return  完全修飾ドメイン名
     */
    public String getCanonicalHostName() {
        return canonicalHostName_;
    }

    /**
     * 生のIPアドレスの取得.
     * <p>
     * ネットワークインターフェイスの生のIPアドレスを取得します。
     * </p>
     * @return  生のIPアドレス
     */
    public byte[] getAddress() {
        return address_;
    }

    /**
     * テキスト表現のIPアドレス文字列の取得.
     * <p>
     * ネットワークインターフェイスのテキスト表現のIPアドレス文字列を取得します。
     * </p>
     * @return  テキスト表現のIPアドレス文字列
     */
    public String getHostAddress() {
        return hostAddress_;
    }

    /**
     * ホスト名の取得.
     * <p>
     * ネットワークインターフェイスのホスト名を取得します。
     * </p>
     * @return  ホスト名
     */
    public String getHostName() {
        return hostName_;
    }

    /**
     * ネットワーク接頭辞長(サブネットマスク長)の取得.
     * <p>
     * ネットワークインターフェイスのネットワーク接頭辞長(サブネットマスク長)を取得します。
     * </p>
     * @return  ネットワーク接頭辞長(サブネットマスク長)
     */
    public short getPrefixLength() {
        return prefixLength_;
    }
}
