package jp.gr.java_conf.osumitan.infoq.exception;

/**
 * ブラックリスト例外
 */
public class BlackListException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 3192950524473340572L;

	/** ユニークキー */
	private String uniqueKey;

	/**
	 * コンストラクタ
	 * @param uniqueKey
	 */
	public BlackListException(String uniqueKey) {
		// ユニークキー
		this.uniqueKey = uniqueKey;
	}

	/**
	 * @return uniqueKey
	 */
	public String getUniqueKey() {
		return uniqueKey;
	}
}
