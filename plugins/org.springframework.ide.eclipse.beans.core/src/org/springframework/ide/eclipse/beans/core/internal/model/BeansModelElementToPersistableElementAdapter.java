/*******************************************************************************
 * Copyright (c) 2007 Spring IDE Developers
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Spring IDE Developers - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.beans.core.internal.model;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.springframework.ide.eclipse.beans.core.model.IBeansModelElement;

/**
 * Utility class that adapts a given {@link IBeansModelElement} to be
 * {@link IPersistableElement}.
 * @author Christian Dupuis
 * @since 2.0
 */
public class BeansModelElementToPersistableElementAdapter implements
		IPersistableElement {

	private final IBeansModelElement beansModelElement;

	public BeansModelElementToPersistableElementAdapter(
			final IBeansModelElement beansModelElement) {
		this.beansModelElement = beansModelElement;
	}

	public String getFactoryId() {
		return BeansModelElementFactory.FACTORY_ID;
	}

	public void saveState(IMemento memento) {
		memento.putString(BeansModelElementFactory.ELEMENT_ID,
				beansModelElement.getElementID());
	}
}
