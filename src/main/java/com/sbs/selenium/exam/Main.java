package com.sbs.selenium.exam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.sbs.selenium.exam.dto.DCInsideArticle;

public class Main {
	public static void main(String[] args) {
		printNaverNewsFlashLatestArticles();
	}

	private static void printNaverNewsFlashLatestArticles() {
		File downloadsFolder = new File("downloads/naverNewsFlash");

		if (downloadsFolder.exists() == false) {
			downloadsFolder.mkdirs();
		}

		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
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

			if (thumbUrl.length() > 0) {
				BufferedImage saveImage = null;

				try {
					saveImage = ImageIO.read(new URL(thumbUrl));
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (saveImage != null) {
					try {
						String fileName = code;
						ImageIO.write(saveImage, "jpg", new File("downloads/naverNewsFlash/" + fileName + ".jpg"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	private static void printDCInsideTreeGalleryLatestArticles() {
		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://gall.dcinside.com/board/lists/?id=tree");

		Util.sleep(1000);

		List<WebElement> elements = driver.findElements(By.cssSelector(".gall_list .us-post"));

		for (WebElement element : elements) {
			int id = Util.getAsInt(element.findElements(By.cssSelector(".gall_num")).get(0).getText().trim());
			String title = element.findElements(By.cssSelector(".gall_tit")).get(0).getText().trim();
			String writer = element.findElements(By.cssSelector(".gall_writer .nickname")).get(0).getText().trim();

			String ipStart = "";

			try {
				WebElement ipElement = element.findElement(By.cssSelector(".gall_writer .ip"));
				ipStart = ipElement.getText().trim();
			} catch (NoSuchElementException e) {

			}

			String regDate = element.findElements(By.cssSelector(".gall_date")).get(0).getAttribute("title").trim();

//			String regDate = element.findElements(By.cssSelector(".gall_date")).get(0).getText().trim();
//			
//			if ( regDate.contains(":") ) {
//				regDate = Util.getNowDateStr().substring(0, 7);
//			}
//			else {
//				regDate = regDate.replaceAll("\\.", ":");
//				regDate = "20" + regDate;
//			}

			int hit = Util.getAsInt(element.findElements(By.cssSelector(".gall_count")).get(0).getText().trim());
			int recommendsCount = Util
					.getAsInt(element.findElements(By.cssSelector(".gall_recommend")).get(0).getText().trim());

			DCInsideArticle article = new DCInsideArticle(id, title, writer, ipStart, regDate, hit, recommendsCount);
			System.out.println(article);
		}
	}

	private static void printPpomppuFreeLatestArticles() {
		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("http://www.ppomppu.co.kr/zboard/zboard.php?id=freeboard");

		Util.sleep(1000);

		List<WebElement> elements = driver.findElements(
				By.cssSelector("#revolution_main_table tbody tr:not(.list_notice) > td:nth-child(3) > a"));

		for (WebElement element : elements) {
			String title = element.getText().trim();

			System.out.println(title);
		}
	}

	private static void downloadUnsplashNatureImgs() {
		Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");

		// WebDriver 경로 설정
		System.setProperty("webdriver.chrome.driver", path.toString());

		// WebDriver 옵션 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		// options.setHeadless(true);

		// WebDriver 객체 생성
		ChromeDriver driver = new ChromeDriver(options);

		// 빈 탭 생성
		// driver.executeScript("window.open('about:blank','_blank');");

		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		// 첫번째 탭으로 전환
		driver.switchTo().window(tabs.get(0));
		driver.get("https://unsplash.com/t/nature");

		File downloadsFolder = new File("downloads/unsplash");

		if (downloadsFolder.exists() == false) {
			downloadsFolder.mkdirs();
		}

		Util.sleep(1000);

		List<WebElement> imgElements = driver
				.findElements(By.cssSelector("[data-test=\"masonry-grid-count-three\"] img"));

		for (WebElement imgElement : imgElements) {
			String src = imgElement.getAttribute("src");

			if (src.contains("images.unsplash.com/photo-") == false) {
				continue;
			}

			BufferedImage saveImage = null;

			try {
				saveImage = ImageIO.read(new URL(src));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (saveImage != null) {
				try {

					String fileName = src.split("/")[3];
					fileName = fileName.split("\\?")[0];
					ImageIO.write(saveImage, "jpg", new File("downloads/unsplash/" + fileName + ".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			System.out.println(src);
		}
	}
}
