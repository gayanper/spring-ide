/*******************************************************************************
 *  Copyright (c) 2012 VMware, Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *      VMware, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.config.ui.editors.webflow.graph.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.mylyn.commons.ui.CommonImages;
import org.springframework.ide.eclipse.config.ui.editors.webflow.graph.WebFlowImages;
import org.springframework.ide.eclipse.config.ui.editors.webflow.graph.model.EndStateModelElement;


/**
 * @author Leo Dos Santos
 */
public class EndStateGraphicalEditPart extends AbstractStateGraphicalEditPart {

	public EndStateGraphicalEditPart(EndStateModelElement state) {
		super(state);
	}

	@Override
	protected IFigure createFigure() {
		Label l = (Label) super.createFigure();
		l.setIcon(CommonImages.getImage(WebFlowImages.END));
		return l;
	}

	@Override
	public EndStateModelElement getModelElement() {
		return (EndStateModelElement) getModel();
	}

}
