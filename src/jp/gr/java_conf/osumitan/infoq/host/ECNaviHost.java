package jp.gr.java_conf.osumitan.infoq.host;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * ホスト：Qzoo
 */
public class ECNaviHost extends Host {

	/** ログインページURL */
	private static final String LOGIN_URL = "http://ecnavi.jp/research/";
	/** ログインメールアドレスセレクタ */
	private static final By LOGIN_MAIL_ADDRESS_SELECTOR = By.cssSelector("input[name='email']");
	/** ログインメールアドレス */
	private static final String LOGIN_MAIL_ADDRESS = "luv3mari@gmail.com";
	/** ログインパスワードセレクタ */
	private static final By LOGIN_PASSWORD_SELECTOR = By.cssSelector("input[name='passwd']");
	/** ログインパスワード */
	private static final String LOGIN_PASSWORD = "68fe6a28fa";
	/** ログインボタンセレクタ */
	private static final By LOGIN_BUTTON_SELECTOR = By.xpath("//button[text()='ログイン']");
	/** アンケートリンクパス */
	private static final By ENQUETE_LINK_PATH = By.xpath("//tr[not(contains(@style,'display:none'))]//a[text()='回答する']");
	/** アンケートユニークキーパス */
	private static final By ENQUETE_LINK_ATTRIBUTE = By.xpath("ancestor::tr/td[@class='title']");
	/** 更新リンクパス */
	private static final By REFRESH_LINK_PATH = By.cssSelector("li.research.active a");

	/**
	 * コンストラクタ
	 * @param driver ドライバ
	 */
	public ECNaviHost(RemoteWebDriver driver) {
		super(driver);
		// ログイン前ポーズ要否
		this.needsPreLoginPause = true;
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
		// アンケートユニークキーパス
		super.enqueteUniqueKeyPath = ENQUETE_LINK_ATTRIBUTE;
		// 更新リンクパス
		super.refreshLinkPath = REFRESH_LINK_PATH;
	}
}
