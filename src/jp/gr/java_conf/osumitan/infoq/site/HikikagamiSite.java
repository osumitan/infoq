package jp.gr.java_conf.osumitan.infoq.site;

import org.openqa.selenium.By;

/**
 * アンケートサイト：hikikagami.com
 */
public class HikikagamiSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public HikikagamiSite() {
		super();
		// ドメイン
		super.domain = "hikikagami.com";
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("button.next_btn");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//h1[contains(text(),'アンケートにご協力ありがとうございます。')]");
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio']/ancestor::label[contains(@for,'answers')]");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox']/ancestor::label[contains(@for,'answers')]");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select[name^='answers'] option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//p[contains(text(),'性別をお知らせください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[contains(text(),'男性')]");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//p[contains(text(),'年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[contains(text(),'40代')]");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//p[contains(text(),'お住まいをお知らせください。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//select[contains(@name,'answers')]/option[contains(text(),'千葉県')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//p[contains(text(),'職業をお知らせください。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[contains(text(),'会社員（契約・派遣社員含む）')]");
	}
}
