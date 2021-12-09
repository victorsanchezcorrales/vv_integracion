package com.practica.integracion;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {
	
	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;
	
	private SystemManager manager;
	private InOrder ordered;
	private User userValido;
	private User userInvalido;
	private static final String idValido = "123";
	private Collection<Object> retorno;
	
	
	@BeforeEach
	void init() throws SystemManagerException, OperationNotSupportedException {
		
		userValido = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));

		when(mockAuthDao.getAuthData(userValido.getId())).thenReturn(userValido);
        
		manager = new SystemManager(mockAuthDao, mockGenericDao);
		ordered = inOrder(mockAuthDao, mockGenericDao);
		       
	}
	
	// usuario y filtro valido 1
	@Test
	public void testStartRemoteSystem1() throws SystemManagerException, OperationNotSupportedException {
		
        when(mockGenericDao.getSomeData(userValido, "where id=" + idValido)).thenReturn(Arrays.asList("uno", "dos"));

		retorno = manager.startRemoteSystem(userValido.getId(), idValido);
		assertEquals(retorno.toString(), "[uno, dos]");
		 
		ordered.verify(mockAuthDao).getAuthData(userValido.getId());
		ordered.verify(mockGenericDao).getSomeData(userValido, "where id=" + idValido);
	}
	
	// usuario valido y filtro null
	@Test
	public void testStartRemoteSystem2() throws SystemManagerException, OperationNotSupportedException {
		
        when(mockGenericDao.getSomeData(userValido, "where id=" + null)).thenReturn(Arrays.asList("uno", "dos", "tres"));

		retorno = manager.startRemoteSystem(userValido.getId(), null);
		assertEquals(retorno.toString(), "[uno, dos, tres]");
		
		ordered.verify(mockAuthDao).getAuthData(userValido.getId());
		ordered.verify(mockGenericDao).getSomeData(userValido, "where id=" + idValido);
	}

	
	
	// usuario y sistema valido
	@Test
	public void testStopRemoteSystem1() throws SystemManagerException, OperationNotSupportedException {
		
        when(mockGenericDao.getSomeData(userValido, "where id=" + idValido)).thenReturn(Arrays.asList("uno", "dos"));

		retorno = manager.startRemoteSystem(userValido.getId(), idValido);
		assertEquals(retorno.toString(), "[uno, dos]");
		 
		ordered.verify(mockAuthDao).getAuthData(userValido.getId());
		ordered.verify(mockGenericDao).getSomeData(userValido, "where id=" + idValido);
	}
	
	
	// usuario valido y filtro null
	@Test
	public void testStopRemoteSystem2() throws SystemManagerException, OperationNotSupportedException {
		
        when(mockGenericDao.getSomeData(userValido, "where id=" + null)).thenReturn(Arrays.asList("uno", "dos", "tres"));

		retorno = manager.startRemoteSystem(userValido.getId(), null);
		assertEquals(retorno.toString(), "[uno, dos, tres]");
		
		ordered.verify(mockAuthDao).getAuthData(userValido.getId());
		ordered.verify(mockGenericDao).getSomeData(userValido, "where id=" + idValido);
		
	}
	
	
	// usuario y filtro valido
	@Test
    public void testAddRemoteSystem1() throws SystemManagerException, OperationNotSupportedException {
		
		String remoto = "VV";
		
		when(mockGenericDao.updateSomeData(userValido, remoto)).thenReturn(true);
		
		manager.addRemoteSystem(userValido.getId(), remoto);

		ordered.verify(mockAuthDao).getAuthData(userValido.getId());
		ordered.verify(mockGenericDao).getSomeData(userValido, remoto);
		
	}
	
	// usuario valido y remote invalido
	@Test
    public void testAddRemoteSystem2() throws SystemManagerException, OperationNotSupportedException {
		
		String remoto = "Vv";
		when(mockGenericDao.updateSomeData(userValido, remoto)).thenReturn(false);
		
		SystemManagerException e = assertThrows(SystemManagerException.class, () -> {
			manager.addRemoteSystem(userValido.getId(), remoto);
		});
		assertEquals("cannot add remote", e.getMessage());
		
		ordered.verify(mockAuthDao).getAuthData(userValido.getId());
		ordered.verify(mockGenericDao).getSomeData(userValido, remoto);
		
	}
	
}