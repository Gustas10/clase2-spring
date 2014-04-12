package org.upiita.spring.jdbc.daos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.upiita.spring.jdbc.entidades.Usuario;

//@Component REGISTRA CUALQUIER CLASE EN EL CONTEXTO DE SPRING

@Component("usuarioDAO")
public class HibernateUsuarioDAO implements UsuarioDAO {
	
	//esta es una clase de utileria de hibernate
	
	//inyectando una instancia
	
	@Autowired 
	private SessionFactory sessionFactory;
	
	

	public Usuario buscaUsuarioPorId(Integer usuarioId) {
		
		//primero creamos una session de hibernate
		Session sesion=sessionFactory.openSession();
		
		//una vez que tienen la sesion de hibernate
		//deben de abrir una transaccion
		sesion.beginTransaction();
		
		//inicia la transaccion
		
		//busca por id en la tabla mapeada por la clase Usuario
		//el renglon cuyo id sea igual a usuarioId
		//sino encuentra nada regresa null
		//el get regresa un objeto de java
		Usuario usuario= (Usuario) sesion.get(Usuario.class, usuarioId);
		
		//termina la transacción
		sesion.getTransaction().commit();		
		//cerramos la sesion de hibernate
		sesion.close();
		
		
		return usuario;
	}

	public void creaUsuario(Usuario usuario) {

		//primero creamos una session de hibernate
		Session sesion=sessionFactory.openSession();
				
		//una vez que tienen la sesion de hibernate
		//deben de abrir una transaccion
		sesion.beginTransaction();
				
		//inicia la transaccion
		
		//crea un nuevo renglon en la tabla
		//cuyas columnas se llenan con las propiedades de
		//la instancia usuario
		sesion.save(usuario); //este save es equivalente a un insert sql
			
				
		//termina la transacción
		sesion.getTransaction().commit();		
		//cerramos la sesion de hibernate
		sesion.close();		
		
		
	}
	
	
	public Usuario buscaPorEmail(String email){
		//primero creamos una session de hibernate
		Session sesion=sessionFactory.openSession();
				
		//una vez que tienen la sesion de hibernate
		//deben de abrir una transaccion
		sesion.beginTransaction();
				
		Criteria criterio=sesion.createCriteria(Usuario.class);
		
		criterio.add(Restrictions.eq("email", email));
		
		//si saben que va a regresar una sola entidad
		Usuario usuario=(Usuario) criterio.uniqueResult();		
				
		//termina la transacción
		sesion.getTransaction().commit();
		sesion.close();
		
		
		return usuario;
	}
	
	public List<Usuario> obtenPorNombre(String nombre){
		//primero creamos una session de hibernate
		Session sesion=sessionFactory.openSession();
				
		//una vez que tienen la sesion de hibernate
		//deben de abrir una transaccion
		sesion.beginTransaction();
				
		Criteria criterio=sesion.createCriteria(Usuario.class);
		
		criterio.add(Restrictions.not(Restrictions.like("nombre","%" + nombre + "%")) );
		
		//si saben que va a regresar una sola entidad
		List<Usuario> usuarios=criterio.list();		
				
		//termina la transacción
		sesion.getTransaction().commit();			
		sesion.close();
		
		return usuarios;
	}

}
