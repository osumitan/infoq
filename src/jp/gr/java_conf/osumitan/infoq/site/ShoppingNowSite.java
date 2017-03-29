package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：shopping-now.jp
 */
public class ShoppingNowSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public ShoppingNowSite() {
		super();
		// ドメイン
		super.domain = "shopping-now.jp";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*shopping-now\\.jp.*$");
		// IFRAMEセレクタ
		super.iframeSelector = By.cssSelector("iframe#iframe");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("div.btn_next input");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//div[contains(@class,'question_last') or contains(@class,'question_thanks')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = By.xpath("//div[contains(@class,'btn_next') or contains(@class,'btn_2next')]/input");
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio']/following-sibling::label");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox']/following-sibling::label");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//h2[contains(text(),'あなたの性別を教えてください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[starts-with(@for,'q') and contains(text(),'男性')]");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//h2[contains(text(),'あなたの年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[starts-with(@for,'q') and contains(text(),'40代')]");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//h2[contains(text(),'あなたのお住まいを教えてください。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//option[contains(text(),'千葉県')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//h2[contains(text(),'あなたのご職業をお知らせください。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[starts-with(@for,'q') and contains(text(),'会社員（契約・派遣社員含む')]");
	}
}
