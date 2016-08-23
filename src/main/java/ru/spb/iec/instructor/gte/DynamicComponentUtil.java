package ru.spb.iec.instructor.gte;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public final class DynamicComponentUtil {

	private DynamicComponentUtil() {
		// do nothing
	}

	public static <T> T loadComponentResource(URL componentResource, Object root, Object controller) {
		FXMLLoader loader = new FXMLLoader(componentResource);
		loader.setRoot(root);
		loader.setController(controller);

		return safeLoad(loader);
	}

	public static <T> T loadComponentResource(URL componentResource, Object root) {
		FXMLLoader loader = new FXMLLoader(componentResource);
		loader.setRoot(root);

		return safeLoad(loader);
	}

	private static <T> T safeLoad(FXMLLoader loader) {
		try {
			return loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
