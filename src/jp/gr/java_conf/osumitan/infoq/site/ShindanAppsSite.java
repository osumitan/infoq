package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：shindan-apps.net
 */
public class ShindanAppsSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public ShindanAppsSite() {
		super();
		// ドメイン
		super.domain = "shindan-apps.net";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*(shindan-apps\\.net|syouhisya-kinyu\\.com).*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//a[not(contains(@style,'none')) and (text()='　次へ　' or text()='　終了　')]");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//a[contains(text(),'作業終了する（最後に必ずクリック）')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.finalTextPath;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//div[not(contains(@class,'dia-invisible'))]/label[contains(@for,'rdo')]");
		// アクションのたびに待つ
		this.toWaitBeforeEveryAction = true;
		// アクションのたびに待つ時間
		this.toWaitBeforeEveryActionTime = 3000L;
	}
}
