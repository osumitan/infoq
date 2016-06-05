package jp.gr.java_conf.osumitan.infoq.site;

import org.openqa.selenium.By;

/**
 * アンケートサイト：enq.shop-qp.com
*/
public class ShopQPSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public ShopQPSite() {
		super();
		// ドメイン
		super.domain = "enq.shop-qp.com";
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//*[@id='btnlarge' or contains(@class,'ui-button')]");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//p[contains(text(),'ご回答ありがとうございます。')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.nextButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("label[for^='radio']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.cssSelector("label[for^='check']");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select[name^='qa'] option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//h2[contains(text(),'性別をお知らせ下さい。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[contains(text(),'男性')]");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//h2[contains(text(),'年齢をお知らせ下さい。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[contains(text(),'40代')]");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//h2[contains(text(),'居住区をお知らせ下さい。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//select[@name='qa']/option[contains(text(),'関東')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//h2[contains(text(),'ご職業をお知らせ下さい。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[contains(text(),'会社員')]");
	}
}
