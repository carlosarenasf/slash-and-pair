package com.slashandpair.exchange;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that implements some functionality for generating QR Codes and decode
 * it.
 * 
 * @author Victor
 * @author Carlos
 * @author Guillermo
 * 
 *
 */

@Slf4j
public class QRUtils {
	/**
	 * generateQRDynamicByParameterString Method that gets input with data for
	 * codify. It returns base64 image.
	 * 
	 * @param identifier
	 *            Information to codify
	 * @return String This string corresponds to a base64 image codified ¡.
	 * @throws NotFoundException
	 * @throws ChecksumException
	 * @throws FormatException
	 */
	public static String generateQRDynamicByParameterString(String identifier)
			throws NotFoundException, ChecksumException, FormatException {
		log.info("El codigo que quieres encriptar es el siguiente {} >>>> ", identifier);
		String myCodeText = identifier;
		String filePath = "CrunchifyQR.png";
		int size = 250;
		String fileType = "png";
		File myFile = new File(filePath);
		byte[] imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {

			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");

			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
			ImageIO.write(image, fileType, bos);

			byte[] imageBytes = bos.toByteArray();
			imageString = Base64.encodeBase64(imageBytes);
			System.out.println(imageString);
			bos.close();
			// BASE64 img QR
			return (new String(imageString, StandardCharsets.UTF_8));

		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n\nYou have successfully created QR Code.");
		return null;
	}

	/**
	 * decoderQRCode that decodes an base64 img and return contained information
	 * 
	 * @param imgEncoded64
	 * @param type
	 * @throws IOException
	 * @throws NotFoundException
	 * @throws ChecksumException
	 * @throws FormatException
	 */
	public static void decoderQRCode(String imgEncoded64, String type)
			throws IOException, NotFoundException, ChecksumException, FormatException {

		// create a buffered image
		BufferedImage image = null;
		byte[] imageByte;

		imageByte = Base64.decodeBase64(imgEncoded64);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);

		QRCodeReader qrCodeReader = new QRCodeReader();

		Hashtable<DecodeHintType, Object> hintMap = new Hashtable<DecodeHintType, Object>();
		hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		QRCodeReader reader = new QRCodeReader();
		Result result;
		result = reader.decode(bitmap, hintMap);

		System.out.println(result.getText());

	}

}