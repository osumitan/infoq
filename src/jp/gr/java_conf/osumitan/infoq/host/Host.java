package jp.gr.java_conf.osumitan.infoq.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import jp.gr.java_conf.osumitan.infoq.exception.BlackListException;
import jp.gr.java_conf.osumitan.infoq.site.AdResearchSite;
import jp.gr.java_conf.osumitan.infoq.site.AdSurveySite;
import jp.gr.java_conf.osumitan.infoq.site.ColumnEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.EnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.HikikagamiGqxSite;
import jp.gr.java_conf.osumitan.infoq.site.HikikagamiR8Site;
import jp.gr.java_conf.osumitan.infoq.site.InfoPanelSite;
import jp.gr.java_conf.osumitan.infoq.site.KotsutaSite;
import jp.gr.java_conf.osumitan.infoq.site.MangaEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.NResearchSite;
import jp.gr.java_conf.osumitan.infoq.site.PhotoEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.QuestionHirobaSite;
import jp.gr.java_conf.osumitan.infoq.site.QuizSite;
import jp.gr.java_conf.osumitan.infoq.site.ResearchECNaviSite;
import jp.gr.java_conf.osumitan.infoq.site.SaveUpSite;
import jp.gr.java_conf.osumitan.infoq.site.ShindanAppsSite;
import jp.gr.java_conf.osumitan.infoq.site.ShinriCheckEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.ShopQPSite;
import jp.gr.java_conf.osumitan.infoq.site.ShoppingNowSite;
import jp.gr.java_conf.osumitan.infoq.site.SurveyEnqueteSite;
import jp.gr.java_conf.osumitan.infoq.site.TsukulinkSite;
import jp.gr.java_conf.osumitan.infoq.site.VoteMediaSite;
import jp.gr.java_conf.osumitan.infoq.site.Y2atSite;

/**
 * ホストの基底クラス
 */
public abstract class Host {

	/** 通常時時ウェイト */
	private static final long NORMAL_WAIT_INTERVAL = 1000L;
	/** エラー時ウェイト */
	private static final long ERROR_WAIT_INTERVAL = 5000L;
	/** タイムアウト時リトライ回数 */
	private static final int TIMEOUT_RETRY_COUNT = 3;

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
	protected boolean needsPreLoginPause;
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
	/** アンケートユニークキーパス */
	protected By enqueteUniqueKeyPath;
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
	/** アンケート前スクリプト */
	protected String beforeEnqueteScript;
	/** アンケート前スクリプト実行中セレクタ */
	protected By beforeEnqueteScriptProcessingSelector;
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
		this.needsPreLoginPause = false;
		// ブラックリスト
		this.blackList = new ArrayList<String>();
		// サイトリスト
		this.siteList = Arrays.asList(
				new SaveUpSite(),
				new ShoppingNowSite(),
				new MangaEnqueteSite(),
				new QuizSite(),
				new InfoPanelSite(),
				new HikikagamiR8Site(),
				new HikikagamiGqxSite(),
				new ShinriCheckEnqueteSite(),
				new AdSurveySite(),
				new KotsutaSite(),
				new ShindanAppsSite(),
				new SurveyEnqueteSite(),
				new ShopQPSite(),
				new ColumnEnqueteSite(),
				new VoteMediaSite(),
				new AdResearchSite(),
				new TsukulinkSite(),
				new PhotoEnqueteSite(),
				new NResearchSite(),
				new ResearchECNaviSite(),
				new Y2atSite(),
				new QuestionHirobaSite());
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
			try {
				// アンケートに回答
				enquete(enqueteLink);
			} catch(BlackListException e) {
				// ブラックリストに追加
				this.blackList.add(e.getUniqueKey());
				// ウィンドウを閉じる
				closeWindow();
			} catch(TimeoutException e) {
				// リトライアウトのタイムアウトは開き直し
				// ウィンドウを閉じる
				closeWindow();
			}
			// 	メインウィンドウにスイッチ
			switchToMainWindow();
			// 一覧を更新
			click(this.refreshLinkPath);
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
		this.mainHandle = get(this.driver::getWindowHandle);
		// メールアドレス
		setValue(this.loginMailSelector, this.loginMailAddress);
		// パスワード
		setValue(this.loginPasswordSelector, this.loginPassword);
		// ログイン前ポーズ要否
		if(this.needsPreLoginPause) {
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
		if(!click(this.logoutLinkSelector)) {
			submit(this.logoutFormSelector);
		}
	}

