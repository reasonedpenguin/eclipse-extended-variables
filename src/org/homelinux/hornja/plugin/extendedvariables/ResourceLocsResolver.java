package org.homelinux.hornja.plugin.extendedvariables;

import java.util.Iterator;

import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.core.resources.IFile;
//import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;
import java.net.URI;
import java.io.File;



public class ResourceLocsResolver implements IDynamicVariableResolver {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.homelinux.hornja.plugin.extendedVariables";

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see IStartup#earlyStartup()
	 *
	 */
	public String resolveValue(IDynamicVariable variable, String argument) {
		String retVal;
		IStructuredSelection sel = getCurrentSelection();
		
		retVal = getStringFromSelection(sel);
		if(retVal == null) {
			//abort("No resources selected");
		}
		return retVal;
	}
		  
	public IStructuredSelection getCurrentSelection() {
		if(PlatformUI.getWorkbench().getDisplay().getThread().equals(Thread.currentThread())) {
			return getCurrentSelection0();
		}
		else {
			final IStructuredSelection[] selection = new IStructuredSelection[1];
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					selection[0] = getCurrentSelection0();
				}
			});
			return selection[0];
		}
	}
	
	private IStructuredSelection getCurrentSelection0() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if(window != null) {
			IWorkbenchPage page  = window.getActivePage();
			if(page != null) {
				IWorkbenchPart part = page.getActivePart();
				if(part instanceof IEditorPart) {
					return null;
				}
				else if(part != null) {
					IWorkbenchSite site = part.getSite();
					if(site != null) {
						ISelectionProvider provider = site.getSelectionProvider();
						if(provider != null) {
							ISelection selection = provider.getSelection();
							if(selection instanceof IStructuredSelection) {
								return (IStructuredSelection) provider.getSelection();
							}
						}
					}
				}
			}
		}
		return null;
	}


	public String getStringFromSelection(IStructuredSelection structured) {
		String retVal = "";
	
		//if the selection is a readme file, get its sections.
		Object object;
		Iterator<Object> it = structured.iterator();
		while(it.hasNext()) {
			object = it.next();
			if (object instanceof IFile) {
				IFile file = (IFile) object;
				//IPath path = file.getFullPath().makeAbsolute();
				
				//retVal += path.toString() + " ";
				retVal += ifileToPathString(file) + " ";
			}	
		}
		

		//the selected object is not a readme file
		return retVal;
	}
	
	public String ifileToPathString(IFile file) {
//		IPath path = file.getFillPath();
//		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
//		URI uri = resource.getLocationURI();
//		if(uri != null) {
//			File file = EFS.getStore(uri).toLocalFile(0, null);
//			if(file != null) {
//				return file.getAbsolutePath();
//			}
//		}
//
		try 
		{
			URI uri = file.getLocationURI();
			if(uri != null) {
				File localFile = EFS.getStore(uri).toLocalFile(0, null);
				if(localFile != null) {
					return localFile.getAbsolutePath();
				}
			}
		}
		catch(CoreException e) {

		}
		return null;

	}
	

}

