package jp.gr.java_conf.osumitan.infoq;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import jp.gr.java_conf.osumitan.infoq.site.EnquateSite;
import jp.gr.java_conf.osumitan.infoq.site.MangaEnquateSite;
import jp.gr.java_conf.osumitan.infoq.site.QuizSite;
import jp.gr.java_conf.osumitan.infoq.site.SaveUpSite;
import jp.gr.java_conf.osumitan.infoq.site.ShoppingNowSite;

/**
 * infoQメイン
 */
public class Main {

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
	/** 完了クローズボタンセレクタ */
	private static final By COMPLETE_CLOSE_BUTTON_SELECTOR = By.cssSelector("input.btn_close_en");
	/** おまけ回答ボタンセレクタ */
	private static final By APPEND_ANSWER_BUTTON_SELECTOR = By.cssSelector("button#button_answer");
	/** おまけラジオボタンセレクタ */
	private static final By APPEND_RADIO_BUTTON_SELECTOR = By.cssSelector("input[type='radio']");
	/** おまけチェックボックスセレクタ */
	private static final By APPEND_CHECK_BOX_SELECTOR = By.cssSelector("input[type='checkbox']");
	/** 更新リンクパス */
	private static final By REFRESH_LINK_PATH = By.xpath("//strong[text()='毎日たまる']//ancestor::div[@id='enquete_mypage_section']//a[@class='btn-reload']");
	/** ログアウトフォームセレクタ */
	private static final By LOGOUT_FORM_SELECTOR = By.cssSelector("form#AccountLogoutForm");

	/** ドライバ */
	private RemoteWebDriver driver;
	/** メインウィンドウハンドル */
	private String mainHandle;
	/** サイトリスト */
	private List<EnquateSite> siteList;
	/** 現在のアンケートサイト */
	private EnquateSite currentSite;

	/**
	 * メイン
	 * @param args 引数
	 */
	public static void main(String[] args) {
		try {
			new Main().start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * コンストラクタ
	 */
	public Main() {
		// サイトリスト
		this.siteList = Arrays.asList(
				new SaveUpSite(),
				new ShoppingNowSite(),
				new MangaEnquateSite(),
				new QuizSite());
	}

	/**
	 * スタート
	 * @exception Exception
	 */
	private void start() throws Exception {
		// ドライバ初期化
		this.driver = initDriver();
		// ログイン
		login();
		// 未回答がなくなるまで
		while(exists(ENQUETE_LINK_PATH)) {
			// アンケートに回答
			enquete();
		}
		// ログアウト
		logout();
		// ウィンドウを閉じる
		this.driver.quit();
	}

	/**
	 * ドライバ初期化
	 * @return ドライバ
	 */
	private RemoteWebDriver initDriver() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("enable-extensions");
		RemoteWebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(800, 600));
		driver.manage().window().setPosition(new Point(520, 120));
		return driver;
	}

	/**
	 * ログイン
	 */
	private void login() {
		// トップページを開く
		navigate(LOGIN_URL);
		// メールアドレス
		setValue(LOGIN_MAIL_ADDRESS_SELECTOR, LOGIN_MAIL_ADDRESS);
		// パスワード
		setValue(LOGIN_PASSWORD_SELECTOR, LOGIN_PASSWORD);
		// ログインボタン押下
		click(LOGIN_BUTTON_SELECTOR);
		// メインウィンドウハンドルを取得
		this.mainHandle = this.driver.getWindowHandle();
	}

	/**
	 * ログアウト
	 */
	private void logout() {
		// ログアウト
		findElement(LOGOUT_FORM_SELECTOR).submit();;
	}

	/**
	 * アンケートに回答
	 */
	private void enquete() {
		// アンケートリンクをクリック
		click(ENQUETE_LINK_PATH);
		// アンケートウィンドウにスイッチ
		switchToSubWindow(true);
		// サイト取得
		this.currentSite = getEnquateSite();
		// IFRAMEにスイッチ
		switchToIframe();
		// 最終テキストが出るまで
		while(!exists(this.currentSite.getFinalTextPath())) {
			// 特殊質問に回答
			if(!answerSpecialQuestion()) {
				// ラジオボタンを選択
				selectRadioButton();
				// チェックボックスを選択
				selectCheckBox();
				// プルダウンを選択
				selectPullDown();
			}
			// 広告クローズボタンがあったら押下
			if(exists(this.currentSite.getAdCloseButton())) {
				WebElement adCloseButton = findElement(this.currentSite.getAdCloseButton());
				if(adCloseButton.isDisplayed()) {
					adCloseButton.click();
				}
			}
			// 次へボタン押下
			click(this.currentSite.getNextButtonSelector());
		}
		// 最終ボタン押下
		click(this.currentSite.getFinalButtonSelector());
		// おまけ質問有無
		if(exists(APPEND_ANSWER_BUTTON_SELECTOR)) {
			// おまけ質問に回答
			clickRandom(APPEND_RADIO_BUTTON_SELECTOR);
			clickRandom(APPEND_CHECK_BOX_SELECTOR);
			click(APPEND_ANSWER_BUTTON_SELECTOR);
		}
		// 完了クローズボタン有無
		if(exists(COMPLETE_CLOSE_BUTTON_SELECTOR)) {
			// 完了クローズボタン押下
			click(COMPLETE_CLOSE_BUTTON_SELECTOR);
		} else {
			// ウィンドウを閉じる
			this.driver.close();
			// 完了ウィンドウにスイッチ
			String cmpHandle = switchToSubWindow(false);
			if(cmpHandle != null) {
				// 完了クローズボタン押下
				click(COMPLETE_CLOSE_BUTTON_SELECTOR);
			}
		}
		// メインウィンドウにスイッチ
		switchToMainWindow();
		// 一覧を更新
		click(REFRESH_LINK_PATH);
	}

