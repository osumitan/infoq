package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：mini.surveyenquete.net
*/
public class SurveyEnqueteSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public SurveyEnqueteSite() {
		super();
		// ドメイン
		super.domain = "mini.surveyenquete.net";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*(mini\\.surveyenquete\\.net|ec-bought\\.com).*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//button[contains(text(),'次へ進む')]");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//div[contains(text(),'以上でアンケートは終了です')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.nextButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio' and (starts-with(@name,'Q') or starts-with(@name,'F'))]");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox' and (starts-with(@name,'Q') or starts-with(@name,'F'))]");
		// オプションセレクタ
		super.optionSelector = By.xpath("//select[starts-with(@name,'Q') or starts-with(@name,'F')]/option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//div[contains(text(),'あなたの性別をお知らせください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[text()='男性']/input[@type='radio']");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//div[contains(text(),'あなたの年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[text()='40-44歳']/input[@type='radio']");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//div[contains(text(),'あなたのお住まいをお知らせください。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//select[contains(@name,'Q')]/option[contains(text(),'千葉県')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//div[contains(text(),'あなたのご職業をお知らせください。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[text()='会社員']/input[@type='radio']");
	}
}
