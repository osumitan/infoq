package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：manga-enquete.com
 */
public class MangaEnqueteSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public MangaEnqueteSite() {
		super();
		// ドメイン
		super.domain = "manga-enquete.com";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*manga-enquete\\.com.*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//input[@type='submit' or @type='image']");
		// 最終テキストパス
		super.finalTextPath = By.cssSelector("div#again_bt a");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.finalTextPath;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("input[type='radio']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.cssSelector("input[type='checkbox']");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//*[contains(text(),'あなたの性別をお知らせ下さい')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//input[@type='radio' and @value='1']");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//*[contains(text(),'あなたの年齢をお知らせ下さい')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//input[@type='radio' and @value='4']");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//*[contains(text(),'あなたのお住まいを教えて下さい')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//option[contains(text(),'千葉県')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//*[contains(text(),'あなたのご職業をお知らせ下さい')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//input[@type='radio' and @value='3']");
		// フロート広告クローズボタンセレクタ
		super.floatAdCloseButtonSelector = By.cssSelector("#inter-close");
		// フロート広告クローズスクリプト
		super.floatAdCloseScript = "document.getElementById('inter-close').click();";
	}
}
