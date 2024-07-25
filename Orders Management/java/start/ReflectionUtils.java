package start;

import java.lang.reflect.Field;

public class ReflectionUtils {

	public static Object[] retrieveProperties(Object object) {
		Object[] properties = new Object[object.getClass().getDeclaredFields().length];
		int index = 0;
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(object);
				properties[index++] = value;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}
