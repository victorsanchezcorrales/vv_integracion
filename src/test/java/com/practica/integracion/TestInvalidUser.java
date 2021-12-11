package com.practica.integracion;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	
	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;
	
	private SystemManager manager;
	private InOrder ordered;
	private User userInvalido;
	
	@BeforeEach
    void init() throws SystemManagerException, OperationNotSupportedException {
		
		userInvalido = new User("0001","Ana","Lopez","Madrid", new ArrayList<Object>());

		when(mockAuthDao.getAuthData(userInvalido.getId())).thenReturn(userInvalido);
        
		manager = new SystemManager(mockAuthDao, mockGenericDao);
		ordered = inOrder(mockAuthDao, mockGenericDao);
		       
	}
	
	@Test
	public void testStartRemoteSystemWithInvalidUser() throws SystemManagerException, OperationNotSupportedException {
		
		String remotoID = "123";
		when(mockGenericDao.getSomeData(userInvalido, "where id=" + remotoID)).thenThrow(new OperationNotSupportedException());
		
		SystemManagerException e = assertThrows(SystemManagerException.class, () -> {
			manager.startRemoteSystem(userInvalido.getId(), remotoID);
		});
		assertEquals(OperationNotSupportedException.class, e.getCause().getClass());
		
		ordered.verify(mockAuthDao).getAuthData(userInvalido.getId());
		ordered.verify(mockGenericDao).getSomeData(userInvalido, "where id=" + remotoID);		
	}
	
	@Test
    public void testStopRemoteSystemWithInvalidUser() throws SystemManagerException, OperationNotSupportedException {
		
		String remotoID = "123";
		when(mockGenericDao.getSomeData(userInvalido, "where id=" + remotoID)).thenThrow(new OperationNotSupportedException());
		
		SystemManagerException e = assertThrows(SystemManagerException.class, () -> {
			manager.stopRemoteSystem(userInvalido.getId(), remotoID);
		});
		assertEquals(OperationNotSupportedException.class, e.getCause().getClass());
		
		ordered.verify(mockAuthDao).getAuthData(userInvalido.getId());
		ordered.verify(mockGenericDao).getSomeData(userInvalido, "where id=" + remotoID);				
	}
	
	@Test
    public void testAddRemoteSystemWithInvalidUser() throws SystemManagerException, OperationNotSupportedException {
		
		String remoto = "VV";
		when(mockGenericDao.updateSomeData(userInvalido, remoto)).thenThrow(new OperationNotSupportedException());
		
		SystemManagerException e = assertThrows(SystemManagerException.class, () -> {
			manager.addRemoteSystem(userInvalido.getId(), remoto);
		});
		assertEquals(OperationNotSupportedException.class, e.getCause().getClass());
		
		ordered.verify(mockAuthDao).getAuthData(userInvalido.getId());
		ordered.verify(mockGenericDao).updateSomeData(userInvalido, remoto);
		
	}
	
	@Test
	public void testDeleteRemoteSystemWithInvalidUser() throws SystemManagerException, OperationNotSupportedException {
		
		String remotoID = "123";
		when(mockGenericDao.deleteSomeData(userInvalido, remotoID)).thenThrow(new OperationNotSupportedException());
		
		SystemManagerException e = assertThrows(SystemManagerException.class, () -> {
			manager.deleteRemoteSystem(userInvalido.getId(), remotoID);
		});
		assertEquals(OperationNotSupportedException.class, e.getCause().getClass());
		
		ordered.verify(mockAuthDao).getAuthData(userInvalido.getId());
		ordered.verify(mockGenericDao).deleteSomeData(userInvalido, remotoID);
	}	
}
