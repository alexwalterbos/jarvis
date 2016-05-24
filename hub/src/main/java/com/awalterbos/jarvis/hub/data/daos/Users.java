package com.awalterbos.jarvis.hub.data.daos;

import com.awalterbos.jarvis.hub.data.entities.User;
import com.google.inject.Inject;
import models.UserModel;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;

public class Users extends EntityDao<User> {
	@Inject
	public Users(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public User findByName(String name) {
		for (User user : listAll()) {
			if (name.equals(user.getUsername())) {
				return user;
			}
		}
		return null;
	}

	public boolean checkUserPass(UserModel userModel) {
		try {
			User user = findByName(userModel.getUsername());
			return BCrypt.checkpw(userModel.getPassword(), user.getPassword());
		}
		catch (NullPointerException e) {
			return false;
		}
	}
}
