/**
 * アンケートサイト：question-hiroba.com
 */
package jp.gr.java_conf.osumitan.infoq.site;

import java.util.regex.Pattern;

import org.openqa.selenium.By;

public class QuestionHirobaSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public QuestionHirobaSite() {
		super();
		// ドメイン
		super.domain = "question-hiroba.com";
		// ドメインパターン
		super.domainPattern = Pattern.compile("^.*(question-hiroba\\.com|minitame\\.com|minnanosurvey\\.com).*$");
		// 次へボタンセレクタ
		super.nextButtonSelector = By.cssSelector("button.next-btn");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//button[contains(@class,'next-btn') and contains(text(),'回答を完了する')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.finalTextPath;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("label[for^=Q]");
	}
}
