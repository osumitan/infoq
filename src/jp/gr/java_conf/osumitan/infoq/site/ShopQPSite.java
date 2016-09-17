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
		super.nextButtonSelector = By.xpath("//*[@id='btnlarge' or contains(@class,'adnextbtn')]");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//p[contains(text(),'ご回答ありがとうございます。')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.nextButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio']/ancestor::label[contains(@for,'q')]");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox']/ancestor::label[contains(@for,'q')]");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select[name^='q'] option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//div[contains(text(),'性別をお知らせください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[contains(text(),'男性')]");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//div[contains(text(),'年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[contains(text(),'40〜49歳')]");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//div[contains(text(),'都道府県をお知らせください。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//select[contains(@name,'q')]/option[contains(text(),'千葉県')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//div[contains(text(),'職業をお知らせください。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[contains(text(),'会社勤務（一般社員）')]");
		// フロート広告クローズボタンセレクタ
		super.floatAdCloseButtonSelector = By.cssSelector("div.bnrclose");
		// フロート広告クローズスクリプト
		super.floatAdCloseScript = "$('div.bnrclose').click();";
	}
}