	/**
	 * スリープ
	 * @param time 時間
	 */
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch(InterruptedException e) {
			return;
		}
	}

	/**
	 * メインウィンドウにスイッチ
	 */
	private void switchToMainWindow() {
		this.driver.switchTo().window(this.mainHandle);
	}

	/**
	 * サブウィンドウにスイッチ
	 * @param required 必須
	 * @return サブウィンドウのハンドル
	 */
	private String switchToSubWindow(boolean required) {
		// 必須時はスイッチできるまでリトライ
		boolean b = false;
		while(!b) {
			for(String handle : this.driver.getWindowHandles()) {
				if(!this.mainHandle.equals(handle)) {
					this.driver.switchTo().window(handle);
					b = true;
					return handle;
				}
			}
			if(!required) {
				b = true;
				return null;
			} else {
				sleep(100L);
			}
		}
		return null;
	}

	/**
	 * IFRAMEにスイッチ
	 */
	private void switchToIframe() {
		if(this.currentSite.getIframeSelector() != null && exists(this.currentSite.getIframeSelector())) {
			this.driver.switchTo().frame(findElement(this.currentSite.getIframeSelector()));
		}
	}

	/**
	 * アンケートサイトを取得
	 * @return アンケートサイト
	 */
	private EnquateSite getEnquateSite() {
		// ドメインから判断
		String url = this.driver.getCurrentUrl();
		for(EnquateSite site : this.siteList) {
			if(url.contains(site.getDomain())) {
				return site;
			}
		}
		throw new RuntimeException("未知のサイトです。");
	}

	/**
	 * 特殊質問に回答
	 * @return 特殊質問に回答したか
	 */
	private boolean answerSpecialQuestion() {
		// 性別
		if(exists(this.currentSite.getGenderQuestionPath())) {
			click(this.currentSite.getGenderAnswerPath());
			return true;
		}
		// 年齢
		else if(exists(this.currentSite.getAgeQuestionPath())) {
			click(this.currentSite.getAgeAnswerPath());
			return true;
		}
		// 居住地
		else if(exists(this.currentSite.getResidenceQuestionPath())) {
			click(this.currentSite.getResidenceAnswerPath());
			return true;
		}
		// 職業
		else if(exists(this.currentSite.getJobQuestionPath())) {
			click(this.currentSite.getJobAnswerPath());
			return true;
		}
		// 特殊質問ではない
		else {
			return false;
		}
	}

	/**
	 * ラジオボタンを選択
	 */
	private void selectRadioButton() {
		clickRandom(this.currentSite.getRadioButtonSelector());
	}

	/**
	 * チェックボックスを選択
	 */
	private void selectCheckBox() {
		clickRandom(this.currentSite.getCheckBoxSelector());
	}

	/**
	 * プルダウンを選択
	 */
	private void selectPullDown() {
		// 空の選択肢は選ばない
		if(!exists(this.currentSite.getOptionSelector())) {
			return;
		}
		WebElement option = null;
		while(option == null || StringUtils.isBlank(option.getAttribute("value"))) {
			option = clickRandom(this.currentSite.getOptionSelector());
		}
	}

	/**
	 * ページを開く
	 * @param url URL
	 */
	private void navigate(String url) {
		this.driver.get(url);;
	}

	/**
	 * エレメントが存在するか
	 * @param by By
	 * @return エレメントが存在するか
	 */
	private boolean exists(By by) {
		if(by == null) {
			return false;
		}
		List<WebElement> list = this.driver.findElements(by);
		return !list.isEmpty();
	}

	/**
	 * エレメントを取得
	 * @param by By
	 * @return エレメント
	 */
	private WebElement findElement(By by) {
		return driver.findElement(by);
	}

	/**
	 * エレメントリストを取得
	 * @param by By
	 * @return エレメントリスト
	 */
	private List<WebElement> findElements(By by) {
		return this.driver.findElements(by);
	}

	/**
	 * エレメントに値を設定
	 * @param by By
	 * @param value 値
	 */
	private void setValue(By by, String value) {
		findElement(by).sendKeys(value);
	}

	/**
	 * エレメントをクリック
	 * @param by By
	 */
	private void click(By by) {
		boolean b = false;
		while(!b) {
			try {
				findElement(by).click();
				b = true;
			} catch(ElementNotVisibleException e) {
				// visibleでないときはリトライ
				b = false;
				sleep(100L);
			} catch(TimeoutException e) {
				// タイムアウト時は無視
				b = true;
			}
		}
	}

	/**
	 * エレメントをランダムクリック
	 * @param by By
	 * @return クリックしたエレメント
	 */
	private WebElement clickRandom(By by) {
		List<WebElement> list = findElements(by);
		if(list == null || list.isEmpty()) {
			return null;
		}
		Double index = Math.random() * list.size();
		WebElement element = list.get(index.intValue());
		element.click();
		return element;
	}
}
