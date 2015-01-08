/*******************************************************************************
 * Copyright (c) 2014 Pivotal, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pivotal, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.propertiesfileeditor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.springframework.ide.eclipse.propertiesfileeditor.util.ClasspathListener;
import org.springframework.ide.eclipse.propertiesfileeditor.util.ClasspathListenerManager;
import org.springframework.ide.eclipse.propertiesfileeditor.util.ListenerManager;
import org.springframework.ide.eclipse.propertiesfileeditor.util.SpringPropertiesIndexManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class SpringPropertiesEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.springframework.ide.eclipse.properties.editor"; //$NON-NLS-1$

	// The shared instance
	private static SpringPropertiesEditorPlugin plugin;
	
	/**
	 * The constructor
	 */
	public SpringPropertiesEditorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static SpringPropertiesEditorPlugin getDefault() {
		return plugin;
	}

	public static void log(Throwable e) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
	}

	public static void warning(String msg) {
		getDefault().getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, msg, null));
	}
	
	private static ListenerManager<ClasspathListener> classpathListeners;
	
	public static synchronized ListenerManager<ClasspathListener> getClasspathListeners() {
		if (classpathListeners==null) {
			classpathListeners = new ClasspathListenerManager();
		}
		return classpathListeners;
	}
	
	private static SpringPropertiesIndexManager indexManager;

	public static SpringPropertiesIndexManager getIndexManager() {
		if (indexManager==null) {
			indexManager = new SpringPropertiesIndexManager();
		}
		return indexManager;
	}



}
