package jp.gr.java_conf.osumitan.infoq.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import jp.gr.java_conf.osumitan.infoq.site.AdSurveySite;
import jp.gr.java_conf.osumitan.infoq.site.EnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.InfoPanelSite;
import jp.gr.java_conf.osumitan.infoq.site.KotsutaSite;
import jp.gr.java_conf.osumitan.infoq.site.MangaEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.QuizSite;
import jp.gr.java_conf.osumitan.infoq.site.SaveUpSite;
import jp.gr.java_conf.osumitan.infoq.site.ShindanAppsSite;
import jp.gr.java_conf.osumitan.infoq.site.ShinriCheckEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.ShoppingNowSite;

/**
 * ホストの基底クラス
 */
public abstract class Host {

	/** ドライバ */
	protected RemoteWebDriver driver;
	/** サイトリスト */
	protected List<EnqueteSite> siteList;
	/** メインウィンドウハンドル */
	protected String mainHandle;
	/** 現在のアンケートサイト */
	protected EnqueteSite currentSite;
	/** ブラックリスト */
	protected List<String> blackList;

	/** ログイン前ポーズ要否 */
	protected boolean neesPreLoginPause;
	/** ログインページURL */
	protected String loginUrl;
	/** ログインメールアドレスセレクタ */
	protected By loginMailSelector;
	/** ログインメールアドレス */
	protected String loginMailAddress;
	/** ログインパスワードセレクタ */
	protected By loginPasswordSelector;
	/** ログインパスワード */
	protected String loginPassword;
	/** ログインボタンセレクタ */
	protected By loginButtonSelector;
	/** アンケートリンクパス */
	protected By enqueteLinkPath;
	/** おまけ回答ボタンセレクタ */
	protected By appendAnswerButtonSelector;
	/** おまけラジオボタンセレクタ */
	protected By appendRadioButtonSelector;
	/** おまけチェックボックスセレクタ */
	protected By appendCheckBoxSelector;
	/** 完了クローズボタンセレクタ */
	protected By completeCloseButtonSelector;
	/** 更新リンクパス */
	protected By refreshLinkPath;
	/** ログアウトリンクセレクタ */
	protected By logoutLinkSelector;
	/** ログアウトフォームセレクタ */
	protected By logoutFormSelector;

	/**
	 * コンストラクタ
	 * @param driver ドライバ
	 */
	public Host(RemoteWebDriver driver) {
		// ドライバ
		this.driver = driver;
		// ログイン前ポーズ要否
		this.neesPreLoginPause = false;
		// ブラックリスト
		this.blackList = new ArrayList<String>();
		// サイトリスト
		this.siteList = Arrays.asList(
				new SaveUpSite(),
				new ShoppingNowSite(),
				new MangaEnqueteSite(),
				new QuizSite(),
				new InfoPanelSite(),
				new ShinriCheckEnqueteSite(),
				new AdSurveySite(),
				new KotsutaSite(),
				new ShindanAppsSite());
	}

	/**
	 * スタート
	 */
	public void start() {
		// ログイン
		login();
		// 未回答がなくなるまで
		WebElement enqueteLink;
		while((enqueteLink = findNextEnqueteLink()) != null) {
			// アンケートに回答
			enquete(enqueteLink);
		}
		// ログアウト
		logout();
	}

