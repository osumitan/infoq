package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

/**
 * アンケートサイト：adsurvey.media-ad.jp
 */
public class AdSurveySite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public AdSurveySite() {
		super();
		// ドメイン
		super.domain = "adsurvey.media-ad.jp";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*(adsurvey\\.media-ad\\.jp|apple\\.questioon\\.online).*$");
		// IFRAMEセレクタ
		super.iframeSelector = By.cssSelector("iframe.question_frame");
		// スタートボタンセレクタ
		super.startButtonSelector = By.cssSelector("div.btn_next input.btn_regular");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("div.btn_next input.btn");
		// 次へボタン押下前スクリプト
		super.scriptBeforeNextButton = "if($) $('div.layered').css('display','none');";
		// 最終テキストパス
		super.finalTextPath = By.xpath("//p[contains(text(),'アンケートは以上になります。')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.startButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("div.answer input[type='radio']");
		// チェックボックスセレクタ
		super.checkBoxSelector = By.cssSelector("div.answer input[type='checkbox']");
		// オプションセレクタ
		super.optionSelector = By.cssSelector("div.answer select option");
		// 性別質問パス
		super.genderQuestionPath = By.xpath("//h2[contains(text(),'あなたの性別を教えてください。')]");
		// 性別回答パス
		super.genderAnswerPath = By.xpath("//label[text()='男性']/preceding-sibling::input[1]");
		// 年齢質問パス
		super.ageQuestionPath = By.xpath("//h2[contains(text(),'あなたの年齢をお知らせください。')]");
		// 年齢回答パス
		super.ageAnswerPath = By.xpath("//label[text()='40代']/preceding-sibling::input[1]");
		// 居住地質問パス
		super.residenceQuestionPath = By.xpath("//h2[contains(text(),'あなたのお住まいを教えてください。')]");
		// 居住地回答パス
		super.residenceAnswerPath = By.xpath("//option[contains(text(),'千葉県')]");
		// 職業質問パス
		super.jobQuestionPath = By.xpath("//h2[contains(text(),'あなたのご職業をお知らせください。')]");
		// 職業回答パス
		super.jobAnswerPath = By.xpath("//label[text()='会社員（契約・派遣社員含む）']/preceding-sibling::input[1]");
		// アクションのたびに待つ
		this.toWaitBeforeEveryAction = true;
		// ブラックアンケートパス
		super.blackEnquetePath = By.xpath("//*[contains(text(),'アンケートは見つかりませんでした') or contains(text(),'アンケートに回答済みです。')]");
		// 同意チェックボックスセレクタ
		super.agreeCheckBoxSelector = By.cssSelector("#agree_checkbox");
	}
}
