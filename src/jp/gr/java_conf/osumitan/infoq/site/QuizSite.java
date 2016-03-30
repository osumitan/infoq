package jp.gr.java_conf.osumitan.infoq.site;

import org.openqa.selenium.By;

/**
 * アンケートサイト：gmor.ws-g.jp
 */
public class QuizSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public QuizSite() {
		super();
		// ドメイン
		super.domain = "gmor.ws-g.jp";
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("input.btn-next");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//b[contains(string(),'アンケートを終了してください')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.nextButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("input.radio");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.cssSelector("input.checkbox");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
	}
}
