package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：enq.y2at.com
*/
public class Y2atSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public Y2atSite() {
		super();
		// ドメイン
		super.domain = "enq.y2at.com";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*enq\\.y2at\\.com.*$");
		// スタートボタンセレクタ
		super.startButtonSelector = By.cssSelector("#btnlarge");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("input[value='次へ']");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//img[@alt='こちらでアンケートは終了です。']");
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.xpath("//label[starts-with(@for,'q')]/ancestor::div/span/input[@type='radio']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.xpath("//label[starts-with(@for,'q')]/ancestor::div/span/input[@type='checkbox']");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select[name^='q'] option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//div[contains(text(),'あなたの性別をお知らせください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[starts-with(@for,'q') and text()='男性']/ancestor::div/span/input[@type='radio']");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//div[contains(text(),'あなたの年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[starts-with(@for,'q') and text()='40〜49歳']/ancestor::div/span/input[@type='radio']");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//div[contains(text(),'あなたのお住まいの都道府県をお知らせください。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//select[starts-with(@name,'q')]/option[text()='千葉県']");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//div[contains(text(),'あなたの職業をお知らせください。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[starts-with(@for,'q') and text()='会社勤務（一般社員）']/ancestor::div/span/input[@type='radio']");
	}
}
