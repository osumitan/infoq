package jp.gr.java_conf.osumitan.infoq.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import jp.gr.java_conf.osumitan.infoq.site.AdResearchSite;
import jp.gr.java_conf.osumitan.infoq.site.AdSurveySite;
import jp.gr.java_conf.osumitan.infoq.site.ColumnEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.EnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.HikikagamiSite;
import jp.gr.java_conf.osumitan.infoq.site.InfoPanelSite;
import jp.gr.java_conf.osumitan.infoq.site.KotsutaSite;
import jp.gr.java_conf.osumitan.infoq.site.MangaEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.QuizSite;
import jp.gr.java_conf.osumitan.infoq.site.SaveUpSite;
import jp.gr.java_conf.osumitan.infoq.site.ShindanAppsSite;
import jp.gr.java_conf.osumitan.infoq.site.ShinriCheckEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.ShopQPSite;
import jp.gr.java_conf.osumitan.infoq.site.ShoppingNowSite;
import jp.gr.java_conf.osumitan.infoq.site.SurveyEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.VoteMediaSite;

/**
 * ホストの基底クラス
 */
public abstract class Host {

	/** 通常時時ウェイト */
	private static final long NORMAL_WAIT_INTERVAL = 100L;
	/** エラー時ウェイト */
	private static final long ERROR_WAIT_INTERVAL = 5000L;

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
				new HikikagamiSite(),
				new ShinriCheckEnqueteSite(),
				new AdSurveySite(),
				new KotsutaSite(),
				new ShindanAppsSite(),
				new SurveyEnqueteSite(),
				new ShopQPSite(),
				new ColumnEnqueteSite(),
				new VoteMediaSite(),
				new AdResearchSite());
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
		// アンケートテキストを退避
		String enquateText = enqueteLink.getAttribute("onclick");
		// アンケートリンクをクリック
		click(enqueteLink);
		// アンケートウィンドウにスイッチ
		switchToSubWindow(true);
		// サイト取得
		this.currentSite = getEnqueteSite();
		// IFRAMEにスイッチ
		switchToIframe();
		// ブラックアンケートを確認
		// （いきなり次へボタンがない場合も同様）
		if(exists(this.currentSite.getBlackEnquetePath())
				|| !exists(this.currentSite.getNextButtonSelector())) {
			// ブラックリストに追加
			this.blackList.add(enquateText);
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
			// サイト取得（infopanelの場合）
			if(InfoPanelSite.DOMAIN.equals(this.currentSite.getDomain())) {
				this.currentSite = getEnqueteSite();
			}
			// ラジオボタンを選択
			selectRadioButton();
			// チェックボックスを選択
			selectCheckBox();
			// プルダウンを選択
			selectPullDown();
			// テキストエリアに入力
			inputTextArea();
			// 特殊質問に回答
			answerSpecialQuestion();
			// フロート広告を閉じる
			if(exists(this.currentSite.getFloatAdCloseButtonSelector())) {
				this.driver.executeScript(this.currentSite.getFloatAdCloseScript());
			}
			// 次へボタン
			if(exists(this.currentSite.getNextButtonSelector())) {
				// 次へボタン押下
				click(this.currentSite.getNextButtonSelector());
			} else if(this.currentSite.isToWaitWhenNextButtonNotFound()) {
				// 次へボタンがないとき待つ
				sleep(NORMAL_WAIT_INTERVAL);
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
		if(exists(this.currentSite.getFinalButtonSelector())) {
			click(this.currentSite.getFinalButtonSelector());
		}
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
		// メインウィンドウ以外を閉じる
		for(String handle : this.driver.getWindowHandles()) {
			if(!this.mainHandle.equals(handle)) {
				try {
					this.driver.switchTo().window(handle);
					this.driver.close();
				} catch(NoSuchWindowException e) {
					// スキップ
				}
			}
		}
		// メインウィンドウにスイッチ
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
				sleep(NORMAL_WAIT_INTERVAL);
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
		throw new RuntimeException("未知のサイトです。" + url);
	}

	/**
	 * 次のアンケートリンクを取得
	 * @return 次のアンケートリンク
	 */
	private WebElement findNextEnqueteLink() {
		for(WebElement element : findElements(this.enqueteLinkPath)) {
			String text = element.getAttribute("onclick");
			if(!this.blackList.contains(text)) {
				return element;
			}
		}
		return null;
	}

	/**
	 * 特殊質問に回答
	 */
	private void answerSpecialQuestion() {
		// 性別
		if(exists(this.currentSite.getGenderQuestionPath())) {
			if(exists(this.currentSite.getGenderAnswerPath())) {
				click(this.currentSite.getGenderAnswerPath());
			}
		}
		// 年齢
		if(exists(this.currentSite.getAgeQuestionPath())) {
			if(exists(this.currentSite.getAgeAnswerPath())) {
				click(this.currentSite.getAgeAnswerPath());
			}
		}
		// 居住地
		if(exists(this.currentSite.getResidenceQuestionPath())) {
			if(exists(this.currentSite.getResidenceAnswerPath())) {
				click(this.currentSite.getResidenceAnswerPath());
			}
		}
		// 職業
		if(exists(this.currentSite.getJobQuestionPath())) {
			if(exists(this.currentSite.getJobAnswerPath())) {
				click(this.currentSite.getJobAnswerPath());
			}
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
		clickRandom(this.currentSite.getOptionSelector());
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
				sleep(ERROR_WAIT_INTERVAL);
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
				sleep(ERROR_WAIT_INTERVAL);
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
			} catch(StaleElementReferenceException e) {
				// StaleElementReferenceException時は無視
				b = true;
			} catch(WebDriverException e) {
				// リトライ
				b = false;
				sleep(ERROR_WAIT_INTERVAL);
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
	private void clickRandom(By by) {
		List<WebElement> listAll = findElements(by);
		if(listAll == null || listAll.isEmpty()) {
			return;
		}
		HashMap<String, List<WebElement>> map = new HashMap<String, List<WebElement>>();
		String attr = this.currentSite.getQuestionGroupAttribute();
		String regexp = this.currentSite.getQuestionGroupIdentifier();
		if(StringUtils.isBlank(attr) || StringUtils.isBlank(regexp)) {
			// 質問グループなし
			map.put("*", listAll);
		} else {
			// 質問グループあり
			Pattern ptn = Pattern.compile(regexp);
			for(WebElement elm : listAll) {
				String value = elm.getAttribute(attr);
				if(!StringUtils.isBlank(value)) {
					Matcher m = ptn.matcher(value);
					m.find();
					String id = m.group(1);
					List<WebElement> list = map.get(id);
					if(list == null) {
						list = new ArrayList<WebElement>();
						map.put(id, list);
					}
					list.add(elm);
				}
			}
		}
		for(List<WebElement> list : map.values()) {
			Double index = Math.random() * list.size();
			click(list.get(index.intValue()));
		}
	}
}
