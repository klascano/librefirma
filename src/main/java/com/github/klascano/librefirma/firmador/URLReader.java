package com.github.klascano.librefirma.firmador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;

public class URLReader {

	public static File copyURLToFile(URL url) throws IOException {

		boolean flagName = true;
		File download = null;
		// open the connection
		URLConnection con = url.openConnection();
		// get and verify the header field
		String fieldValue = con.getHeaderField("Content-Disposition");
		if (fieldValue == null || !fieldValue.contains("filename=\"")) {
			download = File.createTempFile("remoto", "-suffix");
			flagName = false;
		} else {
			String filename = fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1);
			download = new File(System.getProperty("java.io.tmpdir"), filename);
		}

		// open the stream and download
		ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
		FileOutputStream fos = new FileOutputStream(download);
		try {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} finally {
			fos.close();
		}

		if (flagName == false) {

			String mimeType = Files.probeContentType(download.toPath());

			if (mimeType.equalsIgnoreCase("application/pdf")) {

				File tmpFile = File.createTempFile("remoto", ".pdf");

				FileChannel src = new FileInputStream(download).getChannel();
				FileChannel dest = new FileOutputStream(tmpFile).getChannel();
				dest.transferFrom(src, 0, src.size());

				src.close();
				dest.close();

				download = tmpFile;

			}

		}

		return download;
	}

	public static void main(String[] args) throws IOException {

		// URL pointing to the file
		String sUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";

		URL url = new URL(sUrl);

		// File where to be downloaded
		File file = new File("/home/klascano/demo.pdf");

		// URLReader.copyURLToFile(url, file);
	}

}