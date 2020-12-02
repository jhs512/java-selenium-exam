package com.sbs.selenium.exam;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.sbs.selenium.exam.dto.DCInsideArticle;
import com.sbs.selenium.exam.dto.NaverNewsArticle;

public class App {

	public void run() {
		printNaverNewsFlashLatestArticles();
		printDCInsideTreeGalleryLatestArticles();
		printPpomppuFreeLatestArticles();
		downloadUnsplashNatureImgs();
	}

	private void printNaverNewsFlashLatestArticles() {
		Util.mkdirs("downloads/naverNewsFlash");
		RemoteWebDriver driver = Util.getWebDriver();

		driver.get("https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001");

		List<WebElement> elements = driver.findElements(By.cssSelector(".type06_headline li"));

		for (WebElement element : elements) {
			WebElement aElement = element.findElement(By.cssSelector("dt:not(.photo) > a"));
			String href = aElement.getAttribute("href").trim();
			href = href.split("aid=")[1];

			String code = href.split("&")[0];
			String title = element.findElement(By.cssSelector("dt:not(.photo) > a")).getText().trim();
			String summary = element.findElement(By.cssSelector("dd > .lede")).getText().trim();
			String channel = element.findElement(By.cssSelector("dd > .writing")).getText().trim();
			String thumbUrl = "";

			try {
				thumbUrl = element.findElement(By.cssSelector("dt.photo > a > img")).getAttribute("src");
			} catch (NoSuchElementException e) {

			}

			Util.download(thumbUrl, "downloads/naverNewsFlash/" + code + ".jpg");

			NaverNewsArticle article = new NaverNewsArticle(channel, code, title, summary, thumbUrl);
			System.out.println(article);
		}
	}

	private void printDCInsideTreeGalleryLatestArticles() {
		RemoteWebDriver driver = Util.getWebDriver();
		driver.get("https://gall.dcinside.com/board/lists/?id=tree");

		List<WebElement> elements = driver.findElements(By.cssSelector(".gall_list .us-post"));

		for (WebElement element : elements) {
			int id = Util.getAsInt(element.findElement(By.cssSelector(".gall_num")).getText().trim());
			String title = element.findElement(By.cssSelector(".gall_tit")).getText().trim();
			String writer = element.findElement(By.cssSelector(".gall_writer .nickname")).getText().trim();

			String ipStart = "";

			try {
				WebElement ipElement = element.findElement(By.cssSelector(".gall_writer .ip"));
				ipStart = ipElement.getText().trim();
			} catch (NoSuchElementException e) {

			}

			String regDate = element.findElement(By.cssSelector(".gall_date")).getAttribute("title").trim();

//			String regDate = element.findElement(By.cssSelector(".gall_date")).getText().trim();
//			
//			if ( regDate.contains(":") ) {
//				regDate = Util.getNowDateStr().substring(0, 7);
//			}
//			else {
//				regDate = regDate.replaceAll("\\.", ":");
//				regDate = "20" + regDate;
//			}

			int hit = Util.getAsInt(element.findElement(By.cssSelector(".gall_count")).getText().trim());
			int recommendsCount = Util
					.getAsInt(element.findElement(By.cssSelector(".gall_recommend")).getText().trim());

			DCInsideArticle article = new DCInsideArticle(id, title, writer, ipStart, regDate, hit, recommendsCount);
			System.out.println(article);
		}
	}

	private void printPpomppuFreeLatestArticles() {
		RemoteWebDriver driver = Util.getWebDriver();
		driver.get("http://www.ppomppu.co.kr/zboard/zboard.php?id=freeboard");

		List<WebElement> elements = driver.findElements(
				By.cssSelector("#revolution_main_table tbody tr:not(.list_notice) > td:nth-child(3) > a"));

		for (WebElement element : elements) {
			String title = element.getText().trim();

			System.out.println(title);
		}
	}

	private void downloadUnsplashNatureImgs() {
		Util.mkdirs("downloads/unsplash");
		RemoteWebDriver driver = Util.getWebDriver();
		driver.get("https://unsplash.com/t/nature");

		List<WebElement> imgElements = driver
				.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img"));

		for (WebElement imgElement : imgElements) {
			String src = imgElement.getAttribute("src");

			if (src.contains("images.unsplash.com/photo-") == false) {
				continue;
			}

			String fileName = src.split("/")[3];
			fileName = fileName.split("\\?")[0];
			Util.download(src, "downloads/unsplash/" + fileName + ".jpg");

			System.out.println(src);
		}
	}
}
