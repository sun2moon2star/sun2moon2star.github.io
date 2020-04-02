/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20120925 H.Iwaki(CUBE)     G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.common.util.impl;

import java.io.InputStream;

/**
 * InputStream生成.
 *
 * <P>
 * Copyright (C) 2012 TOSHIBA TEC Corporation. All Rights Reserved.
 * </P>
 * @author Administrator
 */
public class FileloadContext {

    /** FileloadContextオブジェクト. */
    private static FileloadContext obj_ = new FileloadContext();

    /**
     * コンストラクタ.
     */
    private FileloadContext() {

    }

    /**
     * インスタンス取得.
     * スレッドセーフなSingletonクラスを取得します。
     * @return
     */
    public static FileloadContext getInstance() {
        return obj_;
    }

    /**
     * ファイルのInputStream生成.
     * <pre>
     * 指定されたパスをもとにファイルのInputStreamを生成し返します。
     * Thread.currentThread().getContextClassLoader().getResourceAsStream(path)
     * </pre>
     *
     * @param path パス
     * @return InputStream
     */
    public synchronized InputStream load(String path) {

        InputStream result = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(path);

        return result;
    }

}
