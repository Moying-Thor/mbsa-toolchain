package mbsa.toolchain.design;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import component.Component;
import component.ComponentElement;
import component.ComponentPackage;
import component.FailureMode;
import mbsa.MBSAPackage;

public class BrowseComponentAction implements IExternalJavaAction {

	private FailureMode focus = null;
	private HashMap<String, Component> map = new HashMap();

	public BrowseComponentAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {

		
		// TODO Auto-generated method stub
		FailureMode cp = null;
		for(EObject obj: selections) {
			cp = (FailureMode) obj;
			focus = cp;
		}
		
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(Display.getDefault().getActiveShell(), new LabelProvider() {
			@Override
			public String getText(Object element) {
				// TODO Auto-generated method stub
				return super.getText(element);
			}
		});
		
		dialog.setTitle(cp.getMode());
		dialog.setElements(getHazards(cp).toArray());
		if (dialog.open() == Window.OK) {
			if (dialog.getResult().length > 0) {
				selectionChanged((String) dialog.getResult()[0]);
			}
		}
	}

	protected void selectionChanged(String selection) {
		Component r = map.get(selection);
		focus.setCitedElement(r);
	}
	
	protected ArrayList<String> getHazards(FailureMode cp) {
		MBSAPackage mbsapkg = getMBSAPackage(cp);
		ArrayList<String> ret = new ArrayList<>();
		for(ComponentPackage cpp :mbsapkg.getComponentPackage()) {
			for(ComponentElement re : cpp.getComponents()) {
				if(re instanceof Component) {
					Component hs = (Component) re;
					map.put(hs.getComponent_name(), hs);
					ret.add(hs.getComponent_name());
				}
			}
		}
		return ret;
	}
	
	protected MBSAPackage getMBSAPackage(FailureMode cp) {
		EObject obj = cp;
		while(obj.eContainer() != null && !(obj instanceof MBSAPackage)) {
			obj = obj.eContainer();
		}
		return (MBSAPackage) obj;
	}
	
	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// TODO Auto-generated method stub
		return true;
	}

}
