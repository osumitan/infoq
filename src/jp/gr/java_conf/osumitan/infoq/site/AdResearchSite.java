package jp.gr.java_conf.osumitan.infoq.site;

import org.openqa.selenium.By;

/**
 * アンケートサイト：ad-research.jp
 */
public class AdResearchSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public AdResearchSite() {
		super();
		// ドメイン
		super.domain = "ad-research.jp";
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector(".ui-button");
		// 最終テキストパス
		super.finalTextPath = By.cssSelector(".ui-button[value='ポイント獲得']");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.finalTextPath;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[contains(@class,'ui-input-radio')]/ancestor::li");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[contains(@class,'ui-input-checkbox')]/ancestor::li");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select.ui-select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//*[contains(text(),'性別をお知らせ下さい')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//input[contains(@class,'ui-input-radio') and @value='1']/ancestor::li");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//*[contains(text(),'年齢をお知らせ下さい')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//input[contains(@class,'ui-input-radio') and @value='4']/ancestor::li");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//*[contains(text(),'居住区をお知らせ下さい')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//select[contains(@class,'ui-select')]/option[contains(text(),'関東・甲信越')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//*[contains(text(),'ご職業をお知らせ下さい')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//input[contains(@class,'ui-input-radio') and @value='3']/ancestor::li");
	}
}
