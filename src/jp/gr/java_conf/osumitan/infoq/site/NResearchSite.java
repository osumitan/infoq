package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：n-research.jp
 */
public class NResearchSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public NResearchSite() {
		super();
		// ドメイン
		super.domain = "n-research.jp";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*n-research\\.jp.*$");
		// スタートボタンセレクタ
		super.startButtonSelector = By.xpath("//input[@value='回答する']");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//p[@class='btm']/input");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//div[@class='end_area']/p[contains(text(),'ご回答ありがとうございました。')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = By.xpath("//input[@value='ポイントをGETする！']");;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio']");
		// フロート広告クローズボタンセレクタ
		super.floatAdCloseButtonSelector = By.cssSelector("#mdl_close");
		// フロート広告クローズスクリプト
		super.floatAdCloseScript = "document.getElementById('mdl_close').click();";
		// アクションのたびに待つ
		this.toWaitBeforeEveryAction = true;
	}
}
