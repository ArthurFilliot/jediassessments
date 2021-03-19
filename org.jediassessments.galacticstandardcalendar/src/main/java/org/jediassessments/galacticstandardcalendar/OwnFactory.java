package org.jediassessments.galacticstandardcalendar;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public interface OwnFactory {
	static <T> T newInstance(Class<T> clazz, Object[] args) {
		try {
			Class<?>[] types = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++) {
				types[i] = args[i].getClass();
			}
			System.out.println("OK");
			Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
			for (Constructor<?> constructor : declaredConstructors) {
				System.out.println(constructor);
				if (Arrays.equals(types, constructor.getParameterTypes())) {
					T instance = (T) constructor.newInstance(args);
					System.out.println(instance);
					return instance;
				}
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