	/**
	 * ログイン
	 */
	private void login() {
		// トップページを開く
		navigate(this.loginUrl);
		// メインウィンドウハンドルを取得
		this.mainHandle = this.driver.getWindowHandle();
		// メールアドレス
		setValue(this.loginMailSelector, this.loginMailAddress);
		// パスワード
		setValue(this.loginPasswordSelector, this.loginPassword);
		// ログイン前ポーズ要否
		if(this.neesPreLoginPause) {
			try {
				System.out.println("★★★ ログイン準備ができたらEnter押下 ★★★");
				new BufferedReader(new InputStreamReader(System.in)).readLine();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		// ログインボタン押下
		click(this.loginButtonSelector);
	}

	/**
	 * ログアウト
	 */
	private void logout() {
		// ログアウト
		if(exists(this.logoutLinkSelector)) {
			findElement(this.logoutLinkSelector).click();
		} else if(exists(this.logoutFormSelector)) {
			findElement(this.logoutFormSelector).submit();
		}
	}

	/**
	 * アンケートに回答
	 * @param アンケートリンク
	 */
	private void enquete(WebElement enqueteLink) {
		// アンケートリンクをクリック
		click(enqueteLink);
		// アンケートウィンドウにスイッチ
		switchToSubWindow(true);
		// サイト取得
		this.currentSite = getEnqueteSite();
		// IFRAMEにスイッチ
		switchToIframe();
		// ブラックアンケートを確認
		if(exists(this.currentSite.getBlackEnquetePath())) {
			// ブラックリストに追加
			this.blackList.add(enqueteLink.getText());
			// ウィンドウを閉じる
			this.driver.close();
			// 	メインウィンドウにスイッチ
			switchToMainWindow();
			// 一覧を更新
			click(this.refreshLinkPath);
			// 広告処理中断
			return;
		}
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
				// テキストエリアに入力
				inputTextArea();
			}
			// 次へボタン
			if(exists(this.currentSite.getNextButtonSelector())) {
				// 次へボタン押下
				click(this.currentSite.getNextButtonSelector());
			} else if(this.currentSite.isToWaitWhenNextButtonNotFound()) {
				// 次へボタンがないとき待つ
				sleep(100L);
			} else {
				// ウィンドウを閉じる
				this.driver.close();
				// 	メインウィンドウにスイッチ
				switchToMainWindow();
				// 一覧を更新
				click(this.refreshLinkPath);
				// 広告処理中断
				return;
			}
		}
		// 最終ボタン押下
		click(this.currentSite.getFinalButtonSelector());
		// おまけ質問有無
		if(exists(this.appendAnswerButtonSelector)) {
			// おまけ質問に回答
			clickRandom(this.appendRadioButtonSelector);
			clickRandom(this.appendCheckBoxSelector);
			click(this.appendAnswerButtonSelector);
		}
		// 完了クローズボタン有無
		if(exists(this.completeCloseButtonSelector)) {
			// 完了クローズボタン押下
			click(this.completeCloseButtonSelector);
		} else {
			// ウィンドウを閉じる
			this.driver.close();
			// 完了ウィンドウにスイッチ
			String cmpHandle = switchToSubWindow(false);
			if(cmpHandle != null) {
				// 完了クローズボタン押下
				click(this.completeCloseButtonSelector);
			}
		}
		// メインウィンドウにスイッチ
		switchToMainWindow();
		// 一覧を更新
		click(this.refreshLinkPath);
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
	private EnqueteSite getEnqueteSite() {
		// ドメインから判断
		String url = this.driver.getCurrentUrl();
		for(EnqueteSite site : this.siteList) {
			if(url.contains(site.getDomain())) {
				return site;
			}
		}
		throw new RuntimeException("未知のサイトです。");
	}

	/**
	 * 次のアンケートリンクを取得
	 * @return 次のアンケートリンク
	 */
	private WebElement findNextEnqueteLink() {
		for(WebElement element : findElements(this.enqueteLinkPath)) {
			String text = element.getText();
			if(!this.blackList.contains(text)) {
				return element;
			}
		}
		return null;
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
	 * テキストエリアに入力
	 */
	private void inputTextArea() {
		setValue(this.currentSite.getTextAreaSelector(), "特になし。");
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
		List<WebElement> list = findElements(by);
		return !list.isEmpty();
	}

	/**
	 * エレメントを取得
	 * @param by By
	 * @return エレメント
	 */
	private WebElement findElement(By by) {
		if(by == null) return null;
		for(;;) {
			try {
				return this.driver.findElement(by);
			} catch(StaleElementReferenceException e) {
				// リトライ
				sleep(100L);
			}
		}
	}

	/**
	 * エレメントリストを取得
	 * @param by By
	 * @return エレメントリスト
	 */
	private List<WebElement> findElements(By by) {
		if(by == null) return null;
		for(;;) {
			try {
				return this.driver.findElements(by);
			} catch(StaleElementReferenceException e) {
				// リトライ
				sleep(100L);
			}
		}
	}

	/**
	 * エレメントに値を設定
	 * @param by By
	 * @param value 値
	 */
	private void setValue(By by, String value) {
		if(!exists(by)) return;
		findElement(by).sendKeys(value);
	}

	/**
	 * エレメントをクリック
	 * @param element エレメント
	 */
	private void click(WebElement element) {
		boolean b = false;
		while(!b) {
			try {
				element.click();
				b = true;
			} catch(TimeoutException e) {
				// タイムアウト時は無視
				b = true;
			} catch(WebDriverException e) {
				// リトライ
				b = false;
				sleep(100L);
			}
		}
	}

	/**
	 * エレメントをクリック
	 * @param by By
	 */
	private void click(By by) {
		click(findElement(by));
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
