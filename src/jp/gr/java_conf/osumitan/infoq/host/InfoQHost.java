package jp.gr.java_conf.osumitan.infoq.host;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * ホスト：infoQ
 */
public class InfoQHost extends Host {

	/** ログインページURL */
	private static final String LOGIN_URL = "https://infoq.jp/";
	/** ログインメールアドレスセレクタ */
	private static final By LOGIN_MAIL_ADDRESS_SELECTOR = By.cssSelector("#AccountEmail");
	/** ログインメールアドレス */
	private static final String LOGIN_MAIL_ADDRESS = "love3factory@gmail.com";
	/** ログインパスワードセレクタ */
	private static final By LOGIN_PASSWORD_SELECTOR = By.cssSelector("#AccountPassword");
	/** ログインパスワード */
	private static final String LOGIN_PASSWORD = "oo5XvmdR7moiOdLoOXzA";
	/** ログインボタンセレクタ */
	private static final By LOGIN_BUTTON_SELECTOR = By.cssSelector(".btn_login_ssl");
	/** アンケートリンクパス */
	private static final By ENQUETE_LINK_PATH = By.xpath("//strong[text()='毎日たまる']//ancestor::div[@id='enquete_mypage_section']//img[contains(@src,'mtop_i_stus01.gif')]/ancestor::a");
	/** おまけ回答ボタンセレクタ */
	private static final By APPEND_ANSWER_BUTTON_SELECTOR = By.cssSelector("button#button_answer");
	/** おまけラジオボタンセレクタ */
	private static final By APPEND_RADIO_BUTTON_SELECTOR = By.cssSelector("input[type='radio']");
	/** おまけチェックボックスセレクタ */
	private static final By APPEND_CHECK_BOX_SELECTOR = By.cssSelector("input[type='checkbox']");
	/** 完了クローズボタンセレクタ */
	private static final By COMPLETE_CLOSE_BUTTON_SELECTOR = By.cssSelector("input.btn_close_en");
	/** 更新リンクパス */
	private static final By REFRESH_LINK_PATH = By.xpath("//strong[text()='毎日たまる']//ancestor::div[@id='enquete_mypage_section']//a[@class='btn-reload']");
	/** ログアウトフォームセレクタ */
	private static final By LOGOUT_FORM_SELECTOR = By.cssSelector("form#AccountLogoutForm");

	/**
	 * コンストラクタ
	 * @param driver ドライバ
	 */
	public InfoQHost(RemoteWebDriver driver) {
		super(driver);
		// ログイン前ポーズ要否
		this.neesPreLoginPause = true;
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
		// おまけ回答ボタンセレクタ
		super.appendAnswerButtonSelector = APPEND_ANSWER_BUTTON_SELECTOR;
		// おまけラジオボタンセレクタ
		super.appendRadioButtonSelector = APPEND_RADIO_BUTTON_SELECTOR;
		// おまけチェックボックスセレクタ
		super.appendCheckBoxSelector = APPEND_CHECK_BOX_SELECTOR;
		// 完了クローズボタンセレクタ
		super.completeCloseButtonSelector = COMPLETE_CLOSE_BUTTON_SELECTOR;
		// 更新リンクパス
		super.refreshLinkPath = REFRESH_LINK_PATH;
		// ログアウトフォームセレクタ
		super.logoutFormSelector = LOGOUT_FORM_SELECTOR;
	}
}
