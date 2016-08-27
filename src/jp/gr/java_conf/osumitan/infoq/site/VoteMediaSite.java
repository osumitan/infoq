package jp.gr.java_conf.osumitan.infoq.site;

import org.openqa.selenium.By;

/**
 * アンケートサイト：vote.media-ad.jp
 */
public class VoteMediaSite extends EnqueteSite {

	/**
	 * コンストラクタ
	 */
	public VoteMediaSite() {
		super();
		// ドメイン
		super.domain = "vote.media-ad.jp";
		// 次へボタンセレクタ
		super.nextButtonSelector = By.xpath("//*[contains(@class,'start__button') or contains(@class,'button--answer')]");
		// 最終テキストパス
		super.finalTextPath = By.xpath("//*[(contains(@class,'button--answer') and @value='回答を終了する')]");
		// 最終ボタンセレクタ
		super.finalButtonSelector = super.nextButtonSelector;
		// ラジオボタンセレクタ
		super.radioButtonSelector = By.cssSelector("label.radio");
		// 次へボタンがないとき待つ
		this.toWaitWhenNextButtonNotFound = true;
	}
}
