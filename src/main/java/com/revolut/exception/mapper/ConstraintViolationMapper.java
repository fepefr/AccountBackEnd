package com.revolut.exception.mapper;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.revolut.vo.Erro;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(final ConstraintViolationException exception) {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(processFieldErrors(exception.getConstraintViolations())).type(MediaType.APPLICATION_JSON)
				.build();
	}

	private Erro processFieldErrors(Set<ConstraintViolation<?>> cvl) {

		Erro error = new Erro(Erro.ERROR_CODE_INVALID_PARAM, Erro.ERROR_MSG_VALIDATION_ERROR);
		for (ConstraintViolation<?> v : cvl) {
			error.addFieldError(v.getPropertyPath().toString(), v.getMessage());
		}
		return error;
	}

}