	/**
	 * アンケートに回答
	 * @param アンケートリンク
	 */
	private void enquete(WebElement enqueteLink) {
		// アンケートユニークキーを退避
		String uniqueKey = get(findElement(enqueteLink, this.enqueteUniqueKeyPath)::getText);
		// アンケートリンクをクリック
		if(!click(enqueteLink)) {
			// アンケートリンクが開けなければブラックリストに追加して中断
			throw new BlackListException(uniqueKey);
		}
		// アンケートウィンドウにスイッチ
		switchToSubWindow(true);
		// サイト取得
		this.currentSite = getEnqueteSite();
		// 未知のサイトならブラックリストに追加して中断
		if(this.currentSite == null) {
			throw new BlackListException(uniqueKey);
		}
		// IFRAMEにスイッチ
		switchToIframe();
		// ブラックアンケートならブラックリストに追加して中断
		if(exists(this.currentSite.getBlackEnquetePath())) {
			throw new BlackListException(uniqueKey);
		}
		// 次へボタン押下前スクリプト
		executeScript(this.currentSite.getScriptBeforeNextButton());
		// スタートボタン押下
		click(this.currentSite.getStartButtonSelector());
		// 最終テキストが出るまで
		while(!existsFinalText()) {
			// サイト取得（infopanelの場合）
			if(InfoPanelSite.DOMAIN.equals(this.currentSite.getDomain())) {
				this.currentSite = getEnqueteSite();
				// 未知のサイトならブラックリストに追加して中断
				if(this.currentSite == null) {
					throw new BlackListException(uniqueKey);
				}
				// IFRAMEにスイッチ
				switchToIframe();
			}
			// 質問に回答
			answerQuestion();
			// 次へボタン押下前スクリプト
			executeScript(this.currentSite.getScriptBeforeNextButton());
			// 次へボタン押下
			if(!click(this.currentSite.getNextButtonSelector())) {
				// 最終テキストがないのに次へボタンもない
				System.out.println("next button not found");
				// ブラックリストに追加して中断
				throw new BlackListException(uniqueKey);
			}
			// サイト取得（infopanelの場合）
			else if(InfoPanelSite.DOMAIN.equals(this.currentSite.getDomain())) {
				this.currentSite = getEnqueteSite();
				// 未知のサイトならブラックリストに追加して中断
				if(this.currentSite == null) {
					throw new BlackListException(uniqueKey);
				}
				// IFRAMEにスイッチ
				switchToIframe();
			}
		}
		// 最終質問に回答
		if(this.currentSite.isHasFinalQuestion()) {
			answerQuestion();
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
		// 完了クローズボタンを待つ
		if(this.currentSite.isToWaitCompleteCloseButton()) {
			while(!exists(this.completeCloseButtonSelector)) {
				sleep(NORMAL_WAIT_INTERVAL);
			}
		}
		// 完了クローズボタン有無
		if(!click(this.completeCloseButtonSelector)) {
			// ウィンドウを閉じる
			closeWindow();
			// 完了ウィンドウにスイッチ
			if(switchToSubWindow(false) != null) {
				// 完了クローズボタン押下
				click(this.completeCloseButtonSelector);
			}
		}
		// 完了したらブラックリストに追加（一覧から消えないものを再度開かないため）
		this.blackList.add(uniqueKey);
	}

	/**
	 * ウィンドウを閉じる
	 */
	private void closeWindow() {
		run(this.driver::close);
	}

	/**
	 * ウィンドウハンドルを取得
	 * @return ウィンドウハンドル
	 */
	private Set<String> getWindowHandles() {
		return get(this.driver::getWindowHandles);
	}

	/**
	 * ウィンドウにスイッチ
	 * @param handle ウィンドウハンドル
	 * @return 処理結果
	 */
	private boolean switchToWindow(String handle) {
		return accept(get(this.driver::switchTo)::window, handle);
	}

	/**
	 * メインウィンドウにスイッチ
	 */
	private void switchToMainWindow() {
		// メインウィンドウ以外を閉じる
		for(String handle : getWindowHandles()) {
			if(!this.mainHandle.equals(handle)) {
				if(switchToWindow(handle)) {
					closeWindow();
				}
			}
		}
		// メインウィンドウにスイッチ
		switchToWindow(this.mainHandle);
	}

	/**
	 * サブウィンドウにスイッチ
	 * @param required 必須
	 * @return サブウィンドウのハンドル
	 */
	private String switchToSubWindow(boolean required) {
		boolean b = false;
		while(!b) {
			// メインウィンドウ以外にスイッチ
			for(String handle : getWindowHandles()) {
				if(!this.mainHandle.equals(handle)) {
					switchToWindow(handle);
					b = true;
					return handle;
				}
			}
			// サブウィンドウがないとき、必須ならリトライ
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
	 * フレームにスイッチ
	 * @param element エレメント
	 */
	private void switchToFrame(WebElement element) {
		accept(get(this.driver::switchTo)::frame, element);
	}

	/**
	 * フレームにスイッチ
	 * @param by By
	 * preturn フレーム有無
	 */
	private boolean switchToFrame(By by) {
		if(exists(by)) {
			switchToFrame(findElement(by));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * IFRAMEにスイッチ
	 */
	private void switchToIframe() {
		switchToFrame(this.currentSite.getIframeSelector());
	}

	/**
	 * アンケートサイトを取得
	 * @return アンケートサイト
	 */
	private EnqueteSite getEnqueteSite() {
		// ドメインから判断
		String url = get(this.driver::getCurrentUrl);
		for(EnqueteSite site : this.siteList) {
			if(site.getDomainPattern().matcher(url).matches()) {
				return site;
			}
		}
		System.out.println("未知のサイトです。" + url);
		return null;
	}

	/**
	 * 次のアンケートリンクを取得
	 * @return 次のアンケートリンク
	 */
	private WebElement findNextEnqueteLink() {
		// アンケート前スクリプト実行
		executeScript(this.beforeEnqueteScript);
		while(exists(this.beforeEnqueteScriptProcessingSelector)) {
			sleep(NORMAL_WAIT_INTERVAL);
		}
		// 次のアンケートリンクを取得
		List<WebElement> list = findElements(this.enqueteLinkPath);
		Collections.reverse(list);
		for(WebElement element : list) {
			String uniqueKey = get(findElement(element, this.enqueteUniqueKeyPath)::getText);
			if(!this.blackList.contains(uniqueKey)) {
				return element;
			}
		}
		return null;
	}

	/**
	 * 質問に回答
	 */
	private void answerQuestion() {
		// クリック広告リンクを開く
		click(this.currentSite.getClickAdLinkSelector());
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
		// 同意チェックボックス
		if(exists(this.currentSite.getAgreeCheckBoxSelector())) {
			click(this.currentSite.getAgreeCheckBoxSelector());
		}
		// 画像変化スタート
		if(exists(this.currentSite.getImageChangeStartSelector())) {
			click(this.currentSite.getImageChangeStartSelector());
			sleep(11000L);
		}
	}

	/**
	 * 特殊質問に回答
	 */
	private void answerSpecialQuestion() {
		// 性別
		if(exists(this.currentSite.getGenderQuestionPath())) {
			click(this.currentSite.getGenderAnswerPath());
		}
		// 年齢
		if(exists(this.currentSite.getAgeQuestionPath())) {
			click(this.currentSite.getAgeAnswerPath());
		}
		// 居住地
		if(exists(this.currentSite.getResidenceQuestionPath())) {
			click(this.currentSite.getResidenceAnswerPath());
		}
		// 職業
		if(exists(this.currentSite.getJobQuestionPath())) {
			click(this.currentSite.getJobAnswerPath());
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
		accept(this.driver::get, url);
	}

	/**
	 * スクリプトを実行
	 * @param script スクリプト
	 */
	private void executeScript(String script) {
		if(StringUtils.isNotBlank(script)) {
			accept(this.driver::executeScript, script);
		}
	}

	/**
	 * エレメントをサブミット
	 * @param by By
	 * @return エレメント有無
	 */
	private boolean submit(By by) {
		if(exists(by)) {
			run(findElement(by)::submit);
			return true;
		} else {
			return false;
		}
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
	 * 最終テキストが存在するか
	 * @return 最終テキストが存在するか
	 */
	private boolean existsFinalText() {
		// アクションのたびに待つ
		if(this.currentSite.isToWaitBeforeEveryAction()) {
			sleep(this.currentSite.getToWaitBeforeEveryActionTime());
		}
		// フロート広告を閉じる
		if(exists(this.currentSite.getFloatAdCloseButtonSelector())) {
			executeScript(this.currentSite.getFloatAdCloseScript());
		}
		// 最終テキストが存在するか
		return exists(this.currentSite.getFinalTextPath());
	}

	/**
	 * エレメントを取得
	 * @param by By
	 * @return エレメント
	 */
	private WebElement findElement(By by) {
		return apply(this.driver::findElement, by);
	}

	/**
	 * エレメントリストを取得
	 * @param by By
	 * @return エレメントリスト
	 */
	private List<WebElement> findElements(By by) {
		if(by == null) return null;
		return apply(this.driver::findElements, by);
	}

	/**
	 * エレメント配下のエレメントを取得
	 * @param element エレメント
	 * @param by By
	 * @return エレメント
	 */
	private WebElement findElement(WebElement element, By by) {
		return apply(element::findElement, by);
	}

	/**
	 * エレメントに値を設定
	 * @param by By
	 * @param value 値
	 * @return エレメント有無
	 */
	private boolean setValue(By by, String value) {
		if(exists(by)) {
			accept(findElement(by)::sendKeys, value);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * エレメントをクリック
	 * @param element エレメント
	 * @return 処理結果
	 */
	private boolean click(WebElement element) {
		// エレメントにマウスオーバー
		if(!run(apply(apply(Actions::new, this.driver)::moveToElement, element)::perform)) {
			return false;
		}
		// クリック
		return run(element::click);
	}

	/**
	 * エレメントをクリック
	 * @param by By
	 * @return 処理結果
	 */
	private boolean click(By by) {
		if(exists(by)) {
			return click(findElement(by));
		} else {
			return false;
		}
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
				String value = apply(elm::getAttribute, attr);
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
			// 選択肢が3件以上なら一番下は選択しない（その他対策）
			Double index = Math.random() * (list.size() - ((list.size() >= 3) ? 1 : 0));
			click(list.get(index.intValue()));
		}
	}

	/**
	 * SeleniumAPI実行（引数なし、戻り値なし）
	 * @param method
	 * @param arg
	 * @return 処理結果
	 */
	private boolean run(Runnable method) {
		for(;;) {
			try {
				method.run();
				return true;
			} catch(ElementNotVisibleException e) {
				// リトライ
				sleep(ERROR_WAIT_INTERVAL);
			} catch(TimeoutException e) {
				// 成功扱いで無視
				return true;
			} catch(StaleElementReferenceException e) {
				// 失敗扱いで無視
				return false;
			} catch(WebDriverException e) {
				if(e.getMessage().indexOf("Element is not clickable") >= 0) {
					// 失敗扱いで無視
					return false;
				} else if(e.getMessage().indexOf("unexpected alert open") >= 0) {
					// リトライ
					sleep(ERROR_WAIT_INTERVAL);
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * SeleniumAPI実行（引数あり、戻り値なし）
	 * @param method
	 * @param arg
	 * @return 処理結果
	 */
	private <A> boolean accept(Consumer<A> method, A arg) {
		for(;;) {
			try {
				method.accept(arg);
				return true;
			} catch(TimeoutException e) {
				// 無視
				return false;
			} catch(NoSuchWindowException e) {
				// 無視
				return false;
			} catch(WebDriverException e) {
				throw e;
			}
		}
	}

	/**
	 * SeleniumAPI実行（引数なし、戻り値あり）
	 * @param method
	 * @return
	 */
	private <R> R get(Supplier<R> method) {
		int timeout = 0;
		for(;;) {
			try {
				return method.get();
			} catch(TimeoutException e) {
				// リトライ
				if(timeout++ < TIMEOUT_RETRY_COUNT) {
					sleep(ERROR_WAIT_INTERVAL);
				} else {
					throw e;
				}
			} catch(WebDriverException e) {
				throw e;
			}
		}
	}

	/**
	 * SeleniumAPI実行（引数あり、戻り値あり）
	 * @param method
	 * @param arg
	 * @return
	 */
	private <A, R> R apply(Function<A, R> method, A arg) {
		int timeout = 0;
		for(;;) {
			try {
				return method.apply(arg);
			} catch(TimeoutException e) {
				// リトライ
				if(timeout++ < TIMEOUT_RETRY_COUNT) {
					sleep(ERROR_WAIT_INTERVAL);
				} else {
					throw e;
				}
			} catch(WebDriverException e) {
				throw e;
			}
		}

	}
}
