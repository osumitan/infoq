package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：infopanel.jp
 */
public class InfoPanelSite extends EnqueteSite {

	/** ドメイン */
	public static final String DOMAIN = "infopanel.jp";

	/**
	 * コンストラクタ
	 */
	public InfoPanelSite() {
		super();
		// ドメイン
		super.domain = DOMAIN;
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*infopanel\\.jp.*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//input[contains(@class,'btn_next') or contains(@class,'btn_submit')]");
		// 最終テキストパス
		super.finalTextPath = By.cssSelector("img[src*='83d11f3db5fe623a6bc8b596055164922145d2d3.png']");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.nextButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("input[type='radio'][name^='q']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.cssSelector("input[type='checkbox'][name^='q']");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("select[name^='q'] option");
		// テキストエリアセレクタ
		super.textAreaSelector = By.cssSelector("textarea");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//b[contains(text(),'あなたの性別をお知らせください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[text()='男性']/ancestor::tr//input[@type='radio']");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//b[contains(text(),'あなたの年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[text()='40代']/ancestor::tr//input[@type='radio']");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//b[contains(text(),'あなたのお住まいをお知らせください。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//option[contains(text(),'千葉県')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//b[contains(text(),'あなたのご職業をお知らせください。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[text()='会社員（契約・派遣社員含む）']/ancestor::tr//input[@type='radio']");
		// 質問グループ属性
		this.questionGroupAttribute = "name";
		// 質問グループ識別子
		this.questionGroupIdentifier = "(q\\d+)";
	}
}
