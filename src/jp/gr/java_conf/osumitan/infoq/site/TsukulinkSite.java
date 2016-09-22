package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：tsukulink.jp
 */
public class TsukulinkSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public TsukulinkSite() {
		super();
		// ドメイン
		super.domain = "tsukulink.jp";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*tsukulink\\.jp.*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("button.next_btn");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//h1[contains(text(),'アンケートにご協力ありがとうございます。')]");
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//input[@type='radio' and @name='answers']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//input[@type='checkbox' and @name='answers[]']");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//p[contains(text(),'あなたの性別をお知らせください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[contains(text(),'男性')]/input[@type='radio' and @name='answers']");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//p[contains(text(),'あなたの年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[contains(text(),'40代')]/input[@type='radio' and @name='answers']");
	}
}
