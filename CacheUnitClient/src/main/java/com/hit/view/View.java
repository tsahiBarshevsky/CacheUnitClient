package com.hit.view;

import java.awt.event.ActionListener;

public interface View {

	void start();
	<T> void updateUIData(T t);
	void addController (ActionListener controller);
}
