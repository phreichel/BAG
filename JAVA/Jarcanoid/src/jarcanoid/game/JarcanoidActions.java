//**************************************************************************************************
package jarcanoid.game;
//**************************************************************************************************

import jade.ExampleActions;
import jade.input.Input;
import jade.input.Trigger;
import jarcanoid.entity.SliderComponent;
import lombok.Setter;

//**************************************************************************************************
public class JarcanoidActions extends ExampleActions {

	//==============================================================================================
	@Setter SliderComponent sliderComponent;
	//==============================================================================================

	//==============================================================================================
	protected void register() {
		super.register();
		this.add("SLIDER_LEFT_START", true, this::startSliderLeft);
		this.add("SLIDER_LEFT_STOP", true, this::stopSliderLeft);
		this.add("SLIDER_RIGHT_START", true, this::startSliderRight);
		this.add("SLIDER_RIGHT_STOP", true, this::stopSliderRight);
	}
	//==============================================================================================

	//==============================================================================================
	public void startSliderLeft(Trigger trigger, Input input) {
		sliderComponent.setLeft(true);
	}
	//==============================================================================================

	//==============================================================================================
	public void stopSliderLeft(Trigger trigger, Input input) {
		sliderComponent.setLeft(false);
	}
	//==============================================================================================
	
	//==============================================================================================
	public void startSliderRight(Trigger trigger, Input input) {
		sliderComponent.setRight(true);
	}
	//==============================================================================================

	//==============================================================================================
	public void stopSliderRight(Trigger trigger, Input input) {
		sliderComponent.setRight(false);
	}
	//==============================================================================================

}
//**************************************************************************************************
