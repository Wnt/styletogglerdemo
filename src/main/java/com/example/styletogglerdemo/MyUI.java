package com.example.styletogglerdemo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.styletoggler.StyleToggler;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.example.styletogglerdemo.MyWidgetSet")
public class MyUI extends UI {
	private VerticalLayout layout;

	public Component getTestComponent() {
		Button enhancedButton = new Button("This button toggles the style already on the client-side");
		Button normalButton = new Button("This button toggles the style on the server-side",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						String currentStyles = layout.getStyleName();
						if (currentStyles.contains("red-bg-color")) {
							layout.removeStyleName("red-bg-color");
						} else {
							layout.addStyleName("red-bg-color");
						}
					}
				});
		layout = new VerticalLayout(enhancedButton, normalButton, new Label(
				"The resposiveness difference is quite noticable on slow connections or "
				+ "if you enable network throtling in your browser's developer tools"));
		layout.setSpacing(true);
		layout.setMargin(true);
		new StyleToggler(enhancedButton, layout, "red-bg-color");

		return layout;
	}

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
        setContent(getTestComponent());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
