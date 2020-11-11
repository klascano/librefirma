package com.github.klascano.librefirma.utils;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

public class Utils {

	public String rutaFichero(FilenameFilter filtro, Frame parent) {
		String ruta = "";

		FileDialog jFileChooser = new FileDialog(parent);
		jFileChooser.setDirectory(System.getProperty("user.home"));
		jFileChooser.setFilenameFilter(filtro);
		jFileChooser.setVisible(true);

		if (jFileChooser.getFile() == null) {
			return null;
		} else {

			File file = new File(new File(jFileChooser.getDirectory()), jFileChooser.getFile());
			ruta = file.getPath();

		}

		return ruta;

	}

	public java.util.List<String> rutaFicheros(FilenameFilter filtro, Frame parent) {
		java.util.List<String> ruta = new java.util.ArrayList<>();

		FileDialog fileDialog = new FileDialog(parent);
		fileDialog.setDirectory(System.getProperty("user.home"));
		fileDialog.setFilenameFilter(filtro);
		fileDialog.setMultipleMode(true);
		fileDialog.setVisible(true);
		File[] files = fileDialog.getFiles();
		for (File file : files) {
			if (file.exists() && file.isFile()) {

				if (filtro.accept(file.getParentFile(), file.getName())) {
					ruta.add(file.toString());
				}

			}
		}

		return ruta;
	}

}
