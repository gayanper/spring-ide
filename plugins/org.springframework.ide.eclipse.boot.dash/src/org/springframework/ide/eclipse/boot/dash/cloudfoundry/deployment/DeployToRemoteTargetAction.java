/*******************************************************************************
 * Copyright (c) 2016-2019 Pivotal, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pivotal, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.boot.dash.cloudfoundry.deployment;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.widgets.Display;
import org.springframework.ide.eclipse.boot.dash.cloudfoundry.RemoteBootDashModel;
import org.springframework.ide.eclipse.boot.dash.model.BootDashElement;
import org.springframework.ide.eclipse.boot.dash.model.BootProjectDashElement;
import org.springframework.ide.eclipse.boot.dash.model.RunState;
import org.springframework.ide.eclipse.boot.dash.model.runtargettypes.RemoteRunTarget;
import org.springframework.ide.eclipse.boot.dash.model.runtargettypes.RemoteRunTargetType;
import org.springframework.ide.eclipse.boot.dash.views.AbstractBootDashElementsAction;
import org.springsource.ide.eclipse.commons.livexp.core.LiveExpression;
import org.springsource.ide.eclipse.commons.livexp.core.ValueListener;
import org.springsource.ide.eclipse.commons.livexp.util.Log;

import com.google.common.collect.ImmutableSet;

/**
 * @author Kris De Volder
 */
public class DeployToRemoteTargetAction<Client, Params> extends AbstractBootDashElementsAction {

	private RunState runOrDebug;
	private RemoteRunTarget<Client, Params> target;
	private ValueListener<Client> connectionListener;

	public DeployToRemoteTargetAction(Params params, RemoteRunTarget<Client, Params> target, RunState runningOrDebugging) {
		super(params);
		this.setText(target.getName());
		Assert.isLegal(target.getType() instanceof RemoteRunTargetType);
		Assert.isLegal(runningOrDebugging==RunState.RUNNING || runningOrDebugging==RunState.DEBUGGING);
		this.target = target;
		this.runOrDebug = runningOrDebugging;

		this.connectionListener = new ValueListener<Client>() {
			@Override
			public void gotValue(LiveExpression<Client> exp, Client value) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						update();
					}
				});
			}
		};

		target.addConnectionStateListener(connectionListener);
		updateEnablement();
	}

	@Override
	public void updateVisibility() {
		BootDashElement element = getSingleSelectedElement();
		setVisible(element != null && element instanceof BootProjectDashElement);
	}

	@Override
	public void updateEnablement() {
		BootDashElement element = getSingleSelectedElement();
		setVisible(element != null && element instanceof BootProjectDashElement);

		if (this.target != null && this.target instanceof RemoteRunTarget) {
			setEnabled(((RemoteRunTarget<?,?>) this.target).isConnected());
		}
	}

	@Override
	public void run() {
		try {
			final BootDashElement element = getSingleSelectedElement();
			if (element != null) {
				final IProject project = element.getProject();
				if (project != null) {
					RemoteBootDashModel cfModel = (RemoteBootDashModel) model.getSectionByTargetId(target.getId());
					//No need to wrap this in a job as it already does that itself:
					cfModel.performDeployment(ImmutableSet.of(project), ui(), runOrDebug);
				}
			}
		} catch (Exception e) {
			Log.log(e);
		}
	}

	@Override
	public void dispose() {
		target.removeConnectionStateListener(connectionListener);
		super.dispose();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"("+runOrDebug+", "+target+")";
	}

}
