//*************************************************************************************************
package phi;
//*************************************************************************************************

import java.util.List;

import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbHub;
import javax.usb.event.UsbDeviceDataEvent;
import javax.usb.event.UsbDeviceErrorEvent;
import javax.usb.event.UsbDeviceEvent;
import javax.usb.event.UsbDeviceListener;

import org.usb4java.javax.Services;

//*************************************************************************************************
public class Application implements UsbDeviceListener {

	//=============================================================================================
	@SuppressWarnings({ "unchecked", "unused" })
	private UsbDevice findDevice(UsbHub hub, short vendorId, short productId)
	{
	    for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices())
	    {
	        UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
	        if (desc.idVendor() == vendorId && desc.idProduct() == productId) {
	        	return device;
	        }
	        if (device.isUsbHub()) {
	            device = findDevice((UsbHub) device, vendorId, productId);
	            if (device != null) return device;
	        }
	    }
	    return null;
	}
	//=============================================================================================
	
	//=============================================================================================
	@SuppressWarnings("unchecked")
	private void listDevices(UsbHub hub, boolean start) throws Exception
	{
	    for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices())
	    {
	    	if (start) {
		    	UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
		        try {
			        for (int i=0; i<10; i++) {
				        var sdesc = device.getUsbStringDescriptor( (byte) i);
				        if (sdesc != null) {
				        	System.out.println(sdesc.getString());
				        }
			        }
		        } catch (Exception e) {
		        	System.out.println(e);
		        }
		        System.out.println(desc);
	    	}
	        if (device.isUsbHub()) {
	        	listDevices((UsbHub) device, start);
	        } else {
	        	if (start)
	        		device.addUsbDeviceListener(this);
	        	else
	        		device.removeUsbDeviceListener(this);
	        }
	    }
	}
	//=============================================================================================
	
	//=============================================================================================
	public void run() throws Exception {

		var services = new Services();
		var hub = services.getRootUsbHub();
		
		listDevices(hub, true);
		
		while (true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {}
			if (System.in.available() > 0) {
				break;
			}
		}
		
		listDevices(hub, false);
		
	}
	//=============================================================================================

	//=============================================================================================
	public void usbDeviceDetached(UsbDeviceEvent event) {
		System.out.println(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void errorEventOccurred(UsbDeviceErrorEvent event) {
		System.out.println(event);
	}
	//=============================================================================================

	//=============================================================================================
	public void dataEventOccurred(UsbDeviceDataEvent event) {
		System.out.println(event);
	}
	//=============================================================================================

}
//*************************************************************************************************
