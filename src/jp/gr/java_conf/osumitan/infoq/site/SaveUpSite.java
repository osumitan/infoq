package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：saveup.jp
 */
public class SaveUpSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public SaveUpSite() {
		super();
		// ドメイン
		super.domain = "saveup.jp";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*saveup\\.jp.*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("input.btn-next");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//*[contains(text(),'以上でアンケートは終了です')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.nextButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("input.radio");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.cssSelector("input.checkbox");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//span[text()='あなたの性別をお知らせください。']");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[text()='男性']/ancestor::tr//input[contains(@class,'radio')]");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//span[text()='あなたの年齢をお知らせください。']");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[text()='40-44歳' or text()='40～44歳']/ancestor::tr//input[contains(@class,'radio')]");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//span[text()='あなたのお住まいをお知らせください。']");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//option[contains(text(),'千葉県')]");
	}
}
