package com.x.portal.assemble.designer.jaxrs.file;

import com.x.base.core.project.exception.PromptException;

class ExceptionEmptyName extends PromptException {

	private static final long serialVersionUID = -9089355008820123519L;

	ExceptionEmptyName() {
		super("名称不能为空.");
	}
}
