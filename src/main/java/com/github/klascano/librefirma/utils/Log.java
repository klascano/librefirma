/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.klascano.librefirma.utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author mfernandez
 */
public class Log {

	private static final String LOG_FILE_PATTERN = Paths.get(".").toAbsolutePath().normalize().toString()
			+ "/bitacora.log";

	public static void initLogging() {
		try {
			Handler handler = new FileHandler(LOG_FILE_PATTERN, 0, 1, true);
			handler.setFormatter(new SimpleFormatter());
			Logger.getLogger("").addHandler(handler);
		} catch (IOException e) {
			System.out.println("Error al inicializar el logging: " + e.getMessage());
		}
	}
}
