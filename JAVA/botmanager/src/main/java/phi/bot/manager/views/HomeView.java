package phi.bot.manager.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

	public HomeView() {

        add(new H1("Welcome to your new application"));
        add(new Paragraph("This is the home view"));
        add(new Paragraph("You can edit this view in src\\main\\java\\phi\\bot\\manager\\views\\HomeView.java"));
       
        add(new Button("Click!", this::onClick));
        
    }
	
	private void onClick(ClickEvent<Button> event) {
		Notification.show("dsffsdf");
	}
	
}
