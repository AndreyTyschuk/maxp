package main.utils;

import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Screenshooter {

    private final static Screenshooter INSTANCE = new Screenshooter();
    private static int screenshotNumber = 1;

    private Screenshooter() {
    }

    public static Screenshooter getInstance() {
        return INSTANCE;
    }

    @Attachment(value = "ScreenshotAttachment", type = "image/png")
    public byte[] takeScreenShot() {
        return captureScreenShot();
    }

    public void takeScreenshotForManual(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                BufferedImage image = new AShot().shootingStrategy(
                    ShootingStrategies.viewportPasting(100)).takeScreenshot(DriverManager.getInstance().getDriver()).getImage();
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd__HH-mm-ss");
                Path dir = Paths.get("./target/screenshots");
                if (!Files.exists(dir)) Files.createDirectory(dir);
                String path = String.format(dir + "/%1$s__%2$d__%3$s.png", dateTime.format(formatter), screenshotNumber, result.getName());
                ImageIO.write(image, "png", new File(path));
                screenshotNumber++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] captureScreenShot() {
        try {
            BufferedImage image = new AShot().shootingStrategy(
                    ShootingStrategies.viewportPasting(100)).takeScreenshot(DriverManager.getInstance().getDriver()).getImage();
            Graphics graphics = image.getGraphics();
            graphics.setFont(new Font("Serif", Font.PLAIN, 10));
            graphics.setColor(Color.WHITE);
            graphics.drawString(DriverManager.getInstance().getDriver().getCurrentUrl(), 0, image.getHeight() - 5);
            graphics.dispose();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Attachment Content Empty, can't create screenshot".getBytes();
    }
}
