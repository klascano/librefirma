/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.klascano.librefirma.utils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

import com.github.klascano.librefirma.firmador.Main;

import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

public class TextFieldTransferHandlerComponent extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -500648609838722731L;
	private final List<String> listExtension;

	public TextFieldTransferHandlerComponent(List<String> listExtension) {
		this.listExtension = listExtension;
	}

	@Override
	public boolean canImport(JComponent com, DataFlavor[] dataFlavors) {
		for (DataFlavor flavor : dataFlavors) {
			if (flavor.equals(DataFlavor.javaFileListFlavor)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean importData(JComponent jComponent, Transferable transferable) {
		JTextField jTextField = (JTextField) jComponent;
		try {
			List list = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				File file = (File) iter.next();
				for (String str : listExtension) {
					if (getFileExtension(file.getName()).toLowerCase().equals(str)) {
						jTextField.setText(file.getCanonicalPath());
					}
				}
			}
			return true;
		} catch (IOException ex) {
			System.err.println("IOError getting data: " + ex);
		} catch (UnsupportedFlavorException e) {
			System.err.println("Unsupported Flavor: " + e);
		}
		return false;
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return null;
		}
	}

	public static void iconInformation(javax.swing.JTextField jTextField, javax.swing.JPanel jPanel,
			String nombreComponente) {
		String message = "Arrastre su " + (nombreComponente == null ? "archivo" : nombreComponente)
				+ " a este campo (Drag&Drop)";
		int width = 16, height = 16;

		Icon icon = (ImageIcon) IconFontSwing.buildIcon(GoogleMaterialDesignIcons.INFO, 16, Main.color);

		JLabel lb = new JLabel(icon);
		lb.setBounds(jTextField.getBounds().x - width, jTextField.getBounds().y, width, height);
		lb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				javax.swing.JOptionPane.showMessageDialog(null, message, "Información",
						javax.swing.JOptionPane.INFORMATION_MESSAGE);
			}
		});
		jTextField.setToolTipText(message);
		lb.setToolTipText("Información");
		jPanel.add(lb);
	}
}
