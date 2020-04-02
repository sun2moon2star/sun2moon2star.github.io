/*
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20180308 FanMH(bbasoft)    G000.00.0 新規作成(仮想POS通信改善)
 * 20180329 Kobayashi(TEC)    G001.00.0 排他処理改善
 */
package jp.co.archTest.rest.ps.v1.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketSemaphore {
	private static Logger logger = LoggerFactory.getLogger(SocketSemaphore.class);

	private static Map<String, Semaphore> semaphoreMap = new HashMap<String, Semaphore>();

	// G001.00.0 Add-Start
	private final static Object lock = new Object();
	// G001.00.0 Add-End

	/**
	 * publicにインスタンスを作成させないためのprotectedなコンストラクタ.
	 */
	protected SocketSemaphore() {
	}

	/**
	 * 仮想POS通信開始要求します.
	 * @param posKey
	 */
	public static void acquire(String posKey) throws Exception {
		Semaphore semaphore = null;
		// G001.00.0 Mainte-Start
		//synchronizedブロックのロックオブジェクトはfinal staticのメンバオブジェクトとする
		//synchronized (SocketSemaphore.class) {
		synchronized (lock) {
		// G001.00.0 Mainte-End
			semaphore = semaphoreMap.get(posKey);
			if (semaphore == null) {
				// 同じタイミング、一要求を行うできます.
				semaphore = new Semaphore(1);
				semaphoreMap.put(posKey, semaphore);
			}
		}

		try {
			semaphore.acquire();
			logger.debug("▼▼▼▼▼仮想POS IP" + posKey + "排他開始。 Thread:" + Thread.currentThread().getName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
			    semaphore.release();
			} catch (Exception e1) {
			}
			throw e;
		}
	}

	/**
	 * 仮想POS通信終了通知します.
	 * @param posKey
	 */
	public static void release(String posKey) {
		Semaphore semaphore = semaphoreMap.get(posKey);
		if (semaphore != null) {
			try {
				logger.debug("▲▲▲▲▲仮想POS IP" + posKey + "排他終了。Thread:" + Thread.currentThread().getName());
				semaphore.release();
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
	}
}
