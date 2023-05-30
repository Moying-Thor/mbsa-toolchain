package mbsa.toolchain.design;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import base.ModelElement;

public class PerformQueryExcelAction implements IExternalJavaAction {

	public PerformQueryExcelAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		EObject focus = null;
		for(EObject eobj: selections) {
			focus = eobj;
		}
		ModelElement modelElement = (ModelElement) focus;
		
//		cp.getImplementationConstraint().get(3).getContent().getValue().get(0).setContent("Ö´ÐÐÍê±Ï£¡");
		
		String doc_path = modelElement.getImplementationConstraint().get(0).getContent().getValue().get(0).getContent();
		String metadata_path = modelElement.getImplementationConstraint().get(1).getContent().getValue().get(0).getContent();
		//assuming EMF models for now
		String constraint = modelElement.getImplementationConstraint().get(2).getContent().getValue().get(0).getContent();
		String result = "Query Result";
		try {
			result = UtilityMethods.executeQueryOnExcel(doc_path, metadata_path, constraint);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		modelElement.getImplementationConstraint().get(3).getContent().getValue().get(0).setContent(result);

//		modelElement.getExternalReference().getImplementationConstraint().get(1).getContent().getValue().get(0).setContent(result);
	}

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// TODO Auto-generated method stub
		return true;
	}

}
