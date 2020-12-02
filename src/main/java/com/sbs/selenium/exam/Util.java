package com.sbs.selenium.exam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Util {

	private static RemoteWebDriver remoteWebDriver;

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getAsInt(String str) {
		if (str == null) {
			return 0;
		}

		str = str.trim();

		if (str.length() == 0) {
			return 0;
		}

		str = str.replaceAll(",", "");

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format1.format(System.currentTimeMillis());
		return dateStr;
	}

	public static void download(String url, String destFilePath) {
		if (url.length() > 0) {
			BufferedImage saveImage = null;

			try {
				saveImage = ImageIO.read(new URL(url));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (saveImage != null) {
				try {
					ImageIO.write(saveImage, "jpg", new File(destFilePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void mkdirs(String path) {
		File downloadsFolder = new File(path);

		if (downloadsFolder.exists() == false) {
			downloadsFolder.mkdirs();
		}
	}

	public static RemoteWebDriver getWebDriver() {
		if (remoteWebDriver != null) {
			return remoteWebDriver;
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

		remoteWebDriver = driver;

		return remoteWebDriver;
	}
}
