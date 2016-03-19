package jp.gr.java_conf.osumitan.infoq.host;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * ホスト：Qzoo
 */
public class QzooHost extends Host {

	/** ログインページURL */
	private static final String LOGIN_URL = "https://qzoo.jp/";
	/** ログインメールアドレスセレクタ */
	private static final By LOGIN_MAIL_ADDRESS_SELECTOR = By.cssSelector("input[name='login_mo_pc_mail']");
	/** ログインメールアドレス */
	private static final String LOGIN_MAIL_ADDRESS = "love3factory@gmail.com";
	/** ログインパスワードセレクタ */
	private static final By LOGIN_PASSWORD_SELECTOR = By.cssSelector("input[name='login_mo_password']");
	/** ログインパスワード */
	private static final String LOGIN_PASSWORD = "y33r7AHWcx";
	/** ログインボタンセレクタ */
	private static final By LOGIN_BUTTON_SELECTOR = By.cssSelector("input[alt='ログインする']");
	/** アンケートリンクパス */
	private static final By ENQUETE_LINK_PATH = By.xpath("//td[contains(text(),'他社：【広告付】') or contains(text(),'【広告付き】')]/ancestor::tr//img[@alt='回答する']//ancestor::a");
	/** 完了クローズボタンセレクタ */
	private static final By COMPLETE_CLOSE_BUTTON_SELECTOR = By.xpath("//input[contains(@class,'btn_close')]");
	/** 更新リンクパス */
	private static final By REFRESH_LINK_PATH = By.xpath("//img[@alt='HOME']/ancestor::a");
	/** ログアウトリンクセレクタ */
	private static final By LOGOUT_LINK_SELECTOR = By.xpath("//img[@alt='ログアウト']/ancestor::a");

	/**
	 * コンストラクタ
	 * @param driver ドライバ
	 */
	public QzooHost(RemoteWebDriver driver) {
		super(driver);
		// ログインページURL
		super.loginUrl = LOGIN_URL;
		// ログインメールアドレスセレクタ
		super.loginMailSelector = LOGIN_MAIL_ADDRESS_SELECTOR;
		// ログインメールアドレス
		super.loginMailAddress = LOGIN_MAIL_ADDRESS;
		// ログインパスワードセレクタ
		super.loginPasswordSelector = LOGIN_PASSWORD_SELECTOR;
		// ログインパスワード
		super.loginPassword = LOGIN_PASSWORD;
		// ログインボタンセレクタ
		super.loginButtonSelector = LOGIN_BUTTON_SELECTOR;
		// アンケートリンクパス
		super.enqueteLinkPath = ENQUETE_LINK_PATH;
		// 完了クローズボタンセレクタ
		super.completeCloseButtonSelector = COMPLETE_CLOSE_BUTTON_SELECTOR;
		// 更新リンクパス
		super.refreshLinkPath = REFRESH_LINK_PATH;
		// ログアウトリンクセレクタ
		super.logoutLinkSelector = LOGOUT_LINK_SELECTOR;
	}
}
